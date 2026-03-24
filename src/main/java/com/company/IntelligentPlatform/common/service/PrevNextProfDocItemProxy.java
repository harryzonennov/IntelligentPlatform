package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class PrevNextProfDocItemProxy {

    @Autowired
    protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

    @Autowired
    protected DocFlowContextProxy docFlowContextProxy;

    protected Logger logger = LoggerFactory.getLogger(PrevNextProfDocItemProxy.class);

    /**
     * Method to add prev-next relationship
     *
     * @param nextProfDocUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean addPrevNextProfRelation(String nextProfDocUUID, int nextProfDocType, int prevProfDocType,
                                    String prevProfDocMatUUID, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        // Add prof next relationship to prof mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(nextProfDocUUID, prevProfDocMatUUID, nextProfDocType, prevProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF, serialLogonInfo);
        // Add next relationship to prev mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(prevProfDocMatUUID, nextProfDocUUID, prevProfDocType, nextProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF, serialLogonInfo);
        return true;
    }

    /**
     * Add prev-next relationship, target item is online, and don't need lock
     *
     * @param nextProfDocMatItem
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean addPrevProfByNext(DocMatItemNode nextProfDocMatItem, int prevProfDocType,
                                    String prevProfDocMatUUID, SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        String nextProfDocUUID = nextProfDocMatItem.getUuid();
        int nextProfDocType = nextProfDocMatItem.getHomeDocumentType();
        // Add prof next relationship to prof mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(nextProfDocMatItem, prevProfDocMatUUID, prevProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF, serialLogonInfo);
        // Add next relationship to prev mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(prevProfDocMatUUID, nextProfDocUUID, prevProfDocType, nextProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF, serialLogonInfo);
        return true;
    }


    /**
     * Method to add prev-next relationship
     *
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean addPrevNextProfRelation(DocMatItemNode nextProfDocMatItem, DocMatItemNode prevProfDocMatItem,
                                           SerialLogonInfo serialLogonInfo)
            throws DocActionException {
        // Add prof next relationship to prof mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(nextProfDocMatItem, prevProfDocMatItem.getUuid(),
                prevProfDocMatItem.getHomeDocumentType(),
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF, serialLogonInfo);
        // Add next relationship to prev mat item instance
        docFlowContextProxy.updateDocItemNodeUnit(prevProfDocMatItem, nextProfDocMatItem.getUuid(),
                nextProfDocMatItem.getHomeDocumentType(),
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF, serialLogonInfo);
        return true;
    }
    /**
     * API to free Prev-next relationship
     *
     * @param prevProfDocUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean cleanPrevNextProf(String prevProfDocUUID, int prevProfDocType, String nextProfDocUUID,
                                 int nextProfDocType, SerialLogonInfo serialLogonInfo) throws DocActionException {
        // clean next doc info on prev doc item firstly
        docFlowContextProxy.clearDocItemNodeUnit(prevProfDocUUID, nextProfDocUUID, prevProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF,
                serialLogonInfo);
        // Update next doc information
        docFlowContextProxy.clearDocItemNodeUnit(nextProfDocUUID, prevProfDocUUID, nextProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF,
                serialLogonInfo);
        return true;
    }

    /**
     * Free Prev-next prof relationship by given the next
     *
     * @param prevProfDocUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean cleanPrevProfByNext(String prevProfDocUUID, int prevProfDocType, DocMatItemNode nextProfMatItemNode,
                                       SerialLogonInfo serialLogonInfo) throws DocActionException {
        // clean next doc info on prev doc item firstly
        docFlowContextProxy.clearDocItemNodeUnit(prevProfDocUUID, nextProfMatItemNode.getUuid(), prevProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF, serialLogonInfo);
        // Update next doc information
        docFlowContextProxy.clearDocItemNodeUnit(nextProfMatItemNode, prevProfDocUUID, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF,
                serialLogonInfo);
        return true;
    }

    /**
     * Free Prev-next prof relationship by given the next
     *
     * @param nextProfDocUUID
     * @param serialLogonInfo
     * @return
     * @throws ServiceEntityConfigureException
     */
    public boolean cleanNextProfByPrev(String nextProfDocUUID, int nextProfDocType, DocMatItemNode prevProfMatItemNode,
                                   SerialLogonInfo serialLogonInfo) throws DocActionException {
        // clean next doc info on prev doc item firstly
        docFlowContextProxy.clearDocItemNodeUnit(nextProfDocUUID, prevProfMatItemNode.getUuid(), nextProfDocType,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF, serialLogonInfo);
        // Update next doc information
        docFlowContextProxy.clearDocItemNodeUnit(prevProfMatItemNode, nextProfDocUUID,
                StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF,
                serialLogonInfo);
        return true;
    }

}
