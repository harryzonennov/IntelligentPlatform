package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.DocumentMatItemRawSearchRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CrossDocBatchConvertProxy<SourceServiceModel extends ServiceModule,
        TargetServiceModel extends ServiceModule,
        TargetItem extends DocMatItemNode, TargetItemServiceModel extends ServiceModule> {

    public static final String KEY_NEWTARGET = "newTarget";

    @Autowired
    protected DocBatchConvertProxy docBatchConvertProxy;

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected DocFlowProxy docFlowProxy;

    @Autowired
    protected ServiceModuleProxy serviceModuleProxy;

    @Autowired
    protected DocInvolvePartyProxy docInvolvePartyProxy;

    @Autowired
    protected DocAttachmentProxy docAttachmentProxy;

    @Autowired
    protected ReserveDocItemProxy reserveDocItemProxy;

    @Autowired
    protected PrevNextDocItemProxy prevNextDocItemProxy;

    protected Logger logger = LoggerFactory.getLogger(CrossDocBatchConvertProxy.class);

    @Transactional
    public List<DocContentCreateContext> createTargetDocumentBatch(SourceServiceModel sourceServiceModel, int sourceDocType,
                                                        List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                        CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                        DocumentMatItemBatchGenRequest genRequest,
                                                        CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException,
            SearchConfigureException {
        /*
         * [Step1] found the existed quality check data: check order and existed
         * reference check material item
         */
        if (ServiceCollectionsHelper.checkNullList(selectedSourceDocMatItemList)) {
            return null;
        }
        TargetServiceModel targetDocServiceModel = (TargetServiceModel) getExistedTargetDocServiceModule(genRequest,
                crossDocConvertRequest, sourceServiceModel,sourceDocType,
                DocMatItemNode::getNextDocMatItemUUID, logonInfo);
        /*
         * [Step2] Batch creation of in-bound item list from purchase contract
         * material item list
         */
        List<DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        loadPrevDocFramework(sourceDocType, selectedSourceDocMatItemList, crossDocConvertRequest,
                (sourceDocMatItemNode, refMaterialSKU, sourceDocActionProxy, targetDocOffset) -> {
                    try {
                        DocMatItemCreateContext docMatItemCreateContext =
                                genDefTargetMatItemServiceModelPrev(sourceServiceModel,
                                        sourceDocMatItemNode,
                                        docContentCreateContextList,
                                        targetDocServiceModel,
                                        crossDocConvertRequest,genRequest, targetDocOffset, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                        // in case need to break loops
                        return !docMatItemCreateContext.getBreakFlag();
                    } catch (ServiceModuleProxyException | DocActionException | ServiceEntityConfigureException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return true;
                }, inputOption, logonInfo);
        storeContext(docContentCreateContextList,LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    public void loadPrevDocFramework(int sourceDocType,
                                     List<ServiceEntityNode> selectedSourceDocMatItemList,
                                     CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                     ISourceDocItemExecutor sourceDocItemExecutor,
                                     CrossDocConvertReservedRequest.InputOption inputOption,
                                     LogonInfo logonInfo)
            throws DocActionException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(selectedSourceDocMatItemList)) {
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        List<ServiceEntityNode> rawMaterialSKUList =
                docFlowProxy.getRefMaterialSKUList(selectedSourceDocMatItemList, null, logonInfo.getClient());
        DocActionExecutionProxy<SourceServiceModel, ?, ?> sourceDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocType);
        AtomicInteger targetDocOffset = new AtomicInteger();
        ServiceCollectionsHelper.traverseListInterrupt(selectedSourceDocMatItemList, seNode -> {
            DocMatItemNode sourceDocMatItemNode = (DocMatItemNode) seNode;
            if ( !inputOption.getUpdateNextItemUUID()) {
                if (!ServiceEntityStringHelper.checkNullString(sourceDocMatItemNode.getNextDocMatItemUUID())) {
                    return true;
                }
            }
            MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper.filterSENodeOnline(
                    sourceDocMatItemNode.getRefMaterialSKUUUID(), rawMaterialSKUList);
            if (sourceDocItemExecutor != null) {
                boolean result = sourceDocItemExecutor.execute(sourceDocMatItemNode, refMaterialSKU, sourceDocActionProxy, targetDocOffset);
                targetDocOffset.getAndIncrement();
                return result;
            }
            return true;
        });
    }

    protected ServiceModule getExistedTargetDocServiceModule(DocumentMatItemBatchGenRequest genRequest,
                                                             CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                             ServiceModule sourceServiceModel, int sourceDocType,
                                                             IRetrieveTargetMatItemUUID retrieveTargetMatItemUUID, LogonInfo logonInfo)
            throws DocActionException, SearchConfigureException, ServiceEntityConfigureException {
        List<ServiceEntityNode> targetExistDocList = getExistTargetDocList(genRequest,
                crossDocConvertRequest, sourceServiceModel, sourceDocType,
                retrieveTargetMatItemUUID, logonInfo);
        DocumentContentSpecifier<TargetServiceModel, ?, ?> targetDocSpecifier =
                docActionExecutionProxyFactory.getSpecifierByDocType(genRequest.getTargetDocType());
        TargetServiceModel targetDocServiceModel = null;
        if (!ServiceCollectionsHelper.checkNullList(targetExistDocList)) {
            ServiceEntityNode targetDoc = targetExistDocList.get(0);
            try {
                targetDocServiceModel = targetDocSpecifier.loadServiceModule(targetDoc.getUuid(),
                        targetDoc.getClient());
            } catch (ServiceModuleProxyException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        return targetDocServiceModel;
    }

    /**
     * Trying to get the possible existed target document list, which could be used for contains the newly created target material item list
     * if these target document list could be found, then don't need to create a new target document.
     * @param genRequest: the document material item batch generation request, which contains the target UUID and other information
     * @param crossDocConvertRequest: cross document conversion request, which contains the filter logic and other information
     * @param sourceServiceModel: source service model
     * @param retrieveTargetMatItemUUID: The custom callback how to get the target material item UUID from the source document material item node
     * @param logonInfo: logon information instance
     * @return
     * @throws ServiceEntityConfigureException
     * @throws DocActionException
     * @throws SearchConfigureException
     */
    protected List<ServiceEntityNode> getExistTargetDocList(DocumentMatItemBatchGenRequest genRequest,
                                                  CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                  ServiceModule sourceServiceModel, int sourceDocType,
                                                  IRetrieveTargetMatItemUUID retrieveTargetMatItemUUID, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, DocActionException, SearchConfigureException {
        List<ServiceEntityNode> targetExistDocList = new ArrayList<>();
        String targetUUID = genRequest.getTargetUUID();
        DocumentContentSpecifier<TargetServiceModel, ?, ?> targetDocSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(genRequest.getTargetDocType());
        DocumentContentSpecifier sourceDocSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(sourceDocType);
        if (!ServiceEntityStringHelper.checkNullString(targetUUID)) {
            // in case specify an existed target document by target UUID from UI side.
            if (!targetUUID.equals(KEY_NEWTARGET)) {
                ServiceEntityNode targetDoc =
                        targetDocSpecifier.getDocumentManager().getEntityNodeByUUID(targetUUID, targetDocSpecifier.getDocNodeName(),
                                logonInfo.getClient());
                if (targetDoc != null) {
                    targetExistDocList.add(targetDoc);
                }
            }// Have to create new target
        } else {
            // in case target doc not specified, using default way to get target doc list
            if (sourceServiceModel != null) {
                List<ServiceEntityNode> allSourceDocMatItemList = sourceDocSpecifier.getDocMatItemList(sourceServiceModel);
                targetExistDocList =
                        getExistTargetDocForCreationBatch(genRequest, targetDocSpecifier, allSourceDocMatItemList,
                                crossDocConvertRequest, retrieveTargetMatItemUUID, logonInfo);
            }
        }
        return targetExistDocList;
    }

    /**
     * Initialize the target service model, as well as the function copy to target document.
     * @param sourceServiceModule
     * @param sourceDocMatItem
     * @param sourceDocActionProxy
     * @param targetDocActionProxy
     * @param crossDocConvertRequest
     * @param targetDocOffset
     * @return
     * @throws DocActionException
     * @throws ServiceEntityConfigureException
     */
    public TargetServiceModel initCopyToTargetServiceModelWrapper(SourceServiceModel sourceServiceModule,
                                                                  DocMatItemNode sourceDocMatItem,
                                                                  DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy,
                                                                  DocActionExecutionProxy<?, ?, ?> targetDocActionProxy,
                                                                  CrossDocConvertRequest<TargetServiceModel,
                                                                          TargetItem, TargetItemServiceModel> crossDocConvertRequest,AtomicInteger targetDocOffset)
            throws DocActionException, ServiceEntityConfigureException {
        DocumentContentSpecifier targetDocSpecifier = targetDocActionProxy.getDocumentContentSpecifier();
        DocumentContentSpecifier sourceDocSpecifier = sourceDocActionProxy.getDocumentContentSpecifier();
        TargetServiceModel targetServiceModule =
                initCopyToTargetDoc(sourceServiceModule, sourceDocActionProxy, targetDocActionProxy,
                        crossDocConvertRequest, sourceDocMatItem.getClient(), targetDocOffset.get());
        targetDocOffset.getAndIncrement();
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                sourceDocActionProxy.getCrossCopyDocConversionConfigMap();
        targetServiceModule = (TargetServiceModel) convToDefTargetServiceModel(sourceServiceModule, targetServiceModule, sourceDocSpecifier,
                targetDocSpecifier, crossDocConvertRequest.getConvertToTargetDoc(),
                crossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType()));
        return targetServiceModule;
    }

    public TargetServiceModel initCopyToTargetDoc(SourceServiceModel sourceServiceModel,
                                                  DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy,
                                                  DocActionExecutionProxy<?, ?, ?> targetDocActionProxy,
                                                  CrossDocConvertRequest<TargetServiceModel,
                                                          TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                  String client, int offset)
            throws ServiceEntityConfigureException, DocActionException {
        DocumentContentSpecifier<SourceServiceModel, ?, ?> sourceDocSpecifier =
                (DocumentContentSpecifier<SourceServiceModel, ?, ?>) sourceDocActionProxy.getDocumentContentSpecifier();
        CrossDocConvertRequest.IGenerateTargetServiceModel generateTargetServiceModel =
                crossDocConvertRequest.getGenerateTargetServiceModel();
        if (generateTargetServiceModel != null) {
            // Use customized callback to initial target document service module.
            TargetServiceModel targetDocServiceModel =
                    (TargetServiceModel) generateTargetServiceModel.execute(sourceServiceModel, sourceDocSpecifier);
            return targetDocServiceModel;
        } else {
            // create default target service model
            DocumentContentSpecifier<TargetServiceModel, ?, ?> targetDocSpecifier =
                    (DocumentContentSpecifier<TargetServiceModel, ?, ?>) targetDocActionProxy.getDocumentContentSpecifier();
            Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                    sourceDocActionProxy.getCrossCopyDocConversionConfigMap();
            TargetServiceModel targetServiceModel = targetDocSpecifier.createDocServiceModule(client, null, offset);
            targetServiceModel = convToDefTargetServiceModel(sourceServiceModel, targetServiceModel, sourceDocSpecifier,
                    targetDocSpecifier, crossDocConvertRequest.getConvertToTargetDoc(),
                    crossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType()));
            return targetServiceModel;
        }
    }

    /**
     * Default Logic to new target item instance and basic data conversion,
     * return null, if data is not valid
     *
     */
    public DocMatItemCreateContext initConvertToTargetMatItem(DocMatItemNode sourceMatItemNode,
                                                 DocumentContentSpecifier targetDocSpecifier,
                                                 ServiceEntityNode targetDocument,
                                                 CrossCopyDocConversionConfig crossCopyDocConversionConfig,
                                                 DocumentMatItemBatchGenRequest genRequest, AtomicInteger targetDocOffset,
                                                 SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if (sourceMatItemNode != null) {
            TargetItem targetMatItemNode = (TargetItem) targetDocSpecifier.createItem(targetDocument, targetDocOffset.getAndIncrement());
            docFlowProxy.buildItemPrevNextRelationship(sourceMatItemNode, targetMatItemNode, genRequest, serialLogonInfo);
            // Post update logic when prev-next relationship build
            targetMatItemNode = initPostUpdateTargetMatItem(sourceMatItemNode, targetMatItemNode, crossCopyDocConversionConfig, serialLogonInfo);
            return new DocMatItemCreateContext(targetMatItemNode, sourceMatItemNode, null, null);
        }
        return null;
    }

    /**
     * Post update logic when prev-next relationship is build for sourceMatItemNode and targetMatItemNode.
     * @param sourceMatItemNode
     * @param crossCopyDocConversionConfig
     * @param targetMatItemNode
     * @param serialLogonInfo
     * @return
     */
    public TargetItem initPostUpdateTargetMatItem(DocMatItemNode sourceMatItemNode,TargetItem targetMatItemNode,
                                                  CrossCopyDocConversionConfig crossCopyDocConversionConfig,
                                                  SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if (crossCopyDocConversionConfig == null) {
            return targetMatItemNode;
        }
        if (crossCopyDocConversionConfig.getReserveOnSrc() == StandardSwitchProxy.SWITCH_ON) {
            // Reserving source doc material item by the newly created target doc material item if necessary.
            reserveDocItemProxy.reserveDocItemOnlineReserved(targetMatItemNode,
                     sourceMatItemNode.getHomeDocumentType(),
                    sourceMatItemNode.getUuid(), serialLogonInfo);
        }
        return targetMatItemNode;
    }

    public DocMatItemCreateContext genDefTargetMatItemServiceModelPrev(SourceServiceModel sourceServiceModel,
                                                                   DocMatItemNode sourceMatItemNode,
                                                                   List<DocContentCreateContext> docContentCreateContextList,
                                                                   TargetServiceModel targetServiceModule,
                                                                   CrossDocConvertRequest crossDocConvertRequest,
                                                                   DocumentMatItemBatchGenRequest genRequest, AtomicInteger targetDocOffset, SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityConfigureException, ServiceModuleProxyException {
        // In case there is no existed target Service module specific, then trying to fitler proper target service module by filter <code>docContentCreateContextList</code>
        if (targetServiceModule == null) {
            targetServiceModule = (TargetServiceModel) filterTargetRootDoc(crossDocConvertRequest.getFilterRootDocContext(), docContentCreateContextList,
                    null, sourceMatItemNode);
        }
        DocActionExecutionProxy sourceDocActionProxy = docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(
                genRequest.getSourceDocType());
        DocActionExecutionProxy targetDocActionProxy = docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(
                genRequest.getTargetDocType());
        DocumentContentSpecifier sourceDocSpecifier = sourceDocActionProxy.getDocumentContentSpecifier();
        DocumentContentSpecifier targetDocSpecifier = targetDocActionProxy.getDocumentContentSpecifier();
        if (targetServiceModule == null ) {
            // if target doc can not find, then create new target root document
            targetServiceModule = (TargetServiceModel) initCopyToTargetDoc(sourceServiceModel, sourceDocActionProxy, targetDocActionProxy,
                    crossDocConvertRequest, serialLogonInfo.getClient(), 0);
            if (crossDocConvertRequest.getParseBatchGenRequest() != null) {
                // Parse useful info from external gen request to target doc service model
                crossDocConvertRequest.getParseBatchGenRequest().execute(genRequest, targetServiceModule);
            }
            docContentCreateContextList =
                    mergeToTargetRootDoc(docContentCreateContextList, targetDocSpecifier, targetServiceModule,
                            null);
        }
        ServiceEntityNode targetDocument = targetDocSpecifier.getCoreEntity(targetServiceModule);
        Map<Integer, CrossCopyDocConversionConfig> srcCrossCopyDocConversionConfigMap =
                sourceDocActionProxy.getCrossCopyDocConversionConfigMap();
        CrossCopyDocConversionConfig srcToTargetConversionConfig =
                srcCrossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType());
        DocMatItemCreateContext docMatItemCreateContext =
                initConvertToTargetMatItem(sourceMatItemNode, targetDocSpecifier, targetDocument,
                        srcToTargetConversionConfig, genRequest, targetDocOffset, serialLogonInfo);
        if (docMatItemCreateContext == null) {
            return null;
        }
        // In case full custom logic to generate target item service model
        TargetItemServiceModel targetItemServiceModel =
                genDefTargetMatItemServiceModel(sourceServiceModel, sourceMatItemNode, targetServiceModule,
                        sourceDocActionProxy, sourceDocSpecifier, targetDocSpecifier,
                        crossDocConvertRequest, docMatItemCreateContext);
        docMatItemCreateContext.setTargetItemServiceModel(targetItemServiceModel);
        // To merge doc mat item context to root list
        mergeItemCreateContext(docContentCreateContextList, docMatItemCreateContext, targetDocSpecifier);
        return docMatItemCreateContext;
    }

    /**
     * Default Logic to New Target Item Service Module, including the default data conversion
     *
     * @param sourceMatItemNode
     * @param targetServiceModule
     * @param targetDocSpecifier
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public TargetItemServiceModel genDefTargetMatItemServiceModel(SourceServiceModel sourceServiceModel,
                                                                  DocMatItemNode sourceMatItemNode,
                                                                  TargetServiceModel targetServiceModule,
                                                                  DocActionExecutionProxy sourceDocActionProxy,
                                                                  DocumentContentSpecifier sourceDocSpecifier,
                                                                  DocumentContentSpecifier targetDocSpecifier,
                                                                  CrossDocConvertRequest crossDocConvertRequest,
                                                                  DocMatItemCreateContext docMatItemCreateContext)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityConfigureException {
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                sourceDocActionProxy.getCrossCopyDocConversionConfigMap();
        CrossCopyDocConversionConfig crossCopyDocConversionConfig =
                crossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType());
        TargetItemServiceModel targetItemServiceModel =
                (TargetItemServiceModel) serviceModuleProxy.quickCreateServiceModel(
                        targetDocSpecifier.getItemServiceModelClass(), docMatItemCreateContext.getTargetDocMatItemNode(),
                        targetDocSpecifier.getMatItemNodeInstId());

        if (crossCopyDocConversionConfig != null) {
            if (crossCopyDocConversionConfig.getCopyPartyToItemParty() == StandardSwitchProxy.SWITCH_ON) {
                /*
                 * [Step2] Try to copy party information from source doc party to target item party
                 */
                docInvolvePartyProxy.genConvertCrossDocItemParty(sourceDocSpecifier, sourceServiceModel,
                        targetDocSpecifier, docMatItemCreateContext.getTargetDocMatItemNode(), targetItemServiceModel,
                        crossCopyDocConversionConfig.getCrossCopyPartyConversionConfigList());
            } else {
                /*
                 * [Step3] Try to copy party information from source item party to target item party
                 */
                ServiceModule sourceItemServiceModule = sourceDocSpecifier.loadItemServiceModule(sourceMatItemNode);
                docInvolvePartyProxy.genConvertCrossItemParty(sourceDocSpecifier, sourceItemServiceModule,
                        targetDocSpecifier, targetServiceModule, docMatItemCreateContext.getTargetDocMatItemNode(), targetItemServiceModel,true,
                        crossCopyDocConversionConfig.getCrossCopyPartyConversionConfigList());
            }
        }
        CrossDocConvertRequest.IConvertToTarItemServiceModel<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModel =
                crossDocConvertRequest.getConvertToTarItemServiceModel();
        if (convertToTarItemServiceModel != null) {
            targetItemServiceModel = convertToTarItemServiceModel.execute(sourceMatItemNode, targetServiceModule,
                    targetItemServiceModel);
        }
        return targetItemServiceModel;
    }

    public List<ServiceEntityNode> getExistTargetDocForCreationBatch(DocumentMatItemBatchGenRequest genRequest,
                                                                     DocumentContentSpecifier<TargetServiceModel, ?, ?> targetDocSpecifier,
                                                                     List<ServiceEntityNode> allDocMaterialItemList,
                                                                     CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                                     IRetrieveTargetMatItemUUID retrieveTargetMatItemUUID, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, DocActionException, SearchConfigureException {
        List<ServiceEntityNode> targetDocMatItemList =
                getExistTargetDocItemListForCreationBatch(genRequest, allDocMaterialItemList, targetDocSpecifier.getMatItemNodeInstId(),
                        targetDocSpecifier.getDocumentType(), targetDocSpecifier.getDocumentManager(),
                        crossDocConvertRequest, retrieveTargetMatItemUUID, logonInfo);
        if (ServiceCollectionsHelper.checkNullList(targetDocMatItemList)) {
            return null;
        }
        String client = targetDocMatItemList.get(0).getClient();
        List<ServiceEntityNode> rawDocList = new ArrayList<>();
        List<String> rootUUIDList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(targetDocMatItemList)) {
            targetDocMatItemList.forEach(rawSENode -> {
                if (!rootUUIDList.contains(rawSENode.getRootNodeUUID())) {
                    rootUUIDList.add(rawSENode.getRootNodeUUID());
                }
            });
            rawDocList = targetDocSpecifier.getDocumentManager()
                    .getEntityNodeListByMultipleKey(rootUUIDList, IServiceEntityNodeFieldConstant.UUID,
                            targetDocSpecifier.getDocNodeName(), client, null);
        }
        /*
         * [Step3] Traverse and process each production plan and only filter initial status
         */
        List<ServiceEntityNode> resultList = new ArrayList<>();
        if (!ServiceCollectionsHelper.checkNullList(rawDocList)) {
            for (ServiceEntityNode rawSENode : rawDocList) {
                boolean filterResult = crossDocConvertRequest.getFilterTargetDoc().execute(rawSENode, genRequest);
                if (filterResult) {
                    resultList.add(rawSENode);
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(resultList)) {
            return resultList;
        }
        return resultList;
    }

    /**
     * Callback interface to get the target material item UUID from the provided source
     * document material item node.
     */
    public interface IRetrieveTargetMatItemUUID {

        String execute(DocMatItemNode docMatItemNode);

    }

    /**
     * Executor to be exeucted in traverse each source doc mat item
     */
    public interface ISourceDocItemExecutor {

        boolean execute(DocMatItemNode sourceDocMatItemNode, MaterialStockKeepUnit refMaterialSKU,
                        DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy, AtomicInteger targetDocOffset) throws DocActionException;

    }

    /**
     * Logic to get the Existed & Proper Source Doc Material Item list for
     * batch Target order creation.
     * <p>
     *
     * @param allDocMaterialItemList
     * @return
     * @throws ServiceEntityConfigureException
     */
    private List<ServiceEntityNode> getExistTargetDocItemListForCreationBatch(
            DocumentMatItemBatchGenRequest genRequest, List<ServiceEntityNode> allDocMaterialItemList,
                                                                        String targetNodeName, int targetDocType,
                                                                        ServiceEntityManager targetManager,
                                                                        CrossDocConvertRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertRequest,
                                                                        IRetrieveTargetMatItemUUID retrieveTargetMatItemUUID, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, DocActionException, SearchConfigureException {
        List<String> targetDocItemUUIDList = new ArrayList<>();
        if (ServiceCollectionsHelper.checkNullList(allDocMaterialItemList)) {
            return null;
        }
        String client = allDocMaterialItemList.get(0).getClient();
        for (ServiceEntityNode seNode : allDocMaterialItemList) {
            DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
            String targetItemUUID = retrieveTargetMatItemUUID.execute(docMatItemNode);
            if (!ServiceEntityStringHelper.checkNullString(targetItemUUID) &&
                    docMatItemNode.getNextDocType() == targetDocType) {
                targetDocItemUUIDList.add(targetItemUUID);
            }
        }
        /*
         * [Step2]:Get the target material item list
         */
        List<ServiceEntityNode> targetDocMatItemList = new ArrayList<>();
        if (crossDocConvertRequest.getGetTargetDocItemList() != null) {
            DocumentMatItemRawSearchRequest searchRequest = new DocumentMatItemRawSearchRequest();
            searchRequest.setUuidList(targetDocItemUUIDList);
            searchRequest.setGenRequest(genRequest);
            targetDocMatItemList = crossDocConvertRequest.getGetTargetDocItemList().execute(searchRequest, logonInfo);
        } else {
            if (!ServiceCollectionsHelper.checkNullList(targetDocItemUUIDList)) {
                targetDocMatItemList = targetManager.getEntityNodeListByMultipleKey(targetDocItemUUIDList,
                        IServiceEntityNodeFieldConstant.UUID, targetNodeName, client, null);
            }
        }
        return targetDocMatItemList;
    }

    public TargetServiceModel convToDefTargetServiceModel(SourceServiceModel sourceServiceModel,
                                                          TargetServiceModel targetServiceModel,
                                                          DocumentContentSpecifier<SourceServiceModel, ?, ?> sourceDocSpecifier,
                                                          DocumentContentSpecifier<TargetServiceModel, ?, ?> targetDocSpecifier,
                                                          CrossDocConvertRequest.IConvertToTargetDoc convertToTargetDoc,
                                                          CrossCopyDocConversionConfig crossCopyDocConversionConfig)
            throws DocActionException {
        try {
            if (crossCopyDocConversionConfig == null) {
                return targetServiceModel;
            }
            ServiceEntityNode targetDocInstance =
                    targetDocSpecifier.getCoreEntity(targetServiceModel);
            if (convertToTargetDoc != null && sourceServiceModel != null) {
                targetDocInstance = convertToTargetDoc.execute(new CrossDocConvertRequest.ConvertDocumentContext(
                        targetDocInstance, sourceServiceModel, sourceDocSpecifier));
            }
            if (targetDocInstance != null) {
                ServiceModuleProxy.setServiceModelFieldValue(targetServiceModel, targetDocSpecifier.getDocNodeName(),
                        targetDocInstance);
            }
            if (sourceServiceModel != null) {
                // In case source service model provided, then execute copy logic
                docInvolvePartyProxy.genConvertCrossDocParty(sourceDocSpecifier, sourceServiceModel, targetDocSpecifier,
                        targetServiceModel, true, crossCopyDocConversionConfig.getCrossCopyPartyConversionConfigList());
                if (crossCopyDocConversionConfig.getCrossAttachmentFlag() == StandardSwitchProxy.SWITCH_ON) {
                    // in case need to conversion attachment
                    docAttachmentProxy.copyServiceModuleAttachmentNode(sourceServiceModel, targetServiceModel, null,
                            targetDocSpecifier.getDocumentManager());
                }
            }
            return targetServiceModel;
        } catch (InstantiationException | IllegalAccessException | ServiceEntityConfigureException  e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * Document creation context class, to be used for target document creation & store
     */
    public static class DocContentCreateContext {

        private ServiceModule targetRootDocument;

        private int targetDocumentType;

        private List<DocMatItemCreateContext> docMatItemCreateContextList;

        public DocContentCreateContext() {
            this.docMatItemCreateContextList = new ArrayList<>();
        }

        public DocContentCreateContext(ServiceModule targetRootDocument, int targetDocumentType) {
            this.targetRootDocument = targetRootDocument;
            this.targetDocumentType = targetDocumentType;
            this.docMatItemCreateContextList = new ArrayList<>();
        }

        public DocContentCreateContext(ServiceModule targetRootDocument, int targetDocumentType, List<DocMatItemCreateContext> docMatItemCreateContextList) {
            this.targetRootDocument = targetRootDocument;
            this.targetDocumentType = targetDocumentType;
            if (!ServiceCollectionsHelper.checkNullList(docMatItemCreateContextList)) {
                this.docMatItemCreateContextList = docMatItemCreateContextList;
            } else {
                this.docMatItemCreateContextList = new ArrayList<>();
            }
        }

        public ServiceModule getTargetRootDocument() {
            return targetRootDocument;
        }

        public void setTargetRootDocument(ServiceModule targetRootDocument) {
            this.targetRootDocument = targetRootDocument;
        }

        public int getTargetDocumentType() {
            return targetDocumentType;
        }

        public void setTargetDocumentType(int targetDocumentType) {
            this.targetDocumentType = targetDocumentType;
        }

        public List<DocMatItemCreateContext> getDocMatItemCreateContextList() {
            return docMatItemCreateContextList;
        }

        public void setDocMatItemCreateContextList(List<DocMatItemCreateContext> docMatItemCreateContextList) {
            this.docMatItemCreateContextList = docMatItemCreateContextList;
        }
    }

    /**
     * Document item creation context class, to be used for target document item creation & store
     */
    public static class DocMatItemCreateContext {

        private ServiceModule targetItemServiceModel;

        private DocMatItemNode sourceDocMatItemNode;

        private DocMatItemNode targetDocMatItemNode;

        private DocMatItemNode reservedDocMatItemNode;

        private DocMatItemNode prevProfDocMatItemNode;

        private boolean breakFlag;

        public DocMatItemCreateContext() {
            this.breakFlag = false;
        }

        public DocMatItemCreateContext(ServiceModule targetItemServiceModel, DocMatItemNode sourceDocMatItemNode) {
            this.targetItemServiceModel = targetItemServiceModel;
            this.sourceDocMatItemNode = sourceDocMatItemNode;
            this.breakFlag = false;
        }

        public DocMatItemCreateContext(DocMatItemNode targetDocMatItemNode, DocMatItemNode sourceDocMatItemNode) {
            this.targetDocMatItemNode = targetDocMatItemNode;
            this.sourceDocMatItemNode = sourceDocMatItemNode;
            this.breakFlag = false;
        }

        public DocMatItemCreateContext(DocMatItemNode targetDocMatItemNode,DocMatItemNode sourceDocMatItemNode,
                                       DocMatItemNode reservedDocMatItemNode) {
            this.sourceDocMatItemNode = sourceDocMatItemNode;
            this.targetDocMatItemNode = targetDocMatItemNode;
            this.reservedDocMatItemNode = reservedDocMatItemNode;
        }

        public DocMatItemCreateContext(DocMatItemNode targetDocMatItemNode,DocMatItemNode sourceDocMatItemNode,
                                       DocMatItemNode reservedDocMatItemNode, DocMatItemNode prevProfDocMatItemNode) {
            this.sourceDocMatItemNode = sourceDocMatItemNode;
            this.targetDocMatItemNode = targetDocMatItemNode;
            this.reservedDocMatItemNode = reservedDocMatItemNode;
            this.prevProfDocMatItemNode = prevProfDocMatItemNode;
        }

        public ServiceModule getTargetItemServiceModel() {
            return targetItemServiceModel;
        }

        public void setTargetItemServiceModel(ServiceModule targetItemServiceModel) {
            this.targetItemServiceModel = targetItemServiceModel;
        }

        public DocMatItemNode getSourceDocMatItemNode() {
            return sourceDocMatItemNode;
        }

        public void setSourceDocMatItemNode(DocMatItemNode sourceDocMatItemNode) {
            this.sourceDocMatItemNode = sourceDocMatItemNode;
        }

        public boolean getBreakFlag() {
            return breakFlag;
        }

        public void setBreakFlag(boolean breakFlag) {
            this.breakFlag = breakFlag;
        }

        public DocMatItemNode getTargetDocMatItemNode() {
            return targetDocMatItemNode;
        }

        public void setTargetDocMatItemNode(DocMatItemNode targetDocMatItemNode) {
            this.targetDocMatItemNode = targetDocMatItemNode;
        }

        public DocMatItemNode getPrevProfDocMatItemNode() {
            return prevProfDocMatItemNode;
        }

        public void setPrevProfDocMatItemNode(DocMatItemNode prevProfDocMatItemNode) {
            this.prevProfDocMatItemNode = prevProfDocMatItemNode;
        }

        public DocMatItemNode getReservedDocMatItemNode() {
            return reservedDocMatItemNode;
        }

        public void setReservedDocMatItemNode(DocMatItemNode reservedDocMatItemNode) {
            this.reservedDocMatItemNode = reservedDocMatItemNode;
        }

        public boolean isBreakFlag() {
            return breakFlag;
        }
    }

    public List<DocContentCreateContext> mergeToTargetRootDoc(List<DocContentCreateContext> docContentCreateContextList,
                                                              DocumentContentSpecifier targetDocSpecifier,
                                                              ServiceModule targetRootDocument,
                                                              List<DocMatItemCreateContext> docMatItemCreateContextList) {
        Objects.requireNonNull(docContentCreateContextList);
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            DocContentCreateContext docContentCreateContext =
                    new DocContentCreateContext(targetRootDocument, targetDocSpecifier.getDocumentType(), docMatItemCreateContextList);
            docContentCreateContextList.add(docContentCreateContext);
            return docContentCreateContextList;
        }
        List<DocContentCreateContext> existedDocContentCreateContextList =
                ServiceCollectionsHelper.filterListOnline(docContentCreateContextList, docContentCreateContext -> {
                    ServiceModule targetServiceModel = docContentCreateContext.getTargetRootDocument();
                    ServiceEntityNode tempTargetSENode = targetDocSpecifier.getCoreEntity(targetServiceModel);
                    ServiceEntityNode targetSENode = targetDocSpecifier.getCoreEntity(targetRootDocument);
                    return tempTargetSENode.getUuid().equals(targetSENode.getUuid());
                }, true);
        if (ServiceCollectionsHelper.checkNullList(existedDocContentCreateContextList)) {
            DocContentCreateContext docContentCreateContext =
                    new DocContentCreateContext(targetRootDocument, targetDocSpecifier.getDocumentType(), docMatItemCreateContextList);

            docContentCreateContextList.add(docContentCreateContext);
            return docContentCreateContextList;
        } else {
            DocContentCreateContext existedDocCreateContext = existedDocContentCreateContextList.get(0);
            ServiceCollectionsHelper.mergeToList(existedDocCreateContext.getDocMatItemCreateContextList(),
                    docMatItemCreateContextList, object -> {
                        DocMatItemCreateContext docMatItemCreateContext = (DocMatItemCreateContext) object;
                        ServiceModule targetDocItemServiceModel = docMatItemCreateContext.getTargetItemServiceModel();
                        try {
                            ServiceEntityNode tempItemSENode =
                                    DocumentContentSpecifier.getDocMatItemNodeFromItemServiceModule(
                                            targetDocItemServiceModel);
                            return tempItemSENode.getUuid();
                        } catch (IllegalAccessException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        }
                        return null;
                    });
        }
        return existedDocContentCreateContextList;
    }

    public List<DocContentCreateContext> mergeItemCreateContext(
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemCreateContext docMatItemCreateContext,
            DocumentContentSpecifier targetDocSpecifier) {
        Objects.requireNonNull(docMatItemCreateContext.getTargetDocMatItemNode());
        String parentNodeUUID = docMatItemCreateContext.getTargetDocMatItemNode().getParentNodeUUID();
        List<DocContentCreateContext> existedDocContentCreateContextList =
                ServiceCollectionsHelper.filterListOnline(docContentCreateContextList, docContentCreateContext -> {
                    ServiceModule targetServiceModel = docContentCreateContext.getTargetRootDocument();
                    ServiceEntityNode tempTargetSENode = targetDocSpecifier.getCoreEntity(targetServiceModel);
                    return tempTargetSENode.getUuid().equals(parentNodeUUID);
                }, true);
        if (ServiceCollectionsHelper.checkNullList(existedDocContentCreateContextList)) {
            return docContentCreateContextList;
        }
        DocContentCreateContext existedDocCreateContext = existedDocContentCreateContextList.get(0);
        ServiceCollectionsHelper.mergeToListUnit(existedDocCreateContext.getDocMatItemCreateContextList(),
                docMatItemCreateContext, object -> {
                    DocMatItemCreateContext tmpItemCreateContext = (DocMatItemCreateContext) object;
                    ServiceModule targetDocItemServiceModel = tmpItemCreateContext.getTargetItemServiceModel();
                    try {
                        ServiceEntityNode tempItemSENode =
                                DocumentContentSpecifier.getDocMatItemNodeFromItemServiceModule(
                                        targetDocItemServiceModel);
                        return tempItemSENode.getUuid();
                    } catch (IllegalAccessException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return null;
                });
        return docContentCreateContextList;
    }

    public static List<ServiceEntityNode> filterOutDocMatItemList(List<DocContentCreateContext> docContentCreateContextList, int docFlowDirection) {
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            return null;
        }
        List<ServiceEntityNode> resultDocMatItemList = new ArrayList<>();
        for (DocContentCreateContext docContentCreateContext: docContentCreateContextList) {
            List<DocMatItemCreateContext> docMatItemCreateContextList =
                    docContentCreateContext.getDocMatItemCreateContextList();
            if (ServiceCollectionsHelper.checkNullList(docMatItemCreateContextList)) {
                continue;
            }
            List<ServiceEntityNode> tmpDocMatItemList = new ArrayList<>();
            if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF) {
                tmpDocMatItemList =
                        docMatItemCreateContextList.stream().map(DocMatItemCreateContext::getPrevProfDocMatItemNode).collect(
                                Collectors.toList());
            }
            if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV) {
                tmpDocMatItemList =
                        docMatItemCreateContextList.stream().map(DocMatItemCreateContext::getSourceDocMatItemNode).collect(
                                Collectors.toList());
            }
            if (docFlowDirection == StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT) {
                tmpDocMatItemList =
                        docMatItemCreateContextList.stream().map(DocMatItemCreateContext::getTargetDocMatItemNode).collect(
                                Collectors.toList());
            }
            if (!ServiceCollectionsHelper.checkNullList(tmpDocMatItemList)) {
                resultDocMatItemList.addAll(tmpDocMatItemList);
            }
        }
        return resultDocMatItemList;
    }

    /**
     * Store creation context information to DB, it includes created target document, updated reserved document or updated source document .
     * @param docContentCreateContextList
     * @param serialLogonInfo
     * @throws ServiceModuleProxyException
     * @throws ServiceEntityConfigureException
     */
    public void storeContext(List<DocContentCreateContext> docContentCreateContextList,
                             SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, DocActionException {
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            return;
        }
        for (DocContentCreateContext docContentCreateContext : docContentCreateContextList) {
            // We should make sure the doc mat item creation context is not null
            if (ServiceCollectionsHelper.checkNullList(docContentCreateContext.getDocMatItemCreateContextList())) {
                continue;
            }
            DocumentContentSpecifier targetDocSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(docContentCreateContext.getTargetDocumentType());
            ServiceModule targetDocServiceModel = docContentCreateContext.getTargetRootDocument();
            targetDocSpecifier.getDocumentManager()
                    .updateServiceModuleWithDelete(targetDocServiceModel.getClass(), targetDocServiceModel,
                            serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID(),
                            targetDocSpecifier.getDocNodeInstId(), targetDocSpecifier.getDocServiceUIModelExtension());
            for (DocMatItemCreateContext docMatItemCreateContext : docContentCreateContext.getDocMatItemCreateContextList()) {
                ServiceModule targetItemServiceModel = docMatItemCreateContext.getTargetItemServiceModel();
                if (targetItemServiceModel != null) {
                    targetDocSpecifier.getDocumentManager()
                            .updateServiceModule(targetItemServiceModel.getClass(), targetItemServiceModel,
                                    serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
                }
                DocMatItemNode sourceDocMatItemNode = docMatItemCreateContext.getSourceDocMatItemNode();
                if (sourceDocMatItemNode != null) {
                    DocumentContentSpecifier sourceDocSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(sourceDocMatItemNode.getHomeDocumentType());
                    sourceDocSpecifier.getDocumentManager()
                            .updateSENode(sourceDocMatItemNode, serialLogonInfo.getRefUserUUID(),
                                    serialLogonInfo.getResOrgUUID());
                }
                DocMatItemNode prevProfDocMatItemNode = docMatItemCreateContext.getPrevProfDocMatItemNode();
                if (prevProfDocMatItemNode != null) {
                    DocumentContentSpecifier prevProfDocSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(prevProfDocMatItemNode.getHomeDocumentType());
                    prevProfDocSpecifier.getDocumentManager()
                            .updateSENode(prevProfDocMatItemNode, serialLogonInfo.getRefUserUUID(),
                                    serialLogonInfo.getResOrgUUID());
                }
            }
        }
    }

    public static ServiceModule filterTargetRootDoc (
            CrossDocConvertRequest.IFilterRootDocContext filterRootDocContext,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode prevProfMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        DocContentCreateContext docContentCreateContext =
                filterTargetRootDocContext(filterRootDocContext, docContentCreateContextList, prevProfMatItemNode,
                        sourceMatItemNode);
        if (docContentCreateContext != null) {
            return docContentCreateContext.getTargetRootDocument();
        }
        return null;
    }

    public static DocContentCreateContext filterTargetRootDocContext(
            CrossDocConvertRequest.IFilterRootDocContext filterRootDocContext,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode prevProfMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            return null;
        }
        if (filterRootDocContext != null) {
            for (DocContentCreateContext docContentCreateContext : docContentCreateContextList) {
                if (filterRootDocContext.execute(docContentCreateContext, prevProfMatItemNode,
                        sourceMatItemNode)) {
                    return docContentCreateContext;
                }
            }
        } else {
            // In case no custom filter logic, then return the 1st member.
            return docContentCreateContextList.get(0);
        }
        return null;
    }
}
