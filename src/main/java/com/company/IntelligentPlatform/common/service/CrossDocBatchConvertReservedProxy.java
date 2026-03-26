package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoManager;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CrossDocBatchConvertReservedProxy<SourceServiceModel extends ServiceModule, TargetServiceModel extends ServiceModule, TargetItem extends DocMatItemNode, TargetItemServiceModel extends ServiceModule>
        extends CrossDocBatchConvertProxy<SourceServiceModel, TargetServiceModel, TargetItem, TargetItemServiceModel> {

    Logger logger = LoggerFactory.getLogger(CrossDocBatchConvertReservedProxy.class);

    @Autowired
    protected ReserveDocItemProxy reserveDocItemProxy;

    @Autowired
    protected PrevNextDocItemProxy prevNextDocItemProxy;

    /**
     * Creates a target document in service module from a reserved document, including the target document's root node
     * and material item list, involved parties, attachments, etc. derived from the reserved document. The method manages the transformation
     * and persistence of document data while handling various document types.
     *
     * @param reservedServiceModel The reserved document service module.
     * @param reservedDocType The reserved document type code.
     * @param sourceDocType The source document type code.
     * @param selectedToReserveDocMatItemList List of material items selected from the reserved document.
     * @param crossDocConvertReservedRequest Contains rules and execution methods for converting reserved document items
     *                                        into target document items.
     * @param inputOption Options specifying input behavior during the document creation process.
     * @param logonInfo Session or authentication information to maintain security and logging.
     * @return List of contexts representing the newly created document contents. Each context contains newly created target document information
     *      as well as updated source and reserved document.
     * @throws ServiceEntityConfigureException If there is an issue configuring entities or models.
     * @throws ServiceModuleProxyException If problems occur during proxy module interactions.
     * @throws DocActionException If errors arise during document actions, such as creation or manipulation.
     */
    @Transactional
    public List<DocContentCreateContext> createTargetBatchDocFromReservedDoc(SourceServiceModel reservedServiceModel,
                                                                             int reservedDocType, int sourceDocType,
                                                                             List<ServiceEntityNode> selectedToReserveDocMatItemList,
                                                                             CrossDocConvertReservedRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertReservedRequest,
                                                                             DocumentMatItemBatchGenRequest genRequest,
                                                                             CrossDocConvertReservedRequest.InputOption inputOption,
                                                                             LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {
        DocActionExecutionProxy<TargetServiceModel, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(
                        crossDocConvertReservedRequest.getTargetDocType());
        TargetServiceModel targetDocServiceModel = (TargetServiceModel) getExistedTargetDocServiceModule(genRequest,
                crossDocConvertReservedRequest, reservedServiceModel, reservedDocType,
                DocMatItemNode::getNextDocMatItemUUID, logonInfo);
        List<DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        loadFromToReserveDocFramework(reservedDocType, sourceDocType, selectedToReserveDocMatItemList,
                crossDocConvertReservedRequest,
                (requestCoreUnit, reservedDocMatItemNode, sourceDocMatItemNode, reservedDocActionProxy,
                 sourceDocActionProxy, targetDocOffset) -> {
                    // Create target type document material item service module instance by source and reserved doc material item.
                    if (crossDocConvertReservedRequest.getGenerateTargetItemServiceModelReserved() != null) {
                        DocMatItemCreateContext docMatItemCreateContext =
                                crossDocConvertReservedRequest.getGenerateTargetItemServiceModelReserved()
                                        .execute(reservedDocMatItemNode, sourceDocMatItemNode,
                                                docContentCreateContextList);
                        // in case need to break loops
                        return !docMatItemCreateContext.getBreakFlag();
                    } else {
                        try {
                            DocMatItemCreateContext docMatItemCreateContext =
                                    genDefTargetMatItemServiceModelReserved(reservedServiceModel,
                                            reservedDocMatItemNode, sourceDocMatItemNode, docContentCreateContextList,
                                            targetDocServiceModel, reservedDocActionProxy, targetDocActionProxy, sourceDocActionProxy,
                                            requestCoreUnit, crossDocConvertReservedRequest, genRequest, targetDocOffset, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                            if (docMatItemCreateContext == null) {
                                return true; // continue, if current item is not created/updated.
                            }
                            // in case need to break loops on purpose
                            return !docMatItemCreateContext.getBreakFlag();
                        } catch (ServiceModuleProxyException | DocActionException | ServiceEntityConfigureException | IllegalAccessException e) {
                            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                        }
                    }
                    return true;
                }, inputOption, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        // Store the newly created target document, as well as updated source document and reserved document into DB.
        storeContext(docContentCreateContextList,LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    /**
     * Retrieves a list of `sourceDocMatItem` from a given list of `reservedDocMatItem`.
     *
     * This process involves calling the <code>loadFromToReserveDocFramework</code> method, which uses a framework to load the appropriate `sourceDocMatItem` list based
     * on the provided reserved document item list.
     * Secondly, The logic within `crossDocConvertReservedRequest` determines which `sourceDocMatItem` instances are selected according to the given `reservedDocMatItem` list.
     * which is executed at callback method as instance of <code>ISourceDocItemExecutor</code> at each `sourceDocMatItem` instance which is used as instance of `sourceDocMatItem` instance.
     *
     *
     * @param reservedDocType The type of the reserved document.
     * @param sourceDocType The type of the source document.
     * @param selectedToReserveDocMatItemList A list of document items selected for reservation.
     * @param crossDocConvertReservedRequest Contains logic to select `sourceDocMatItem` instances.
     * @param inputOption Options for input processing.
     * @param serialLogonInfo Logon information for serialization.
     * @return A list of `ReservedDocItemContext` representing the source document items.
     * @throws ServiceEntityConfigureException If there is a configuration issue with the service entity.
     * @throws DocActionException If there is an error during document action processing.
     */
    public List<ReseveredDocItemContext> getSourceItemListFromReserved(int reservedDocType, int sourceDocType,
                                                                       List<ServiceEntityNode> selectedToReserveDocMatItemList,
                                                                       CrossDocConvertReservedRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertReservedRequest,
                                                                       CrossDocConvertReservedRequest.InputOption inputOption,
                                                                       SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
        List<ReseveredDocItemContext> reservedDocItemContextList = new ArrayList<>();
        loadFromToReserveDocFramework(reservedDocType, sourceDocType, selectedToReserveDocMatItemList,
                crossDocConvertReservedRequest,
                (requestCoreUnit, reservedDocMatItemNode, sourceDocMatItemNode, reservedDocActionProxy,
                 sourceDocActionProxy, targetDocOffset) -> {
                    ReseveredDocItemContext reseveredDocItemContext =
                            filteredSourceMatItemNode(reservedDocMatItemNode, sourceDocMatItemNode, requestCoreUnit,
                                    crossDocConvertReservedRequest);
                    if (reseveredDocItemContext == null) {
                        return true;
                    }
                    reservedDocItemContextList.add(reseveredDocItemContext);
                    return true;
                }, inputOption, serialLogonInfo);
        return reservedDocItemContextList;
    }

    /**
     * This method serves as a framework to obtain the relevant `sourceDocMatItem` list from a given `reservedDocMatItem` list.
     * It includes a callback method: `sourceDocItemExecutor`, an instance of <code>ISourceDocItemExecutor</code>, which can be executed for various use cases when processing each `sourceDocMatItem` instance.
     *
     * The process is as follows:
     * 1. Retrieve all materials into a comprehensive material list based on the provided `reservedDocMatItem` list.
     * 2. Obtain all potential source material items.
     * 3. Filter the potential source material items.
     * 4. Iterate through the filtered source material item list; for each `sourceDocMatItem` instance,
     * invoke the callback method instance of <code>ISourceDocItemExecutor</code> to implement different use cases.
     *
     * Two common use cases for this framework method are:
     * 1. Generating the target document material item list using the provided reserved document material item list, identifying the relevant source material items,
     * and subsequently creating the target material item list, such as creating an outbound delivery as a target document item from warehouse items as source material items.
     * 2. Obtaining the relevant source material item list using the provided reserved document material item list.
     *
     * @param reservedDocType The reserved document type, for example, purchase contract or sales contract type.
     * @param sourceDocType The source document type, such as warehouse store item type.
     * @param selectedToReserveDocMatItemList The list of selected reserved document material items, such as a selected sales contract item list.
     * @param crossDocConvertReservedRequest An instance of <code>CrossDocConvertReservedRequest</code> that contains the necessary information for the conversion process.
     * @param sourceDocItemExecutor This is the key executor to be executed when looping each source material item.
     *                              This is an instance of <code>ISourceDocItemExecutor</code> that is executed for various use cases when processing each `sourceDocMatItem` instance.
     * @param inputOption An instance of <code>CrossDocConvertReservedRequest.InputOption</code> that contains the necessary information for the conversion process.
     * @param serialLogonInfo System login information.
     * @throws ServiceEntityConfigureException If there is an error in configuring the service entity.
     * @throws DocActionException If there is an error during document action processing.
     */
    public void loadFromToReserveDocFramework(int reservedDocType, int sourceDocType,
                                             List<ServiceEntityNode> selectedToReserveDocMatItemList,
                                             CrossDocConvertReservedRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertReservedRequest,
                                             ISourceDocItemExecutor sourceDocItemExecutor,
                                             CrossDocConvertReservedRequest.InputOption inputOption,
                                             SerialLogonInfo serialLogonInfo)
            throws ServiceEntityConfigureException, DocActionException {
        /*
         * [Step1] Get all the possible source document list according to given reserved document item list
         */
        if (ServiceCollectionsHelper.checkNullList(selectedToReserveDocMatItemList)) {
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        // Build the Material SKU list according to given reserved document item list
        List<ServiceEntityNode> rawMaterialSKUList =
                docFlowProxy.getRefMaterialSKUList(selectedToReserveDocMatItemList, null, serialLogonInfo.getClient());
        DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocType);
        DocActionExecutionProxy<?, ?, ?> reservedDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(reservedDocType);
        List<ServiceEntityNode> allSourceMatItemListBySelectedReserved =
                crossDocConvertReservedRequest.getGetALLSourceMatItemListBySelectedReserved()
                        .execute(selectedToReserveDocMatItemList, reservedDocType);
        /*
         * [Step2] Loop selected reserved doc item list, for each reserved doc item instance, filter out the
         * suitable source doc item list
         */
        AtomicInteger targetDocOffset = new AtomicInteger();
        ServiceCollectionsHelper.traverseListInterrupt(selectedToReserveDocMatItemList, seNode -> {
            DocMatItemNode reservedDocMatItemNode = (DocMatItemNode) seNode;
            List<ServiceEntityNode> filteredSourceMatItemList = allSourceMatItemListBySelectedReserved;
            try {
                if (crossDocConvertReservedRequest.getGetSourceMatItemListBySelectedReserved() != null) {
                    filteredSourceMatItemList =
                            crossDocConvertReservedRequest.getGetSourceMatItemListBySelectedReserved()
                                    .execute(reservedDocMatItemNode, allSourceMatItemListBySelectedReserved);
                }
                filteredSourceMatItemList =
                        DocFlowProxy.sortDocMatItemList(filteredSourceMatItemList, reservedDocMatItemNode.getUuid());
                if (ServiceCollectionsHelper.checkNullList(filteredSourceMatItemList)) {
                    throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
                }
            } catch (ServiceEntityConfigureException e) {
                return true; // continue
            }
            MaterialStockKeepUnit tempSKU = null;
            try {
                tempSKU = materialStockKeepUnitManager.getRefTemplateMaterialSKU(
                        reservedDocMatItemNode.getRefMaterialSKUUUID(), reservedDocMatItemNode.getClient());
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
            StorageCoreUnit requestCoreUnit =
                    new StorageCoreUnit(tempSKU.getUuid(), reservedDocMatItemNode.getRefUnitUUID(),
                            reservedDocMatItemNode.getAmount());
            StorageCoreUnit requestCoreUnitBack = (StorageCoreUnit) requestCoreUnit.clone();
            /*
             * [Step3] Iterate through the filtered source material item list; for each `sourceDocMatItem` instance,
             * invoke the callback method instance of <code>ISourceDocItemExecutor</code> to implement different use cases.
             */
            ServiceCollectionsHelper.traverseListInterrupt(filteredSourceMatItemList, sourceSENode -> {
                DocMatItemNode sourceDocMatItemNode = (DocMatItemNode) sourceSENode;
                MaterialStockKeepUnit refMaterialSKU =
                        (MaterialStockKeepUnit) ServiceCollectionsHelper.filterSENodeOnline(
                                sourceDocMatItemNode.getRefMaterialSKUUUID(), rawMaterialSKUList);
                if (crossDocConvertReservedRequest.getFilterSelectedMatItem() != null) {
                    boolean filterItem = crossDocConvertReservedRequest.getFilterSelectedMatItem()
                            .execute(sourceDocMatItemNode, refMaterialSKU, null);
                    if (!filterItem) {
                        return true;
                    }
                }
                if (sourceDocItemExecutor != null) {
                    return sourceDocItemExecutor.execute(requestCoreUnitBack, reservedDocMatItemNode,
                            sourceDocMatItemNode, reservedDocActionProxy, sourceDocActionProxy, targetDocOffset);
                }
                return true;
            });
            return true;
        });
    }

    private boolean _checkNonEmptyNextItem(DocMatItemNode sourceDocMatItemNode, String reservedMatItemUUID){
        String nextDocMatItemUUID = sourceDocMatItemNode.getNextDocMatItemUUID();
        if (ServiceEntityStringHelper.checkNullString(nextDocMatItemUUID) ) {
            return false;
        }
        return !nextDocMatItemUUID.equals(reservedMatItemUUID);
    }

    /**
     * Executor to be exeucted in traverse each source doc mat item
     */
    public interface ISourceDocItemExecutor {

        boolean execute(StorageCoreUnit requestCoreUnit, DocMatItemNode reservedDocMatItemNode,
                        DocMatItemNode sourceDocMatItemNode, DocActionExecutionProxy<?, ?, ?> reservedDocActionProxy,
                        DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy, AtomicInteger targetDocOffset) throws DocActionException;

    }

    /**
     * Default Logic to Create Target type Document material Item Service Module.
     * it includes the following working process:
     *  1. Try to filter or get the existed target document root node, if not exist, then create a new target type root service model.
     *  2. Get the core target document root node and getting the cross-copy document configuration.
     *  3. Call method: initConvertToTargetMatItem to create the target document material item and copy the data from source document as well as reserved material item.
     *  4. Using the previous cross-copy document configuration to create involve party node and attachment node.
     *
     *
     *
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public DocMatItemCreateContext genDefTargetMatItemServiceModelReserved(ServiceModule reservedServiceModel,
                                                                           DocMatItemNode reservedMatItemNode,
                                                                           DocMatItemNode sourceMatItemNode,
                                                                           List<DocContentCreateContext> docContentCreateContextList,
                                                                           TargetServiceModel targetServiceModule,
                                                                           DocActionExecutionProxy<?, ?, ?> reservedDocActionProxy,
                                                                           DocActionExecutionProxy<?, ?, ?> targetDocActionProxy,
                                                                           DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy,
                                                                           StorageCoreUnit requestCoreUnitContext,
                                                                           CrossDocConvertReservedRequest crossDocConvertReservedRequest,
                                                                           DocumentMatItemBatchGenRequest genRequest,
                                                                           AtomicInteger targetDocOffset,
                                                                           SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityConfigureException,
            IllegalAccessException {
        if (targetServiceModule == null) {
            targetServiceModule = (TargetServiceModel) filterTargetRootDoc(crossDocConvertReservedRequest.getFilterRootDocContextReserved(), docContentCreateContextList,
                    reservedMatItemNode, sourceMatItemNode);
        }
        DocumentContentSpecifier targetDocSpecifier = targetDocActionProxy.getDocumentContentSpecifier();
        DocumentContentSpecifier sourceDocSpecifier = sourceDocActionProxy.getDocumentContentSpecifier();
        if (targetServiceModule == null) {
            SourceServiceModel sourceServiceModel = (SourceServiceModel) sourceDocSpecifier.loadServiceModule(
                    sourceDocSpecifier.getParentDocUUIDByMatItem(sourceMatItemNode), sourceMatItemNode.getClient());
            targetServiceModule =
                    (TargetServiceModel) initCopyToTargetServiceModelWrapper(sourceServiceModel,
                            sourceMatItemNode,
                            sourceDocActionProxy,targetDocActionProxy,
                            crossDocConvertReservedRequest, targetDocOffset);
            docContentCreateContextList =
                    mergeToTargetRootDoc(docContentCreateContextList, targetDocSpecifier, targetServiceModule,
                            null);
        }
        ServiceEntityNode targetDocument = targetDocSpecifier.getCoreEntity(targetServiceModule);
        Map<Integer, CrossCopyDocConversionConfig> srcCrossCopyDocConversionConfigMap =
                sourceDocActionProxy.getCrossCopyDocConversionConfigMap();
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                reservedDocActionProxy.getCrossCopyDocConversionConfigMap();
        CrossCopyDocConversionConfig reservedToTargetConversionConfig =
                crossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType());
        CrossCopyDocConversionConfig srcToReservedConversionConfig =
                srcCrossCopyDocConversionConfigMap.get(reservedDocActionProxy.getDocumentContentSpecifier().getDocumentType());
        CrossCopyDocConversionConfig srcToTargetConversionConfig =
                srcCrossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType());
        DocMatItemCreateContext docMatItemCreateContext =
                initConvertToTargetMatItem(reservedMatItemNode, sourceMatItemNode, targetDocActionProxy, targetDocument,
                        requestCoreUnitContext, reservedToTargetConversionConfig, srcToReservedConversionConfig,
                        srcToTargetConversionConfig,genRequest,
                        crossDocConvertReservedRequest.getConvertToTargetItemReserved(),
                        serialLogonInfo);
        if (docMatItemCreateContext == null) {
            return null;
        }
        DocumentContentSpecifier<?, ?, ?> reservedDocSpecifier = reservedDocActionProxy.getDocumentContentSpecifier();
        TargetItemServiceModel targetItemServiceModel =
                (TargetItemServiceModel) serviceModuleProxy.quickCreateServiceModel(
                        targetDocSpecifier.getItemServiceModelClass(),
                        docMatItemCreateContext.getTargetDocMatItemNode(), targetDocSpecifier.getMatItemNodeInstId());
        if (reservedToTargetConversionConfig != null) {
            if (reservedToTargetConversionConfig.getCopyPartyToItemParty() == StandardSwitchProxy.SWITCH_ON) {
                /*
                 * [Step2] Try to copy party information from source doc party to target item party
                 */
                docInvolvePartyProxy.genConvertCrossDocItemParty(reservedDocSpecifier, reservedServiceModel,
                        targetDocSpecifier, docMatItemCreateContext.getTargetDocMatItemNode(), targetItemServiceModel,
                        reservedToTargetConversionConfig.getCrossCopyPartyConversionConfigList());
            } else {
                /*
                 * [Step3] Try to copy party information from source item party to target item party
                 */
                ServiceModule sourceItemServiceModule = sourceDocSpecifier.loadItemServiceModule(sourceMatItemNode);
                docInvolvePartyProxy.genConvertCrossItemParty(sourceDocSpecifier, sourceItemServiceModule,
                        targetDocSpecifier, targetServiceModule, docMatItemCreateContext.getTargetDocMatItemNode(),
                        targetItemServiceModel, true,
                        reservedToTargetConversionConfig.getCrossCopyPartyConversionConfigList());
            }
        }
        CrossCopyDocConversionConfig reservedToSourceConversionConfig =
                crossCopyDocConversionConfigMap.get(sourceDocSpecifier.getDocumentType());
        if (reservedToSourceConversionConfig != null) {
            ServiceModule sourceMatItemServiceModel = sourceDocSpecifier.loadItemServiceModule(sourceMatItemNode);
            if (reservedToSourceConversionConfig.getCopyPartyToItemParty() == StandardSwitchProxy.SWITCH_ON) {
                /*
                 * [Step2] Try to copy party information from source doc party to target item party
                 */
                docInvolvePartyProxy.genConvertCrossDocItemParty(reservedDocSpecifier, reservedServiceModel,
                        sourceDocSpecifier, sourceMatItemNode, sourceMatItemServiceModel,
                        reservedToTargetConversionConfig.getCrossCopyPartyConversionConfigList());
            } else {
                /*
                 * [Step3] Try to copy party information from source item party to target item party
                 */
                docInvolvePartyProxy.genConvertCrossItemParty(sourceDocSpecifier, sourceMatItemServiceModel,
                        sourceDocSpecifier, targetServiceModule, sourceMatItemNode, sourceMatItemServiceModel, true,
                        reservedToSourceConversionConfig.getCrossCopyPartyConversionConfigList());
            }
        }
        CrossDocConvertReservedRequest.IConvertToTarItemServiceModelReserved<TargetServiceModel,
                TargetItemServiceModel> convertToTarItemServiceModelReserved =
                crossDocConvertReservedRequest.getConvertToTarItemServiceModelReserved();
        if(convertToTarItemServiceModelReserved != null){
            targetItemServiceModel = convertToTarItemServiceModelReserved.execute(sourceMatItemNode,
                    reservedMatItemNode, targetServiceModule,
                    targetItemServiceModel);
        }
        docMatItemCreateContext.setTargetItemServiceModel(targetItemServiceModel);
        // To merge doc mat item context to root list
        mergeItemCreateContext(docContentCreateContextList, docMatItemCreateContext, targetDocSpecifier);
        return docMatItemCreateContext;
    }

    /**
     * Initializes the conversion of source and reserved material item nodes into a new target material item node.
     * Includes data validation, relationship updates, and data transformation logic.
     * Returns null if the provided source material item node is not valid.
     *
     * @param reservedMatItemNode                The reserved material item node (optional) used in the conversion process.
     * @param sourceMatItemNode                  The source material item node to be converted into the target material item node.
     * @param targetDocActionProxy               Target document action proxy.
     * @param targetDocument                     The target document where the new material item will be created.
     * @param requestCoreUnitContext             The core context of the request, containing storage-related metadata.
     * @param reservedToTargetConversionConfig   Configuration for converting the reserved item to the target item.
     * @param srcToReservedConversionConfig      Configuration for mapping the source item to the reserved item.
     * @param srcToTargetCrossCopyDocConversionConfig
     *                                           Configuration for mapping the source item to the target item directly.
     * @param convertToTargetItemReserved        A callback interface for custom logic to convert the reserved item into the target item.
     * @param serialLogonInfo                    Logon information required for serializing and saving operations.
     * @return A context object encapsulating the newly created target item node and its relationship with the source node,
     *         or null if the source node is invalid.
     * @throws DocActionException                If an error occurs during the document conversion or update process.
     */
    public DocMatItemCreateContext initConvertToTargetMatItem(DocMatItemNode reservedMatItemNode,
                                                              DocMatItemNode sourceMatItemNode,
                                                              DocActionExecutionProxy<?, ?, ?> targetDocActionProxy,
                                                              ServiceEntityNode targetDocument,
                                                              StorageCoreUnit requestCoreUnitContext,
                                                              CrossCopyDocConversionConfig reservedToTargetConversionConfig,
                                                              CrossCopyDocConversionConfig srcToReservedConversionConfig,
                                                              CrossCopyDocConversionConfig srcToTargetCrossCopyDocConversionConfig,
                                                              DocumentMatItemBatchGenRequest genRequest,
                                                              CrossDocConvertReservedRequest.IConvertToTargetItemReserved<TargetItem> convertToTargetItemReserved, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if (sourceMatItemNode != null) {
            DocumentContentSpecifier targetDocSpecifier = targetDocActionProxy.getDocumentContentSpecifier();
            TargetItem targetMatItemNode = (TargetItem) targetDocSpecifier.createItem(targetDocument);
            // [Step 1] Pre-clear: clean any relationships between the reserved item and the source item, preparing for conversion.
            initPreUpdateSrcMatItemTarget(reservedMatItemNode, sourceMatItemNode, srcToReservedConversionConfig, serialLogonInfo);

            // [Step 2] Establish a forward relationship between the source item and the newly created target item.
            docFlowProxy.buildItemPrevNextRelationship(sourceMatItemNode, targetMatItemNode, genRequest, serialLogonInfo);

            // [Step 3] Post-update target material
            // Post-update item from source material item
            targetMatItemNode = initPostUpdateTargetMatItem(sourceMatItemNode, targetMatItemNode, srcToTargetCrossCopyDocConversionConfig, serialLogonInfo);
            // Post-update target material item from reserved material item.
            targetMatItemNode = initPostUpdateTargetMatItemReserved(reservedMatItemNode, targetMatItemNode,
                    reservedToTargetConversionConfig, serialLogonInfo);

            DocMatItemCreateContext docMatItemCreateContext =
                    new DocMatItemCreateContext(targetMatItemNode, sourceMatItemNode, reservedMatItemNode);
            // Apply custom logic for converting the reserved item into the target item (if custom callback is provided).
            if (convertToTargetItemReserved != null) {
                docMatItemCreateContext =
                        convertToTargetItemReserved.execute(docMatItemCreateContext, reservedMatItemNode,
                                requestCoreUnitContext);
            }
            return docMatItemCreateContext;
        }
        return null;
    }

    /**
     * Update logic when new target doc item is created, need to update / clean relationship for reserved mat item
     * @param reservedMatItemNode: Main Doc Mat item, which is reserved doc mat item to source
     * @param sourceMatItemNode: newly created target doc mat item
     * @param srcToReservedConversionConfig
     */
    public void initPreUpdateSrcMatItemTarget(DocMatItemNode reservedMatItemNode, DocMatItemNode sourceMatItemNode,
                                                         CrossCopyDocConversionConfig srcToReservedConversionConfig,
                                              SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if(srcToReservedConversionConfig == null){
            return ;
        }
        // clean old prev-next relationship from reserved item
        prevNextDocItemProxy.cleanPrevNext(sourceMatItemNode,
                reservedMatItemNode,serialLogonInfo);
        if(srcToReservedConversionConfig.getCleanReserveOnSrc() == StandardSwitchProxy.SWITCH_ON){
            // need to clean the previous reserve relationship
            reserveDocItemProxy.freeReserve(sourceMatItemNode.getUuid(),  sourceMatItemNode.getHomeDocumentType(),
                    reservedMatItemNode.getUuid(),
                    reservedMatItemNode.getHomeDocumentType(),serialLogonInfo);
        }
    }

    /**
     * Executes post-update logic after a new target document material item is created.
     * Handles tasks such as adding "prev-next" relationships between the reserved and target items
     * and applying additional configuration-based updates.
     *
     * @param reservedMatItemNode                The reserved material item node, which serves as the main reference
     *                                           and source for the new target material item node.
     * @param targetMatItemNode                  The newly created target document material item node.
     * @param reservedToTargetConversionConfig   Configuration for applying transformations or updates
     *                                           from the reserved item to the target item.
     * @param serialLogonInfo                    Logon information required for performing secure operations.
     * @return The modified or updated target material item node after applying the post-update logic.
     *         If no updates are applied (e.g., if the configuration is null), the original target item node is returned.
     * @throws DocActionException                If an error occurs during the update or relationship-building processes.
     */
    public TargetItem initPostUpdateTargetMatItemReserved(DocMatItemNode reservedMatItemNode, TargetItem targetMatItemNode,
                                                  CrossCopyDocConversionConfig reservedToTargetConversionConfig,
                                                         SerialLogonInfo serialLogonInfo) throws DocActionException {
        if(reservedToTargetConversionConfig == null){
            return targetMatItemNode;
        }
        // In case need to create prev-next relationship from newly created target item to reserved item
        if(reservedToTargetConversionConfig.getTargetOnCreate() == StandardSwitchProxy.SWITCH_ON){
            DocActionExecutionProxy<?, ?, ?> reservedDocActionProxy = docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(reservedMatItemNode.getHomeDocumentType());
            // Add a "prev-next" relationship from the newly created target item to the reserved item.
            docFlowProxy.buildItemPrevNextRelationship(targetMatItemNode, reservedMatItemNode, null, serialLogonInfo,
                    new DocFlowProxy.CrossCreateContextDocOption(DocFlowProxy.CrossCreateContextDocOption.COPYMODEL_SKIP));
            // Execute additional post-update logic after establishing the "prev-next" relationship.
            initPostUpdateTargetFromReserved(reservedMatItemNode, targetMatItemNode,
                    reservedToTargetConversionConfig, serialLogonInfo);
        }
        return targetMatItemNode;
    }

    /**
     * Post update logic when prev-next relationship is build.
     * @param reservedMatItemNode
     * @param crossCopyDocConversionConfig
     * @param targetMatItemNode
     * @param serialLogonInfo
     * @return
     */
    public TargetItem initPostUpdateTargetFromReserved(DocMatItemNode reservedMatItemNode,
                                                           TargetItem targetMatItemNode,
                                                  CrossCopyDocConversionConfig crossCopyDocConversionConfig,
                                                  SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if(crossCopyDocConversionConfig == null){
            return targetMatItemNode;
        }
        if(crossCopyDocConversionConfig.getReserveOnSrc() == StandardSwitchProxy.SWITCH_ON){
            // need to reserve source mat item
            reserveDocItemProxy.reserveDocItemOnlineReserveTar(targetMatItemNode,
                    reservedMatItemNode.getHomeDocumentType(),
                    reservedMatItemNode.getUuid(), serialLogonInfo);
        }
        return targetMatItemNode;
    }

    /**
     * Default Logic to New Target Item Service Module, including the default data conversion
     *
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public ReseveredDocItemContext filteredSourceMatItemNode(DocMatItemNode reservedMatItemNode,
                                                             DocMatItemNode sourceMatItemNode,
                                                             StorageCoreUnit requestCoreUnitContext,
                                                             CrossDocConvertReservedRequest crossDocConvertReservedRequest)
            throws DocActionException {
        if (sourceMatItemNode != null) {
            if (crossDocConvertReservedRequest.getLoadSourceItemReserved() != null) {
                return crossDocConvertReservedRequest.getLoadSourceItemReserved()
                        .execute(reservedMatItemNode, sourceMatItemNode, requestCoreUnitContext);
            }
        }
        return null;
    }

    public static class ReseveredDocItemContext {

        private DocMatItemNode sourceDocMatItemNode;

        private StorageCoreUnit reservedAmount;

        public ReseveredDocItemContext() {
        }

        public ReseveredDocItemContext(DocMatItemNode sourceDocMatItemNode, StorageCoreUnit reservedAmount) {
            this.sourceDocMatItemNode = sourceDocMatItemNode;
            this.reservedAmount = reservedAmount;
        }

        public DocMatItemNode getSourceDocMatItemNode() {
            return sourceDocMatItemNode;
        }

        public void setSourceDocMatItemNode(DocMatItemNode sourceDocMatItemNode) {
            this.sourceDocMatItemNode = sourceDocMatItemNode;
        }

        public StorageCoreUnit getReservedAmount() {
            return reservedAmount;
        }

        public void setReservedAmount(StorageCoreUnit reservedAmount) {
            this.reservedAmount = reservedAmount;
        }
    }

    public static ServiceModule filterTargetRootDoc(
            CrossDocConvertReservedRequest.IFilterRootDocContextReserved filterRootDocContextReserved,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode reservedMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        DocContentCreateContext docContentCreateContext =
                filterTargetRootDocContext(filterRootDocContextReserved, docContentCreateContextList, reservedMatItemNode,
                        sourceMatItemNode);
        if (docContentCreateContext != null) {
            return docContentCreateContext.getTargetRootDocument();
        }
        return null;
    }

    public static DocContentCreateContext filterTargetRootDocContext(
            CrossDocConvertReservedRequest.IFilterRootDocContextReserved filterRootDocContextReserved,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode reservedMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            return null;
        }
        for (DocContentCreateContext docContentCreateContext : docContentCreateContextList) {
            if (filterRootDocContextReserved.execute(docContentCreateContext, reservedMatItemNode,
                    sourceMatItemNode)) {
                return docContentCreateContext;
            }
        }
        return null;
    }

}
