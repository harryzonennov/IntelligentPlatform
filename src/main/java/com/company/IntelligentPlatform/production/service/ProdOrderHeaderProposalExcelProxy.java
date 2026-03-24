package com.company.IntelligentPlatform.production.service;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.production.dto.ProdOrderHeaderExcelProposal;
import com.company.IntelligentPlatform.common.service.ServiceExcelReportProxy;

@Service
public class ProdOrderHeaderProposalExcelProxy extends ServiceExcelReportProxy{
	
public static final String CONFIG_NAME = "ProdOrderHeaderProposal";
	
	@PostConstruct
	public void initConfig(){
		this.configureName = CONFIG_NAME;
		this.excelModelClass = ProdOrderHeaderExcelProposal.class;
	}

}
