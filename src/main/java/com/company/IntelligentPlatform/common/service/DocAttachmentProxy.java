package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.dto.DocAttachmentNodeUIModel;
import com.company.IntelligentPlatform.common.service.ServiceAttachmentException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.AttachmentConstantHelper;
import com.company.IntelligentPlatform.common.model.FileAttachmentTextRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.DocAttachmentNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
public class DocAttachmentProxy {

    public static final String METHOD_ConvAttachmentToUI = "convAttachmentToUI";

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    public Logger logger = LoggerFactory.getLogger(DocAttachmentProxy.class);

    public static class DocAttachmentProcessPara {

        protected ServiceEntityManager serviceEntityManager;

        protected String attachmentNodeName;

        protected String parentNodeName;

        protected String logonUserUUID;

        protected String organizationUUID;

        protected HttpServletRequest request;

        public DocAttachmentProcessPara() {
        }

        public DocAttachmentProcessPara(ServiceEntityManager serviceEntityManager, String attachmentNodeName,
                                        String parentNodeName, String logonUserUUID, String organizationUUID,
                                        HttpServletRequest request) {
            this.serviceEntityManager = serviceEntityManager;
            this.attachmentNodeName = attachmentNodeName;
            this.parentNodeName = parentNodeName;
            this.logonUserUUID = logonUserUUID;
            this.organizationUUID = organizationUUID;
            this.request = request;
        }

        public ServiceEntityManager getServiceEntityManager() {
            return serviceEntityManager;
        }

        public void setServiceEntityManager(ServiceEntityManager serviceEntityManager) {
            this.serviceEntityManager = serviceEntityManager;
        }

        public String getAttachmentNodeName() {
            return attachmentNodeName;
        }

        public void setAttachmentNodeName(String attachmentNodeName) {
            this.attachmentNodeName = attachmentNodeName;
        }

        public String getParentNodeName() {
            return parentNodeName;
        }

