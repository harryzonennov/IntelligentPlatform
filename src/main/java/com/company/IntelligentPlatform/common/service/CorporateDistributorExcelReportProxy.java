package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.CorporateDistributorExcelModel;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class CorporateDistributorExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "CorporateDistributor";	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = CorporateDistributorExcelModel.class;
	}

}
