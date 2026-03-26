package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.MatConfigExtPropertySettingSearchModel;
import com.company.IntelligentPlatform.common.dto.MatConfigExtPropertySettingUIModel;
import com.company.IntelligentPlatform.common.dto.MaterialConfigureTemplateSearchModel;
import com.company.IntelligentPlatform.common.dto.MaterialConfigureTemplateUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.MaterialConfigureTemplateRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.model.MatConfigExtPropertySetting;
import com.company.IntelligentPlatform.common.model.MatConfigHeaderCondition;
import com.company.IntelligentPlatform.common.model.MatDecisionValueSetting;
import com.company.IntelligentPlatform.common.model.Material;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplate;
import com.company.IntelligentPlatform.common.model.MaterialConfigureTemplateConfigureProxy;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.MaterialType;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardFieldTypeProxy;
import com.company.IntelligentPlatform.common.service.StandardLogicOperatorProxy;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class MaterialConfigureTemplateManager extends ServiceEntityManager {

	public static final String METHOD_ConvMaterialConfigureTemplateToUI = "convMaterialConfigureTemplateToUI";

	public static final String METHOD_ConvUIToMaterialConfigureTemplate = "convUIToMaterialConfigureTemplate";

	public static final String ResFileName_RefNodeInstId = "MatConfigHeaderCondition_RefNodeInstId";

	@Autowired
	protected BsearchService bsearchService;

	@Autowired
	protected StandardLogicOperatorProxy standardLogicOperatorProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected MaterialConfigureTemplateRepository materialConfigureTemplateDAO;
	@Autowired
	protected MaterialConfigureTemplateConfigureProxy materialConfigureTemplateConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	@Autowired
	protected MaterialConfigureTemplateSearchProxy materialConfigureTemplateSearchProxy;

	@Autowired
	protected StandardFieldTypeProxy standardFieldTypeProxy;

	private Map<String, String> fieldNameMap;

	public Map<Integer, String> initLogicOperatorMap(String languageCode)
			throws ServiceEntityInstallationException {
		return standardLogicOperatorProxy.getLogicOperatorMap(languageCode);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convMaterialConfigureTemplateToUI(
			MaterialConfigureTemplate materialConfigureTemplate,
			MaterialConfigureTemplateUIModel materialConfigureTemplateUIModel) {
		if (materialConfigureTemplate != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(materialConfigureTemplate, materialConfigureTemplateUIModel);
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:materialConfigureTemplate
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToMaterialConfigureTemplate(
			MaterialConfigureTemplateUIModel materialConfigureTemplateUIModel,
			MaterialConfigureTemplate rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(materialConfigureTemplateUIModel, rawEntity);
	}

	public List<ServiceEntityNode> searchExtPropertySetting(
			MatConfigExtPropertySettingSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[matDecisionValueSetting]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(MatConfigExtPropertySetting.SENAME);
		searchNodeConfig0.setNodeName(MatConfigExtPropertySetting.NODENAME);
		searchNodeConfig0.setNodeInstID(MatConfigExtPropertySetting.NODENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, materialConfigureTemplateDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(materialConfigureTemplateConfigureProxy);
	}

	public Map<String, String> initFieldNameMap(Class<?> baseSEType)
			throws ServiceEntityInstallationException {
		List<Field> fieldList = ServiceEntityFieldsHelper
				.getFieldsList(baseSEType);
		this.fieldNameMap = new HashMap<String, String>();
		if (!ServiceCollectionsHelper.checkNullList(fieldList)) {
			for (Field field : fieldList) {
				this.fieldNameMap.put(field.getName(), field.getName());
			}
		}
		return this.fieldNameMap;
	}

	public Class<?> getNodeInstIdClass(String nodeInstId)
			throws ServiceEntityInstallationException {
		if (nodeInstId.equals(MaterialType.SENAME)) {
			return MaterialType.class;
		}
		if (nodeInstId.equals(MaterialStockKeepUnit.SENAME)) {
			return MaterialStockKeepUnit.class;
		}
		if (nodeInstId.equals(Material.SENAME)) {
			return Material.class;
		}
		return null;
	}

	public Map<Integer, String> initMeasureFlagMap(String lanCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(lanCode);
	}

	public Map<Integer, String> initQualityInspectMap(String lanCode)
			throws ServiceEntityInstallationException {
		return standardSwitchProxy.getSimpleSwitchMap(lanCode);
	}

	public Map<String, String> initFieldTypeMap()
			throws ServiceEntityInstallationException {
		return standardFieldTypeProxy.getFieldTypeMap();
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return materialConfigureTemplateSearchProxy;
	}
}
