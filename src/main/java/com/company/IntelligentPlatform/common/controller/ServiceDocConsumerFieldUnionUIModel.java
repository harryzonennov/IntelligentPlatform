package com.company.IntelligentPlatform.common.controller;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.ServiceDocConsumerFieldUnion;


public class ServiceDocConsumerFieldUnionUIModel extends SEUIComModel {		
	
	@ISEUIModelMapping(fieldName = "fieldLabel", seName = ServiceDocConsumerFieldUnion.SENAME, nodeName = ServiceDocConsumerFieldUnion.NODENAME, 
			nodeInstID = ServiceDocConsumerFieldUnion.NODENAME)
	protected String fieldLabel;
	
	@ISEUIModelMapping(fieldName = "name", seName = ServiceDocConsumerFieldUnion.SENAME, nodeName = ServiceDocConsumerFieldUnion.NODENAME, 
			nodeInstID = ServiceDocConsumerFieldUnion.NODENAME)
	protected String name;


}
