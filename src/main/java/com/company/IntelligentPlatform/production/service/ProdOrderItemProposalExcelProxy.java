package com.company.IntelligentPlatform.production.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.dto.ProdOrderItemExcelProposal;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class ProdOrderItemProposalExcelProxy extends ServiceExcelReportProxy{
	
    public static final String CONFIG_NAME = "ProdOrderItemProposal";
	
	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = ProdOrderItemExcelProposal.class;
	}

}
