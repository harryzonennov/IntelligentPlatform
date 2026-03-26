package com.company.IntelligentPlatform.logistics.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryMessageHelper;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServicePDFReportHelper;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class InboundDeliveryReportService {

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	InboundDeliveryMessageHelper inboundDeliveryMessageHelper;
	
	@Autowired
	protected ServicePDFReportHelper servicePDFReportHelper;
	
	protected Map<String, String> getDefPreWarnMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = InboundDeliveryUIModel.class.getResource("").getPath();
		String resFileName = InboundDelivery.SENAME;
		Map<String, String> preWarnMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return preWarnMap;
	}

	protected Map<String, String> getItemPropMap() throws IOException {
		Locale locale = ServiceLanHelper.getDefault();
		String path = InboundDeliveryUIModel.class.getResource("").getPath();
		String resFileName = "InboundItem";
		Map<String, String> itemPropMap = serviceDropdownListHelper
				.getDropDownMap(path, resFileName, locale);
		return itemPropMap;
	}

	/**
	 * Logic to build compound default map for generate the site settlement map
	 * 
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws IOException
	 * @throws ServiceEntityConfigureException 
	 */
	public Map<String, Object> buildReportMap(
			InboundDeliveryUIModel inboundDeliveryUIModel,
			HostCompany hostCompany, LogonUser currentUser, String currentDate)
			throws ServiceEntityInstallationException, IOException, ServiceEntityConfigureException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String inboundDeliveryHead = ServiceEntityStringHelper.EMPTYSTRING;
		if (ServiceEntityStringHelper.checkNullString(hostCompany.getComReportTitle())) {
			inboundDeliveryHead = hostCompany.getName();
		} else {
			inboundDeliveryHead = hostCompany.getComReportTitle();
		}
		String inboundDeliveryTitle =  inboundDeliveryMessageHelper
				.getMessage(InboundDeliveryMessageHelper.REPORT_TITLE);
		parameters.put("inboundDeliveryHead", inboundDeliveryHead);
		parameters.put("inboundDeliveryTitle", inboundDeliveryTitle);
		if (hostCompany.getComLogo() != null
				&& hostCompany.getComLogo().length > 0) {
			InputStream sbs = new ByteArrayInputStream(hostCompany.getComLogo());
			parameters.put("comLogo", sbs);
		}
		parameters.put("inboundDeliveryId", inboundDeliveryUIModel.getId());
		parameters.put("warehouseName", inboundDeliveryUIModel.getRefWarehouseId()
				+ "-" + inboundDeliveryUIModel.getRefWarehouseName());
		parameters.put("freightCharge", inboundDeliveryUIModel.getFreightCharge());
		parameters.put("freightChargeTypeValue", inboundDeliveryUIModel.getFreightChargeTypeValue());
		parameters.put("shippingPoint", inboundDeliveryUIModel.getShippingPoint());
		parameters.put("shippingTime", inboundDeliveryUIModel.getShippingTime());		
//		if(inboundDeliveryUIModel.getApprovedDate() != null){
//			parameters.put("approvedDate",
//					DefaultDateFormatConstant.DATE_MIN_FORMAT
//							.format(inboundDeliveryUIModel.getApprovedDate()));
////		}
//		parameters
//				.put("processedBy", inboundDeliveryUIModel.getProcessByName());
//		if(inboundDeliveryUIModel.getRecordStoreDate() != null){
//			parameters.put("processedDate",
//					DefaultDateFormatConstant.DATE_MIN_FORMAT
//							.format(inboundDeliveryUIModel.getRecordStoreDate()));
//		}
		parameters.put("grossDeclaredValue", inboundDeliveryUIModel.getGrossDeclaredValue());
		parameters.put("grossInboundFee", inboundDeliveryUIModel.getGrossInboundFee());
		parameters.put("comFax", hostCompany.getFax());
		parameters.put("comTelephone", hostCompany.getTelephone());
		parameters.put("comAddress", hostCompany.getAddress());
		parameters.put("currentUser", currentUser.getName());
		parameters.put("currentDate", currentDate);	
		Map<String, String> rawPropertiesMap = getDefPreWarnMap();
		Map<String, String> itemPropertiesMap = getItemPropMap();
		ServiceCollectionsHelper.mergeMap(rawPropertiesMap, itemPropertiesMap);
		servicePDFReportHelper.getDefaultReportTitleInfo(parameters, 
				rawPropertiesMap, InboundDelivery.SENAME,
				"", hostCompany);
		return parameters;
	}

}
