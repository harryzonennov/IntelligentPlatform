package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServiceCrossActionHandler {

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;
    /**
     * Central handler method for standard cross doc action event.
     * @param serviceCrossDocActionEvent
     */
    public void handleCrossDocEvent(ServiceCrossDocActionEvent serviceCrossDocActionEvent,
                                    DocActionExecutionProxy homeDocActionExecutionProxy, SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            ServiceEntityInstallationException {

        /*
         * [Step 1] get doc configure from source doc
         */
        List<CrossDocActConfigure> crossDocActConfigureList =
                filterCurrentCrossDocActList(homeDocActionExecutionProxy.getCrossDocActConfigureList(serialLogonInfo.getClient()),
                        serviceCrossDocActionEvent.getSourceActionCode());
        /*
         * [Step 2] Try to get each target sub doc mat item
         */
        List<ServiceEntityNode> sourceDocMatItemList = serviceCrossDocActionEvent.getSourceDocMatItemList();
        if(ServiceCollectionsHelper.checkNullList(sourceDocMatItemList) || ServiceCollectionsHelper.checkNullList(crossDocActConfigureList)){
            return;
        }
        Map<String, DocExchangeUnion> serviceModuleMap = new HashMap<>();
        for(CrossDocActConfigure crossDocActConfigure: crossDocActConfigureList){
            List<ServiceEntityNode> targetDocMatItemList = docFlowProxy.getPrevNextDocMatItemList(sourceDocMatItemList,
                    crossDocActConfigure.getTargetDocType(), crossDocActConfigure.getCrossDocRelationType());
            DocActionExecutionProxy targetDocActExecutionProxy =
                                    docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(crossDocActConfigure.getTargetDocType());
            if(!ServiceCollectionsHelper.checkNullList(targetDocMatItemList)){
                for(ServiceEntityNode tmpSENode: targetDocMatItemList){
                    if(serviceModuleMap.containsKey(tmpSENode.getParentNodeUUID())){
                        DocExchangeUnion docExchangeUnion = serviceModuleMap.get(tmpSENode.getParentNodeUUID());
                        List<ServiceEntityNode> selectedItemList = docExchangeUnion.getSelectedItemList();
                        ServiceCollectionsHelper.mergeToList(selectedItemList, tmpSENode);
                    } else {
                        ServiceModule targetServiceModel =
                                targetDocActExecutionProxy.getDocumentContentSpecifier().loadServiceModule(tmpSENode.getParentNodeUUID(), tmpSENode.getClient());
                        DocExchangeUnion docExchangeUnion =
                                new DocExchangeUnion(ServiceCollectionsHelper.asList(tmpSENode), targetServiceModel);
                        serviceModuleMap.put(tmpSENode.getParentNodeUUID(), docExchangeUnion);
                    }
                }
                /*
                 * [Step 3] Execute target action
                 */
                Set<String> parentNodeUUIDSets = serviceModuleMap.keySet();
                for(String parentNodeUUID: parentNodeUUIDSets){
                    DocExchangeUnion docExchangeUnion = serviceModuleMap.get(parentNodeUUID);
                    if(crossDocActConfigure.getTriggerDocActionScenario() == ServiceCrossDocEventMonitor.TRIG_DOCACT_SCEN_SELECTITEM){
                        targetDocActExecutionProxy.batchExecSelectedItemTryExecuteParent(
                                new DocActionExecutorCase.DocItemBatchExecutionRequest<>(docExchangeUnion.getServiceModule(),
                                        targetDocMatItemList, crossDocActConfigure.getTargetActionCode(), null,
                                        null), serialLogonInfo);
                    }
                }
            }
        }
    }

    private List<CrossDocActConfigure> filterCurrentCrossDocActList(List<CrossDocActConfigure> crossDocActConfigureList, int sourceActionCode){
        if(ServiceCollectionsHelper.checkNullList(crossDocActConfigureList)){
            return null;
        }
        return crossDocActConfigureList.stream().filter(crossDocActConfigure ->
                crossDocActConfigure.getTriggerHomeActionCode() == sourceActionCode).collect(Collectors.toList());
    }

    private static class DocExchangeUnion{

        private List<ServiceEntityNode> selectedItemList;

        private ServiceModule serviceModule;

        public DocExchangeUnion() {
        }

        public DocExchangeUnion(List<ServiceEntityNode> selectedItemList, ServiceModule serviceModule) {
            this.selectedItemList = selectedItemList;
            this.serviceModule = serviceModule;
        }

        public List<ServiceEntityNode> getSelectedItemList() {
            return selectedItemList;
        }

        public void setSelectedItemList(List<ServiceEntityNode> selectedItemList) {
            this.selectedItemList = selectedItemList;
        }

        public ServiceModule getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(ServiceModule serviceModule) {
            this.serviceModule = serviceModule;
        }
    }

}
