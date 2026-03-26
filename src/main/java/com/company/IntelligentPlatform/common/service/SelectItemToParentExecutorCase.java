package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes a batch process to handle selected items and, if all items meet the expected conditions,
 * attempts to execute the corresponding action on the parent node.
 *
 */
@Service
public class SelectItemToParentExecutorCase<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode>
        extends DocActionExecutorCase<R, T, Item> {

    @Autowired
    protected DocFlowProxy docFlowProxy;

    /**
     * Executes a batch process to handle selected items and, if all items meet the expected conditions,
     * attempts to execute the corresponding action on the Parent Node.
     *
     * The following steps are performed inside this method:
     * 1. Validate the parent node based on the action code and configuration.
     * 2. Iterates through each selected item, performing execution and updating its state in the database.
     * 3. Validate the status of all items.
     * 4. If all items are in valid states, triggers the specified action (standard or custom) on the parent node.
     *
     * @param docActionExecutionProxy  Proxy to facilitate document action execution.
     * @param docItemBatchExecutionRequest  Batch request containing details about selected items, actions, and configurations.
     * @param serialLogonInfo  Logon information for authentication and localization purposes.
     * @throws DocActionException  If an error occurs during the processing or action execution.
     */
    public void batchExecSelectedItemTryExecuteParent(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
            DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
            SerialLogonInfo serialLogonInfo)
            throws DocActionException{
        /*
         * Step 1: Validate the root node based on the primary action code and configuration.
         * Step 2: Execute the primary action for the selected item in the batch.
         */
        ItemExecutionMetadata<R, T, Item> itemItemExecutionMetadata = batchExecSelectedItemHeader(docActionExecutionProxy, docItemBatchExecutionRequest, serialLogonInfo);
        if (itemItemExecutionMetadata == null) {
            return;
        }
        DocumentContentSpecifier<R, T, Item> documentContentSpecifier =
                docActionExecutionProxy.getDocumentContentSpecifier();
        R serviceModule = itemItemExecutionMetadata.getServiceModule();
        T parentDocNode = itemItemExecutionMetadata.getParentDocNode();
        DocActionConfigure docActionConfigure = itemItemExecutionMetadata.getDocActionConfigure();
        List<ServiceEntityNode> allDocMatItemList = itemItemExecutionMetadata.getAllMatItemList();
        /*
         * [Step 3] Validate the status of all items
         */
        // Refresh the `allDocMatItemList` list again after `checkUpdateDocMatItemList` to ensure consistency
        serviceModule = refreshLoadServiceModule(documentContentSpecifier, serviceModule);
        allDocMatItemList = documentContentSpecifier.getDocMatItemList(serviceModule);
        for (ServiceEntityNode itemSENode : allDocMatItemList) {
            boolean itemCheckResult =
                    checkItemForCalculate((Item) itemSENode, docActionExecutionProxy, docItemBatchExecutionRequest);
            if (!itemCheckResult) {
                return; // exit method if one item don't match
            }
        }
        /*
         * [Step 4] Execute the action on the parent node
         */
        try{
            if (docActionConfigure != null) {
                // In case have to trigger standard doc action as well as custom action call back,
                // Using one empty docItemActionCallback, only need to trigger action on root node now.
                docActionExecutionProxy.defExecuteActionCore(serviceModule, docActionConfigure.getActionCode(), docItemBatchExecutionRequest.getDocActionCallback(),
                        (serviceEntityNode, itemSelectionExecutionContext) -> true, serialLogonInfo);
            } else {
                // In case just custom action for parent node
                docItemBatchExecutionRequest.getDocActionCallback().execute(parentDocNode, serialLogonInfo);
            }
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Executes the primary document action exclusively for the selected items in the batch and performs
     * the secondary document action for the remaining items in the complete item list.
     *
     *
     * @param docActionExecutionProxy The proxy to execute document actions.
     * @param docItemBatchExecutionRequest The batch execution request containing all relevant items and configurations.
     * @param serialLogonInfo Information required for authenticated execution of document actions.
     * @throws DocActionException If an error occurs during the execution of document actions.
     */
    public void exclusiveExecSelectedItemTryExecuteParent(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
                                                      DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
                                                      SerialLogonInfo serialLogonInfo)
            throws DocActionException{
        /*
         * Step 1: Validate the root node based on the primary action code and configuration.
         * Step 2: Execute the primary action for the selected item in the batch.
         */
        ItemExecutionMetadata<R, T, Item> itemItemExecutionMetadata = batchExecSelectedItemHeader(docActionExecutionProxy, docItemBatchExecutionRequest, serialLogonInfo);
        if (itemItemExecutionMetadata == null) {
            return;
        }
        DocumentContentSpecifier<R, T, Item> documentContentSpecifier =
                docActionExecutionProxy.getDocumentContentSpecifier();
        R serviceModule = itemItemExecutionMetadata.getServiceModule();
        T parentDocNode = itemItemExecutionMetadata.getParentDocNode();
        DocActionConfigure docActionConfigure = itemItemExecutionMetadata.getDocActionConfigure();
        List<ServiceEntityNode> allDocMatItemList = itemItemExecutionMetadata.getAllMatItemList();

        /*
         * Step 3: Execute the secondary document action for non-selected items in the list.
         */
        List<ServiceEntityNode> selectedMatItemList = itemItemExecutionMetadata.getSelectedMatItemList();
        List<ServiceEntityNode> restItemList = ServiceCollectionsHelper.getDiffItemList(selectedMatItemList, allDocMatItemList);
        DocActionConfigure secondaryDocActionConfigure = itemItemExecutionMetadata.getSecondaryDocActionConfigure();
        if (secondaryDocActionConfigure != null) {
            checkUpdateDocMatItemList(restItemList, allDocMatItemList,
                    docItemBatchExecutionRequest, secondaryDocActionConfigure, docActionExecutionProxy, serialLogonInfo);
        }
        // Currently no need to go through and check all the items.
        /*
         * Step 4: Execute the primary action on the root node
         */
        try{
            if (docActionConfigure != null) {
                // In case have to trigger standard doc action as well as custom action call back,
                // Using one empty docItemActionCallback, only need to trigger action on root node now.
                docActionExecutionProxy.defExecuteActionCore(serviceModule, docActionConfigure.getActionCode(), docItemBatchExecutionRequest.getDocActionCallback(),
                        (serviceEntityNode, itemSelectionExecutionContext) -> true, serialLogonInfo);
            } else {
                // In case just custom action for parent node
                docItemBatchExecutionRequest.getDocActionCallback().execute(parentDocNode, serialLogonInfo);
            }
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * Common `header` process of batch execute selected item list: check parent status and check update seletected item.
     */
    private ItemExecutionMetadata<R, T, Item> batchExecSelectedItemHeader(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
                                             DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest, SerialLogonInfo serialLogonInfo) throws DocActionException {
        ItemExecutionMetadata<R, T, Item> itemItemExecutionMetadata = prepareItemExecMetadata(docActionExecutionProxy, docItemBatchExecutionRequest);
        if (itemItemExecutionMetadata == null) {
            return null;
        }
        // Load and refresh the service module and parent node
        R serviceModule = itemItemExecutionMetadata.getServiceModule();
        T parentDocNode = itemItemExecutionMetadata.getParentDocNode();
        List<ServiceEntityNode> selectedMatItemList = itemItemExecutionMetadata.getSelectedMatItemList();
        List<ServiceEntityNode> allDocMatItemList = itemItemExecutionMetadata.getAllMatItemList();

        /*
         * [Step 1]: Validate the parent node based on the action code and configuration
         */
        boolean skipFlag = docItemBatchExecutionRequest.getSkipFlag();
        DocActionConfigure docActionConfigure = itemItemExecutionMetadata.getDocActionConfigure();
        if (docActionConfigure != null && !docItemBatchExecutionRequest.getSkipParentCheck()) {
            // In case standard action, need to use standard action code check.
            boolean checkParent;
            try {
                checkParent = docActionExecutionProxy.checkStatus(parentDocNode, docActionConfigure.getActionCode(),
                        docActionConfigure,serialLogonInfo.getLanguageCode(), skipFlag);
            } catch (ServiceEntityInstallationException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
            if (!checkParent) {
                return null;
            }
        }
        /*
         * [Step 2] Process and update each selected item in the batch
         */
        checkUpdateDocMatItemList(selectedMatItemList, allDocMatItemList,
                docItemBatchExecutionRequest, docActionConfigure, docActionExecutionProxy, serialLogonInfo);
        return itemItemExecutionMetadata;
    }

    /**
     * Iterates through each selected item, validates its status, performs execution, and updates its state in the database.
     * This method handles locking for each item during processing to ensure consistency in a concurrent environment.
     *
     * Steps performed:
     * 1. Validate the item's status using the provided action code and configuration.
     * 2. Execute item-specific actions or updates (either through a callback or a default status update mechanism).
     * 3. Update the database with the state of all successfully processed items.
     *
     * @param selectedMatItemList  A list of selected items under a common root node.
     * @param allMatItemList  A complete list of all items under the same root node.
     * @param docItemBatchExecutionRequest  The batch execution request containing details for processing the selected items.
     * @param docActionConfigure  The configuration for the document action to be performed.
     * @param docActionExecutionProxy  The proxy facilitating execution of the document action.
     * @param serialLogonInfo  Logon details for authentication and session context.
     * @return A list of items that were successfully updated in the database.
     */
    private List<ServiceEntityNode> checkUpdateDocMatItemList(List<ServiceEntityNode> selectedMatItemList, List<ServiceEntityNode> allMatItemList, DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
                                                              DocActionConfigure docActionConfigure,
                                                              DocActionExecutionProxy<R, T, Item> docActionExecutionProxy, SerialLogonInfo serialLogonInfo){
        if(ServiceCollectionsHelper.checkNullList(selectedMatItemList)){
            return null;
        }
        int docActionCode = docItemBatchExecutionRequest.getDocActionCode();
        List<ServiceEntityNode> updatedMatItemList = new ArrayList<>();
        for (ServiceEntityNode itemSENode : selectedMatItemList) {
            if (docActionCode > 0) {
                // Lock the item to prevent concurrent modifications for standard doc action
                ServiceConcurrentProxy.writeLock(itemSENode.getUuid(), serialLogonInfo.getRefUserUUID());
                Item docMatItem = (Item) itemSENode;
                try {
                    // Step 1: Validate the item's status
                    boolean itemStatus = checkItem(docMatItem, docActionExecutionProxy, docItemBatchExecutionRequest,
                            docActionConfigure,
                            serialLogonInfo);
                    if (!itemStatus) {
                        continue;
                    }
                    // Step 2: Perform action on the item
                    boolean result;
                    if (docItemBatchExecutionRequest.getDocItemActionCallback() != null) {
                        result = docItemBatchExecutionRequest.getDocItemActionCallback().execute(docMatItem,
                                new DocActionExecutionProxy.ItemSelectionExecutionContext(allMatItemList));
                        if (!result)
                            continue;
                        // Perform the default status update
                        docActionExecutionProxy.checkUpdateItemStatus(docMatItem,
                                docActionCode, serialLogonInfo, false, null);
                        updatedMatItemList.add(docMatItem);
                    } else {
                        // Perform the default status update
                        docActionExecutionProxy.checkUpdateItemStatus(docMatItem,
                                docActionCode, serialLogonInfo, false, null);
                        updatedMatItemList.add(docMatItem);
                    }
                } catch (DocActionException e) {
                    return updatedMatItemList;
                } finally{
                    ServiceConcurrentProxy.unLockWrite(itemSENode.getUuid());
                }
            }
        }
        if (!ServiceCollectionsHelper.checkNullList(updatedMatItemList)) {
            // Update the database with the state of all updated items
            docActionExecutionProxy.getDocumentContentSpecifier().getDocumentManager()
                    .updateSENodeList(updatedMatItemList, serialLogonInfo.getRefUserUUID(),
                            serialLogonInfo.getResOrgUUID());
        }
        return updatedMatItemList;
    }

    private ItemExecutionMetadata<R, T, Item> prepareItemExecMetadata(DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
                                                                      DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest) throws DocActionException {
        try {
            List<ServiceEntityNode> selectedMatItemList = docItemBatchExecutionRequest.getSelectedDocMatItemList();
            if (ServiceCollectionsHelper.checkNullList(selectedMatItemList)) {
                return null;
            }
            // Refresh the selected item list from the database to ensure consistency
            selectedMatItemList = docFlowProxy.refreshLoadDocItemList(selectedMatItemList);
            // Load and refresh the service module and parent node
            DocumentContentSpecifier<R, T, Item> documentContentSpecifier =
                    docActionExecutionProxy.getDocumentContentSpecifier();
            R serviceModule = docItemBatchExecutionRequest.getServiceModule();
            T parentDocNode = documentContentSpecifier.getCoreEntity(serviceModule);
            serviceModule = refreshLoadServiceModule(documentContentSpecifier, serviceModule);
            List<ServiceEntityNode> allDocMatItemList = documentContentSpecifier.getDocMatItemList(serviceModule);
            if (ServiceCollectionsHelper.checkNullList(allDocMatItemList)) {
                return null;
            }
            DocActionConfigure docActionConfigure = docActionExecutionProxy.getDocActionConfigureByCode(docItemBatchExecutionRequest.getDocActionCode(), parentDocNode.getClient());
            DocActionConfigure secondaryDocActionConfigure = null;
            if (docItemBatchExecutionRequest.getSecondaryDocActionCode() > 0) {
                secondaryDocActionConfigure = docActionExecutionProxy.getDocActionConfigureByCode(
                        docItemBatchExecutionRequest.getSecondaryDocActionCode(), parentDocNode.getClient());
            }
            return new ItemExecutionMetadata<>(selectedMatItemList, allDocMatItemList, serviceModule, parentDocNode,
                    docActionConfigure, secondaryDocActionConfigure);
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }

    }

    private static class ItemExecutionMetadata<R, T, Item> {

        protected List<ServiceEntityNode> selectedMatItemList;

        protected List<ServiceEntityNode> allMatItemList;

        protected R serviceModule;

        protected T parentDocNode;

        protected DocActionConfigure docActionConfigure;

        protected DocActionConfigure secondaryDocActionConfigure;

        public ItemExecutionMetadata(List<ServiceEntityNode> selectedMatItemList,
                                     List<ServiceEntityNode> allMatItemList, R serviceModule, T parentDocNode,
                                     DocActionConfigure docActionConfigure, DocActionConfigure secondaryDocActionConfigure) {
            this.selectedMatItemList = selectedMatItemList;
            this.allMatItemList = allMatItemList;
            this.serviceModule = serviceModule;
            this.parentDocNode = parentDocNode;
            this.docActionConfigure = docActionConfigure;
            this.secondaryDocActionConfigure = secondaryDocActionConfigure;
        }

        public List<ServiceEntityNode> getSelectedMatItemList() {
            return selectedMatItemList;
        }

        public void setSelectedMatItemList(List<ServiceEntityNode> selectedMatItemList) {
            this.selectedMatItemList = selectedMatItemList;
        }

        public List<ServiceEntityNode> getAllMatItemList() {
            return allMatItemList;
        }

        public void setAllMatItemList(List<ServiceEntityNode> allMatItemList) {
            this.allMatItemList = allMatItemList;
        }

        public R getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(R serviceModule) {
            this.serviceModule = serviceModule;
        }

        public T getParentDocNode() {
            return parentDocNode;
        }

        public void setParentDocNode(T parentDocNode) {
            this.parentDocNode = parentDocNode;
        }

        public DocActionConfigure getDocActionConfigure() {
            return docActionConfigure;
        }

        public void setDocActionConfigure(DocActionConfigure docActionConfigure) {
            this.docActionConfigure = docActionConfigure;
        }

        public DocActionConfigure getSecondaryDocActionConfigure() {
            return secondaryDocActionConfigure;
        }

        public void setSecondaryDocActionConfigure(DocActionConfigure secondaryDocActionConfigure) {
            this.secondaryDocActionConfigure = secondaryDocActionConfigure;
        }

    }

}
