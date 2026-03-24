package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocActionExecutorCase;
import com.company.IntelligentPlatform.common.service.DocMatItemToParentExecutorCase;
import com.company.IntelligentPlatform.common.service.SelectItemToParentExecutorCase;
import com.company.IntelligentPlatform.common.service.ServiceCrossActionHandler;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.SystemDefDocActionCodeProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingServiceModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocConfigure;
import com.company.IntelligentPlatform.common.model.ServiceCrossDocEventMonitor;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Standard Document Executable Action Proxy
 */
@Service
public abstract class DocActionExecutionProxy<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode> {

    @Autowired
    protected SystemDefDocActionCodeProxy systemDefDocActionCodeProxy;

    @Autowired
    protected DocActionNodeProxy docActionNodeProxy;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ServiceCrossActionHandler serviceCrossActionHandler;

    @Autowired
    protected SelectItemToParentExecutorCase<R, T, Item> selectItemToParentExecutorCase;

    @Autowired
    protected DocMatItemToParentExecutorCase<R, T, Item> docMatItemToParentExecutorCase;

    @Autowired
    protected ServiceDocumentSettingManager serviceDocumentSettingManager;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected CrossDocBatchConvertProxy crossDocBatchConvertProxy;

    @Autowired
    protected CrossDocBatchConvertReservedProxy crossDocBatchConvertReservedProxy;

    @Autowired
    protected CrossDocBatchConvertProfProxy crossDocBatchConvertProfProxy;

    @Autowired
    protected DocSplitMergeProxy<T, Item> docSplitMergeProxy;

    @Autowired
    protected PrevNextDocItemProxy prevNextDocItemProxy;

    protected Map<String, Map<Integer, String>> actionCodeMapLan = new HashMap<>();

    protected Logger logger = LoggerFactory.getLogger(DocActionExecutionProxy.class);

    public abstract List<DocActionConfigure> getDefDocActionConfigureList();

    public abstract List<DocActionConfigure> getCustomDocActionConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException;

    public abstract List<CrossDocActConfigure> getDefCrossDocActConfigureList();

    public abstract List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException;

    public Map<Integer, CrossCopyDocConversionConfig> getCrossCopyDocConversionConfigMap() {
        Map<Integer, CrossCopyDocConversionConfig> customCopyDocConversionConfigMap =
                this.getCustomCrossCopyDocConversionConfigMap();
        if (customCopyDocConversionConfigMap != null && customCopyDocConversionConfigMap.size() > 0) {
            return customCopyDocConversionConfigMap;
        }
        return getDefCrossCopyDocConversionConfigMap();
    }

    /**
     * Provide the source to target doc type map from reserved doc
     *
     * @return
     */
    public Map<Integer, SourceTargetDocTypeConfig> getDefReservedSrcTargetDocTypeMap() {
        Map<Integer, SourceTargetDocTypeConfig> sourceTargetDocTypeMapArray = new HashMap<>();
        sourceTargetDocTypeMapArray.put(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
                new SourceTargetDocTypeConfig(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM));
        sourceTargetDocTypeMapArray.put(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM,
                new SourceTargetDocTypeConfig(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY));
        return sourceTargetDocTypeMapArray;
    }

    public int getDefSourceDocTypeReserved(int targetDocType) {
        Map<Integer, SourceTargetDocTypeConfig> reservedSrcTargetMap = getDefReservedSrcTargetDocTypeMap();
        SourceTargetDocTypeConfig reservedSrcTargetConfig = reservedSrcTargetMap.get(targetDocType);
        if (reservedSrcTargetConfig != null) {
            return reservedSrcTargetConfig.getSourceDocType();
        }
        return 0;
    }

    public static class SourceTargetDocTypeConfig {

        private int sourceDocType;

        private int targetDocType;

        private int reservedDocType;

        public SourceTargetDocTypeConfig() {
        }

        public SourceTargetDocTypeConfig(int sourceDocType) {
            this.sourceDocType = sourceDocType;
        }

        public SourceTargetDocTypeConfig(int sourceDocType, int reservedDocType) {
            this.sourceDocType = sourceDocType;
            this.reservedDocType = reservedDocType;
        }

        public SourceTargetDocTypeConfig(int sourceDocType, int targetDocType, int reservedDocType) {
            this.sourceDocType = sourceDocType;
            this.targetDocType = targetDocType;
            this.reservedDocType = reservedDocType;
        }

        public int getSourceDocType() {
            return sourceDocType;
        }

        public void setSourceDocType(int sourceDocType) {
            this.sourceDocType = sourceDocType;
        }

        public int getTargetDocType() {
            return targetDocType;
        }

        public void setTargetDocType(int targetDocType) {
            this.targetDocType = targetDocType;
        }

        public int getReservedDocType() {
            return reservedDocType;
        }

        public void setReservedDocType(int reservedDocType) {
            this.reservedDocType = reservedDocType;
        }
    }

    public abstract Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap();

    public abstract Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap();

    public abstract DocumentContentSpecifier<R, T, Item> getDocumentContentSpecifier();

    public abstract DocSplitMergeRequest<T, Item> getDocMergeRequest();

    public abstract CrossDocConvertRequest<R, Item, ?> getCrossDocCovertRequest();

    public CrossDocConvertReservedRequest<R, Item, ?> getCrossDocCovertReservedRequest() {
        return null;
    }

    public CrossDocConvertProfRequest<R, Item, ?> getCrossDocCovertProfRequest() {
        return null;
    }

    @Transactional
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentBatch(ServiceModule sourceServiceModule,
                                                  List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                  DocumentMatItemBatchGenRequest genRequest,
                                                  LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        return crossCreateDocumentBatch(sourceServiceModule, selectedSourceDocMatItemList, genRequest,
                new CrossDocConvertRequest.InputOption(), logonInfo);
    }

    @Transactional
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocReserved(
            ServiceModule reservedServiceModule, List<ServiceEntityNode> selectedSourceDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        return crossCreateBatchDocReserved(reservedServiceModule, selectedSourceDocMatItemList, genRequest,
                new CrossDocConvertRequest.InputOption(), logonInfo);
    }

