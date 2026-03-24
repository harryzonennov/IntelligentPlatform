package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.dto.NavigationItemSettingUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.ActionCode;
import com.company.IntelligentPlatform.common.model.AuthorizationObject;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NavigationGroupSetting;
import com.company.IntelligentPlatform.common.model.NavigationItemSetting;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [NavigationSystemSetting]
 *
 * @author Zhang, Hang
 * @date Thu Aug 06 23:43:51 CST 2020
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
public class NavigationItemSettingManager extends ServiceEntityManager {

    public static final String METHOD_ConvNavigationItemSettingToUI = "convNavigationItemSettingToUI";

    public static final String METHOD_ConvUIToNavigationItemSetting = "convUIToNavigationItemSetting";

    public static final String METHOD_ConvParentItemSettingToUI = "convParentItemSettingToUI";

    public static final String METHOD_ConvItemAuthorizationToUI = "convItemAuthorizationToUI";

    public static final String METHOD_ConvDefaultChildItemSettingToUI = "convDefaultChildItemSettingToUI";

    public static final String METHOD_ConvGroupToItemUI = "convGroupToItemUI";

    public static final String METHOD_ConvItemActionCodeToUI = "convItemActionCodeToUI";

    @Autowired
    protected NavigationSystemSettingManager navigationSystemSettingManager;

    @Autowired
    protected NavigationGroupSettingManager navigationGroupSettingManager;

    @Autowired
    protected DocPageHeaderModelProxy docPageHeaderModelProxy;

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected NavigationSystemSettingSpecifier navigationSystemSettingSpecifier;

    @Autowired
    protected NavigationSystemSettingSearchProxy navigationSystemSettingSearchProxy;

    protected Logger logger = LoggerFactory
            .getLogger(NavigationItemSettingManager.class);

