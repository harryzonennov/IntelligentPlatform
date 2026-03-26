package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.PricingCurrencyConfigureSearchModel;
import com.company.IntelligentPlatform.common.dto.PricingCurrencyConfigureUIModel;
import com.company.IntelligentPlatform.common.dto.PricingSettingSearchModel;
import com.company.IntelligentPlatform.common.dto.PricingSettingUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.PricingSettingRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.PricingCurrencyConfigure;
import com.company.IntelligentPlatform.common.model.PricingSetting;
import com.company.IntelligentPlatform.common.model.PricingSettingConfigureProxy;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class PricingSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvPricingCurrencyConfigureToUI = "convPricingCurrencyConfigureToUI";

	public static final String METHOD_ConvUIToPricingCurrencyConfigure = "convUIToPricingCurrencyConfigure";

	public static final String METHOD_ConvPricingSettingToUI = "convPricingSettingToUI";

	public static final String METHOD_ConvUIToPricingSetting = "convUIToPricingSetting";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected PricingSettingRepository pricingSettingDAO;
	@Autowired
	protected PricingSettingConfigureProxy pricingSettingConfigureProxy;

	@Autowired
	protected PricingSettingSearchProxy pricingSettingSearchProxy;

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPricingCurrencyConfigureToUI(
			PricingCurrencyConfigure pricingCurrencyConfigure,
			PricingCurrencyConfigureUIModel pricingCurrencyConfigureUIModel) {
		if (pricingCurrencyConfigure != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(pricingCurrencyConfigure, pricingCurrencyConfigureUIModel);
			pricingCurrencyConfigureUIModel
					.setCurrencyCode(pricingCurrencyConfigure.getCurrencyCode());
			pricingCurrencyConfigureUIModel
					.setDefaultCurrency(pricingCurrencyConfigure
							.getDefaultCurrency());
			pricingCurrencyConfigureUIModel
					.setActiveFlag(pricingCurrencyConfigure.getActiveFlag());
			pricingCurrencyConfigureUIModel
					.setExchangeRate(pricingCurrencyConfigure.getExchangeRate());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:pricingCurrencyConfigure
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToPricingCurrencyConfigure(
			PricingCurrencyConfigureUIModel pricingCurrencyConfigureUIModel,
			PricingCurrencyConfigure rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(pricingCurrencyConfigureUIModel, rawEntity);
		rawEntity.setCurrencyCode(pricingCurrencyConfigureUIModel
				.getCurrencyCode());
		rawEntity.setExchangeRate(pricingCurrencyConfigureUIModel
				.getExchangeRate());
		rawEntity
				.setActiveFlag(pricingCurrencyConfigureUIModel.getActiveFlag());
		rawEntity.setDefaultCurrency(pricingCurrencyConfigureUIModel
				.getDefaultCurrency());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPricingSettingToUI(PricingSetting pricingSetting,
			PricingSettingUIModel pricingSettingUIModel) {
		if (pricingSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(pricingSetting, pricingSettingUIModel);
			pricingSettingUIModel.setDefaultTaxRate(pricingSetting
					.getDefaultTaxRate());
			pricingSettingUIModel.setMultiCurrencyFlag(pricingSetting
					.getMultiCurrencyFlag());
			pricingSettingUIModel.setDefCurrencyCode(pricingSetting
					.getDefCurrencyCode());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:pricingSetting
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToPricingSetting(
			PricingSettingUIModel pricingSettingUIModel,
			PricingSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(pricingSettingUIModel, rawEntity);
		rawEntity.setDefaultTaxRate(pricingSettingUIModel.getDefaultTaxRate());
		rawEntity.setMultiCurrencyFlag(pricingSettingUIModel
				.getMultiCurrencyFlag());
		rawEntity
				.setDefCurrencyCode(pricingSettingUIModel.getDefCurrencyCode());
		rawEntity.setClient(pricingSettingUIModel.getClient());
	}

	
	/**
	 * Logic to get system default currency
	 * @param client
	 * @return
	 */
	public PricingCurrencyConfigure getDefaultCurrency(String client) throws SearchConfigureException {
		PricingCurrencyConfigureSearchModel pricingCurrencyConfigureSearchModel = new PricingCurrencyConfigureSearchModel();
		pricingCurrencyConfigureSearchModel.setActiveFlag(true);
		pricingCurrencyConfigureSearchModel.setDefaultCurrency(true);
		List<ServiceEntityNode> rawList;
		SearchContext searchContext = new SearchContextBuilder().client(client).searchModel(pricingCurrencyConfigureSearchModel).
				acId(ISystemActionCode.ACID_LIST).aoId(PricingSetting.SENAME).build();
		try {
			BSearchResponse bSearchResponse = getSearchProxy().searchItemList(searchContext);
			rawList = bSearchResponse.getResultList();
			if(!ServiceCollectionsHelper.checkNullList(rawList)){
				return (PricingCurrencyConfigure) rawList.get(0);
			} else {
				return null;
			}
		} catch (SearchConfigureException | ServiceEntityConfigureException | ServiceEntityInstallationException e) {
			return null;
		} catch (AuthorizationException | LogonInfoException e) {
            throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, pricingSettingDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(pricingSettingConfigureProxy);
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return pricingSettingSearchProxy;
	}
}
