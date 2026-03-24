package com.company.IntelligentPlatform.logistics.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import com.company.IntelligentPlatform.logistics.dto.*;
import com.company.IntelligentPlatform.logistics.repository.InquiryRepository;
import com.company.IntelligentPlatform.logistics.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.StandardPriorityProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import java.time.ZoneId;
import java.time.LocalDate;

@Service
@Transactional
public class InquiryManager extends ServiceEntityManager {

	public static final String METHOD_ConvInquiryToUI = "convInquiryToUI";

	public static final String METHOD_ConvUIToInquiry = "convUIToInquiry";

	@Autowired
	protected InquiryRepository inquiryDAO;

	@Autowired
	protected InquiryConfigureProxy inquiryConfigureProxy;

	@Autowired
	protected InquiryIdHelper inquiryIdHelper;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected InquiryServiceUIModelExtension inquiryServiceUIModelExtension;

	@Autowired
	protected InquiryMaterialItemServiceUIModelExtension inquiryMaterialItemServiceUIModelExtension;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	@Autowired
	protected StandardPriorityProxy standardPriorityProxy;

	@Autowired
	protected InquirySearchProxy inquirySearchProxy;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, Map<Integer, String>> statusLanMap = new HashMap<>();

	public Map<Integer, String> initPriorityCode(String languageCode)
			throws ServiceEntityInstallationException {
		return standardPriorityProxy.getPriorityMap(languageCode);
	}

