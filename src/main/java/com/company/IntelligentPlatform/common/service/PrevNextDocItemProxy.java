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
public class PrevNextDocItemProxy {

	@Autowired
	protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

	@Autowired
	protected DocFlowContextProxy docFlowContextProxy;

	protected Logger logger = LoggerFactory.getLogger(PrevNextDocItemProxy.class);

	/**
	 * Method to add prev-next relationship, target item is online, and don't need lock
	 *
	 * @param prevDocMatItem
	 * @param nextDocMatItem
	 * @param serialLogonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean addPrevByNext(DocMatItemNode prevDocMatItem, DocMatItemNode nextDocMatItem,
								 SerialLogonInfo serialLogonInfo) throws DocActionException {
		String prevDocUUID = prevDocMatItem.getUuid();
		int prevDocType = prevDocMatItem.getHomeDocumentType();
		String nextDocUUID = nextDocMatItem.getUuid();
		int nextDocType = nextDocMatItem.getHomeDocumentType();
		// Add next relationship to prev mat item instance
		docFlowContextProxy.updateDocItemNodeUnit(prevDocMatItem, nextDocUUID, nextDocType,
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT, serialLogonInfo);
		// Add prev relationship to next mat item instance
		docFlowContextProxy.updateDocItemNodeUnit(nextDocMatItem, prevDocUUID, prevDocType,
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV, serialLogonInfo);
		return true;
	}

	/**
	 * API to free Prev-next relationship
	 *
	 * @param prevDocUUID
	 * @param serialLogonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean cleanPrevNext(String prevDocUUID, int prevDocType, String nextDocUUID, int nextDocType,
								 SerialLogonInfo serialLogonInfo) throws DocActionException {
		// clean next doc info on prev doc item firstly
		docFlowContextProxy.clearDocItemNodeUnit(prevDocUUID, nextDocUUID, prevDocType, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT,
				serialLogonInfo);
		// Update next doc information
		docFlowContextProxy.clearDocItemNodeUnit(nextDocUUID, prevDocUUID, nextDocType, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
				serialLogonInfo);
		return true;
	}

	/**
	 * API to free Prev-next relationship
	 *
	 * @param prevDocUUID
	 * @param serialLogonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean cleanPrevByNext(String prevDocUUID, int prevDocType, DocMatItemNode nextMatItemNode,
								   SerialLogonInfo serialLogonInfo) throws DocActionException {
		// clean next doc info on prev doc item firstly
		docFlowContextProxy.clearDocItemNodeUnit(prevDocUUID, nextMatItemNode.getUuid(), prevDocType,
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT, serialLogonInfo);
		// update the next doc instance: remove the prev doc information in current next doc mat item instance
		docFlowContextProxy.clearDocItemNodeUnit(nextMatItemNode, prevDocUUID, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
				serialLogonInfo);
		return true;
	}

	/**
	 * API to free Prev-next relationship
	 *
	 * @param nextDocUUID
	 * @param serialLogonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean cleanNextByPrev(String nextDocUUID, int nextDocType, DocMatItemNode prevMatItemNode,
								   SerialLogonInfo serialLogonInfo) throws DocActionException {
		// clean next doc info on prev doc item firstly
		docFlowContextProxy.clearDocItemNodeUnit(nextDocUUID, prevMatItemNode.getUuid(), nextDocType,
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV, serialLogonInfo);
		// update the next doc instance: remove the prev doc information in current next doc mat item instance
		docFlowContextProxy.clearDocItemNodeUnit(prevMatItemNode, nextDocUUID,
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT,
				serialLogonInfo);
		return true;
	}

	/**
	 * API to free Prev-next relationship
	 *
	 * @param serialLogonInfo
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public boolean cleanPrevNext(DocMatItemNode prevMatItemNode, DocMatItemNode nextMatItemNode,
								   SerialLogonInfo serialLogonInfo) throws DocActionException {
		// clean next doc info on prev doc item firstly
		docFlowContextProxy.clearDocItemNodeUnit(prevMatItemNode, nextMatItemNode.getUuid(),
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT, serialLogonInfo);
		// Update next doc information
		docFlowContextProxy.clearDocItemNodeUnit(nextMatItemNode, prevMatItemNode.getUuid(),
				StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV,
				serialLogonInfo);
		return true;
	}

}
