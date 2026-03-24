package com.company.IntelligentPlatform.common.service;

// TODO-LEGACY: import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.ServiceDefaultIdGenerateHelper;
import com.company.IntelligentPlatform.common.service.ServiceBarcodeException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.OrganizationBarcodeBasicSetting;
import com.company.IntelligentPlatform.common.model.SerialNumberSetting;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class EAN13BarcodeGenerator implements IServiceBarcodeGenerator{

	@Autowired
	protected OrganizationBarcodeBasicSettingManager organizationBarcodeBasicSettingManager;

	@Autowired
	protected ServiceDefaultIdGenerateHelper serviceDefaultIdGenerateHelper;

	public String genBarcode(SerialNumberSetting serialNumberSetting,
			String tableName) throws ServiceEntityConfigureException,
			SearchConfigureException, ServiceBarcodeException {
		OrganizationBarcodeBasicSetting curHostBarcodeSetting = organizationBarcodeBasicSettingManager
				.getCurHostCompanyBarcodeBasicSetting(serialNumberSetting
						.getClient());
		if (curHostBarcodeSetting == null) {
			// Should raise exception
			throw new ServiceBarcodeException(ServiceBarcodeException.TYPE_NO_HOMEORG_BARCODESETTING);
		}
		checkValidBarcodeSetting(serialNumberSetting);
		int countryHead = curHostBarcodeSetting.getEan13CountryHead();
		String countryHeadString = new Integer(countryHead).toString();
		int companyCode = generateCompanyCode(serialNumberSetting,
				curHostBarcodeSetting, tableName);
		String companyCodeString = new Integer(companyCode).toString();
		int postCode = generatePostCode(serialNumberSetting,
				curHostBarcodeSetting, tableName);
		String postCodeString = new Integer(postCode).toString();
		// Special handing for ascend
		// In case using asending value, need to combine with post code
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_ASCEND
				&& serialNumberSetting.getEan13PostCodeGenType() == SerialNumberSetting.EAN13_POST_GENTYPE_ASCEND) {
			return generateAsendBarcode(countryHead, 9,
					serialNumberSetting.getBarcodeAsendSize(), serialNumberSetting.getBarcodeStartValue(), tableName,
					serialNumberSetting.getClient());
		}
		String barcode = countryHeadString + companyCodeString + postCodeString;
		return barcode;
	}

	protected int generateCompanyCode(SerialNumberSetting serialNumberSetting,
			OrganizationBarcodeBasicSetting curHostBarcodeSetting,
			String tableName) throws ServiceEntityConfigureException {
		// In case company value is fixed and retrieve from setting company
		// value
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_FIXCOMVALUE) {
			return curHostBarcodeSetting.getEan13CompanyCode();
		}
		// In case using the setting value
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_FIXSETVALUE) {
			return serialNumberSetting.getBarcodeStartValue();
		}
		// In case using the setting value
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_RANDOM) {
			if (curHostBarcodeSetting.getEan13CountryHead() == OrganizationBarcodeBasicSetting.EAN13_COUNTRYHEAD_690
					|| curHostBarcodeSetting.getEan13CountryHead() == OrganizationBarcodeBasicSetting.EAN13_COUNTRYHEAD_691) {
				return new Integer(RandomStringUtils.randomNumeric(4));
			} else {
				return new Integer(RandomStringUtils.randomNumeric(5));
			}
		}
		// In case using asending value, need to combine with post code
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_ASCEND) {
			return 0;
		}
		return 0;
	}

	protected String generateAsendBarcode(int prefix, int indexLength,
			int size, int startValue, String tableName, String client)
			throws SearchConfigureException {
		int lastBarcodeIndex = serviceDefaultIdGenerateHelper
				.getLastBarcodeIndex(client, tableName, indexLength);
		String prefixString = new Integer(prefix).toString();
		if(lastBarcodeIndex == 0){
			// In case empty index
			lastBarcodeIndex = startValue;
		} else {
			lastBarcodeIndex = lastBarcodeIndex + size * 1;
		}
		return prefixString + new Integer(lastBarcodeIndex).toString();
	}

	protected int generatePostCode(SerialNumberSetting serialNumberSetting,
			OrganizationBarcodeBasicSetting curHostBarcodeSetting,
			String tableName) throws ServiceEntityConfigureException, SearchConfigureException {
		// In case company value is fixed and retrieve from setting company
		// value
		int indexLength = 4;
		if (curHostBarcodeSetting.getEan13CountryHead() == OrganizationBarcodeBasicSetting.EAN13_COUNTRYHEAD_690
				|| curHostBarcodeSetting.getEan13CountryHead() == OrganizationBarcodeBasicSetting.EAN13_COUNTRYHEAD_691) {
			indexLength = 5;
		}
		if (serialNumberSetting.getEan13PostCodeGenType() == SerialNumberSetting.EAN13_POST_GENTYPE_RANDOM) {
			return new Integer(RandomStringUtils.randomNumeric(indexLength));
		}
		// In case using the setting value
		if (serialNumberSetting.getEan13PostCodeGenType() == SerialNumberSetting.EAN13_POST_GENTYPE_ASCEND) {
			int lastBarcodeIndex = serviceDefaultIdGenerateHelper
					.getLastBarcodeIndex(serialNumberSetting.getClient(), tableName, indexLength);
			if(lastBarcodeIndex == 0){
				// In case empty index
				return serialNumberSetting.getBarcodeStartValue();
			} else {
				return lastBarcodeIndex + serialNumberSetting.getBarcodeAsendSize() * 1;
			}
		}
		return 0;
	}

	public void checkValidBarcodeSetting(SerialNumberSetting serialNumberSetting) throws ServiceBarcodeException {
		if (serialNumberSetting.getEan13CompanyCodeGenType() == SerialNumberSetting.EAN13_COMP_GENTYPE_RANDOM) {
			if (serialNumberSetting.getEan13PostCodeGenType() == SerialNumberSetting.EAN13_POST_GENTYPE_ASCEND){
				throw new ServiceBarcodeException(ServiceBarcodeException.TYPE_INVALID_CODEGENTYPE_COMBINE);
			}
		}
	}

}
