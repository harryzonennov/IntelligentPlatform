package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.company.IntelligentPlatform.common.dto.DataTableRequestData;
import com.company.IntelligentPlatform.common.dto.DataTableResponseData;
// TODO-DAO: import platform.foundation.DAO.PageSplitHelper; // replaced by local stub
// TODO-DAO: import platform.foundation.DAO.PageSplitModel; // replaced by local stub
import com.company.IntelligentPlatform.common.service.PageSplitHelper;
import com.company.IntelligentPlatform.common.service.PageSplitModel;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.BSearchResponse;
import com.company.IntelligentPlatform.common.service.BSearchSettings;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Service Entity Basic List View Controller
 * 
 * @author Zhang,Hang
 * 
 * @date Dec 15, 2012
 */
public class SEListController extends SEBasicController {
	

	public static final String LABEL_VIEWPAGE_SIZE = "viewPageSize";

	public static final String LABEL_CURRENT_PAGE = "currentPage";

	public static final String LABEL_START_INDEX = "startIndex";

	public static final String LABEL_PAGELIST = "pageList";

	public static final String LABEL_START_PAGE = "startPage";

	public static final String LABEL_END_PAGE = "endPage";

	public static final String LABEL_RECORD_NUM = "recordNum";

	public static final String LABEL_PAGE_NUM = "pageNum";

	protected PageSplitModel pageSplitModel = new PageSplitModel();

	public SEListController() {
		
	}

	public void setListPageConfig(int viewPageSize, int recordSizePage) {
		this.pageSplitModel = new PageSplitModel();
		pageSplitModel.setViewPageSize(viewPageSize);
		pageSplitModel.setRecordInPageSize(recordSizePage);
	}

	/**
	 * Get SE node list in current page
	 * 
	 * @param rawList
	 * @param currentPage
	 * @return
	 */
	public List<ServiceEntityNode> getSubPageList(
			List<ServiceEntityNode> rawList, int currentPage) {
		if (this.pageSplitModel == null) {
			// should raise exception
		}
		if (rawList == null) {
			return new ArrayList<>();
		}
		refreshPageSplitModel(rawList.size(), currentPage);
		int startIndex = PageSplitHelper
				.caculateStartRecordIndex(pageSplitModel);
		int endIndex = PageSplitHelper.caculateEndRecordIndex(pageSplitModel);
		List<ServiceEntityNode> subList = rawList.subList(startIndex, endIndex);
		return subList;
	}
	
	/**
	 * Get SE UI Model list in current page
	 * 
	 * @param rawList
	 * @param currentPage
	 * @return
	 */
	public List<SEUIComModel> getSubPageUIModelList(
			List<SEUIComModel> rawList, int currentPage) {
		if (this.pageSplitModel == null) {
			// should raise exception
		}
		if (rawList == null) {
			return new ArrayList<SEUIComModel>();
		}
		refreshPageSplitModel(rawList.size(), currentPage);
		int startIndex = PageSplitHelper
				.caculateStartRecordIndex(pageSplitModel);
		int endIndex = PageSplitHelper.caculateEndRecordIndex(pageSplitModel);
		List<SEUIComModel> subList = rawList.subList(startIndex, endIndex);
		return subList;
	}
	
	/**
	 * Logic to refresh the page split mode by all record amount and current page
	 * @param allRecordAmount
	 * @param currentPage
	 */
	public void refreshPageSplitModel(int allRecordAmount, int currentPage){
		this.pageSplitModel.setAllRecordAmount(allRecordAmount);
		// set the right page index firstly
		int allPageNum = PageSplitHelper.caculateAllPageNum(pageSplitModel);
		if (currentPage > allPageNum) {
			currentPage = allPageNum;
		}
		this.pageSplitModel.setAllPageNum(allPageNum);
		// set current page to attribute pageSplitModel
		this.pageSplitModel.setCurrentPage(currentPage);
	}

	/**
	 * Public API for standard list controller to add page split object resource
	 * requirement: controller attribute <code>pageSplitModel</code> must not be
	 * empty. sub attribute [allPageNum] [currrentPage] [allRecordAmount] should
	 * not be empty.
	 * 
	 * @param mav
	 */
	public void assignPageSplitResource(ModelAndView mav) {
		mav.addObject(LABEL_PAGE_NUM, this.pageSplitModel.getAllPageNum());
		mav.addObject(LABEL_CURRENT_PAGE, this.pageSplitModel.getCurrentPage());
		mav.addObject(LABEL_RECORD_NUM,
				this.pageSplitModel.getAllRecordAmount());
		int startIndex = PageSplitHelper
				.caculateStartRecordIndex(pageSplitModel);
		List<Integer> pageArray = PageSplitHelper
				.getViewPageArray(pageSplitModel);
		int startPage = 1;
		int endPage = 1;
		if (pageArray != null && pageArray.size() > 1) {
			startPage = pageArray.get(0);
			endPage = pageArray.get(pageArray.size() - 1);
		}
		mav.addObject(LABEL_START_PAGE, startPage);
		mav.addObject(LABEL_START_INDEX, startIndex);
		mav.addObject(LABEL_END_PAGE, endPage);
	}
	
