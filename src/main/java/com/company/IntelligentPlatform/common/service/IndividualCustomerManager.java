package com.company.IntelligentPlatform.common.service;

import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerAttachmentUIModel;
import com.company.IntelligentPlatform.common.dto.IndividualCustomerUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.IndividualCustomerRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.AccountManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.CityManager;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.CorporateContactPerson;
import com.company.IntelligentPlatform.common.model.IndividualCustomerAttachment;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IndividualCustomerConfigureProxy;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Logic Manager CLASS FOR Service Entity [IndividualCustomer]
 * 
 * @author
 * @date Fri Jun 28 11:04:43 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class IndividualCustomerManager extends ServiceEntityManager {

	public static final String METHOD_ConvIndividualCustomerToUI = "convIndividualCustomerToUI";

	public static final String METHOD_ConvUIToIndividualCustomer = "convUIToIndividualCustomer";
	
	public static final String METHOD_ConvIndividualCustomerAttachmentToUI = "convIndividualCustomerAttachmentToUI";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected IndividualCustomerRepository individualCustomerDAO;
	@Autowired
	protected IndividualCustomerConfigureProxy individualCustomerConfigureProxy;

	@Autowired
	protected IndividualCustomerIdHelper individualCustomerIdHelper;

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected CustomerManager customerManager;

	@Autowired
	protected AccountManager accountManager;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected CityManager cityManager;
	
	@Autowired
	protected IndividualCustomerSearchProxy individualCustomerSearchProxy;

	public IndividualCustomerManager() {
		super.seConfigureProxy = new IndividualCustomerConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, individualCustomerDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(individualCustomerConfigureProxy);
	}

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		IndividualCustomer individualCustomer = (IndividualCustomer) super
				.newRootEntityNode(client);
		String individualCustomerID = individualCustomerIdHelper
				.genDefaultId(client);
		individualCustomer.setId(individualCustomerID);
		return individualCustomer;
	}

	/**
	 * [Internal method] Convert from UI model to se model:individualCustomer
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToIndividualCustomer(
			IndividualCustomerUIModel individualCustomerUIModel,
			IndividualCustomer rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(individualCustomerUIModel, rawEntity);
		rawEntity.setWeiboID(individualCustomerUIModel.getWeiboID());
		rawEntity.setId(individualCustomerUIModel.getId());
		rawEntity.setFaceBookID(individualCustomerUIModel.getFaceBookID());
		rawEntity.setEmail(individualCustomerUIModel.getEmail());
		rawEntity.setMobile(individualCustomerUIModel.getMobile());
		rawEntity.setBaseCityUUID(individualCustomerUIModel.getBaseCityUUID());
		rawEntity.setWeiXinID(individualCustomerUIModel.getWeiXinID());
		rawEntity.setCustomerType(individualCustomerUIModel.getCustomerType());
		rawEntity.setQqNumber(individualCustomerUIModel.getQqNumber());
		rawEntity.setAddress(individualCustomerUIModel.getAddress());
	}
	
	public void convIndividualCustomerToUI(
			IndividualCustomer individualCustomer,
			IndividualCustomerUIModel individualCustomerUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		convIndividualCustomerToUI(individualCustomer,individualCustomerUIModel, null);
	}

	public void convIndividualCustomerToUI(
			IndividualCustomer individualCustomer,
			IndividualCustomerUIModel individualCustomerUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (individualCustomer != null) {
			accountManager.convAccountToUI(individualCustomer, individualCustomerUIModel, logonInfo);			
			individualCustomerUIModel.setNote(individualCustomer.getNote());			
			individualCustomerUIModel.setFax(individualCustomer.getFax());
			individualCustomerUIModel.setRegularType(individualCustomer
					.getRegularType());			
			individualCustomerUIModel.setWebPage(individualCustomer
					.getWebPage());
			individualCustomerUIModel.setMobile(individualCustomer.getMobile());
			individualCustomerUIModel.setQqNumber(individualCustomer
					.getQqNumber());
			individualCustomerUIModel.setWeiboID(individualCustomer
					.getWeiboID());
			individualCustomerUIModel.setWeiXinID(individualCustomer
					.getWeiXinID());
			individualCustomerUIModel.setFaceBookID(individualCustomer
					.getFaceBookID());
			individualCustomerUIModel.setRegularType(individualCustomer
					.getRegularType());
			individualCustomerUIModel.setRefCityUUID(individualCustomer
					.getRefCityUUID());
			City city = cityManager.getCityByDefaultWay(
					individualCustomer.getCityName(),
					individualCustomer.getRefCityUUID());
			if (city != null) {
				individualCustomerUIModel.setCityName(city.getName());
				individualCustomerUIModel.setRefCityUUID(city.getUuid());
			}
			Map<Integer, String> regularTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(IndividualCustomerUIModel.class,
							"regularType");
			individualCustomerUIModel.setRegularTypeValue(regularTypeMap
					.get(individualCustomer.getRegularType()));
			individualCustomerUIModel.setRefCityUUID(individualCustomer
					.getRefCityUUID());
			individualCustomerUIModel.setTownZone(individualCustomer
					.getTownZone());
			individualCustomerUIModel.setSubArea(individualCustomer
					.getSubArea());
			individualCustomerUIModel.setStreetName(individualCustomer
					.getStreetName());
			individualCustomerUIModel.setHouseNumber(individualCustomer
					.getHouseNumber());
			individualCustomerUIModel.setPostcode(individualCustomer
					.getPostcode());
		}
	}

	public void convUIToIndividualCustomer(IndividualCustomer rawEntity,
			IndividualCustomerUIModel individualCustomerUIModel) {
		rawEntity.setId(individualCustomerUIModel.getId());
		rawEntity.setUuid(individualCustomerUIModel.getUuid());
		rawEntity.setName(individualCustomerUIModel.getName());
		rawEntity.setNote(individualCustomerUIModel.getNote());
		rawEntity.setAddress(individualCustomerUIModel.getAddress());
		rawEntity.setTelephone(individualCustomerUIModel.getTelephone());
		rawEntity.setFax(individualCustomerUIModel.getFax());
		rawEntity.setEmail(individualCustomerUIModel.getEmail());
		rawEntity.setWebPage(individualCustomerUIModel.getWebPage());
		rawEntity.setMobile(individualCustomerUIModel.getMobile());
		rawEntity.setQqNumber(individualCustomerUIModel.getQqNumber());
		rawEntity.setRefCityUUID(individualCustomerUIModel.getRefCityUUID());
		rawEntity.setRegularType(individualCustomerUIModel.getRegularType());
		rawEntity.setWeiboID(individualCustomerUIModel.getWeiboID());
		rawEntity.setWeiXinID(individualCustomerUIModel.getWeiXinID());
		rawEntity.setFaceBookID(individualCustomerUIModel.getFaceBookID());
		rawEntity.setRefCityUUID(individualCustomerUIModel.getRefCityUUID());
		rawEntity.setCityName(individualCustomerUIModel.getCityName());
		rawEntity.setTownZone(individualCustomerUIModel.getTownZone());
		rawEntity.setSubArea(individualCustomerUIModel.getSubArea());
		rawEntity.setStreetName(individualCustomerUIModel.getStreetName());
		rawEntity.setHouseNumber(individualCustomerUIModel.getHouseNumber());
		rawEntity.setPostcode(individualCustomerUIModel.getPostcode());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convIndividualCustomerAttachmentToUI(
			IndividualCustomerAttachment individualCustomerAttachment,
			IndividualCustomerAttachmentUIModel individualCustomerAttachmentUIModel) {
		if (individualCustomerAttachment != null) {
			if (!ServiceEntityStringHelper
					.checkNullString(individualCustomerAttachment.getUuid())) {
				individualCustomerAttachmentUIModel
						.setUuid(individualCustomerAttachment.getUuid());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(individualCustomerAttachment
							.getParentNodeUUID())) {
				individualCustomerAttachmentUIModel
						.setParentNodeUUID(individualCustomerAttachment
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(individualCustomerAttachment
							.getRootNodeUUID())) {
				individualCustomerAttachmentUIModel
						.setRootNodeUUID(individualCustomerAttachment
								.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper
					.checkNullString(individualCustomerAttachment.getClient())) {
				individualCustomerAttachmentUIModel
						.setClient(individualCustomerAttachment.getClient());
			}
			individualCustomerAttachmentUIModel
					.setAttachmentDescription(individualCustomerAttachment
							.getNote());
			individualCustomerAttachmentUIModel
					.setAttachmentTitle(individualCustomerAttachment.getName());

			individualCustomerAttachmentUIModel
					.setFileType(individualCustomerAttachment.getFileType());
		}
	}

	public void convCorporateContactToUIModel(
			CorporateContactPerson corporateContactPerson,
			IndividualCustomerUIModel individualCustomerUIModel)
			throws ServiceEntityInstallationException {
		if (corporateContactPerson != null && individualCustomerUIModel != null) {
			individualCustomerUIModel.setContactPosition(corporateContactPerson
					.getContactPosition());
			individualCustomerUIModel.setContactRole(corporateContactPerson
					.getContactRole());
			individualCustomerUIModel.setContactRoleNote(corporateContactPerson
					.getContactRoleNote());
			Map<Integer, String> contactRoleMap = serviceDropdownListHelper
					.getUIDropDownMap(IndividualCustomerUIModel.class,
							"contactRole");
			individualCustomerUIModel.setContactRoleValue(contactRoleMap
					.get(corporateContactPerson.getContactRole()));
			Map<Integer, String> contactPositionMap = serviceDropdownListHelper
					.getUIDropDownMap(IndividualCustomerUIModel.class,
							"contactPosition");
			individualCustomerUIModel
					.setContactPositionValue(contactPositionMap
							.get(corporateContactPerson.getContactPosition()));
		}
	}

	public void convUIToCorporateContact(
			IndividualCustomerUIModel individualCustomerUIModel,
			CorporateContactPerson corporateContactPerson) {
		if (corporateContactPerson != null && individualCustomerUIModel != null) {
			corporateContactPerson.setContactPosition(individualCustomerUIModel
					.getContactPosition());
			corporateContactPerson.setContactRole(individualCustomerUIModel
					.getContactRole());
			corporateContactPerson.setContactRoleNote(individualCustomerUIModel
					.getContactRoleNote());
		}
	}

	@Override
	public String getAuthorizationResource() {
		return IServiceModelConstants.IndividualCustomer;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return individualCustomerSearchProxy;
	}

}
