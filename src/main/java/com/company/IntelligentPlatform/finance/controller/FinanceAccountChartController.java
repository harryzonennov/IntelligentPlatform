package com.company.IntelligentPlatform.finance.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.model.GMainBusinessExpense;
import com.company.IntelligentPlatform.finance.model.GMainBusinessIncome;
import com.company.IntelligentPlatform.finance.service.FinAccountChartException;
import com.company.IntelligentPlatform.finance.service.FinAccountException;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleException;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;
import com.company.IntelligentPlatform.finance.model.FinComChartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.INavigationElementConstants;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.ServiceCalendarHelper;
import com.company.IntelligentPlatform.common.service.ServiceChartDataSeries;
import com.company.IntelligentPlatform.common.service.ServiceChartHelper;
import com.company.IntelligentPlatform.common.service.ServiceChartTimeSlot;
import com.company.IntelligentPlatform.common.service.ServiceComChartModel;
import com.company.IntelligentPlatform.common.service.ServiceComChartUIModel;
import com.company.IntelligentPlatform.common.service.BsearchService;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IMobileErrorCodeConstant;
import com.company.IntelligentPlatform.common.model.IMobileJSONConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "financeAccountChartController")
@RequestMapping(value = "/finAccount")
public class FinanceAccountChartController extends SEListController {

	protected final static String UNKNOWN_SYS_ERROR_JSON = "{\""
			+ IMobileJSONConstant.ELE_ERROR_CODE + "\":\""
			+ IMobileErrorCodeConstant.UNKNOWN_SYS_ERROR + "\"}";

	protected final static String PARSE_JSON_ERROR = "{\""
			+ IMobileJSONConstant.ELE_ERROR_CODE + "\":\""
			+ IMobileErrorCodeConstant.JSON_PARSE_ERROR + "\"}";

	public final static String LABEL_TAB = "tab";

	public final static int TAB_BASIC = 1;

	public final static int TAB_TRANSPROFIT = 2;

	public final static int TAB_TRANSCOST = 3;

	public final static int MAX_TIMESLOT_NUM = 15;

	@Autowired
	FinAccountManager finAccountManager;

	@Autowired
	FinAccountTitleManager finAccountTitleManager;

	@Autowired
	LogonUserManager logonUserManager;

	@Autowired
	BsearchService bsearchService;

	@Autowired
	ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	LogonActionController logonActionController;

	@Autowired
	FinanceAccountMessageHelper financeAccountMessageHelper;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected ServiceChartHelper serviceChartHelper;
	
	@Autowired
	protected ServiceCalendarHelper serviceCalendarHelper;
	
	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	public static final String AOID_RESOURCE = FinanceAccountEditorController.AOID_RESOURCE;