	/**
	 * Mapping the string format request to Search Model
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static <T extends SEUIComModel> T parseRequestToSearchModel(String request, Class<T> type)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		JsonNode root = mapper.readTree(request);
		JsonNode searchContentNode = root
				.path(DataTableResponseData.FIELD_content);
		T searchModel = mapper.treeToValue(
				searchContentNode, type);
		return searchModel;
	}

	/**
	 * Core method to handle dataTable search request, and return Json format
	 * response data
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public <T extends SEUIComModel> String searchTableCore(String request, String client,
			Comparator<ServiceEntityNode> comparator,
			Class<T> searchModelClass,
			ISearchCoreMethod<T> searchCore,
			IConvertSearchDataMethod convertDataList)
            throws IOException, LogonInfoException, SearchConfigureException, ServiceModuleProxyException,
            ServiceEntityConfigureException, ServiceEntityInstallationException, DocActionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		JsonNode root = mapper.readTree(request);
        T searchModel = parseRequestToSearchModel(request, searchModelClass);
        List<ServiceEntityNode> rawList = searchCore.apply(searchModel);
		if (comparator != null) {
			if(rawList == null){
				rawList = new ArrayList<>();
			}
			rawList.sort(comparator);
		}
		int start = root.path(DataTableResponseData.FIELD_start).asInt();
		int draw = root.path(DataTableResponseData.FIELD_draw).asInt();
		int length = root.path(DataTableResponseData.FIELD_length).asInt();
		int recordsFiltered = 0;
		int recordsTotal = 0;
		if (!ServiceCollectionsHelper.checkNullList(rawList)) {
			recordsTotal = rawList.size();
			recordsFiltered = rawList.size();
		}
		List<ServiceEntityNode> tempList = ServiceCollectionsHelper.subList(rawList, start, length);

		List<?> uiModelList = convertDataList.apply(tempList);
		DataTableResponseData dataTableResponseData = new DataTableResponseData();
		dataTableResponseData.setDraw(draw);
		dataTableResponseData.setData(uiModelList);
		dataTableResponseData.setRecordsFiltered(recordsFiltered);
		dataTableResponseData.setRecordsTotal(recordsTotal);
		JSONObject responseObject = JSONObject
				.fromObject(dataTableResponseData);
		return responseObject.toString();
	}

	/**
	 * Core method to handle dataTable search request, and return Json format
	 * response data
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String searchDataTableCore(String request, String client,
								  Comparator<ServiceEntityNode> comparator,
								  Class<?> searchModelClass, ISearchDataMethodResponse searchCore,
								  IConvertSearchDataMethod convertDataList) throws IOException, LogonInfoException, SearchConfigureException,
            ServiceModuleProxyException, ServiceEntityConfigureException,
            ServiceEntityInstallationException, AuthorizationException, DocActionException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		JsonNode root = mapper.readTree(request);
		Class<SEUIComModel> newSearchClass = (Class<SEUIComModel>) searchModelClass;
        SEUIComModel searchModel = parseRequestToSearchModel(request, newSearchClass);
        int start = root.path(DataTableResponseData.FIELD_start).asInt();
		int draw = root.path(DataTableResponseData.FIELD_draw).asInt();
		int length = root.path(DataTableResponseData.FIELD_length).asInt();
		int recordsFiltered = 0;
		int recordsTotal = 0;
		int pageEnd = start + length; // Page End might be larger than total size
		DataTableRequestData dataTableRequestData = new DataTableRequestData(searchModel, start,length, pageEnd
				, 0);
		BSearchResponse bSearchResponse = searchCore.apply(dataTableRequestData);
		List<ServiceEntityNode> rawList = bSearchResponse.getResultList();
		if (comparator != null) {
			if(rawList == null){
				rawList = new ArrayList<>();
			}
			rawList.sort(comparator);
		}
		if (!ServiceCollectionsHelper.checkNullList(rawList)) {
			recordsTotal = rawList.size();
			recordsFiltered = rawList.size();
		}
		List<ServiceEntityNode> tempList = rawList;
		if (length > 0 && !ServiceCollectionsHelper.checkNullList(rawList)
				&& recordsTotal > length ) {
			if(recordsTotal > pageEnd){
				// In case Page End smaller than total size, means not the end page
				tempList = new ArrayList<>(rawList.subList(start,
						pageEnd));
			}else{
				// In case Page End larger than total size, means the end page
				tempList = new ArrayList<>(rawList.subList(start,
						recordsTotal));
			}
		}
		List<?> uiModelList = convertDataList.apply(tempList);
		DataTableResponseData dataTableResponseData = new DataTableResponseData();
		dataTableResponseData.setDraw(draw);
		dataTableResponseData.setData(uiModelList);
		dataTableResponseData.setRecordsFiltered(recordsFiltered);
		dataTableResponseData.setRecordsTotal(recordsTotal);
		JSONObject responseObject = JSONObject
				.fromObject(dataTableResponseData);
		return responseObject.toString();
	}



	/**
	 * Core method to handle dataTable search request, and return Json format
	 * response data
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public String searchModelTableDataCore(String request, String client,
			Comparator<ServiceEntityNode> comparator,
			Class<?> searchModelClass,
			Function<SEUIComModel, List<ServiceEntityNode>> searchCore,
			Function<List<ServiceEntityNode>, List<?>> convertDataList)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		JsonNode root = mapper.readTree(request);
		JsonNode searchContentNode = root
				.path(DataTableResponseData.FIELD_content);
		SEUIComModel searchModel = (SEUIComModel) mapper.treeToValue(
				searchContentNode, searchModelClass);
		List<ServiceEntityNode> rawList = searchCore.apply(searchModel);
		if (comparator != null) {
		    if(rawList == null){
		    	rawList = new ArrayList<>();
		    }
			rawList.sort(comparator);
		}
		int start = root.path(DataTableResponseData.FIELD_start).asInt();
		int draw = root.path(DataTableResponseData.FIELD_draw).asInt();
		int length = root.path(DataTableResponseData.FIELD_length).asInt();
		int recordsFiltered = 0;
		int recordsTotal = 0;
		if (!ServiceCollectionsHelper.checkNullList(rawList)) {
			recordsTotal = rawList.size();
			recordsFiltered = rawList.size();
		}
		List<ServiceEntityNode> tempList = rawList;
		int pageEnd = start + length; // Page End might be larger than total size
		if (length > 0 && !ServiceCollectionsHelper.checkNullList(rawList)
				&& recordsTotal > length ) {
			if(recordsTotal > pageEnd){
				// In case Page End smaller than total size, means not the end page
				tempList = new ArrayList<>(rawList.subList(start,
						pageEnd));
			}else{
				// In case Page End larger than total size, means the end page
				tempList = new ArrayList<>(rawList.subList(start,
						recordsTotal));
			}
		}
		List<?> uiModelList = convertDataList.apply(tempList);
		DataTableResponseData dataTableResponseData = new DataTableResponseData();
		dataTableResponseData.setDraw(draw);
		dataTableResponseData.setData(uiModelList);
		dataTableResponseData.setRecordsFiltered(recordsFiltered);
		dataTableResponseData.setRecordsTotal(recordsTotal);
		JSONObject responseObject = JSONObject
				.fromObject(dataTableResponseData);
		return responseObject.toString();

	}

	/**
	 * Core method to handle dataTable search request, and return Json format
	 * response data
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String searchModelSetting(String request, String client,
			Comparator<ServiceEntityNode> comparator,
			Class<?> searchModelClass,
			Function<BSearchSettings, BSearchResponse> searchCore,
			Function<List<ServiceEntityNode>, List<?>> convertDataList)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		JsonNode root = mapper.readTree(request);
		JsonNode searchContentNode = root
				.path(DataTableResponseData.FIELD_content);
		SEUIComModel searchModel = (SEUIComModel) mapper.treeToValue(
				searchContentNode, searchModelClass);
		int start = root.path(DataTableResponseData.FIELD_start).asInt();
		int draw = root.path(DataTableResponseData.FIELD_draw).asInt();
		int length = root.path(DataTableResponseData.FIELD_length).asInt();
		int recordsFiltered = 0;
		int recordsTotal = 0;
		BSearchSettings bSearchSettings = new BSearchSettings(null, client, true, start, length, searchModel);
		BSearchResponse bSearchResponse = searchCore.apply(bSearchSettings);
		if (comparator != null) {
		    if(bSearchResponse.getResultList() == null){
		    	bSearchResponse.setResultList(new ArrayList<>());
		    }
			Collections.sort(bSearchResponse.getResultList(), comparator);
		}
		List<ServiceEntityNode> rawList = bSearchResponse.getResultList();
		if (!ServiceCollectionsHelper.checkNullList(rawList)) {
			recordsTotal = bSearchResponse.getRecordsTotal();
			recordsFiltered = bSearchResponse.getRecordsTotal();
		}
		List<ServiceEntityNode> tempList = rawList;
		int pageEnd = start + length; // Page End might be larger than total size
		if (length > 0 && !ServiceCollectionsHelper.checkNullList(rawList)
				&& recordsTotal > length ) {
			if(recordsTotal > pageEnd){
				// In case Page End smaller than total size, means not the end page
				tempList = new ArrayList<>(rawList.subList(start,
						pageEnd));
			}else{
				// In case Page End larger than total size, means the end page
				tempList = new ArrayList<>(rawList.subList(start,
						recordsTotal));
			}
		}
		List<?> uiModelList = convertDataList.apply(tempList);
		DataTableResponseData dataTableResponseData = new DataTableResponseData();
		dataTableResponseData.setDraw(draw);
		dataTableResponseData.setData(uiModelList);
		dataTableResponseData.setRecordsFiltered(recordsFiltered);
		dataTableResponseData.setRecordsTotal(recordsTotal);
		JSONObject responseObject = JSONObject
				.fromObject(dataTableResponseData);
		String result = responseObject.toString();
		return result;
	}

}
