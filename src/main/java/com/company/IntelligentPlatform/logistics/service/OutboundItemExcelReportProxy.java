package com.company.IntelligentPlatform.logistics.service;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.OutboundItemUIModel;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.dto.ServiceExcelReportResponseModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceExcelConfigException;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class OutboundItemExcelReportProxy extends ServiceExcelReportProxy{
	
	public static final String CONFIG_NAME = OutboundItem.NODENAME;

	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.documentResourceId = CONFIG_NAME;
		this.excelModelClass = OutboundItemUIModel.class;
	}

	@Override
	public void insertExcelBatchData(
			ServiceExcelReportResponseModel serviceExcelReportResponseModel,
			String modelName) throws ServiceExcelConfigException,
			AuthorizationException, LogonInfoException {
		super.insertExcelBatchData(serviceExcelReportResponseModel, modelName);
	}

}