    @Transactional
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocFromPrevProf(
            ServiceModule prevProfServiceModule, List<ServiceEntityNode> selectedPrevProfDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException {
        return crossCreateBatchDocFromPrevProf(prevProfServiceModule, selectedPrevProfDocMatItemList, genRequest,
                new CrossDocConvertRequest.InputOption(false, true), logonInfo);
    }

    @Transactional
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocToPrevProf(
            ServiceModule sourceServiceModule, List<ServiceEntityNode> selectedSourceDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {
        return crossCreateBatchDocToPrevProf(sourceServiceModule, selectedSourceDocMatItemList, genRequest,
                new CrossDocConvertRequest.InputOption(false, true), logonInfo);
    }

    /**
     * Provide outbound logic from source doc for creating cross document
     *
     * @param sourceServiceModule
     * @param selectedSourceDocMatItemList
     * @param genRequest
     * @param logonInfo
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentBatch(ServiceModule sourceServiceModule,
                                                  List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                  DocumentMatItemBatchGenRequest genRequest,
                                                  CrossDocConvertRequest.InputOption inputOption,
                                                  LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        /*
         * [step1] Execute the batch cross-creation
         */
        List<CrossDocBatchConvertProxy.DocContentCreateContext> docContentCreateContextList = crossCreateDocumentCore(sourceServiceModule,
                selectedSourceDocMatItemList, genRequest, inputOption,
                logonInfo);
        /*
         * [step2]: trigger home action code
         */
        postTriggerSourceAction(genRequest.getTargetDocType(), (R) sourceServiceModule, selectedSourceDocMatItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    /**
     * Provide outbound logic from source doc for creating cross document
     *
     * @param sourceServiceModule
     * @param selectedSourceDocMatItemList
     * @param genRequest
     * @param logonInfo
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateDocumentCore(ServiceModule sourceServiceModule,
                                                                                            List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                                                            DocumentMatItemBatchGenRequest genRequest,
                                                                                            CrossDocConvertRequest.InputOption inputOption,
                                                                                            LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException, ServiceEntityInstallationException {
        /*
         * [step1]: call target handleInboundCrossCreateDoc
         */
        int targetDocType = genRequest.getTargetDocType();
        DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(targetDocType);
        if (targetDocActionProxy == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }
        /*
         * [Step1.5] Pre process service model
         */
        if (sourceServiceModule == null) {
            sourceServiceModule = processEmptyServiceModel(selectedSourceDocMatItemList);
        }
        return targetDocActionProxy.handleInboundCrossCreateDoc(sourceServiceModule,
                this.getDocumentContentSpecifier().getDocumentType(), selectedSourceDocMatItemList, genRequest, inputOption,
                logonInfo);
    }

    /**
     * Provide outbound logic from source doc for creating cross document
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocReserved(
            ServiceModule reservedServiceModule, List<ServiceEntityNode> selectedToReserveDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, CrossDocConvertRequest.InputOption inputOption,
            LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {

        int targetDocType = genRequest.getTargetDocType();
        DocumentContentSpecifier<R, T, Item> reservedDocContentSpecifier = this.getDocumentContentSpecifier();
        DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(targetDocType);
        if (targetDocActionProxy == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }

        if (reservedServiceModule == null) {
            reservedServiceModule = processEmptyServiceModel(selectedToReserveDocMatItemList);
        }
        // [Step 2] Calculate the Source Document Type by the target document type
        int sourceDocType = getDefSourceDocTypeReserved(genRequest.getTargetDocType());
        if (sourceDocType == 0) {
            throw new DocActionException(DocActionException.PARA_WRG_CONFIG);
        }
        // [Step 3] Call target handleInboundCrossCreateDocReserved
        List<CrossDocBatchConvertReservedProxy.DocContentCreateContext> docContentCreateContextList =
                targetDocActionProxy.handleInboundCrossCreateDocReserved(reservedServiceModule,
                        reservedDocContentSpecifier.getDocumentType(), sourceDocType, selectedToReserveDocMatItemList,
                        genRequest, inputOption, logonInfo);
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            // should raise exception when no data created and stop following action
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        /*
         * [Step 4]: trigger source action code
         */
        postTriggerSourceAction(targetDocType, (R) reservedServiceModule, selectedToReserveDocMatItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    /**
     * Provide outbound logic from source doc for creating cross document
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocFromPrevProf(
            ServiceModule prevProfServiceModule,  List<ServiceEntityNode> selectedPrevProfDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, CrossDocConvertRequest.InputOption inputOption,
            LogonInfo logonInfo)
            throws ServiceModuleProxyException, DocActionException{
        /*
         * [step1]: call target handleInboundCrossCreateDoc
         */
        int targetDocType = genRequest.getTargetDocType();
        DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(targetDocType);
        if (targetDocActionProxy == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }
        int prevProfDocType = this.getDocumentContentSpecifier().getDocumentType();
        List<CrossDocBatchConvertReservedProxy.DocContentCreateContext> docContentCreateContextList = null;
        try {
            docContentCreateContextList = targetDocActionProxy.handleInboundCrossCreateDocFromPrevProf(
                    prevProfDocType, prevProfServiceModule, selectedPrevProfDocMatItemList, genRequest, inputOption,
                    logonInfo);
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (SearchConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            // should raise exception when no data created and stop following action
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        /*
         * [step2]: trigger home action code
         */
        postTriggerSourceAction(targetDocType, null, selectedPrevProfDocMatItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    /**
     * Provide outbound logic from source doc for creating cross document
     *
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     * @throws DocActionException
     * @throws SearchConfigureException
     * @throws ServiceEntityInstallationException
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> crossCreateBatchDocToPrevProf(
            ServiceModule sourceServiceModule, List<ServiceEntityNode> selectedSourceDocMatItemList,
            DocumentMatItemBatchGenRequest genRequest, CrossDocConvertRequest.InputOption inputOption,
            LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {
        /*
         * [step1]: call target handleInboundCrossCreateDoc
         */
        int targetDocType = genRequest.getTargetDocType();
        DocActionExecutionProxy<?, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(targetDocType);
        if (targetDocActionProxy == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetDocType);
        }
        int sourceDocType = this.getDocumentContentSpecifier().getDocumentType();
        List<CrossDocBatchConvertReservedProxy.DocContentCreateContext> docContentCreateContextList =
                targetDocActionProxy.handleInboundCrossCreateDocToPrevProf( sourceServiceModule,
                        genRequest.getPrevProfDocType(), sourceDocType, selectedSourceDocMatItemList, genRequest, inputOption,
                        logonInfo);
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            // should raise exception when no data created and stop following action
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        /*
         * [step2]: trigger home action code
         */
        List<ServiceEntityNode> selectedPrevProfDocMatItemList =
                CrossDocBatchConvertProxy.filterOutDocMatItemList(docContentCreateContextList,
                        StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF);
        postTriggerSourceAction(targetDocType, null, selectedPrevProfDocMatItemList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }



    /**
     * [Internal method] Try to trigger source doc action, after cross creation task done on target doc side.
     * @param targetDocType target doc type
     * @param sourceServiceModule [Optional] Main source service model
     * @param selectedSourceDocMatItemList selected source mat item list
     * @throws DocActionException
     */
    private void postTriggerSourceAction(int targetDocType, R sourceServiceModule,
                                         List<ServiceEntityNode> selectedSourceDocMatItemList,
                                         SerialLogonInfo serialLogonInfo) throws DocActionException {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                this.getCrossCopyDocConversionConfigMap();
        CrossCopyDocConversionConfig toTargetConversionConfig = crossCopyDocConversionConfigMap.get(targetDocType);
        if (toTargetConversionConfig != null && toTargetConversionConfig.getTriggerSourceActionCode() > 0) {
            // trigger source action code
            Map<String, R> serviceModelMap = buildServiceModelMap(sourceServiceModule, selectedSourceDocMatItemList);
            if(ServiceCollectionsHelper.checkNullMap(serviceModelMap)){
                return;
            }
            Set<String> keySet = serviceModelMap.keySet();
            for (String key : keySet) {
                try {
                    batchExecSelectedItemTryExecuteParent(
                            new DocActionExecutorCase.DocItemBatchExecutionRequest(serviceModelMap.get(key),
                                    selectedSourceDocMatItemList, toTargetConversionConfig.getTriggerSourceActionCode(), null,
                                    null), serialLogonInfo);
                } catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,key));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,key));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
            }
        }
    }

    /**
     * [Internal method] generate Map for uuid and service model
     * @param sourceServiceModule
     * @param selectedSourceDocMatItemList
     * @return
     * @throws DocActionException
     */
    private Map<String, R> buildServiceModelMap(R sourceServiceModule,
                                                            List<ServiceEntityNode> selectedSourceDocMatItemList)
            throws DocActionException {
        if(ServiceCollectionsHelper.checkNullList(selectedSourceDocMatItemList)){
            return null;
        }
        Map<String, R> resultMap = new HashMap<>();
        DocumentContentSpecifier<R, T, Item> documentContentSpecifier = getDocumentContentSpecifier();
        if(sourceServiceModule != null){
            ServiceEntityNode parentDoc = documentContentSpecifier.getCoreEntity(sourceServiceModule);
            resultMap.put(parentDoc.getUuid(), sourceServiceModule);
        }
        ServiceCollectionsHelper.traverseListInterrupt(selectedSourceDocMatItemList, seItemNode->{
            String parentDocUUID = documentContentSpecifier.getParentDocUUIDByMatItem((DocMatItemNode) seItemNode);
            if(!resultMap.containsKey(parentDocUUID)){
                R tempServiceModule = null;
                try {
                    tempServiceModule =
                            documentContentSpecifier.loadServiceModule(parentDocUUID, seItemNode.getClient());
                } catch (ServiceEntityConfigureException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,parentDocUUID));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
                } catch (ServiceModuleProxyException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e,parentDocUUID));
                    throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
                }
                resultMap.put(parentDocUUID, tempServiceModule);
            }
            return true;
        });
        return resultMap;
    }

    /**
     * Logic for processing empty doc root service model, when current service module is empty
     *
     * @param selectedSourceDocMatItemList
     * @return
     */
    public ServiceModule processEmptyServiceModel(List<ServiceEntityNode> selectedSourceDocMatItemList)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return getDocServiceModelFromItemList(selectedSourceDocMatItemList);
    }

    public ServiceModule getDocServiceModelFromItemList(List<ServiceEntityNode> selectedSourceDocMatItemList)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        DocumentContentSpecifier<R, T, Item> documentContentSpecifier = this.getDocumentContentSpecifier();
        if (ServiceCollectionsHelper.checkNullList(selectedSourceDocMatItemList)) {
            return null;
        }
        return documentContentSpecifier.loadServiceModule(selectedSourceDocMatItemList.get(0).getParentNodeUUID(),
                selectedSourceDocMatItemList.get(0).getClient());
    }

    /**
     * Default way to handle inbound cross create doc request as target document
     *
     * @param sourceServiceModule
     * @param sourceDocType
     * @param selectedSourceDocMatItemList
     * @param genRequest
     * @param logonInfo
     */
    public List<CrossDocBatchConvertProxy.DocContentCreateContext> handleInboundCrossCreateDoc(ServiceModule sourceServiceModule, int sourceDocType,
                                                     List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                     DocumentMatItemBatchGenRequest genRequest,
                                                     CrossDocConvertRequest.InputOption inputOption,
                                                     LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, SearchConfigureException,
            ServiceEntityConfigureException, ServiceEntityInstallationException {
        CrossDocConvertRequest<R, Item, ?> crossDocConvertRequest = this.getCrossDocCovertRequest();
        if (crossDocConvertRequest != null) {
            return crossDocBatchConvertProxy.createTargetDocumentBatch(sourceServiceModule, sourceDocType,
                    selectedSourceDocMatItemList, crossDocConvertRequest, genRequest, inputOption, logonInfo);
        }
        return null;
    }

    /**
     * Default way to handle inbound cross create doc request as target document
     *
     * @param reservedServiceModel
     * @param sourceDocType
     * @param selectedToReserveDocMatItemList
     * @param logonInfo
     */
    public List<CrossDocBatchConvertReservedProxy.DocContentCreateContext> handleInboundCrossCreateDocReserved(
            ServiceModule reservedServiceModel, int reservedDocType, int sourceDocType,
            List<ServiceEntityNode> selectedToReserveDocMatItemList, DocumentMatItemBatchGenRequest genRequest,
            CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException, SearchConfigureException {
        CrossDocConvertReservedRequest<?, ?, ?> reservedCrossDocConvertReservedRequest =
                this.getCrossDocCovertReservedRequest();
        if (reservedCrossDocConvertReservedRequest != null) {
            return crossDocBatchConvertReservedProxy.createTargetBatchDocFromReservedDoc(reservedServiceModel,
                    reservedDocType, sourceDocType, selectedToReserveDocMatItemList,
                    reservedCrossDocConvertReservedRequest, genRequest, inputOption, logonInfo);
        }
        return null;
    }

    public List<CrossDocBatchConvertProxy.DocContentCreateContext> handleInboundCrossCreateDocToPrevProf(
            ServiceModule sourceServiceModule, int prevProfDocType, int sourceDocType,
            List<ServiceEntityNode> selectedSourceDocMatItemList, DocumentMatItemBatchGenRequest genRequest,
            CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException, SearchConfigureException {
        CrossDocConvertProfRequest<?, ?, ?> crossDocConvertProfRequest =
                this.getCrossDocCovertProfRequest();
        if (crossDocConvertProfRequest != null) {
            return crossDocBatchConvertProfProxy.createTargetBatchDocToPrevDoc(
                    sourceServiceModule, prevProfDocType, sourceDocType, selectedSourceDocMatItemList,
                    crossDocConvertProfRequest, genRequest, inputOption, logonInfo);
        }
        return null;
    }

    public List<CrossDocBatchConvertProfProxy.DocContentCreateContext> handleInboundCrossCreateDocFromPrevProf(
            int prevProfDocType, ServiceModule prevProfServiceModule,
            List<ServiceEntityNode> selectedPrevProfDocMatItemList, DocumentMatItemBatchGenRequest genRequest,
            CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws DocActionException, ServiceModuleProxyException, ServiceEntityConfigureException,
            ServiceEntityInstallationException, SearchConfigureException {
        CrossDocConvertProfRequest<?, ?, ?> crossDocConvertProfRequest =
                this.getCrossDocCovertProfRequest();
        if (crossDocConvertProfRequest != null) {
            return crossDocBatchConvertProfProxy.createTargetBatchDocFromPrevDoc(
                    prevProfDocType, prevProfServiceModule, selectedPrevProfDocMatItemList,
                    crossDocConvertProfRequest, genRequest, inputOption, logonInfo);
        }
        return null;
    }

    /**
     * API to get source document item lists according to the given reserved document information
     * @param sourceDocType: source doc type
     * @param reservedDocMatItem: reserved document item
     * @param serialLogonInfo: session info
     */
    public List<ServiceEntityNode> getRawSourceItemListFromReserved(int sourceDocType,
                                                                    DocMatItemNode reservedDocMatItem,
                                                                    SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityConfigureException {
        return getRawSourceItemListFromReserved(reservedDocMatItem.getHomeDocumentType(), sourceDocType,
                ServiceCollectionsHelper.asList(reservedDocMatItem), serialLogonInfo);
    }

    public List<ServiceEntityNode> getRawSourceItemListFromReserved(int reservedDocType, int sourceDocType,
                                                                    List<ServiceEntityNode> selectedToReserveDocMatItemList,
                                                                    SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityConfigureException {
        List<CrossDocBatchConvertReservedProxy.ReseveredDocItemContext> reservedDocItemContextList =
                getSourceItemListFromReserved(reservedDocType, sourceDocType, selectedToReserveDocMatItemList,
                        new CrossDocConvertRequest.InputOption(), serialLogonInfo);
        if (ServiceCollectionsHelper.checkNullList(reservedDocItemContextList)) {
            return null;
        }
        List<ServiceEntityNode> rawSourceItemList = new ArrayList<>();
        for (CrossDocBatchConvertReservedProxy.ReseveredDocItemContext reseveredDocItemContext : reservedDocItemContextList) {
            StorageCoreUnit reservedCoreUnit = reseveredDocItemContext.getReservedAmount();
            if (reservedCoreUnit == null) {
                continue;
            }
            if (reservedCoreUnit.getAmount() > 0) {
                rawSourceItemList.add(reseveredDocItemContext.getSourceDocMatItemNode());
            }
        }
        return rawSourceItemList;
    }

    public List<CrossDocBatchConvertReservedProxy.ReseveredDocItemContext> getSourceItemListFromReserved(
            int reservedDocType, int sourceDocType, List<ServiceEntityNode> selectedToReserveDocMatItemList,
            CrossDocConvertRequest.InputOption inputOption, SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityConfigureException {
        CrossDocConvertReservedRequest<?, ?, ?> reservedCrossDocConvertReservedRequest =
                this.getCrossDocCovertReservedRequest();
        if (reservedCrossDocConvertReservedRequest != null) {
            return crossDocBatchConvertReservedProxy.getSourceItemListFromReserved(reservedDocType, sourceDocType,
                    selectedToReserveDocMatItemList, reservedCrossDocConvertReservedRequest, inputOption,
                    serialLogonInfo);
        }
        return null;
    }

    public void mergeDocBatch(T mergeTargetDocument, int documentType, List<ServiceEntityNode> selectedDocMatItemList,
                              String logonUserUUID, String resOrgUUID)
            throws DocActionException, ServiceEntityConfigureException {
        DocSplitMergeRequest<T, Item> docMergeRequest = this.getDocMergeRequest();
        if (docMergeRequest != null) {
            docSplitMergeProxy.mergeDocBatch(mergeTargetDocument, documentType, selectedDocMatItemList, docMergeRequest,
                    logonUserUUID, resOrgUUID);
        }
    }

    public List<ServiceEntityNode> getExistTargetDocForCreationBatch(DocumentMatItemBatchGenRequest genRequest,
                                                                     DocumentContentSpecifier<?, ?, ?> targetDocSpecifier,
                                                                     List<ServiceEntityNode> allDocMaterialItemList,
                                                                     CrossDocBatchConvertProxy.IRetrieveTargetMatItemUUID retrieveTargetMatItemUUID, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, DocActionException, SearchConfigureException {
        CrossDocConvertRequest<R, Item, ?> crossDocConvertRequest = this.getCrossDocCovertRequest();
        return crossDocBatchConvertProxy.getExistTargetDocForCreationBatch(genRequest, targetDocSpecifier,
                allDocMaterialItemList, crossDocConvertRequest, retrieveTargetMatItemUUID, logonInfo);
    }

    /**
     * Finally output List of <code>DocActionConfigure</code> Standard Document executable action configure
     *
     * @return List of Standard Document executable configure
     */
    public List<DocActionConfigure> getDocActionConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        boolean customDocActionConfigureFlag = this.getCustomDocActionConfigureFlag();
        if (customDocActionConfigureFlag) {
            List<DocActionConfigure> customDocActionConfigureList = this.getCustomDocActionConfigureList(client);
            if (!ServiceCollectionsHelper.checkNullList(customDocActionConfigureList)) {
                return customDocActionConfigureList;
            }
        }
        return this.getDefDocActionConfigureList();
    }

    public List<CrossDocActConfigure> getCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        boolean customCrossDocActionConfigureFlag = this.getCustomCrossDocActionConfigureFlag();
        if (customCrossDocActionConfigureFlag) {
            List<CrossDocActConfigure> customCrossDocActList = this.getCustomCrossDocActConfigureList(client);
            if (!ServiceCollectionsHelper.checkNullList(customCrossDocActList)) {
                return customCrossDocActList;
            }
        }
        return this.getDefCrossDocActConfigureList();
    }

    public abstract ServiceEntityManager getServiceEntityManager();

    /**
     * Logic to check if custom document action configure exist and need to overwrite default configure.
     *
     * @return false as default
     */
    public boolean getCustomDocActionConfigureFlag() {
        return false;
    }

    protected List<CrossDocActConfigure> getCustomCrossDocActionConfigureListTool(String docSettingsId,
                                                                                  int documentType, String client)
            throws ServiceEntityConfigureException, ServiceModuleProxyException {
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(docSettingsId, IServiceEntityNodeFieldConstant.ID);
        ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(ServiceDocumentSetting.FIELD_DOCUMENT_TYPE,
                Integer.toString(documentType));
        ServiceDocumentSetting serviceDocumentSetting =
                (ServiceDocumentSetting) serviceDocumentSettingManager.getEntityNodeByKeyList(
                        ServiceCollectionsHelper.asList(key1, key2), ServiceDocumentSetting.NODENAME, client, null);
        if (serviceDocumentSetting == null) {
            return null;
        }
        ServiceDocumentSettingServiceModel serviceDocumentSettingServiceModel =
                (ServiceDocumentSettingServiceModel) serviceDocumentSettingManager.loadServiceModule(
                        ServiceDocumentSettingServiceModel.class, serviceDocumentSetting);
        List<CrossDocActConfigure> resultList = new ArrayList<>();
        ServiceCollectionsHelper.forEach(serviceDocumentSettingServiceModel.getServiceCrossDocConfigureList(),
                serviceCrossDocConfigureServiceModel -> {
                    ServiceCollectionsHelper.forEach(
                            serviceCrossDocConfigureServiceModel.getServiceCrossDocEventMonitorList(),
                            serviceCrossDocEventMonitorServiceModel -> {
                                ServiceCrossDocEventMonitor serviceCrossDocEventMonitor =
                                        serviceCrossDocEventMonitorServiceModel.getServiceCrossDocEventMonitor();
                                CrossDocActConfigure crossDocActConfigure = new CrossDocActConfigure();
                                ServiceCrossDocConfigure serviceCrossDocConfigure =
                                        serviceCrossDocConfigureServiceModel.getServiceCrossDocConfigure();
                                crossDocActConfigure.setHomeDocType(
                                        Integer.parseInt(serviceDocumentSetting.getDocumentType()));
                                crossDocActConfigure.setTargetDocType(
                                        Integer.parseInt(serviceCrossDocConfigure.getTargetDocType()));
                                crossDocActConfigure.setCrossDocRelationType(
                                        serviceCrossDocConfigure.getCrossDocRelationType());
                                crossDocActConfigure.setTriggerHomeActionCode(
                                        serviceCrossDocEventMonitor.getTriggerHomeActionCode());
                                crossDocActConfigure.setTargetActionCode(
                                        serviceCrossDocEventMonitor.getTargetActionCode());
                                crossDocActConfigure.setTriggerParentMode(
                                        serviceCrossDocEventMonitor.getTriggerParentMode());
                                crossDocActConfigure.setTriggerDocActionScenario(
                                        serviceCrossDocEventMonitor.getTriggerDocActionScenario());
                                resultList.add(crossDocActConfigure);
                                return serviceCrossDocEventMonitorServiceModel;
                            });
                    return serviceCrossDocConfigureServiceModel;
                });
        return resultList;
    }

    /**
     * Logic to check if custom cross doc configure exist and need to overwrite default configure.
     *
     * @return false as default
     */
    public boolean getCustomCrossDocActionConfigureFlag() {
        return false;
    }

    /**
     * Utility method: to get the document action configure model by action code.
     *
     * @param docActionCode: Standard Document executable action code
     * @return
     */
    public DocActionConfigure getDocActionConfigureByCode(int docActionCode, String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        List<DocActionConfigure> docActionConfigureList = filterDocActionConfigureCore(tempActionConfigure -> docActionCode == tempActionConfigure.getActionCode(), client);
        return docActionConfigureList.get(0);
    }

    /**
     * Utility method: to get the document action configure model by action code.
     *
     * @param targetStatus: Target status of DocActionConfigure
     * @return
     */
    public List<DocActionConfigure> getDocActionConfigureByTargetStatus(int targetStatus, String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return filterDocActionConfigureCore(tempActionConfigure -> targetStatus == tempActionConfigure.getTargetStatus(), client);
    }

    public List<DocActionConfigure> filterDocActionConfigureCore(Predicate<? super DocActionConfigure> predicate, String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        List<DocActionConfigure> docActionConfigureList = this.getDocActionConfigureList(client);
        if (ServiceCollectionsHelper.checkNullList(docActionConfigureList)) {
            return null;
        }
        List<DocActionConfigure> subDocActionConfigureList = docActionConfigureList.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        if (ServiceCollectionsHelper.checkNullList(subDocActionConfigureList)) {
            return null;
        } else {
            return subDocActionConfigureList;
        }
    }

    /**
     * Get the List of Document executable action code map in specified language, In this super class, just provide
     * system default action code map.
     *
     * @param lanCode: Language code
     * @return document executable action code map
     * @throws ServiceEntityInstallationException
     */
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        // Default action code map
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, null, null);
    }

    /**
     * Utility method: to get the document action code label in specified language.
     *
     * @param docActionCode: Standard Document executable action code
     * @param lanCode:       Language code
     * @return
     * @throws ServiceEntityInstallationException
     * @throws DocActionException
     */
    public String getActionCodeLabel(int docActionCode, String lanCode)
            throws ServiceEntityInstallationException, DocActionException {
        Map<Integer, String> actionCodeMap = this.getActionCodeMap(lanCode);
        if (actionCodeMap == null) {
            throw new DocActionException(DocActionException.TYPE_SYSTEM_WRONG);
        }
        return actionCodeMap.get(docActionCode);
    }

    /**
     * Logic to check status before execute root document status update
     *
     * @param serviceEntityNode: root document node.
     * @param docActionCode:     Standard Document executable action code
     * @param lanCode:           Language code
     * @param skipFlag
     * @return
     * @throws DocActionException
     * @throws ServiceEntityInstallationException
     */
    public boolean checkStatus(T serviceEntityNode, int docActionCode, DocActionConfigure docActionConfigure,
                               String lanCode, boolean skipFlag)
            throws DocActionException, ServiceEntityInstallationException {
        int curStatus = getDocumentContentSpecifier().getDocumentStatus(serviceEntityNode);
        return checkStatusCore(docActionCode, lanCode, curStatus, docActionConfigure, skipFlag,
                serviceEntityNode.getClient());
    }

    /**
     * Logic to check item status before execute doc mat item status update
     *
     * @param serviceEntityNode
     * @param docActionCode
     * @param lanCode
     * @param skipFlag
     * @return
     * @throws DocActionException
     * @throws ServiceEntityInstallationException
     */
    public boolean checkItemStatus(Item serviceEntityNode, int docActionCode, DocActionConfigure docActionConfigure,
                                   String lanCode, boolean skipFlag, String client)
            throws DocActionException, ServiceEntityInstallationException {
        int curStatus = getDocumentContentSpecifier().getItemStatus(serviceEntityNode);
        if (curStatus == 0) {
            return false;
        }
        return checkStatusCore(docActionCode, lanCode, curStatus, docActionConfigure, skipFlag, client);
    }

    public boolean checkItemTargetStatus(Item serviceEntityNode, int docActionCode) throws DocActionException {
        int curStatus = getDocumentContentSpecifier().getItemStatus(serviceEntityNode);
        if (curStatus == 0) {
            return false;
        }
        DocActionConfigure docActionConfigure = null;
        try {
            docActionConfigure = this.getDocActionConfigureByCode(docActionCode, serviceEntityNode.getClient());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        if (docActionConfigure == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docActionCode);
        }
        return docActionConfigure.getTargetStatus() == curStatus;
    }

    /**
     * Core Logic to check status before update root node document status
     *
     * @param docActionCode
     * @param lanCode:      In case throw error exception,
     * @param curStatus:    current document status
     * @param skipFlag
     * @return
     * @throws DocActionException
     * @throws ServiceEntityInstallationException
     */
    public boolean checkStatusCore(int docActionCode, String lanCode, int curStatus,
                                   DocActionConfigure docActionConfigure, boolean skipFlag, String client)
            throws DocActionException, ServiceEntityInstallationException {
        if (docActionConfigure == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docActionCode);
        }
        List<Integer> preStatusList = docActionConfigure.getPreStatusList();
        if (!DocActionNodeProxy.checkCurStatus(preStatusList, curStatus)) {
            if (!skipFlag) {
                String actionCodeLabel = this.getActionCodeLabel(docActionCode, lanCode);
                throw new DocActionException(DocActionException.PARA_WRONG_PRE_STATUS, actionCodeLabel);
            }
            return false;
        }
        return true;
    }

    /**
     * API to record each item action execution, check & update Item Status
     * @param docMatItem
     * @param docActionCode
     * @param serialLogonInfo
     * @param skipFlag
     * @param docItemActionExecution
     * @throws DocActionException
     */
    public void checkUpdateItemStatus(Item docMatItem, int docActionCode, SerialLogonInfo serialLogonInfo,
                                      boolean skipFlag, DocItemActionExecution<Item> docItemActionExecution)
            throws DocActionException {
        boolean checkStatus;
        if (docActionCode == 0){
            return;
        }
        try {
            DocActionConfigure docActionConfigure = getDocActionConfigureByCode(docActionCode, docMatItem.getClient());
            checkStatus =
                    checkItemStatus(docMatItem, docActionCode, docActionConfigure, serialLogonInfo.getLanguageCode(),
                            skipFlag, serialLogonInfo.getClient());
        } catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        if (!checkStatus) {
            return;
        }
        int targetStatus = calculateTargetStatus(docActionCode, docMatItem.getClient());
        ServiceEntityTargetStatus serviceEntityTargetStatus = new ServiceEntityTargetStatus(docMatItem, targetStatus);
        if (docItemActionExecution != null) {
            docItemActionExecution.execute(docMatItem, new ItemSelectionExecutionContext());
        }
        getDocumentContentSpecifier().setItemStatus(serviceEntityTargetStatus);
    }

    private int calculateTargetStatus(int docActionCode, String client) throws DocActionException {
        DocActionConfigure docActionConfigure = null;
        try {
            docActionConfigure = this.getDocActionConfigureByCode(docActionCode, client);
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        if (docActionConfigure == null) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, docActionCode);
        }
        return docActionConfigure.getTargetStatus();
    }

    public void updateStatus(T serviceEntityNode, int docActionCode, SerialLogonInfo serialLogonInfo,
                             DocActionExecution<T> docActionExecution) throws DocActionException {
        int targetStatus = calculateTargetStatus(docActionCode, serviceEntityNode.getClient());
        ServiceEntityTargetStatus serviceEntityTargetStatus =
                new ServiceEntityTargetStatus(serviceEntityNode, targetStatus);
        if (docActionExecution != null) {
            docActionExecution.execute(serviceEntityNode, serialLogonInfo);
        }
        getDocumentContentSpecifier().setDocumentStatus(serviceEntityTargetStatus);
    }

    public void updateDocActionNodeWrapper(ServiceDocExecutionRequest<R, T, Item> serviceDocExecutionRequest)
            throws ServiceEntityConfigureException, DocActionException, ServiceEntityInstallationException {
        R serviceModule = serviceDocExecutionRequest.getServiceModule();
        int docActionCode = serviceDocExecutionRequest.getDocActionCode();
        boolean skipFlag = serviceDocExecutionRequest.getSkipFlag();
        SerialLogonInfo serialLogonInfo = serviceDocExecutionRequest.getSerialLogonInfo();
        String nodeInstId = getDocumentContentSpecifier().getDocActionNodeInstId();
        String refDocMatItemUUID = serviceDocExecutionRequest.getRefDocMatItemUUID();
        T parentDocNode = getDocumentContentSpecifier().getCoreEntity(serviceModule);
        int documentType = getDocumentContentSpecifier().getDocumentType();
        updateStatus(parentDocNode, docActionCode, serialLogonInfo,
                serviceDocExecutionRequest.getDocActionCallback());
        docActionNodeProxy.updateDocActionWrapper(docActionCode, nodeInstId, refDocMatItemUUID, documentType,
                this.getServiceEntityManager(), serviceModule, parentDocNode, serialLogonInfo.getRefUserUUID(),
                serialLogonInfo.getResOrgUUID());
    }

    public void updateDocActionNode(ServiceModule serviceModule, int docActionCode, String nodeInstId,
                                    String refDocMatItemUUID, ServiceEntityNode parentDocNode, int documentType,
                                    String logonUserUUID, String organizationUUID)
            throws ServiceEntityConfigureException {
        docActionNodeProxy.updateDocActionWrapper(docActionCode, nodeInstId, refDocMatItemUUID, documentType,
                this.getServiceEntityManager(), serviceModule, parentDocNode, logonUserUUID, organizationUUID);
    }

    @Transactional
    public void defExecuteActionCore(R serviceModel, int docActionCode, DocActionExecution<T> docActionCallback,
                                     DocItemActionExecution<Item> docItemActionCallback,
                                     SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        ServiceDocExecutionRequest<R, T, Item> serviceDocExecutionRequest =
                new ServiceDocExecutionRequest<>(serviceModel, docActionCode, serialLogonInfo, false, null,
                        docActionCallback, docItemActionCallback);
        /*
         * [Step1] pre-check conditions
         */
        R serviceModule = serviceDocExecutionRequest.getServiceModule();
        boolean skipFlag = serviceDocExecutionRequest.getSkipFlag();
        DocActionConfigure docActionConfigure;
        try {
            docActionConfigure = this.getDocActionConfigureByCode(docActionCode, serialLogonInfo.getClient());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
        T serviceEntityNode = getDocumentContentSpecifier().getCoreEntity(serviceModule);
        try {
            ServiceConcurrentProxy.writeLock(serviceEntityNode.getUuid(), serialLogonInfo.getRefUserUUID());
            boolean checkStatus =
                    checkStatus(serviceEntityNode, docActionCode, docActionConfigure, serialLogonInfo.getLanguageCode(),
                            skipFlag);
            if (!checkStatus) {
                return;
            }
            /*
             * [Step2] process each item firstly
             */
            DocumentContentSpecifier<R, T, Item> documentContentSpecifier = getDocumentContentSpecifier();
            if (docItemActionCallback == null) {
                documentContentSpecifier.traverseMatItemNode(serviceModel, (serviceEntityNode1, itemSelectionExecutionContext) -> {
                    checkUpdateItemStatus(serviceEntityNode1, docActionCode, serialLogonInfo, skipFlag,
                            null);
                    return true;
                }, serialLogonInfo);
            } else {
                documentContentSpecifier.traverseMatItemNode(serviceModel, docItemActionCallback, serialLogonInfo);
            }

            /*
             * [Step3] process document root node update action node
             */
            this.updateDocActionNodeWrapper(serviceDocExecutionRequest);
            getServiceEntityManager().updateServiceModuleWithDelete(serviceModel.getClass(), serviceModel,
                    serialLogonInfo.getRefUserUUID(), serialLogonInfo.getHomeOrganizationUUID(),
                    this.getDocumentContentSpecifier().getDocNodeInstId(),
                    this.getDocumentContentSpecifier().getDocServiceUIModelExtension());
        } catch (ServiceEntityConfigureException | ServiceEntityInstallationException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } finally {
            ServiceConcurrentProxy.unLockWrite(serviceEntityNode.getUuid());
        }
    }

    @Transactional
    public void batchExecItemHomeAction(ServiceModule serviceModel, List<ServiceEntityNode> selectedSourceDocMatItemList, int docActionCode,
                                        SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        defBatchExecItemHomeAction(serviceModel, selectedSourceDocMatItemList, docActionCode, null, null, serialLogonInfo);
    }

    @Transactional
    public void defBatchExecItemHomeAction(ServiceModule serviceModel, List<ServiceEntityNode> selectedSourceDocMatItemList,  int docActionCode, DocActionExecution<T> docActionCallback,
                                           DocItemActionExecution<Item> docItemActionCallback,
                                     SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        batchExecSelectedItemTryExecuteParent(
                new DocActionExecutorCase.DocItemBatchExecutionRequest(serviceModel,
                        selectedSourceDocMatItemList, docActionCode, docActionCallback, docItemActionCallback), serialLogonInfo);
    }


    @Transactional
    public void execItemExclusiveHomeAction(ServiceModule serviceModel, List<ServiceEntityNode> selectedSourceDocMatItemList, int docActionCode, int secondaryActionCode,
                                        SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        defBatchExclusiveItemHomeAction(serviceModel, selectedSourceDocMatItemList, docActionCode, secondaryActionCode,null, null, serialLogonInfo);
    }

    @Transactional
    public void defBatchExclusiveItemHomeAction(ServiceModule serviceModel, List<ServiceEntityNode> selectedSourceDocMatItemList,  int docActionCode, int secondaryActionCode, DocActionExecution<T> docActionCallback,
                                           DocItemActionExecution<Item> docItemActionCallback,
                                           SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityInstallationException, ServiceEntityConfigureException {
        selectItemToParentExecutorCase.exclusiveExecSelectedItemTryExecuteParent(this, new DocActionExecutorCase.DocItemBatchExecutionRequest(serviceModel,
                        selectedSourceDocMatItemList, docActionCode, secondaryActionCode, docActionCallback, docItemActionCallback),
                serialLogonInfo);
    }

    public static class ServiceDocExecutionRequest<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode> {

        private R serviceModule;

        private int docActionCode;

        private SerialLogonInfo serialLogonInfo;

        private boolean skipFlag;

        private String refDocMatItemUUID;

        private DocActionExecution<T> docActionCallback;

        private DocItemActionExecution<Item> docItemActionCallback;

        public ServiceDocExecutionRequest() {

        }

        public ServiceDocExecutionRequest(R serviceModule, int docActionCode, SerialLogonInfo serialLogonInfo,
                                          boolean skipFlag, String refDocMatItemUUID,
                                          DocActionExecution<T> docActionCallback,
                                          DocItemActionExecution<Item> docItemActionCallback) {
            this.serviceModule = serviceModule;
            this.docActionCode = docActionCode;
            this.serialLogonInfo = serialLogonInfo;
            this.skipFlag = skipFlag;
            this.refDocMatItemUUID = refDocMatItemUUID;
            this.docActionCallback = docActionCallback;
            this.docItemActionCallback = docItemActionCallback;
        }

        public R getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(R serviceModule) {
            this.serviceModule = serviceModule;
        }

        public int getDocActionCode() {
            return docActionCode;
        }

        public void setDocActionCode(int docActionCode) {
            this.docActionCode = docActionCode;
        }

        public boolean getSkipFlag() {
            return skipFlag;
        }

        public void setSkipFlag(boolean skipFlag) {
            this.skipFlag = skipFlag;
        }

        public String getRefDocMatItemUUID() {
            return refDocMatItemUUID;
        }

        public void setRefDocMatItemUUID(String refDocMatItemUUID) {
            this.refDocMatItemUUID = refDocMatItemUUID;
        }

        public SerialLogonInfo getSerialLogonInfo() {
            return serialLogonInfo;
        }

        public void setSerialLogonInfo(SerialLogonInfo serialLogonInfo) {
            this.serialLogonInfo = serialLogonInfo;
        }

        public DocActionExecution<T> getDocActionCallback() {
            return docActionCallback;
        }

        public void setDocActionCallback(DocActionExecution<T> docActionCallback) {
            this.docActionCallback = docActionCallback;
        }

        public DocItemActionExecution<Item> getDocItemActionCallback() {
            return docItemActionCallback;
        }

        public void setDocItemActionCallback(DocItemActionExecution<Item> docItemActionCallback) {
            this.docItemActionCallback = docItemActionCallback;
        }
    }

    public static class ServiceEntityTargetStatus {

        private ServiceEntityNode serviceEntityNode;

        private int targetStatus;

        public ServiceEntityTargetStatus() {
        }

        public ServiceEntityTargetStatus(ServiceEntityNode serviceEntityNode, int targetStatus) {
            this.serviceEntityNode = serviceEntityNode;
            this.targetStatus = targetStatus;
        }

        public ServiceEntityNode getServiceEntityNode() {
            return serviceEntityNode;
        }

        public void setServiceEntityNode(ServiceEntityNode serviceEntityNode) {
            this.serviceEntityNode = serviceEntityNode;
        }

        public int getTargetStatus() {
            return targetStatus;
        }

        public void setTargetStatus(int targetStatus) {
            this.targetStatus = targetStatus;
        }

    }

    /**
     * Executor for Doc root action
     */
    public interface DocActionExecution<T extends ServiceEntityNode> {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        T execute(T serviceEntityNode, SerialLogonInfo serialLogonInfo) throws DocActionException;
    }


    /**
     * Executor for Doc Mat item action
     */
    public interface DocItemActionExecution<Item extends ServiceEntityNode> {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        boolean execute(Item serviceEntityNode, ItemSelectionExecutionContext itemSelectionExecutionContext) throws DocActionException;
    }

    public static class ItemSelectionExecutionContext {

        protected List<ServiceEntityNode> allItemList;

        public ItemSelectionExecutionContext() {

        }

        public ItemSelectionExecutionContext(List<ServiceEntityNode> allItemList) {
            this.allItemList = allItemList;
        }

        public List<ServiceEntityNode> getAllItemList() {
            return allItemList;
        }

        public void setAllItemList(List<ServiceEntityNode> allItemList) {
            this.allItemList = allItemList;
        }
    }

    /**
     * Executor for Doc Mat item custom check logic
     */
    public interface DocItemCheck<Item extends ServiceEntityNode> {
        /**
         * This method is called in the impersonation execution context.
         *
         * @return The return value
         */
        boolean execute(Item serviceEntityNode) throws DocActionException;
    }


    /**
     * Execution Framework for getting message list by each item
     *
     * @param <Item>
     * @param <EX>
     */
    public interface IGetMessageExecutor<Item extends ServiceEntityNode, EX extends ServiceEntityException> {

        List<SimpleSEMessageResponse> executeService(Item docMatItem)
                throws DocActionException, EX, ServiceModuleProxyException, ServiceEntityConfigureException;
    }


    /**
     * Framework methods to execute precheck jobs and generate messages if [Error] or [Warning]
     *
     * @param serviceModule
     * @return
     */
    public <EX extends ServiceEntityException> List<SimpleSEMessageResponse> preCheckErrorMessage(R serviceModule,
                                                                                                  IGetMessageExecutor<Item, EX> getMessageExecutor)
            throws EX, ServiceEntityConfigureException, DocActionException, ServiceModuleProxyException {
        List<ServiceEntityNode> docMatItemList = this.getDocumentContentSpecifier().getDocMatItemList(serviceModule);
        List<SimpleSEMessageResponse> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(docMatItemList)) {
            for (ServiceEntityNode seNode : docMatItemList) {
                Item docMatItem = (Item) seNode;
                // Get item check
                List<SimpleSEMessageResponse> messageList = getMessageExecutor.executeService(docMatItem);
                if (ServiceCollectionsHelper.checkNullList(messageList)) {
                    continue;
                }
                for (SimpleSEMessageResponse simpleSEMessageResponse : messageList) {
                    if (simpleSEMessageResponse.getErrorParas() == null) {
                        String indentifier = docFlowProxy.getDefDocItemIdentifier((DocMatItemNode) docMatItem);
                        simpleSEMessageResponse.setErrorParas(new String[]{indentifier});
                    }
                    resultList.add(simpleSEMessageResponse);
                }
            }
        }
        return resultList;
    }

    public void batchExecItemTryExecuteParent(
            DocActionExecutorCase.DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
            SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityInstallationException, ServiceModuleProxyException {
        docMatItemToParentExecutorCase.batchExecItemTryExecuteParent(this, docItemBatchExecutionRequest,
                serialLogonInfo);
    }

    public void batchExecSelectedItemTryExecuteParent(
            DocActionExecutorCase.DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
            SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityInstallationException, ServiceModuleProxyException,
            ServiceEntityConfigureException {
        selectItemToParentExecutorCase.batchExecSelectedItemTryExecuteParent(this, docItemBatchExecutionRequest,
                serialLogonInfo);
    }

}
