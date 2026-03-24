package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.Field;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.SEUIModelFieldsHelper;
// TODO-DAO: import platform.foundation.DAO.DAOConstant;
// TODO-DAO: import platform.foundation.DAO.HibernateDefaultImpDAO;
import com.company.IntelligentPlatform.common.service.AuthorizationManager;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceBasicPerformHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SpringContextBeanService;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRegisterEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceReflectiveHelper;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.SEFieldSearchConfig;
import com.company.IntelligentPlatform.common.model.SENodeFieldSearchInfo;

/**
 * This service class for executing searches by generating SQL commands from the provided search context and the search node configurations
 */
@Service
public class BsearchService {

	@Autowired
	protected ServiceEntityRegisterEntityManager serviceEntityRegisterEntityManager;

	// TODO-LEGACY: @Autowired
	@Autowired
	protected HibernateDefaultImpDAO hibernateDefaultImpDAO; // TODO-DAO: stub, replace with JPA

	@Autowired
	protected SpringContextBeanService springContextBeanService;

	@Autowired
	protected AuthorizationManager authorizationManager;

	@Autowired
	protected ServiceEntityManager serviceEntityManager;

	@Autowired
	protected ServiceBasicPerformHelper serviceBasicPerformHelper;

	/**
	 * The execution of search
	 * 
	 * @param seUIComModel
	 * @param searchNodeConfigList
	 * @param fuzzyFlag
	 * @param client
	 *            : client information, if not needed set null value
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> doSearch(SEUIComModel seUIComModel,
			List<BSearchNodeComConfigure> searchNodeConfigList, String client,
			boolean fuzzyFlag) throws ServiceEntityInstallationException,
			SearchConfigureException, ServiceEntityConfigureException {
		// [1st step]:generate the
		// search field configuration from search UI model and fill value to
		// node compound configuration list, set the node filter flag with
		// valued field
		fillSearchModuleFieldConfiguration(searchNodeConfigList, seUIComModel,
				null);

		// [1.5 Step] in case the client information is needed, add to start
		// search node
		if (client != null
				&& !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			BSearchNodeComConfigure startNodeComConfigure = getStartNode(searchNodeConfigList);
			List<SearchConfigPreCondition> preConditionList = startNodeComConfigure
					.getPreConditions();
			if (preConditionList == null) {
				preConditionList = new ArrayList<SearchConfigPreCondition>();
			}
			startNodeComConfigure.setPreConditions(preConditionList);
		}
		// [1.8 step]: for add-on process for
		// multi-table node
		// Flag to indicate weather it is neccessary to process multiple table
		boolean multiTablesFlag = checkMultiTablesFlag(searchNodeConfigList);
		if (multiTablesFlag) {
			List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
			for (BSearchNodeComConfigure trace : traceList) {
				processMultipleTableFromTraceUnion(trace, searchNodeConfigList);
			}
		}
		// [2nd step]:
		// Traverse all the node configure and generate raw SQL statement for
		// each node
		// by valued field and also set node filter flag with pre condition list
		try{
			for (BSearchNodeComConfigure curNodeConfigure : searchNodeConfigList) {
				if (curNodeConfigure.isStartNodeFlag()) {
					setRawSQLStatement(curNodeConfigure, fuzzyFlag,  client);
				} else {
					setRawSQLStatement(curNodeConfigure, fuzzyFlag,  null);
				}
			}
		} catch (NodeNotFoundException e){
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
		}
		// [3rd step]: Generate trace list
		// generate the raw trace list
		List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
		// refresh trace list, set last node which are with filter flag
		List<BSearchNodeComConfigure> refreshedTraceList = refreshValuableTraceList(
				traceList, searchNodeConfigList);
		// [5th] Traverse each trace again and merge the SQL statement
		List<String> commandList = this.generateFinalSQLStatement0(
				refreshedTraceList, searchNodeConfigList);
		// [6th] execute the sql command list
		List<List<ServiceEntityNode>> rawResultList = new ArrayList<>();
		for (String command : commandList) {
			if (!ServiceEntityStringHelper.checkNullString(command)) {
				List<ServiceEntityNode> tmpResultList = this.hibernateDefaultImpDAO
						.getEntityNodeListBySQLCommand(command);
				rawResultList.add(tmpResultList);
			}
		}
		if (rawResultList.size() == 0) {
			return new ArrayList<>();
		}
		if (multiTablesFlag) {
			// special process for multiple start node list
			List<BSearchNodeComConfigure> startNodeList = getStartNodeBatch(searchNodeConfigList);
			if (startNodeList != null && startNodeList.size() > 1) {
				return defaultjoinResultForMultipleTable(searchNodeConfigList,
						rawResultList);
			}
		}
		List<ServiceEntityNode> result = rawResultList.get(0);
		for (int i = 1; i < rawResultList.size(); i++) {
			result = innerjoinResultList(result, rawResultList.get(i));
		}
		return result;
	}

	/**
	 * The execution of search with multi value map
	 * 
	 * @param seUIComModel
	 * @param searchNodeConfigList
	 * @param fuzzyFlag
	 * @param client
	 *            : client information, if not needed set null value
	 * @return
	 * @throws ServiceEntityInstallationException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> doSearch(SEUIComModel seUIComModel,
			List<BSearchNodeComConfigure> searchNodeConfigList,
			Map<String, List<?>> multiValueMap, String client, boolean fuzzyFlag)
			throws ServiceEntityInstallationException,
			SearchConfigureException, ServiceEntityConfigureException {
		// [1st step]:generate the
		// search field configuration from search UI model and fill value to
		// node compound configuration list, set the node filter flag with
		// valued field
		fillSearchModuleFieldConfiguration(searchNodeConfigList, seUIComModel,
				multiValueMap);
		// [1.5 Step] in case the client information is needed, add to start
		// search node
		if (client != null
				&& !client.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			BSearchNodeComConfigure startNodeComConfigure = getStartNode(searchNodeConfigList);
			List<SearchConfigPreCondition> preConditionList = startNodeComConfigure
					.getPreConditions();
			if (preConditionList == null) {
				preConditionList = new ArrayList<>();
			}
			startNodeComConfigure.setPreConditions(preConditionList);
		}
		// [1.8 step]: for add-on process for
		// multi-table node
		// Flag to indicate weather it is necessary to process multiple table
		boolean multiTablesFlag = checkMultiTablesFlag(searchNodeConfigList);
		if (multiTablesFlag) {
			List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
			for (BSearchNodeComConfigure trace : traceList) {
				processMultipleTableFromTraceUnion(trace, searchNodeConfigList);
			}
		}
		// [2nd step]:
		// Traverse all the node configure and generate raw SQL statement for
		// each node
		// by valued field and also set node filter flag with pre condition list
		try{
			for (BSearchNodeComConfigure curNodeConfigure : searchNodeConfigList) {
				if (curNodeConfigure.isStartNodeFlag()) {
					setRawSQLStatement(curNodeConfigure, fuzzyFlag,  client);
				} else {
					setRawSQLStatement(curNodeConfigure, fuzzyFlag,  null);
				}
			}
		} catch (NodeNotFoundException e){
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
		}
		// [3rd step]: Generate trace list
		// generate the raw trace list
		List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
		// refresh trace list, set last node which are with filter flag
		List<BSearchNodeComConfigure> refreshedTraceList = refreshValuableTraceList(
				traceList, searchNodeConfigList);
		// [5th] Traverse each trace again and merge the SQL statement
		List<String> commandList = this.generateFinalSQLStatement0(
				refreshedTraceList, searchNodeConfigList);
		// [6th] execute the sql command list
		List<List<ServiceEntityNode>> rawResultList = new ArrayList<>();
		for (String command : commandList) {
			List<ServiceEntityNode> tmpResultList = this.hibernateDefaultImpDAO
					.getEntityNodeListBySQLCommand(command);
			rawResultList.add(tmpResultList);
		}
		if (rawResultList.size() == 0) {
			return new ArrayList<>();
		}
		if (multiTablesFlag) {
			// special process for multiple start node list
			List<BSearchNodeComConfigure> startNodeList = getStartNodeBatch(searchNodeConfigList);
			if (startNodeList != null && startNodeList.size() > 1) {
				return defaultjoinResultForMultipleTable(searchNodeConfigList,
						rawResultList);
			}
		}
		List<ServiceEntityNode> result = rawResultList.get(0);
		for (int i = 1; i < rawResultList.size(); i++) {
			result = innerjoinResultList(result, rawResultList.get(i));
		}
		return result;
	}

	/**
	 * The main API method to executes a search based on the provided search context and a list of search node configurations,
	 * and returns the search response.
	 *
	 * @param searchContext the context containing the search context information: such as logon info, search models, authorization
	 * @param searchNodeConfigList a list of configurations for the search nodes
	 * @return a BSearchResponse object containing the results of the search
	 * @throws ServiceEntityConfigureException if there is an issue configuring a service entity
	 * @throws LogonInfoException if there is an issue with logon information
	 * @throws SearchConfigureException if there is an issue configuring the search
	 * @throws ServiceEntityInstallationException if there is an issue installing a service entity
	 */
	public BSearchResponse doSearchWithContext(SearchContext searchContext,
												List<BSearchNodeComConfigure> searchNodeConfigList)
			throws ServiceEntityConfigureException, LogonInfoException, SearchConfigureException, ServiceEntityInstallationException {
		String[] fieldNameArray = searchContext.getFieldNameArray();
		SEUIComModel searchModel = searchContext.getSearchModel();
		Map<String, List<?>> multiValueMap = generateMulitpleSearchMap(searchModel, fieldNameArray);
		// Step 1: pre-process the start node configure, like merging the login, authorization information into the start node.
		preProcessStartComConfigure(searchContext, searchNodeConfigList);
		// Step 2: generate the SQL command for searching
		String sqlCommand = genSQLCommandCore(searchContext, searchNodeConfigList, multiValueMap,
				searchContext.getFuzzyFlag(), searchContext.getClient());
		// Step 3: execute the SQL command and get the search result.
		return genSearchResultByCommand(searchContext, sqlCommand);
	}

	public BSearchResponse genSearchResultByCommand(SearchContext searchContext,
									   String sqlCommand) throws SearchConfigureException {
		List<?> rawResultList = new ArrayList<>();
		List<ServiceBasicPerformHelper.RecordPointTime> recordPointTimeList =
				searchContext.getRecordPointTimeList();
		// Process the raw sql command
		if (searchContext.getSearchHeaderContext() != null) {
			// in case only uuid needed
			if (searchContext.getSearchHeaderContext().isFlagUuidHeader()) {
				sqlCommand = "select uuid " + sqlCommand;
			}
		}
		if (searchContext.getStart() >= 0
				&& searchContext.getLength() > 0) {
			rawResultList = this.hibernateDefaultImpDAO
					.executeBySQLCommand(sqlCommand);
			if(!ServiceCollectionsHelper.checkNullList(recordPointTimeList)){
				serviceBasicPerformHelper.recordPointTime("after get paged data list",
						recordPointTimeList);
			}
		} else {
			rawResultList = this.hibernateDefaultImpDAO
					.executeBySQLCommand(sqlCommand);
		}
		if (searchContext.getSearchHeaderContext() != null && searchContext.getSearchHeaderContext().isFlagUuidHeader()) {
			BSearchResponse searchResponse = new BSearchResponse();
			searchResponse.setUuidList((List<String>) rawResultList);
			return searchResponse;
		}
		return genSearchResponse((List<ServiceEntityNode>) rawResultList, 0);
	}

