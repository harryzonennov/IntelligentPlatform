package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.SpringContextBeanService;

/**
 * Factory class to generate logon User message process factory
 * @author Zhang,Hang
 *
 */
@Service
public class MessageCenterProcessFactory {

	protected static final String finAccountToAuditMessageHandler = "finAccountToAuditMessageHandler";

	protected static final String  finAccountToRecordMessageHandler = "finAccountToRecordMessageHandler";

	protected static final String  finAccountToVerifyMessageHandler = "finAccountToVerifyMessageHandler";	
	
	protected static final String  transSiteStoreToDeliveryMessageHandler = "transSiteStoreToDeliveryMessageHandler";
	
	protected static final String  transitOrderDeliveryToTraceMessageHandler = "transitOrderDeliveryToTraceMessageHandler";
	
	protected static final String  bookingNoteDeliveryToTraceMessageHandler = "bookingNoteDeliveryToTraceMessageHandler";
	
	protected static final String  vehicleRunOrderContractToSettleMessageHandler = "vehicleRunOrderContractToSettleMessageHandler";
	
	protected static final String  warehouseStoreSafetyWarnningMessageHandler = "warehouseStoreSafetyWarnningMessageHandler";
	
	@Autowired
	protected SpringContextBeanService springContextBeanService;


	/**
	 * Generate message process handler
	 * 
	 * @param messageCategory
	 * @return
	 */
	public IUserMessageProcessHandler getMessageProcessHandler(
			int messageCategory) {
		IUserMessageProcessHandler userMessageProcessHandler = null;
		if (messageCategory == IMessageCategory.FINACC_TO_RECORD) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(finAccountToRecordMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.FINACC_TO_VERIFY) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(finAccountToVerifyMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.FINACC_TO_AUDIT) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(finAccountToAuditMessageHandler);
			return userMessageProcessHandler;
		}		
		if (messageCategory == IMessageCategory.TRANS_STORE_TO_DELIVERY) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(transSiteStoreToDeliveryMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.TRANSIT_DELIVERY_TOTRACE) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(transitOrderDeliveryToTraceMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.BOOK_DELIVERY_TOTRACE) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(vehicleRunOrderContractToSettleMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.VEH_CON_TO_SETTLE) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(vehicleRunOrderContractToSettleMessageHandler);
			return userMessageProcessHandler;
		}
		if (messageCategory == IMessageCategory.WARESTORE_SAFETY) {
			userMessageProcessHandler = (IUserMessageProcessHandler) springContextBeanService.getBean(warehouseStoreSafetyWarnningMessageHandler);
			return userMessageProcessHandler;
		}
		return null;
	}

}
