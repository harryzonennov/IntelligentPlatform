package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocSplitMergeProxy <T extends ServiceEntityNode,
        Item extends ServiceEntityNode>{

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected ServiceItemIdGenerator serviceItemIdGenerator;

    protected Logger logger = LoggerFactory.getLogger(DocSplitMergeProxy.class);

    @Transactional
    public void mergeDocBatch(T mergeTargetDocument, int documentType,
                                   List<ServiceEntityNode> selectedDocMatItemList,
                              DocSplitMergeRequest<T, Item> docMergeRequest, String logonUserUUID,
                                   String resOrgUUID)
            throws ServiceEntityConfigureException, DocActionException {
        String organizationUUID = ServiceEntityStringHelper.EMPTYSTRING;
        if (ServiceCollectionsHelper.checkNullList(selectedDocMatItemList)) {
            throw new DocActionException(DocActionException.TYPE_NO_SELECT_ITEM);
        }
        List<String> existedRootUUIDList = new ArrayList<>();
        selectedDocMatItemList.forEach(seNode -> {
            existedRootUUIDList.add(seNode.getRootNodeUUID());
        });
        ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure();
        key1.setKeyName(IServiceEntityNodeFieldConstant.UUID);
        key1.setMultipleValueList(existedRootUUIDList);
        DocumentContentSpecifier<?, T, Item> documentContentSpecifier;
        documentContentSpecifier =
                docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
        List<ServiceEntityNode> oldDocumentList =
                documentContentSpecifier.getDocumentManager().getEntityNodeListByKeyList(ServiceCollectionsHelper.asList(key1),
                        documentContentSpecifier.getDocNodeName(), null);
        if (ServiceCollectionsHelper.checkNullList(oldDocumentList)) {
            throw new DocActionException(DocActionException.TYPE_NO_VALID_DATA);
        }
        List<ServiceEntityNode> filteredDocList = ServiceCollectionsHelper.filterListOnline(oldDocumentList, seNode->{
            ServiceEntityNode filteredSENode = docMergeRequest.getFilterDocToMerge().filterData((T) seNode, mergeTargetDocument);
            return filteredSENode != null;
        }, false);
        /*
         * [Step2] Update all the parent uuid & root uuid
         */
        for (int index = 0; index < selectedDocMatItemList.size(); index++) {
            Item docMatItem =
                    (Item) selectedDocMatItemList.get(index);
            String itemId;
            try {
                itemId = serviceItemIdGenerator.genItemIdParentUUID(documentContentSpecifier.getMatItemNodeInstId(),
                        IServiceEntityNodeFieldConstant.ID, mergeTargetDocument.getId(), mergeTargetDocument.getUuid(), index,
                        mergeTargetDocument.getClient());
                docMatItem.setId(itemId);
                docMatItem.setParentNodeUUID(mergeTargetDocument.getUuid());
                docMatItem.setRootNodeUUID(mergeTargetDocument.getUuid());
                if(docMergeRequest.getSetTargetDocItem() != null){
                    docMatItem = docMergeRequest.getSetTargetDocItem().execute(docMatItem, mergeTargetDocument);
                }
            } catch (SearchConfigureException e) {
                logger.error(
                        ServiceEntityStringHelper.genDefaultErrorMessage(e, docMatItem.getUuid()));
            }
        }
        documentContentSpecifier.getDocumentManager().updateSENodeList(selectedDocMatItemList, logonUserUUID, organizationUUID);
        /*
         * [Step3] Archive previous parent doc in case needed
         */
        if (!ServiceCollectionsHelper.checkNullList(filteredDocList)) {
            filteredDocList.forEach(seNode -> {
                try {
                    checkAndArchiveOldDocAfterMerge(seNode.getUuid(), documentContentSpecifier, seNode.getClient(),
                            logonUserUUID, resOrgUUID);
                } catch (ServiceEntityConfigureException e) {
                    logger.error(
                            ServiceEntityStringHelper.genDefaultErrorMessage(e, "checkAndArchiveOldDocAfterMerge"));
                }
            });
        }
    }

    /**
     * After merge document, just check and archive document if needed
     *
     * @throws ServiceEntityConfigureException
     */
    private void checkAndArchiveOldDocAfterMerge(String rootDocUUID,
                                                 DocumentContentSpecifier<?, T, Item> documentContentSpecifier,
                                                 String client, String logonUserUUID,
                                                   String resOrgUUID) throws ServiceEntityConfigureException {
        List<ServiceEntityNode> subDocMatItemList =
                documentContentSpecifier.getDocumentManager().getEntityNodeListByKey(rootDocUUID,
                IServiceEntityNodeFieldConstant.ROOTNODEUUID,
                        documentContentSpecifier.getMatItemNodeInstId(), null);
        if (!ServiceCollectionsHelper.checkNullList(subDocMatItemList)) {
            return;
        }
        // In case no sub material item, then archive this old document
        documentContentSpecifier.getDocumentManager().archiveDeleteEntityByKey(rootDocUUID,
                IServiceEntityNodeFieldConstant.UUID, client, documentContentSpecifier.getDocNodeName(),
                logonUserUUID, resOrgUUID);
    }
}
