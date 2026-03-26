package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.controller.ServiceFlowCondGroupUIModel;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowCondGroup;
import com.company.IntelligentPlatform.common.model.ServiceFlowModel;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class ServiceFlowCondGroupManager {

    public static final String METHOD_ConvServiceFlowCondGroupToUI = "convServiceFlowCondGroupToUI";

    public static final String METHOD_ConvUIToServiceFlowCondGroup = "convUIToServiceFlowCondGroup";

    public static final String METHOD_ConvRootDocToFieldUI = "convRootDocToFieldUI";

    @Autowired
    protected ServiceFlowModelManager serviceFlowModelManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected StandardLogicOperatorProxy standardLogicOperatorProxy;

    public static final String META_RESOURCE = "ServiceFlowCondGroupMeta";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ServiceFlowModel.NODENAME,
                        request.getUuid(), ServiceFlowCondGroup.NODENAME, serviceFlowModelManager);

        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<ServiceFlowModel>) serviceFlowModel -> {
            // How to get the base page header model list
            List<PageHeaderModel> pageHeaderModelList =
                    docPageHeaderModelProxy.getDocPageHeaderModelList(serviceFlowModel, null);
            return pageHeaderModelList;
        });

        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<ServiceFlowCondGroup>) (serviceFlowCondGroup, pageHeaderModel) -> {
            pageHeaderModel.setHeaderName(serviceFlowCondGroup.getId());
            return pageHeaderModel;
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public Map<Integer, String> initLogicOperatorMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardLogicOperatorProxy.getLogicOperatorMap(languageCode);
    }

    public Map<Integer, String> initLogicOperatorExpressMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardLogicOperatorProxy.getLogicOperatorExpressMap(languageCode);
    }

    public ServiceFlowCondGroupServiceModel initServiceFlowCondGroup(ServiceFlowModel serviceFlowModel) throws ServiceEntityConfigureException {
        ServiceFlowCondGroup serviceFlowCondGroup =
                (ServiceFlowCondGroup) serviceFlowModelManager.newEntityNode(serviceFlowModel, ServiceFlowCondGroup.NODENAME);
        ServiceFlowCondGroupServiceModel serviceFlowCondGroupServiceModel = new ServiceFlowCondGroupServiceModel();
        serviceFlowCondGroupServiceModel.setServiceFlowCondGroup(serviceFlowCondGroup);
        return serviceFlowCondGroupServiceModel;
    }

    public String getDefGroupMetaValue(String lanCode, String fieldName) throws IOException {
        String path = ServiceFlowCondGroupUIModel.class.getResource("").getPath();
        Map<String, String> tempMessageMap = ServiceDropdownListHelper
                .getStrStaticDropDownMap(path + META_RESOURCE, lanCode);
        return tempMessageMap.get(fieldName);
    }

    public void convServiceFlowCondGroupToUI(ServiceFlowCondGroup serviceFlowCondGroup, ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel) {
        convServiceFlowCondGroupToUI(serviceFlowCondGroup, serviceFlowCondGroupUIModel, null);
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convServiceFlowCondGroupToUI(ServiceFlowCondGroup serviceFlowCondGroup,
                                             ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel, LogonInfo logonInfo) {
        if (serviceFlowCondGroup != null) {
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroup.getUuid())) {
                serviceFlowCondGroupUIModel.setUuid(serviceFlowCondGroup.getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroup.getParentNodeUUID())) {
                serviceFlowCondGroupUIModel.setParentNodeUUID(serviceFlowCondGroup.getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroup.getRootNodeUUID())) {
                serviceFlowCondGroupUIModel.setRootNodeUUID(serviceFlowCondGroup.getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroup.getClient())) {
                serviceFlowCondGroupUIModel.setClient(serviceFlowCondGroup.getClient());
            }
            serviceFlowCondGroupUIModel.setId(serviceFlowCondGroup.getId());
            serviceFlowCondGroupUIModel.setName(serviceFlowCondGroup.getName());
            serviceFlowCondGroupUIModel.setInnerLogicOperator(serviceFlowCondGroup.getInnerLogicOperator());
            serviceFlowCondGroupUIModel.setExternalLogicOperator(serviceFlowCondGroup.getExternalLogicOperator());
            try {
                Map<Integer, String> logicOperatorMap = initLogicOperatorMap(logonInfo.getLanguageCode());
                serviceFlowCondGroupUIModel.setInnerLogicOperatorValue(logicOperatorMap.get(serviceFlowCondGroup.getInnerLogicOperator()));
                serviceFlowCondGroupUIModel.setExternalLogicOperatorValue(logicOperatorMap.get(serviceFlowCondGroup.getExternalLogicOperator()));
            } catch (ServiceEntityInstallationException e) {
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "logicOperator"));
            }
            serviceFlowCondGroupUIModel.setNote(serviceFlowCondGroup.getNote());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:serviceFlowCondGroup
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToServiceFlowCondGroup(ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel,
                                             ServiceFlowCondGroup rawEntity) {
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroupUIModel.getUuid())) {
            rawEntity.setUuid(serviceFlowCondGroupUIModel.getUuid());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroupUIModel.getParentNodeUUID())) {
            rawEntity.setParentNodeUUID(serviceFlowCondGroupUIModel.getParentNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroupUIModel.getRootNodeUUID())) {
            rawEntity.setRootNodeUUID(serviceFlowCondGroupUIModel.getRootNodeUUID());
        }
        if (!ServiceEntityStringHelper.checkNullString(serviceFlowCondGroupUIModel.getClient())) {
            rawEntity.setClient(serviceFlowCondGroupUIModel.getClient());
        }
        rawEntity.setId(serviceFlowCondGroupUIModel.getId());
        rawEntity.setName(serviceFlowCondGroupUIModel.getName());
        rawEntity.setNote(serviceFlowCondGroupUIModel.getNote());
        rawEntity.setExternalLogicOperator(serviceFlowCondGroupUIModel.getExternalLogicOperator());
        rawEntity.setInnerLogicOperator(serviceFlowCondGroupUIModel.getInnerLogicOperator());
    }

    public void convRootDocToFieldUI(ServiceFlowModel serviceFlowModel,
                                     ServiceFlowCondGroupUIModel serviceFlowCondGroupUIModel) {
        if (serviceFlowModel != null) {
            serviceFlowCondGroupUIModel.setRootDocId(serviceFlowModel.getId());
            serviceFlowCondGroupUIModel.setRootDocName(serviceFlowModel.getName());
        }
    }

}
