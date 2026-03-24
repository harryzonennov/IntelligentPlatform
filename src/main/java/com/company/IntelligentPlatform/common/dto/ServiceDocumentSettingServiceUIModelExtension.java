package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.DocAttachmentProxy;
import com.company.IntelligentPlatform.common.service.ServiceDocumentSettingManager;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelDownloadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelUploadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocumentReportTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;

@Service
public class ServiceDocumentSettingServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected ServiceCrossDocConfigureServiceUIModelExtension serviceCrossDocConfigureServiceUIModelExtension;

	@Autowired
	protected ServiceDocActionConfigureServiceUIModelExtension serviceDocActionConfigureServiceUIModelExtension;

	@Autowired
	protected CrossCopyDocConfigureServiceUIModelExtension crossCopyDocConfigureServiceUIModelExtension;

	@Autowired
	protected ServiceDocDeletionSettingServiceUIModelExtension serviceDocDeletionSettingServiceUIModelExtension;

	// TODO-LEGACY: @Autowired

	// TODO-LEGACY: 	protected ServiceDocInitConfigureServiceUIModelExtension serviceDocInitConfigureServiceUIModelExtension;

	@Autowired
	protected ServiceDocumentSettingManager serviceDocumentSettingManager;

	@Autowired
	protected DocAttachmentProxy docAttachmentProxy;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<>();
		resultList.add(serviceCrossDocConfigureServiceUIModelExtension);
		resultList.add(serviceDocActionConfigureServiceUIModelExtension);
		resultList.add(crossCopyDocConfigureServiceUIModelExtension);
		resultList.add(serviceDocDeletionSettingServiceUIModelExtension);
		// TODO-LEGACY: serviceDocInitConfigureServiceUIModelExtension not yet migrated
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ServiceDocumentReportTemplate.SENAME,
				ServiceDocumentReportTemplate.NODENAME,
				ServiceDocumentReportTemplate.NODENAME
		)));
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ServiceDocExcelUploadTemplate.SENAME,
				ServiceDocExcelUploadTemplate.NODENAME,
				ServiceDocExcelUploadTemplate.NODENAME
		)));
		resultList.add(docAttachmentProxy.genDefServiceUIModelExtension(new DocAttachmentProxy.DocAttchNodeInputPara(
				ServiceDocExcelDownloadTemplate.SENAME,
				ServiceDocExcelDownloadTemplate.NODENAME,
				ServiceDocExcelDownloadTemplate.NODENAME
		)));
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion serviceDocumentSettingExtensionUnion = new ServiceUIModelExtensionUnion();
		serviceDocumentSettingExtensionUnion
				.setNodeInstId(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingExtensionUnion
				.setNodeName(ServiceDocumentSetting.NODENAME);

		// UI Model Configure of node:[ServiceDocumentSetting]
		UIModelNodeMapConfigure serviceDocumentSettingMap = new UIModelNodeMapConfigure();
		serviceDocumentSettingMap.setSeName(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setNodeName(ServiceDocumentSetting.NODENAME);
		serviceDocumentSettingMap.setNodeInstID(ServiceDocumentSetting.SENAME);
		serviceDocumentSettingMap.setHostNodeFlag(true);
		Class<?>[] serviceDocumentSettingConvToUIParas = {
				ServiceDocumentSetting.class,
				ServiceDocumentSettingUIModel.class };
		serviceDocumentSettingMap
				.setConvToUIMethodParas(serviceDocumentSettingConvToUIParas);
		serviceDocumentSettingMap
				.setConvToUIMethod(ServiceDocumentSettingManager.METHOD_ConvServiceDocumentSettingToUI);
		Class<?>[] ServiceDocumentSettingConvUIToParas = {
				ServiceDocumentSettingUIModel.class,
				ServiceDocumentSetting.class };
		serviceDocumentSettingMap
				.setConvUIToMethodParas(ServiceDocumentSettingConvUIToParas);
		serviceDocumentSettingMap
				.setConvUIToMethod(ServiceDocumentSettingManager.METHOD_ConvUIToServiceDocumentSetting);
		uiModelNodeMapList.add(serviceDocumentSettingMap);
		serviceDocumentSettingExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(serviceDocumentSettingExtensionUnion);
		return resultList;
	}

}
