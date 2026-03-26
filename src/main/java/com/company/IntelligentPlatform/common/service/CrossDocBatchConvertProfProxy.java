package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.company.IntelligentPlatform.common.service.CrossDocConvertProfRequest.IConvertToTarItemServiceModelPrevProf;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CrossDocBatchConvertProfProxy<SourceServiceModel extends ServiceModule, TargetServiceModel extends ServiceModule, TargetItem extends DocMatItemNode, TargetItemServiceModel extends ServiceModule>
        extends CrossDocBatchConvertProxy {

    Logger logger = LoggerFactory.getLogger(CrossDocBatchConvertProfProxy.class);

    @Autowired
    protected PrevNextProfDocItemProxy prevNextProfDocItemProxy;

    @Transactional
    public List<DocContentCreateContext> createTargetBatchDocToPrevDoc(SourceServiceModel sourceServiceModel, int prevProfDocType, int sourceDocType,
                                                                         List<ServiceEntityNode> selectedSourceDocMatItemList,
                                                                         CrossDocConvertProfRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertProfRequest,
                                                                         DocumentMatItemBatchGenRequest genRequest,
                                                                         CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {
        DocActionExecutionProxy<TargetServiceModel, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(
                        crossDocConvertProfRequest.getTargetDocType());
        TargetServiceModel targetDocServiceModel = (TargetServiceModel) getExistedTargetDocServiceModule(genRequest,
                crossDocConvertProfRequest, sourceServiceModel, genRequest.getPrevProfDocType(),
                DocMatItemNode::getNextDocMatItemUUID, logonInfo);
        List<DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        loadToPrevProfDocFramework(prevProfDocType, sourceDocType, selectedSourceDocMatItemList,
                crossDocConvertProfRequest,
                (prevProfMatItemNode, sourceDocMatItemNode, sourceDocActionProxy,
                 prevProfDocActionProxy, targetDocOffset) -> {
                    if (prevProfMatItemNode == null && inputOption.getSkipIfPrevProfEmpty()) {
                        return true;
                    }
                    try {
                        DocMatItemCreateContext docMatItemCreateContext =
                                genDefTargetMatItemServiceModelPrevProf(sourceServiceModel,
                                        prevProfMatItemNode, sourceDocMatItemNode, docContentCreateContextList,
                                        targetDocServiceModel, prevProfDocActionProxy, sourceDocActionProxy, targetDocActionProxy,
                                        crossDocConvertProfRequest, genRequest, targetDocOffset, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                        // in case need to break loops
                        return !docMatItemCreateContext.getBreakFlag();
                    } catch (ServiceModuleProxyException | DocActionException | ServiceEntityConfigureException | IllegalAccessException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return true;
                });
        storeContext(docContentCreateContextList, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    /**
     * Creates a batch of target documents from a given list of items from a previous professional document item list.
     *
     * Typical Use Cases:
     * - Create a Sales Return Order from a Sales Contract.
     * - Create a Purchase Return Order from a Purchase Contract.
     *
     * @param prevProfDocType                The previous professional document type: such as `Sales Contract` or `Purchase Contract`.
     * @param selectedPrevProfDocMatItemList A list of material items selected from the previous professional document.
     * @param crossDocConvertProfRequest     The request object containing conversion rules and configurations
     *                                       for cross-document professional operations.
     * @param inputOption                    The input option specifying the mode of the conversion process.
     * @param logonInfo                The serial logon information for authentication and context handling.
     * @return                               A list of `DocContentCreateContext` representing the created target documents.
     * @throws ServiceEntityConfigureException    If there is an error configuring service entities during the process.
     * @throws ServiceModuleProxyException        If there is an issue with the service module proxy.
     * @throws DocActionException                 If there is an error while performing document-related actions.
     * @throws ServiceEntityInstallationException If there is an error during entity installation or initialization.
     */
    public List<DocContentCreateContext> createTargetBatchDocFromPrevDoc(int prevProfDocType,
                                                                         ServiceModule prevProfServiceModule,
                                                                         List<ServiceEntityNode> selectedPrevProfDocMatItemList,
                                                                         CrossDocConvertProfRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertProfRequest,
                                                                         DocumentMatItemBatchGenRequest genRequest,
                                                                         CrossDocConvertRequest.InputOption inputOption, LogonInfo logonInfo)
            throws ServiceEntityConfigureException, ServiceModuleProxyException, DocActionException, SearchConfigureException {
        DocActionExecutionProxy<TargetServiceModel, ?, ?> targetDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(
                        crossDocConvertProfRequest.getTargetDocType());
        TargetServiceModel targetDocServiceModel = (TargetServiceModel) getExistedTargetDocServiceModule(genRequest,
                crossDocConvertProfRequest, prevProfServiceModule, genRequest.getPrevProfDocType(),
                DocMatItemNode::getNextProfDocMatItemUUID, logonInfo);
        List<DocContentCreateContext> docContentCreateContextList = new ArrayList<>();
        loadFromPrevProfDocFramework(prevProfDocType, selectedPrevProfDocMatItemList,
                crossDocConvertProfRequest,
                (prevProfMatItemNode, sourceDocMatItemNode, sourceDocActionProxy,
                 prevProfDocActionProxy, targetDocOffset) -> {
                    try {
                        DocMatItemCreateContext docMatItemCreateContext =
                                genDefTargetMatItemServiceModelPrevProf(prevProfServiceModule,
                                        prevProfMatItemNode, sourceDocMatItemNode, docContentCreateContextList, targetDocServiceModel,
                                        prevProfDocActionProxy, sourceDocActionProxy, targetDocActionProxy,
                                        crossDocConvertProfRequest, genRequest, targetDocOffset, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
                        // in case need to break loops
                        return !docMatItemCreateContext.getBreakFlag();
                    } catch (ServiceModuleProxyException | DocActionException | ServiceEntityConfigureException | IllegalAccessException e) {
                        logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
                    }
                    return true;
                }, LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        storeContext(docContentCreateContextList,LogonInfoManager.cloneToSerialLogonInfo(logonInfo));
        return docContentCreateContextList;
    }

    public void loadFromPrevProfDocFramework(int prevProfDocType,
                                             List<ServiceEntityNode> selectedPrevProfDocMatItemList,
                                             CrossDocConvertProfRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertProfRequest,
                                             ISourceDocItemExecutor sourceDocItemExecutor, SerialLogonInfo serialLogonInfo)
            throws DocActionException, ServiceEntityConfigureException {
        if (ServiceCollectionsHelper.checkNullList(selectedPrevProfDocMatItemList)) {
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        DocActionExecutionProxy<?, ?, ?> prevProfDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(prevProfDocType);
        Map<String, ServiceModule> parentPrevProfDocMap = new HashMap<>();// Build the Material SKU list according to given reserved document item list
        List<ServiceEntityNode> rawMaterialSKUList =
                docFlowProxy.getRefMaterialSKUList(selectedPrevProfDocMatItemList, null, serialLogonInfo.getClient());
        AtomicInteger targetDocOffset = new AtomicInteger(); // calculate how many target doc created in cache
        ServiceCollectionsHelper.traverseListInterrupt(selectedPrevProfDocMatItemList, seNode -> {
            DocMatItemNode prevProfDocMatItemNode = (DocMatItemNode) seNode;
            // Filter if current prev prof doc mat item and relative material is valid
            MaterialStockKeepUnit refMaterialSKU = (MaterialStockKeepUnit) ServiceCollectionsHelper.filterSENodeOnline(
                    prevProfDocMatItemNode.getRefMaterialSKUUUID(), rawMaterialSKUList);
            if (crossDocConvertProfRequest.getFilterSelectedMatItem() != null) {
                boolean filterItem =
                        crossDocConvertProfRequest.getFilterSelectedMatItem().execute(prevProfDocMatItemNode,
                                refMaterialSKU, null);
                if (!filterItem) {
                    return true;
                }
            }
            // Get prev document item by provided prev prof material item.
            DocMatItemNode sourceDocMatItemNode = null;
            try {
                if (crossDocConvertProfRequest.getGetSourceMatItemByPrevProf() != null) {
                    sourceDocMatItemNode =
                            crossDocConvertProfRequest.getGetSourceMatItemByPrevProf()
                                    .execute(prevProfDocMatItemNode);
                } else {
                    sourceDocMatItemNode = docFlowProxy.findEndDocMatItemTillNext(prevProfDocMatItemNode);
                }
                if (sourceDocMatItemNode == null) {
                    sourceDocMatItemNode = prevProfDocMatItemNode;
                }
            } catch (ServiceEntityConfigureException e) {
                return true; // continue
            }
            if (sourceDocItemExecutor != null) {
                ServiceModule prevProfServiceModule = null;
                DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy =
                        docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocMatItemNode.getHomeDocumentType());
                return sourceDocItemExecutor.execute(prevProfDocMatItemNode,
                        sourceDocMatItemNode,
                        sourceDocActionProxy,prevProfDocActionProxy, targetDocOffset);
            }
            return true;
        });
    }

    public void loadToPrevProfDocFramework(int prevProfDocType, int sourceDocType,
                                             List<ServiceEntityNode> selectedSourceDocMatItemList,
                                             CrossDocConvertProfRequest<TargetServiceModel, TargetItem, TargetItemServiceModel> crossDocConvertProfRequest,
                                             ISourceDocItemExecutor sourceDocItemExecutor)
            throws DocActionException {
        if (ServiceCollectionsHelper.checkNullList(selectedSourceDocMatItemList)) {
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        DocActionExecutionProxy<SourceServiceModel, ?, ?> sourceDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(sourceDocType);
        DocActionExecutionProxy<?, ?, ?> prevProfDocActionProxy =
                docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(prevProfDocType);
        Map<String, ServiceModule> parentPrevProfDocMap = new HashMap<>();
        AtomicInteger targetDocOffset = new AtomicInteger();
        ServiceCollectionsHelper.traverseListInterrupt(selectedSourceDocMatItemList, seNode -> {
            DocMatItemNode sourceDocMatItemNode = (DocMatItemNode) seNode;
            DocMatItemNode prevProfDocMatItemNode = null;
            try {
                if (crossDocConvertProfRequest.getGetPrevProfMatItemBySource() != null) {
                    prevProfDocMatItemNode =
                            crossDocConvertProfRequest.getGetPrevProfMatItemBySource()
                                    .execute(sourceDocMatItemNode);
                }
            } catch (ServiceEntityConfigureException e) {
                return true; // continue
            }
            if (sourceDocItemExecutor != null) {
                ServiceModule prevProfServiceModule = null;
                boolean result = sourceDocItemExecutor.execute(prevProfDocMatItemNode,
                        sourceDocMatItemNode,
                        sourceDocActionProxy,prevProfDocActionProxy, targetDocOffset);
                targetDocOffset.getAndIncrement();
                return result;
            }
            return true;
        });
    }

    private ServiceModule getParentServiceModel(DocMatItemNode prevProfDocMatItemNode,
                                                        DocumentContentSpecifier<?, ?,?> prevProfDocSpecifier, 
                                                        Map<String, ServiceModule> parentPrevProfDocMap)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        if (prevProfDocMatItemNode == null) {
            return null;
        }
        if (parentPrevProfDocMap == null) {
            return null;
        }
        String parentDocUUID = prevProfDocSpecifier.getParentDocUUIDByMatItem(prevProfDocMatItemNode);
        if (parentPrevProfDocMap.containsKey(parentDocUUID)) {
            return parentPrevProfDocMap.get(parentDocUUID);
        } else {
            ServiceModule prevProfServiceModule = prevProfDocSpecifier.loadServiceModule(parentDocUUID,
                    prevProfDocMatItemNode.getClient());
            parentPrevProfDocMap.put(parentDocUUID, prevProfServiceModule);
            return prevProfServiceModule;
        }
    }

    /**
     * Executor to be exeucted in traverse each source doc mat item
     */
    public interface ISourceDocItemExecutor {

        boolean execute(DocMatItemNode prevProfMatItemNode,
                        DocMatItemNode sourceDocMatItemNode,
                        DocActionExecutionProxy<?, ?, ?> sourceDocActionProxy,
                        DocActionExecutionProxy<?, ?, ?> prevProfDocActionProxy, AtomicInteger targetDocOffset) throws DocActionException;

    }

    /**
     * Default Logic to New Target Item Service Module, including the default data conversion
     *
     * @return
     * @throws ServiceEntityConfigureException
     * @throws ServiceModuleProxyException
     */
    public DocMatItemCreateContext genDefTargetMatItemServiceModelPrevProf(ServiceModule prevProfServiceModel,
                                                                           DocMatItemNode prevProfMatItemNode,
                                                                           DocMatItemNode sourceMatItemNode,
                                                                           List<DocContentCreateContext> docContentCreateContextList,
                                                                           TargetServiceModel targetServiceModule,
                                                                           DocActionExecutionProxy prevProfDocActionProxy,
                                                                           DocActionExecutionProxy sourceDocActionProxy,
                                                                           DocActionExecutionProxy targetDocActionProxy,
                                                                           CrossDocConvertProfRequest crossDocConvertProfRequest,
                                                                           DocumentMatItemBatchGenRequest genRequest,
                                                                           AtomicInteger targetDocOffset, SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException, ServiceEntityConfigureException,
            IllegalAccessException {
        if (targetServiceModule == null) {
            targetServiceModule = (TargetServiceModel) filterTargetRootDoc(crossDocConvertProfRequest.getFilterRootDocContextPrevProf(), docContentCreateContextList,
                    prevProfMatItemNode, sourceMatItemNode);
        }
        // Only use when prevProfServiceModel is empty
        ServiceModule sourceServiceModule = null;
        if (prevProfServiceModel == null) {
            sourceServiceModule = getParentServiceModel(sourceMatItemNode,
                    sourceDocActionProxy.getDocumentContentSpecifier(), new HashMap<>());
        }
        DocumentContentSpecifier targetDocSpecifier = targetDocActionProxy.getDocumentContentSpecifier();
        DocumentContentSpecifier prevProfDocSpecifier = prevProfDocActionProxy.getDocumentContentSpecifier();
        DocumentContentSpecifier sourceDocSpecifier = sourceDocActionProxy.getDocumentContentSpecifier();
        if (targetServiceModule == null ) {
            if (prevProfServiceModel != null && prevProfMatItemNode != null) {
                targetServiceModule = (TargetServiceModel) initCopyToTargetServiceModelWrapper(prevProfServiceModel,
                        prevProfMatItemNode,
                        prevProfDocActionProxy,targetDocActionProxy,
                        crossDocConvertProfRequest, targetDocOffset);
            } else {
                targetServiceModule = (TargetServiceModel) initCopyToTargetServiceModelWrapper(sourceServiceModule,
                        sourceMatItemNode,
                        sourceDocActionProxy,targetDocActionProxy,
                        crossDocConvertProfRequest, targetDocOffset);
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
                initConvertToTargetMatItem(prevProfMatItemNode, sourceMatItemNode, targetDocSpecifier, targetDocument,
                        srcToTargetConversionConfig, crossDocConvertProfRequest.getConvertToTargetItemProf(),
                        genRequest, serialLogonInfo);
        if (docMatItemCreateContext == null) {
            return null;
        }
        TargetItemServiceModel targetItemServiceModel =
                (TargetItemServiceModel) serviceModuleProxy.quickCreateServiceModel(
                        targetDocSpecifier.getItemServiceModelClass(),
                        docMatItemCreateContext.getTargetDocMatItemNode(), targetDocSpecifier.getMatItemNodeInstId());
        Map<Integer, CrossCopyDocConversionConfig> crossCopyDocConversionConfigMap =
                prevProfDocActionProxy.getCrossCopyDocConversionConfigMap();
        CrossCopyDocConversionConfig toTargetConversionConfig =
                crossCopyDocConversionConfigMap.get(targetDocSpecifier.getDocumentType());
        if (toTargetConversionConfig != null) {
            if (toTargetConversionConfig.getCopyPartyToItemParty() == StandardSwitchProxy.SWITCH_ON) {
                /*
                 * [Step2] Try to copy party information from source doc party to target item party
                 */
                if (prevProfServiceModel != null) {
                    docInvolvePartyProxy.genConvertCrossDocItemParty(prevProfDocSpecifier, prevProfServiceModel,
                            targetDocSpecifier, docMatItemCreateContext.getTargetDocMatItemNode(), targetItemServiceModel,
                            toTargetConversionConfig.getCrossCopyPartyConversionConfigList());
                } else {
                    docInvolvePartyProxy.genConvertCrossDocItemParty(sourceDocSpecifier, sourceServiceModule,
                            targetDocSpecifier, docMatItemCreateContext.getTargetDocMatItemNode(), targetItemServiceModel,
                            toTargetConversionConfig.getCrossCopyPartyConversionConfigList());
                }
            } else {
                /*
                 * [Step3] Try to copy party information from source item party to target item party
                 */
                if (prevProfMatItemNode != null) {
                    ServiceModule prevProfMatItemServiceModule =
                            prevProfDocSpecifier.loadItemServiceModule(prevProfMatItemNode);
                    docInvolvePartyProxy.genConvertCrossItemParty(prevProfDocSpecifier, prevProfMatItemServiceModule,
                            targetDocSpecifier, targetServiceModule, docMatItemCreateContext.getTargetDocMatItemNode(),
                            targetItemServiceModel, true,
                            toTargetConversionConfig.getCrossCopyPartyConversionConfigList());
                } else {
                    ServiceModule sourceMatItemServiceModule =
                            sourceDocSpecifier.loadItemServiceModule(sourceMatItemNode);
                    docInvolvePartyProxy.genConvertCrossItemParty(sourceDocSpecifier, sourceMatItemServiceModule,
                            targetDocSpecifier, targetServiceModule, docMatItemCreateContext.getTargetDocMatItemNode(),
                            targetItemServiceModel, true,
                            toTargetConversionConfig.getCrossCopyPartyConversionConfigList());
                }
            }
        }
        IConvertToTarItemServiceModelPrevProf<TargetServiceModel, TargetItemServiceModel> convertToTarItemServiceModelPrevProf =
                crossDocConvertProfRequest.getConvertToTarItemServiceModelPrevProf();
        if (convertToTarItemServiceModelPrevProf != null) {
            convertToTarItemServiceModelPrevProf.execute(sourceMatItemNode, prevProfMatItemNode, targetServiceModule,
                    targetItemServiceModel);
        }
        docMatItemCreateContext.setTargetItemServiceModel(targetItemServiceModel);
        // To merge doc mat item context to root list
        mergeItemCreateContext(docContentCreateContextList, docMatItemCreateContext, targetDocSpecifier);
        return docMatItemCreateContext;
    }

    /**
     * Default Logic to new target item instance and basic data conversion,
     * return null, if data is not valid
     *
     * @param convertToTargetItemProf: callback method to convert source item, reserved item to target item
     */
    public DocMatItemCreateContext initConvertToTargetMatItem(DocMatItemNode prevProfMatItemNode,
                                                              DocMatItemNode sourceMatItemNode,
                                                              DocumentContentSpecifier targetDocSpecifier,
                                                              ServiceEntityNode targetDocument,
                                                              CrossCopyDocConversionConfig crossCopyDocConversionConfig,
                                                              CrossDocConvertProfRequest.IConvertToTargetItemProf<TargetItem> convertToTargetItemProf,
                                                              DocumentMatItemBatchGenRequest genRequest, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        if (sourceMatItemNode != null) {
            TargetItem targetMatItemNode = (TargetItem) targetDocSpecifier.createItem(targetDocument);
            docFlowProxy.buildItemPrevNextRelationship(sourceMatItemNode, targetMatItemNode, genRequest, serialLogonInfo);
            if (prevProfMatItemNode != null) {
                docFlowProxy.copyPrevProfDocMatItemToNextProfDoc(prevProfMatItemNode,
                        prevProfMatItemNode.getHomeDocumentType(),
                        targetMatItemNode, targetMatItemNode.getHomeDocumentType());
                prevNextProfDocItemProxy.addPrevNextProfRelation(targetMatItemNode,
                        prevProfMatItemNode, serialLogonInfo);
            }
            // Post update logic when prev-next relationship build
            targetMatItemNode = (TargetItem) initPostUpdateTargetMatItem(sourceMatItemNode, targetMatItemNode,
                    crossCopyDocConversionConfig, serialLogonInfo);
            DocMatItemCreateContext docMatItemCreateContext =
                    new DocMatItemCreateContext(targetMatItemNode, sourceMatItemNode, null, prevProfMatItemNode);
            if (convertToTargetItemProf != null) {
                docMatItemCreateContext =
                        convertToTargetItemProf.execute(docMatItemCreateContext);
            }
            return docMatItemCreateContext;
        }
        return null;
    }

    public static ServiceModule filterTargetRootDoc(
            CrossDocConvertProfRequest.IFilterRootDocContextPrevProf filterRootDocContextPrevProf,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode prevProfMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        DocContentCreateContext docContentCreateContext =
                filterTargetRootDocContext(filterRootDocContextPrevProf, docContentCreateContextList, prevProfMatItemNode,
                        sourceMatItemNode);
        if (docContentCreateContext != null) {
            return docContentCreateContext.getTargetRootDocument();
        }
        return null;
    }

    public static DocContentCreateContext filterTargetRootDocContext(
            CrossDocConvertProfRequest.IFilterRootDocContextPrevProf filterRootDocContextPrevProf,
            List<DocContentCreateContext> docContentCreateContextList, DocMatItemNode prevProfMatItemNode,
            DocMatItemNode sourceMatItemNode) {
        if (ServiceCollectionsHelper.checkNullList(docContentCreateContextList)) {
            return null;
        }
        for (DocContentCreateContext docContentCreateContext : docContentCreateContextList) {
            if (filterRootDocContextPrevProf.execute(docContentCreateContext, prevProfMatItemNode,
                    sourceMatItemNode)) {
                return docContentCreateContext;
            }
        }
        return null;
    }

}
