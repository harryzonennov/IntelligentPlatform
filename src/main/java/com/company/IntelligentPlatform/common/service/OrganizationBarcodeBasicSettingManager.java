package com.company.IntelligentPlatform.common.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.OrganizationBarcodeBasicSettingUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.OrganizationBarcodeBasicSettingRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.HostCompanyManager;
import com.company.IntelligentPlatform.common.model.HostCompany;
import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSetting;
import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSettingConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [OrganizationBarcodeBasicSetting]
 *
 * @author
 * @date Mon Mar 14 22:23:05 CST 2016
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Service
@Transactional
public class OrganizationBarcodeBasicSettingManager extends
		ServiceEntityManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected OrganizationBarcodeBasicSettingRepository organizationBarcodeBasicSettingDAO;
	@Autowired
	OrganizationBarcodeBasicSettingConfigureProxy organizationBarcodeBasicSettingConfigureProxy;	

	@Autowired
	protected HostCompanyManager hostCompanyManager;

	public OrganizationBarcodeBasicSettingManager() {
		super.seConfigureProxy = new OrganizationBarcodeBasicSettingConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, organizationBarcodeBasicSettingDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(organizationBarcodeBasicSettingConfigureProxy);
	}
	
	public OrganizationBarcodeBasicSetting getCurHostCompanyBarcodeBasicSetting(
			String client) throws ServiceEntityConfigureException {
		HostCompany hostCompany = hostCompanyManager.getCurHostCompany(client);
		OrganizationBarcodeBasicSetting organizationBarcodeBasicSetting = (OrganizationBarcodeBasicSetting) getEntityNodeByKey(hostCompany.getUuid(),
						"refOrganizationUUID",
						OrganizationBarcodeBasicSetting.NODENAME, client, null);
		return organizationBarcodeBasicSetting;		
	}

	public void convUIToOrganizationBarcodeBasicSetting(
			OrganizationBarcodeBasicSettingUIModel organizationBarcodeBasicSettingUIModel,
			OrganizationBarcodeBasicSetting organizationBarcodeBasicSetting) {
		if (organizationBarcodeBasicSetting != null
				&& organizationBarcodeBasicSettingUIModel != null) {
			DocFlowProxy.convUIToServiceEntityNode(organizationBarcodeBasicSettingUIModel, organizationBarcodeBasicSetting);
			organizationBarcodeBasicSetting
					.setRefOrganizationUUID(organizationBarcodeBasicSettingUIModel
							.getRefOrganizationUUID());
			organizationBarcodeBasicSetting
					.setEan13CompanyCode(organizationBarcodeBasicSettingUIModel
							.getEan13CompanyCode());
			organizationBarcodeBasicSetting
					.setEan13CountryHead(organizationBarcodeBasicSettingUIModel
							.getEan13CountryHead());
		}
	}

	public void convOrganizationBarcodeBasicSettingToUI(
			OrganizationBarcodeBasicSetting organizationBarcodeBasicSetting,
			OrganizationBarcodeBasicSettingUIModel organizationBarcodeBasicSettingUIModel) {
		if (organizationBarcodeBasicSetting != null
				&& organizationBarcodeBasicSettingUIModel != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(organizationBarcodeBasicSetting, organizationBarcodeBasicSettingUIModel);
			organizationBarcodeBasicSettingUIModel
					.setRefOrganizationUUID(organizationBarcodeBasicSetting
							.getRefOrganizationUUID());
			organizationBarcodeBasicSettingUIModel
					.setEan13CompanyCode(organizationBarcodeBasicSetting
							.getEan13CompanyCode());
			organizationBarcodeBasicSettingUIModel
					.setEan13CountryHead(organizationBarcodeBasicSetting
							.getEan13CountryHead());
		}
	}
}
