package com.company.IntelligentPlatform.common.service;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocumentContent;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocActionChainProxy {

    public <R extends ServiceModule> void executeDocActionChainToTargetStatus(R serviceModule, DocActionExecutionProxy<R, ?, ?> docActionExecutionProxy, int targetStatus, SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, DocActionException {
        List<DocActionConfigure> docActionConfigureList = docActionExecutionProxy.getDefDocActionConfigureList();
        DocumentContentSpecifier<R, ?, ?> documentContentSpecifier = docActionExecutionProxy.getDocumentContentSpecifier();
        DocumentContent documentContent = (DocumentContent) documentContentSpecifier.getCoreEntity(serviceModule);
        int currenStatus = documentContent.getStatus();
        if (currenStatus == targetStatus) {
            return;
        }
        List<List<DocActionConfigureContext>> docActionConfigureContextArray = getDocActionChainRecursive(docActionExecutionProxy, currenStatus, targetStatus, new ArrayList<>(), serialLogonInfo.getClient());
        List<DocActionConfigureContext> docActionConfigureContextList = filterValidDocActionChain(docActionConfigureContextArray, currenStatus);
        ServiceCollectionsHelper.traverseListInterrupt(docActionConfigureContextList, docActionConfigureContext -> {
            // Execute each doc Action configure
            try {
                docActionExecutionProxy.defExecuteActionCore(serviceModule,
                        docActionConfigureContext.getDocActionConfigure().getActionCode(), null, null, serialLogonInfo);
            } catch (ServiceModuleProxyException e) {
                throw new DocActionException(DocActionException.PARA_WRG_CONFIG, e.getErrorMessage());
            }
            return true;
        });
    }

    public List<DocActionConfigureContext> filterValidDocActionChain(List<List<DocActionConfigureContext>> docActionConfigureContextArray, int currenStatus) throws DocActionException {
        List<DocActionConfigureContext> resultDocActionConfigureList = new ArrayList<>();
        ServiceCollectionsHelper.traverseListInterrupt(docActionConfigureContextArray, docActionConfigureContextList -> {
            DocActionConfigureContext firstDocActionConfigureContext = docActionConfigureContextList.get(0);
            if (currenStatus == firstDocActionConfigureContext.getPreStatus()) {
                resultDocActionConfigureList.addAll(docActionConfigureContextList);
                return false;
            } else {
                return true;
            }
        });
        return resultDocActionConfigureList;
    }

    public <R extends ServiceModule> List<List<DocActionConfigureContext>> getDocActionChainRecursive(DocActionExecutionProxy<R, ?, ?> docActionExecutionProxy,
                        int currenStatus, int targetStatus, List<DocActionConfigureContext> inputActionConfigureContextList, String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException, DocActionException {
        List<DocActionConfigure> targetDocActionConfigureList = docActionExecutionProxy.getDocActionConfigureByTargetStatus(targetStatus, client);
        if (ServiceCollectionsHelper.checkNullList(targetDocActionConfigureList)) {
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, targetStatus);
        }
        List<List<DocActionConfigureContext>> resultConfigureContextArray = new ArrayList<>();
        ServiceCollectionsHelper.traverseListInterrupt(targetDocActionConfigureList, targetDocActionConfigure -> {
            ServiceCollectionsHelper.traverseListInterrupt(targetDocActionConfigure.getPreStatusList(), tmpTargetStatus -> {
                List<DocActionConfigureContext> docActionConfigureContextList = newDocActionConfigureContextList(tmpTargetStatus, targetDocActionConfigure, inputActionConfigureContextList);
                try {
                    if (checkDocActionDeadEnd(tmpTargetStatus, docActionConfigureContextList, docActionExecutionProxy, client)) {
                        System.out.println("Detected loop in duplicate history, target status:" + tmpTargetStatus + " action code:" + targetDocActionConfigure.getActionCode());
                        return true;
                    }
                    if (checkDocActionConfigureMatches(targetDocActionConfigure, currenStatus)) {
                        System.out.println("matches the currentStatus" + currenStatus + " in the DocAction code: " + targetDocActionConfigure.getActionCode());
                        resultConfigureContextArray.add(docActionConfigureContextList);
                        return true;
                    }
                    List<List<DocActionConfigureContext>> subConfigureContextArray = getDocActionChainRecursive(docActionExecutionProxy,
                            currenStatus, tmpTargetStatus, docActionConfigureContextList, client);
                    if (!ServiceCollectionsHelper.checkNullList(subConfigureContextArray)) {
                        resultConfigureContextArray.addAll(subConfigureContextArray);
                    } else {
                        resultConfigureContextArray.add(docActionConfigureContextList);
                    }
                } catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
                    throw new DocActionException(DocActionException.PARA_WRG_CONFIG, e.getErrorMessage());
                }
                return true;
            });
            return true;
        });
        return resultConfigureContextArray;
    }

    private boolean checkDocActionConfigureMatches(DocActionConfigure docActionConfigure, int currentStatus) {
        return docActionConfigure.getPreStatusList().contains(currentStatus);
    }

    private List<DocActionConfigureContext> newDocActionConfigureContextList(int preStatus, DocActionConfigure docActionConfigure,
                                                                             List<DocActionConfigureContext> existedDocActionConfigureContextList) {
        DocActionConfigureContext docActionConfigureContext = new DocActionConfigureContext(docActionConfigure, preStatus, docActionConfigure.getTargetStatus());
        if (!ServiceCollectionsHelper.checkNullList(existedDocActionConfigureContextList)) {
            List<DocActionConfigureContext> docActionConfigureContextList = new ArrayList<>(existedDocActionConfigureContextList);
            docActionConfigureContextList.add(0, docActionConfigureContext);
            return docActionConfigureContextList;
        } else {
            return ServiceCollectionsHelper.asList(docActionConfigureContext);
        }
    }

    /**
     * The logic to check if the current new target status is a dead end in the list of docActionConfigureContext
     * @param newTargetStatus
     * @param docActionConfigureContextList
     * @return
     */
    private <R extends ServiceModule> boolean checkDocActionDeadEnd(int newTargetStatus,
                                                                    List<DocActionConfigureContext> docActionConfigureContextList,
                                                                    DocActionExecutionProxy<R, ?, ?> docActionExecutionProxy, String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        for (DocActionConfigureContext docActionConfigureContext : docActionConfigureContextList) {
            // Case 1: in case the new target status happen in the history, then this line is dead end
            if (docActionConfigureContext.getTargetStatus() == newTargetStatus) {
                return true;
            }
            // Case 2: in case the no DocActionConfigure can reach this new target status, then this line is dead end
            List<DocActionConfigure> newTargetDocActionConfigureList = docActionExecutionProxy.getDocActionConfigureByTargetStatus(newTargetStatus, client);
            if (ServiceCollectionsHelper.checkNullList(newTargetDocActionConfigureList)) {
                return true;
            }
        }
        return false;
    }

    public static class DocActionConfigureContext {

        private DocActionConfigure docActionConfigure;

        private int targetStatus;

        private int preStatus;

        public DocActionConfigureContext(DocActionConfigure docActionConfigure, int preStatus, int targetStatus) {
            this.docActionConfigure = docActionConfigure;
            this.preStatus = preStatus;
            this.targetStatus = targetStatus;
        }

        public DocActionConfigure getDocActionConfigure() {
            return docActionConfigure;
        }

        public void setDocActionConfigure(DocActionConfigure docActionConfigure) {
            this.docActionConfigure = docActionConfigure;
        }

        public int getTargetStatus() {
            return targetStatus;
        }

        public void setTargetStatus(int targetStatus) {
            this.targetStatus = targetStatus;
        }

        public int getPreStatus() {
            return preStatus;
        }

        public void setPreStatus(int preStatus) {
            this.preStatus = preStatus;
        }
    }

}
