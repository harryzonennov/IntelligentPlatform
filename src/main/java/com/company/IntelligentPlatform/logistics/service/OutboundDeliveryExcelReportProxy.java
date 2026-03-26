package com.company.IntelligentPlatform.logistics.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.OutboundDeliveryExcelModel;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class OutboundDeliveryExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = OutboundDelivery.SENAME;	

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = OutboundDeliveryExcelModel.class;
	}

}