	@RequestMapping(value = "/preGenReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String preGenReport(@RequestBody FinComChartRequest request) {
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			// [Step1] check the account object list
			String startDateString = request.getStartDate();
			Date startDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(startDateString);
			String endDateString = request.getEndDate();
			Date endDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(endDateString);
			String accObjectUUID1 = request.getAccObjectUUID1();
			String accObjectUUID2 = request.getAccObjectUUID2();
			String accObjectUUID3 = request.getAccObjectUUID3();
			String accObjectUUID4 = request.getAccObjectUUID4();
			int unit = request.getUnit();
			List<String> accUUIDList = mergeAccObjectUUID(accObjectUUID1,
					accObjectUUID2, accObjectUUID3, accObjectUUID4);
			if (accUUIDList.size() == 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_NO_ACCOBJECT);
			}
			// [Step2] check the time slot
			List<ServiceChartTimeSlot> tsList = ServiceChartHelper
					.genChartTimeSlots(unit, startDate, endDate);
			if (tsList == null || tsList.size() <= 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_WRONG_TIME_SET);
			}
			if (tsList.size() > MAX_TIMESLOT_NUM) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_TOOLONG_TS);
			}
			return ServiceJSONParser.genSimpleOKResponse();
		} catch (FinAccountChartException e) {
			return genChartExceptionErrorResponse(e);
		} catch (ParseException | ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (AuthorizationException | LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		}
	}

	protected String genChartExceptionErrorResponse(FinAccountChartException e) {
		return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
	}

	@RequestMapping(value = "/genReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String genReport(@RequestBody FinComChartRequest request) {
		ServiceComChartModel charModel;
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			// [Step1] check the account object list
			String startDateString = request.getStartDate();
			Date startDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(startDateString);
			String endDateString = request.getEndDate();
			Date endDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(endDateString);
			String accObjectUUID1 = request.getAccObjectUUID1();
			String accObjectUUID2 = request.getAccObjectUUID2();
			String accObjectUUID3 = request.getAccObjectUUID3();
			String accObjectUUID4 = request.getAccObjectUUID4();
			String accountTitleUUID = request.getAccountTitleUUID();
			int unit = request.getUnit();
			List<String> accObjectUUIDList = mergeAccObjectUUID(accObjectUUID1,
					accObjectUUID2, accObjectUUID3, accObjectUUID4);
			if (accObjectUUIDList.size() == 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_NO_ACCOBJECT);
			}
			// [Step2] check the time slot
			List<ServiceChartTimeSlot> tsList = ServiceChartHelper
					.genChartTimeSlots(unit, startDate, endDate);
			if (tsList == null || tsList.size() <= 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_WRONG_TIME_SET);
			}
			if (tsList.size() > MAX_TIMESLOT_NUM) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_TOOLONG_TS);
			}
			charModel = finAccountManager.genComFinChartModel(accountTitleUUID,
					unit, accObjectUUIDList,  logonUser.getClient(), startDate, endDate);
			ServiceComChartUIModel serviceComChartUIModel = ServiceChartHelper
					.convertChartModelToUI(charModel);
			String jsonString = ServiceJSONParser
					.genDefOKJSONObject(serviceComChartUIModel);
			return jsonString;
		} catch (FinAccountException | SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (FinAccountChartException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ParseException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	@RequestMapping(value = "/genReportTransProfit", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String genReportTransProfit(@RequestBody FinComChartRequest request) {
		ServiceComChartModel charModel;
		try {
			serviceBasicUtilityController.preCheckResourceAccessCore(
					AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(
						LogonInfoException.TYPE_NO_LOGON_USER);
			}
			// [Step1] check the account object list
			String startDateString = request.getStartDate();
			Date startDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(startDateString);
			String endDateString = request.getEndDate();
			Date endDate = DefaultDateFormatConstant.DATE_MIN_FORMAT
					.parse(endDateString);
			String accObjectUUID1 = request.getAccObjectUUID1();
			String accObjectUUID2 = request.getAccObjectUUID2();
			String accObjectUUID3 = request.getAccObjectUUID3();
			String accObjectUUID4 = request.getAccObjectUUID4();
			/**
			 * Define the format to calculate the TransProfit: Main business
			 * income + Main business expense
			 */
			String gmainBusinessInComeID = GMainBusinessIncome.ID;
			String gmainBusinessExpenseID = GMainBusinessExpense.ID;
			FinAccountTitle fAccTitleIncome = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(gmainBusinessInComeID,
							IServiceEntityNodeFieldConstant.ID,
							FinAccountTitle.NODENAME, logonUser.getClient(), null, true);
			FinAccountTitle fAccTitleExpense = (FinAccountTitle) finAccountTitleManager
					.getEntityNodeByKey(gmainBusinessExpenseID,
							IServiceEntityNodeFieldConstant.ID,
							FinAccountTitle.NODENAME, logonUser.getClient(), null, true);
			if (fAccTitleIncome == null) {
				throw new FinAccountTitleException(
						FinAccountTitleException.TYPE_NO_TITLE_EXIST_BYID,
						GMainBusinessIncome.NAME);
			}
			if (fAccTitleExpense == null) {
				throw new FinAccountTitleException(
						FinAccountTitleException.TYPE_NO_TITLE_EXIST_BYID,
						GMainBusinessExpense.NAME);
			}
			List<String> accountTitleUUIDList = new ArrayList<String>();
			int unit = request.getUnit();
			List<String> accObjectUUIDList = mergeAccObjectUUID(accObjectUUID1,
					accObjectUUID2, accObjectUUID3, accObjectUUID4);
			if (accObjectUUIDList.size() == 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_NO_ACCOBJECT);
			}
			// [Step2] check the time slot
			List<ServiceChartTimeSlot> tsList = ServiceChartHelper
					.genChartTimeSlots(unit, startDate, endDate);
			if (tsList == null || tsList.size() <= 0) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_WRONG_TIME_SET);
			}
			if (tsList.size() > MAX_TIMESLOT_NUM) {
				throw new FinAccountChartException(
						FinAccountChartException.TYPE_TOOLONG_TS);
			}
			charModel = finAccountManager.genComFinChartModel(accountTitleUUIDList,
					unit, accObjectUUIDList, logonUser.getClient(), startDate, endDate);
			ServiceComChartUIModel serviceComChartUIModel = ServiceChartHelper
					.convertChartModelToUI(charModel);
			/**
			 * Set the sub title of transport profit
			 */
			serviceComChartUIModel.setSubTitle("运输利润 = 主营业务收入-主营业务支出");
			String jsonString = ServiceJSONParser
					.genDefOKJSONObject(serviceComChartUIModel);
			return jsonString;
		} catch (FinAccountException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (SearchConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (NodeNotFoundException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (ServiceEntityInstallationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (FinAccountChartException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e
					.getErrorMessage());
		} catch (ParseException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (IOException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (FinAccountTitleException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	protected List<String> mergeAccObjectUUID(String accObjectUUID1,
			String accObjectUUID2, String accObjectUUID3, String accObjectUUID4) {
		List<String> uuidList = new ArrayList<String>();
		internalMergeAccUUID(accObjectUUID1, uuidList);
		internalMergeAccUUID(accObjectUUID2, uuidList);
		internalMergeAccUUID(accObjectUUID3, uuidList);
		internalMergeAccUUID(accObjectUUID4, uuidList);
		return uuidList;
	}

	protected void internalMergeAccUUID(String tmpUUID, List<String> uuidList) {
		if (tmpUUID == null
				|| tmpUUID.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return;
		}
		for (String uuid : uuidList) {
			// Avoid the duplicate account object UUID
			if (tmpUUID.equals(uuid)) {
				return;
			}
		}
		uuidList.add(tmpUUID);
	}


	@RequestMapping(value = "/genFinanceReport", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String genFinanceReport() {
		try {
			return genInitFinReportData();
		} catch (ServiceEntityInstallationException e) {
			return UNKNOWN_SYS_ERROR_JSON;
		} catch (IOException e) {
			return UNKNOWN_SYS_ERROR_JSON;
		}
	}

	public String genInitFinReportData()
			throws ServiceEntityInstallationException, IOException {
		ServiceComChartModel charModel = new ServiceComChartModel();
		// Init data series
		List<ServiceChartDataSeries> dataSeries = new ArrayList<ServiceChartDataSeries>();
		ServiceChartDataSeries series1 = new ServiceChartDataSeries();
		series1.setObjectName("A进出口贸易公司");
		series1.setObjectUUID("43039291-8673-4c23-9378-817aa047353a");
		List<Double> valueList1 = new ArrayList<Double>();
		valueList1.add(193.5);
		valueList1.add(245.0);
		valueList1.add(235.3);
		series1.setValueList(valueList1);
		dataSeries.add(series1);

		ServiceChartDataSeries series2 = new ServiceChartDataSeries();
		series2.setObjectName("B装饰材料有限公司");
		series2.setObjectUUID("6947b836-92c0-4965-a145-b8229fc100b2");
		List<Double> valueList2 = new ArrayList<Double>();
		valueList2.add(263.5);
		valueList2.add(295.0);
		valueList2.add(292.3);
		series2.setValueList(valueList2);
		dataSeries.add(series2);

		ServiceChartDataSeries series3 = new ServiceChartDataSeries();
		series3.setObjectName("C汽车配件有限公司");
		series3.setObjectUUID("6e775667-3897-4ef4-82cb-b0ce590dfc85");
		List<Double> valueList3 = new ArrayList<Double>();
		valueList3.add(98.5);
		valueList3.add(102.0);
		valueList3.add(104.3);
		series3.setValueList(valueList3);
		dataSeries.add(series3);

		ServiceChartDataSeries series4 = new ServiceChartDataSeries();
		series4.setObjectName("D电气配件有限公司");
		series4.setObjectUUID("6e775667-3897-4ef4-82cb-b0ce590dfc85");
		List<Double> valueList4 = new ArrayList<>();
		valueList4.add(156.2);
		valueList4.add(184.0);
		valueList4.add(192.5);
		series4.setValueList(valueList4);
		dataSeries.add(series4);

		charModel.setDataSeries(dataSeries);
		// init category
		List<String> categories = new ArrayList<>();
		categories.add("2013-09");
		categories.add("2013-10");
		categories.add("2013-11");
		charModel.setCategories(categories);

		charModel.setTitle("通用报表示例");
		charModel
				.setSubTitle(financeAccountMessageHelper
						.getMessage(FinanceAccountMessageHelper.SUBTITLE_GEN_FIN_CHART));
		charModel.setyAxisTitle(financeAccountMessageHelper
				.getMessage(FinanceAccountMessageHelper.YAIXSLABLE_GEN_FIN));
		ServiceComChartUIModel serviceComChartUIModel = ServiceChartHelper
				.convertChartModelToUI(charModel);
		String jsonString = ServiceJSONParser
				.genDefOKJSONObject(serviceComChartUIModel);
		return jsonString;
	}

}
