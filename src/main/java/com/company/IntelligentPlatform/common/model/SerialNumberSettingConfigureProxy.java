package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [SerialNumberSetting]
 * 
 * @author
 * @date Fri Nov 20 13:07:37 CST 2015
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class SerialNumberSettingConfigureProxy extends
		ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of SerialNumberSetting [ROOT] node
		ServiceEntityConfigureMap serialNumberSettingConfigureMap = new ServiceEntityConfigureMap();
		serialNumberSettingConfigureMap.setParentNodeName(" ");
		serialNumberSettingConfigureMap
				.setNodeName(SerialNumberSetting.NODENAME);
		serialNumberSettingConfigureMap.setNodeType(SerialNumberSetting.class);
		serialNumberSettingConfigureMap
				.setTableName(SerialNumberSetting.SENAME);
		serialNumberSettingConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		serialNumberSettingConfigureMap.addNodeFieldMap("prefixCode1",
				java.lang.String.class);
		serialNumberSettingConfigureMap
				.addNodeFieldMap("seperator1", int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("seperator1Json",
				java.lang.String.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("prefixTimeCode",
				java.lang.String.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("timeCodeFormat",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("postfixTimeCode",
				java.lang.String.class);
		serialNumberSettingConfigureMap
				.addNodeFieldMap("seperator2", int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("seperator2Json",
				java.lang.String.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("coreNumberLength",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("coreStartNumber",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("coreStepSize",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("switchFlag",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("category",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("systemStandardCategory",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("barcodeType",
				java.lang.String.class);		
		serialNumberSettingConfigureMap.addNodeFieldMap("ean13CompanyCodeGenType",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("ean13PostCodeGenType",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("barcodeAsendSize",
				int.class);
		serialNumberSettingConfigureMap.addNodeFieldMap("barcodeStartValue",
				int.class);		
		serialNumberSettingConfigureMap.addNodeFieldMap("qrcodeType",
				java.lang.String.class);
		seConfigureMapList.add(serialNumberSettingConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
