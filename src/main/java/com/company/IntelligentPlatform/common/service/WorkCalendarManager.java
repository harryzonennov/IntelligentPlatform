package com.company.IntelligentPlatform.common.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.WorkCalendarDayItemUIModel;
import com.company.IntelligentPlatform.common.dto.WorkCalendarSearchModel;
import com.company.IntelligentPlatform.common.dto.WorkCalendarUIModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.WorkCalendarRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.model.CalendarTemplate;
import com.company.IntelligentPlatform.common.model.CalendarTemplateItem;
import com.company.IntelligentPlatform.common.model.WorkCalendar;
import com.company.IntelligentPlatform.common.model.WorkCalendarConfigureProxy;
import com.company.IntelligentPlatform.common.model.WorkCalendarDayItem;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSystemDefaultProxy;
import com.company.IntelligentPlatform.common.service.BSearchNodeComConfigure;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchNodeMapping;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class WorkCalendarManager extends ServiceEntityManager {

	public static final String METHOD_ConvWorkCalendarDayItemToUI = "convWorkCalendarDayItemToUI";

	public static final String METHOD_ConvUIToWorkCalendarDayItem = "convUIToWorkCalendarDayItem";

	public static final String METHOD_ConvWorkCalendarToUI = "convWorkCalendarToUI";

	public static final String METHOD_ConvUIToWorkCalendar = "convUIToWorkCalendar";

	public static final String METHOD_ConvCalendarTemplateToUI = "convCalendarTemplateToUI";

	private Map<Integer, String> vocationTypeMap;

	private Map<Integer, String> dayStatusMap;
	
	private Map<Integer, String> statusMap;

	@Autowired
	protected BsearchService bsearchService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected WorkCalendarRepository workCalendarDAO;
	@Autowired
	protected CalendarTemplateManager calendarTemplateManager;

	@Autowired
	protected WorkCalendarConfigureProxy workCalendarConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected StandardSystemDefaultProxy standardSystemDefaultProxy;

	public List<ServiceEntityNode> initCalendarDayItemFromTemplateItem(WorkCalendar workCalendar, 
			List<ServiceEntityNode> rawTemplateItemList) throws ServiceEntityConfigureException {
		Map<CalendarTemplateItem, List<ServiceEntityNode>> calendarGenMap = new HashMap<CalendarTemplateItem, List<ServiceEntityNode>>();
		for (ServiceEntityNode seNode : rawTemplateItemList) {
			CalendarTemplateItem calendarTemplateItem = (CalendarTemplateItem) seNode;
			List<ServiceEntityNode> tempResult;
			if (calendarTemplateItem.getPeriodType() == CalendarTemplateItem.PERIODTYPE_WEEK) {
				tempResult = generateCalendarItemByWeekTemplate(workCalendar, calendarTemplateItem);
				if(!ServiceCollectionsHelper.checkNullList(tempResult)){
					calendarGenMap.put(calendarTemplateItem, tempResult);
				}
			}
            if (calendarTemplateItem.getPeriodType() == CalendarTemplateItem.PERIODTYPE_MONTH) {
				tempResult = generateCalendarItemByMonthTemplate(workCalendar, calendarTemplateItem);	
				if(!ServiceCollectionsHelper.checkNullList(tempResult)){
					calendarGenMap.put(calendarTemplateItem, tempResult);
				}			
			}
            if (calendarTemplateItem.getPeriodType() == CalendarTemplateItem.PERIODTYPE_QUARTER) {
				tempResult = generateCalendarItemByQuarterTemplate(workCalendar, calendarTemplateItem);	
				if(!ServiceCollectionsHelper.checkNullList(tempResult)){
					calendarGenMap.put(calendarTemplateItem, tempResult);
				}
			}
            if (calendarTemplateItem.getPeriodType() == CalendarTemplateItem.PERIODTYPE_YEAR) {
				tempResult = generateCalendarItemByYearTemplate(workCalendar, calendarTemplateItem);
				if(!ServiceCollectionsHelper.checkNullList(tempResult)){
					calendarGenMap.put(calendarTemplateItem, tempResult);
				}				
			}			
		}
		// merge result
		Set<CalendarTemplateItem> keySet = calendarGenMap.keySet();
		Iterator<CalendarTemplateItem> it = keySet.iterator();
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<CalendarTemplateItem> keyList = new ArrayList<>();
		while (it.hasNext()) {
			CalendarTemplateItem calendarTemplateItem = it.next();
			keyList.add(calendarTemplateItem);
		}
		// sort template by period type
		Collections.sort(keyList, new CalendarTempItemPeriodTypeCompare());
		// merge result
		for(CalendarTemplateItem calendarTemplateItem: keyList){
			List<ServiceEntityNode> tempResultList = calendarGenMap.get(calendarTemplateItem);
			resultList = mergeToResultByCheckDate(tempResultList, resultList);
		}
		return resultList;
	}
	
	private List<ServiceEntityNode> mergeToResultByCheckDate(List<ServiceEntityNode> tempResult, List<ServiceEntityNode> resultList){
		if(ServiceCollectionsHelper.checkNullList(tempResult)){
			return resultList;
		}
		if(resultList.size() == 0){
			return tempResult;
		}
		for(ServiceEntityNode seNode:tempResult){
			WorkCalendarDayItem workCalendarDayItem = (WorkCalendarDayItem) seNode;
			WorkCalendarDayItem extCalendarDayItem = filterWorkCalendarDayItem(resultList,workCalendarDayItem.getRefDate());
			if(extCalendarDayItem != null){
				// just skip
				continue;
			}else{
				resultList.add(workCalendarDayItem);
			}
		}
		return resultList;
	}
	
	private WorkCalendarDayItem filterWorkCalendarDayItem(List<ServiceEntityNode> resultList, Date targetDate){
		if(ServiceCollectionsHelper.checkNullList(resultList)){
			return null;
		}
		if(targetDate == null){
			return null;
		}
		for(ServiceEntityNode seNode:resultList){
			WorkCalendarDayItem workCalendarDayItem = (WorkCalendarDayItem) seNode;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(targetDate);
		    int targetDay = calendar.get(Calendar.DATE);
		    int targetMonth = calendar.get(Calendar.MONTH);
		    calendar.setTime(workCalendarDayItem.getRefDate());
		    int day = calendar.get(Calendar.DATE);
		    int month = calendar.get(Calendar.MONTH);
		    if(targetDay == day && targetMonth == month){
		    	return workCalendarDayItem;
		    }
		}
		return null;
	}

	
	public static Calendar getCalendar() {
		Calendar calendar = Calendar.getInstance();
		// for Chinese: set first day of week.
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar;
	}

	/**
	 * Core Logic to generate Work calendar list by [WEEK] type calendar
	 * template
	 * 
	 * @param workCalendar
	 * @param calendarTemplateItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> generateCalendarItemByWeekTemplate(
			WorkCalendar workCalendar, CalendarTemplateItem calendarTemplateItem)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (int i = 0; i < 53; i++) {
			Calendar calendar = getCalendar();
			calendar.set(Calendar.WEEK_OF_YEAR, i + 1);
			// TODO, Convert Western style into Chinese calendar style
			calendar.set(Calendar.DAY_OF_WEEK,
					calendarTemplateItem.getStartDay() + 1);
			for (int j = 0; j <= 7; j++) {
				if (j >= calendarTemplateItem.getLastDays()) {
					break;
				}
				WorkCalendarDayItem workCalendarDayItem = (WorkCalendarDayItem) newEntityNode(
						workCalendar, WorkCalendarDayItem.NODENAME);
				workCalendarDayItem.setRefDate(calendar.getTime());
				workCalendarDayItem.setDayStatus(calendarTemplateItem
						.getDayStatus());
				workCalendarDayItem.setVocationType(calendarTemplateItem
						.getVocationType());
				resultList.add(workCalendarDayItem);
				// Add one more day
				calendar.add(Calendar.DATE, 1);
			}
		}
		return resultList;
	}

	/**
	 * Core Logic to generate Work calendar list by [MONTH] type calendar
	 * template
	 * 
	 * @param workCalendar
	 * @param calendarTemplateItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> generateCalendarItemByMonthTemplate(
			WorkCalendar workCalendar, CalendarTemplateItem calendarTemplateItem)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			Calendar calendar = getCalendar();
			calendar.set(Calendar.MONTH, i);			
			setStartDayToCalendar(calendar, calendarTemplateItem.getStartDay());
			List<ServiceEntityNode> tmpResultList = genWorkCalendarInOneMonth(calendar, workCalendar, calendarTemplateItem);
			if(ServiceCollectionsHelper.checkNullList(tmpResultList)){
				resultList.addAll(tmpResultList);
			}
		}
		return resultList;
	}

	/**
	 * Core Logic to generate Work calendar list by [QUARTER] type calendar
	 * template
	 * 
	 * @param workCalendar
	 * @param calendarTemplateItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> generateCalendarItemByQuarterTemplate(
			WorkCalendar workCalendar, CalendarTemplateItem calendarTemplateItem)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			Calendar calendar = getCalendar();
			if (calendarTemplateItem.getStartMonth() > 0) {
				calendar.set(Calendar.MONTH,
						3 * i + calendarTemplateItem.getStartMonth());
			}
			if (calendarTemplateItem.getStartMonth() == -1) {
				// in case the last month
				calendar.set(Calendar.MONTH, 3 * i + 3);
			}
			if (calendarTemplateItem.getStartMonth() == 0) {
				// in case the every month
				calendar.set(Calendar.MONTH,
						3 * i);
				for (int t = 1; t < 3; t++) {
					setStartDayToCalendar(calendar, calendarTemplateItem.getStartDay());
					List<ServiceEntityNode> tmpResultList = genWorkCalendarInOneMonth(calendar, workCalendar, calendarTemplateItem);
					if(!ServiceCollectionsHelper.checkNullList(tmpResultList)){
						resultList.addAll(tmpResultList);
					}
					// Add one more month
					calendar.add(Calendar.MONTH, 1);
				}
				return resultList;
			}
			setStartDayToCalendar(calendar, calendarTemplateItem.getStartDay());
			List<ServiceEntityNode> tmpResultList = genWorkCalendarInOneMonth(calendar, workCalendar, calendarTemplateItem);
			if(!ServiceCollectionsHelper.checkNullList(tmpResultList)){
				resultList.addAll(tmpResultList);
			}
		}
		return resultList;
	}

	private List<ServiceEntityNode> genWorkCalendarInOneMonth(
			Calendar calendar, WorkCalendar workCalendar,
			CalendarTemplateItem calendarTemplateItem) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for (int j = 0; j <= 31; j++) {
			if (j >= calendarTemplateItem.getLastDays()) {
				break;
			}
			WorkCalendarDayItem workCalendarDayItem = (WorkCalendarDayItem) newEntityNode(
					workCalendar, WorkCalendarDayItem.NODENAME);
			workCalendarDayItem.setRefDate(calendar.getTime());
			workCalendarDayItem.setDayStatus(calendarTemplateItem
					.getDayStatus());
			workCalendarDayItem.setVocationType(calendarTemplateItem
					.getVocationType());
			resultList.add(workCalendarDayItem);
			// Add one more day
			calendar.add(Calendar.DATE, 1);
		}
		return resultList;
	}
	
	private void setStartDayToCalendar(Calendar calendar, int startDay){
		if(startDay != -1){
			calendar.set(Calendar.DAY_OF_MONTH,
					startDay);
		}else{
			// in case last day of this month
			calendar.set(Calendar.DAY_OF_MONTH,
					startDay);               
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
	}

	/**
	 * Core Logic to generate Work calendar list by [YEAR] type calendar
	 * template
	 * 
	 * @param workCalendar
	 * @param calendarTemplateItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	protected List<ServiceEntityNode> generateCalendarItemByYearTemplate(
			WorkCalendar workCalendar, CalendarTemplateItem calendarTemplateItem)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		Calendar calendar = getCalendar();
		if (calendarTemplateItem.getStartMonth() > 0) {
			calendar.set(Calendar.MONTH, calendarTemplateItem.getStartMonth() - 1);
		}
		if (calendarTemplateItem.getStartMonth() == -1) {
			calendar.set(Calendar.MONTH, 12);
		}
		if (calendarTemplateItem.getStartMonth() == 0) {
			// in case the every month
			calendar.set(Calendar.MONTH, 1);
			for (int t = 1; t <= 12; t++) {
				calendar.set(Calendar.MONTH, 12);
				setStartDayToCalendar(calendar, calendarTemplateItem.getStartDay());
				List<ServiceEntityNode> tmpResultList = genWorkCalendarInOneMonth(calendar, workCalendar, calendarTemplateItem);
				if(!ServiceCollectionsHelper.checkNullList(tmpResultList)){
					resultList.addAll(tmpResultList);
				}
				// Add one more month
				calendar.add(Calendar.MONTH, 1);
			}
			return resultList;
		}
		setStartDayToCalendar(calendar, calendarTemplateItem.getStartDay());
		List<ServiceEntityNode> tmpResultList = genWorkCalendarInOneMonth(calendar, workCalendar, calendarTemplateItem);
		if(!ServiceCollectionsHelper.checkNullList(tmpResultList)){
			resultList.addAll(tmpResultList);
		}
		return resultList;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convWorkCalendarDayItemToUI(
			WorkCalendarDayItem workCalendarDayItem,
			WorkCalendarDayItemUIModel workCalendarDayItemUIModel)
			throws ServiceEntityInstallationException {
		if (workCalendarDayItem != null) {
			if (!ServiceEntityStringHelper.checkNullString(workCalendarDayItem
					.getUuid())) {
				workCalendarDayItemUIModel.setUuid(workCalendarDayItem
						.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendarDayItem
					.getParentNodeUUID())) {
				workCalendarDayItemUIModel
						.setParentNodeUUID(workCalendarDayItem
								.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendarDayItem
					.getRootNodeUUID())) {
				workCalendarDayItemUIModel.setRootNodeUUID(workCalendarDayItem
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendarDayItem
					.getClient())) {
				workCalendarDayItemUIModel.setClient(workCalendarDayItem
						.getClient());
			}
			workCalendarDayItemUIModel.setVocationType(workCalendarDayItem
					.getVocationType());
			if (workCalendarDayItem.getRefDate() != null) {
				workCalendarDayItemUIModel
						.setRefDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(workCalendarDayItem.getRefDate()));
			}
			initVocationTypeMap();
			workCalendarDayItemUIModel
					.setVocationTypeValue(this.vocationTypeMap
							.get(workCalendarDayItem.getVocationType()));
			initDayStatusMap();
			workCalendarDayItemUIModel.setDayStatus(workCalendarDayItem
					.getDayStatus());
			workCalendarDayItemUIModel.setDayStatusValue(this.dayStatusMap
					.get(workCalendarDayItem.getDayStatus()));
			workCalendarDayItemUIModel.setName(workCalendarDayItem.getName());
			workCalendarDayItemUIModel.setId(workCalendarDayItem.getId());
		}

	}

	/**
	 * [Internal method] Convert from UI model to se model:workCalendarDayItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToWorkCalendarDayItem(
			WorkCalendarDayItemUIModel workCalendarDayItemUIModel,
			WorkCalendarDayItem rawEntity) {
		if (!ServiceEntityStringHelper
				.checkNullString(workCalendarDayItemUIModel.getUuid())) {
			rawEntity.setUuid(workCalendarDayItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(workCalendarDayItemUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(workCalendarDayItemUIModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(workCalendarDayItemUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(workCalendarDayItemUIModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(workCalendarDayItemUIModel.getClient())) {
			rawEntity.setClient(workCalendarDayItemUIModel.getClient());
		}
		rawEntity.setVocationType(workCalendarDayItemUIModel.getVocationType());
		if (!ServiceEntityStringHelper
				.checkNullString(workCalendarDayItemUIModel.getRefDate())) {
			try {
				rawEntity.setRefDate(DefaultDateFormatConstant.DATE_FORMAT
						.parse(workCalendarDayItemUIModel.getRefDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setDayStatus(workCalendarDayItemUIModel.getDayStatus());
		rawEntity.setUuid(workCalendarDayItemUIModel.getUuid());
		rawEntity.setName(workCalendarDayItemUIModel.getName());
		rawEntity.setClient(workCalendarDayItemUIModel.getClient());
		rawEntity.setId(workCalendarDayItemUIModel.getId());
		rawEntity.setNote(workCalendarDayItemUIModel.getNote());
	}

	public Map<Integer, String> initVocationTypeMap()
			throws ServiceEntityInstallationException {
		if (this.vocationTypeMap == null) {
			this.vocationTypeMap = calendarTemplateManager.initVocationTypeMap();
		}
		return this.vocationTypeMap;
		
	}

	public Map<Integer, String> initDayStatusMap()
			throws ServiceEntityInstallationException {
		if (this.dayStatusMap == null) {
			this.dayStatusMap = calendarTemplateManager.initDayStatusMap();
		}
		return this.dayStatusMap;
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convWorkCalendarToUI(WorkCalendar workCalendar,
			WorkCalendarUIModel workCalendarUIModel)
			throws ServiceEntityInstallationException {
		if (workCalendar != null) {
			if (!ServiceEntityStringHelper.checkNullString(workCalendar
					.getUuid())) {
				workCalendarUIModel.setUuid(workCalendar.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendar
					.getParentNodeUUID())) {
				workCalendarUIModel.setParentNodeUUID(workCalendar
						.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendar
					.getRootNodeUUID())) {
				workCalendarUIModel.setRootNodeUUID(workCalendar
						.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(workCalendar
					.getClient())) {
				workCalendarUIModel.setClient(workCalendar.getClient());
			}
			workCalendarUIModel.setId(workCalendar.getId());
			workCalendarUIModel.setDefaultFlag(workCalendar.getDefaultFlag());
			Map<Integer, String> defaultFlagMap = initDefaultFlagMap();
			workCalendarUIModel.setDefaultFlagValue(defaultFlagMap
					.get(workCalendar.getDefaultFlag()));
			workCalendarUIModel.setStatus(workCalendar.getStatus());
			this.initStatusMap();
			workCalendarUIModel.setStatusValue(this.statusMap.get(workCalendar.getStatus()));
			workCalendarUIModel.setYear(workCalendar.getYear());
			workCalendarUIModel.setNote(workCalendar.getNote());
			workCalendarUIModel.setName(workCalendar.getName());
			workCalendarUIModel.setRefTemplateUUID(workCalendar
					.getRefTemplateUUID());
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:workCalendar
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToWorkCalendar(WorkCalendarUIModel workCalendarUIModel,
			WorkCalendar rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(workCalendarUIModel
				.getUuid())) {
			rawEntity.setUuid(workCalendarUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(workCalendarUIModel
				.getParentNodeUUID())) {
			rawEntity
					.setParentNodeUUID(workCalendarUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(workCalendarUIModel
				.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(workCalendarUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(workCalendarUIModel
				.getClient())) {
			rawEntity.setClient(workCalendarUIModel.getClient());
		}
		rawEntity.setId(workCalendarUIModel.getId());
		rawEntity.setDefaultFlag(workCalendarUIModel.getDefaultFlag());
		rawEntity.setYear(workCalendarUIModel.getYear());
		if (workCalendarUIModel.getStatus() != 0) {
			rawEntity.setStatus(workCalendarUIModel.getStatus());
		}
		rawEntity.setNote(workCalendarUIModel.getNote());
		rawEntity.setName(workCalendarUIModel.getName());
		rawEntity.setRefTemplateUUID(workCalendarUIModel.getRefTemplateUUID());
		rawEntity.setUuid(workCalendarUIModel.getUuid());
		rawEntity.setClient(workCalendarUIModel.getClient());
	}

	public Map<Integer, String> initDefaultFlagMap()
			throws ServiceEntityInstallationException {
		return standardSystemDefaultProxy.getSystemDefaultMap();
	}
	
	public Map<Integer, String> initStatusMap()
			throws ServiceEntityInstallationException {
		if (this.statusMap == null) {
			this.statusMap = serviceDropdownListHelper.getUIDropDownMap(
					WorkCalendarUIModel.class, "status");
		}
		return this.statusMap;
	}

	public List<ServiceEntityNode> searchInternal(
			WorkCalendarSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[workCalendarDayItem]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(WorkCalendarDayItem.SENAME);
		searchNodeConfig0.setNodeName(WorkCalendarDayItem.NODENAME);
		searchNodeConfig0.setNodeInstID(WorkCalendarDayItem.NODENAME);
		searchNodeConfig0.setStartNodeFlag(false);
		searchNodeConfig0
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig0.setBaseNodeInstID(WorkCalendar.SENAME);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[workCalendar]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(WorkCalendar.SENAME);
		searchNodeConfig2.setNodeName(WorkCalendar.NODENAME);
		searchNodeConfig2.setNodeInstID(WorkCalendar.SENAME);
		searchNodeConfig2.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig2);
		// Search node:[calendarTemplate]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(CalendarTemplate.SENAME);
		searchNodeConfig4.setNodeName(CalendarTemplate.NODENAME);
		searchNodeConfig4.setNodeInstID(CalendarTemplate.SENAME);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig4.setMapBaseFieldName("null");
		searchNodeConfig4.setMapSourceFieldName("null");
		searchNodeConfig4.setBaseNodeInstID(WorkCalendar.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, workCalendarDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(workCalendarConfigureProxy);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCalendarTemplateToUI(CalendarTemplate calendarTemplate,
			WorkCalendarUIModel workCalendarUIModel) {
		if (calendarTemplate != null) {			
			workCalendarUIModel.setTemplateName(calendarTemplate
					.getName());
			workCalendarUIModel.setTemplateId(calendarTemplate.getId());
		}
	}

}
