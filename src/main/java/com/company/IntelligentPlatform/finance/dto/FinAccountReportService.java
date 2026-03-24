package com.company.IntelligentPlatform.finance.dto;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.company.IntelligentPlatform.finance.service.FinAccountMessageHelper;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class FinAccountReportService {

	public static final int REP_TYPE_LOAD = 1;

	public static final int REP_TYPE_UNLOAD = 2;

	public static final int REP_TYPE_DEPSETTLE = 3;

	public static final int REP_TYPE_ARRSETTLE = 4;

	public static final String URL_VOUCHER = "voucher.jrxml";
	
	public static final String URL_RECEIPT = "financeReceipt.jrxml";
	
	@Autowired
	protected ServiceDropdownListHelper  serviceDropdownListHelper;
	
	@Autowired
	protected FinAccountMessageHelper  finAccountMessageHelper;

	/**
	 * Logic to build compound default map for generate the site settlement map
	 * 
	 * @return
	 * @throws ServiceEntityInstallationException 
	 */
	public Map<String, Object> buildReportMap(
			FinAccountUIModel finAccountUIModel, HostCompany hostCompany,
			LogonUser currentUser, String currentDate, String currentSite) throws ServiceEntityInstallationException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String finAccountTitle = ServiceEntityStringHelper.EMPTYSTRING;
		String postTitle = ServiceEntityStringHelper.EMPTYSTRING;
		if(finAccountUIModel.getFinAccountType() == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT){
			postTitle = finAccountMessageHelper
					.getMessage(FinAccountMessageHelper.TITLE_REP_VOUCHER);
		}else{
			postTitle = finAccountMessageHelper
					.getMessage(FinAccountMessageHelper.TITLE_REP_RECEIPT);
		}
		if(hostCompany.getComReportTitle() == null || hostCompany.getComReportTitle().equals(ServiceEntityStringHelper.EMPTYSTRING)){
			finAccountTitle = hostCompany.getName() + 
					postTitle;
		}else{
			finAccountTitle = hostCompany.getComReportTitle()
					+ postTitle;
		}
		if(hostCompany.getComLogo()!= null && hostCompany.getComLogo().length > 0){
			InputStream sbs = new ByteArrayInputStream(hostCompany.getComLogo()); 
			parameters.put("comLogo", sbs);
		}
		parameters.put("finAccountTitle", finAccountTitle);
		parameters.put("id", finAccountUIModel.getId());
		parameters
				.put("accountObjectName", finAccountUIModel.getAccountObjectName());
		parameters.put("accountantName", finAccountUIModel.getAccountantName());
		parameters.put("cashierName", finAccountUIModel.getCashierName());
		parameters.put("accountTitleName", finAccountUIModel.getAccountTitleName());
		parameters.put("dateStr", finAccountUIModel.getDateStr());
		parameters.put("amount", finAccountUIModel.getAmount());
		parameters.put("note", finAccountUIModel.getNote());
		parameters.put("comFax", hostCompany.getFax());
		parameters.put("currentUser", currentUser.getName());
		parameters.put("currentDate", currentDate);
		parameters.put("currentSite", currentSite);
		Map<Integer, String> paymentTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(FinAccountUIModel.class, "paymentType");
		String paymentTypeValue = paymentTypeMap.get(finAccountUIModel.getPaymentType());
		parameters.put("paymentTypeValue", paymentTypeValue);
		return parameters;
	}

	public String getTemplateResouceURL(int accountType) {
		String path = FinAccountReportService.class.getResource("").getPath();
		String resFileFullName = path;
		if(accountType == FinAccountTitle.FIN_ACCOUNTTYPE_CREDIT){
			resFileFullName = resFileFullName + URL_VOUCHER;
		}
		if(accountType == FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT){
			resFileFullName = resFileFullName + URL_RECEIPT;
		}		
		return resFileFullName;
	}

}
