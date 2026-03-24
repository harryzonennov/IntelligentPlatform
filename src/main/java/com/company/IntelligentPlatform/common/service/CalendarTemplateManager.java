package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.CalendarTempWorkScheduleUIModel;
import com.company.IntelligentPlatform.common.dto.CalendarTemplateItemUIModel;
import com.company.IntelligentPlatform.common.dto.CalendarTemplateSearchModel;
import com.company.IntelligentPlatform.common.dto.CalendarTemplateUIModel;
// TODO-DAO: import ...CalendarTemplateDAO;
import com.company.IntelligentPlatform.common.model.CalendarTempWorkSchedule;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateConfigureProxy;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class CalendarTemplateManager extends ServiceEntityManager {

	public static final String METHOD_ConvCalendarTemplateToUI = "convCalendarTemplateToUI";

	public static final String METHOD_ConvUIToCalendarTemplate = "convUIToCalendarTemplate";

	public static final String METHOD_ConvCalendarTempWorkScheduleToUI = "convCalendarTempWorkScheduleToUI";

	public static final String METHOD_ConvUIToCalendarTempWorkSchedule = "convUIToCalendarTempWorkSchedule";

	public static final String METHOD_ConvCalendarTemplateItemToUI = "convCalendarTemplateItemToUI";

	public static final String METHOD_ConvUIToCalendarTemplateItem = "convUIToCalendarTemplateItem";

	@Autowired
	protected BsearchService bsearchService;

	// TODO-DAO: @Autowired

	// TODO-DAO: 	protected CalendarTemplateDAO calendarTemplateDAO;

	@Autowired
	protected CalendarTemplateConfigureProxy calendarTemplateConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected CalendarTemplateSearchProxy calendarTemplateSearchProxy;

	private Map<Integer, String> periodTypeMap;

	private Map<Integer, String> vocationTypeMap;

	private Map<Integer, String> dayStatusMap;

	private Map<Integer, String> statusMap;

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException 
	 */
	public void convCalendarTemplateToUI(CalendarTemplate calendarTemplate,
			CalendarTemplateUIModel calendarTemplateUIModel) throws ServiceEntityInstallationException {
		if (calendarTemplate != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(calendarTemplate, calendarTemplateUIModel);
			calendarTemplateUIModel.setYear(calendarTemplate.getYear());
			calendarTemplateUIModel.setStatus(calendarTemplate.getStatus());
			this.initStatusMap();
			calendarTemplateUIModel.setStatusValue(this.statusMap.get(calendarTemplate.getStatus()));
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:calendarTemplate
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCalendarTemplate(
			CalendarTemplateUIModel calendarTemplateUIModel,
			CalendarTemplate rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(calendarTemplateUIModel, rawEntity);
		rawEntity.setYear(calendarTemplateUIModel.getYear());
		if (calendarTemplateUIModel.getStatus() != 0) {
			rawEntity.setStatus(calendarTemplateUIModel.getStatus());
		}
	}

	public List<ServiceEntityNode> searchInternal(
			CalendarTemplateSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[calendarTemplate]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(CalendarTemplate.SENAME);
		searchNodeConfig0.setNodeName(CalendarTemplate.NODENAME);
		searchNodeConfig0.setNodeInstID(CalendarTemplate.SENAME);
		searchNodeConfig0.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[calendarTempWorkSchedule]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(CalendarTempWorkSchedule.SENAME);
		searchNodeConfig2.setNodeName(CalendarTempWorkSchedule.NODENAME);
		searchNodeConfig2.setNodeInstID(CalendarTempWorkSchedule.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(CalendarTemplate.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[calendarTemplateItem]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(CalendarTemplateItem.SENAME);
		searchNodeConfig4.setNodeName(CalendarTemplateItem.NODENAME);
		searchNodeConfig4.setNodeInstID(CalendarTemplateItem.NODENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig4.setBaseNodeInstID(CalendarTemplate.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		// TODO-DAO: super.setServiceEntityDAO(calendarTemplateDAO);
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(calendarTemplateConfigureProxy);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCalendarTempWorkScheduleToUI(
			CalendarTempWorkSchedule calendarTempWorkSchedule,
			CalendarTempWorkScheduleUIModel calendarTempWorkScheduleUIModel) {
		if (calendarTempWorkSchedule != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(calendarTempWorkSchedule, calendarTempWorkScheduleUIModel);
			calendarTempWorkScheduleUIModel.setEndTime(calendarTempWorkSchedule
					.getEndTime());
			calendarTempWorkScheduleUIModel
					.setStartTime(calendarTempWorkSchedule.getStartTime());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:calendarTempWorkSchedule
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCalendarTempWorkSchedule(
			CalendarTempWorkScheduleUIModel calendarTempWorkScheduleUIModel,
			CalendarTempWorkSchedule rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(calendarTempWorkScheduleUIModel, rawEntity);
		rawEntity.setEndTime(calendarTempWorkScheduleUIModel.getEndTime());
		rawEntity.setStartTime(calendarTempWorkScheduleUIModel.getStartTime());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException 
	 */
	public void convCalendarTemplateItemToUI(
			CalendarTemplateItem calendarTemplateItem,
			CalendarTemplateItemUIModel calendarTemplateItemUIModel) throws ServiceEntityInstallationException {
		if (calendarTemplateItem != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(calendarTemplateItem, calendarTemplateItemUIModel);
			calendarTemplateItemUIModel.setStartMonth(calendarTemplateItem
					.getStartMonth());
			calendarTemplateItemUIModel.setLastDays(calendarTemplateItem
					.getLastDays());
			this.initPeriodTypeMap();
			calendarTemplateItemUIModel.setPeriodType(calendarTemplateItem
					.getPeriodType());
			calendarTemplateItemUIModel.setPeriodTypeValue(this.periodTypeMap.get(calendarTemplateItem
					.getPeriodType()));
			calendarTemplateItemUIModel.setVocationType(calendarTemplateItem
					.getVocationType());
			this.initVocationTypeMap();
			calendarTemplateItemUIModel.setVocationTypeValue(this.vocationTypeMap.get(calendarTemplateItem
					.getVocationType()));
			calendarTemplateItemUIModel.setStartDay(calendarTemplateItem
					.getStartDay());
			this.initDayStatusMap();
			calendarTemplateItemUIModel.setDayStatus(calendarTemplateItem
					.getDayStatus());
			calendarTemplateItemUIModel.setDayStatusValue(this.dayStatusMap.get(calendarTemplateItem
					.getDayStatus()));
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:calendarTemplateItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToCalendarTemplateItem(
			CalendarTemplateItemUIModel calendarTemplateItemUIModel,
			CalendarTemplateItem rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(calendarTemplateItemUIModel, rawEntity);
		rawEntity.setStartMonth(calendarTemplateItemUIModel.getStartMonth());
		rawEntity.setLastDays(calendarTemplateItemUIModel.getLastDays());
		rawEntity.setPeriodType(calendarTemplateItemUIModel.getPeriodType());
		rawEntity
				.setVocationType(calendarTemplateItemUIModel.getVocationType());
		rawEntity.setStartDay(calendarTemplateItemUIModel.getStartDay());
		rawEntity.setDayStatus(calendarTemplateItemUIModel.getDayStatus());
	}

	public Map<Integer, String> initPeriodTypeMap()
			throws ServiceEntityInstallationException {
		if (this.periodTypeMap == null) {
			this.periodTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					CalendarTemplateItemUIModel.class, "periodType");
		}
		return this.periodTypeMap;
	}

	public Map<Integer, String> initVocationTypeMap()
			throws ServiceEntityInstallationException {
		if (this.vocationTypeMap == null) {
			this.vocationTypeMap = serviceDropdownListHelper.getUIDropDownMap(
					CalendarTemplateItemUIModel.class, "vocationType");
		}
		return this.vocationTypeMap;
	}

	public Map<Integer, String> initDayStatusMap()
			throws ServiceEntityInstallationException {
		if (this.dayStatusMap == null) {
			this.dayStatusMap = serviceDropdownListHelper.getUIDropDownMap(
					CalendarTemplateItemUIModel.class, "dayStatus");
		}
		return this.dayStatusMap;
	}
	
	public Map<Integer, String> initStatusMap()
			throws ServiceEntityInstallationException {
		if (this.statusMap == null) {
			this.statusMap = serviceDropdownListHelper.getUIDropDownMap(
					CalendarTemplateUIModel.class, "status");
		}
		return this.statusMap;
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return calendarTemplateSearchProxy;
	}
}