	public static BSearchResponse genSearchResponse(List<ServiceEntityNode> resultList, int recordsTotal) {
		BSearchResponse bSearchResponse = new BSearchResponse();
		bSearchResponse.setResultList(resultList);
		if (recordsTotal > 0) {
			bSearchResponse.setRecordsTotal(recordsTotal);
		} else {
			bSearchResponse.setRecordsTotal(ServiceCollectionsHelper.checkNullList(resultList) ? 0: resultList.size());
		}
		return bSearchResponse;
	}

	/**
	 * API to search on line list from cache
	 * @param searchContext
	 * @param searchNodeConfigList
	 * @param rawSEList
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws LogonInfoException
	 * @throws SearchConfigureException
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityInstallationException
	 */
	public BSearchResponse doSearchOnline(SearchContext searchContext,
												  List<BSearchNodeComConfigure> searchNodeConfigList,
													 List<ServiceEntityNode> rawSEList)
			throws ServiceEntityConfigureException, LogonInfoException, SearchConfigureException,
			ServiceEntityInstallationException {
		if(ServiceCollectionsHelper.checkNullList(rawSEList)){
			return null;
		}
		String[] fieldNameArray = searchContext.getFieldNameArray();
		SEUIComModel searchModel = searchContext.getSearchModel();
		Map<String, List<?>> multiValueMap = generateMulitpleSearchMap(searchModel, fieldNameArray);
		BSearchNodeComConfigure startSearchComConfigure = preProcessStartComConfigure(searchContext, searchNodeConfigList);
		genSQLCommandCore(searchContext, searchNodeConfigList, multiValueMap,
				searchContext.getFuzzyFlag(), searchContext.getClient());
		List<SEFieldSearchConfig> fieldConfigList = startSearchComConfigure.getFieldConfigList();
		List<ServiceEntityNode> resultList = new ArrayList<>();
		resultList = filterOnlineListByConfigureListCore(rawSEList, fieldConfigList);
		List<SearchConfigPreCondition> preConditionList = startSearchComConfigure.getPreConditions();
		resultList = filterOnlineListByPreConfigureListCore(resultList, preConditionList);
		return genSearchResponse(resultList, 0);
	}



	private List<ServiceEntityNode> filterOnlineListByConfigureListCore(List<ServiceEntityNode> rawSEList,
																		List<SEFieldSearchConfig> fieldConfigList) {
		if(ServiceCollectionsHelper.checkNullList(rawSEList)){
			return null;
		}
		if(ServiceCollectionsHelper.checkNullList(fieldConfigList)){
			return rawSEList;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for(ServiceEntityNode serviceEntityNode: rawSEList){
			if(!ServiceCollectionsHelper.checkNullList(fieldConfigList)){
				// Traverse each valid field configure
				boolean hitFlag = false;
				for(SEFieldSearchConfig seFieldSearchConfig: fieldConfigList){
					Object tmpSEValue = ServiceReflectiveHelper.getFieldValue(serviceEntityNode,
							seFieldSearchConfig.getFieldName());
					if(tmpSEValue == null){
						continue;
					}
					if(seFieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_EQUAL){
						String fieldTypeName = seFieldSearchConfig.getLowValueField().getType().getSimpleName();
						if(ServiceReflectiveHelper.checkValueEqual(tmpSEValue, seFieldSearchConfig.getLowValue(),
								fieldTypeName)){
							hitFlag = true;
							continue;
						} else {
							hitFlag = false;
							break;
						}
					}
				}
				if(hitFlag){
					ServiceCollectionsHelper.mergeToList(resultList, serviceEntityNode);
				}
			}
		}
		return resultList;
	}

	private List<ServiceEntityNode> filterOnlineListByPreConfigureListCore(List<ServiceEntityNode> rawSEList,
																	List<SearchConfigPreCondition> preConditionList)
			throws SearchConfigureException {
		if(ServiceCollectionsHelper.checkNullList(rawSEList)){
			return null;
		}
		if(ServiceCollectionsHelper.checkNullList(preConditionList)){
			return rawSEList;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		for(ServiceEntityNode serviceEntityNode: rawSEList){
			if(!ServiceCollectionsHelper.checkNullList(preConditionList)){
				// Traverse each valid field configure
				for(SearchConfigPreCondition searchConfigPreCondition: preConditionList){
					Object tmpSEValue = ServiceReflectiveHelper.getFieldValue(serviceEntityNode,
							searchConfigPreCondition.getFieldName());
					if(searchConfigPreCondition.getOperator() == SEFieldSearchConfig.OPERATOR_EQUAL){
						Field field = null;
						try {
							field = ServiceEntityFieldsHelper.getServiceField(serviceEntityNode.getClass(),
									searchConfigPreCondition.getFieldName());
						} catch (IllegalAccessException | NoSuchFieldException e) {
							continue;
						}
						field.setAccessible(true);
						if(ServiceReflectiveHelper.checkValueEqual(tmpSEValue, searchConfigPreCondition.getFieldValue(),
								field.getType().getSimpleName())){
							// In case hit this condition
							continue;
						} else {
							// In case doesn't hit this condition
							break;
						}
					}
					ServiceCollectionsHelper.mergeToList(resultList, serviceEntityNode);
				}
			}
		}
		return resultList;
	}

	private BSearchNodeComConfigure preProcessStartComConfigure(SearchContext searchContext, List<BSearchNodeComConfigure> searchNodeConfigList)
			throws ServiceEntityConfigureException, LogonInfoException, SearchConfigureException {
		List<ServiceBasicKeyStructure> keyStructureList =
				authorizationManager.generateSearchCondition(searchContext.getSearchAuthorizationContext());
		BSearchNodeComConfigure startSearchComConfigure = getStartNode(searchNodeConfigList);
		List<SearchConfigPreCondition> preConditionList = ServiceSearchProxy.mergeIntoPreConditionList(keyStructureList,
				startSearchComConfigure.getPreConditions());
		startSearchComConfigure.setPreConditions(preConditionList);
		return startSearchComConfigure;
	}


	@Deprecated
	/**
	 * Don't use this method, since pre-get uuid way cause bad hibernate performance
	 */
	public BSearchResponse doSearchWithContextRaw(SearchContext searchContext,
												List<BSearchNodeComConfigure> searchNodeConfigList)
			throws ServiceEntityConfigureException, LogonInfoException, SearchConfigureException, ServiceEntityInstallationException {
		String[] fieldNameArray = searchContext.getFieldNameArray();
		SEUIComModel searchModel = searchContext.getSearchModel();
		Map<String, List<?>> multiValueMap = generateMulitpleSearchMap(searchModel, fieldNameArray);
		// get List of search key structure from authorization manager
		List<ServiceBasicKeyStructure> keyStructureList =
				authorizationManager.generateSearchCondition(searchContext.getSearchAuthorizationContext());
		BSearchNodeComConfigure startSearchComConfigure = getStartNode(searchNodeConfigList);
		List<SearchConfigPreCondition> preConditionList = ServiceSearchProxy.mergeIntoPreConditionList(keyStructureList,
				startSearchComConfigure.getPreConditions());
		startSearchComConfigure.setPreConditions(preConditionList);
		// [6th] execute the sql command list
		String sqlCommand = genSQLCommandCore(searchContext, searchNodeConfigList, multiValueMap,
				searchContext.getFuzzyFlag(), searchContext.getClient());
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (searchContext.getStart() >= 0
				&& searchContext.getLength() > 0) {
			SQLStatementHeader sqlStatementHeader = null;
			try {
				sqlStatementHeader = generateRawSQLHeader(startSearchComConfigure,
						searchContext.getClient());
			} catch (NodeNotFoundException e) {
				throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
			}
			// Add default sort logic
			sqlCommand =
					sqlCommand + " order by " + sqlStatementHeader.getVarName() + "." + IServiceEntityNodeFieldConstant.LASTUPDATETIME + " desc";
			// in case need pagination
			List<ServiceBasicPerformHelper.RecordPointTime> recordPointTimeList =
					searchContext.getRecordPointTimeList();
			if(!ServiceCollectionsHelper.checkNullList(recordPointTimeList)){
				recordPointTimeList = serviceBasicPerformHelper.recordPointTime("before get uuid list",
						recordPointTimeList);
			}
			List<String> uuidList = this.hibernateDefaultImpDAO.getUUIDListBySQLCommand(sqlCommand);
			int totalRecords = (uuidList == null)? 0 : uuidList.size();
			// sub list of uuid by start, length
			List<String> subUUIDList = ServiceCollectionsHelper.subList(uuidList, searchContext.getStart(), searchContext.getLength());
			if(ServiceCollectionsHelper.checkNullList(subUUIDList)){
				return null;
			}
			if(!ServiceCollectionsHelper.checkNullList(recordPointTimeList)){
				recordPointTimeList = serviceBasicPerformHelper.recordPointTime("after get uuid list",
						recordPointTimeList);
			}
			SearchConfigPreCondition preConditionUUID = new SearchConfigPreCondition(null,
					IServiceEntityNodeFieldConstant.UUID, startSearchComConfigure.getNodeInstID());
			preConditionUUID.setMultipleValueList(subUUIDList);
			startSearchComConfigure.setPreConditions(ServiceCollectionsHelper.asList(preConditionUUID));
			resultList = this.hibernateDefaultImpDAO
					.getEntityNodeListBySQLCommand(sqlStatementHeader.getSqlStatement());
			if(!ServiceCollectionsHelper.checkNullList(recordPointTimeList)){
				recordPointTimeList = serviceBasicPerformHelper.recordPointTime("after get paged data list",
						recordPointTimeList);
			}
//			resultList = (List<ServiceEntityNode>) this.hibernateDefaultImpDAO
//					.getListBySQLCommand(sqlCommand,
//							searchContext.getStart(),
//							searchContext.getLength());
			//int totalRecords = this.hibernateDefaultImpDAO.getRecordNumBySQLCommand(sqlCommand);
			return genSearchResponse(resultList, totalRecords);
		} else {
			resultList = this.hibernateDefaultImpDAO
					.getEntityNodeListBySQLCommand(sqlCommand);
			return genSearchResponse(resultList, 0);
		}
	}

	/**
	 * The logic to generate the SQL command for executing a search by inputting search context information and list of search node configuration.
	 *
	 * @param searchContext        The search context information for the search.
	 * @param searchNodeConfigList A list of search node configurations .
	 * @param multiValueMap        A map containing lists of multiple values .
	 * @param fuzzyFlag            A boolean flag indicating whether fuzzy search should be enabled.
	 * @param client               The client information from login.
	 * @return                     The generated SQL command as a string.
	 * @throws ServiceEntityInstallationException If there is an issue with service entity installation.
	 * @throws SearchConfigureException           If there is an error in search configuration.
	 * @throws NodeNotFoundException              If a necessary search node is not found.
	 * @throws ServiceEntityConfigureException    If there is an issue configuring the service entity.
	 */
	private String genSQLCommandCore(SearchContext searchContext, List<BSearchNodeComConfigure> searchNodeConfigList,
									  Map<String, List<?>> multiValueMap,
									boolean fuzzyFlag, String client) throws ServiceEntityInstallationException, SearchConfigureException, ServiceEntityConfigureException {
		// [1st step]:generate the
		// search field configuration from search UI model and fill value to
		// node compound configuration list, set the node filter flag with
		// valued field
		SEUIComModel searchModel = searchContext.getSearchModel();
		fillSearchModuleFieldConfiguration(searchNodeConfigList, searchModel,
				multiValueMap);
		// [1.5 Step] in case the client information is needed, add to start
		// search node
		if (!ServiceEntityStringHelper.checkNullString(client)){
			BSearchNodeComConfigure startNodeComConfigure = getStartNode(searchNodeConfigList);
			List<SearchConfigPreCondition> preConditionList = startNodeComConfigure
					.getPreConditions();
			if (preConditionList == null) {
				preConditionList = new ArrayList<>();
			}
			startNodeComConfigure.setPreConditions(preConditionList);
		}
		// [1.8 step]: for add-on process for
		// multi-table node
		// Flag to indicate weather it is necessary to process multiple table
		boolean multiTablesFlag = checkMultiTablesFlag(searchNodeConfigList);
		if (multiTablesFlag) {
			List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
			for (BSearchNodeComConfigure trace : traceList) {
				processMultipleTableFromTraceUnion(trace, searchNodeConfigList);
			}
		}
		// [2nd step]:
		// Traverse all the node configure and generate raw SQL statement for
		// each node
		// by valued field and also set node filter flag with pre-condition list
		try{
			for (BSearchNodeComConfigure curNodeConfigure : searchNodeConfigList) {
				if (curNodeConfigure.isStartNodeFlag()) {
					setRawSQLStatement(curNodeConfigure,  fuzzyFlag, client);
				} else {
					setRawSQLStatement(curNodeConfigure,  fuzzyFlag, null);
				}
			}
		} catch (NodeNotFoundException e){
			throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getMessage());
		}
		// [3rd step]: Generate trace list
		// generate the raw trace list
		List<BSearchNodeComConfigure> traceList = generateTraceList(searchNodeConfigList);
		// refresh trace list, set last node which are with filter flag
		List<BSearchNodeComConfigure> refreshedTraceList = refreshValuableTraceList(
				traceList, searchNodeConfigList);
		// [5th] Traverse each trace again and merge the SQL statement
        return this.generateFinalSQLStatement(
				refreshedTraceList, searchNodeConfigList);
	}



	/**
	 * [Internal method] return the inner join result list from 2 SE list
	 * 
	 * @param searchNodeConfigList
	 * @param rawResultList
	 * @return
	 */
	protected List<ServiceEntityNode> defaultjoinResultForMultipleTable(
			List<BSearchNodeComConfigure> searchNodeConfigList,
			List<List<ServiceEntityNode>> rawResultList) {
		Map<String, List<ServiceEntityNode>> resultMap = new HashMap<String, List<ServiceEntityNode>>();
		for (int i = 1; i < rawResultList.size(); i++) {
			String tableName = getDefaultTableName(rawResultList.get(i));
			List<ServiceEntityNode> tmpResultList = resultMap.get(tableName);
			if (tmpResultList == null) {
				tmpResultList = rawResultList.get(i);
				resultMap.put(tableName, tmpResultList);
			} else {
				List<ServiceEntityNode> resultList = innerjoinResultList(
						tmpResultList, rawResultList.get(i));
				resultMap.put(tableName, resultList);
			}
		}
		List<ServiceEntityNode> result = rawResultList.get(0);
		Set<String> keySet = resultMap.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			result.addAll(resultMap.get(key));
		}
		return result;
	}