    public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
            throws ServiceEntityConfigureException {
        DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
                new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getUuid(), NavigationGroupSetting.NODENAME,
                        request.getUuid(), NavigationItemSetting.NODENAME, navigationSystemSettingManager);
        docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<NavigationItemSetting>) navigationItemSetting -> {
            // How to get the base page header model list
            return getParentPageHeaderModelList(navigationItemSetting, client);
        });
        docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<NavigationItemSetting>) (navigationItemSetting, pageHeaderModel) -> {
            // How to render current page header
            pageHeaderModel.setHeaderName(navigationItemSetting.getName());
            return pageHeaderModel;
        });
        return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
    }

    public List<PageHeaderModel> getParentPageHeaderModelList(
            NavigationItemSetting navigationItemSetting, String client)
            throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<>();
        List<PageHeaderModel> groupHeaderList = navigationGroupSettingManager.getPageHeaderModelList(new SimpleSEJSONRequest(navigationItemSetting.getRootNodeUUID(),
                navigationItemSetting.getParentNodeUUID()), client);
        if (!ServiceCollectionsHelper.checkNullList(groupHeaderList)) {
            resultList.addAll(groupHeaderList);
        }
        if (ServiceEntityStringHelper.checkNullString(navigationItemSetting
                .getParentElementUUID())) {
            return resultList;
        } else {
            NavigationItemSetting parentNavigationItemSetting = (NavigationItemSetting) navigationSystemSettingManager
                    .getEntityNodeByUUID(
                            navigationItemSetting.getParentElementUUID(),
                            NavigationItemSetting.NODENAME, client);
            if (parentNavigationItemSetting == null) {
                return resultList;
            } else {
                int index = groupHeaderList.size();
                PageHeaderModel parentHeader = getParentItemPageHeaderModel(
                        parentNavigationItemSetting, index);
                resultList.add(parentHeader);
                return resultList;
            }
        }
    }

    protected PageHeaderModel getParentItemPageHeaderModel(
            NavigationItemSetting navigationItemSetting, int index) {
        if (navigationItemSetting == null) {
            return null;
        }
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        pageHeaderModel.setPageTitle("parentNavigationItemSettingPageTitle");
        pageHeaderModel.setNodeInstId(NavigationItemSetting.NODENAME);
        pageHeaderModel.setUuid(navigationItemSetting.getUuid());
        pageHeaderModel.setHeaderName(navigationItemSetting.getId());
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    public Map<Integer, String> initDisplayFlagMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardSwitchProxy.getSimpleSwitchMap(languageCode);
    }

    public NavigationItemSetting newNavigationItemFromParent(String baseUUID, String client) throws ServiceEntityConfigureException, DocActionException {
        return navigationSystemSettingSpecifier.newEntityNodeFromBaseUUID(new DocumentContentSpecifier.
                InitSubServiceEntityRequest<>(NavigationGroupSetting.NODENAME, NavigationItemSetting.NODENAME, baseUUID, (parentNode, parentItem, newItem) -> {
            newItem.setDisplayIndex(calculateDefDisplayIndex((NavigationGroupSetting) parentNode, parentItem));
            if (parentItem != null) {
                newItem.setParentElementUUID(parentItem.getUuid());
                int parentLayer = parentItem.getLayer() == 0 ? 1 : parentItem.getLayer();
                newItem.setLayer(parentLayer + 1);
            }
            return newItem;
        }), client);
    }


    /**
     * Get all item list could be used as parent item in one special navigation system
     *
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllTopItemList(String rootNodeUUID,
                                                     String client) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> allItemList = navigationSystemSettingManager
                .getEntityNodeListByKey(rootNodeUUID,
                        IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        NavigationItemSetting.NODENAME, client, null);
        if (ServiceCollectionsHelper.checkNullList(allItemList)) {
            return new ArrayList<>();
        }
        List<ServiceEntityNode> resultList = new ArrayList<>();
        for (ServiceEntityNode seNode : allItemList) {
            NavigationItemSetting navigationItemSetting = (NavigationItemSetting) seNode;
            if (ServiceEntityStringHelper.checkNullString(navigationItemSetting.getParentElementUUID())) {
                // In case this item doesn't has parent
                resultList.add(navigationItemSetting);
            }
        }
        return resultList;
    }

    /**
     * Get all item list could be used as parent item in one special navigation system
     *
     * @param client
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> getAllChildItemList(String parentItemUUID,
                                                       String client) throws ServiceEntityConfigureException {
        return navigationSystemSettingManager
                .getEntityNodeListByKey(parentItemUUID,
                        NavigationItemSetting.FIELD_PARENT_ELEMENT_UUID,
                        NavigationItemSetting.NODENAME, client, null);
    }

    public int calculateDefDisplayIndex(NavigationGroupSetting parentNode, NavigationItemSetting parentItem) throws ServiceEntityConfigureException {
        if (parentItem == null) {
            // In case no parent navigation item
            List<ServiceEntityNode> backNavItemSettingList = navigationSystemSettingManager
                    .getEntityNodeListByKey(parentNode.getUuid(),
                            IServiceEntityNodeFieldConstant.PARENTNODEUUID,
                            NavigationItemSetting.NODENAME, parentNode.getClient(), null);
            return calculateDefDisplayIndex(backNavItemSettingList);
        } else {
            // In case has parent navigation item
            List<ServiceEntityNode> backNavItemSettingList = navigationSystemSettingManager
                    .getEntityNodeListByKey(parentItem.getUuid(),
                            NavigationItemSetting.FIELD_PARENT_ELEMENT_UUID,
                            NavigationItemSetting.NODENAME, parentNode.getClient(), null);
            return calculateDefDisplayIndex(backNavItemSettingList);
        }
    }

    /**
     * Core Logic to calculate the max displayIndex
     *
     * @param backNavItemSettingList
     * @return
     */
    public static int calculateDefDisplayIndex(
            List<ServiceEntityNode> backNavItemSettingList) {
        int displayIndex = 1;
        int step = 1;
        if (ServiceCollectionsHelper.checkNullList(backNavItemSettingList)) {
            return displayIndex;
        }
        for (ServiceEntityNode seNode : backNavItemSettingList) {
            NavigationItemSetting navigationItemSetting = (NavigationItemSetting) seNode;
            if (navigationItemSetting.getDisplayIndex() > displayIndex) {
                displayIndex = navigationItemSetting.getDisplayIndex();
            }
        }
        displayIndex = displayIndex + step;
        return displayIndex;
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convNavigationItemSettingToUI(
            NavigationItemSetting navigationItemSetting,
            NavigationItemSettingUIModel navigationItemSettingUIModel,
            LogonInfo logonInfo) {
        if (navigationItemSetting != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(navigationItemSetting, navigationItemSettingUIModel);
            navigationItemSettingUIModel.setTargetUrl(navigationItemSetting
                    .getTargetUrl());
            navigationItemSettingUIModel
                    .setParentElementUUID(navigationItemSetting
                            .getParentElementUUID());
            navigationItemSettingUIModel.setResOrgUUID(navigationItemSetting
                    .getResOrgUUID());
            navigationItemSettingUIModel.setLastUpdateBy(navigationItemSetting
                    .getLastUpdateBy());
            navigationItemSettingUIModel.setDisplayIndex(navigationItemSetting
                    .getDisplayIndex());
            navigationItemSettingUIModel
                    .setRefDefItemUUID(navigationItemSetting
                            .getRefDefItemUUID());
            navigationItemSettingUIModel.setDisplayFlag(navigationItemSetting
                    .getDisplayFlag());
            if (logonInfo != null) {
                try {
                    Map<Integer, String> displayFlagMap = this
                            .initDisplayFlagMap(logonInfo.getLanguageCode());
                    navigationItemSettingUIModel
                            .setDisplayFlagValue(displayFlagMap
                                    .get(navigationItemSetting.getDisplayFlag()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper
                            .genDefaultErrorMessage(e, "displayFlag"));
                }
            }
            navigationItemSettingUIModel
                    .setRefSimAuthorObjectUUID(navigationItemSetting
                            .getRefSimAuthorObjectUUID());
            navigationItemSettingUIModel.setElementIcon(navigationItemSetting
                    .getElementIcon());
            navigationItemSettingUIModel
                    .setResEmployeeUUID(navigationItemSetting
                            .getResEmployeeUUID());
            navigationItemSettingUIModel.setKeywords(navigationItemSetting
                    .getKeywords());
            navigationItemSettingUIModel.setLayer(navigationItemSetting
                    .getLayer());
            navigationItemSettingUIModel
                    .setRefAuthorActionCodeUUID(navigationItemSetting
                            .getRefAuthorActionCodeUUID());
            navigationItemSettingUIModel.setRefSourceUUID(navigationItemSetting
                    .getRefSourceUUID());
        }
    }

    /**
     * [Internal method] Convert from UI model to se model:navigationItemSetting
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convUIToNavigationItemSetting(
            NavigationItemSettingUIModel navigationItemSettingUIModel,
            NavigationItemSetting rawEntity) {
        DocFlowProxy.convUIToServiceEntityNode(navigationItemSettingUIModel, rawEntity);
        rawEntity.setTargetUrl(navigationItemSettingUIModel.getTargetUrl());
        rawEntity.setParentElementUUID(navigationItemSettingUIModel
                .getParentElementUUID());
        rawEntity.setResOrgUUID(navigationItemSettingUIModel.getResOrgUUID());
        rawEntity.setDisplayIndex(navigationItemSettingUIModel
                .getDisplayIndex());
        rawEntity.setRefDefItemUUID(navigationItemSettingUIModel
                .getRefDefItemUUID());
        rawEntity.setDisplayFlag(navigationItemSettingUIModel.getDisplayFlag());
        rawEntity.setRefSimAuthorObjectUUID(navigationItemSettingUIModel
                .getRefSimAuthorObjectUUID());
        rawEntity.setElementIcon(navigationItemSettingUIModel.getElementIcon());
        rawEntity.setResEmployeeUUID(navigationItemSettingUIModel
                .getResEmployeeUUID());
        rawEntity.setKeywords(navigationItemSettingUIModel.getKeywords());
        rawEntity.setLayer(navigationItemSettingUIModel.getLayer());
        rawEntity.setRefAuthorActionCodeUUID(navigationItemSettingUIModel
                .getRefAuthorActionCodeUUID());
        rawEntity.setRefSourceUUID(navigationItemSettingUIModel
                .getRefSourceUUID());
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemAuthorizationToUI(
            AuthorizationObject itemAuthorization,
            NavigationItemSettingUIModel navigationItemSettingUIModel) {
        if (itemAuthorization != null) {
            navigationItemSettingUIModel
                    .setRefAuthorizationObjectId(itemAuthorization.getId());
            navigationItemSettingUIModel
                    .setRefAuthorizationObjectName(itemAuthorization.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convParentItemSettingToUI(
            NavigationItemSetting parentItemSetting,
            NavigationItemSettingUIModel navigationItemSettingUIModel) {
        if (parentItemSetting != null) {
            navigationItemSettingUIModel
                    .setParentItemSettingId(parentItemSetting.getId());
            navigationItemSettingUIModel
                    .setParentItemSettingName(parentItemSetting.getName());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convItemActionCodeToUI(ActionCode itemActionCode,
                                       NavigationItemSettingUIModel navigationItemSettingUIModel) {
        if (itemActionCode != null) {
            if (!ServiceEntityStringHelper.checkNullString(itemActionCode
                    .getClient())) {
                navigationItemSettingUIModel.setClient(itemActionCode
                        .getClient());
            }
            navigationItemSettingUIModel.setRefActionCodeName(itemActionCode
                    .getName());
            navigationItemSettingUIModel.setRefActionCodeId(itemActionCode
                    .getId());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convDefaultChildItemSettingToUI(
            NavigationItemSetting defaultChildItemSetting,
            NavigationItemSettingUIModel navigationItemSettingUIModel) {
        if (defaultChildItemSetting != null) {
            navigationItemSettingUIModel
                    .setDefaultChildItemSettingName(defaultChildItemSetting
                            .getName());
            navigationItemSettingUIModel
                    .setDefaultChildItemSettingId(defaultChildItemSetting
                            .getId());
        }
    }

    public void convGroupToItemUI(
            NavigationGroupSetting navigationGroupSetting,
            NavigationItemSettingUIModel navigationItemSettingUIModel) {
        if (navigationGroupSetting != null) {
            navigationItemSettingUIModel
                    .setRefGroupName(navigationGroupSetting.getName());
            navigationItemSettingUIModel
                    .setRefGroupId(navigationGroupSetting.getId());
        }
    }

    public NavigationSystemSettingSearchProxy getSearchProxy() {
        return navigationSystemSettingSearchProxy;
    }

}
