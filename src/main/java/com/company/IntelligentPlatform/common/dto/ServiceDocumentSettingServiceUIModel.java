package com.company.IntelligentPlatform.common.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.company.IntelligentPlatform.common.dto.IServiceUIModuleFieldConfig;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.model.*;

@Component
public class ServiceDocumentSettingServiceUIModel extends ServiceUIModule {

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocumentSetting.NODENAME, nodeInstId = ServiceDocumentSetting.SENAME)
	protected ServiceDocumentSettingUIModel serviceDocumentSettingUIModel;

	@IServiceUIModuleFieldConfig(nodeName = ServiceCrossDocConfigure.NODENAME, nodeInstId = ServiceCrossDocConfigure.NODENAME)
	protected List<ServiceCrossDocConfigureServiceUIModel> serviceCrossDocConfigureUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = CrossCopyDocConfigure.NODENAME, nodeInstId = CrossCopyDocConfigure.NODENAME)
	protected List<CrossCopyDocConfigureServiceUIModel> crossCopyDocConfigureUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocDeletionSetting.NODENAME, nodeInstId = ServiceDocDeletionSetting.NODENAME)
	protected List<ServiceDocDeletionSettingServiceUIModel> serviceDocDeletionSettingUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocInitConfigure.NODENAME, nodeInstId = ServiceDocInitConfigure.NODENAME)
	protected List<ServiceDocInitConfigureServiceUIModel> serviceDocInitConfigureUIModelList =
			new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocActionConfigure.NODENAME, nodeInstId = ServiceDocActionConfigure.NODENAME)
	protected List<ServiceDocActionConfigureServiceUIModel> serviceDocActionConfigureModelList = new ArrayList<>();

	@IServiceUIModuleFieldConfig(nodeName = ServiceDocumentReportTemplate.NODENAME, nodeInstId = ServiceDocumentReportTemplate.NODENAME)
	protected List<ServiceDocumentReportTemplateUIModel> serviceDocumentReportTemplateUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = ServiceDocExcelUploadTemplate.NODENAME, nodeInstId = ServiceDocExcelUploadTemplate.NODENAME)
	protected List<ServiceDocExcelUploadTemplateUIModel> serviceDocExcelUploadTemplateUIModelList = new ArrayList<>();
	
	@IServiceUIModuleFieldConfig(nodeName = ServiceDocExcelDownloadTemplate.NODENAME, nodeInstId = ServiceDocExcelDownloadTemplate.NODENAME)
	protected List<ServiceDocExcelDownloadTemplateUIModel> serviceDocExcelDownloadTemplateUIModelList = new ArrayList<>();

	public ServiceDocumentSettingUIModel getServiceDocumentSettingUIModel() {
		return this.serviceDocumentSettingUIModel;
	}

	public void setServiceDocumentSettingUIModel(
			ServiceDocumentSettingUIModel serviceDocumentSettingUIModel) {
		this.serviceDocumentSettingUIModel = serviceDocumentSettingUIModel;
	}

	public List<ServiceDocumentReportTemplateUIModel> getServiceDocumentReportTemplateUIModelList() {
		return this.serviceDocumentReportTemplateUIModelList;
	}

	public List<ServiceCrossDocConfigureServiceUIModel> getServiceCrossDocConfigureUIModelList() {
		return serviceCrossDocConfigureUIModelList;
	}

	public void setServiceCrossDocConfigureUIModelList(List<ServiceCrossDocConfigureServiceUIModel> serviceCrossDocConfigureUIModelList) {
		this.serviceCrossDocConfigureUIModelList = serviceCrossDocConfigureUIModelList;
	}

	public List<CrossCopyDocConfigureServiceUIModel> getCrossCopyDocConfigureUIModelList() {
		return crossCopyDocConfigureUIModelList;
	}

	public void setCrossCopyDocConfigureUIModelList(List<CrossCopyDocConfigureServiceUIModel> crossCopyDocConfigureUIModelList) {
		this.crossCopyDocConfigureUIModelList = crossCopyDocConfigureUIModelList;
	}

	public List<ServiceDocActionConfigureServiceUIModel> getServiceDocActionConfigureModelList() {
		return serviceDocActionConfigureModelList;
	}

	public void setServiceDocActionConfigureModelList(
			List<ServiceDocActionConfigureServiceUIModel> serviceDocActionConfigureModelList) {
		this.serviceDocActionConfigureModelList = serviceDocActionConfigureModelList;
	}

	public void setServiceDocumentReportTemplateUIModelList(
			List<ServiceDocumentReportTemplateUIModel> serviceDocumentReportTemplateUIModelList) {
		this.serviceDocumentReportTemplateUIModelList = serviceDocumentReportTemplateUIModelList;
	}

	public List<ServiceDocExcelUploadTemplateUIModel> getServiceDocExcelUploadTemplateUIModelList() {
		return serviceDocExcelUploadTemplateUIModelList;
	}

	public void setServiceDocExcelUploadTemplateUIModelList(
			List<ServiceDocExcelUploadTemplateUIModel> serviceDocExcelUploadTemplateUIModelList) {
		this.serviceDocExcelUploadTemplateUIModelList = serviceDocExcelUploadTemplateUIModelList;
	}

	public List<ServiceDocExcelDownloadTemplateUIModel> getServiceDocExcelDownloadTemplateUIModelList() {
		return serviceDocExcelDownloadTemplateUIModelList;
	}

	public void setServiceDocExcelDownloadTemplateUIModelList(
			List<ServiceDocExcelDownloadTemplateUIModel> serviceDocExcelDownloadTemplateUIModelList) {
		this.serviceDocExcelDownloadTemplateUIModelList = serviceDocExcelDownloadTemplateUIModelList;
	}

	public List<ServiceDocDeletionSettingServiceUIModel> getServiceDocDeletionSettingUIModelList() {
		return serviceDocDeletionSettingUIModelList;
	}

	public void setServiceDocDeletionSettingUIModelList(List<ServiceDocDeletionSettingServiceUIModel> serviceDocDeletionSettingUIModelList) {
		this.serviceDocDeletionSettingUIModelList = serviceDocDeletionSettingUIModelList;
	}

	public List<ServiceDocInitConfigureServiceUIModel> getServiceDocInitConfigureUIModelList() {
		return serviceDocInitConfigureUIModelList;
	}

	public void setServiceDocInitConfigureUIModelList(List<ServiceDocInitConfigureServiceUIModel> serviceDocInitConfigureUIModelList) {
		this.serviceDocInitConfigureUIModelList = serviceDocInitConfigureUIModelList;
	}
}
