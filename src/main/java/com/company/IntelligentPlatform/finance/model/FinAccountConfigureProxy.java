package com.company.IntelligentPlatform.finance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.company.IntelligentPlatform.finance.model.FinAccDocRef;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountLog;
import com.company.IntelligentPlatform.finance.model.FinAccountMatItemAttachment;
import com.company.IntelligentPlatform.finance.model.FinAccountMaterialItem;
import com.company.IntelligentPlatform.finance.model.FinAccountObjectRef;
import com.company.IntelligentPlatform.finance.model.FinAccountPrerequirement;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
 * Configure Proxy CLASS FOR Service Entity [FinAccount]
 *
 * @author
 * @date Fri May 23 13:58:58 CST 2014
 *
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class FinAccountConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("net.thorstein.finance");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
		// Init configuration of FinAccount [ROOT] node
		ServiceEntityConfigureMap finAccountConfigureMap = new ServiceEntityConfigureMap();
		finAccountConfigureMap.setParentNodeName(" ");
		finAccountConfigureMap.setNodeName(FinAccount.NODENAME);
		finAccountConfigureMap.setNodeType(FinAccount.class);
		finAccountConfigureMap.setTableName("FinAccount");
		finAccountConfigureMap.setFieldList(super.getBasicDocumentFieldMap());
		finAccountConfigureMap.addNodeFieldMap("amount", double.class);
		finAccountConfigureMap.addNodeFieldMap("accountTitleUUID",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("auditStatus", int.class);
		finAccountConfigureMap.addNodeFieldMap("verifyStatus", int.class);
		finAccountConfigureMap.addNodeFieldMap("recordStatus", int.class);
		finAccountConfigureMap.addNodeFieldMap("recordLock", boolean.class);
		finAccountConfigureMap.addNodeFieldMap("verifyLock", boolean.class);
		finAccountConfigureMap.addNodeFieldMap("auditLock", boolean.class);
		finAccountConfigureMap.addNodeFieldMap("auditNote",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("accountObject",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("accountantUUID",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("cashierUUID",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("paymentType", int.class);
		finAccountConfigureMap.addNodeFieldMap("auditTime",
				java.util.Date.class);
		finAccountConfigureMap.addNodeFieldMap("auditBy",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("auditLockMSG",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("verifyTime",
				java.util.Date.class);
		finAccountConfigureMap.addNodeFieldMap("verifyBy",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("verifyLockMSG",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("verifyNote",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("recordTime",
				java.util.Date.class);
		finAccountConfigureMap.addNodeFieldMap("recordBy",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("recordNote",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("recordLockMSG",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("execOrgUUID",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("recordedAmount", double.class);
		finAccountConfigureMap.addNodeFieldMap("toRecordAmount", double.class);
		finAccountConfigureMap.addNodeFieldMap("financeTime",
				java.util.Date.class);
		finAccountConfigureMap.addNodeFieldMap("adjustAmount", double.class);
		finAccountConfigureMap.addNodeFieldMap("adjustDirection", int.class);
		finAccountConfigureMap.addNodeFieldMap("currencyCode",
				java.lang.String.class);
		finAccountConfigureMap.addNodeFieldMap("amountInSetCurrency",
				double.class);
		finAccountConfigureMap.addNodeFieldMap("exchangeRate", double.class);
		finAccountConfigureMap.addNodeFieldMap("recordedAmountInSetCurrency;",
				double.class);
		finAccountConfigureMap.addNodeFieldMap("toRecordAmountInSetCurrency;",
				double.class);
		finAccountConfigureMap.addNodeFieldMap("refAccountObjectUUID",
				java.lang.String.class);
		seConfigureMapList.add(finAccountConfigureMap);
		// Init configuration of FinAccount [FinAccountMaterialItem] node
		ServiceEntityConfigureMap finAccountMaterialItemConfigureMap = new ServiceEntityConfigureMap();
		finAccountMaterialItemConfigureMap
				.setParentNodeName(FinAccount.NODENAME);
		finAccountMaterialItemConfigureMap
				.setNodeName(FinAccountMaterialItem.NODENAME);
		finAccountMaterialItemConfigureMap
				.setNodeType(FinAccountMaterialItem.class);
		finAccountMaterialItemConfigureMap
				.setTableName(FinAccountMaterialItem.NODENAME);
		finAccountMaterialItemConfigureMap.setFieldList(super
				.getBasicDocMatItemMap());
		seConfigureMapList.add(finAccountMaterialItemConfigureMap);
		// Init configuration of FinAccount [FinAccountLog] node
		ServiceEntityConfigureMap finAccountLogConfigureMap = new ServiceEntityConfigureMap();
		finAccountLogConfigureMap.setParentNodeName(FinAccount.NODENAME);
		finAccountLogConfigureMap.setNodeName("FinAccountLog");
		finAccountLogConfigureMap.setNodeType(FinAccountLog.class);
		finAccountLogConfigureMap.setTableName("FinAccountLog");
		finAccountLogConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		finAccountLogConfigureMap.addNodeFieldMap("financeDate",
				java.util.Date.class);
		finAccountLogConfigureMap.addNodeFieldMap("previousAmount",
				double.class);
		finAccountLogConfigureMap
				.addNodeFieldMap("currentAmount", double.class);
		finAccountLogConfigureMap.addNodeFieldMap("actionCode", int.class);
		finAccountLogConfigureMap.addNodeFieldMap("auditStatus", int.class);
		seConfigureMapList.add(finAccountLogConfigureMap);

		// Init configuration of Material [FinAccountAttachment] node
		ServiceEntityConfigureMap finAccountAttachmentConfigureMap = new ServiceEntityConfigureMap();
		finAccountAttachmentConfigureMap.setParentNodeName(FinAccount.NODENAME);
		finAccountAttachmentConfigureMap
				.setNodeName(FinAccountAttachment.NODENAME);
		finAccountAttachmentConfigureMap
				.setNodeType(FinAccountAttachment.class);
		finAccountAttachmentConfigureMap
				.setTableName(FinAccountAttachment.NODENAME);
		finAccountAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		finAccountAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		finAccountAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		seConfigureMapList.add(finAccountAttachmentConfigureMap);

		// Init configuration of Material [FinAccountMatItemAttachment] node
		ServiceEntityConfigureMap finAccountMatItemAttachmentConfigureMap = new ServiceEntityConfigureMap();
		finAccountMatItemAttachmentConfigureMap
				.setParentNodeName(FinAccount.NODENAME);
		finAccountMatItemAttachmentConfigureMap
				.setNodeName(FinAccountMatItemAttachment.NODENAME);
		finAccountMatItemAttachmentConfigureMap
				.setNodeType(FinAccountMatItemAttachment.class);
		finAccountMatItemAttachmentConfigureMap
				.setTableName(FinAccountMatItemAttachment.NODENAME);
		finAccountMatItemAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		finAccountMatItemAttachmentConfigureMap.addNodeFieldMap("fileType",
				String.class);
		finAccountMatItemAttachmentConfigureMap.addNodeFieldMap("content",
				byte[].class);
		seConfigureMapList.add(finAccountMatItemAttachmentConfigureMap);

		// Init configuration of FinAccount [FinAccountPrerequirement] node
		ServiceEntityConfigureMap finAccountPrerequirementConfigureMap = new ServiceEntityConfigureMap();
		finAccountPrerequirementConfigureMap.setParentNodeName("ROOT");
		finAccountPrerequirementConfigureMap
				.setNodeName("FinAccountPrerequirement");
		finAccountPrerequirementConfigureMap
				.setNodeType(FinAccountPrerequirement.class);
		finAccountPrerequirementConfigureMap
				.setTableName("FinAccountPrerequirement");
		finAccountPrerequirementConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		finAccountPrerequirementConfigureMap.addNodeFieldMap("requireType",
				int.class);
		seConfigureMapList.add(finAccountPrerequirementConfigureMap);
		// Init configuration of FinAccount [FinAccountObjectRef] node
		ServiceEntityConfigureMap finAccountObjectRefConfigureMap = new ServiceEntityConfigureMap();
		finAccountObjectRefConfigureMap.setParentNodeName("ROOT");
		finAccountObjectRefConfigureMap.setNodeName("FinAccountObjectRef");
		finAccountObjectRefConfigureMap.setNodeType(FinAccountObjectRef.class);
		finAccountObjectRefConfigureMap.setTableName("FinAccountObjectRef");
		finAccountObjectRefConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		seConfigureMapList.add(finAccountObjectRefConfigureMap);
		// Init configuration of FinAccount [FinAccDocRef] node
		ServiceEntityConfigureMap finAccDocRefConfigureMap = new ServiceEntityConfigureMap();
		finAccDocRefConfigureMap.setParentNodeName("ROOT");
		finAccDocRefConfigureMap.setNodeName("FinAccDocRef");
		finAccDocRefConfigureMap.setNodeType(FinAccDocRef.class);
		finAccDocRefConfigureMap.setTableName("FinAccDocRef");
		finAccDocRefConfigureMap
				.setFieldList(super.getBasicReferenceFieldMap());
		seConfigureMapList.add(finAccDocRefConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
