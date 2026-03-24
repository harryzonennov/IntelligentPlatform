package com.company.IntelligentPlatform.finance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinanceDocument;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [FinanceDocument]
 *
 * @author
 * @date Wed May 08 13:27:09 CST 2013
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class FinanceDocumentConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.finance");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of FinanceDocument [ROOT] node
		ServiceEntityConfigureMap financeDocumentConfigureMap = new ServiceEntityConfigureMap();
		financeDocumentConfigureMap.setParentNodeName(" ");
		financeDocumentConfigureMap.setNodeName("ROOT");
		financeDocumentConfigureMap.setNodeType(FinanceDocument.class);
		financeDocumentConfigureMap.setTableName("FinanceDocument");
		financeDocumentConfigureMap
				.setFieldList(super.getBasicSENodeFieldMap());
		financeDocumentConfigureMap.addNodeFieldMap("documentType", int.class);
		financeDocumentConfigureMap.addNodeFieldMap("objectName",
				java.lang.String.class);
		financeDocumentConfigureMap.addNodeFieldMap("accountTitleName",
				java.lang.String.class);
		financeDocumentConfigureMap.addNodeFieldMap("paymentsType", int.class);
		financeDocumentConfigureMap.addNodeFieldMap("amount", double.class);
		financeDocumentConfigureMap.addNodeFieldMap("accountant",
				java.lang.String.class);
		financeDocumentConfigureMap.addNodeFieldMap("cashier",
				java.lang.String.class);
		financeDocumentConfigureMap.addNodeFieldMap("acountUUID",
				java.lang.String.class);
		financeDocumentConfigureMap.addNodeFieldMap("accountTitleUUID",
				java.lang.String.class);
		seConfigureMapList.add(financeDocumentConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
