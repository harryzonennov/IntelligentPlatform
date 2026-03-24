package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.List;

@Service
public class DocMatItemToParentExecutorCase<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode>
        extends DocActionExecutorCase<R, T, Item> {

    /**
     * Try to execute action for item list from service model, if all the item from parent service module is done
     * execution, then
     * trying
     * to execute parent node
     * @param serialLogonInfo
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     */
    public void batchExecItemTryExecuteParent(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
            DocActionExecutorCase.DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
            SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityInstallationException, ServiceModuleProxyException {
        R serviceModule = docItemBatchExecutionRequest.getServiceModule();
        DocumentContentSpecifier<R, T, Item> documentContentSpecifier =
                docActionExecutionProxy.getDocumentContentSpecifier();
        serviceModule = refreshLoadServiceModule(documentContentSpecifier, serviceModule);
        List<ServiceEntityNode> allDocMatItemList =
                docActionExecutionProxy.getDocumentContentSpecifier().getDocMatItemList(serviceModule);
        if(ServiceCollectionsHelper.checkNullList(allDocMatItemList)){
            return;
        }
        /*
         * [Step 2] Check if parent node match condition
         */
        T parentDocNode = docActionExecutionProxy.getDocumentContentSpecifier().getCoreEntity(serviceModule);
        int docActionCode = docItemBatchExecutionRequest.getDocActionCode();
        boolean skipFlag = docItemBatchExecutionRequest.getSkipFlag();
        DocActionConfigure docActionConfigure;
        try {
            docActionConfigure = docActionExecutionProxy.getDocActionConfigureByCode(docActionCode, parentDocNode.getClient());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        if(docActionCode > 0 && !docItemBatchExecutionRequest.getSkipParentCheck()){
            // In case standard action, need to use standard action code check.
            boolean checkParent = docActionExecutionProxy.checkStatus(parentDocNode,docActionCode, docActionConfigure
                    , serialLogonInfo.getLanguageCode(), skipFlag);
            if(!checkParent){
                return;
            }
        }

        /*
         * Check logic: if need to navigate to update parent node
         */
        for(ServiceEntityNode itemSENode: allDocMatItemList){
            boolean itemCheckResult = checkItemForCalculate((Item) itemSENode, docActionExecutionProxy,
                    docItemBatchExecutionRequest);
            if(!itemCheckResult){
                return; // exit method if one item don't match
            }
        }
        /*
         * [Step4] Try to update parent node and execute standard action process
         */
        if(docActionCode > 0){
            // In case have to trigger standard doc action
            docActionExecutionProxy.defExecuteActionCore(serviceModule, docActionCode, docItemBatchExecutionRequest.getDocActionCallback(),
                    null, serialLogonInfo);
        } else {
            // In case total custom action for parent node
            docItemBatchExecutionRequest.getDocActionCallback().execute(parentDocNode, serialLogonInfo);
        }
    }

}
