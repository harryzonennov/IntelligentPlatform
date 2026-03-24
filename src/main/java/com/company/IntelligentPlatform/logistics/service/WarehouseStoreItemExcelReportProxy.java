package com.company.IntelligentPlatform.logistics.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemUIModel;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class WarehouseStoreItemExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = "WarehouseStoreItem";	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.documentResourceId = CONFIG_NAME;
		this.excelModelClass = WarehouseStoreItemUIModel.class;
	}

}