        public void setParentNodeName(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        public String getLogonUserUUID() {
            return logonUserUUID;
        }

        public void setLogonUserUUID(String logonUserUUID) {
            this.logonUserUUID = logonUserUUID;
        }

        public String getOrganizationUUID() {
            return organizationUUID;
        }

        public void setOrganizationUUID(String organizationUUID) {
            this.organizationUUID = organizationUUID;
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public void setRequest(HttpServletRequest request) {
            this.request = request;
        }
    }

    public static class DocAttchNodeInputPara {

        protected String seName;

        protected String nodeName;

        protected String nodeInstId;

        public DocAttchNodeInputPara() {
        }

        public DocAttchNodeInputPara(String seName, String nodeName, String nodeInstId) {
            this.seName = seName;
            this.nodeName = nodeName;
            this.nodeInstId = nodeInstId;
        }

        public String getSeName() {
            return seName;
        }

        public void setSeName(String seName) {
            this.seName = seName;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getNodeInstId() {
            return nodeInstId;
        }

        public void setNodeInstId(String nodeInstId) {
            this.nodeInstId = nodeInstId;
        }

    }

    public ServiceUIModelExtension genDefServiceUIModelExtension(DocAttchNodeInputPara docAttchNodeInputPara) {
        ServiceUIModelExtension serviceUIModelExtension = new ServiceUIModelExtension();
        serviceUIModelExtension.setChildUIModelExtensions(null);
        List<ServiceUIModelExtensionUnion> uiModelExtensionUnionList = new ArrayList<>();
        serviceUIModelExtension.setUIModelExtensionUnion(genDefUIModelExtensionUnion(docAttchNodeInputPara));
        return serviceUIModelExtension;
    }

    public List<ServiceUIModelExtensionUnion> genDefUIModelExtensionUnion(DocAttchNodeInputPara docAttchNodeInputPara) {
        List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
        List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
        ServiceUIModelExtensionUnion docAttachmentNodeExtensionUnion = new ServiceUIModelExtensionUnion();
        docAttachmentNodeExtensionUnion
                .setNodeInstId(docAttchNodeInputPara.getNodeInstId());
        docAttachmentNodeExtensionUnion
                .setNodeName(docAttchNodeInputPara.getNodeName());

        // UI Model Configure of node:[Attachment Node]
        UIModelNodeMapConfigure docAttachmentNodeMap = new UIModelNodeMapConfigure();
        docAttachmentNodeMap
                .setSeName(docAttchNodeInputPara.getSeName());
        docAttachmentNodeMap
                .setNodeName(docAttchNodeInputPara.getNodeName());
        docAttachmentNodeMap
                .setNodeInstID(docAttchNodeInputPara.getNodeInstId());
        docAttachmentNodeMap.setHostNodeFlag(true);
        Class<?>[] docAttachmentConvToUIParas = {
                DocAttachmentNode.class,
                DocAttachmentNodeUIModel.class};
        docAttachmentNodeMap
                .setConvToUIMethodParas(docAttachmentConvToUIParas);
        docAttachmentNodeMap.setLogicManager(this);
        docAttachmentNodeMap
                .setConvToUIMethod(METHOD_ConvAttachmentToUI);
        uiModelNodeMapList.add(docAttachmentNodeMap);
        docAttachmentNodeExtensionUnion
                .setUiModelNodeMapList(uiModelNodeMapList);
        resultList.add(docAttachmentNodeExtensionUnion);
        return resultList;
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convAttachmentToUI(
            DocAttachmentNode docAttachmentNode,
            DocAttachmentNodeUIModel docAttachmentNodeUIModel) {
        if (docAttachmentNode != null) {
            if (!ServiceEntityStringHelper
                    .checkNullString(docAttachmentNode
                            .getUuid())) {
                docAttachmentNodeUIModel
                        .setUuid(docAttachmentNode.getUuid());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(docAttachmentNode
                            .getParentNodeUUID())) {
                docAttachmentNodeUIModel
                        .setParentNodeUUID(docAttachmentNode
                                .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(docAttachmentNode
                            .getRootNodeUUID())) {
                docAttachmentNodeUIModel
                        .setRootNodeUUID(docAttachmentNode
                                .getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper
                    .checkNullString(docAttachmentNode
                            .getClient())) {
                docAttachmentNodeUIModel
                        .setClient(docAttachmentNode
                                .getClient());
            }
            docAttachmentNodeUIModel
                    .setAttachmentDescription(docAttachmentNode
                            .getNote());
            docAttachmentNodeUIModel
                    .setAttachmentTitle(docAttachmentNode
                            .getName());

            docAttachmentNodeUIModel
                    .setFileType(docAttachmentNode
                            .getFileType());

        }
    }

    public void uploadAttachmentUnionCore(ServiceEntityNode parentNode, String uuid, String attachmentNodeName,
                                          ServiceEntityManager serviceEntityManager,
                                          MultipartFile multipartFile, String logonUserUUID, String organizationUUID) throws ServiceEntityConfigureException {
        try {
            byte[] bytes = multipartFile.getBytes();
            String fileType = AttachmentConstantHelper
                    .getFileTypeFromPostFix(multipartFile);
            DocAttachmentNode docAttachmentNode = (DocAttachmentNode) serviceEntityManager
                    .getEntityNodeByKey(uuid,
                            IServiceEntityNodeFieldConstant.UUID,
                            attachmentNodeName,
                            null);
            if (docAttachmentNode == null) {
                docAttachmentNode = (DocAttachmentNode) serviceEntityManager
                        .newEntityNode(parentNode,
                                attachmentNodeName);
            }
            if (ServiceEntityStringHelper
                    .checkNullString(docAttachmentNode
                            .getName())) {
                docAttachmentNode.setName(multipartFile
                        .getOriginalFilename());
            }
            docAttachmentNode.setContent(bytes);
            docAttachmentNode.setFileType(fileType);
            docAttachmentNode.setId(multipartFile
                    .getOriginalFilename());
            serviceEntityManager.updateSENode(
                    docAttachmentNode,
                    logonUserUUID, organizationUUID);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadDefAttachment(DocAttachmentProcessPara docAttachmentProcessPara) {
        try {
            HttpServletRequest request = docAttachmentProcessPara.getRequest();
            request.setCharacterEncoding("UTF-8");
            MultipartHttpServletRequest muti = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> map = muti.getMultiFileMap();
            for (Map.Entry<String, List<MultipartFile>> entry : map.entrySet()) {
                List<MultipartFile> list = entry.getValue();
                for (MultipartFile multipartFile : list) {
                    byte[] bytes = multipartFile.getBytes();
                    String fileType = AttachmentConstantHelper
                            .getFileTypeFromPostFix(multipartFile);
                    String uuid = request
                            .getParameter(SimpleSEJSONRequest.UUID);
                    String baseUUID = request
                            .getParameter(SimpleSEJSONRequest.BASEUUID);
                    ServiceEntityNode parentNode = docAttachmentProcessPara.getServiceEntityManager()
                            .getEntityNodeByKey(baseUUID,
                                    IServiceEntityNodeFieldConstant.UUID,
                                    docAttachmentProcessPara.getParentNodeName(), null);
                    uploadAttachmentUnionCore(parentNode, uuid, docAttachmentProcessPara.getAttachmentNodeName(),
                            docAttachmentProcessPara.getServiceEntityManager(), multipartFile,
                            docAttachmentProcessPara.getLogonUserUUID(),
                            docAttachmentProcessPara.getOrganizationUUID());
                }
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException | IOException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String deleteAttachmentCore(DocAttachmentProcessPara docAttachmentProcessPara, String uuid) {
        try {
            DocAttachmentNode docAttachmentNode = (DocAttachmentNode) docAttachmentProcessPara.getServiceEntityManager()
                    .getEntityNodeByKey(uuid,
                            IServiceEntityNodeFieldConstant.UUID,
                            docAttachmentProcessPara.getAttachmentNodeName(),
                            null);
            if (docAttachmentNode != null) {
                docAttachmentProcessPara.getServiceEntityManager().deleteSENode(
                        docAttachmentNode, docAttachmentProcessPara.getLogonUserUUID(),
                        docAttachmentProcessPara.getOrganizationUUID());
            }
            return ServiceJSONParser.genSimpleOKResponse();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public String uploadAttachmentText(DocAttachmentProcessPara docAttachmentProcessPara,
            FileAttachmentTextRequest request) {
        try {
            ServiceEntityNode parentNode = docAttachmentProcessPara.getServiceEntityManager()
                    .getEntityNodeByKey(request.getBaseUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            docAttachmentProcessPara.getParentNodeName(), null);
            DocAttachmentNode docAttachmentNode = (DocAttachmentNode) docAttachmentProcessPara.getServiceEntityManager()
                    .getEntityNodeByKey(request.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID,
                            docAttachmentProcessPara.getAttachmentNodeName(),
                            null);
            if (docAttachmentNode == null) {
                docAttachmentNode = (DocAttachmentNode) docAttachmentProcessPara.getServiceEntityManager()
                        .newEntityNode(parentNode,
                                docAttachmentProcessPara.getAttachmentNodeName());
            }
            docAttachmentNode.setName(request.getAttachmentTitle());
            docAttachmentNode.setNote(request
                    .getAttachmentDescription());
            String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
            docAttachmentProcessPara.getServiceEntityManager().updateSENode(docAttachmentNode,
                    docAttachmentProcessPara.getLogonUserUUID(), docAttachmentProcessPara.getLogonUserUUID());
            return ServiceJSONParser
                    .genDefOKJSONObject(docAttachmentNode);
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    public ResponseEntity<byte[]> loadAttachment(DocAttachmentProcessPara docAttachmentProcessPara, String uuid) {
        final HttpHeaders headers = new HttpHeaders();
        try {
            DocAttachmentNode docAttachmentNode = (DocAttachmentNode) docAttachmentProcessPara.getServiceEntityManager()
                    .getEntityNodeByKey(uuid,
                            IServiceEntityNodeFieldConstant.UUID,
                            docAttachmentProcessPara.getAttachmentNodeName(),
                            null);
            if (docAttachmentNode != null) {
                docAttachmentProcessPara.getServiceEntityManager().setAttachmentHttpHeaders(headers,
                        docAttachmentNode.getFileType(),
                        docAttachmentNode.getName());
                return new ResponseEntity<byte[]>(
                        docAttachmentNode.getContent(), headers,
                        HttpStatus.CREATED);
            }
        } catch (ServiceEntityConfigureException e) {
            throw new ServiceAttachmentException(ServiceAttachmentException.PARA_SYSTEM_WRONG, e.getMessage());
        }
        throw new ServiceAttachmentException(ServiceAttachmentException.TYPE_SYSTEM_WRONG);
    }

    public List<ServiceEntityNode> copyServiceModuleAttachmentNode(ServiceModule sourceServiceModule,
                                                                   ServiceModule targetServiceModule,
                                                                   ServiceEntityNode parentNode,
                                                                   ServiceEntityManager targetDocManager)
            throws ServiceEntityConfigureException, InstantiationException, IllegalAccessException {
        if(sourceServiceModule == null || targetServiceModule == null){
            return null;
        }
        List<ServiceEntityNode> sourceAttchmentList = getAttachmentListFromServiceModel(sourceServiceModule);
        if(ServiceCollectionsHelper.checkNullList(sourceAttchmentList)){
            return null;
        }
        List<Field> resultFieldList = ServiceModuleProxy.getFieldListByDocNodeCategory(targetServiceModule.getClass(),
                IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT);
        if(ServiceCollectionsHelper.checkNullList(resultFieldList)){
            return null;
        }
        /*
         * [Step2] traverse source attachment list and copy to target attachment list
         */
        // There should be only one attachment field in parent service model
        Field targetAttachField = resultFieldList.get(0);
        targetAttachField.setAccessible(true);
        List<ServiceEntityNode> targetAttachmentList = new ArrayList<>();
        for(ServiceEntityNode seNode: sourceAttchmentList){
            DocAttachmentNode sourceAttachmentNode = (DocAttachmentNode) seNode;
            IServiceModuleFieldConfig fieldConfig = targetAttachField.getAnnotation(IServiceModuleFieldConfig.class);
            DocAttachmentNode targetAttachmentNode = (DocAttachmentNode) serviceModuleProxy.newServiceEntityNode(fieldConfig.nodeName(),
                    targetDocManager, parentNode, parentNode.getClient());
            copyAttachmentNode(sourceAttachmentNode, targetAttachmentNode);
            targetAttachmentList.add(targetAttachmentNode);
        }
        /*
         * [Step3] set attachment list to attr from target service model
         */
        targetAttachField.set(targetServiceModule, targetAttachmentList);
        return targetAttachmentList;
    }

    public void copyAttachmentNode(DocAttachmentNode sourceAttachmentNode, DocAttachmentNode targetAttachmentNode){
        if( targetAttachmentNode != null && sourceAttachmentNode != null){
            targetAttachmentNode.setContent(sourceAttachmentNode.getContent());
            targetAttachmentNode.setFileType(sourceAttachmentNode.getFileType());
        }
    }

    public List<ServiceEntityNode> getAttachmentListFromServiceModel(ServiceModule parentServiceModule){
        List<ServiceEntityNode> attchmentList = serviceModuleProxy.getNodeValueByDocNodeCategory(parentServiceModule,
                IServiceModuleFieldConfig.DOCNODE_CATE_ATTACHMENT);
        return attchmentList;
    }

}
