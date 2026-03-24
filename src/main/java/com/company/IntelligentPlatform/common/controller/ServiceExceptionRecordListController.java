package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.ServiceExceptionRecordSearchModel;
import com.company.IntelligentPlatform.common.dto.ServiceExceptionRecordUIModel;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceExceptionRecordManager;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigPreCondition;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.LogonUser;

@Scope("session")
@Controller(value = "serviceExceptionRecordListController")
@RequestMapping(value = "/serviceExceptionRecord")
public class ServiceExceptionRecordListController extends SEListController {

	@Autowired
	protected ServiceExceptionRecordManager serviceExceptionRecordManager;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	LogonUserManager logonUserManager;

	@Autowired
	BsearchService bsearchService;
	
	@Autowired
	protected LogonActionController logonActionController;


	protected List<ServiceEntityNode> searchInternal(
			ServiceExceptionRecordSearchModel searchModel)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Sample code below !!
		// start node:[root]

		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(ServiceExceptionRecord.SENAME);
		searchNodeConfig0.setNodeName(ServiceExceptionRecord.NODENAME);
		searchNodeConfig0.setNodeInstID(ServiceExceptionRecord.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		if (searchModel.getStatus() == 0) {
			// In case no target status, then ignore the confirmed, wasted, and
			// ignored exception record
			List<SearchConfigPreCondition> preConditions = new ArrayList<SearchConfigPreCondition>();
			SearchConfigPreCondition preCondition1 = new SearchConfigPreCondition();
			preCondition1.setFieldName("status");
			preCondition1.setOperator(SEFieldSearchConfig.OPERATOR_NOT_EQ);
			preCondition1
					.setFieldValue(ServiceExceptionRecord.STATUS_COMPLETED);
			preConditions.add(preCondition1);
			SearchConfigPreCondition preCondition2 = new SearchConfigPreCondition();
			preCondition2.setFieldName("status");
			preCondition2.setOperator(SEFieldSearchConfig.OPERATOR_NOT_EQ);
			preCondition2
					.setFieldValue(ServiceExceptionRecord.STATUS_CONFIRMED);
			preConditions.add(preCondition2);
			SearchConfigPreCondition preCondition3 = new SearchConfigPreCondition();
			preCondition3.setFieldName("status");
			preCondition3.setOperator(SEFieldSearchConfig.OPERATOR_NOT_EQ);
			preCondition3.setFieldValue(ServiceExceptionRecord.STATUS_IGNORED);
			preConditions.add(preCondition3);
			SearchConfigPreCondition preCondition4 = new SearchConfigPreCondition();
			preCondition4.setFieldName("status");
			preCondition4.setOperator(SEFieldSearchConfig.OPERATOR_NOT_EQ);
			preCondition4.setFieldValue(ServiceExceptionRecord.STATUS_WASTED);
			preConditions.add(preCondition4);
			searchNodeConfig0.setPreConditions(preConditions);
		}
		searchNodeConfigList.add(searchNodeConfig0);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, null, true);
		return resultList;
	}

	protected void convServiceExceptionRecordToUI(
			ServiceExceptionRecord serviceExceptionRecord,
			ServiceExceptionRecordUIModel serviceExceptionRecordUIModel)
			throws ServiceEntityInstallationException {
		serviceExceptionRecordUIModel.setUuid(serviceExceptionRecord.getUuid());
		serviceExceptionRecordUIModel.setId(serviceExceptionRecord.getId());
		serviceExceptionRecordUIModel.setName(serviceExceptionRecord.getName());
		serviceExceptionRecordUIModel.setNote(serviceExceptionRecord.getNote());
		if (serviceExceptionRecord.getCreatedTime() != null
				&& !serviceExceptionRecord
						.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			serviceExceptionRecordUIModel
					.setCreatedTimeValue(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(java.util.Date.from(serviceExceptionRecord.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant())));
		}
		if (serviceExceptionRecord.getCreatedTime() != null) {
			serviceExceptionRecordUIModel.setCreatedTime(
				java.util.Date.from(serviceExceptionRecord.getCreatedTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		if (serviceExceptionRecord.getLastUpdateTime() != null
				&& !serviceExceptionRecord
						.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			serviceExceptionRecordUIModel
					.setLastUpdateTimeValue(DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(java.util.Date.from(serviceExceptionRecord.getLastUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant())));
		}
		if (serviceExceptionRecord.getLastUpdateTime() != null) {
			serviceExceptionRecordUIModel.setLastUpdateTime(
				java.util.Date.from(serviceExceptionRecord.getLastUpdateTime().atZone(java.time.ZoneId.systemDefault()).toInstant()));
		}
		serviceExceptionRecordUIModel.setCallStack(serviceExceptionRecord
				.getCallStack());
		serviceExceptionRecordUIModel.setSource(serviceExceptionRecord
				.getSource());
		serviceExceptionRecordUIModel.setProcessorUUID(serviceExceptionRecord
				.getProcessorUUID());
		serviceExceptionRecordUIModel.setReporterUUID(serviceExceptionRecord
				.getReporterUUID());
		serviceExceptionRecordUIModel.setProcessorName(serviceExceptionRecord
				.getProcessorName());
		serviceExceptionRecordUIModel.setReporterName(serviceExceptionRecord
				.getReporterName());
		Map<Integer, String> categoryMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceExceptionRecordUIModel.class,
						"category");
		serviceExceptionRecordUIModel.setCategoryValue(categoryMap
				.get(serviceExceptionRecord.getCategory()));
		serviceExceptionRecordUIModel.setCategory(serviceExceptionRecord
				.getCategory());
		Map<Integer, String> priorityMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceExceptionRecordUIModel.class,
						"priority");
		serviceExceptionRecordUIModel.setPriorityValue(priorityMap
				.get(serviceExceptionRecord.getPriority()));
		serviceExceptionRecordUIModel.setPriority(serviceExceptionRecord
				.getPriority());
		Map<Integer, String> statusMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceExceptionRecordUIModel.class, "status");
		serviceExceptionRecordUIModel.setStatusValue(statusMap
				.get(serviceExceptionRecord.getStatus()));
		serviceExceptionRecordUIModel.setStatus(serviceExceptionRecord
				.getStatus());
		Map<Integer, String> sourceTypeMap = serviceDropdownListHelper
				.getUIDropDownMap(ServiceExceptionRecordUIModel.class,
						"sourceType");
		serviceExceptionRecordUIModel.setSourceTypeValue(sourceTypeMap
				.get(serviceExceptionRecord.getSourceType()));
		serviceExceptionRecordUIModel.setSourceType(serviceExceptionRecord
				.getSourceType());
	}

	protected void convReporterProcessorToUI(LogonUser reporter,
			LogonUser processor,
			ServiceExceptionRecordUIModel serviceExceptionRecordUIModel) {
		if (reporter != null) {
			serviceExceptionRecordUIModel.setReporterName(reporter.getName());
		}
		if (processor != null) {
			serviceExceptionRecordUIModel.setReporterName(processor.getName());
		}
	}
}