	protected String getDefaultTableName(List<ServiceEntityNode> list) {
		if (ServiceCollectionsHelper.checkNullList(list)) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		ServiceEntityNode seNode = list.get(0);
		return ServiceDocumentComProxy.getServiceEntityModelName(seNode);
	}

	/**
	 * [Internal method] return the inner join result list from 2 SE list
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public List<ServiceEntityNode> innerjoinResultList(
			List<ServiceEntityNode> list1, List<ServiceEntityNode> list2) {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (list1 == null || list1.size() == 0) {
			return resultList;
		}
		if (list2 == null || list2.size() == 0) {
			return resultList;
		}
		for (ServiceEntityNode seNode : list1) {
			for (ServiceEntityNode seNode2 : list2) {
				if (seNode.getUuid().equals(seNode2.getUuid())) {
					resultList.add(seNode2);
					break;
				}
			}
		}
		return resultList;
	}

	/**
	 * Tool method to generate multiple search value map by analysis the
	 * searchModel
	 * 
	 * @param searchModel
	 *            : Search model instance
	 * @param fieldNameArray
	 *            : field name array
	 * @return
	 */
	public Map<String, List<?>> generateMulitpleSearchMap(
			SEUIComModel searchModel, String[] fieldNameArray) {
		if (fieldNameArray == null || searchModel == null) {
			return null;
		}
		Map<String, List<?>> resultMap = new HashMap<>();
		for (String fieldName : fieldNameArray) {
			List<?> tmpList = generateMultipleSearchValueList(searchModel,
					fieldName);
			if (!ServiceCollectionsHelper.checkNullList(tmpList)) {
				resultMap.put(fieldName, tmpList);
			}
		}
		if (resultMap.isEmpty()) {
			return new HashMap<>();
		} else {
			return resultMap;
		}
	}

	public List<?> generateMultipleSearchValueList(SEUIComModel searchModel,
			String fieldName) {
		try {
			Field field = searchModel.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			Object rawValue = field.get(searchModel);
			if (rawValue == null) {
				return null;
			}
			String strValue = rawValue.toString();
			if (ServiceEntityStringHelper.checkNullString(strValue)) {
				return null;
			}
			// Split the multiple value by space.
			String[] strArray = strValue.split("\\s+");
			if ( strArray.length < 2) {
				return null;
			}
			List<String> strList = new ArrayList<>();
			Collections.addAll(strList, strArray);
			// Need to clear the raw value on search model
			field.set(searchModel, ServiceEntityStringHelper.EMPTYSTRING);
			return strList;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return null;
		}
	}

	private void fillGroupFieldConfiguration(List<BSearchNodeComConfigure> searchNodeConfigList, Field groupField, SEUIComModel seUIComModel) throws IllegalAccessException {
		BSearchGroupConfig bSearchGroupConfig = groupField.getAnnotation(BSearchGroupConfig.class);
		if(bSearchGroupConfig == null){
			return;
		}
		String groupId = bSearchGroupConfig.groupInstId();
		BSearchNodeComConfigure groupStartNodeConfig = filterSearchNodeConfigOnline(
				searchNodeConfigList, groupId);
		if (groupStartNodeConfig == null) {
			return;
		}
		groupField.setAccessible(true);
		Object groupValue = groupField.get(seUIComModel);
		if(groupValue == null){
			return;
		}
		// Get all the child nodes from start node
		List<BSearchNodeComConfigure> childSearchNodeConfigList;
		childSearchNodeConfigList = (List<BSearchNodeComConfigure>) ServiceCollectionsHelper.filterListOnline(groupStartNodeConfig.getNodeInstID(), rawNode->{
			BSearchNodeComConfigure searchNodeComConfigure = (BSearchNodeComConfigure) rawNode;
			return searchNodeComConfigure.getBaseNodeInstID();
		}, searchNodeConfigList, false);
		// Traverse all sub fields for start node
		List<Field> searchFields = SEUIModelFieldsHelper
				.getFieldsList(groupField.getType());
		/*
		 * [Step1] Traverse each field from search UI model
		 */
		for (Field field : searchFields) {
			BSearchFieldConfig bSearchFieldConfig = field
					.getAnnotation(BSearchFieldConfig.class);
			if (bSearchFieldConfig == null) {
				continue;
			}
			field.setAccessible(true);
			Object fieldValue = field.get(groupValue);
			if (ServiceEntityStringHelper.checkNullString(bSearchFieldConfig.subNodeInstId())) {
				// In case NO sub node inst ID, then register the field search directly to group start node.
				registerFieldSearchCore(groupStartNodeConfig, bSearchFieldConfig, fieldValue, field);
			} else {
				// In case with sub node inst ID, register the field search to different sub node.
				if(!ServiceCollectionsHelper.checkNullList(childSearchNodeConfigList)){
					BSearchNodeComConfigure subNodeConfig =
							filterSearchNodeConfigBySubNodeInstId(bSearchFieldConfig.subNodeInstId(),
									childSearchNodeConfigList);
					if(subNodeConfig != null){
						registerFieldSearchCore(subNodeConfig, bSearchFieldConfig, fieldValue, field);
					}
				}
			}
		}
	}

	private void registerFieldSearchCore(BSearchNodeComConfigure searchNodeConfig,
										 BSearchFieldConfig bSearchFieldConfig, Object searchValue, Field field){
		field.setAccessible(true);
		SEFieldSearchConfig seFieldSearchConfig = new SEFieldSearchConfig();
		seFieldSearchConfig.setFieldName(bSearchFieldConfig.fieldName());
		boolean nullValueFlag = ServiceReflectiveHelper.checkNullValue(
				searchValue, field.getType().getSimpleName());
		if (!nullValueFlag) {
			if (bSearchFieldConfig.fieldType() == ISearchFieldConfig.FIELDTYPE_EQ) {
				seFieldSearchConfig.setLowValueField(field);
				seFieldSearchConfig.setLowValue(searchValue);
			}
			if (bSearchFieldConfig.fieldType() == ISearchFieldConfig.FIELDTYPE_LOW) {
				seFieldSearchConfig
						.setOperator(SEFieldSearchConfig.OPERATOR_GREATER_EQ);
				seFieldSearchConfig.setLowValueField(field);
				seFieldSearchConfig.setLowValue(searchValue);
			}
			if (bSearchFieldConfig.fieldType() == ISearchFieldConfig.FIELDTYPE_HIGH) {
				seFieldSearchConfig
						.setOperator(SEFieldSearchConfig.OPERATOR_LESS_EQ);
				seFieldSearchConfig.setHighValueField(field);
				seFieldSearchConfig.setHighValue(searchValue);
			}
		}

		if (searchNodeConfig != null) {
			// In case find the existed same node
			if (bSearchFieldConfig.fieldType() == ISearchFieldConfig.FIELDTYPE_EQ) {
				// In case the value is for "EQ" search
				// operator, don't need to traverse each field
				if (!nullValueFlag) {
					searchNodeConfig.setFilterFlag(true);
					searchNodeConfig.getFieldConfigList().add(
							seFieldSearchConfig);
				}
			} else {
				// In case the value is for "between or equal"
				// operator, has to traverse each field and
				// merge the existed one
				if (!nullValueFlag) {
					searchNodeConfig.setNodeName(searchNodeConfig
							.getNodeName());
					searchNodeConfig.setSeName(searchNodeConfig
							.getSeName());
					searchNodeConfig.setFilterFlag(true);
					mergeNonEqualFields(searchNodeConfig,
							seFieldSearchConfig,
							bSearchFieldConfig.fieldType(), field);
				}
			}
		}
	}


