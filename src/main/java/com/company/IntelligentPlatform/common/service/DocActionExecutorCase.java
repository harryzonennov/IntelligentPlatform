package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.List;

@Service
public class DocActionExecutorCase<R extends ServiceModule, T extends ServiceEntityNode,
        Item extends ServiceEntityNode> {

    /**
     * [Internal method] Check logic for each item before execute item action
     *
     * @return
     */
    protected boolean checkItem(Item docMatItem, DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
                              DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest,
                                DocActionConfigure docActionConfigure,
                              SerialLogonInfo serialLogonInfo) throws DocActionException {
        // in case standard doc action
        boolean itemStatus = false;
        int docActionCode = docItemBatchExecutionRequest.getDocActionCode();
        if (docItemBatchExecutionRequest.getCustomItemCheck() != null) {
            // In case custom item check logic specified
            itemStatus = docItemBatchExecutionRequest.getCustomItemCheck().execute(docMatItem);
        } else {
            // Use standard action item check method
            try {
                itemStatus = docActionExecutionProxy.checkItemStatus(docMatItem, docActionCode,docActionConfigure,
                        serialLogonInfo.getLanguageCode(), true, serialLogonInfo.getClient());
            } catch (ServiceEntityInstallationException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
            }
        }
        return itemStatus;
    }

    protected R refreshLoadServiceModule(
            DocumentContentSpecifier<R, T, Item> documentContentSpecifier, R serviceModule) throws DocActionException {
        try {
            T parentDocNode = documentContentSpecifier.getCoreEntity(serviceModule);
            return documentContentSpecifier.loadServiceModule(parentDocNode.getUuid(), parentDocNode.getClient());
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        } catch (ServiceModuleProxyException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

    /**
     * [Internal method] Check logic for each item if could be calculated for update parent node
     *
     * @return
     */
    protected boolean checkItemForCalculate(Item docMatItem,DocActionExecutionProxy<R, T, Item> docActionExecutionProxy,
                                          DocItemBatchExecutionRequest<R, T, Item> docItemBatchExecutionRequest) throws DocActionException {
        boolean itemCheckResult = false;
        /*
         * [Priority 1] In case custom item check logic to calculate is specified
         */
        if (docItemBatchExecutionRequest.getCustomItemCheckToCalculate() != null) {
            itemCheckResult = docItemBatchExecutionRequest.getCustomItemCheckToCalculate().execute(docMatItem);
            return itemCheckResult;
        }
        /*
         * [Priority 2] Use standard action item check
         */
        int docActionCode = docItemBatchExecutionRequest.getDocActionCode();
        itemCheckResult = docActionExecutionProxy.checkItemTargetStatus(docMatItem, docActionCode);
        return itemCheckResult;
    }

    public static class DocItemBatchExecutionRequest<R extends ServiceModule, T extends ServiceEntityNode, Item extends ServiceEntityNode> {

        private R serviceModule;

        private List<ServiceEntityNode> selectedDocMatItemList;

        private int docActionCode;

        private int secondaryDocActionCode;

        private boolean skipFlag = false;

        private boolean skipParentCheck = false;

        private DocActionExecutionProxy.DocActionExecution<T> docActionCallback;

        private DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback;

        private DocActionExecutionProxy.DocItemCheck<Item> customItemCheck;

        private DocActionExecutionProxy.DocItemCheck<Item> customItemCheckToCalculate;

        public DocItemBatchExecutionRequest() {
        }

        public DocItemBatchExecutionRequest(R serviceModule, List<ServiceEntityNode> selectedDocMatItemList,
                                            int docActionCode,
                                            DocActionExecutionProxy.DocActionExecution<T> docActionCallback,
                                            DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback) {
            this.serviceModule = serviceModule;
            this.selectedDocMatItemList = selectedDocMatItemList;
            this.docActionCode = docActionCode;
            this.docActionCallback = docActionCallback;
            this.docItemActionCallback = docItemActionCallback;
        }

        public DocItemBatchExecutionRequest(R serviceModule, List<ServiceEntityNode> selectedDocMatItemList,
                                            int docActionCode, int secondaryDocActionCode,
                                            DocActionExecutionProxy.DocActionExecution<T> docActionCallback,
                                            DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback) {
            this.serviceModule = serviceModule;
            this.selectedDocMatItemList = selectedDocMatItemList;
            this.docActionCode = docActionCode;
            this.secondaryDocActionCode = secondaryDocActionCode;
            this.docActionCallback = docActionCallback;
            this.docItemActionCallback = docItemActionCallback;
        }

        public DocItemBatchExecutionRequest(R serviceModule, List<ServiceEntityNode> selectedDocMatItemList,
                                            int docActionCode,
                                            DocActionExecutionProxy.DocActionExecution<T> docActionCallback,
                                            DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback,
                                            DocActionExecutionProxy.DocItemCheck<Item> customItemCheck,
                                            DocActionExecutionProxy.DocItemCheck<Item> customItemCheckToCalculate) {
            this.serviceModule = serviceModule;
            this.selectedDocMatItemList = selectedDocMatItemList;
            this.docActionCode = docActionCode;
            this.docActionCallback = docActionCallback;
            this.docItemActionCallback = docItemActionCallback;
            this.customItemCheck = customItemCheck;
            this.customItemCheckToCalculate = customItemCheckToCalculate;
        }

        public R getServiceModule() {
            return serviceModule;
        }

        public void setServiceModule(R serviceModule) {
            this.serviceModule = serviceModule;
        }

        public boolean getSkipFlag() {
            return skipFlag;
        }

        public void setSkipFlag(boolean skipFlag) {
            this.skipFlag = skipFlag;
        }

        public List<ServiceEntityNode> getSelectedDocMatItemList() {
            return selectedDocMatItemList;
        }

        public void setSelectedDocMatItemList(List<ServiceEntityNode> selectedDocMatItemList) {
            this.selectedDocMatItemList = selectedDocMatItemList;
        }

        public int getDocActionCode() {
            return docActionCode;
        }

        public int getSecondaryDocActionCode() {
            return secondaryDocActionCode;
        }

        public void setSecondaryDocActionCode(int secondaryDocActionCode) {
            this.secondaryDocActionCode = secondaryDocActionCode;
        }

        public void setDocActionCode(int docActionCode) {
            this.docActionCode = docActionCode;
        }

        public DocActionExecutionProxy.DocActionExecution<T> getDocActionCallback() {
            return docActionCallback;
        }

        public void setDocActionCallback(DocActionExecutionProxy.DocActionExecution<T> docActionCallback) {
            this.docActionCallback = docActionCallback;
        }

        public DocActionExecutionProxy.DocItemActionExecution<Item> getDocItemActionCallback() {
            return docItemActionCallback;
        }

        public void setDocItemActionCallback(
                DocActionExecutionProxy.DocItemActionExecution<Item> docItemActionCallback) {
            this.docItemActionCallback = docItemActionCallback;
        }

        public DocActionExecutionProxy.DocItemCheck<Item> getCustomItemCheck() {
            return customItemCheck;
        }

        public void setCustomItemCheck(DocActionExecutionProxy.DocItemCheck<Item> customItemCheck) {
            this.customItemCheck = customItemCheck;
        }

        public DocActionExecutionProxy.DocItemCheck<Item> getCustomItemCheckToCalculate() {
            return customItemCheckToCalculate;
        }

        public void setCustomItemCheckToCalculate(
                DocActionExecutionProxy.DocItemCheck<Item> customItemCheckToCalculate) {
            this.customItemCheckToCalculate = customItemCheckToCalculate;
        }

        public boolean getSkipParentCheck() {
            return skipParentCheck;
        }

        public void setSkipParentCheck(boolean skipParentCheck) {
            this.skipParentCheck = skipParentCheck;
        }
    }

}
