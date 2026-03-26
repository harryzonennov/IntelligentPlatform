package com.company.IntelligentPlatform.logistics.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.InboundDeliveryExcelModel;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class InboundDeliveryExcelReportProxy extends ServiceExcelReportProxy {

	public static final String CONFIG_NAME = InboundDelivery.SENAME;

	@PostConstruct
	public void initConfig() {
		this.configureName = CONFIG_NAME;
		this.excelModelClass = InboundDeliveryExcelModel.class;
	}

}
