package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomerAttachment;

@Service
public class IndividualCustomerServiceUIModelExtension extends
		ServiceUIModelExtension {
	
	@Autowired
	protected CorContactPersonServiceUIModelExtension corContactPersonServiceUIModelExtension;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				IndividualCustomerAttachment.SENAME,
				IndividualCustomerAttachment.NODENAME,
				IndividualCustomerAttachment.NODENAME
		)));
		resultList.add(corContactPersonServiceUIModelExtension);
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion individualCustomerExtensionUnion = new ServiceUIModelExtensionUnion();
		individualCustomerExtensionUnion
				.setNodeInstId(IndividualCustomer.SENAME);
		individualCustomerExtensionUnion
				.setNodeName(IndividualCustomer.NODENAME);

		// UI Model Configure of node:[IndividualCustomer]
		UIModelNodeMapConfigure individualCustomerMap = new UIModelNodeMapConfigure();
		individualCustomerMap.setSeName(IndividualCustomer.SENAME);
		individualCustomerMap.setNodeName(IndividualCustomer.NODENAME);
		individualCustomerMap.setNodeInstID(IndividualCustomer.SENAME);
		individualCustomerMap.setHostNodeFlag(true);
		Class<?>[] individualCustomerConvToUIParas = {
				IndividualCustomer.class, IndividualCustomerUIModel.class };
		individualCustomerMap
				.setConvToUIMethodParas(individualCustomerConvToUIParas);
		individualCustomerMap
				.setConvToUIMethod(IndividualCustomerManager.METHOD_ConvIndividualCustomerToUI);
		Class<?>[] IndividualCustomerConvUIToParas = {
				IndividualCustomerUIModel.class, IndividualCustomer.class };
		individualCustomerMap
				.setConvUIToMethodParas(IndividualCustomerConvUIToParas);
		individualCustomerMap
				.setConvUIToMethod(IndividualCustomerManager.METHOD_ConvUIToIndividualCustomer);
		uiModelNodeMapList.add(individualCustomerMap);
		individualCustomerExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(individualCustomerExtensionUnion);
		return resultList;
	}

}
