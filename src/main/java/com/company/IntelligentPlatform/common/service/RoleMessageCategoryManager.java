package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.RoleMessageCategoryUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxyRepository;
import com.company.IntelligentPlatform.common.service.MessageTemplateManager;
import com.company.IntelligentPlatform.common.model.Role;
import com.company.IntelligentPlatform.common.model.RoleMessageCategory;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.MessageTempPrioritySetting;
import com.company.IntelligentPlatform.common.model.MessageTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RoleMessageCategoryManager {

    public static final String METHOD_ConvRoleMessageCategoryToUI = "convRoleMessageCategoryToUI";

    public static final String METHOD_ConvUIToRoleMessageCategory = "convUIToRoleMessageCategory";

    public static final String METHOD_ConvMessageTemplateToUI = "convMessageTemplateToUI";

    @Autowired
    protected RoleManager roleManager;

    @Autowired
    protected MessageTemplateManager messageTemplateManager;

    @Autowired
    protected ServiceSearchProxyRepository serviceSearchProxyRepository;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    protected Logger logger = LoggerFactory.getLogger(RoleMessageCategoryManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), Role.NODENAME,
                        request.getUuid(), RoleMessageCategory.NODENAME, roleManager);
        docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<Role>() {
            @Override
            public List<com.company.IntelligentPlatform.common.controller.PageHeaderModel> execute(Role role) throws ServiceEntityConfigureException {
                // How to get the base page header model list
                List<PageHeaderModel> pageHeaderModelList =
                        roleManager.getPageHeaderModelList(role, client);
                return pageHeaderModelList;
            }
        });
        docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<RoleMessageCategory>() {
            @Override
            public com.company.IntelligentPlatform.common.controller.PageHeaderModel execute(RoleMessageCategory roleMessageCategory, com.company.IntelligentPlatform.common.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
                // How to render current page header
                MessageTemplate messageTemplate = (MessageTemplate) messageTemplateManager
                        .getEntityNodeByKey(roleMessageCategory.getRefUUID(), IServiceEntityNodeFieldConstant.UUID,
                                MessageTemplate.NODENAME, client, null);
                if (messageTemplate != null) {
                    pageHeaderModel.setHeaderName(messageTemplate.getId() + "-" + messageTemplate.getName());
                }
                return pageHeaderModel;
            }
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public void convRoleMessageCategoryToUI(
            RoleMessageCategory roleMessageCategory,
            RoleMessageCategoryUIModel roleMessageCategoryUIModel, LogonInfo logonInfo) {
        if (roleMessageCategory != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(roleMessageCategory, roleMessageCategoryUIModel);
            if (!ServiceEntityStringHelper.checkNullString(roleMessageCategory
                    .getRefUUID())) {
                roleMessageCategoryUIModel.setRefUUID(roleMessageCategory.getRefUUID());
            }
            postConvMessageTemplateToUI(roleMessageCategory.getRefUUID(), roleMessageCategoryUIModel, logonInfo);
        }
    }

    public void convUIToRoleMessageCategory(RoleMessageCategoryUIModel roleMessageCategoryUIModel,
                                            RoleMessageCategory rawEntity) {
        if(roleMessageCategoryUIModel != null && rawEntity != null){
            DocFlowProxy.convUIToServiceEntityNode(roleMessageCategoryUIModel, rawEntity);
            if (!ServiceEntityStringHelper.checkNullString(roleMessageCategoryUIModel
                    .getRefUUID())) {
                rawEntity.setRefUUID(roleMessageCategoryUIModel.getRefUUID());
            }
        }
    }

    public void convMessageTemplateToUI(MessageTemplate messageTemplate,
                                        RoleMessageCategoryUIModel roleMessageCategoryUIModel) {
        convMessageTemplateToUI(messageTemplate, roleMessageCategoryUIModel, null);
    }

    public void convMessageTemplateToUI(MessageTemplate messageTemplate,
                                        RoleMessageCategoryUIModel roleMessageCategoryUIModel, LogonInfo logonInfo) {
        roleMessageCategoryUIModel.setTemplateName(messageTemplate.getName());
        roleMessageCategoryUIModel.setTemplateId(messageTemplate.getId());
        roleMessageCategoryUIModel.setSearchProxyName(messageTemplate.getSearchProxyName());
        roleMessageCategoryUIModel.setSearchModelName(messageTemplate.getSearchModelName());
        roleMessageCategoryUIModel.setNavigationSourceId(messageTemplate.getNavigationSourceId());
        roleMessageCategoryUIModel.setMessageTitle(messageTemplate.getMessageTitle());
        roleMessageCategoryUIModel.setMessageContent(messageTemplate.getMessageContent());
    }

    public void postConvMessageTemplateToUI(String templateUUID,
                                            RoleMessageCategoryUIModel roleMessageCategoryUIModel, LogonInfo logonInfo) {
        try {
            MessageTempPrioritySetting messageTempPrioritySetting =
                    messageTemplateManager.getHighestPrioritySetting(templateUUID, null,
                            logonInfo.getClient());
            if (messageTempPrioritySetting != null) {
                roleMessageCategoryUIModel.setMessageTitle(messageTempPrioritySetting.getMessageTitle());
                roleMessageCategoryUIModel.setMessageContent(messageTempPrioritySetting.getMessageContent());
                roleMessageCategoryUIModel.setMessageLevelCode(messageTempPrioritySetting.getMessageLevelCode());
                Map<Integer, String> messageCodeLevelMap =
                        messageTemplateManager.initMessageLevelCodeMap(logonInfo.getLanguageCode());
                roleMessageCategoryUIModel.setMessageLevelCodeValue(messageCodeLevelMap.get(messageTempPrioritySetting.getMessageLevelCode()));
            }
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "convMessageTemplateToUI"));
        }
    }

    

}