	public Map<Integer, String> initStatus(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.statusLanMap, InquiryUIModel.class, IDocumentNodeFieldConstant.STATUS);
	}



	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		String id = inquiryIdHelper.genDefaultId(client);
		Inquiry inquiry = (Inquiry) super.newRootEntityNode(client);
		inquiry.setId(id);
		return inquiry;
	}
	/**
	 * Get Sales Inquiry Party by base UUID and partyRole
	 * @param baseUUID
	 * @param client
	 * @return
	 */
	public InquiryParty getInquiryParty(String baseUUID, int partyRole, String client) throws ServiceEntityConfigureException {
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(baseUUID,
				IServiceEntityNodeFieldConstant.ROOTNODEUUID);
		ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(partyRole,
				DocInvolveParty.FIELD_PARTYROLE);
		return (InquiryParty) this.getEntityNodeByKeyList(ServiceCollectionsHelper.asList(key1,
				key2), InquiryParty.NODENAME, client, null);
	}


	public void convInquiryToUI(Inquiry inquiry, InquiryUIModel inquiryUIModel)
			throws ServiceEntityInstallationException {
		convInquiryToUI(inquiry, inquiryUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convInquiryToUI(Inquiry inquiry, InquiryUIModel inquiryUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (inquiry != null) {
			docFlowProxy.convDocumentToUI(inquiry, inquiryUIModel, logonInfo);
			inquiryUIModel.setNote(inquiry.getNote());
			inquiryUIModel.setStatus(inquiry.getStatus());
			if (logonInfo != null) {
				Map<Integer, String> statusMap = this.initStatus(logonInfo
						.getLanguageCode());
				inquiryUIModel
						.setStatusValue(statusMap.get(inquiry.getStatus()));
				Map<Integer, String> priorityCodeMap = this.initPriorityCode(logonInfo
						.getLanguageCode());
				inquiryUIModel
						.setPriorityCodeValue(priorityCodeMap.get(inquiry.getPriorityCode()));
			}
			inquiryUIModel.setPriorityCode(inquiry.getPriorityCode());
			inquiryUIModel.setCurrencyCode(inquiry.getCurrencyCode());
			if (inquiry.getSignDate() != null) {
				inquiryUIModel
						.setSignDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(inquiry.getSignDate()));
			}
			if (inquiry.getRequireExecutionDate() != null) {
				inquiryUIModel
						.setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(inquiry.getRequireExecutionDate()));
			}
			inquiryUIModel.setContractDetails(inquiry.getContractDetails());
			inquiryUIModel.setGrossPrice(inquiry.getGrossPrice());
			inquiryUIModel.setGrossPriceDisplay(inquiry.getGrossPriceDisplay());
			inquiryUIModel.setCurrencyCode(inquiry.getCurrencyCode());
			
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:inquiry
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToInquiry(InquiryUIModel inquiryUIModel, Inquiry rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(inquiryUIModel, rawEntity);
		if (!ServiceEntityStringHelper.checkNullString(inquiryUIModel
				.getSignDate())) {
			try {
				rawEntity.setSignDate(DefaultDateFormatConstant.DATE_FORMAT.parse(inquiryUIModel.getSignDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(inquiryUIModel
				.getRequireExecutionDate())) {
			try {
				rawEntity
						.setRequireExecutionDate(DefaultDateFormatConstant.DATE_FORMAT.parse(inquiryUIModel.getRequireExecutionDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setCurrencyCode(inquiryUIModel.getCurrencyCode());
		rawEntity.setPriorityCode(inquiryUIModel.getPriorityCode());
		rawEntity.setGrossPrice(ServiceEntityDoubleHelper
				.trancateDoubleScale2(inquiryUIModel.getGrossPrice()));
		rawEntity.setGrossPriceDisplay(ServiceEntityDoubleHelper
				.trancateDoubleScale2(inquiryUIModel.getGrossPriceDisplay()));
		rawEntity.setContractDetails(inquiryUIModel.getContractDetails());
		rawEntity.setCurrencyCode(inquiryUIModel.getCurrencyCode());
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(inquiryDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(inquiryConfigureProxy);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convInquiryAttachmentToUI(InquiryAttachment inquiryAttachment,
			InquiryAttachmentUIModel inquiryAttachmentUIModel) {
		if (inquiryAttachment != null) {
			if (!ServiceEntityStringHelper.checkNullString(inquiryAttachment
					.getUuid())) {
				inquiryAttachmentUIModel.setUuid(inquiryAttachment.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(inquiryAttachment
					.getParentNodeUUID())) {
				inquiryAttachmentUIModel.setParentNodeUUID(inquiryAttachment
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(inquiryAttachment
					.getRootNodeUUID())) {
				inquiryAttachmentUIModel.setRootNodeUUID(inquiryAttachment
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(inquiryAttachment
					.getClient())) {
				inquiryAttachmentUIModel.setClient(inquiryAttachment
						.getClient());
			}
			inquiryAttachmentUIModel.setFileType(inquiryAttachment
					.getFileType());
			inquiryAttachmentUIModel.setAttachmentDescription(inquiryAttachment
					.getNote());
			inquiryAttachmentUIModel.setAttachmentTitle(inquiryAttachment
					.getName());
		}
	}

	public ServiceDocumentExtendUIModel convInquiryToDocExtUIModel(
			InquiryUIModel inquiryUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(inquiryUIModel);
		serviceDocumentExtendUIModel.setUuid(inquiryUIModel.getUuid());
		serviceDocumentExtendUIModel.setParentNodeUUID(inquiryUIModel
				.getParentNodeUUID());
		serviceDocumentExtendUIModel.setRootNodeUUID(inquiryUIModel
				.getRootNodeUUID());
		serviceDocumentExtendUIModel.setId(inquiryUIModel.getId());
		serviceDocumentExtendUIModel.setStatus(inquiryUIModel.getStatus());
		serviceDocumentExtendUIModel.setStatusValue(inquiryUIModel
				.getStatusValue());
		serviceDocumentExtendUIModel
				.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY);
		if(logonInfo != null){
			serviceDocumentExtendUIModel
					.setDocumentTypeValue(serviceDocumentComProxy
							.getDocumentTypeValue(IDefDocumentResource.DOCUMENT_TYPE_INQUIRY,
									logonInfo.getLanguageCode()));
		}
		// Logic of reference Date
		String referenceDate = inquiryUIModel.getSignDate();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = inquiryUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convInquiryMatItemToDocExtUIModel(
			InquiryMaterialItemUIModel inquiryMaterialItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		docFlowProxy.convDocMatItemUIToDocExtUIModel(inquiryMaterialItemUIModel,
				serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_INQUIRY);
		serviceDocumentExtendUIModel.setRefUIModel(inquiryMaterialItemUIModel);		
		serviceDocumentExtendUIModel.setId(inquiryMaterialItemUIModel
				.getParentDocId());
		serviceDocumentExtendUIModel.setStatus(inquiryMaterialItemUIModel
				.getParentDocStatus());
		serviceDocumentExtendUIModel.setStatusValue(inquiryMaterialItemUIModel
				.getParentDocStatusValue());
		// Logic of reference Date
		String referenceDate = inquiryMaterialItemUIModel.getUpdatedDate();
		if (ServiceEntityStringHelper.checkNullString(referenceDate)) {
			referenceDate = inquiryMaterialItemUIModel.getCreatedDate();
		}
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	@Override
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(
			ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ServiceEntityNode.NODENAME_ROOT.equals(seNode.getNodeName())) {
			Inquiry inquiry = (Inquiry) seNode;
			try {
				InquiryUIModel inquiryUIModel = (InquiryUIModel) genUIModelFromUIModelExtension(
						InquiryUIModel.class, inquiryServiceUIModelExtension
								.genUIModelExtensionUnion().get(0), inquiry,
						logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convInquiryToDocExtUIModel(inquiryUIModel
						, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, Inquiry.SENAME));
			}
		}
		if (InquiryMaterialItem.NODENAME.equals(seNode.getNodeName())) {
			InquiryMaterialItem inquiryMaterialItem = (InquiryMaterialItem) seNode;
			try {
				InquiryMaterialItemUIModel inquiryMaterialItemUIModel = (InquiryMaterialItemUIModel) genUIModelFromUIModelExtension(
						InquiryMaterialItemUIModel.class,
						inquiryMaterialItemServiceUIModelExtension
								.genUIModelExtensionUnion().get(0),
						inquiryMaterialItem, logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convInquiryMatItemToDocExtUIModel(inquiryMaterialItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(
						e, InquiryMaterialItem.NODENAME));
			}
		}
		return null;
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.Inquiry;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return inquirySearchProxy;
	}

}
