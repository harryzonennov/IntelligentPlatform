package com.company.IntelligentPlatform.logistics.service;

import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.logistics.model.Delivery;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Document class which could generate delivery.
 * 
 * @author Zhang,hang
 *
 */
@Service
public class DeliveryDocumentManager extends ServiceEntityManager {

	/**
	 * generate the log for documents from relative delivery
	 * 
	 * @param delivery
	 * @param deliveryItemList
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @throws ServiceEntityConfigureException
	 */
	public void generateDeliveryLog(Delivery delivery,
			List<ServiceEntityNode> deliveryItemList, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException {
		// Implementation in sub class
		
	}

	/**
	 * Logic of generate batch of inbound delivery from reference document.
	 * 
	 * @param documentUUID
	 * @param client
	 * @return
	 */
	public List<ServiceEntityNode> generateInboundDeliveryByDocument(
			String documentUUID, String client) throws InboundDeliveryException {
		// Implemented in sub-class
		return null;
	}

	/**
	 * Logic of generate batch of outbound delivery from reference document.
	 * 
	 * @param documentUUID
	 * @param client
	 * @return
	 */
	public List<ServiceEntityNode> generateOutboundDeliveryByDocument(
			String documentUUID, String client)
			throws OutboundDeliveryException {
		// Implemented in sub-class
		return null;
	}
	

	
	/**
	 * Logic of update reference document from delivery information
	 * @param delivery
	 * @param deliveryItemList
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void updateDocumentsFromDelivery(Delivery delivery,
			List<ServiceEntityNode> deliveryItemList, String logonUserUUID,
			String organizationUUID) {

	}

	/**
	 *
	 * @param warehouse
	 * @param resourceID
	 * @param autoApproveFlag
	 * @param logonUser
	 * @param organization
	 * @return List of delivery order which has been recorded successfully
	 * @throws SearchConfigureException
	 * @throws ServiceEntityConfigureException
	 */
	public String scanBarcodeToRecordDelivery(String barcode,
			Warehouse warehouse, String resourceID, boolean autoApproveFlag,
			LogonUser logonUser, Organization organization)
			throws SearchConfigureException, ServiceEntityConfigureException {
		// Implemented in sub-class
		return null;
	}
	
	
}
