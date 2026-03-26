package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocPageHeaderModelProxy {

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    protected Logger logger = LoggerFactory.getLogger(DocPageHeaderModelProxy.class);

    /**
     * Logic how to get base header model list
     */
    public interface GenModelList {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        List<PageHeaderModel> execute(SimpleSEJSONRequest request, String client) throws ServiceEntityConfigureException;
    }

    /**
     * Logic how to get base header model list
     *
     * @param <S>
     */
    public interface GenBaseModelList<S extends ServiceEntityNode> {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        List<PageHeaderModel> execute(S s) throws ServiceEntityConfigureException;
    }

    /**
     * Logic how to get current header model from service entity node
     *
     * @param <S>
     */
    public interface GenHomePageModel<S extends ServiceEntityNode> {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        PageHeaderModel execute(S s, PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException;
    }

    public static class DocPageHeaderInputPara<HOMException extends Exception, BaseException extends Exception> {

        protected String baseUUID;

        protected String baseNodeName;

        protected String uuid;

        protected String nodeName;

        protected ServiceEntityManager serviceEntityManager;

        protected GenHomePageModel genHomePageModel;

        protected GenBaseModelList genBaseModelList;

        public DocPageHeaderInputPara() {
        }

        public DocPageHeaderInputPara(String baseUUID, String baseNodeName, String uuid, String nodeName,
                                      ServiceEntityManager serviceEntityManager) {
            this.baseUUID = baseUUID;
            this.baseNodeName = baseNodeName;
            this.uuid = uuid;
            this.nodeName = nodeName;
            this.serviceEntityManager = serviceEntityManager;
        }

        public String getBaseUUID() {
            return baseUUID;
        }

        public void setBaseUUID(String baseUUID) {
            this.baseUUID = baseUUID;
        }

        public String getBaseNodeName() {
            return baseNodeName;
        }

        public void setBaseNodeName(String baseNodeName) {
            this.baseNodeName = baseNodeName;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public GenHomePageModel getGenHomePageModel() {
            return genHomePageModel;
        }

        public void setGenHomePageModel(GenHomePageModel genHomePageModel) {
            this.genHomePageModel = genHomePageModel;
        }

        public GenBaseModelList getGenBaseModelList() {
            return genBaseModelList;
        }

        public void setGenBaseModelList(GenBaseModelList genBaseModelList) {
            this.genBaseModelList = genBaseModelList;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }
    }

    public <T extends Exception> List<PageHeaderModel> getPageHeaderModelList(DocPageHeaderInputPara docPageHeaderInputPara,
                                                                              String client)
            throws ServiceEntityConfigureException {
        ServiceEntityNode serviceEntityNode = docPageHeaderInputPara.getServiceEntityManager()
                .getEntityNodeByKey(docPageHeaderInputPara.getUuid(), IServiceEntityNodeFieldConstant.UUID,
                        docPageHeaderInputPara.getNodeName(), client, null);
        ServiceEntityNode baseNode = docPageHeaderInputPara.getServiceEntityManager()
                .getEntityNodeByKey(docPageHeaderInputPara.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID,
                        docPageHeaderInputPara.getBaseNodeName(), client, null);
        List<PageHeaderModel> resultList = new ArrayList<>();
        List<PageHeaderModel> baseHeaderModelList = docPageHeaderInputPara.getGenBaseModelList().execute(baseNode);
        int index = 0;
        if (!ServiceCollectionsHelper.checkNullList(baseHeaderModelList)) {
            resultList.addAll(baseHeaderModelList);
            index = baseHeaderModelList.size();
        }
        PageHeaderModel curHeaderModel = getDefPageHeaderModel(docPageHeaderInputPara, serviceEntityNode, index);
        if (curHeaderModel != null) {
            resultList.add(curHeaderModel);
        }
        return resultList;
    }

    protected PageHeaderModel getDefPageHeaderModel(DocPageHeaderInputPara docPageHeaderInputPara,
                                                    ServiceEntityNode serviceEntityNode, int index)
            throws ServiceEntityConfigureException {
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        String nodeInstId = docPageHeaderInputPara.getNodeName();
        if (serviceEntityNode != null) {
            nodeInstId = ServiceDocumentComProxy.getServiceEntityModelName(serviceEntityNode);
        }
        pageHeaderModel.setNodeInstId(ServiceEntityStringHelper.headerToLowerCase(nodeInstId));
        pageHeaderModel.setPageTitle(ServiceEntityStringHelper.headerToLowerCase(ServiceEntityStringHelper.headerToLowerCase(nodeInstId) + "Title"));
        pageHeaderModel.setUuid(docPageHeaderInputPara.getUuid());
        if (serviceEntityNode != null) {
            docPageHeaderInputPara.getGenHomePageModel().execute(serviceEntityNode, pageHeaderModel);
        }
        pageHeaderModel.setIndex(index);
        return pageHeaderModel;
    }

    /**
     * Generate default page header Model list
     *
     * @param documentContent
     * @return
     * @throws ServiceEntityConfigureException
     */
    public List<PageHeaderModel> getDocPageHeaderModelList(
            ServiceEntityNode documentContent, String fieldName)
            throws ServiceEntityConfigureException {
        List<PageHeaderModel> resultList = new ArrayList<>();
        if (documentContent == null) {
            return null;
        }
        int index = 0;
        PageHeaderModel pageHeaderModel = new PageHeaderModel();
        String nodeInstId = ServiceDocumentComProxy.getServiceEntityModelName(documentContent);
        pageHeaderModel.setNodeInstId(ServiceEntityStringHelper.headerToLowerCase(nodeInstId));
        pageHeaderModel.setPageTitle(ServiceEntityStringHelper.headerToLowerCase(nodeInstId) + "Title");
        pageHeaderModel.setUuid(documentContent.getUuid());
        if (!ServiceEntityStringHelper.checkNullString(fieldName)) {
            if (IServiceEntityNodeFieldConstant.NAME.equals(fieldName)) {
                pageHeaderModel.setHeaderName(documentContent.getName());
            }
            if (IServiceEntityNodeFieldConstant.ID.equals(fieldName)) {
                pageHeaderModel.setHeaderName(documentContent.getId());
            }
        } else {
            pageHeaderModel.setHeaderName(documentContent.getId());
        }
        pageHeaderModel.setIndex(index);
        resultList.add(pageHeaderModel);
        return resultList;
    }

    public PageHeaderModel getDefDocMaterialItemPageHeaderModel(DocMatItemNode matItemNode, PageHeaderModel pageHeaderModel){
        // How to render current page header
        MaterialStockKeepUnit materialStockKeepUnit = null;
        try {
            materialStockKeepUnit = materialStockKeepUnitManager
                    .getMaterialSKUWrapper(
                            matItemNode.getRefMaterialSKUUUID(),
                            matItemNode.getClient(), null);
        } catch (ServiceComExecuteException | ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
        }
        if (materialStockKeepUnit != null) {
            pageHeaderModel.setHeaderName(materialStockKeepUnit.getId());
        } else {
            pageHeaderModel.setHeaderName(matItemNode.getId());
        }
        return pageHeaderModel;
    }

    /**
     * Utility method with default logic to convert se node into request.
     *
     * @param serviceEntityNode
     * @return
     */
    public static SimpleSEJSONRequest getDefRequest(ServiceEntityNode serviceEntityNode) {
        SimpleSEJSONRequest request = new SimpleSEJSONRequest();
        request.setBaseUUID(serviceEntityNode.getParentNodeUUID());
        request.setUuid(serviceEntityNode.getUuid());
        request.setClient(serviceEntityNode.getClient());
        request.setId(serviceEntityNode.getId());
        request.setNodeName(serviceEntityNode.getNodeName());
        return request;
    }

}
