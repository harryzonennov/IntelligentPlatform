package com.company.IntelligentPlatform.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import com.company.IntelligentPlatform.common.dto.SerialNumberSettingUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.SerialNumberSettingRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DefaultDateFormatSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocumentBarCodeService;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.StandardSystemCategoryProxy;
import com.company.IntelligentPlatform.common.model.SerNumberSettingJSONItem;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.SerialNumberSettingConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [SerialNumberSetting]
 *
 * @author
 * @date Fri Nov 20 13:07:37 CST 2015
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class SerialNumberSettingManager extends ServiceEntityManager {

	public static final String METHOD_ConvSerialNumberSettingToUI = "convSerialNumberSettingToUI";

	public static final String METHOD_ConvUIToSerialNumberSetting = "convUIToSerialNumberSetting";
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected SerialNumberSettingRepository serialNumberSettingDAO;
	@Autowired
	protected SerialNumberSettingConfigureProxy serialNumberSettingConfigureProxy;

	@Autowired
	protected StandardSystemCategoryProxy standardSystemCategoryProxy;

	@Autowired
	protected StandardSwitchProxy standardSwitchProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected DocumentBarCodeService documentBarCodeService;

	@Autowired
	protected DefaultDateFormatSwitchProxy defaultDateFormatSwitchProxy;

	@Autowired
	protected SerialNumberSettingSearchProxy serialNumberSettingSearchProxy;

	private Map<Integer, String> categoryMap;

	private Map<Integer, String> switchFlagMap;

	private Map<Integer, String> seperatorMap;

	private Map<Integer, String> systemStandardCategoryMap;

	private Map<String, String> barcodeTypeMap;

	private Map<Integer, String> dateFormatMap;

	private Map<Integer, String> coreNumberFormatMap;

	private Map<Integer, String> ean13PostCodeGenTypeMap;

	private Map<Integer, String> ean13CompanyCodeGenTypeMap;

	public SerialNumberSettingManager() {
		super.seConfigureProxy = new SerialNumberSettingConfigureProxy();
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, serialNumberSettingDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(serialNumberSettingConfigureProxy);
	}

	public Map<String, String> initBarcodeTypeMap() throws ServiceEntityInstallationException {
		if (this.barcodeTypeMap == null) {
			this.barcodeTypeMap = documentBarCodeService.getBarcodeTypeMap();
		}
		return this.barcodeTypeMap;
	}

	public Map<Integer, String> initDateFormatMap() throws ServiceEntityInstallationException {
		if (this.dateFormatMap == null) {
			this.dateFormatMap = defaultDateFormatSwitchProxy.getFormatMap();
		}
		return this.dateFormatMap;
	}

	public Map<Integer, String> initCoreNumberFormatMap() throws ServiceEntityInstallationException {
		if (this.coreNumberFormatMap == null) {
			this.coreNumberFormatMap = serviceDropdownListHelper
					.getUIDropDownMap(SerialNumberSettingUIModel.class, "coreNumberLength");
		}
		return this.coreNumberFormatMap;
	}

	public Map<Integer, String> initEan13PostCodeGenTypeMap() throws ServiceEntityInstallationException {
		if (this.ean13PostCodeGenTypeMap == null) {
			this.ean13PostCodeGenTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(SerialNumberSettingUIModel.class, "ean13PostCodeGenType");
		}
		return this.ean13PostCodeGenTypeMap;
	}

	public Map<Integer, String> initEan13CompanyCodeGenTypeMap() throws ServiceEntityInstallationException {
		if (this.ean13CompanyCodeGenTypeMap == null) {
			this.ean13CompanyCodeGenTypeMap = serviceDropdownListHelper
					.getUIDropDownMap(SerialNumberSettingUIModel.class, "ean13CompanyCodeGenType");
		}
		return this.ean13CompanyCodeGenTypeMap;
	}

	public Map<Integer, String> initSwitchFlagMap() throws ServiceEntityInstallationException {
		if (this.switchFlagMap == null) {
			this.switchFlagMap = standardSwitchProxy.getSimpleSwitchMap();
		}
		return this.switchFlagMap;
	}

	public Map<Integer, String> initCategoryMap() throws ServiceEntityInstallationException {
		if (this.categoryMap == null) {
			this.categoryMap = serviceDropdownListHelper.getUIDropDownMap(SerialNumberSettingUIModel.class, "category");
		}
		return this.categoryMap;
	}

	public Map<Integer, String> initSystemStandardCategoryMap() throws ServiceEntityInstallationException {
		if (this.systemStandardCategoryMap == null) {
			this.systemStandardCategoryMap = standardSystemCategoryProxy.getSystemCategoryMap();
		}
		return this.systemStandardCategoryMap;
	}

	public Map<Integer, String> initSeperatorMap() throws ServiceEntityInstallationException {
		if (this.seperatorMap == null) {
			this.seperatorMap = serviceDropdownListHelper.getUIDropDownMap(SerialNumberSettingUIModel.class, "seperator1");
		}
		return this.seperatorMap;
	}

	/**
	 * Logic to get the seperator value by key
	 *
	 * @param key
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public String getSeperatorValue(int key) throws ServiceEntityInstallationException {
		Map<Integer, String> resultMap = this.initSeperatorMap();
		return resultMap.get(key);
	}

	public String getSeperatorValueFromJSON(String rawSeperator, List<ServiceEntityNode> rawSEList) {
		if (ServiceEntityStringHelper.checkNullString(rawSeperator) || ServiceCollectionsHelper.checkNullList(rawSEList)) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		JSONArray jsonArray = JSONArray.fromObject(rawSeperator);
		List<SerNumberSettingJSONItem> jsonItemList = JSONArray.toList(jsonArray, new SerNumberSettingJSONItem(), new JsonConfig());
		StringBuilder result = new StringBuilder(ServiceEntityStringHelper.EMPTYSTRING);
		for (SerNumberSettingJSONItem serNumberSettingJSONItem : jsonItemList) {
			if (ServiceEntityStringHelper.checkNullString(serNumberSettingJSONItem.getFieldName())) {
				continue;
			}
			ServiceEntityNode rawModel = ServiceCollectionsHelper
					.filterSENodeOnline(ServiceEntityStringHelper.headerToLowerCase(serNumberSettingJSONItem.getModelName()),
							rawSEList, tempSeNode -> {
								return ServiceEntityStringHelper
										.headerToLowerCase(ServiceDocumentComProxy.getServiceEntityModelName(tempSeNode));
							});
			if (rawModel == null) {
				continue;
			}
			try {
				String coreValue = parseCoreFieldValue(serNumberSettingJSONItem, rawModel);
				if (!ServiceEntityStringHelper.checkNullString(coreValue)) {
					result.append(coreValue);
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				// do nothing
			}
		}
		return result.toString();
	}

	/**
	 * [Internal method] parse core field value
	 *
	 * @param serNumberSettingJSONItem
	 * @param rawModel
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private String parseCoreFieldValue(SerNumberSettingJSONItem serNumberSettingJSONItem, ServiceEntityNode rawModel)
			throws NoSuchFieldException, IllegalAccessException {
		Object rawValue = ServiceEntityFieldsHelper.getServiceFieldValueWrapper(rawModel, serNumberSettingJSONItem.getFieldName());
		String rawStr = ServiceEntityStringHelper.EMPTYSTRING;
		if (rawValue != null) {
			rawStr = rawValue.toString();
			int length = rawStr.length();
			int startNumber = serNumberSettingJSONItem.getStartPos();
			int endNumber = serNumberSettingJSONItem.getEndPos();
			if(startNumber > endNumber){
				endNumber = length;
			}
			startNumber = Math.min(startNumber, length);
			endNumber = Math.min(endNumber, length);
			startNumber = Math.max(startNumber, 0);
			endNumber = Math.max(endNumber, 0);
			if (ServiceEntityStringHelper.checkNullString(serNumberSettingJSONItem.getBack())){
				return ServiceEntityStringHelper
						.getSubString(rawValue.toString(), startNumber, endNumber);
			} else {
				return ServiceEntityStringHelper
						.getSubString(rawValue.toString(), length - endNumber, length - startNumber);
			}
		}
		return null;
	}

	/**
	 * Get barcode type by model name
	 *
	 * @param key    :model name
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceBarcodeException
	 */
	public String getBarcodeType(String key, String client) throws ServiceEntityConfigureException, ServiceBarcodeException {
		SerialNumberSetting serialNumberSetting = (SerialNumberSetting) getEntityNodeByKey(key, IServiceEntityNodeFieldConstant.ID,
				SerialNumberSetting.NODENAME, client, null, true);
		if (serialNumberSetting == null) {
			throw new ServiceBarcodeException(ServiceBarcodeException.PARA_NO_ENCODETYPE, key);
		}
		return serialNumberSetting.getBarcodeType();
	}

	/**
	 * Core logic to generate
	 *
	 * @param key
	 * @return
	 */
	public String getTimeFormat(int key, Date date) {
		if (key == DefaultDateFormatConstant.FORT_YYYY_MM_DD) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YY_MM) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YY_MM_DD) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YYMM) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YY) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YYMMDD) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		if (key == DefaultDateFormatConstant.FORT_YYYYMMDD) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			String dateString = dateFormat.format(date);
			return dateString;
		}
		return null;
	}

	public void convSerialNumberSettingToUI(SerialNumberSetting serialNumberSetting,
			SerialNumberSettingUIModel serialNumberSettingUIModel) throws ServiceEntityInstallationException {
		if (serialNumberSetting != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(serialNumberSetting, serialNumberSettingUIModel);
			serialNumberSettingUIModel.setPrefixCode1(serialNumberSetting.getPrefixCode1());
			serialNumberSettingUIModel.setSeperator1(serialNumberSetting.getSeperator1());
			serialNumberSettingUIModel.setSeperator1Json(serialNumberSetting.getSeperator1Json());
			serialNumberSettingUIModel.setPrefixTimeCode(serialNumberSetting.getPrefixTimeCode());
			serialNumberSettingUIModel.setTimeCodeFormat(serialNumberSetting.getTimeCodeFormat());
			serialNumberSettingUIModel.setPostfixTimeCode(serialNumberSetting.getPostfixTimeCode());
			serialNumberSettingUIModel.setSeperator2(serialNumberSetting.getSeperator2());
			serialNumberSettingUIModel.setSeperator2Json(serialNumberSetting.getSeperator2Json());
			serialNumberSettingUIModel.setCoreNumberLength(serialNumberSetting.getCoreNumberLength());
			serialNumberSettingUIModel.setCoreStartNumber(serialNumberSetting.getCoreStartNumber());
			serialNumberSettingUIModel.setCoreStepSize(serialNumberSetting.getCoreStepSize());
			serialNumberSettingUIModel.setSwitchFlag(serialNumberSetting.getSwitchFlag());
			serialNumberSettingUIModel.setSystemStandardCategory(serialNumberSetting.getSystemStandardCategory());
			serialNumberSettingUIModel.setCategory(serialNumberSetting.getCategory());
			this.initSystemStandardCategoryMap();
			serialNumberSettingUIModel.setSystemStandardCategoryValue(
					this.systemStandardCategoryMap.get(serialNumberSetting.getSystemStandardCategory()));
			this.initSwitchFlagMap();
			serialNumberSettingUIModel.setSwitchFlagValue(this.switchFlagMap.get(serialNumberSetting.getSwitchFlag()));
			this.initCategoryMap();
			serialNumberSettingUIModel.setCategoryValue(this.categoryMap.get(serialNumberSetting.getCategory()));
			serialNumberSettingUIModel.setBarcodeType(serialNumberSetting.getBarcodeType());
			serialNumberSettingUIModel.setEan13CompanyCodeGenType(serialNumberSetting.getEan13CompanyCodeGenType());
			serialNumberSettingUIModel.setEan13PostCodeGenType(serialNumberSetting.getEan13CompanyCodeGenType());
			serialNumberSettingUIModel.setBarcodeStartValue(serialNumberSetting.getBarcodeStartValue());
			serialNumberSettingUIModel.setBarcodeAsendSize(serialNumberSetting.getBarcodeAsendSize());
			serialNumberSettingUIModel.setQrcodeType(serialNumberSetting.getQrcodeType());
		}
	}

	public void convUIToSerialNumberSetting(SerialNumberSettingUIModel serialNumberSettingUIModel, SerialNumberSetting rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(serialNumberSettingUIModel, rawEntity);
		rawEntity.setPrefixCode1(serialNumberSettingUIModel.getPrefixCode1());
		rawEntity.setSeperator1(serialNumberSettingUIModel.getSeperator1());
		if(ServiceEntityStringHelper.checkNullString(serialNumberSettingUIModel.getSeperator1Json())){
			rawEntity.setSeperator1Json("[]");
		} else {
			rawEntity.setSeperator1Json(serialNumberSettingUIModel.getSeperator1Json());
		}
		rawEntity.setSwitchFlag(serialNumberSettingUIModel.getSwitchFlag());
		rawEntity.setPrefixTimeCode(serialNumberSettingUIModel.getPrefixTimeCode());
		rawEntity.setTimeCodeFormat(serialNumberSettingUIModel.getTimeCodeFormat());
		rawEntity.setPostfixTimeCode(serialNumberSettingUIModel.getPostfixTimeCode());
		rawEntity.setSeperator2(serialNumberSettingUIModel.getSeperator2());
		rawEntity.setSeperator2Json(serialNumberSettingUIModel.getSeperator2Json());
		if(ServiceEntityStringHelper.checkNullString(serialNumberSettingUIModel.getSeperator2Json())){
			rawEntity.setSeperator2Json("[]");
		} else {
			rawEntity.setSeperator2Json(serialNumberSettingUIModel.getSeperator2Json());
		}
		rawEntity.setCoreNumberLength(serialNumberSettingUIModel.getCoreNumberLength());
		rawEntity.setCoreStartNumber(serialNumberSettingUIModel.getCoreStartNumber());
		rawEntity.setCoreStepSize(serialNumberSettingUIModel.getCoreStepSize());
		rawEntity.setBarcodeType(serialNumberSettingUIModel.getBarcodeType());
		rawEntity.setEan13CompanyCodeGenType(serialNumberSettingUIModel.getEan13CompanyCodeGenType());
		rawEntity.setEan13PostCodeGenType(serialNumberSettingUIModel.getEan13CompanyCodeGenType());
		rawEntity.setBarcodeStartValue(serialNumberSettingUIModel.getBarcodeStartValue());
		rawEntity.setBarcodeAsendSize(serialNumberSettingUIModel.getBarcodeAsendSize());
		rawEntity.setQrcodeType(serialNumberSettingUIModel.getQrcodeType());
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return serialNumberSettingSearchProxy;
	}

}
