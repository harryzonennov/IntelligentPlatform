package com.company.IntelligentPlatform.logistics.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.InquiryManager;
import com.company.IntelligentPlatform.logistics.service.InquiryMaterialItemManager;
import com.company.IntelligentPlatform.logistics.service.InquiryMaterialItemManager;
import com.company.IntelligentPlatform.logistics.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.common.service.IndividualCustomerManager;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.controller.*;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class InquiryMaterialItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected InquiryManager inquiryManager;

	@Autowired
	protected InquiryMaterialItemManager inquiryMaterialItemManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected IndividualCustomerManager individualCustomerManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				InquiryMaterialItemAttachment.SENAME,
				InquiryMaterialItemAttachment.NODENAME,
				InquiryMaterialItemAttachment.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		try {
			ServiceUIModelUnionBuilder inquiryExtensionUnionBuilder = ServiceUIModelExtensionHelper.genUnionBuilder(InquiryMaterialItem.class,
					InquiryMaterialItemUIModel.class, uiModelNodeMapConfigureBuilder -> uiModelNodeMapConfigureBuilder.convToUIMethod(
									InquiryMaterialItemManager.METHOD_ConvInquiryMaterialItemToUI).logicManager(inquiryMaterialItemManager)
							.convUIToMethod(InquiryMaterialItemManager.METHOD_ConvUIToInquiryMaterialItem));
			Class<?>[] convParentDocToUIMethodParas = {Inquiry.class, InquiryMaterialItemUIModel.class};
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy.getDefParentDocMapConfigureList(InquiryMaterialItem.NODENAME,
					InquiryMaterialItemManager.METHOD_ConvParentDocToItemUI,
					inquiryMaterialItemManager, convParentDocToUIMethodParas));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefMaterialNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefPrevNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefNextNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefNextProfNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefPrevProfNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefReservedNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			inquiryExtensionUnionBuilder.addMapConfigureList(docFlowProxy
					.getDefCreateUpdateNodeMapConfigureList(InquiryMaterialItem.NODENAME));
			resultList.add(inquiryExtensionUnionBuilder.build());
		} catch (ServiceEntityConfigureException e) {
			// TODO remove ServiceEntityConfigureException catch
		}
		return resultList;
	}
	
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion2() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion inquiryMaterialItemExtensionUnion = new ServiceUIModelExtensionUnion();
		inquiryMaterialItemExtensionUnion
				.setNodeInstId(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemExtensionUnion
				.setNodeName(InquiryMaterialItem.NODENAME);

		// UI Model Configure of node:[InquiryMaterialItem]
		UIModelNodeMapConfigure inquiryMaterialItemMap = new UIModelNodeMapConfigure();
		inquiryMaterialItemMap.setSeName(InquiryMaterialItem.SENAME);
		inquiryMaterialItemMap.setNodeName(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemMap.setNodeInstID(InquiryMaterialItem.NODENAME);
		inquiryMaterialItemMap.setHostNodeFlag(true);
		Class<?>[] inquiryMaterialItemConvToUIParas = {
				InquiryMaterialItem.class, InquiryMaterialItemUIModel.class };
		inquiryMaterialItemMap
				.setConvToUIMethodParas(inquiryMaterialItemConvToUIParas);
		inquiryMaterialItemMap.setLogicManager(inquiryMaterialItemManager);
		inquiryMaterialItemMap
				.setConvToUIMethod(InquiryMaterialItemManager.METHOD_ConvInquiryMaterialItemToUI);
		Class<?>[] InquiryMaterialItemConvUIToParas = {
				InquiryMaterialItemUIModel.class, InquiryMaterialItem.class };
		inquiryMaterialItemMap
				.setConvUIToMethodParas(InquiryMaterialItemConvUIToParas);
		inquiryMaterialItemMap
				.setConvUIToMethod(InquiryMaterialItemManager.METHOD_ConvUIToInquiryMaterialItem);
		uiModelNodeMapList.add(inquiryMaterialItemMap);

		Class<?>[] convParentDocToUIMethodParas = {Inquiry.class, InquiryMaterialItemUIModel.class};
		uiModelNodeMapList.addAll(docFlowProxy.getDefParentDocMapConfigureList(InquiryMaterialItem.NODENAME,
				InquiryMaterialItemManager.METHOD_ConvParentDocToItemUI, inquiryMaterialItemManager,
				convParentDocToUIMethodParas));

		uiModelNodeMapList.addAll(docFlowProxy
				.getDefMaterialNodeMapConfigureList(
						InquiryMaterialItem.NODENAME));

		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefPrevNodeMapConfigureList(InquiryMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefNextNodeMapConfigureList(InquiryMaterialItem.NODENAME));
		uiModelNodeMapList
				.addAll(docFlowProxy
						.getDefReservedNodeMapConfigureList(InquiryMaterialItem.NODENAME));
		uiModelNodeMapList
		.addAll(docFlowProxy
				.getDefCreateUpdateNodeMapConfigureList(InquiryMaterialItem.NODENAME));

		inquiryMaterialItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(inquiryMaterialItemExtensionUnion);
		return resultList;
	}

}