	/**
	 * [Internal method] the 1st sub step of dynamic search:</P>generate the
	 * search field configuration from search UI model and fill value to node
	 * compound configuration list
	 * 
	 * @param searchNodeConfigList
	 * @param seUIComModel
	 * @throws SearchConfigureException
	 */
	public void fillSearchModuleFieldConfiguration(
			List<BSearchNodeComConfigure> searchNodeConfigList,
			SEUIComModel seUIComModel, Map<String, List<?>> multiValueMap)
			throws  SearchConfigureException {
		List<Field> searchFields = SEUIModelFieldsHelper
				.getFieldsList(seUIComModel.getClass());
		/*
		 * [Step1] Traverse each field from search UI model
		 */
		for (Field field : searchFields) {
			try {
				field.setAccessible(true);
				BSearchFieldConfig bSearchFieldConfig = field
						.getAnnotation(BSearchFieldConfig.class);
				if (bSearchFieldConfig == null) {
					// in case search group
					BSearchGroupConfig bSearchGroupConfig = field.getAnnotation(BSearchGroupConfig.class);
					if(bSearchGroupConfig != null){
						// Process all sub-fields in bSearchGroupConfig
						fillGroupFieldConfiguration(searchNodeConfigList, field, seUIComModel);
					}
					continue;
				}
				BSearchNodeComConfigure searchNodeConfig = filterSearchNodeConfigOnline(
						searchNodeConfigList, bSearchFieldConfig.nodeInstID());
				Object searchValue = field.get(seUIComModel);
				// local flag to indicate whether this field has null value,
				// will not be
				// Regarded as search criteria unit
				registerFieldSearchCore(searchNodeConfig, bSearchFieldConfig, searchValue, field);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SearchConfigureException(
						SearchConfigureException.TYPE_WRG_FIELD_NAME,
						e.getMessage());
			}
		}
		/*
		 * [Step2] Process the multi value map
		 */
		if (multiValueMap != null && !multiValueMap.isEmpty()) {
			Set<String> keySet = multiValueMap.keySet();
            for (String fieldName : keySet) {
                List<?> valueList = multiValueMap.get(fieldName);
                if (valueList == null || valueList.isEmpty()) {
                    continue;
                }
                List<Field> fieldList = filterFieldsByName(searchFields,
                        fieldName);
                if (ServiceCollectionsHelper.checkNullList(fieldList)) {
                    continue;
                }
                for (Field field : fieldList) {
                    BSearchFieldConfig iSearchConfigure = field
                            .getAnnotation(BSearchFieldConfig.class);
                    if (iSearchConfigure == null) {
                        continue;
                    }
                    SEFieldSearchConfig seFieldSearchConfig = new SEFieldSearchConfig();
                    seFieldSearchConfig.setFieldName(iSearchConfigure
                            .fieldName());
                    seFieldSearchConfig.setValueList(valueList);
                    seFieldSearchConfig.setLowValueField(field);
                    BSearchNodeComConfigure searchNodeConfig = filterSearchNodeConfigOnline(
                            searchNodeConfigList, iSearchConfigure.nodeInstID());
                    if (searchNodeConfig != null) {
                        searchNodeConfig.setFilterFlag(true);
                        searchNodeConfig.getFieldConfigList().add(
                                seFieldSearchConfig);
                    }
                }
            }
		}
	}

	/**
	 * [Internal method] Filter the SearchNodeComConfigure online
	 * 
	 * @param searchNodeConfigList
	 * @param nodeInstID
	 * @return
	 */
	protected BSearchNodeComConfigure filterSearchNodeConfigOnline(
			List<BSearchNodeComConfigure> searchNodeConfigList,
			String nodeInstID) {
		if (searchNodeConfigList == null || searchNodeConfigList.size() == 0) {
			return null;
		}
		if (ServiceEntityStringHelper.checkNullString(nodeInstID)) {
			return null;
		}
		for (BSearchNodeComConfigure bSearchNodeComConfigure : searchNodeConfigList) {
			if (nodeInstID.equals(bSearchNodeComConfigure.getNodeInstID())) {
				return bSearchNodeComConfigure;
			}
		}
		return null;
	}

