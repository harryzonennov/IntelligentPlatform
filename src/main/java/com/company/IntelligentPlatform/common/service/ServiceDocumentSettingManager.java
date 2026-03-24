package com.company.IntelligentPlatform.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.ServiceDocumentSettingUIModel;
// TODO-DAO: import ...ServiceDocumentSettingDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDateHelper;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelDownloadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocExcelUploadTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocumentReportTemplate;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSetting;
import com.company.IntelligentPlatform.common.model.ServiceDocumentSettingConfigureProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class ServiceDocumentSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvServiceDocumentSettingToUI = "convServiceDocumentSettingToUI";

	public static final String METHOD_ConvUIToServiceDocumentSetting = "convUIToServiceDocumentSetting";

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected ServiceDocumentSettingDAO serviceDocumentSettingDAO;

	@Autowired
	protected ServiceDocumentSettingConfigureProxy serviceDocumentSettingConfigureProxy;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceDocumentSettingSearchProxy serviceDocumentSettingSearchProxy;

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(serviceDocumentSettingDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serviceDocumentSettingConfigureProxy);
	}

	public Map<Integer, String> initDocumentTypeMap(String lanCode)
			throws ServiceEntityInstallationException {
		return serviceDocumentComProxy
				.getDocumentTypeMap(false, lanCode);
	}

	public String getDocumentTypeValue(String documentType, String languageCode)
			throws ServiceEntityInstallationException {
		return serviceDocumentComProxy.getExtendDocumentTypeValue(documentType, languageCode);
	}

	public Map<String, String> getDocumentTypeMap(String languageCode)
			throws ServiceEntityInstallationException {
		return serviceDocumentComProxy.getExtendDocumentTypeMap(languageCode);
	}

	/**
	 * Get Document Setting by document id.
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocumentSetting getDocumentSettingById(String documentId,
			String client) throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		return serviceDocumentSetting;
	}

	/**
	 * Get Document default previous doc type by document id.
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	// TODO: to change this logic
	public int getDefaultPrevDocType(String documentId, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		if (serviceDocumentSetting == null) {
			return 0;
		}
		return 0;
	}

	/**
	 * Get Document default previous doc type by document id.
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	//TODO to modify this logic
	public int getDefaultNextDocType(String documentId, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		if (serviceDocumentSetting == null) {
			return 0;
		}
		return 0;
	}

	/**
	 * Get Document Setting by document id.
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocumentReportTemplate getDocumentReportTemplateById(
			String documentId, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		if (serviceDocumentSetting == null) {
			return null;
		}
		List<ServiceEntityNode> documentReportTemplateList = getEntityNodeListByKey(
				serviceDocumentSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ServiceDocumentReportTemplate.NODENAME, client, null);
		if (!ServiceCollectionsHelper.checkNullList(documentReportTemplateList)) {
			return (ServiceDocumentReportTemplate) documentReportTemplateList
					.get(0);
		}
		return null;
	}

	/**
	 * Get Download Excel template by document id
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocExcelDownloadTemplate getDownloadExcelTemplate(
			String documentId, String fileType, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		if (serviceDocumentSetting == null) {
			return null;
		}
		List<ServiceEntityNode> serviceDocExcelDownloadTemplateList = getEntityNodeListByKey(
				serviceDocumentSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ServiceDocExcelDownloadTemplate.NODENAME, client, null);
		if (!ServiceCollectionsHelper
				.checkNullList(serviceDocExcelDownloadTemplateList)) {
			for (ServiceEntityNode seNode : serviceDocExcelDownloadTemplateList) {
				ServiceDocExcelDownloadTemplate ServiceDocExcelDownloadTemplate = (ServiceDocExcelDownloadTemplate) seNode;
				if (fileType.equals(ServiceDocExcelDownloadTemplate
						.getFileType())) {
					return ServiceDocExcelDownloadTemplate;
				}
			}
		}
		return null;
	}

	/**
	 * Get Download Excel template by document id
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocExcelUploadTemplate getUploadExcelTemplate(
			String documentId, String fileType, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null, true);
		if (serviceDocumentSetting == null) {
			return null;
		}
		List<ServiceEntityNode> serviceDocExcelUploadTemplateList = getEntityNodeListByKey(
				serviceDocumentSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ServiceDocExcelUploadTemplate.NODENAME, client, null);
		if (!ServiceCollectionsHelper
				.checkNullList(serviceDocExcelUploadTemplateList)) {
			for (ServiceEntityNode seNode : serviceDocExcelUploadTemplateList) {
				ServiceDocExcelUploadTemplate serviceDocExcelUploadTemplate = (ServiceDocExcelUploadTemplate) seNode;
				if (fileType
						.equals(serviceDocExcelUploadTemplate.getFileType())) {
					return serviceDocExcelUploadTemplate;
				}
			}
		}
		return null;
	}

	/**
	 * Get Document Setting by document id.
	 * 
	 * @param documentId
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ServiceDocumentReportTemplate getDocumentReportTemplate(
			String documentId, String fileType, String client)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) getEntityNodeByKey(
				documentId, IServiceEntityNodeFieldConstant.ID,
				ServiceDocumentSetting.NODENAME, client, null);
		if (serviceDocumentSetting == null) {
			return null;
		}
		List<ServiceEntityNode> documentReportTemplateList = getEntityNodeListByKey(
				serviceDocumentSetting.getUuid(),
				IServiceEntityNodeFieldConstant.PARENTNODEUUID,
				ServiceDocumentReportTemplate.NODENAME, client, null);
		if (!ServiceCollectionsHelper.checkNullList(documentReportTemplateList)) {
			for (ServiceEntityNode seNode : documentReportTemplateList) {
				ServiceDocumentReportTemplate serviceDocumentReportTemplate = (ServiceDocumentReportTemplate) seNode;
				if (fileType
						.equals(serviceDocumentReportTemplate.getFileType())) {
					return serviceDocumentReportTemplate;
				}
			}
		}
		return null;
	}

	public boolean checkDocumentUpdateValid(String documentId)
			throws ServiceEntityConfigureException {
		ServiceDocumentSetting serviceDocumentSetting = (ServiceDocumentSetting) this
				.getEntityNodeByKey(documentId,
						IServiceEntityNodeFieldConstant.ID,
						ServiceDocumentSetting.NODENAME, null);
		if (serviceDocumentSetting != null
				&& serviceDocumentSetting.getValidToDate() != null) {
			long diffDay = ServiceEntityDateHelper.getDiffDays(new Date(),
					serviceDocumentSetting.getValidToDate());
			if (diffDay < 0) {
				return false;
			}
		}
		return true;
	}



	public void convServiceDocumentSettingToUI(
			ServiceDocumentSetting serviceDocumentSetting,
			ServiceDocumentSettingUIModel serviceDocumentSettingUIModel) throws ServiceEntityInstallationException {
		convServiceDocumentSettingToUI(serviceDocumentSetting,
				serviceDocumentSettingUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 * 
	 * @param serviceDocumentSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convServiceDocumentSettingToUI(
			ServiceDocumentSetting serviceDocumentSetting,
			ServiceDocumentSettingUIModel serviceDocumentSettingUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (serviceDocumentSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serviceDocumentSetting, serviceDocumentSettingUIModel);
			serviceDocumentSettingUIModel.setId(serviceDocumentSetting.getId());
			if (serviceDocumentSetting.getValidToDate() != null) {
				serviceDocumentSettingUIModel
						.setValidToDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(serviceDocumentSetting.getValidToDate()));
			}
			serviceDocumentSettingUIModel.setName(serviceDocumentSetting
					.getName());
			serviceDocumentSettingUIModel.setNote(serviceDocumentSetting
					.getNote());
			serviceDocumentSettingUIModel
					.setDocumentType(serviceDocumentSetting.getDocumentType());
			if (logonInfo != null) {
				Map<String, String> extendDocMap = serviceDocumentComProxy
						.getExtendDocumentTypeMap(logonInfo.getLanguageCode());
				serviceDocumentSettingUIModel
				.setDocumentTypeValue(extendDocMap
						.get(serviceDocumentSetting.getDocumentType()));
			}
			
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:serviceDocumentSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToServiceDocumentSetting(
			ServiceDocumentSettingUIModel serviceDocumentSettingUIModel,
			ServiceDocumentSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serviceDocumentSettingUIModel, rawEntity);
		rawEntity.setDocumentType(serviceDocumentSettingUIModel
				.getDocumentType());
		rawEntity.setUuid(serviceDocumentSettingUIModel.getUuid());
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return serviceDocumentSettingSearchProxy;
	}
}
