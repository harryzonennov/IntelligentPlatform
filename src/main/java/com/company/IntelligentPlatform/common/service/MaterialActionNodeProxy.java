package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialStockKeepUnitUIModel;
import com.company.IntelligentPlatform.common.dto.RegisteredProductActionLogUIModel;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProductActionLog;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.DocActionNodeUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaterialActionNodeProxy {

    public static final String METHOD_ConvMaterialActionNodeToUI = "convMaterialActionNodeToUI";

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ServiceDocumentComProxy serviceDocumentComProxy;

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    public static final String PROPERTIES_RESOURCE = "MaterialStockKeepUnit_actionCode";

    protected Map<String, Map<Integer, String>> actionCodeMapLan = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(MaterialActionNodeProxy.class);


    public ServiceUIModelExtension genDefServiceUIModelExtension(DocActionNodeProxy.DocActionNodeInputPara docActionNodeInputPara)
            throws ServiceEntityConfigureException {
        ServiceUIModelExtension serviceUIModelExtension = new ServiceUIModelExtension();
        serviceUIModelExtension.setChildUIModelExtensions(null);
        serviceUIModelExtension.setUIModelExtensionUnion(genDefUIModelExtensionUnion(docActionNodeInputPara));
        return serviceUIModelExtension;
    }

    public List<RegisteredProductActionLogUIModel> getSubActionCodeUIModelList(String parentNodeUUID, String seName,
                                                                               String nodeName,
                                                                               String actionCode,
                                                                               ServiceEntityManager serviceEntityManager, LogonInfo logonInfo) throws ServiceEntityConfigureException, ServiceModuleProxyException {
        List<DocActionNodeUIModel> docActionNodeUIModelList =
                docActionNodeProxy.getSubActionCodeUIModelList(parentNodeUUID, seName, nodeName,
                        actionCode,
                        docActionNode -> {
                            int actionCodeInt = ServiceEntityStringHelper.checkNullString(actionCode) ? 0 :
                                    Integer.parseInt(actionCode);
                            ServiceUIModelExtension defServiceUIModelExtension =
                                    genDefServiceUIModelExtension(new DocActionNodeProxy.DocActionNodeInputPara(seName, nodeName, nodeName,
                                            serviceEntityManager, actionCodeInt));
                            DocActionNodeUIModel docActionNodeUIModel = null;
                            try {
                                docActionNodeUIModel = (RegisteredProductActionLogUIModel) serviceEntityManager
                                        .genUIModelFromUIModelExtension(
                                                RegisteredProductActionLogUIModel.class,
                                                defServiceUIModelExtension
                                                        .genUIModelExtensionUnion().get(0),
                                                docActionNode, logonInfo,
                                                null);
                            } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
                                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                                throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG, e.getErrorMessage());
                            }
                            return docActionNodeUIModel;
                        }, serviceEntityManager, logonInfo);
        if (ServiceCollectionsHelper.checkNullList(docActionNodeUIModelList)) {
            return null;
        }
        List<RegisteredProductActionLogUIModel> resultList =
                docActionNodeUIModelList.stream().map(docActionNodeUIModel -> {
                    return (RegisteredProductActionLogUIModel) docActionNodeUIModel;
                }).collect(Collectors.toList());
        return resultList;
    }


    public List<ServiceUIModelExtensionUnion> genDefUIModelExtensionUnion(DocActionNodeProxy.DocActionNodeInputPara docActionNodeInputPara)
            throws ServiceEntityConfigureException {
        // UI Model Configure of node:[ActionCodeUnion]
        if (docActionNodeInputPara.getConvToUIMethod() == null) {
            docActionNodeInputPara.setConvToUIMethod(METHOD_ConvMaterialActionNodeToUI);
            Class<?>[] convToUIParas = {  RegisteredProductActionLog.class, RegisteredProductActionLogUIModel.class };
            docActionNodeInputPara.setConvToUIMethodParas(convToUIParas);
            docActionNodeInputPara.setLogicManager(this);
        }
        return docActionNodeProxy.genDefUIModelExtensionUnion(docActionNodeInputPara);
    }

    /**
     * Utility Method to Convert
     */
    public void convMaterialActionNodeToUI(RegisteredProductActionLog registeredProductActionLog,
                                           RegisteredProductActionLogUIModel registeredProductActionLogUIModel) {
        convMaterialActionNodeToUI(registeredProductActionLog, registeredProductActionLogUIModel, null);
    }

    public Map<Integer, String> getMaterialActionCodeMap(String languageCode)
            throws ServiceEntityInstallationException {
        return ServiceLanHelper.initDefLanguageMapResource(languageCode,
                this.actionCodeMapLan,
                MaterialStockKeepUnitUIModel.class.getResource("").getPath() + PROPERTIES_RESOURCE);
    }

    /**
     * Utility Method to Convert
     */
    public void convMaterialActionNodeToUI(RegisteredProductActionLog registeredProductActionLog,
                                           RegisteredProductActionLogUIModel registeredProductActionLogUIModel,
                                           LogonInfo logonInfo) {
        docActionNodeProxy.convDocActionNodeToUI(registeredProductActionLog, registeredProductActionLogUIModel);
        if(logonInfo != null){
            try {
                Map<Integer, String> docActionCodeMap =
                        getMaterialActionCodeMap(logonInfo.getLanguageCode());
                registeredProductActionLogUIModel.setDocActionCodeLabel(docActionCodeMap.get(registeredProductActionLog.getDocActionCode()));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            }
        }
    }


}