	/**
	 * [Internal method] Filter the SearchNodeComConfigure online
	 *
	 * @param searchNodeConfigList
	 * @param subNodeInstId
	 * @return
	 */
	protected BSearchNodeComConfigure filterSearchNodeConfigBySubNodeInstId(
			String subNodeInstId, List<BSearchNodeComConfigure> searchNodeConfigList) {
		List<BSearchNodeComConfigure> tempList =
				ServiceCollectionsHelper.filterListOnline(searchNodeConfigList,  rawConfigure->{
					BSearchNodeComConfigure searchNodeComConfigure = rawConfigure;
			return (subNodeInstId.equals(searchNodeComConfigure.getSubNodeInstId()));
		}, true);
		if(!ServiceCollectionsHelper.checkNullList(tempList)){
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * [Internal method] field search field lists by fields name, sometimes,
	 * could be multiple fields by field names
	 * 
	 * @param searchFields
	 * @param fieldName
	 * @return
	 */
	protected List<Field> filterFieldsByName(List<Field> searchFields,
			String fieldName) {
		if (searchFields == null || searchFields.size() == 0) {
			return null;
		}
		List<Field> result = new ArrayList<>();
		for (Field field : searchFields) {
			field.setAccessible(true);
			if (field.getName().equals(fieldName)) {
				result.add(field);
			}
		}
		return result;
	}

	/**
	 * Get start node batch list, only exist when there are multiple table for
	 * start node
	 */
	protected List<BSearchNodeComConfigure> getStartNodeBatch(
			List<BSearchNodeComConfigure> searchNodeCofigList)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> resultList = new ArrayList<>();
		for (BSearchNodeComConfigure tmpConfig : searchNodeCofigList) {
			if (tmpConfig.isStartNodeFlag()) {
				resultList.add(tmpConfig);
			}
		}
		return resultList;
	}

	protected BSearchNodeComConfigure getStartNode(
			List<BSearchNodeComConfigure> searchNodeCofigList)
			throws SearchConfigureException {
		for (BSearchNodeComConfigure tmpConfig : searchNodeCofigList) {
			if (tmpConfig.isStartNodeFlag()) {
				return tmpConfig;
			}
		}
		throw new SearchConfigureException(
				SearchConfigureException.TYPE_NO_START_NODE);
	}

	protected BSearchNodeComConfigure getStartNodeFromCurNode(
			BSearchNodeComConfigure curComConfigure,
			List<BSearchNodeComConfigure> searchNodeCofigList)
			throws SearchConfigureException {
		if (curComConfigure.isStartNodeFlag()) {
			return curComConfigure;
		} else {
			BSearchNodeComConfigure baseNodeComConfigure = getBaseSearchNode(
					curComConfigure, searchNodeCofigList);
			return getStartNodeFromCurNode(baseNodeComConfigure,
					searchNodeCofigList);
		}
	}

	/**
	 * [Internal method] to be invoked by, if possible, to merge two fields with
	 * non-equal type operator, during the merge process,
	 * <code>High value</code> or <code>Low value</code> will be set, and
	 * operator will be decided by the high value and low value
	 * 
	 * @param searchNodeConfig
	 * @param seFieldSearchConfig
	 * @param fieldType
	 * @param field
	 */
	protected void mergeNonEqualFields(
			BSearchNodeComConfigure searchNodeConfig,
			SEFieldSearchConfig seFieldSearchConfig, int fieldType, Field field) {
		// [Bug] don't know why this filter exist
		// if (searchInfo.getFieldConfigList().size() <= 0)
		// return;
		boolean fieldMatchFlag = false;
		for (SEFieldSearchConfig tmpSearchConfig : searchNodeConfig
				.getFieldConfigList()) {
			if (tmpSearchConfig.getFieldName().equals(
					seFieldSearchConfig.getFieldName())) {
				if (fieldType == ISearchFieldConfig.FIELDTYPE_LOW) {
					tmpSearchConfig.setLowValueField(field);
					tmpSearchConfig.setLowValue(seFieldSearchConfig
							.getLowValue());
					// In case the field type is low and no high value assigned,
					// then regard
					// compare type as "Greater or Equal" or else set as
					// "Between"
					if (tmpSearchConfig.getHighValue() == null) {
						tmpSearchConfig
								.setOperator(SEFieldSearchConfig.OPERATOR_GREATER_EQ);
					} else {
						tmpSearchConfig
								.setOperator(SEFieldSearchConfig.OPERATOR_BETWEEN);
					}
				}
				if (fieldType == ISearchFieldConfig.FIELDTYPE_HIGH) {
					tmpSearchConfig.setHighValueField(field);
					tmpSearchConfig.setHighValue(seFieldSearchConfig
							.getHighValue());
					// In case the field type is high and no low value assigned,
					// then regard
					// compare type as "Less or Equal" or else set as
					// "Between"
					if (tmpSearchConfig.getLowValue() == null) {
						tmpSearchConfig
								.setOperator(SEFieldSearchConfig.OPERATOR_LESS_EQ);
					} else {
						tmpSearchConfig
								.setOperator(SEFieldSearchConfig.OPERATOR_BETWEEN);
					}
				}
				fieldMatchFlag = true;
				break;
			}
		}
		if (!fieldMatchFlag) {
			searchNodeConfig.getFieldConfigList().add(seFieldSearchConfig);
		}
	}

	/**
	 * [Internal] Generate the SQL command header part by table, var, precondition and client info
	 * @param searchNodeComConfigure
	 * @param client
	 * @return
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 */
	private SQLStatementHeader generateRawSQLHeader(BSearchNodeComConfigure searchNodeComConfigure,
													String client ) throws NodeNotFoundException,
			ServiceEntityConfigureException {
		// if the table name has not been configured manually, then it could be
		// generated by se name and node name
		String tableName = ServiceEntityStringHelper.EMPTYSTRING;
		if (searchNodeComConfigure.getTablename() == null
				|| searchNodeComConfigure.getTablename().equals(
				ServiceEntityStringHelper.EMPTYSTRING)) {
			tableName = getTableName(searchNodeComConfigure.getSeName(),
					searchNodeComConfigure.getNodeName());
			if (ServiceEntityStringHelper.checkNullString(tableName)) {
				// In case table name is null, return directly
				return null;
			}
		} else {
			tableName = searchNodeComConfigure.getTablename();
		}
		String varName = getNodeVarName(searchNodeComConfigure);
		String statement = " from " + tableName + " " + varName;

		boolean preConditionFlag = false;
		// check the pre-condition
		if (searchNodeComConfigure.getPreConditions() != null
				&& searchNodeComConfigure.getPreConditions().size() > 0) {
			// if there's precondition, if "filter flag" should be set, should
			// depend on sub
			preConditionFlag = true;
			// When Setting precondition, always use strict = true;
			statement = ServiceEntityStringHelper.mergeStrWhenNotNull(statement,
					BsearchService.generateEachPreConditionList(
							searchNodeComConfigure.getPreConditions(), varName, true));
		}
		// Special handling for client
		if (!ServiceEntityStringHelper.checkNullString(client)) {
			SearchConfigPreCondition clientCondition = new SearchConfigPreCondition();
			searchNodeComConfigure.setFilterFlag(true);
			clientCondition
					.setFieldName(IServiceEntityNodeFieldConstant.CLIENT);
			clientCondition.setFieldValue(client);
			if (preConditionFlag) {
				// In case there is already pre conditions, using "and" in front
				// of real conditions
				statement = statement + " and "
						+ generateEachPreCondition(clientCondition, varName, true);
			} else {
				statement = statement + " Where "
						+ generateEachPreCondition(clientCondition, varName, true);

			}
			preConditionFlag = true;
		}
		return new SQLStatementHeader(statement, preConditionFlag, tableName, varName);
	}

	private class SQLStatementHeader{

		private String sqlStatement;

		private boolean preConditionFlag;

		private String tableName;

		private String varName;

		public SQLStatementHeader() {
		}

		public SQLStatementHeader(String sqlStatement, boolean preConditionFlag, String tableName, String varName) {
			this.sqlStatement = sqlStatement;
			this.preConditionFlag = preConditionFlag;
			this.tableName = tableName;
			this.varName = varName;
		}

		public String getSqlStatement() {
			return sqlStatement;
		}

		public void setSqlStatement(String sqlStatement) {
			this.sqlStatement = sqlStatement;
		}

		public boolean getPreConditionFlag() {
			return preConditionFlag;
		}

		public void setPreConditionFlag(boolean preConditionFlag) {
			this.preConditionFlag = preConditionFlag;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getVarName() {
			return varName;
		}

		public void setVarName(String varName) {
			this.varName = varName;
		}
	}

	
	/**
	 * [Internal method]generate SQL statement by each SE node search info, and
	 * set to search info, if there's no search condition, the
	 * <code>filterFlag</code> of search Info is also set
	 * 
	 * @param searchNodeComConfigure
	 * @return
	 * @throws NodeNotFoundException
	 * @throws ServiceEntityConfigureException
	 */
	private void setRawSQLStatement(
			BSearchNodeComConfigure searchNodeComConfigure, boolean fuzzyFlag,
			String client) throws ServiceEntityConfigureException, NodeNotFoundException {
		SQLStatementHeader sqlStatementHeader = generateRawSQLHeader(searchNodeComConfigure, client);
		boolean preConditionFlag = sqlStatementHeader.getPreConditionFlag();
		String statement = sqlStatementHeader.getSqlStatement();
		String varName = sqlStatementHeader.getVarName();
		if (searchNodeComConfigure.getFieldConfigList() != null
				&& !searchNodeComConfigure.getFieldConfigList().isEmpty()) {
			SEFieldSearchConfig firstConfig = searchNodeComConfigure
					.getFieldConfigList().get(0);
			if (preConditionFlag) {
				// In case there is already pre-conditions, using "and" in front
				// of real conditions
				statement = statement
						+ " and "
						+ generateSearchConditionEachField(firstConfig,
						varName, fuzzyFlag);
			} else {
				statement = statement
						+ " Where "
						+ generateSearchConditionEachField(firstConfig,
						varName, fuzzyFlag);
			}
			if (searchNodeComConfigure.getFieldConfigList().size() > 1) {
				for (int i = 1; i < searchNodeComConfigure.getFieldConfigList()
						.size(); i++) {
					String subStatment = generateSearchConditionEachField(
							searchNodeComConfigure.getFieldConfigList().get(i),
							varName, fuzzyFlag);
					if (subStatment == null
							|| subStatment
									.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
						// do nothing
					} else {
						statement = statement + " and " + subStatment;
					}
				}
			}
		} else {

		}
		searchNodeComConfigure.setRawSearchCommand(statement);
	}

	/**
	 * [Internal method] this method should be invoked should after the raw SQL
	 * statement is generated from
	 * 
	 * @param traceList
	 * @return
	 * @throws SearchConfigureException
	 */
	public List<String> generateFinalSQLStatement0(
			List<BSearchNodeComConfigure> traceList,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		// [Special case handling for only one start node, then traceList is
		// empty: just return the raw search command
		if (searchNodeConfigList.size() == 1) {
			List<String> commandList = new ArrayList<>();
			String rawCommand = searchNodeConfigList.get(0)
					.getRawSearchCommand();
			commandList.add(rawCommand);
			return commandList;
		}
		// navigate each search node configure and generate the mapping
		// relationship
		for (BSearchNodeComConfigure nodeConfigure : searchNodeConfigList) {
			// In case current node doesn't map to base node by specified field
			processMapField(nodeConfigure);
		}

		List<String> commandList = new ArrayList<>();
		for (BSearchNodeComConfigure nodeConfigure : traceList) {
			// Process each trace, form the last node to start node
			BSearchNodeComConfigure curNodeConfigure = nodeConfigure;
			String sqlCommand = nodeConfigure.getRawSearchCommand();
			// variable indicate whether to start generate SQL command from this
			// node
			boolean startTraceFlag = false;
			if (curNodeConfigure.isStartNodeFlag()) {
				// Exceptional case: if current node is already start node: only
				// one node in this trace
				commandList.add(sqlCommand);
				continue;
			}
			// Normal case: more than 1 node in one trace: process from last
			// node to start node
			while (!curNodeConfigure.isStartNodeFlag()
					&& curNodeConfigure != null) {
				if (curNodeConfigure.isFilterFlag()) {
					// If current node has true filter flag, then start from
					// this node
					startTraceFlag = true;
				}
				BSearchNodeComConfigure baseNodeConfig = getBaseSearchNode(
						curNodeConfigure, searchNodeConfigList);
				if (!baseNodeConfig.isStartNodeFlag()) {
					if (startTraceFlag) {
						// If this node has the "filter flag", then all the base
						// node are with filter flag
						sqlCommand = this.refreshSQLStatement(curNodeConfigure,
								baseNodeConfig, sqlCommand);
					}
				} else {
					// In case base node already a start node
					sqlCommand = this.refreshSQLStatement(curNodeConfigure,
							baseNodeConfig, sqlCommand);
					commandList.add(sqlCommand);
				}
				curNodeConfigure = baseNodeConfig;
			}
		}
		return commandList;
	}
	
	/**
	 * [Internal method] Core Logic to set the map source field name according the different to base node types
	 * @param nodeConfigure
	 */
	private void processMapField(BSearchNodeComConfigure nodeConfigure){
		if(ServiceEntityStringHelper.checkNullString(nodeConfigure.getMapSourceFieldName())){
			// generate the default mapping field pairs
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_TO_ROOT) {
				nodeConfigure
						.setMapSourceFieldName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
				nodeConfigure
						.setMapBaseFieldName(IServiceEntityNodeFieldConstant.UUID);
			}
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_TO_PARENT) {
				nodeConfigure
						.setMapSourceFieldName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
				nodeConfigure
						.setMapBaseFieldName(IServiceEntityNodeFieldConstant.UUID);
			}
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_TO_CHILD) {
				nodeConfigure
						.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
				nodeConfigure
						.setMapBaseFieldName(IServiceEntityNodeFieldConstant.PARENTNODEUUID);
			}
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_FROM_ROOT) {
				nodeConfigure
						.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
				nodeConfigure
						.setMapBaseFieldName(IServiceEntityNodeFieldConstant.ROOTNODEUUID);
			}
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_REFTO_SOURCE) {
				nodeConfigure
						.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
				nodeConfigure
						.setMapBaseFieldName(IReferenceNodeFieldConstant.REFUUID);
			}
			if (nodeConfigure.getToBaseNodeType() == SearchNodeMapping.TOBASENODE_REFTO_TARGET) {
				nodeConfigure
						.setMapSourceFieldName(IReferenceNodeFieldConstant.REFUUID);
				nodeConfigure
						.setMapBaseFieldName(IServiceEntityNodeFieldConstant.UUID);
			}
		}
	}

	/**
	 * [Internal method] Generate the final SQL command for execution search by traceList.
	 * 
	 * @param traceList
	 * @return
	 * @throws SearchConfigureException
	 */
	private String generateFinalSQLStatement(
			List<BSearchNodeComConfigure> traceList,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		// [Special case handling for only one start node, then traceList is
		// empty: just return the raw search command
		if (searchNodeConfigList.size() == 1) {
            return searchNodeConfigList.get(0)
					.getRawSearchCommand();
		}
		// navigate each search node configure and generate the mapping
		// relationship
		for (BSearchNodeComConfigure nodeConfigure : searchNodeConfigList) {
			// In case current node doesn't map to base node by specified field
			processMapField(nodeConfigure);
		}
		// Fill in the connector nodes for each trace List to start node
		fillConnectNodeToTraceList(traceList, searchNodeConfigList);
		BSearchNodeComConfigure startNodeConfigure = this
				.getStartNode(traceList);
        return refreshSQLCommand(startNodeConfigure, traceList);
	}

	/**
	 * [Internal method] Fill the connector node to trace list,
	 * this method is for post-processing the trace list before generate the final SQL command
	 * @param traceList
	 * @param searchNodeConfigList
	 * @return
	 * @throws SearchConfigureException
	 */
	@SuppressWarnings("unchecked")
	private List<BSearchNodeComConfigure> fillConnectNodeToTraceList(
			List<BSearchNodeComConfigure> traceList,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		if (!ServiceCollectionsHelper.checkNullList(traceList)) {
			List<BSearchNodeComConfigure> connectorList  = new ArrayList<>();
			for (BSearchNodeComConfigure traceNodeConfigure : traceList) {
				if (traceNodeConfigure.isStartNodeFlag()) {
					continue;
				}
				BSearchNodeComConfigure parentComConfigure = this
						.getBaseSearchNode(traceNodeConfigure,
								searchNodeConfigList);
				fillParentNodeToTraceListRecursive(parentComConfigure, connectorList, searchNodeConfigList);
			}
			if(!ServiceCollectionsHelper.checkNullList(connectorList)){
				traceList.addAll(connectorList);
			}
			traceList = (List<BSearchNodeComConfigure>) ServiceCollectionsHelper.removeDuplicatesList(traceList);
			return traceList;
		}
		return null;
	}

	/**
	 * [Internal method] Fill the parent node to trace list recursively, until the start node
	 * @param curComConfigure
	 * @param connectorList
	 * @param searchNodeConfigList
	 * @throws SearchConfigureException
	 */
	private void fillParentNodeToTraceListRecursive(
			BSearchNodeComConfigure curComConfigure,
			List<BSearchNodeComConfigure> connectorList,
			List<BSearchNodeComConfigure> searchNodeConfigList) throws SearchConfigureException{
		if(ServiceCollectionsHelper.checkNullList(searchNodeConfigList)){
			return;
		}
		if(connectorList.contains(curComConfigure)) {
			return;
		}
		connectorList.add(curComConfigure);
		if (curComConfigure.isStartNodeFlag()) {
			return;
		}
		BSearchNodeComConfigure parentComConfigure = this
				.getBaseSearchNode(curComConfigure,
						searchNodeConfigList);
		fillParentNodeToTraceListRecursive(parentComConfigure, connectorList, searchNodeConfigList);
	}

	private String refreshSQLCommand(
			BSearchNodeComConfigure searchNodeConfigure,
			List<BSearchNodeComConfigure> traceList)
			throws SearchConfigureException {
		String sqlCommand = "";
        StringBuilder finalCommand = new StringBuilder();
        List<BSearchNodeComConfigure> childNodeConfigureList = this
				.getNextSearchConfigureNodeList(searchNodeConfigure,
						traceList);
		if (!ServiceCollectionsHelper.checkNullList(childNodeConfigureList)) {
			for (BSearchNodeComConfigure childNodeConfigure : childNodeConfigureList) {
				// Process each trace, form the last node to start node
				sqlCommand = refreshSQLStatementRecursive(childNodeConfigure,
						searchNodeConfigure, traceList);
				finalCommand.append(sqlCommand);
			}
		}
		finalCommand.insert(0, searchNodeConfigure.getRawSearchCommand());
		return finalCommand.toString();
	}

	protected String refreshSQLStatementRecursive(
			BSearchNodeComConfigure curNodeConfig,
			BSearchNodeComConfigure baseNodeConfig,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		StringBuilder command = new StringBuilder();
		// In case base node has pre-conditions, still have to connect with 'and'
		if (baseNodeConfig.isFilterFlag() ||!ServiceCollectionsHelper.checkNullList(baseNodeConfig.getPreConditions())) {
			command.append(" and ");
		} else {
			command.append(" where ");
		}
		String baseNodeVar = getNodeVarName(baseNodeConfig);
		command.append(baseNodeVar).append(".").append(curNodeConfig.getMapBaseFieldName()).append(" in ( select ").append(curNodeConfig.getMapSourceFieldName())
				.append(" ").append(curNodeConfig.getRawSearchCommand());
		List<BSearchNodeComConfigure> childNodeConfigureList = this
				.getNextSearchConfigureNodeList(curNodeConfig,
						searchNodeConfigList);
		if (!ServiceCollectionsHelper.checkNullList(childNodeConfigureList)) {
			if (!ServiceCollectionsHelper.checkNullList(childNodeConfigureList)) {
				for (BSearchNodeComConfigure childNodeConfigure : childNodeConfigureList) {
					command.append(" ").append(refreshSQLStatementRecursive(childNodeConfigure,
                            curNodeConfig, searchNodeConfigList));
				}
			}
		}
		command.append(")");
		return command.toString();
	}

	protected String refreshSQLStatement(BSearchNodeComConfigure curNodeConfig,
			BSearchNodeComConfigure baseNodeConfig, String rawCommand) {
		String command = baseNodeConfig.getRawSearchCommand();
		// In case base node has pre conditions, still have to connect with 'and'
		if (baseNodeConfig.isFilterFlag() ||!ServiceCollectionsHelper.checkNullList(baseNodeConfig.getPreConditions())) {
			command = command + " and ";
		} else {
			command = command + " where ";
		}
		command = command + curNodeConfig.getMapBaseFieldName()
				+ " in ( select " + curNodeConfig.getMapSourceFieldName() + " "
				+ rawCommand + ")";
		return command;
	}

	public List<BSearchNodeComConfigure> generateTraceList(
			List<BSearchNodeComConfigure> searchNodeComConfigList)
			throws SearchConfigureException {
		// find the last node list
		List<BSearchNodeComConfigure> traceMappings = new ArrayList<>();
		// merge the two configurations:moving the previous config:searchConfig
		// to SearchNodeMapping
		for (BSearchNodeComConfigure searchNodeConfig : searchNodeComConfigList) {
			if (checkLastSearchNode(searchNodeConfig, searchNodeComConfigList)) {
				traceMappings.add(searchNodeConfig);
			}
		}
		return traceMappings;
	}

	/**
	 * Refresh the trace list again, make sure the last node is with "true"
	 * filter flag, make sure each trace can start from the really valuable
	 * "last node"
	 * 
	 * @param traceList
	 * @throws SearchConfigureException
	 */
	@SuppressWarnings("unchecked")
	public List<BSearchNodeComConfigure> refreshValuableTraceList(
			List<BSearchNodeComConfigure> traceList,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> refreshedTraceList = new ArrayList<>();
		BSearchNodeComConfigure startNodeConfigure = this
				.getStartNode(searchNodeConfigList);
		for (BSearchNodeComConfigure trace : traceList) {
			BSearchNodeComConfigure curNodeConfigure = trace;
			// In case the trace node is the start node, then add to refreshed trace list directly.
			if (curNodeConfigure.isStartNodeFlag()) {
				refreshedTraceList.add(curNodeConfigure);
				continue;
			}
			if (curNodeConfigure.isFilterFlag()) {
				refreshedTraceList.add(curNodeConfigure);
				continue;
			}
			// Using this while loop to navigate trace node to the base node, if there is no filter flag.
			boolean onlyStartNodeFlag = true;
			while (curNodeConfigure != null
					&& !curNodeConfigure.isStartNodeFlag()) {
				if (curNodeConfigure.isFilterFlag()) {
					refreshedTraceList.add(curNodeConfigure);
					onlyStartNodeFlag = false;
					break;
				} else {
					// if not then navigate to base search node
					curNodeConfigure = getBaseSearchNode(curNodeConfigure,
							searchNodeConfigList);
				}
			}
			if (onlyStartNodeFlag) {
				// In case all the node inside the trace has not filter, then
				// have to add only one start node
				if (refreshedTraceList.contains(startNodeConfigure)) {
					continue;
				}
				refreshedTraceList.add(this.getStartNodeFromCurNode(
						curNodeConfigure, searchNodeConfigList));
			}
		}
		// Remove duplicate in list
		refreshedTraceList = (List<BSearchNodeComConfigure>) ServiceCollectionsHelper.removeDuplicatesList(refreshedTraceList);
		return refreshedTraceList;
	}
	
	/**
	 * [Internal method] check weather this search node is the last node, which means
	 * * no other node in the searchNodeList regards this one as a base node, or is it a start node.
	 * 
	 * @param searchNodeConfig
	 * @param searchNodeConfigList
	 * @return
	 * @throws SearchConfigureException
	 */
	private boolean checkLastSearchNode(
			BSearchNodeComConfigure searchNodeConfig,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		if (searchNodeConfig.isStartNodeFlag()) {
			// return false for start node
			return false;
		}
		// Try to find any node regard this node as base node.
		for (BSearchNodeComConfigure nodeConfig : searchNodeConfigList) {
			if (nodeConfig.isStartNodeFlag()) {
				continue;
			}
			if (ServiceEntityStringHelper.checkNullString(nodeConfig.getBaseNodeInstID())) {
				throw new SearchConfigureException(
						SearchConfigureException.TYPE_NO_BASE_NODE,
						nodeConfig.getSeName(), nodeConfig.getNodeName());
			}
			if (nodeConfig.getBaseNodeInstID().equals(
					searchNodeConfig.getNodeInstID())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * [Internal method] Get list of NEXT search configure node, which base node
	 * point to this configure node
	 * 
	 * @param searchNodeConfigList
	 * @return
	 * @throws SearchConfigureException
	 */
	protected List<BSearchNodeComConfigure> getNextSearchConfigureNodeList(
			BSearchNodeComConfigure searchNodeConfig,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		if (checkLastSearchNode(searchNodeConfig, searchNodeConfigList)) {
			return null;
		}
		List<BSearchNodeComConfigure> nextNodeList = new ArrayList<>();
		for (BSearchNodeComConfigure nodeConfig : searchNodeConfigList) {
			if (ServiceEntityStringHelper.checkNullString(nodeConfig.getBaseNodeInstID())) {
				continue;
			}
			if (nodeConfig.getBaseNodeInstID().equals(
					searchNodeConfig.getNodeInstID())) {
				nextNodeList.add(nodeConfig);
			}
		}
		return nextNodeList;
	}

	protected boolean checkMultiTablesFlag(
			List<BSearchNodeComConfigure> overallSearchNodeList)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> multiTableNodeList = new ArrayList<BSearchNodeComConfigure>();
		for (BSearchNodeComConfigure nodeConfigure : overallSearchNodeList) {
			if (nodeConfigure.getTableNameList() != null
					&& nodeConfigure.getTableNameList().size() > 1) {
				// In case a multi-table node
				multiTableNodeList.add(nodeConfigure);
				// Check if the duplicate table name
				checkDuplicateTableNameInOneNode(nodeConfigure);
			}
		}
        return multiTableNodeList.size() > 0;
	}

	/**
	 * [Internal method] For process multi-table node, check if duplicate same
	 * table name set for one node
	 * 
	 * @param searchNodeComConfigure
	 * @throws SearchConfigureException
	 */
	protected void checkDuplicateTableNameInOneNode(
			BSearchNodeComConfigure searchNodeComConfigure)
			throws SearchConfigureException {
		List<String> tableNameList = searchNodeComConfigure.getTableNameList();
		if (tableNameList == null || tableNameList.size() <= 1) {
			return;
		}
		for (int i = 0; i < tableNameList.size(); i++) {
			for (int j = i + 1; j < tableNameList.size(); j++) {
				if (tableNameList.get(i).equals(tableNameList.get(j))) {
					throw new SearchConfigureException(
							SearchConfigureException.TYPE_DUP_TABLENAME,
							searchNodeComConfigure.getSeName(),
							searchNodeComConfigure.getNodeName());
				}
			}
		}
	}

	public void processMultipleTableFromTraceUnion(
			BSearchNodeComConfigure searchNodeComConfigure,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		if (searchNodeComConfigure.getTableNameList() != null
				&& searchNodeComConfigure.getTableNameList().size() > 0) {
			// split this node and batch
			List<BSearchNodeComConfigure> newAddedSearchNodeList = splitSearchConfigNode(
					searchNodeComConfigure, searchNodeConfigList);
			searchNodeConfigList.addAll(newAddedSearchNodeList);
		}
		if (searchNodeComConfigure.isStartNodeFlag()) {
			return;
		}
		BSearchNodeComConfigure baseNodeComConfigure = getBaseSearchNode(
				searchNodeComConfigure, searchNodeConfigList);
		processMultipleTableFromTraceUnion(baseNodeComConfigure,
				searchNodeConfigList);
	}

	/**
	 * [Internal method] In node split function for process multi-table node,
	 * split and copy this node by each table name, also split and copy for next
	 * node list
	 * 
	 * @param searchNodeComConfigure
	 * @return
	 * @throws SearchConfigureException
	 */
	protected List<BSearchNodeComConfigure> splitSearchConfigNode(
			BSearchNodeComConfigure searchNodeComConfigure,
			List<BSearchNodeComConfigure> overallSearchNodeList)
			throws SearchConfigureException {
		List<BSearchNodeComConfigure> newAddedSearchNodeList = new ArrayList<BSearchNodeComConfigure>();
		List<String> tableNameList = searchNodeComConfigure.getTableNameList();
		// Set split flag to true
		searchNodeComConfigure.setTableSplitFlag(true);
		// Set table name for its self
		if (ServiceEntityStringHelper.checkNullString(searchNodeComConfigure.getTablename())) {
			searchNodeComConfigure.setTablename(tableNameList.get(0));
		}
		for (int i = 1; i < tableNameList.size(); i++) {
			String tableName = tableNameList.get(i);
			// Copy new search configure node
			BSearchNodeComConfigure newSearchNode = (BSearchNodeComConfigure) searchNodeComConfigure
					.clone();
			// For new split node, also point to same base node
			newSearchNode.setBaseNodeInstID(searchNodeComConfigure
					.getBaseNodeInstID());
			// Set new node inst id
			String newInstID = refreshNodeInstIDByTableName(
					searchNodeComConfigure.getNodeInstID(), tableName);
			newSearchNode.setNodeInstID(newInstID);
			newSearchNode.setTablename(tableName);
			newAddedSearchNodeList.add(newSearchNode);
			// split and copy the next node list
			if (!checkLastSearchNode(searchNodeComConfigure,
					overallSearchNodeList)) {
				// Get all next node list
				List<BSearchNodeComConfigure> nextNodeList = getNextSearchConfigureNodeList(
						searchNodeComConfigure, overallSearchNodeList);
				for (BSearchNodeComConfigure tempNextNode : nextNodeList) {
					this.cloneSearchConfigNode(tempNextNode,
							newSearchNode.getNodeInstID(), tableName,
							newAddedSearchNodeList, overallSearchNodeList);
				}
			}
		}
		return newAddedSearchNodeList;
	}

	/**
	 * [Internal method]In node split function for process multi-table node,
	 * split and copy this each search configure node, if base node is
	 * multi-table node
	 * 
	 * @param searchConfigureNode
	 * @param baseNodeInstID
	 * @param subTableName
	 * @param newAddedSearchNodeList
	 * @param overallSearchNodeList
	 * @throws SearchConfigureException
	 */
	protected void cloneSearchConfigNode(
			BSearchNodeComConfigure searchConfigureNode, String baseNodeInstID,
			String subTableName,
			List<BSearchNodeComConfigure> newAddedSearchNodeList,
			List<BSearchNodeComConfigure> overallSearchNodeList)
			throws SearchConfigureException {
		BSearchNodeComConfigure newBSearchNodeComConfigure = (BSearchNodeComConfigure) searchConfigureNode
				.clone();
		newBSearchNodeComConfigure.setBaseNodeInstID(baseNodeInstID);
		newBSearchNodeComConfigure.setNodeInstID(refreshNodeInstIDByTableName(
				newBSearchNodeComConfigure.getNodeInstID(), subTableName));
		newAddedSearchNodeList.add(newBSearchNodeComConfigure);
		// In case this node is not the last node then call recursively
		if (!checkLastSearchNode(searchConfigureNode, overallSearchNodeList)) {
			// Get all next node list
			List<BSearchNodeComConfigure> nextNodeList = getNextSearchConfigureNodeList(
					searchConfigureNode, overallSearchNodeList);
			for (BSearchNodeComConfigure tempNextNode : nextNodeList) {
				this.cloneSearchConfigNode(tempNextNode, baseNodeInstID,
						subTableName, newAddedSearchNodeList,
						overallSearchNodeList);
			}
		}
	}

	/**
	 * [Internal method] The logic to generate new NodeInstID by table name This
	 * method is usually invoked by multi table list split node function
	 * 
	 * @param oldNodeInstID
	 * @param tableName
	 * @return
	 */
	protected String refreshNodeInstIDByTableName(String oldNodeInstID,
			String tableName) {
		String randomPrefix = ServiceEntityStringHelper.getRandomString(4);
		String newInstID = ServiceEntityStringHelper
				.headerToLowerCase(oldNodeInstID)
				+ ServiceEntityStringHelper.headerToUpperCase(randomPrefix)
				+ ServiceEntityStringHelper.headerToUpperCase(tableName);
		return newInstID;
	}

	/**
	 * [Internal method] check wether this search node is the last node, means
	 * no other node in searchNodeList regard this one as base node
	 * 
	 * @param searchNodeConfig
	 * @param searchNodeConfigList
	 * @return
	 * @throws SearchConfigureException
	 */
	private BSearchNodeComConfigure getBaseSearchNode(
			BSearchNodeComConfigure searchNodeConfig,
			List<BSearchNodeComConfigure> searchNodeConfigList)
			throws SearchConfigureException {
		if (searchNodeConfig.isStartNodeFlag()) {
			return null;
		}
		for (BSearchNodeComConfigure tmpNodeConfig : searchNodeConfigList) {
			if (searchNodeConfig.getBaseNodeInstID().equals(
					tmpNodeConfig.getNodeInstID())) {
				return tmpNodeConfig;
			}
		}
		throw new SearchConfigureException(
				SearchConfigureException.TYPE_NO_BASE_NODE,
				searchNodeConfig.getSeName(), searchNodeConfig.getNodeName());
	}

	/**
	 * Check if current Node make filter effect in search, this method should be
	 * invoked after the search action is triggered.
	 * 
	 * @param seFieldSearchInfo
	 * @param nodeName
	 * @param seName
	 * @return
	 */
	public boolean checkNodeFilterExist(
			List<SENodeFieldSearchInfo> seFieldSearchInfo, String nodeName,
			String seName) {
		for (SENodeFieldSearchInfo nodeConfig : seFieldSearchInfo) {
			if (nodeConfig.getSeName().equals(seName)
					&& nodeConfig.getNodeName().equals(nodeName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the search result by Node name
	 * 
	 * @param seFieldSearchInfos
	 * @param nodeName
	 * @param seName
	 * @return
	 */
	public List<ServiceEntityNode> getSearchResult(
			List<SENodeFieldSearchInfo> seFieldSearchInfos, String nodeName,
			String seName) {
		for (SENodeFieldSearchInfo searchInfo : seFieldSearchInfos) {
			if (searchInfo.getSeName().equals(seName)
					&& searchInfo.getNodeName().equals(nodeName)) {
				return searchInfo.getResult();
			}
		}
		return null;
	}

	/**
	 * Get all the search result from table
	 * 
	 * @param seName
	 * @param nodeName
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	protected List<ServiceEntityNode> getAllSearchResult(String seName,
			String nodeName) throws ServiceEntityConfigureException,
			NodeNotFoundException, SearchConfigureException {
		String tableName = getTableName(seName, nodeName);
		String varName = ServiceEntityStringHelper.headerToLowerCase(tableName);
		String commandString = "from " + tableName + " " + varName;
		List<ServiceEntityNode> seResultList = hibernateDefaultImpDAO
				.getEntityNodeListBySQLCommand(commandString);
		return seResultList;
	}
	
	public static String getNodeVarName(BSearchNodeComConfigure bSearchNodeComConfigure){
		return ServiceEntityStringHelper.headerToLowerCase(bSearchNodeComConfigure.getNodeInstID());
	}

	/**
	 * Get relative table name by SE name and node name
	 * 
	 * @param seName
	 * @param nodeName
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws NodeNotFoundException
	 */
	public static String getTableName(String seName, String nodeName)
			throws ServiceEntityConfigureException, NodeNotFoundException {
		return ServiceEntityStringHelper.getDefModelId(seName, nodeName);
	}

	public static String generateEachPreConditionList(
			List<SearchConfigPreCondition> preConditionList, String varName, boolean strictFlag) {
		if (ServiceCollectionsHelper.checkNullList(preConditionList)) {
			return null;
		}
		String statement = " Where ";
		String coreStatement = ServiceEntityStringHelper.EMPTYSTRING;
		if(ServiceCollectionsHelper.checkNullList(preConditionList)){
			return null;
		}
		List<SQLStatementWithLogicOperator> sqlUnitList = new ArrayList<>();
		for(SearchConfigPreCondition preCondition: preConditionList){
			String curStatement = generateEachPreCondition(preCondition,
					varName, strictFlag);
			if(ServiceEntityStringHelper.checkNullString(curStatement)){
				continue;
			}
			sqlUnitList.add(new SQLStatementWithLogicOperator(curStatement, preCondition.getOperator()));
		}
		if(ServiceCollectionsHelper.checkNullList(sqlUnitList)){
			return null;
		}
		// !! Important !! bracing the precondition in SQL statement
		statement = statement + "(";
		for (int i = 0; i < sqlUnitList.size() - 1; i++) {
			String curStatement = sqlUnitList.get(i).getSqlCommand();
			if (sqlUnitList.get(i).getLogicOperator() == SEFieldSearchConfig.LOGIC_OPERATOR_OR) {
				coreStatement = coreStatement
						+ curStatement + " or ";
			} else {
				coreStatement = coreStatement
						+ curStatement + " and ";
			}
		}
		String curStatement = sqlUnitList.get(sqlUnitList.size() - 1).getSqlCommand();
		if(!ServiceEntityStringHelper.checkNullString(curStatement)){
			coreStatement = coreStatement
					+ curStatement;
		}
		if (ServiceEntityStringHelper.checkNullString(coreStatement)) {
			return null;
		}
		statement = statement + coreStatement + ")";
		return statement;
	}

	private static class SQLStatementWithLogicOperator{

		private String sqlCommand;

		private int logicOperator;

		public SQLStatementWithLogicOperator(String sqlCommand, int logicOperator) {
			this.sqlCommand = sqlCommand;
			this.logicOperator = logicOperator;
		}

		public String getSqlCommand() {
			return sqlCommand;
		}

		public void setSqlCommand(String sqlCommand) {
			this.sqlCommand = sqlCommand;
		}

		public int getLogicOperator() {
			return logicOperator;
		}

		public void setLogicOperator(int logicOperator) {
			this.logicOperator = logicOperator;
		}
	}


	public static String generateEachPreCondition(
			SearchConfigPreCondition preCondition, String varName, boolean strictFlag) {
		if(!ServiceCollectionsHelper.checkNullList(preCondition.getMultipleValueList())){
			return "(" + generateEachPreConditionWithMuliValue(preCondition, varName, strictFlag) + ")";
		} else {
			return generateEachPreConditionSingleField(preCondition, varName, strictFlag);
		}
	}

	protected static String generateEachPreConditionWithMuliValue(
			SearchConfigPreCondition preCondition, String varName, boolean strictFlag) {
		if (ServiceCollectionsHelper.checkNullList(preCondition.getMultipleValueList())){
			return null;
		}
		String statement = ServiceEntityStringHelper.EMPTYSTRING;
		if (preCondition.getMultipleValueList().size() == 1) {
			Object value = preCondition.getMultipleValueList().get(0);
			SearchConfigPreCondition dummyPreCondition = new SearchConfigPreCondition();
			dummyPreCondition.setFieldValue(value);
			dummyPreCondition.setOperator(preCondition.getOperator());
			dummyPreCondition.setFieldName(preCondition.getFieldName());
			return generateEachPreConditionSingleField(dummyPreCondition, varName,
					strictFlag);
		}
		if (preCondition.getMultipleValueList().size() > 1) {
			for (int i = 0; i < preCondition.getMultipleValueList().size(); i++) {
				Object value = preCondition.getMultipleValueList().get(i);
				SearchConfigPreCondition dummyPreCondition = new SearchConfigPreCondition();
				dummyPreCondition.setFieldValue(value);
				dummyPreCondition.setFieldName(preCondition.getFieldName());
				if (i > 0) {
					statement = statement
							+ " or "
							+ generateEachPreConditionSingleField(
							dummyPreCondition, varName, strictFlag);
				} else {
					statement = generateEachPreConditionSingleField(
							dummyPreCondition, varName, strictFlag);
				}
			}
		}
		// Special handing for OR SQL command
		statement = "(" + statement + ")";
		return statement;
	}

	/**
	 * [Internal method] generate SQL pre-conditon search condition statement by
	 * each pre-condition
	 * 
	 * @return
	 */
	public static String generateEachPreConditionSingleField(
			SearchConfigPreCondition preCondition, String varName, boolean strictFlag) {
		String statement = ServiceEntityStringHelper.EMPTYSTRING;
		Class<?> valueType = null;
		if(preCondition.getFieldValue() != null){
			valueType = preCondition.getFieldValue().getClass();
		}
		if(!ServiceCollectionsHelper.checkNullList(preCondition.getMultipleValueList())){
			valueType = preCondition.getMultipleValueList().get(0).getClass();
		}
		if(valueType == null){
			// Return empty statement
			return null;
		}
		int basicType = ServiceReflectiveHelper.getBasicType(valueType);
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_STR){
			boolean localStrictFlag = DAOConstant.strictKeyName(preCondition
					.getFieldName());
			if (strictFlag) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " = " + "'"
						+ preCondition.getFieldValue() + "'";
			} else {
				if(localStrictFlag){
					statement = statement + varName + "."
							+ preCondition.getFieldName() + " = " + "'"
							+ preCondition.getFieldValue() + "'";
				} else {
					statement = statement + varName + "."
							+ preCondition.getFieldName() + " like " + "'%"
							+ preCondition.getFieldValue() + "%'";
				}
			}
			return statement;
		}
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_BOOLEAN) {
			statement = statement + varName + "." + preCondition.getFieldName()
					+ " = " + preCondition.getFieldValue();
			return statement;
		}
		if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER || basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_EQUAL) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " = "
						+ preCondition.getFieldValue();
			}
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_GREATER) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " > "
						+ preCondition.getFieldValue();
			}
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_GREATER_EQ) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " >= "
						+ preCondition.getFieldValue();
			}
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_LESS) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " < "
						+ preCondition.getFieldValue();
			}
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_LESS_EQ) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " <= "
						+ preCondition.getFieldValue();
			}
			if (preCondition.getOperator() == SEFieldSearchConfig.OPERATOR_NOT_EQ) {
				statement = statement + varName + "."
						+ preCondition.getFieldName() + " <> "
						+ preCondition.getFieldValue();
			}
			return statement;
		}
		return null;
	}

	protected String generateSearchConditionEachField(
			SEFieldSearchConfig fieldSearchConfig, String varName,
			boolean fuzzyFlag) {
		if (!ServiceCollectionsHelper.checkNullList(fieldSearchConfig.getValueList())){
			return generateSearchConditionWithMuliValue(fieldSearchConfig,
					varName, fuzzyFlag);
		} else {
			return generateSearchConditionEachFieldSingleValue(
					fieldSearchConfig, varName, fuzzyFlag);
		}
	}

	protected String generateSearchConditionWithMuliValue(
			SEFieldSearchConfig fieldSearchConfig, String varName,
			boolean fuzzyFlag) {
		if (ServiceCollectionsHelper.checkNullList(fieldSearchConfig.getValueList())){
			return null;
		}
		String statement = ServiceEntityStringHelper.EMPTYSTRING;
		if (fieldSearchConfig.getValueList().size() == 1) {
			Object value = fieldSearchConfig.getValueList().get(0);
			SEFieldSearchConfig dummySearchConfig = new SEFieldSearchConfig();
			dummySearchConfig.setLowValue(value);
			dummySearchConfig.setLowValueField(fieldSearchConfig
					.getLowValueField());
			dummySearchConfig.setOperator(fieldSearchConfig.getOperator());
			dummySearchConfig.setFieldName(fieldSearchConfig.getFieldName());
			return generateSearchConditionEachField(dummySearchConfig, varName,
					fuzzyFlag);
		}
		if (fieldSearchConfig.getValueList().size() > 1) {
			for (int i = 0; i < fieldSearchConfig.getValueList().size(); i++) {
				Object value = fieldSearchConfig.getValueList().get(i);
				SEFieldSearchConfig dummySearchConfig = new SEFieldSearchConfig();
				dummySearchConfig.setLowValue(value);
				dummySearchConfig.setLowValueField(fieldSearchConfig
						.getHighValueField());
				dummySearchConfig.setOperator(fieldSearchConfig.getOperator());
				dummySearchConfig
						.setFieldName(fieldSearchConfig.getFieldName());
				dummySearchConfig.setLowValueField(fieldSearchConfig
						.getLowValueField());
				if (i > 0) {
					statement = statement
							+ " or "
							+ generateSearchConditionEachFieldSingleValue(
									dummySearchConfig, varName, fuzzyFlag);
				} else {
					statement = generateSearchConditionEachFieldSingleValue(
							dummySearchConfig, varName, fuzzyFlag);
				}
			}
		}
		// Special handing for OR SQL command
		statement = "(" + statement + ")";
		return statement;
	}

	/**
	 * [Internal method] generate search condition command statement by each
	 * Field
	 * 
	 * @param fieldSearchConfig
	 * @param varName
	 *            :hibernate sql statement var name
	 * @param fuzzyFlag
	 * @return
	 */
	protected String generateSearchConditionEachFieldSingleValue(
			SEFieldSearchConfig fieldSearchConfig, String varName,
			boolean fuzzyFlag) {
		String statement = ServiceEntityStringHelper.EMPTYSTRING;
		int basicType;
		if(fieldSearchConfig.getLowValueField() != null){
			basicType = ServiceReflectiveHelper.getBasicType(fieldSearchConfig.getLowValueField()
					.getType());
		} else {
			basicType = ServiceReflectiveHelper.getBasicType(fieldSearchConfig.getHighValueField()
					.getType());
		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_EQUAL) {
			// In case equal operator
			String fieldTypeName = fieldSearchConfig.getLowValueField()
					.getType().getSimpleName();

			// In case field type is [String]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_STR ) {
				if (fuzzyFlag) {
					boolean nullLowValueFlag = ServiceReflectiveHelper
							.checkNullValue(fieldSearchConfig.getLowValue(),
									fieldTypeName);
					if (!nullLowValueFlag) {
						// In case fuzzy search
						Object rawValue = fieldSearchConfig.getLowValue();
						String serachValueString;
						if (rawValue != null
								&& rawValue.toString().endsWith("%")) {
							serachValueString = "'" + rawValue + "'";
						} else {
							serachValueString = "'%" + rawValue + "%'";
						}
						statement = statement + varName + "."
								+ fieldSearchConfig.getFieldName() + " like "
								+ serachValueString;
						return statement;
					}
				} else {
					boolean nullLowValueFlag = ServiceReflectiveHelper
							.checkNullValue(fieldSearchConfig.getLowValue(),
									fieldTypeName);
					if (!nullLowValueFlag) {
						String serachValueString = "'"
								+ fieldSearchConfig.getLowValue() + "'";
						statement = statement + varName + "."
								+ fieldSearchConfig.getFieldName() + " = "
								+ serachValueString;
						return statement;
					}
				}
			}
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE || basicType == ServiceReflectiveHelper.BASIC_TYPE_OBJ) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				String lowValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
						.format(fieldSearchConfig.getLowValue());
				if (!nullLowValueFlag) {
					String serachValueString = "'" + lowValueStr + "'";
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " = "
							+ serachValueString;
					return statement;
				}
			}
			// In case type is [int]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER
					|| basicType == ServiceReflectiveHelper.BASIC_TYPE_BOOLEAN) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " = "
							+ fieldSearchConfig.getLowValue();
					return statement;
				}
			}
		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_BETWEEN) {
			// In case Between operator
			String fieldTypeName = fieldSearchConfig.getLowValueField()
					.getType().getSimpleName();
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
				boolean nullHighValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				// Check null value of high value
				if (!nullHighValueFlag) {
					// In case not null high value
					String highValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(fieldSearchConfig.getHighValue());
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " <= " + "'"
							+ highValueStr + "'";
					if (!nullLowValueFlag) {
						// In case not null low value
						String lowValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
								.format(fieldSearchConfig.getLowValue());
						statement = statement + " and " + varName + "."
								+ fieldSearchConfig.getFieldName() + " >= "
								+ "'" + lowValueStr + "'";

					} else {
						// no further statement
					}
				} else {
					if (!nullLowValueFlag) {
						// In case not null low value
						String lowValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
								.format(fieldSearchConfig.getLowValue());
						statement = statement + varName + "."
								+ fieldSearchConfig.getFieldName() + " >= "
								+ "'" + lowValueStr + "'";
					} else {
						// no further statement
					}
				}
				return statement;
			}
			// In case type is [number]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
				boolean nullHighValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				// Check null value of high value
				if (!nullHighValueFlag) {
					// In case not null high value
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " <= "
							+ fieldSearchConfig.getHighValue();
					if (!nullLowValueFlag) {
						// In case not null low value
						statement = statement + " and " + varName + "."
								+ fieldSearchConfig.getFieldName() + " >= "
								+ fieldSearchConfig.getLowValue();

					} else {
						// no further statement
					}
				} else {
					if (!nullLowValueFlag) {
						// In case not null low value
						statement = statement + varName + "."
								+ fieldSearchConfig.getFieldName() + " >= "
								+ fieldSearchConfig.getLowValue();
					} else {
						// no further statement
					}
				}
				return statement;
			}

		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_GREATER) {
			// In case Greater operator
			String fieldTypeName = fieldSearchConfig.getLowValueField()
					.getType().getSimpleName();
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					String lowValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(fieldSearchConfig.getLowValue());
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " > " + "'"
							+ lowValueStr + "'";
					return statement;
				}
			}
			// In case type is [int]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
				statement = statement + varName + "."
						+ fieldSearchConfig.getFieldName() + " > "
						+ fieldSearchConfig.getLowValue();
				return statement;
			}

		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_GREATER_EQ) {
			// In case greater operator
			String fieldTypeName = fieldSearchConfig.getLowValueField()
					.getType().getSimpleName();
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					String lowValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(fieldSearchConfig.getLowValue());
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " >= " + "'"
							+ lowValueStr + "'";
					return statement;
				}
			}
			// In case type is [int]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getLowValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " >= "
							+ fieldSearchConfig.getLowValue();
					return statement;
				}
			}
		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_LESS) {
			// In case equal operator
			String fieldTypeName = fieldSearchConfig.getHighValueField()
					.getType().getSimpleName();
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
				boolean nullHighValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				if (!nullHighValueFlag) {
					String highValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(fieldSearchConfig.getHighValue());
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " < " + "'"
							+ highValueStr + "'";
					return statement;
				}
			}
			// In case type is [number]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " < "
							+ fieldSearchConfig.getHighValue();
					return statement;
				}

			}
		}
		if (fieldSearchConfig.getOperator() == SEFieldSearchConfig.OPERATOR_LESS_EQ) {
			// In case less operator
			String fieldTypeName = fieldSearchConfig.getHighValueField()
					.getType().getSimpleName();
			// In case field type is [Date]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_DATE) {
				boolean nullHighValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				if (!nullHighValueFlag) {
					String highValueStr = DefaultDateFormatConstant.DATE_MIN_FORMAT
							.format(fieldSearchConfig.getHighValue());
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " <= " + "'"
							+ highValueStr + "'";
					return statement;
				}
			}
			// In case type is [number]
			if (basicType == ServiceReflectiveHelper.BASIC_TYPE_NUMBER) {
				boolean nullLowValueFlag = ServiceReflectiveHelper
						.checkNullValue(fieldSearchConfig.getHighValue(),
								fieldTypeName);
				if (!nullLowValueFlag) {
					statement = statement + varName + "."
							+ fieldSearchConfig.getFieldName() + " <= "
							+ fieldSearchConfig.getHighValue();
					return statement;
				}
			}
		}
		return ServiceEntityStringHelper.EMPTYSTRING;
	}

	/**
	 * filter out the Search Node ComConfigure instance by node inst ID
	 * 
	 * @param searchNodeComConfigureList
	 * @param nodeInstID
	 * @return
	 */
	public BSearchNodeComConfigure getSearchNodeConfigure(
			List<BSearchNodeComConfigure> searchNodeComConfigureList,
			String nodeInstID) {
		if (ServiceCollectionsHelper.checkNullList(searchNodeComConfigureList)) {
			return null;
		}
		for (BSearchNodeComConfigure searchNodeComConfigure : searchNodeComConfigureList) {
			if (nodeInstID.equals(searchNodeComConfigure.getNodeInstID())) {
				return searchNodeComConfigure;
			}
		}
		return null;
	}

	public void mergeToRawList(List<ServiceEntityNode> rawList,
			List<ServiceEntityNode> addedList) {
		List<ServiceEntityNode> filteredAddedList = new ArrayList<>();
		for (ServiceEntityNode seNode : addedList) {
			boolean existedFlag = false;
			for (ServiceEntityNode rawNode : rawList) {
				if (rawNode.getUuid().equals(seNode.getUuid())) {
					existedFlag = true;
					break;
				}
			}
			if (!existedFlag) {
				filteredAddedList.add(seNode);
			}
		}
		rawList.addAll(filteredAddedList);
	}

	public void mergeToRawList(List<ServiceEntityNode> rawList,
			ServiceEntityNode addSeNode) {
		boolean existedFlag = false;
		for (ServiceEntityNode rawNode : rawList) {
			if (rawNode.getUuid().equals(addSeNode.getUuid())) {
				existedFlag = true;
				break;
			}
		}
		if (!existedFlag) {
			rawList.add(addSeNode);
		}
	}

}
