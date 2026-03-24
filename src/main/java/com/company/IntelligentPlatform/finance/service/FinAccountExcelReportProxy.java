package com.company.IntelligentPlatform.finance.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.finance.dto.FinAccountExcelModel;
import com.company.IntelligentPlatform.finance.model.FinAccount;

import org.springframework.stereotype.Service;



import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class FinAccountExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = FinAccount.SENAME;
	
	public static final String LOC_EXCEL_NAMECORE = FinAccount.SENAME;
	
	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = FinAccountExcelModel.class;
	}

}
