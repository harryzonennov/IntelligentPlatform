package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.service.IServiceModuleFieldConfig;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

public class ServiceDocumentSettingServiceModel extends ServiceModule {

	@IServiceModuleFieldConfig(nodeName = ServiceDocumentSetting.NODENAME, nodeInstId = ServiceDocumentSetting.SENAME)
	protected ServiceDocumentSetting serviceDocumentSetting;

	@IServiceModuleFieldConfig(nodeName = ServiceCrossDocConfigure.NODENAME, nodeInstId = ServiceCrossDocConfigure.NODENAME)
	protected List<ServiceCrossDocConfigureServiceModel> serviceCrossDocConfigureList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocDeletionSetting.NODENAME, nodeInstId = ServiceDocDeletionSetting.NODENAME)
	protected List<ServiceDocDeletionSettingServiceModel> ServiceDocDeletionSettingList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocInitConfigure.NODENAME, nodeInstId = ServiceDocInitConfigure.NODENAME)
	protected List<ServiceDocInitConfigureServiceModel> serviceDocInitConfigureList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = CrossCopyDocConfigure.NODENAME, nodeInstId =
			CrossCopyDocConfigure.NODENAME)
	protected List<CrossCopyDocConfigureServiceModel> crossCopyDocConfigureList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocActionConfigure.NODENAME, nodeInstId = ServiceDocActionConfigure.NODENAME)
	protected List<ServiceDocActionConfigureServiceModel> serviceDocActionConfigureList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocumentReportTemplate.NODENAME, nodeInstId = ServiceDocumentReportTemplate.NODENAME)
	protected List<ServiceEntityNode> serviceDocumentReportTemplateList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocExcelDownloadTemplate.NODENAME, nodeInstId = ServiceDocExcelDownloadTemplate.NODENAME)
	protected List<ServiceEntityNode> serviceDocExcelDownloadTemplateList = new ArrayList<>();

	@IServiceModuleFieldConfig(nodeName = ServiceDocExcelUploadTemplate.NODENAME, nodeInstId = ServiceDocExcelUploadTemplate.NODENAME)
	protected List<ServiceEntityNode> serviceDocExcelUploadTemplateList = new ArrayList<>();

	public List<ServiceDocDeletionSettingServiceModel> getServiceDocDeletionSettingList() {
		return ServiceDocDeletionSettingList;
	}

	public void setServiceDocDeletionSettingList(List<ServiceDocDeletionSettingServiceModel> serviceDocDeletionSettingList) {
		ServiceDocDeletionSettingList = serviceDocDeletionSettingList;
	}

	public List<ServiceCrossDocConfigureServiceModel> getServiceCrossDocConfigureList() {
		return serviceCrossDocConfigureList;
	}

	public void setServiceCrossDocConfigureList(
			List<ServiceCrossDocConfigureServiceModel> serviceCrossDocConfigureList) {
		this.serviceCrossDocConfigureList = serviceCrossDocConfigureList;
	}

	public List<ServiceEntityNode> getServiceDocumentReportTemplateList() {
		return this.serviceDocumentReportTemplateList;
	}

	public void setServiceDocumentReportTemplateList(
			List<ServiceEntityNode> serviceDocumentReportTemplateList) {
		this.serviceDocumentReportTemplateList = serviceDocumentReportTemplateList;
	}

	public List<CrossCopyDocConfigureServiceModel> getCrossCopyDocConfigureList() {
		return crossCopyDocConfigureList;
	}

	public void setCrossCopyDocConfigureList(List<CrossCopyDocConfigureServiceModel> crossCopyDocConfigureList) {
		this.crossCopyDocConfigureList = crossCopyDocConfigureList;
	}

	public ServiceDocumentSetting getServiceDocumentSetting() {
		return this.serviceDocumentSetting;
	}

	public void setServiceDocumentSetting(
			ServiceDocumentSetting serviceDocumentSetting) {
		this.serviceDocumentSetting = serviceDocumentSetting;
	}

	public List<ServiceDocInitConfigureServiceModel> getServiceDocInitConfigureList() {
		return serviceDocInitConfigureList;
	}

	public void setServiceDocInitConfigureList(List<ServiceDocInitConfigureServiceModel> serviceDocInitConfigureList) {
		this.serviceDocInitConfigureList = serviceDocInitConfigureList;
	}

	public List<ServiceDocActionConfigureServiceModel> getServiceDocActionConfigureList() {
		return serviceDocActionConfigureList;
	}

	public void setServiceDocActionConfigureList(
			List<ServiceDocActionConfigureServiceModel> serviceDocActionConfigureList) {
		this.serviceDocActionConfigureList = serviceDocActionConfigureList;
	}

	public List<ServiceEntityNode> getServiceDocExcelDownloadTemplateList() {
		return serviceDocExcelDownloadTemplateList;
	}

	public void setServiceDocExcelDownloadTemplateList(
			List<ServiceEntityNode> serviceDocExcelDownloadTemplateList) {
		this.serviceDocExcelDownloadTemplateList = serviceDocExcelDownloadTemplateList;
	}

	public List<ServiceEntityNode> getServiceDocExcelUploadTemplateList() {
		return serviceDocExcelUploadTemplateList;
	}

	public void setServiceDocExcelUploadTemplateList(
			List<ServiceEntityNode> serviceDocExcelUploadTemplateList) {
		this.serviceDocExcelUploadTemplateList = serviceDocExcelUploadTemplateList;
	}

}
