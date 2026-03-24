package com.company.IntelligentPlatform.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceDocConfigParaFactory {

	protected static final String salesOrderConfigParaProxy = "salesOrderConfigParaProxy";

	protected static final String inboundDeliveryConfigParaProxy = "inboundDeliveryConfigParaProxy";

	protected static final String  outboundDeliveryConfigParaProxy = "outboundDeliveryConfigParaProxy";

	protected static final String purchaseOrderConfigParaProxy = "purchaseOrderConfigParaProxy";

	protected static final String serviceHighChartXTimeSlotParaProxy = "serviceHighChartXTimeSlotParaProxy";
	
	protected static final String  warehouseStoreItemLogConfigParaProxy = "warehouseStoreItemLogConfigParaProxy";
	
	protected ServiceDropdownListHelper serviceDropdownListHelper;
	
	protected SpringContextBeanService springContextBeanService;
	
	public ServiceDocConfigParaProxy getDocConfigProxy2(int documentType)
			throws ServiceDocConfigureException {
		ServiceDocConfigParaProxy paraProxy = null;
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESORDER) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(salesOrderConfigParaProxy);
			return paraProxy;
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(inboundDeliveryConfigParaProxy);
			return paraProxy;
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(outboundDeliveryConfigParaProxy);
			return paraProxy;
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASE) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(purchaseOrderConfigParaProxy);
			return paraProxy;
		}
		if (documentType == IServiceDocConfigureResourceConstants.DOCRESOURCE_TYPE_WAREHOUSESTORELOG) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(warehouseStoreItemLogConfigParaProxy);
			return paraProxy;
		}
		if (documentType == IServiceDocConfigureResourceConstants.DOCRESOURCE_TYPE_XTIME_HIGHCHART) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(serviceHighChartXTimeSlotParaProxy);
			return paraProxy;
		}
		throw new ServiceDocConfigureException(
				ServiceDocConfigureException.PARA_INVALID_DOCTYPE, documentType);
	}
	
	public ServiceDocConfigParaProxy getDocConfigProxy(String sourceID)
			throws ServiceDocConfigureException {
		if(ServiceEntityStringHelper.checkNullString(sourceID)){
			throw new ServiceDocConfigureException(
					ServiceDocConfigureException.PARA_INVALID_DOCTYPE, ServiceEntityStringHelper.EMPTYSTRING);
		}
		ServiceDocConfigParaProxy paraProxy = null;		
		if (sourceID.equals(IServiceResourceConstants.ID_SalseOrder)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(salesOrderConfigParaProxy);
			return paraProxy;
		}
		if (sourceID.equals(IServiceResourceConstants.ID_InboundDelivery)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(inboundDeliveryConfigParaProxy);
			return paraProxy;
		}
		if (sourceID.equals(IServiceResourceConstants.ID_OutboundDelivery)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(outboundDeliveryConfigParaProxy);
			return paraProxy;
		}
		if (sourceID.equals(IServiceResourceConstants.ID_PurchaseOrder)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(purchaseOrderConfigParaProxy);
			return paraProxy;
		}
		if (sourceID.equals(IServiceResourceConstants.ID_WarehouseStoreLog)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(warehouseStoreItemLogConfigParaProxy);
			return paraProxy;
		}
		if (sourceID.equals(IServiceResourceConstants.ID_ServiceHighChartXTimeSlotProxy)) {
			paraProxy = (ServiceDocConfigParaProxy) springContextBeanService.getBean(serviceHighChartXTimeSlotParaProxy);
			return paraProxy;
		}
		throw new ServiceDocConfigureException(
				ServiceDocConfigureException.PARA_INVALID_DOCTYPE, sourceID);
	}
	
	/**
	 * Get all available service resource id list
	 * @return
	 */
	public List<String> getAllServiceResourceID(){
		List<String> resultList = new ArrayList<String>();
		resultList.add(IServiceResourceConstants.ID_SalseOrder);
		resultList.add(IServiceResourceConstants.ID_InboundDelivery);
		resultList.add(IServiceResourceConstants.ID_OutboundDelivery);
		resultList.add(IServiceResourceConstants.ID_PurchaseOrder);
		resultList.add(IServiceResourceConstants.ID_WarehouseStoreLog);
		resultList.add(IServiceResourceConstants.ID_ServiceHighChartXTimeSlotProxy);
		return resultList;
	}
	
	/**
	 * Logic to return all the service id-name hash map.
	 * @return
	 */
	public Map<String, String> generateServiceResourceMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = IServiceResourceConstants.class.getResource("")
				.getPath();
		String resFileName = "ServiceResource";
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}
	

}
