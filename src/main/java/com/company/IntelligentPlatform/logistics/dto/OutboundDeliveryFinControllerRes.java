package com.company.IntelligentPlatform.logistics.dto;

import java.util.HashMap;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.Delivery;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CustomerManager;
import com.company.IntelligentPlatform.common.controller.IFinanceControllerResource;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Inbound Delivery Manager fin controller resource, should be attribute of
 * <inbound delivery editor controller>
 * 
 * @author Zhang,hang
 * 
 */
@Service
public class OutboundDeliveryFinControllerRes implements
		IFinanceControllerResource {

	public static final int ACCOBJ_KEY_VENDOR = 1;

	public static final String ACCOBJ_LABEL_VENDOR = "Vendor";

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected CustomerManager customerManager;

	@Override
	public Map<Integer, String> getFinAccObjectMap() {
		Map<Integer, String> accObjectMap = new HashMap<Integer, String>();
		accObjectMap.put(new Integer(ACCOBJ_KEY_VENDOR), ACCOBJ_LABEL_VENDOR);
		return accObjectMap;
	}

	@Override
	public Map<Integer, String> getProcessCodeMap() {		
		Map<Integer, String> processCodeMap = new HashMap<Integer, String>();
		processCodeMap.put(new Integer(Delivery.PROCESSCODE_PROCESSDELIVERY),
				Delivery.LABEL_PC_PROCESSDELIVERY);
		return processCodeMap;
	}

	@Override
	public ServiceEntityNode getFinAccObjectByKey(String baseUUID, String client, int key)
			throws ServiceEntityConfigureException {
//		if (key == ACCOBJ_KEY_VENDOR) {
//			OutboundDelivery outboundDelivery = (OutboundDelivery) outboundDeliveryManager
//					.getEntityNodeByKey(baseUUID,
//							IServiceEntityNodeFieldConstant.UUID,
//							OutboundDelivery.NODENAME, client, null);
//			Account account = customerManager.getAllCustomer(
//					outboundDelivery.getRefCustomerUUID(), outboundDelivery.getClient(),
//					outboundDelivery.getRefCustomerAccountType());
//			return account;
//		}
		return null;
	}

	@Override
	public ServiceEntityNode getDocument(String baseUUID, String client)
			throws ServiceEntityConfigureException {
		return outboundDeliveryManager
				.getEntityNodeByUUID(baseUUID,
						OutboundDelivery.NODENAME, client);
	}

	@Override
	public int getDocumentType() throws ServiceEntityConfigureException {
		return IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY;
	}
}
