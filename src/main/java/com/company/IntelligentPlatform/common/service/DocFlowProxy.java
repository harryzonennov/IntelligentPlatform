package com.company.IntelligentPlatform.common.service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.common.controller.DocMatItemUIModel;
import com.company.IntelligentPlatform.common.controller.DocumentUIModel;
import com.company.IntelligentPlatform.common.dto.BasicDocItemInitModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.SearchContextBuilder;
import com.company.IntelligentPlatform.common.service.ServiceSearchProxy;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.DocumentMatItemBatchGenRequest;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;

import static com.company.IntelligentPlatform.common.service.StandardDocFlowDirectionProxy.*;

/**
 * General Proxy class for Document flow Management
 *
 * @author Zhang, Hang
 */
@Service
public class DocFlowProxy {

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected LogonUserManager logonUserManager;

	@Autowired
	protected DocumentSpecifierFactory documentSpecifierFactory;

	@Autowired
	protected DocActionExecutionProxyFactory docActionExecutionProxyFactory;

	@Autowired
	protected ReserveDocItemProxy reserveDocItemProxy;

	@Autowired
	protected DocFlowContextProxy docFlowContextProxy;

	@Autowired
	protected PrevNextDocItemProxy prevNextDocItemProxy;

	@Autowired
	protected PrevNextProfDocItemProxy prevNextProfDocItemProxy;

	protected Logger logger = LoggerFactory.getLogger(DocFlowProxy.class);

	public static final String METHOD_ConvPrevDocToItemUI = "convPrevDocToItemUI";

	public static final String METHOD_ConvPrevProfDocToItemUI = "convPrevProfDocToItemUI";

	public static final String METHOD_ConvNextDocToItemUI = "convNextDocToItemUI";

	public static final String METHOD_ConvNextProfDocToItemUI = "convNextProfDocToItemUI";

	public static final String METHOD_ConvReservedDocToItemUI = "convReservedDocToItemUI";

	public static final String METHOD_ConvParentDocToItemUI = "convParentDocToItemUI";

	public static final String METHOD_ConvMaterialSKUToItemUI = "convMaterialSKUToItemUI";

	public static final String METHOD_ConvCreatedByToItemUI = "convCreatedByToItemUI";

	public static final String METHOD_ConvUpdatedByToItemUI = "convUpdatedByToItemUI";

	public static final String METHOD_ConvCreatedByToDocUI = "convCreatedByToDocUI";

	public static final String METHOD_ConvUpdatedByToDocUI = "convUpdatedByToDocUI";

	public static final String METHOD_ConvPrevDocToDocUI = "convPrevDocToDocUI";

	public static final String METHOD_ConvPrevProfDocToDocUI = "convPrevProfDocToDocUI";

	public static final String METHOD_ConvNextDocToDocUI = "convNextDocToDocUI";

	public static final String METHOD_ConvNextProfDocToDocUI = "convNextProfDocToDocUI";

	public static final String NODEINSTID_SPEC_MATITEM = "specDocMatItem";

	public static final String NODEINSTID_SPEC_DOC = "specDoc";

	public static final String NODEINSTID_SPEC_DIRECTDOC = "specDirectDoc";

	public static final String NODEINSTID_PREV_MATITEM = "prevDocMatItem";

	public static final String NODEINSTID_PREVPROF_MATITEM = "prevProfDocMatItem";

	public static final String NODEINSTID_PREV_DOC = "prevDoc";

	public static final String NODEINSTID_PREVPROF_DOC = "prevProfDoc";

	public static final String NODEINSTID_PREV_DIRECTDOC = "prevDirectDoc";

	public static final String NODEINSTID_PREVPROF_DIRECTDOC = "prevProfDirectDoc";

	public static final String NODEINSTID_NEXT_MATITEM = "nextDocMatItem";

	public static final String NODEINSTID_NEXTPROF_MATITEM = "nextProfDocMatItem";

	public static final String NODEINSTID_NEXT_DOC = "nextDoc";

	public static final String NODEINSTID_NEXTPROF_DOC = "nextProfDoc";

	public static final String NODEINSTID_NEXT_DIRECTDOC = "nextDirectDoc";

	public static final String NODEINSTID_NEXTPROF_DIRECTDOC = "nextProfDirectDoc";

	public static final String NODEINSTID_RESERVED_MATITEM = "reservedDocMatItem";

	public static final String NODEINSTID_RESERVED_DOC = "reservedDoc";

	public static final String NODEINSTID_RESERVED_DIRECTDOC = "reservedDirectDoc";

	/**
	 * Common Logic to get previous document item node
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getPrevDocItemNode(DocMatItemNode docMatItemNode) {
		return getDefDocItemNode(docMatItemNode.getPrevDocType(), docMatItemNode.getPrevDocMatItemUUID(),
				docMatItemNode.getClient());
	}

	/**
	 * Common Logic to get Next document item node
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getNextDocItemNode(DocMatItemNode docMatItemNode) {
		return getDefDocItemNode(docMatItemNode.getNextDocType(), docMatItemNode.getNextDocMatItemUUID(),
				docMatItemNode.getClient());
	}

	/**
	 * Common Logic to get previous document item node
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getPrevProfDocItemNode(DocMatItemNode docMatItemNode) {
		return getDefDocItemNode(docMatItemNode.getPrevProfDocType(), docMatItemNode.getPrevProfDocMatItemUUID(),
				docMatItemNode.getClient());
	}

	/**
	 * Common Logic to get Next document item node
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getNextProfDocItemNode(DocMatItemNode docMatItemNode) {
		return getDefDocItemNode(docMatItemNode.getNextProfDocType(), docMatItemNode.getNextProfDocMatItemUUID(),
				docMatItemNode.getClient());
	}

	/**
	 * Common Logic to get preserved document item node
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getReservedDocItemNode(DocMatItemNode docMatItemNode) {
		return getDefDocItemNode(docMatItemNode.getReservedDocType(), docMatItemNode.getReservedMatItemUUID(),
				docMatItemNode.getClient());
	}

	public static <T extends BasicDocItemInitModel> T parseToDocItemInitModel(String request, Class<?> T) {
		JSONObject jsonObject = JSONObject.fromObject(request);
		@SuppressWarnings("rawtypes") Map<String, Class> classMap = new HashMap<>();
		return (T) JSONObject.toBean(jsonObject, T, classMap);
	}

	public String getDefDocItemIdentifier(DocMatItemNode docMatItemNode) throws ServiceEntityConfigureException {
		if (!ServiceEntityStringHelper.checkNullString(docMatItemNode.getId())) {
			return docMatItemNode.getId();
		}
		MaterialStockKeepUnit materialStockKeepUnit =
				getMaterialSKUFromDocMatItem(docMatItemNode);
		if (materialStockKeepUnit != null) {
			return MaterialStockKeepUnitManager.getMaterialIdentifier(materialStockKeepUnit, true);
		} else {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
	}

	private ServiceEntityManager getDocumentManager(int documentType) throws DocActionException {
		if (documentType == 0) {
			return null;
		}
		DocumentContentSpecifier documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
		if (documentContentSpecifier == null) {
			throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, documentType);
		}
		return documentContentSpecifier.getDocumentManager();
	}

	/**
	 * Default Logic to get Document Mat Item instance on document flow
	 *
	 * @param targetDocType
	 * @param targetDocItemUUID
	 * @return
	 */
	public ServiceEntityNode getDefDocItemNode(int targetDocType, String targetDocItemUUID, String client) {
		return getDefDocItemNode(targetDocType, targetDocItemUUID, IServiceEntityNodeFieldConstant.UUID, client);
	}

	/**
	 * Default Logic to get Document Mat Item instance on document flow
	 *
	 * @param targetDocType
	 * @return
	 * @parm keyValue
	 * @parm keyName
	 */
	public ServiceEntityNode getDefDocItemNode(int targetDocType, String keyValue, String keyName, String client) {
		try {
			ServiceEntityManager refDocumentManager = getDocumentManager(targetDocType);
			if (refDocumentManager == null || ServiceEntityStringHelper.checkNullString(keyValue)) {
				return null;
			}
			String targetNodeName = serviceDocumentComProxy.getDocumentMaterialItemNodeName(targetDocType);
			ServiceEntityNode documentItemNode =
					refDocumentManager.getEntityNodeByKey(keyValue, keyName, targetNodeName, client, null);
			return documentItemNode;
		} catch (ServiceEntityConfigureException | DocActionException e) {
			return null;
		}
	}

	public void updateDocItemNode(DocMatItemNode docMatItemNode, String resUserUUID, String organizationUUID)
			throws DocActionException {
		ServiceEntityManager refDocumentManager = getDocumentManager(docMatItemNode.getHomeDocumentType());
		if (refDocumentManager == null) {
			return;
		}
		refDocumentManager.updateSENode(docMatItemNode, resUserUUID, organizationUUID);
	}

	public Map<Integer, String> getStatusMap(int documentType, String lauanageCode)
			throws ServiceEntityInstallationException, DocActionException {
		DocumentContentSpecifier documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
		if (documentContentSpecifier == null) {
			throw new DocActionException(DocActionException.PARA_MISS_CONFIG_SPECIFIER, documentType);
		}
		Map<Integer, String> statusMap = documentContentSpecifier.getStatusMap(lauanageCode);
		if (statusMap != null) {
			return statusMap;
		}
		ServiceEntityManager refDocumentManager = documentContentSpecifier.getDocumentManager();
		if (refDocumentManager == null) {
			return null;
		}
		ServiceSearchProxy serviceSearchProxy = refDocumentManager.getSearchProxy();
		if (serviceSearchProxy == null) {
			return null;
		}
		return serviceSearchProxy.getStatusMap(lauanageCode);
	}

	//TODO this method has problems
	public List<ServiceEntityNode> getDefDocItemNodeList(ServiceSearchProxy serviceSearchProxy, List<String> uuidList,
														 LogonInfo logonInfo) throws DocActionException {
		Class<?> searchItemType = serviceSearchProxy.getMatItemSearchModelCls();
		try {
			SEUIComModel searchItemModule = (SEUIComModel) searchItemType.getDeclaredConstructor().newInstance();
			searchItemModule.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(uuidList));
			SearchContextBuilder searchContextBuilder = SearchContextBuilder.genBuilder(logonInfo).searchModel(searchItemModule);
			return serviceSearchProxy.searchItemList(searchContextBuilder.build()).getResultList();
		} catch (InstantiationException | IllegalAccessException | ServiceEntityInstallationException | ServiceEntityConfigureException | NoSuchMethodException | InvocationTargetException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
		} catch (SearchConfigureException | AuthorizationException | LogonInfoException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
		}
    }

	public List<ServiceEntityNode> getDefDocItemNodeList(int documentType, List<String> uuidList, String client)
			throws DocActionException {
		try {
			DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
					docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
			String docItemNodeName = documentContentSpecifier.getMatItemNodeInstId();
            return documentContentSpecifier.getDocumentManager()
					.getEntityNodeListByMultipleKey(uuidList, IServiceEntityNodeFieldConstant.UUID, docItemNodeName,
							client, null);
		} catch (ServiceEntityConfigureException e) {
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
		}
	}

	/**
	 * Utility method to refresh and reload doc item list from persistence
	 *
	 * @param docMatItemList
	 * @return
	 */
	public List<ServiceEntityNode> refreshLoadDocItemList(List<ServiceEntityNode> docMatItemList)
			throws DocActionException {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return null;
		}
		int documentType = getHomeDocTypeByMatItemList(docMatItemList);
		List<String> uuidList = docMatItemList.stream().map(ServiceEntityNode::getUuid).collect(Collectors.toList());
		return getDefDocItemNodeList(documentType, uuidList, docMatItemList.get(0).getClient());
	}

	/**
	 * Default Logic to get Document Mat Item instance on document flow
	 *
	 * @param targetDocType
	 * @param keyValue
	 * @param keyName
	 * @return
	 */
	public List<ServiceEntityNode> getDefDocItemNodeList(int targetDocType, String keyValue, String keyName,
														 String client) {
		try {
			ServiceEntityManager refDocumentManager = getDocumentManager(targetDocType);
			if (refDocumentManager == null || ServiceEntityStringHelper.checkNullString(keyValue) ||
					ServiceEntityStringHelper.checkNullString(keyName)) {
				return null;
			}
			String targetNodeName = serviceDocumentComProxy.getDocumentMaterialItemNodeName(targetDocType);
			return refDocumentManager.getEntityNodeListByKey(keyValue, keyName, targetNodeName, client, null);
		} catch (ServiceEntityConfigureException | DocActionException e) {
			return null;
		}
	}

	/**
	 * Default Logic to get Document Content instance directly by uuid
	 *
	 * @param targetDocType
	 * @param targetDocItemUUID
	 * @return
	 */
	public ServiceEntityNode getDirectDocContentNode(int targetDocType, String targetDocItemUUID, String client) {
		try {
			ServiceEntityManager refDocumentManager = getDocumentManager(targetDocType);
			DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
					docActionExecutionProxyFactory.getSpecifierByDocType(targetDocType);
			if (refDocumentManager == null || ServiceEntityStringHelper.checkNullString(targetDocItemUUID)) {
				return null;
			}
			String documentNodeName = documentContentSpecifier.getDocNodeName();
			return refDocumentManager.getEntityNodeByKey(targetDocItemUUID, IServiceEntityNodeFieldConstant.UUID,
					documentNodeName, client, null);
		} catch (ServiceEntityConfigureException | DocActionException e) {
			return null;
		}
	}

	/**
	 * Default Logic to get Document Content instance list directly by uuidlist
	 *
	 * @param targetDocType
	 * @param docUUIDList
	 * @return
	 */
	public List<ServiceEntityNode> getDirectDocContentNodeList(int targetDocType, List<String> docUUIDList,
															   String client) {
		try {
			ServiceEntityManager refDocumentManager = getDocumentManager(targetDocType);
			DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
					docActionExecutionProxyFactory.getSpecifierByDocType(targetDocType);
			if (refDocumentManager == null || ServiceCollectionsHelper.checkNullList(docUUIDList)) {
				return null;
			}
			String documentNodeName = documentContentSpecifier.getDocNodeName();
			return refDocumentManager.getEntityNodeListByMultipleKey(docUUIDList, IServiceEntityNodeFieldConstant.UUID,
					documentNodeName, client, null);
		} catch (ServiceEntityConfigureException | DocActionException e) {
			return null;
		}
	}

	/**
	 * Default logic to get the document content by document Mat Item node
	 * instance
	 *
	 * @param docMatItemNode
	 * @return
	 */
	public ServiceEntityNode getDefDocumentContentCore(ServiceEntityNode docMatItemNode)
			throws ServiceEntityConfigureException {
		if (docMatItemNode instanceof DocMatItemNode) {
			try {
				return getDefDocumentContentFromDocMatItem((DocMatItemNode) docMatItemNode);
			} catch (DocActionException e) {
				throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,
						e.getErrorMessage());
			}
		}
		ServiceEntityManager refDocumentManager =
				serviceEntityManagerFactoryInContext.getManagerBySEName(docMatItemNode.getServiceEntityName());
		if (refDocumentManager == null) {
			return null;
		}
		ServiceEntityNode documentContent = null;
		String documentNodeName = ServiceEntityNode.NODENAME_ROOT;
		try {
			documentContent = refDocumentManager.getEntityNodeByKey(docMatItemNode.getParentNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID, documentNodeName, docMatItemNode.getClient(), null);
			// In case document Content is null, then try using root node
			if (documentContent == null) {
				documentContent = refDocumentManager.getEntityNodeByKey(docMatItemNode.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID, documentNodeName, docMatItemNode.getClient(), null);
			}
			return documentContent;
		} catch (ServiceEntityConfigureException e) {
			return null;
		}
	}

	public ServiceEntityNode getDefDocumentContentFromDocMatItem(DocMatItemNode docMatItemNode)
			throws DocActionException {
		int documentType = docMatItemNode.getHomeDocumentType();
		DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
		ServiceEntityManager refDocumentManager = documentContentSpecifier.getDocumentManager();
		if (refDocumentManager == null) {
			return null;
		}
		ServiceEntityNode documentContent = null;
		String documentNodeName = documentContentSpecifier.getDocNodeName();
		try {
			documentContent = refDocumentManager.getEntityNodeByKey(docMatItemNode.getParentNodeUUID(),
					IServiceEntityNodeFieldConstant.UUID, documentNodeName, docMatItemNode.getClient(), null);
			// In case document Content is null, then try using root node
			if (documentContent == null) {
				documentContent = refDocumentManager.getEntityNodeByKey(docMatItemNode.getRootNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID, documentNodeName, docMatItemNode.getClient(), null);
			}
			return documentContent;
		} catch (ServiceEntityConfigureException e) {
			return null;
		}
	}

	public static class SimpleDocConfigurePara {

		protected int documentType;

		protected String sourceFieldName = IServiceEntityNodeFieldConstant.UUID;

		protected String targetFieldName = IServiceEntityNodeFieldConstant.UUID;

		protected String serviceEntityName;

		protected String baseNodeId;

		protected String convToUIMethod;

		protected Class<?>[] convToUIParas;

		protected String convToDocItemUIMethod;

		protected Class<?>[] convToDocItemUIParas;

		protected Object logicManager;

		protected UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback;

		protected UIModelNodeMapConfigure.IGetSENode docGetCallback;

		public SimpleDocConfigurePara() {
			super();
		}

		public SimpleDocConfigurePara(String baseNodeId, String convToUIMethod, Class<?>[] convToUIParas,
									  Object logicManager) {
			super();
			this.baseNodeId = baseNodeId;
			this.convToUIMethod = convToUIMethod;
			this.convToUIParas = convToUIParas;
			this.logicManager = logicManager;
		}

		public SimpleDocConfigurePara(String baseNodeId, String convToUIMethod, Class<?>[] convToUIParas,
									  Object logicManager, UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback,
									  UIModelNodeMapConfigure.IGetSENode docGetCallback) {
			super();
			this.baseNodeId = baseNodeId;
			this.convToUIMethod = convToUIMethod;
			this.convToUIParas = convToUIParas;
			this.logicManager = logicManager;
			this.docMatItemGetCallback = docMatItemGetCallback;
			this.docGetCallback = docGetCallback;
		}

		public SimpleDocConfigurePara(String baseNodeId, String convToUIMethod, Class<?>[] convToUIParas,
									  String convToDocItemUIMethod, Class<?>[] convToDocItemUIParas,
									  Object logicManager, UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback,
									  UIModelNodeMapConfigure.IGetSENode docGetCallback) {
			this.baseNodeId = baseNodeId;
			this.convToUIMethod = convToUIMethod;
			this.convToUIParas = convToUIParas;
			this.convToDocItemUIMethod = convToDocItemUIMethod;
			this.convToDocItemUIParas = convToDocItemUIParas;
			this.logicManager = logicManager;
			this.docMatItemGetCallback = docMatItemGetCallback;
			this.docGetCallback = docGetCallback;
		}

		public SimpleDocConfigurePara(int documentType, String baseNodeId, String sourceFieldName,
									  String serviceEntityName, Object logicManager, String convToUIMethod,
									  Class<?>[] convToUIParas, String convToDocItemUIMethod,
									  Class<?>[] convToDocItemUIParas) {
			this.documentType = documentType;
			this.baseNodeId = baseNodeId;
			this.sourceFieldName = sourceFieldName;
			this.serviceEntityName = serviceEntityName;
			this.convToUIMethod = convToUIMethod;
			this.logicManager = logicManager;
			this.convToUIParas = convToUIParas;
			this.convToDocItemUIMethod = convToDocItemUIMethod;
			this.convToDocItemUIParas = convToDocItemUIParas;
		}

		public SimpleDocConfigurePara(int documentType, String baseNodeId, String sourceFieldName,
									  String targetFieldName, String serviceEntityName, Object logicManager,
									  String convToUIMethod, Class<?>[] convToUIParas, String convToDocItemUIMethod,
									  Class<?>[] convToDocItemUIParas) {
			this.documentType = documentType;
			this.baseNodeId = baseNodeId;
			this.sourceFieldName = sourceFieldName;
			this.targetFieldName = targetFieldName;
			this.serviceEntityName = serviceEntityName;
			this.convToUIMethod = convToUIMethod;
			this.logicManager = logicManager;
			this.convToUIParas = convToUIParas;
			this.convToDocItemUIMethod = convToDocItemUIMethod;
			this.convToDocItemUIParas = convToDocItemUIParas;
		}

		public String getBaseNodeId() {
			return baseNodeId;
		}

		public void setBaseNodeId(String baseNodeId) {
			this.baseNodeId = baseNodeId;
		}

		public UIModelNodeMapConfigure.IGetSENode getDocMatItemGetCallback() {
			return docMatItemGetCallback;
		}

		public void setDocMatItemGetCallback(UIModelNodeMapConfigure.IGetSENode docMatItemGetCallback) {
			this.docMatItemGetCallback = docMatItemGetCallback;
		}

		public String getConvToUIMethod() {
			return convToUIMethod;
		}

		public void setConvToUIMethod(String convToUIMethod) {
			this.convToUIMethod = convToUIMethod;
		}

		public Class<?>[] getConvToUIParas() {
			return convToUIParas;
		}

		public void setConvToUIParas(Class<?>[] convToUIParas) {
			this.convToUIParas = convToUIParas;
		}

		public Object getLogicManager() {
			return logicManager;
		}

		public void setLogicManager(Object logicManager) {
			this.logicManager = logicManager;
		}

		public UIModelNodeMapConfigure.IGetSENode getDocGetCallback() {
			return docGetCallback;
		}

		public void setDocGetCallback(UIModelNodeMapConfigure.IGetSENode docGetCallback) {
			this.docGetCallback = docGetCallback;
		}

		public String getConvToDocItemUIMethod() {
			return convToDocItemUIMethod;
		}

		public void setConvToDocItemUIMethod(String convToDocItemUIMethod) {
			this.convToDocItemUIMethod = convToDocItemUIMethod;
		}

		public Class<?>[] getConvToDocItemUIParas() {
			return convToDocItemUIParas;
		}

		public void setConvToDocItemUIParas(Class<?>[] convToDocItemUIParas) {
			this.convToDocItemUIParas = convToDocItemUIParas;
		}

		public int getDocumentType() {
			return documentType;
		}

		public void setDocumentType(int documentType) {
			this.documentType = documentType;
		}

		public String getSourceFieldName() {
			return sourceFieldName;
		}

		public void setSourceFieldName(String sourceFieldName) {
			this.sourceFieldName = sourceFieldName;
		}

		public String getTargetFieldName() {
			return targetFieldName;
		}

		public void setTargetFieldName(String targetFieldName) {
			this.targetFieldName = targetFieldName;
		}

		public String getServiceEntityName() {
			return serviceEntityName;
		}

		public void setServiceEntityName(String serviceEntityName) {
			this.serviceEntityName = serviceEntityName;
		}
	}

	@Deprecated
	public List<UIModelNodeMapConfigure> getSpecNodeMapConfigureList(SimpleDocConfigurePara simpleDocConfigurePara)
			throws ServiceEntityConfigureException {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[specific Doc MatItem]
		UIModelNodeMapConfigure specDocMatItemMap = new UIModelNodeMapConfigure();
		DocumentContentSpecifier<?, ?, ?> documentContentSpecifier = null;
		int documentType = simpleDocConfigurePara.getDocumentType();
		try {
			if (documentType > 0) {
				documentContentSpecifier = docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
			}
		} catch (DocActionException e) {
			throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,
					e.getErrorMessage());
		}
		specDocMatItemMap.setBaseNodeInstID(simpleDocConfigurePara.getBaseNodeId());
		specDocMatItemMap.setNodeInstID(NODEINSTID_SPEC_MATITEM);
		specDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);

		if (documentContentSpecifier != null) {
			specDocMatItemMap.setNodeName(documentContentSpecifier.getMatItemNodeInstId());
			specDocMatItemMap.setNodeInstID(documentContentSpecifier.getMatItemNodeInstId());
			specDocMatItemMap.setServiceEntityManager(documentContentSpecifier.getDocumentManager());
		}
		if (simpleDocConfigurePara.getConvToDocItemUIMethod() != null) {
			specDocMatItemMap.setConvToUIMethodParas(simpleDocConfigurePara.getConvToDocItemUIParas());
			specDocMatItemMap.setConvToUIMethod(simpleDocConfigurePara.getConvToDocItemUIMethod());
		}
		if (simpleDocConfigurePara.getLogicManager() != null) {
			specDocMatItemMap.setLogicManager(simpleDocConfigurePara.getLogicManager());
		}
		UIModelNodeMapConfigure.IGetSENode<?> docMatItemGetCallback = simpleDocConfigurePara.getDocMatItemGetCallback();
		if (docMatItemGetCallback != null) {
			// In case use call back to get se node instance
			specDocMatItemMap.setGetSENodeCallback(simpleDocConfigurePara.getDocMatItemGetCallback());
		} else {
			List<SearchConfigConnectCondition> itemNodeConditionList = new ArrayList<>();
			SearchConfigConnectCondition itemNodeCondition0 = new SearchConfigConnectCondition();
			itemNodeCondition0.setSourceFieldName(simpleDocConfigurePara.getSourceFieldName());
			String targetFieldName =
					ServiceEntityStringHelper.checkNullString(simpleDocConfigurePara.getTargetFieldName()) ?
							simpleDocConfigurePara.getTargetFieldName() : IServiceEntityNodeFieldConstant.UUID;
			itemNodeCondition0.setTargetFieldName(targetFieldName);
			itemNodeConditionList.add(itemNodeCondition0);
		}
		uiModelNodeMapList.add(specDocMatItemMap);

		UIModelNodeMapConfigure specDocMap = new UIModelNodeMapConfigure();
		specDocMap.setNodeInstID(NODEINSTID_SPEC_DOC);
		specDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		specDocMap.setBaseNodeInstID(NODEINSTID_SPEC_MATITEM);
		specDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		if (simpleDocConfigurePara.getLogicManager() != null) {
			specDocMap.setLogicManager(simpleDocConfigurePara.getLogicManager());
		} else {
			specDocMap.setLogicManager(this);
		}
		if (simpleDocConfigurePara.getConvToUIMethod() != null) {
			specDocMap.setConvToUIMethodParas(simpleDocConfigurePara.getConvToUIParas());
			specDocMap.setConvToUIMethod(simpleDocConfigurePara.getConvToUIMethod());
		} else {
			Class<?>[] specDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
			specDocMap.setConvToUIMethodParas(specDocConvToUIParas);
			specDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevDocToItemUI);
		}
		uiModelNodeMapList.add(specDocMap);
		if (simpleDocConfigurePara.getDocGetCallback() != null) {
			// In case need direct doc call back
			List<UIModelNodeMapConfigure> direcDocMapList = getDirectDocMapConfigureList(simpleDocConfigurePara);
			if (!ServiceCollectionsHelper.checkNullList(direcDocMapList)) {
				uiModelNodeMapList.addAll(direcDocMapList);
			}
		}
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDirectDocMapConfigureList(SimpleDocConfigurePara simpleDocConfigurePara) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		UIModelNodeMapConfigure specDocDirectMap = new UIModelNodeMapConfigure();
		specDocDirectMap.setBaseNodeInstID(simpleDocConfigurePara.getBaseNodeId());
		specDocDirectMap.setNodeInstID(NODEINSTID_PREV_DIRECTDOC);
		specDocDirectMap.setGetSENodeCallback(simpleDocConfigurePara.getDocGetCallback());

		specDocDirectMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		if (simpleDocConfigurePara.getConvToUIMethod() != null) {
			specDocDirectMap.setConvToUIMethodParas(simpleDocConfigurePara.getConvToUIParas());
			specDocDirectMap.setConvToUIMethod(simpleDocConfigurePara.getConvToUIMethod());
		} else {
			Class<?>[] specDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
			specDocDirectMap.setConvToUIMethodParas(specDocConvToUIParas);
			specDocDirectMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevDocToItemUI);
		}
		if (simpleDocConfigurePara.getLogicManager() != null) {
			specDocDirectMap.setLogicManager(simpleDocConfigurePara.getLogicManager());
		} else {
			specDocDirectMap.setLogicManager(this);
		}
		uiModelNodeMapList.add(specDocDirectMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefPrevProfDocMapConfigureList(String baseNodeId) {
		return getDefDocMapConfigureListCore(baseNodeId, DOCFLOW_DIREC_PREV_PROF);
	}

	public List<UIModelNodeMapConfigure> getDefPrevDocMapConfigureList(String baseNodeId) {
		return getDefDocMapConfigureListCore(baseNodeId, DOCFLOW_DIREC_PREV);
	}

	public List<UIModelNodeMapConfigure> getDefNextProfDocMapConfigureList(String baseNodeId) {
		return getDefDocMapConfigureListCore(baseNodeId, DOCFLOW_DIREC_NEXT_PROF);
	}

	public List<UIModelNodeMapConfigure> getDefNextDocMapConfigureList(String baseNodeId) {
		return getDefDocMapConfigureListCore(baseNodeId, DOCFLOW_DIREC_NEXT);
	}

	public List<UIModelNodeMapConfigure> getDefDocMapConfigureListCore(String baseNodeId, int direction) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		UIModelNodeMapConfigure prevDocMap = new UIModelNodeMapConfigure();
		Class<?>[] docConvToUIParas = {ServiceEntityNode.class, DocumentUIModel.class};
		prevDocMap.setConvToUIMethodParas(docConvToUIParas);
		prevDocMap.setBaseNodeInstID(baseNodeId);
		if (direction == DOCFLOW_DIREC_PREV_PROF) {
			prevDocMap.setNodeInstID(NODEINSTID_PREVPROF_DOC);
			prevDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevProfDocToDocUI);
		}
		if (direction == DOCFLOW_DIREC_PREV) {
			prevDocMap.setNodeInstID(NODEINSTID_PREV_DOC);
			prevDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevDocToDocUI);
		}
		if (direction == DOCFLOW_DIREC_NEXT) {
			prevDocMap.setNodeInstID(NODEINSTID_NEXT_DOC);
			prevDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvNextDocToDocUI);
		}
		if (direction == DOCFLOW_DIREC_NEXT_PROF) {
			prevDocMap.setNodeInstID(NODEINSTID_NEXTPROF_DOC);
			prevDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvNextProfDocToDocUI);
		}
		prevDocMap.setGetSENodeCallback(rawSENode -> {
			DocumentContent documentContent = (DocumentContent) rawSENode;
			if (direction == DOCFLOW_DIREC_PREV_PROF) {
				return getDirectDocContentNode(documentContent.getPrevProfDocType(), documentContent.getPrevProfDocUUID(),
						documentContent.getClient());
			}
			if (direction == DOCFLOW_DIREC_PREV) {
				return getDirectDocContentNode(documentContent.getPrevDocType(), documentContent.getPrevDocUUID(),
						documentContent.getClient());
			}
			if (direction == DOCFLOW_DIREC_NEXT) {
				return getDirectDocContentNode(documentContent.getNextDocType(), documentContent.getNextDocUUID(),
						documentContent.getClient());
			}
			if (direction == DOCFLOW_DIREC_NEXT_PROF) {
				return getDirectDocContentNode(documentContent.getNextProfDocType(), documentContent.getNextProfDocUUID(),
						documentContent.getClient());
			}
			return rawSENode;
		});
		prevDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prevDocMap.setLogicManager(this);
		uiModelNodeMapList.add(prevDocMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefPrevNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[Previous Doc MatItem]
		UIModelNodeMapConfigure prevDocMatItemMap = new UIModelNodeMapConfigure();
		prevDocMatItemMap.setBaseNodeInstID(baseNodeId);
		prevDocMatItemMap.setNodeInstID(NODEINSTID_PREV_MATITEM);
		prevDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prevDocMatItemMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode prevMatItemNode = getPrevDocItemNode(docMatItemNode);
			return prevMatItemNode;
		});
		uiModelNodeMapList.add(prevDocMatItemMap);

		UIModelNodeMapConfigure prevDocMap = new UIModelNodeMapConfigure();
		prevDocMap.setNodeInstID(NODEINSTID_PREV_DOC);
		prevDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		prevDocMap.setBaseNodeInstID(NODEINSTID_PREV_MATITEM);
		prevDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] prevDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
		prevDocMap.setConvToUIMethodParas(prevDocConvToUIParas);
		prevDocMap.setLogicManager(this);
		prevDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevDocToItemUI);
		uiModelNodeMapList.add(prevDocMap);

		/*
		 * Node to retrieve document content directly, in case
		 * "prevDocMatItemUUID" store document content uuid
		 */
		UIModelNodeMapConfigure prevDocDirectMap = new UIModelNodeMapConfigure();
		prevDocDirectMap.setBaseNodeInstID(baseNodeId);
		prevDocDirectMap.setNodeInstID(NODEINSTID_PREV_DIRECTDOC);
		prevDocDirectMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode prevContent =
					getDirectDocContentNode(docMatItemNode.getPrevDocType(), docMatItemNode.getPrevDocMatItemUUID(),
							docMatItemNode.getClient());
			return prevContent;
		});
		prevDocDirectMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prevDocDirectMap.setConvToUIMethodParas(prevDocConvToUIParas);
		prevDocDirectMap.setLogicManager(this);
		prevDocDirectMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevDocToItemUI);
		uiModelNodeMapList.add(prevDocDirectMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefNextNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[Next Doc MatItem]
		UIModelNodeMapConfigure nextDocMatItemMap = new UIModelNodeMapConfigure();
		nextDocMatItemMap.setBaseNodeInstID(baseNodeId);
		nextDocMatItemMap.setNodeInstID(NODEINSTID_NEXT_MATITEM);
		nextDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		nextDocMatItemMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode nextMatItemNode = getNextDocItemNode(docMatItemNode);
			return nextMatItemNode;
		});
		uiModelNodeMapList.add(nextDocMatItemMap);

		UIModelNodeMapConfigure nextDocMap = new UIModelNodeMapConfigure();
		nextDocMap.setNodeInstID(NODEINSTID_NEXT_DOC);
		nextDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		nextDocMap.setBaseNodeInstID(NODEINSTID_NEXT_MATITEM);
		nextDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] nextDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
		nextDocMap.setConvToUIMethodParas(nextDocConvToUIParas);
		nextDocMap.setLogicManager(this);
		nextDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvNextDocToItemUI);
		uiModelNodeMapList.add(nextDocMap);

		/*
		 * Node to retrieve document content directly, in case
		 * "nextDocMatItemUUID" store document content uuid
		 */
		UIModelNodeMapConfigure nextDocDirectMap = new UIModelNodeMapConfigure();
		nextDocDirectMap.setBaseNodeInstID(baseNodeId);
		nextDocDirectMap.setNodeInstID(NODEINSTID_NEXT_DIRECTDOC);
		nextDocDirectMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode nextContent =
					getDirectDocContentNode(docMatItemNode.getNextDocType(), docMatItemNode.getNextDocMatItemUUID(),
							docMatItemNode.getClient());
			return nextContent;
		});
		nextDocDirectMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		nextDocDirectMap.setConvToUIMethodParas(nextDocConvToUIParas);
		nextDocDirectMap.setLogicManager(this);
		nextDocDirectMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvNextDocToItemUI);
		uiModelNodeMapList.add(nextDocDirectMap);

		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefPrevProfNodeMapConfigureList(String baseNodeId) {
		return getDefMaterialNodeMapConfigureList(baseNodeId, null, null, null);
	}

	public List<UIModelNodeMapConfigure> getDefPrevProfNodeMapConfigureList(String baseNodeId, String convToUIMethod,
																			Object logicManager,
																			Class<?>[] convToUIMethodParas) {
		return getDefPrevProfNodeMapConfigureListFrame(baseNodeId, null, convToUIMethod, null, logicManager, null,
				convToUIMethodParas);
	}

	public List<UIModelNodeMapConfigure> getDefPrevProfNodeMapConfigureListFrame(String baseNodeId,
																				 String convToItemUIMethod,
																				 String convToUIMethod,
																				 Object itemLogicManager,
																				 Object logicManager,
																				 Class<?>[] convToUIItemMethodParas,
																				 Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[Previous Doc MatItem]
		UIModelNodeMapConfigure prevProfDocMatItemMap = new UIModelNodeMapConfigure();
		prevProfDocMatItemMap.setBaseNodeInstID(baseNodeId);
		prevProfDocMatItemMap.setNodeInstID(NODEINSTID_PREVPROF_MATITEM);
		prevProfDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prevProfDocMatItemMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode prevMatItemNode = getPrevProfDocItemNode(docMatItemNode);
			return prevMatItemNode;
		});
		if (convToItemUIMethod != null) {
			prevProfDocMatItemMap.setConvToUIMethod(convToItemUIMethod);
			prevProfDocMatItemMap.setConvToUIMethodParas(convToUIItemMethodParas);
		}
		if (itemLogicManager != null) {
			prevProfDocMatItemMap.setLogicManager(itemLogicManager);
		}
		uiModelNodeMapList.add(prevProfDocMatItemMap);

		UIModelNodeMapConfigure prevProfDocMap = new UIModelNodeMapConfigure();
		prevProfDocMap.setNodeInstID(NODEINSTID_PREVPROF_DOC);
		prevProfDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		prevProfDocMap.setBaseNodeInstID(NODEINSTID_PREVPROF_MATITEM);
		prevProfDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);

		if (convToUIMethodParas != null) {
			prevProfDocMap.setConvToUIMethodParas(convToUIMethodParas);
		} else {
			Class<?>[] prevDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
			prevProfDocMap.setConvToUIMethodParas(prevDocConvToUIParas);
		}
		if (logicManager != null) {
			prevProfDocMap.setLogicManager(logicManager);
		} else {
			prevProfDocMap.setLogicManager(this);
		}
		if (convToUIMethod != null) {
			prevProfDocMap.setConvToUIMethod(convToUIMethod);
		} else {
			prevProfDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvPrevProfDocToItemUI);
		}
		uiModelNodeMapList.add(prevProfDocMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefNextProfNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[Next Doc MatItem]
		UIModelNodeMapConfigure nextProfDocMatItemMap = new UIModelNodeMapConfigure();
		nextProfDocMatItemMap.setBaseNodeInstID(baseNodeId);
		nextProfDocMatItemMap.setNodeInstID(NODEINSTID_NEXTPROF_MATITEM);
		nextProfDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		nextProfDocMatItemMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode nextMatItemNode = getNextProfDocItemNode(docMatItemNode);
			return nextMatItemNode;
		});
		uiModelNodeMapList.add(nextProfDocMatItemMap);

		UIModelNodeMapConfigure nextProfDocMap = new UIModelNodeMapConfigure();
		nextProfDocMap.setNodeInstID(NODEINSTID_NEXTPROF_DOC);
		nextProfDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		nextProfDocMap.setBaseNodeInstID(NODEINSTID_NEXTPROF_MATITEM);
		nextProfDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] nextDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
		nextProfDocMap.setConvToUIMethodParas(nextDocConvToUIParas);
		nextProfDocMap.setLogicManager(this);
		nextProfDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvNextProfDocToItemUI);
		uiModelNodeMapList.add(nextProfDocMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefReservedNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[Reserved Doc MatItem]
		UIModelNodeMapConfigure reservedDocMatItemMap = new UIModelNodeMapConfigure();
		reservedDocMatItemMap.setBaseNodeInstID(baseNodeId);
		reservedDocMatItemMap.setNodeInstID(NODEINSTID_RESERVED_MATITEM);
		reservedDocMatItemMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		reservedDocMatItemMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode reservedMatItemNode = getReservedDocItemNode(docMatItemNode);
			return reservedMatItemNode;
		});
		uiModelNodeMapList.add(reservedDocMatItemMap);

		UIModelNodeMapConfigure reservedDocMap = new UIModelNodeMapConfigure();
		reservedDocMap.setNodeInstID(NODEINSTID_RESERVED_DOC);
		reservedDocMap.setGetSENodeCallback(this::getDefDocumentContentCore);
		reservedDocMap.setBaseNodeInstID(NODEINSTID_RESERVED_MATITEM);
		reservedDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		Class<?>[] reservedDocConvToUIParas = {ServiceEntityNode.class, DocMatItemUIModel.class};
		reservedDocMap.setConvToUIMethodParas(reservedDocConvToUIParas);
		reservedDocMap.setLogicManager(this);
		reservedDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvReservedDocToItemUI);
		uiModelNodeMapList.add(reservedDocMap);

		/*
		 * Node to retrieve document content directly, in case
		 * "nextDocMatItemUUID" store document content uuid
		 */
		UIModelNodeMapConfigure reservedDocDirectMap = new UIModelNodeMapConfigure();
		reservedDocDirectMap.setBaseNodeInstID(baseNodeId);
		reservedDocDirectMap.setNodeInstID(NODEINSTID_RESERVED_DIRECTDOC);
		reservedDocDirectMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode reservedContent = getDirectDocContentNode(docMatItemNode.getReservedDocType(),
					docMatItemNode.getReservedMatItemUUID(), docMatItemNode.getClient());
			return reservedContent;
		});
		reservedDocDirectMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		reservedDocDirectMap.setConvToUIMethodParas(reservedDocConvToUIParas);
		reservedDocDirectMap.setLogicManager(this);
		reservedDocDirectMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvReservedDocToItemUI);
		uiModelNodeMapList.add(reservedDocDirectMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefParentDocMapConfigureList(String baseNodeId) {
		return getDefParentDocMapConfigureList(baseNodeId, null, null, null);
	}

	public static String getDefParentDocId(String baseNodeId) {
		return "parentDoc" + baseNodeId;
	}

	public List<UIModelNodeMapConfigure> getDefParentDocMapConfigureList(String baseNodeId, String convToUIMethod,
																		 Object logicManager,
																		 Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		UIModelNodeMapConfigure parentDocMap = new UIModelNodeMapConfigure();
		parentDocMap.setBaseNodeInstID(baseNodeId);
		parentDocMap.setNodeInstID(getDefParentDocId(baseNodeId));
		if (convToUIMethodParas != null) {
			parentDocMap.setConvToUIMethodParas(convToUIMethodParas);
		} else {
			Class<?>[] parentDocConvToUIParas = {DocumentContent.class, DocMatItemUIModel.class};
			parentDocMap.setConvToUIMethodParas(parentDocConvToUIParas);
		}
		parentDocMap.setGetSENodeCallback(rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			ServiceEntityNode parentDocument =
					getDirectDocContentNode(docMatItemNode.getHomeDocumentType(), docMatItemNode.getParentNodeUUID(),
							docMatItemNode.getClient());
			return parentDocument;
		});
		parentDocMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		if (logicManager != null) {
			parentDocMap.setLogicManager(logicManager);
		} else {
			parentDocMap.setLogicManager(this);
		}
		if (convToUIMethod != null) {
			parentDocMap.setConvToUIMethod(convToUIMethod);
		} else {
			parentDocMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvParentDocToItemUI);
		}
		uiModelNodeMapList.add(parentDocMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefMaterialNodeMapConfigureList(String baseNodeId) {
		return getDefMaterialNodeMapConfigureList(baseNodeId, null, null, null);
	}

	public List<UIModelNodeMapConfigure> getDefMaterialNodeMapConfigureList(String baseNodeId, String convToUIMethod,
																			Object logicManager,
																			Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[MaterialStockKeepUnit]
		UIModelNodeMapConfigure defMaterialSKUMap = new UIModelNodeMapConfigure();
		defMaterialSKUMap.setSeName(MaterialStockKeepUnit.SENAME);
		defMaterialSKUMap.setNodeName(MaterialStockKeepUnit.NODENAME);
		defMaterialSKUMap.setNodeInstID("Def" + MaterialStockKeepUnit.SENAME);
		defMaterialSKUMap.setBaseNodeInstID(baseNodeId);
		defMaterialSKUMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> defMaterialSKUConditionList = new ArrayList<>();
		SearchConfigConnectCondition defMaterialSKUCondition0 = new SearchConfigConnectCondition();
		defMaterialSKUCondition0.setSourceFieldName(IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
		defMaterialSKUCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		defMaterialSKUConditionList.add(defMaterialSKUCondition0);
		defMaterialSKUMap.setConnectionConditions(defMaterialSKUConditionList);
		defMaterialSKUMap.setServiceEntityManager(materialStockKeepUnitManager);
		if (convToUIMethodParas == null) {
			Class<?>[] materialStockKeepUnitConvToUIParas = {MaterialStockKeepUnit.class, DocMatItemUIModel.class};
			defMaterialSKUMap.setConvToUIMethodParas(materialStockKeepUnitConvToUIParas);
		} else {
			defMaterialSKUMap.setConvToUIMethodParas(convToUIMethodParas);
		}
		if (convToUIMethod == null) {
			defMaterialSKUMap.setLogicManager(this);
			defMaterialSKUMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvMaterialSKUToItemUI);
		} else {
			if (logicManager != null) {
				defMaterialSKUMap.setLogicManager(logicManager);
			}
			defMaterialSKUMap.setConvToUIMethod(convToUIMethod);
		}
		uiModelNodeMapList.add(defMaterialSKUMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getCreatedByNodeMapConfigureList(String baseNodeId, String convToUIMethod,
																		  Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[LogonUser: CreatedBy]
		UIModelNodeMapConfigure createdByMap = new UIModelNodeMapConfigure();
		createdByMap.setSeName(LogonUser.SENAME);
		createdByMap.setNodeName(LogonUser.NODENAME);
		createdByMap.setNodeInstID("DefCreatedBy" + LogonUser.SENAME);
		createdByMap.setBaseNodeInstID(baseNodeId);
		createdByMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> createdByConditionList = new ArrayList<>();
		SearchConfigConnectCondition createdByCondition0 = new SearchConfigConnectCondition();
		createdByCondition0.setSourceFieldName(IServiceEntityNodeFieldConstant.CREATEDBY);
		createdByCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		createdByConditionList.add(createdByCondition0);
		createdByMap.setConnectionConditions(createdByConditionList);
		createdByMap.setServiceEntityManager(logonUserManager);
		if (convToUIMethodParas == null) {
			Class<?>[] createdByConvToUIParas = {LogonUser.class, DocMatItemUIModel.class};
			createdByMap.setConvToUIMethodParas(createdByConvToUIParas);
		} else {
			createdByMap.setConvToUIMethodParas(convToUIMethodParas);
		}
		if (convToUIMethod == null) {
			createdByMap.setLogicManager(this);
			createdByMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvCreatedByToItemUI);
		} else {
			createdByMap.setConvToUIMethod(convToUIMethod);
		}
		uiModelNodeMapList.add(createdByMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getUpdatedByNodeMapConfigureList(String baseNodeId, String convToUIMethod,
																		  Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[LogonUser: CreatedBy]
		UIModelNodeMapConfigure updatedByMap = new UIModelNodeMapConfigure();
		updatedByMap.setSeName(LogonUser.SENAME);
		updatedByMap.setNodeName(LogonUser.NODENAME);
		updatedByMap.setNodeInstID("DefUpdatedBy" + LogonUser.SENAME);
		updatedByMap.setBaseNodeInstID(baseNodeId);
		updatedByMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> updatedByConditionList = new ArrayList<>();
		SearchConfigConnectCondition updatedByCondition0 = new SearchConfigConnectCondition();
		updatedByCondition0.setSourceFieldName(IServiceEntityNodeFieldConstant.LASTUPDATEBY);
		updatedByCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		updatedByConditionList.add(updatedByCondition0);
		updatedByMap.setConnectionConditions(updatedByConditionList);
		updatedByMap.setServiceEntityManager(logonUserManager);
		if (convToUIMethodParas == null) {
			Class<?>[] updatedByConvToUIParas = {LogonUser.class, DocMatItemUIModel.class};
			updatedByMap.setConvToUIMethodParas(updatedByConvToUIParas);
		} else {
			updatedByMap.setConvToUIMethodParas(convToUIMethodParas);
		}
		if (convToUIMethod == null) {
			updatedByMap.setLogicManager(this);
			updatedByMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvUpdatedByToItemUI);
		} else {
			updatedByMap.setConvToUIMethod(convToUIMethod);
		}
		uiModelNodeMapList.add(updatedByMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDocDefCreateUpdateNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> resultList = new ArrayList<>();
		resultList.addAll(getDocCreatedMapConfigureList(baseNodeId, null, null));
		resultList.addAll(getDocUpdatedMapConfigureList(baseNodeId, null, null));
		return resultList;
	}

	public List<UIModelNodeMapConfigure> getDocCreatedMapConfigureList(String baseNodeId, String convToUIMethod,
																	   Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[LogonUser: CreatedBy]
		UIModelNodeMapConfigure createdByMap = new UIModelNodeMapConfigure();
		createdByMap.setSeName(LogonUser.SENAME);
		createdByMap.setNodeName(LogonUser.NODENAME);
		createdByMap.setNodeInstID("DefCreatedBy" + LogonUser.SENAME);
		createdByMap.setBaseNodeInstID(baseNodeId);
		createdByMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> createdByConditionList = new ArrayList<>();
		SearchConfigConnectCondition createdByCondition0 = new SearchConfigConnectCondition();
		createdByCondition0.setSourceFieldName(IServiceEntityNodeFieldConstant.CREATEDBY);
		createdByCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		createdByConditionList.add(createdByCondition0);
		createdByMap.setConnectionConditions(createdByConditionList);
		createdByMap.setServiceEntityManager(logonUserManager);
		if (convToUIMethodParas == null) {
			Class<?>[] createdByConvToUIParas = {LogonUser.class, DocumentUIModel.class};
			createdByMap.setConvToUIMethodParas(createdByConvToUIParas);
		} else {
			createdByMap.setConvToUIMethodParas(convToUIMethodParas);
		}
		if (convToUIMethod == null) {
			createdByMap.setLogicManager(this);
			createdByMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvCreatedByToDocUI);
		} else {
			createdByMap.setConvToUIMethod(convToUIMethod);
		}
		uiModelNodeMapList.add(createdByMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDocUpdatedMapConfigureList(String baseNodeId, String convToUIMethod,
																	   Class<?>[] convToUIMethodParas) {
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		// UI Model Configure of node:[LogonUser: CreatedBy]
		UIModelNodeMapConfigure updatedByMap = new UIModelNodeMapConfigure();
		updatedByMap.setSeName(LogonUser.SENAME);
		updatedByMap.setNodeName(LogonUser.NODENAME);
		updatedByMap.setNodeInstID("DefUpdatedBy" + LogonUser.SENAME);
		updatedByMap.setBaseNodeInstID(baseNodeId);
		updatedByMap.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		List<SearchConfigConnectCondition> updatedByConditionList = new ArrayList<>();
		SearchConfigConnectCondition updatedByCondition0 = new SearchConfigConnectCondition();
		updatedByCondition0.setSourceFieldName(IServiceEntityNodeFieldConstant.LASTUPDATEBY);
		updatedByCondition0.setTargetFieldName(IServiceEntityNodeFieldConstant.UUID);
		updatedByConditionList.add(updatedByCondition0);
		updatedByMap.setConnectionConditions(updatedByConditionList);
		updatedByMap.setServiceEntityManager(logonUserManager);
		if (convToUIMethodParas == null) {
			Class<?>[] updatedByConvToUIParas = {LogonUser.class, DocumentUIModel.class};
			updatedByMap.setConvToUIMethodParas(updatedByConvToUIParas);
		} else {
			updatedByMap.setConvToUIMethodParas(convToUIMethodParas);
		}
		if (convToUIMethod == null) {
			updatedByMap.setLogicManager(this);
			updatedByMap.setConvToUIMethod(DocFlowProxy.METHOD_ConvUpdatedByToDocUI);
		} else {
			updatedByMap.setConvToUIMethod(convToUIMethod);
		}
		uiModelNodeMapList.add(updatedByMap);
		return uiModelNodeMapList;
	}

	public List<UIModelNodeMapConfigure> getDefCreateUpdateNodeMapConfigureList(String baseNodeId) {
		List<UIModelNodeMapConfigure> resultList = new ArrayList<>();
		resultList.addAll(getCreatedByNodeMapConfigureList(baseNodeId, null, null));
		resultList.addAll(getUpdatedByNodeMapConfigureList(baseNodeId, null, null));
		return resultList;
	}

	/**
	 * Default Conversion method: reserved document to DocMatItemUIModel
	 *
	 * @param reservedDocument
	 * @param docMatItemUIModel
	 */
	public void convReservedDocToItemUI(ServiceEntityNode reservedDocument, DocMatItemUIModel docMatItemUIModel) {
		if (reservedDocument != null) {
			docMatItemUIModel.setReservedDocId(reservedDocument.getId());
			docMatItemUIModel.setReservedDocName(reservedDocument.getName());
		}
	}

	/**
	 * Returns a list of all system-defined non-business document types.
	 * These types must not be assigned to fields: `prevProfDocType` or `nextProfDocType`.
	 * @return a list of all system-defined non-business document types
	 */
	public static List<Integer> getSystemNoneBusinessDocTypes() {
		return Arrays.asList( IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER,
				IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY,
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY,
				IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER,
				IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER );
	}

	/**
	 * Determines whether the specified document type is a business document type.
	 * 	 A document type is considered non-business if it is included in {@link #getSystemNoneBusinessDocTypes()}.
	 *
	 * @param docType docType the document type identifier (nullable)
	 * @return true if the type is a business document type; false if it is a system-defined non-business type
	 */
	public static boolean checkBusinessDocType(Integer docType) {
		if (getSystemNoneBusinessDocTypes().contains(docType)) {
			return false;
		}
		return true;
	}

	/**
	 * Default Conversion method: previous document to DocumentUIModel
	 *
	 * @param prevProfDocument
	 * @param documentUIModel
	 */
	public void convPrevProfDocToDocUI(ServiceEntityNode prevProfDocument, DocumentUIModel documentUIModel) {
		if (prevProfDocument != null) {
			documentUIModel.setPrevProfDocId(prevProfDocument.getId());
			documentUIModel.setPrevProfDocName(prevProfDocument.getName());
		}
	}

	/**
	 * Default Conversion method: previous document to DocumentUIModel
	 *
	 * @param prevDocument
	 * @param documentUIModel
	 */
	public void convPrevDocToDocUI(ServiceEntityNode prevDocument, DocumentUIModel documentUIModel) {
		if (prevDocument != null) {
			documentUIModel.setPrevDocId(prevDocument.getId());
			documentUIModel.setPrevDocName(prevDocument.getName());
		}
	}

	/**
	 * Default Conversion method: next document to DocumentUIModel
	 *
	 * @param nextProfDocument
	 * @param documentUIModel
	 */
	public void convNextProfDocToDocUI(ServiceEntityNode nextProfDocument, DocumentUIModel documentUIModel) {
		if (nextProfDocument != null) {
			documentUIModel.setNextProfDocId(nextProfDocument.getId());
			documentUIModel.setNextProfDocName(nextProfDocument.getName());
		}
	}

	/**
	 * Default Conversion method: previous document to DocumentUIModel
	 *
	 * @param nextDocument
	 * @param documentUIModel
	 */
	public void convNextDocToDocUI(ServiceEntityNode nextDocument, DocumentUIModel documentUIModel) {
		if (nextDocument != null) {
			documentUIModel.setNextDocId(nextDocument.getId());
			documentUIModel.setNextDocName(nextDocument.getName());
		}
	}

	/**
	 * Default Conversion method: previous document to DocMatItemUIModel
	 *
	 * @param prevDocument
	 * @param docMatItemUIModel
	 */
	public void convPrevDocToItemUI(ServiceEntityNode prevDocument, DocMatItemUIModel docMatItemUIModel) {
		if (prevDocument != null) {
			docMatItemUIModel.setPrevDocId(prevDocument.getId());
			docMatItemUIModel.setPrevDocName(prevDocument.getName());
			if (prevDocument instanceof DocumentContent) {
				DocumentContent documentContent = (DocumentContent) prevDocument;
				docMatItemUIModel.setPrevDocStatus(documentContent.getStatus());
			}
		}
	}

	/**
	 * Default Conversion method: previous document to DocMatItemUIModel
	 *
	 * @param prevProfDocument
	 * @param docMatItemUIModel
	 */
	public void convPrevProfDocToItemUI(ServiceEntityNode prevProfDocument, DocMatItemUIModel docMatItemUIModel) {
		if (prevProfDocument != null) {
			docMatItemUIModel.setPrevProfDocId(prevProfDocument.getId());
			docMatItemUIModel.setPrevProfDocName(prevProfDocument.getName());
			if (prevProfDocument instanceof DocumentContent) {
				DocumentContent documentContent = (DocumentContent) prevProfDocument;
				docMatItemUIModel.setPrevProfDocStatus(documentContent.getStatus());
			}
		}
	}

	/**
	 * Default Conversion method: next document to DocMatItemUIModel
	 *
	 * @param nextDocument
	 * @param docMatItemUIModel
	 */
	public void convNextDocToItemUI(ServiceEntityNode nextDocument, DocMatItemUIModel docMatItemUIModel) {
		if (nextDocument != null) {
			docMatItemUIModel.setNextDocId(nextDocument.getId());
			docMatItemUIModel.setNextDocName(nextDocument.getName());
			if (nextDocument instanceof DocumentContent) {
				DocumentContent documentContent = (DocumentContent) nextDocument;
				docMatItemUIModel.setNextDocStatus(documentContent.getStatus());
			}
		}
	}

	/**
	 * Default Conversion method: next document to DocMatItemUIModel
	 *
	 * @param nextProfDocument
	 * @param docMatItemUIModel
	 */
	public void convNextProfDocToItemUI(ServiceEntityNode nextProfDocument, DocMatItemUIModel docMatItemUIModel) {
		if (nextProfDocument != null) {
			docMatItemUIModel.setNextProfDocId(nextProfDocument.getId());
			docMatItemUIModel.setNextProfDocName(nextProfDocument.getName());
			if (nextProfDocument instanceof DocumentContent) {
				DocumentContent documentContent = (DocumentContent) nextProfDocument;
				docMatItemUIModel.setNextProfDocStatus(documentContent.getStatus());
			}
		}
	}

	public Map<Integer, String> initDocTypeMap(LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (logonInfo != null) {
			Map<Integer, String> docTypeMap =
					serviceDocumentComProxy.getDocumentTypeMap(false, logonInfo.getLanguageCode());
			return docTypeMap;
		} else {
			Map<Integer, String> docTypeMap = serviceDocumentComProxy.getDocumentTypeMap(false);
			return docTypeMap;
		}
	}

	/**
	 * Utility Method to Convert Document(service entity node) to Document UI
	 * model
	 */
	public void convDocumentToUI(DocumentContent documentContent, DocumentUIModel documentUIModel,
								 LogonInfo logonInfo) {
		if (documentContent != null && documentUIModel != null) {
			if (!ServiceEntityStringHelper.checkNullString(documentContent.getUuid())) {
				documentUIModel.setUuid(documentContent.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(documentContent.getParentNodeUUID())) {
				documentUIModel.setParentNodeUUID(documentContent.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(documentContent.getRootNodeUUID())) {
				documentUIModel.setRootNodeUUID(documentContent.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(documentContent.getClient())) {
				documentUIModel.setClient(documentContent.getClient());
			}
			documentUIModel.setId(documentContent.getId());
			documentUIModel.setName(documentContent.getName());
			documentUIModel.setStatus(documentContent.getStatus());
			documentUIModel.setCreatedByUUID(documentContent.getCreatedBy());
			documentUIModel.setUpdatedByUUID(documentContent.getLastUpdateBy());
			documentUIModel.setPrevDocType(documentContent.getPrevDocType());
			setDocTypeMap(documentUIModel, documentContent.getPrevDocType(), DOCFLOW_DIREC_PREV, logonInfo);
			documentUIModel.setPrevDocUUID(documentContent.getPrevDocUUID());
			documentUIModel.setPrevProfDocType(documentContent.getPrevProfDocType());
			setDocTypeMap(documentUIModel, documentContent.getPrevProfDocType(), DOCFLOW_DIREC_PREV_PROF, logonInfo);
			documentUIModel.setPrevProfDocUUID(documentContent.getPrevProfDocUUID());
			documentUIModel.setNextDocType(documentContent.getNextDocType());
			setDocTypeMap(documentUIModel, documentContent.getNextDocType(), DOCFLOW_DIREC_NEXT, logonInfo);
			documentUIModel.setNextDocUUID(documentContent.getNextDocUUID());
			documentUIModel.setNextProfDocUUID(documentContent.getNextProfDocUUID());
			documentUIModel.setNextProfDocType(documentContent.getNextProfDocType());
			setDocTypeMap(documentUIModel, documentContent.getNextProfDocType(), DOCFLOW_DIREC_NEXT_PROF, logonInfo);
			if (documentContent.getCreatedTime() != null) {
				documentUIModel.setCreatedDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(documentContent.getCreatedTime()));
				documentUIModel.setCreatedTime(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(documentContent.getCreatedTime()));
			}
			if (documentContent.getLastUpdateTime() != null) {
				documentUIModel.setUpdatedDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(documentContent.getLastUpdateTime()));
				documentUIModel.setUpdatedTime(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(documentContent.getLastUpdateTime()));
			}
		}
	}

	private void setDocTypeMap(DocumentUIModel documentUIModel, int docType, int docFlowDirection, LogonInfo logonInfo) {
		if (docType > 0 && logonInfo != null) {
			try {
				Map<Integer, String> docTypeMap = initDocTypeMap(logonInfo);
				if (docFlowDirection == DOCFLOW_DIREC_PREV) {
					documentUIModel.setPrevDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_PREV_PROF) {
					documentUIModel.setPrevProfDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_NEXT) {
					documentUIModel.setNextDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_NEXT_PROF) {
					documentUIModel.setNextProfDocTypeValue(docTypeMap.get(docType));
				}
			} catch (ServiceEntityInstallationException e) {
				// just continue
			}
		}
	}

	public void convUIToDocument(DocumentUIModel documentUIModel, DocumentContent rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(documentUIModel.getUuid())) {
			rawEntity.setUuid(documentUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(documentUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(documentUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(documentUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(documentUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(documentUIModel.getClient())) {
			rawEntity.setClient(documentUIModel.getClient());
		}
		rawEntity.setId(documentUIModel.getId());
		rawEntity.setName(documentUIModel.getName());
		rawEntity.setNote(documentUIModel.getNote());
	}

	/**
	 * Basic Utility method to convert common fields from backend SENode to UIModel
	 */
	public static void convServiceEntityNodeToUIModel(ServiceEntityNode serviceEntityNode, SEUIComModel uiModel) {
		if (!ServiceEntityStringHelper.checkNullString(serviceEntityNode.getUuid())) {
			uiModel.setUuid(serviceEntityNode.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(serviceEntityNode.getParentNodeUUID())) {
			uiModel.setParentNodeUUID(serviceEntityNode.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(serviceEntityNode.getRootNodeUUID())) {
			uiModel.setRootNodeUUID(serviceEntityNode.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(serviceEntityNode.getClient())) {
			uiModel.setClient(serviceEntityNode.getClient());
		}
		if (serviceEntityNode.getCreatedTime() != null) {
			uiModel.setCreatedDate(
					DefaultDateFormatConstant.DATE_FORMAT.format(serviceEntityNode.getCreatedTime()));
			uiModel.setCreatedTime(
					DefaultDateFormatConstant.DATE_MIN_FORMAT.format(serviceEntityNode.getCreatedTime()));
		}
		if (serviceEntityNode.getLastUpdateTime() != null) {
			uiModel.setUpdatedDate(
					DefaultDateFormatConstant.DATE_FORMAT.format(serviceEntityNode.getLastUpdateTime()));
			uiModel.setUpdatedTime(
					DefaultDateFormatConstant.DATE_MIN_FORMAT.format(serviceEntityNode.getLastUpdateTime()));
		}
		uiModel.setId(serviceEntityNode.getId());
		uiModel.setName(serviceEntityNode.getName());
		uiModel.setNote(serviceEntityNode.getNote());
	}

	public static void convUIToServiceEntityNode(SEUIComModel uiModel,
													 ServiceEntityNode rawEntity){
		if (!ServiceEntityStringHelper
				.checkNullString(uiModel.getUuid())) {
			rawEntity.setUuid(uiModel.getUuid());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(uiModel
						.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(uiModel
					.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(uiModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(uiModel
					.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper
				.checkNullString(uiModel.getClient())) {
			rawEntity.setClient(uiModel.getClient());
		}
		rawEntity.setId(uiModel.getId());
		rawEntity.setName(uiModel.getName());
		rawEntity.setNote(uiModel.getNote());
	}

	/**
	 * Utility Method to Convert Doc Mat item to UI item model
	 */
	public void convDocMatItemToUI(DocMatItemNode docMatItemNode, DocMatItemUIModel docMatItemUIModel,
								   LogonInfo logonInfo) {
		if (docMatItemNode != null && docMatItemUIModel != null) {
			if (!ServiceEntityStringHelper.checkNullString(docMatItemNode.getUuid())) {
				docMatItemUIModel.setUuid(docMatItemNode.getUuid());
			}
			if (!ServiceEntityStringHelper.checkNullString(docMatItemNode.getParentNodeUUID())) {
				docMatItemUIModel.setParentNodeUUID(docMatItemNode.getParentNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(docMatItemNode.getRootNodeUUID())) {
				docMatItemUIModel.setRootNodeUUID(docMatItemNode.getRootNodeUUID());
			}
			if (!ServiceEntityStringHelper.checkNullString(docMatItemNode.getClient())) {
				docMatItemUIModel.setClient(docMatItemNode.getClient());
			}
			docMatItemUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(docMatItemNode.getAmount()));
			docMatItemUIModel.setRefUnitUUID(docMatItemNode.getRefUnitUUID());
			docMatItemUIModel.setRefMaterialSKUUUID(docMatItemNode.getRefMaterialSKUUUID());
			docMatItemUIModel.setCreatedByUUID(docMatItemNode.getCreatedBy());
			if (docMatItemNode.getCreatedTime() != null) {
				docMatItemUIModel.setCreatedDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(docMatItemNode.getCreatedTime()));
				docMatItemUIModel.setCreatedTime(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(docMatItemNode.getCreatedTime()));
			}
			docMatItemUIModel.setUpdatedByUUID(docMatItemNode.getLastUpdateBy());
			if (docMatItemNode.getLastUpdateTime() != null) {
				docMatItemUIModel.setUpdatedDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(docMatItemNode.getLastUpdateTime()));
				docMatItemUIModel.setUpdatedTime(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(docMatItemNode.getLastUpdateTime()));
			}
			docMatItemUIModel.setProductionBatchNumber(docMatItemNode.getProductionBatchNumber());
			docMatItemUIModel.setPurchaseBatchNumber(docMatItemNode.getPurchaseBatchNumber());
			docMatItemUIModel.setItemPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale4(docMatItemNode.getItemPrice()));
			docMatItemUIModel.setUnitPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale4(docMatItemNode.getUnitPrice()));
			docMatItemUIModel.setItemPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale4(docMatItemNode.getItemPriceDisplay()));
			docMatItemUIModel.setUnitPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale4(docMatItemNode.getUnitPriceDisplay()));
			docMatItemUIModel.setNextDocMatItemUUID(docMatItemNode.getNextDocMatItemUUID());
			docMatItemUIModel.setMaterialStatus(docMatItemNode.getMaterialStatus());
			if (logonInfo != null) {
				try {
					Map<Integer, String> traceStatusMap =
							materialStockKeepUnitManager.initTraceStatusMap(logonInfo.getLanguageCode());
					docMatItemUIModel.setMaterialStatusValue(traceStatusMap.get(docMatItemNode.getMaterialStatus()));
				} catch (ServiceEntityInstallationException e) {
					// just continue
				}
			}
			docMatItemUIModel.setNextDocType(docMatItemNode.getNextDocType());
			setItemDocTypeMap(docMatItemUIModel, docMatItemNode.getNextDocType(), DOCFLOW_DIREC_NEXT, logonInfo);
			docMatItemUIModel.setPrevDocType(docMatItemNode.getPrevDocType());
			docMatItemUIModel.setPrevDocMatItemUUID(docMatItemNode.getPrevDocMatItemUUID());
			setItemDocTypeMap(docMatItemUIModel, docMatItemNode.getPrevDocType(), DOCFLOW_DIREC_PREV, logonInfo);
			docMatItemUIModel.setPrevProfDocType(docMatItemNode.getPrevProfDocType());
			docMatItemUIModel.setPrevProfDocMatItemUUID(docMatItemNode.getPrevProfDocMatItemUUID());
			setItemDocTypeMap(docMatItemUIModel, docMatItemNode.getPrevProfDocType(), DOCFLOW_DIREC_PREV_PROF, logonInfo);
			docMatItemUIModel.setNextProfDocType(docMatItemNode.getNextProfDocType());
			docMatItemUIModel.setNextProfDocMatItemUUID(docMatItemNode.getNextProfDocMatItemUUID());
			setItemDocTypeMap(docMatItemUIModel, docMatItemNode.getNextProfDocType(), DOCFLOW_DIREC_NEXT_PROF, logonInfo);
			docMatItemUIModel.setItemStatus(docMatItemNode.getItemStatus());
			docMatItemUIModel.setReservedMatItemUUID(docMatItemNode.getReservedMatItemUUID());
			docMatItemUIModel.setReservedDocType(docMatItemNode.getReservedDocType());
			docMatItemUIModel.setHomeDocumentType(docMatItemNode.getHomeDocumentType());
			try {
				Map<Integer, String> reservedDocTypeMap = initDocTypeMap(logonInfo);
				docMatItemUIModel.setReservedDocTypeValue(reservedDocTypeMap.get(docMatItemNode.getReservedDocType()));
			} catch (ServiceEntityInstallationException e) {
				// just continue
			}
			if (docMatItemNode.getCreatedTime() != null) {
				docMatItemUIModel.setCreatedDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(docMatItemNode.getCreatedTime()));
			}
		}
	}

	private void setItemDocTypeMap(DocMatItemUIModel docMatItemUIModel, int docType, int docFlowDirection, LogonInfo logonInfo) {
		if (docType > 0) {
			try {
				Map<Integer, String> docTypeMap = initDocTypeMap(logonInfo);
				if (docFlowDirection == DOCFLOW_DIREC_PREV) {
					docMatItemUIModel.setPrevDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_PREV_PROF) {
					docMatItemUIModel.setPrevProfDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_NEXT) {
					docMatItemUIModel.setNextDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_DIREC_NEXT_PROF) {
					docMatItemUIModel.setNextProfDocTypeValue(docTypeMap.get(docType));
				}
				if (docFlowDirection == DOCFLOW_TO_RESERVED_BY) {
					docMatItemUIModel.setReservedDocTypeValue(docTypeMap.get(docType));
				}
			} catch (ServiceEntityInstallationException e) {
				// just continue
			}
		}
	}

	/**
	 * Utility Method to Convert Doc Mat item UI to item model
	 */
	public void convUIToDocMatItem(DocMatItemUIModel docMatItemUIModel, DocMatItemNode rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getUuid())) {
			rawEntity.setUuid(docMatItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(docMatItemUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(docMatItemUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getClient())) {
			rawEntity.setClient(docMatItemUIModel.getClient());
		}
		if (docMatItemUIModel.getNextDocType() > 0) {
			rawEntity.setNextDocType(docMatItemUIModel.getNextDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getNextDocMatItemUUID())) {
			rawEntity.setNextDocMatItemUUID(docMatItemUIModel.getNextDocMatItemUUID());
		}
		if (docMatItemUIModel.getReservedDocType() > 0) {
			rawEntity.setReservedDocType(docMatItemUIModel.getReservedDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getReservedMatItemUUID())) {
			rawEntity.setReservedMatItemUUID(docMatItemUIModel.getReservedMatItemUUID());
		}
		if (docMatItemUIModel.getPrevDocType() > 0) {
			rawEntity.setPrevDocType(docMatItemUIModel.getPrevDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getPrevDocMatItemUUID())) {
			rawEntity.setPrevDocMatItemUUID(docMatItemUIModel.getPrevDocMatItemUUID());
		}
		if (docMatItemUIModel.getPrevProfDocType() > 0) {
			rawEntity.setPrevProfDocType(docMatItemUIModel.getPrevProfDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getPrevProfDocMatItemUUID())) {
			rawEntity.setPrevProfDocMatItemUUID(docMatItemUIModel.getPrevProfDocMatItemUUID());
		}
		if (docMatItemUIModel.getNextProfDocType() > 0) {
			rawEntity.setNextProfDocType(docMatItemUIModel.getNextProfDocType());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getNextProfDocMatItemUUID())) {
			rawEntity.setNextProfDocMatItemUUID(docMatItemUIModel.getNextProfDocMatItemUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getProductionBatchNumber())) {
			rawEntity.setProductionBatchNumber(docMatItemUIModel.getProductionBatchNumber());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getPurchaseBatchNumber())) {
			rawEntity.setPurchaseBatchNumber(docMatItemUIModel.getPurchaseBatchNumber());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getId())) {
			rawEntity.setId(docMatItemUIModel.getId());
		}
		if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getNote())) {
			rawEntity.setNote(docMatItemUIModel.getNote());
		}
		rawEntity.setItemPrice(docMatItemUIModel.getItemPrice());
		rawEntity.setUnitPrice(docMatItemUIModel.getUnitPrice());
		rawEntity.setItemPriceDisplay(docMatItemUIModel.getItemPriceDisplay());
		rawEntity.setUnitPriceDisplay(docMatItemUIModel.getUnitPriceDisplay());
		rawEntity.setRefMaterialSKUUUID(docMatItemUIModel.getRefMaterialSKUUUID());
		rawEntity.setAmount(docMatItemUIModel.getAmount());
		rawEntity.setRefUnitUUID(docMatItemUIModel.getRefUnitUUID());
	}

	/**
	 * Utility Method to Convert Material SKU / Registered Product to item model
	 */
	public void convParentDocToItemUI(DocumentContent parentDocument, DocMatItemUIModel docMatItemUIModel,
									  LogonInfo logonInfo) {
		if (parentDocument != null) {
			docMatItemUIModel.setParentNodeUUID(parentDocument.getUuid());
			docMatItemUIModel.setParentDocId(parentDocument.getId());
			docMatItemUIModel.setParentDocName(parentDocument.getName());
			docMatItemUIModel.setParentDocStatus(parentDocument.getStatus());
			if (logonInfo != null) {
				Map<Integer, String> statusMap = null;
				try {
					statusMap = this.getStatusMap(docMatItemUIModel.getHomeDocumentType(), logonInfo.getLanguageCode());
					if (statusMap != null) {
						docMatItemUIModel.setParentDocStatusValue(statusMap.get(parentDocument.getStatus()));
					}
				} catch (ServiceEntityInstallationException | DocActionException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
				}
			}
		}
	}

	/**
	 * Utility Method to Convert Material SKU / Registered Product to item model
	 */
	public void convMaterialSKUToItemUI(MaterialStockKeepUnit materialStockKeepUnit,
										DocMatItemUIModel docMatItemUIModel) {
		convMaterialSKUToItemUI(materialStockKeepUnit, docMatItemUIModel, null);
	}

	/**
	 * Utility Method to Convert Material SKU / Registered Product to item model
	 */
	public void convMaterialSKUToItemUI(MaterialStockKeepUnit materialStockKeepUnit,
										DocMatItemUIModel docMatItemUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			// Avoid over-written the manual value
			if (ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getRefMaterialSKUName())) {
				docMatItemUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
			}
			// Avoid over-written the manual value
			if (ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getRefMaterialSKUId())) {
				docMatItemUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
			}
			// Avoid over-written the manual value
			if (ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getProductionBatchNumber())) {
				docMatItemUIModel.setProductionBatchNumber(materialStockKeepUnit.getProductionBatchNumber());
			}
			// Avoid over-written the manual value
			if (ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getPackageStandard())) {
				docMatItemUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
			}

			if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getRefUnitUUID())) {
				try {
					MaterialStockKeepUnitManager.AmountUnion amountUnion =
							materialStockKeepUnitManager.getAmountUnion(docMatItemUIModel.getRefMaterialSKUUUID(),
									docMatItemUIModel.getRefUnitUUID(), docMatItemUIModel.getAmount(),
									materialStockKeepUnit.getClient());
					docMatItemUIModel.setRefUnitName(amountUnion.getRefUnitName());
					docMatItemUIModel.setAmountLabel(amountUnion.getAmountLabel());
				} catch (MaterialException | ServiceEntityConfigureException e) {
					// just continue
				}
			}
			docMatItemUIModel.setMaterialStatus(materialStockKeepUnit.getTraceStatus());
			docMatItemUIModel.setRefMaterialTraceMode(materialStockKeepUnit.getTraceMode());
			if (logonInfo != null) {
				try {
					Map<Integer, String> traceStatusMap =
							materialStockKeepUnitManager.initTraceStatusMap(logonInfo.getLanguageCode());
					docMatItemUIModel.setMaterialStatusValue(
							traceStatusMap.get(materialStockKeepUnit.getTraceStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "traceStatus"));
				}
			}
			docMatItemUIModel.setRefMaterialTemplateUUID(materialStockKeepUnit.getUuid());
			// In case Registered Product
			if (RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)) {
				RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
				docMatItemUIModel.setSerialId(registeredProduct.getSerialId());
				docMatItemUIModel.setRefMaterialTemplateUUID(registeredProduct.getRefMaterialSKUUUID());
			}
		}
	}

	/**
	 * Utility Method to Convert CreatedBy (LogonUser) to item model
	 */
	public void convCreatedByToItemUI(LogonUser createdBy, DocMatItemUIModel docMatItemUIModel) {
		if (createdBy != null) {
			docMatItemUIModel.setCreatedById(createdBy.getId());
			docMatItemUIModel.setCreatedByName(createdBy.getName());
		}
	}

	/**
	 * Utility Method to Convert CreatedBy (LogonUser) to item model
	 */
	public void convUpdatedByToItemUI(LogonUser updatedBy, DocMatItemUIModel docMatItemUIModel) {
		if (updatedBy != null) {
			docMatItemUIModel.setUpdatedById(updatedBy.getId());
			docMatItemUIModel.setUpdatedByName(updatedBy.getName());
		}
	}

	/**
	 * Utility Method to Convert CreatedBy (LogonUser) to document UI model
	 */
	public void convCreatedByToDocUI(LogonUser createdBy, DocumentUIModel documentUIModel) {
		if (createdBy != null) {
			documentUIModel.setCreatedById(createdBy.getId());
			documentUIModel.setCreatedByName(createdBy.getName());
		}
	}

	/**
	 * Utility Method to Convert CreatedBy (LogonUser) to document UI model
	 */
	public void convUpdatedByToDocUI(LogonUser updatedBy, DocumentUIModel documentUIModel) {
		if (updatedBy != null) {
			documentUIModel.setUpdatedById(updatedBy.getId());
			documentUIModel.setUpdatedByName(updatedBy.getName());
		}
	}

	/**
	 * Utility method to split doc item list by checking the quality flag
	 *
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> splitDocItemListByMaterialQualityFlag(List<ServiceEntityNode> rawDocItemList,
																		 List<ServiceEntityNode> rawMaterialSKUList,
																		 int qualityInspectFlag)
            throws ServiceEntityConfigureException, DocActionException {
		if (ServiceCollectionsHelper.checkNullList(rawDocItemList)) {
			return null;
		}
		List<ServiceEntityNode> resultList = new ArrayList<>();
		ServiceCollectionsHelper.traverseListInterrupt(rawDocItemList, seNode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			MaterialStockKeepUnit materialStockKeepUnit =
					(MaterialStockKeepUnit) ServiceCollectionsHelper.filterSENodeOnline(
							docMatItemNode.getRefMaterialSKUUUID(), rawMaterialSKUList);
            MaterialStockKeepUnit tempMaterialSKU =
                    null;
            try {
                tempMaterialSKU = materialStockKeepUnitManager.getRefTemplateMaterialSKU(materialStockKeepUnit);
            } catch (ServiceEntityConfigureException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
            if (tempMaterialSKU.getQualityInspectFlag() == qualityInspectFlag) {
				resultList.add(docMatItemNode);
			}
			return true;
		});
		return resultList;
	}

	/**
	 * Get Reference Material SKU List in batch by doc mat item list
	 *
	 * @param docMatItemList
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRefMaterialSKUList(List<ServiceEntityNode> docMatItemList,
														 Function<DocMatItemNode, Boolean> filterFunction,
														 String client) throws ServiceEntityConfigureException, DocActionException {
		List<Object> materialUUIDList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return null;
		}
		ServiceCollectionsHelper.traverseListInterrupt(docMatItemList, rawSENode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) rawSENode;
			if (filterFunction != null) {
				if (!filterFunction.apply(docMatItemNode)) {
					return true;
				}
			}
			materialUUIDList.add(docMatItemNode.getRefMaterialSKUUUID());
			return true;
		});
		if (ServiceCollectionsHelper.checkNullList(materialUUIDList)) {
			return null;
		}
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key = new ServiceBasicKeyStructure();
		key.setKeyName(IServiceEntityNodeFieldConstant.UUID);
		key.setMultipleValueList(materialUUIDList);
		keyList.add(key);
		List<ServiceEntityNode> rawMaterialSKUList =
				materialStockKeepUnitManager.getEntityNodeListByKeyList(keyList, MaterialStockKeepUnit.NODENAME, client,
						null);
		return rawMaterialSKUList;
	}

	/**
	 * Core Logic to check if current doc item's material could be accountable .
	 *
	 * @param materialStatus
	 * @return
	 */
	public static boolean checkAccountableMaterialStatus(int materialStatus) {
        return materialStatus == MaterialStockKeepUnit.TRACESTATUS_ACTIVE ||
                materialStatus == MaterialStockKeepUnit.TRACESTATUS_INIT ||
                materialStatus == MaterialStockKeepUnit.TRACESTATUS_INSERVICE;
    }

	public static class CrossCreateContextDocOption {

		private int copyModel;

		public static int COPYMODEL_OVERWRITE = 1;

		public static int COPYMODEL_ADDTIONAL = 2;

		public static int COPYMODEL_SKIP = 3;

		public CrossCreateContextDocOption() {
			this.copyModel = COPYMODEL_OVERWRITE;
		}

		public CrossCreateContextDocOption(int copyModel) {
			this.copyModel = copyModel;
		}

		public int getCopyModel() {
			return copyModel;
		}

		public void setCopyModel(int copyModel) {
			this.copyModel = copyModel;
		}

		public static boolean checkNeedCopyProperties(CrossCreateContextDocOption crossCreateContextDocOption) {
            return crossCreateContextDocOption.getCopyModel() != COPYMODEL_SKIP;
		}
	}

	/**
	 * Copies relevant information from a previous document material item (prevDocMatItemNode)
	 * to a newly created target document material item (nextDocMatItemNode).
	 *
	 * Note: This method does NOT copy UUIDs or document type information, as these values
	 * are intended to be managed separately (handled specifically in `prevNextDocItemProxy.addPrevByNext`).
	 *
	 * @param prevDocMatItemNode the source document material item node to copy information from
	 * @param nextDocMatItemNode the target document material item node to copy information to
	 * @return the updated target document material item node (nextDocMatItemNode), or null if input nodes are null
	 */
	public DocMatItemNode copyPrevDocMatItemToNextDoc(DocMatItemNode prevDocMatItemNode,
													  DocMatItemNode nextDocMatItemNode) {
		return copyPrevDocMatItemToNextDoc(prevDocMatItemNode, nextDocMatItemNode, new CrossCreateContextDocOption());
	}

	/**
	 * Copies relevant information from a previous document material item (prevDocMatItemNode)
	 * to a newly created target document material item (nextDocMatItemNode).
	 *
	 * Note: This method does NOT copy UUIDs or document type information, as these values
	 * are intended to be managed separately (handled specifically in `prevNextDocItemProxy.addPrevByNext`).
	 *
	 * @param prevDocMatItemNode the source document material item node to copy information from
	 * @param nextDocMatItemNode the target document material item node to copy information to
	 * @param crossCreateContextDocOption additional context or options that may influence copy behavior (currently unused)
	 * @return the updated target document material item node (nextDocMatItemNode), or null if input nodes are null
	 */
	public DocMatItemNode copyPrevDocMatItemToNextDoc(DocMatItemNode prevDocMatItemNode,
													  DocMatItemNode nextDocMatItemNode, CrossCreateContextDocOption crossCreateContextDocOption) {
		// Attention: This method does NOT copy UUIDs or document type information, as these values
		// are intended to be managed separately (handled specifically in `prevNextDocItemProxy.addPrevByNext`).
		if (nextDocMatItemNode != null && prevDocMatItemNode != null) {
			nextDocMatItemNode.setAmount(prevDocMatItemNode.getAmount());
			nextDocMatItemNode.setRefUnitUUID(prevDocMatItemNode.getRefUnitUUID());
			// Copy reserved document type and material item UUID if applicable
			if (prevDocMatItemNode.getReservedDocType() > 0) {
				nextDocMatItemNode.setReservedDocType(prevDocMatItemNode.getReservedDocType());
				nextDocMatItemNode.setReservedMatItemUUID(prevDocMatItemNode.getReservedMatItemUUID());
			}
			//TODO add logic to using crossCreateContextDocOption
			nextDocMatItemNode.setRefUUID(prevDocMatItemNode.getRefUUID());
			nextDocMatItemNode.setRefMaterialSKUUUID(prevDocMatItemNode.getRefMaterialSKUUUID());
			nextDocMatItemNode.setRefUUID(prevDocMatItemNode.getRefMaterialSKUUUID());
			nextDocMatItemNode.setProductionBatchNumber(prevDocMatItemNode.getProductionBatchNumber());
			nextDocMatItemNode.setPurchaseBatchNumber(prevDocMatItemNode.getPurchaseBatchNumber());
			nextDocMatItemNode.setItemPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getItemPrice()));
			nextDocMatItemNode.setUnitPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getUnitPrice()));
			nextDocMatItemNode.setItemPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getItemPriceDisplay()));
			nextDocMatItemNode.setUnitPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getUnitPriceDisplay()));
			nextDocMatItemNode.setCurrencyCode(prevDocMatItemNode.getCurrencyCode());
			nextDocMatItemNode.setMaterialStatus(prevDocMatItemNode.getMaterialStatus());
		}
		return nextDocMatItemNode;
	}

	public DocMatItemNode buildItemPrevNextRelationship(DocMatItemNode prevDocMatItemNode,
														   DocMatItemNode nextDocMatItemNode, DocumentMatItemBatchGenRequest genRequest,
														   SerialLogonInfo serialLogonInfo) throws DocActionException {
		return buildItemPrevNextRelationship(prevDocMatItemNode, nextDocMatItemNode, genRequest, serialLogonInfo, new CrossCreateContextDocOption());
	}

		/**
         * Establishes a previous–next relationship between two document material items.
         *
         * This method performs the following steps:
         * 1. Copies properties from the previous document material item to the next item.
         * 2. Creates and persists the previous–next relationship between the two items.
         * 3. Executes custom cross-document conversion logic (if applicable) for both document types.
         *
         * @param prevDocMatItemNode the previous document material item node
         * @param nextDocMatItemNode the next document material item node to update and link
         * @param serialLogonInfo context or session information necessary for the operation
         * @return the updated next document material item node (`nextDocMatItemNode`)
         * @throws DocActionException if an error occurs during property copying, relationship linking, or custom conversion
         */
	public DocMatItemNode buildItemPrevNextRelationship(DocMatItemNode prevDocMatItemNode,
														DocMatItemNode nextDocMatItemNode, DocumentMatItemBatchGenRequest genRequest, SerialLogonInfo serialLogonInfo,
														DocFlowProxy.CrossCreateContextDocOption crossCreateContextDocOption) throws DocActionException {
		// Step 1: Standard logic to copy properties from prev document item to next document item.
		if (CrossCreateContextDocOption.checkNeedCopyProperties(crossCreateContextDocOption)) {
			copyPrevDocMatItemToNextDoc(prevDocMatItemNode,
					nextDocMatItemNode, crossCreateContextDocOption);
		}
		// Step 2: Build prev-next relationship
		prevNextDocItemProxy.addPrevByNext(prevDocMatItemNode, nextDocMatItemNode, serialLogonInfo);

		// Step 3: Execute customized logic to convert prev-next document item in prev/next doc crossDocConvert request
		int prevDocType = prevDocMatItemNode.getHomeDocumentType();
		int nextDocType = nextDocMatItemNode.getHomeDocumentType();
		DocActionExecutionProxy<?, ?, ?> prevDocActionProxy = docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(prevDocType);
		DocActionExecutionProxy<?, ?, ?> nextDocActionProxy = docActionExecutionProxyFactory.getDocActionExecutionProxyByDocType(nextDocType);
		// In case customized update properties in prev doc DocMatItemNode
		if (prevDocActionProxy != null && prevDocActionProxy.getCrossDocCovertRequest() != null ) {
			CrossDocConvertRequest.IConvertToTargetItem convertToSourceItem = prevDocActionProxy.getCrossDocCovertRequest().getCovertToSourceItem();
			if (convertToSourceItem != null) {
				convertToSourceItem.execute(new CrossDocConvertRequest.ConvertItemContext(prevDocMatItemNode, nextDocMatItemNode, genRequest));
			}
		}
		// In case customized update properties in next doc DocMatItemNode
		if (nextDocActionProxy != null && nextDocActionProxy.getCrossDocCovertRequest() != null ) {
			CrossDocConvertRequest.IConvertToTargetItem convertToTargetItem = nextDocActionProxy.getCrossDocCovertRequest().getCovertToTargetItem();
			if (convertToTargetItem != null) {
				convertToTargetItem.execute(new CrossDocConvertRequest.ConvertItemContext(prevDocMatItemNode, nextDocMatItemNode, genRequest));
			}
		}
		return nextDocMatItemNode;
	}

	public DocMatItemNode copyPrevProfDocMatItemToNextProfDoc(DocMatItemNode prevDocMatItemNode, int prevDocType,
															  DocMatItemNode nextDocMatItemNode, int nextDocType) {
		return copyPrevProfDocMatItemToNextProfDoc(prevDocMatItemNode, prevDocType, nextDocMatItemNode, nextDocType,
				new CrossCreateContextDocOption());
	}

	/**
	 * Provide default logic to copy prev profession doc material item instance as
	 * previous item to target in-bound item
	 *
	 * @param nextDocMatItemNode
	 * @param prevDocMatItemNode
	 * @param prevDocType
	 * @return
	 */
	public DocMatItemNode copyPrevProfDocMatItemToNextProfDoc(DocMatItemNode prevDocMatItemNode, int prevDocType,
															  DocMatItemNode nextDocMatItemNode, int nextDocType,
															  CrossCreateContextDocOption crossCreateContextDocOption) {
		if (nextDocMatItemNode != null && prevDocMatItemNode != null) {
			nextDocMatItemNode.setAmount(prevDocMatItemNode.getAmount());
			nextDocMatItemNode.setRefUnitUUID(prevDocMatItemNode.getRefUnitUUID());
			nextDocMatItemNode.setPrevProfDocType(prevDocType);
			nextDocMatItemNode.setPrevProfDocMatItemUUID(prevDocMatItemNode.getUuid());
			if (prevDocMatItemNode.getReservedDocType() > 0) {
				nextDocMatItemNode.setReservedDocType(prevDocMatItemNode.getReservedDocType());
				nextDocMatItemNode.setReservedMatItemUUID(prevDocMatItemNode.getReservedMatItemUUID());
			}
			//TODO add logic to using crossCreateContextDocOption
			nextDocMatItemNode.setRefUUID(prevDocMatItemNode.getRefUUID());
			nextDocMatItemNode.setRefMaterialSKUUUID(prevDocMatItemNode.getRefMaterialSKUUUID());
			nextDocMatItemNode.setRefUUID(prevDocMatItemNode.getRefMaterialSKUUUID());
			nextDocMatItemNode.setProductionBatchNumber(prevDocMatItemNode.getProductionBatchNumber());
			nextDocMatItemNode.setPurchaseBatchNumber(prevDocMatItemNode.getPurchaseBatchNumber());
			nextDocMatItemNode.setItemPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getItemPrice()));
			nextDocMatItemNode.setUnitPrice(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getUnitPrice()));
			nextDocMatItemNode.setItemPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getItemPriceDisplay()));
			nextDocMatItemNode.setUnitPriceDisplay(
					ServiceEntityDoubleHelper.trancateDoubleScale2(prevDocMatItemNode.getUnitPriceDisplay()));
			nextDocMatItemNode.setCurrencyCode(prevDocMatItemNode.getCurrencyCode());
			nextDocMatItemNode.setMaterialStatus(prevDocMatItemNode.getMaterialStatus());
			prevDocMatItemNode.setNextProfDocMatItemUUID(nextDocMatItemNode.getUuid());
			prevDocMatItemNode.setNextProfDocType(nextDocType);
		}
		return nextDocMatItemNode;
	}

	/**
	 * Copy more doc mat item instance with same parent, root and other information except for uuid
	 */
	public DocMatItemNode duplicateDocMatItemNode(DocMatItemNode sourceDocMatItem) {
		DocMatItemNode targetDocMatItem = (DocMatItemNode) sourceDocMatItem.clone();
		targetDocMatItem.setUuid(UUID.randomUUID().toString());
		return targetDocMatItem;
	}

	public static void copyServiceEntityNodeMutual(ServiceEntityNode sourceNode, ServiceEntityNode targetNode,
												 boolean fullCover) {
		if (fullCover) {
			// In case all the unique information need to be copied
			targetNode.setUuid(sourceNode.getUuid());
			targetNode.setParentNodeUUID(sourceNode.getParentNodeUUID());
			targetNode.setRootNodeUUID(sourceNode.getRootNodeUUID());
			targetNode.setNodeName(sourceNode.getNodeName());
			targetNode.setServiceEntityName(sourceNode.getServiceEntityName());
		}
		targetNode.setClient(sourceNode.getClient());
		targetNode.setId(sourceNode.getId());
		targetNode.setName(sourceNode.getName());
		targetNode.setNote(sourceNode.getNote());
	}

	public static void copyDocumentContentMutual(DocumentContent sourceDocument, DocumentContent targetDocument,
												 boolean fullCover) {
		copyServiceEntityNodeMutual(sourceDocument, targetDocument, fullCover);
		targetDocument.setStatus(sourceDocument.getStatus());
		targetDocument.setPriorityCode(sourceDocument.getPriorityCode());
	}

	/**
	 * Just Copy one doc mat item to another
	 *
	 * @param sourceDocMatItem
	 * @param targetDocMatItem
	 */
	public static void copyDocMatItemMutual(DocMatItemNode sourceDocMatItem, DocMatItemNode targetDocMatItem,
											boolean fullCover) {
		if (sourceDocMatItem == null || targetDocMatItem == null) {
			return;
		}
		targetDocMatItem.setAmount(sourceDocMatItem.getAmount());
		targetDocMatItem.setRefUnitUUID(sourceDocMatItem.getRefUnitUUID());
		copyServiceEntityNodeMutual(sourceDocMatItem, targetDocMatItem, fullCover);
		if (fullCover) {
			// In case all the unique information need to be copied
			targetDocMatItem.setPrevDocType(sourceDocMatItem.getPrevDocType());
			targetDocMatItem.setPrevDocMatItemUUID(sourceDocMatItem.getPrevDocMatItemUUID());
			targetDocMatItem.setNextDocType(sourceDocMatItem.getNextDocType());
			targetDocMatItem.setNextDocMatItemUUID(sourceDocMatItem.getNextDocMatItemUUID());
			targetDocMatItem.setPrevProfDocType(sourceDocMatItem.getPrevProfDocType());
			targetDocMatItem.setPrevProfDocMatItemUUID(sourceDocMatItem.getPrevProfDocMatItemUUID());
			targetDocMatItem.setNextProfDocType(sourceDocMatItem.getNextProfDocType());
			targetDocMatItem.setNextProfDocMatItemUUID(sourceDocMatItem.getNextProfDocMatItemUUID());
			targetDocMatItem.setReservedDocType(sourceDocMatItem.getReservedDocType());
			targetDocMatItem.setReservedMatItemUUID(sourceDocMatItem.getReservedMatItemUUID());
			targetDocMatItem.setHomeDocumentType(sourceDocMatItem.getHomeDocumentType());
		}
		targetDocMatItem.setRefUUID(sourceDocMatItem.getRefUUID());
		targetDocMatItem.setRefMaterialSKUUUID(sourceDocMatItem.getRefMaterialSKUUUID());
		targetDocMatItem.setProductionBatchNumber(sourceDocMatItem.getProductionBatchNumber());
		targetDocMatItem.setPurchaseBatchNumber(sourceDocMatItem.getPurchaseBatchNumber());
		targetDocMatItem.setItemPrice(ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItem.getItemPrice()));
		targetDocMatItem.setUnitPrice(ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItem.getUnitPrice()));
		targetDocMatItem.setItemPriceDisplay(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItem.getItemPriceDisplay()));
		targetDocMatItem.setUnitPriceDisplay(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItem.getUnitPriceDisplay()));
		targetDocMatItem.setCurrencyCode(sourceDocMatItem.getCurrencyCode());
		targetDocMatItem.setMaterialStatus(sourceDocMatItem.getMaterialStatus());
	}

	/**
	 * Just Copy one doc mat item to another
	 *
	 * @param sourceDocMatItemUIModel
	 * @param targetDocMatItemUIModel
	 */
	public static void copyDocMatItemUIModelMutual(DocMatItemUIModel sourceDocMatItemUIModel,
												   DocMatItemUIModel targetDocMatItemUIModel, boolean fullCover) {
		if (sourceDocMatItemUIModel == null || targetDocMatItemUIModel == null) {
			return;
		}
		targetDocMatItemUIModel.setAmount(sourceDocMatItemUIModel.getAmount());
		targetDocMatItemUIModel.setRefUnitUUID(sourceDocMatItemUIModel.getRefUnitUUID());
		targetDocMatItemUIModel.setAmountLabel(sourceDocMatItemUIModel.getAmountLabel());
		if (fullCover) {
			// In case all the unique information need to be copied
			targetDocMatItemUIModel.setUuid(sourceDocMatItemUIModel.getUuid());
			targetDocMatItemUIModel.setId(sourceDocMatItemUIModel.getId());
			targetDocMatItemUIModel.setParentNodeUUID(sourceDocMatItemUIModel.getParentNodeUUID());
			targetDocMatItemUIModel.setRootNodeUUID(sourceDocMatItemUIModel.getRootNodeUUID());
			targetDocMatItemUIModel.setPrevDocType(sourceDocMatItemUIModel.getPrevDocType());
			targetDocMatItemUIModel.setPrevDocTypeValue(sourceDocMatItemUIModel.getPrevDocTypeValue());
			targetDocMatItemUIModel.setPrevDocMatItemUUID(sourceDocMatItemUIModel.getPrevDocMatItemUUID());
			targetDocMatItemUIModel.setPrevDocId(sourceDocMatItemUIModel.getPrevDocId());
			targetDocMatItemUIModel.setPrevDocName(sourceDocMatItemUIModel.getPrevDocName());
			targetDocMatItemUIModel.setNextDocType(sourceDocMatItemUIModel.getNextDocType());
			targetDocMatItemUIModel.setNextDocMatItemUUID(sourceDocMatItemUIModel.getNextDocMatItemUUID());
			targetDocMatItemUIModel.setNextDocId(sourceDocMatItemUIModel.getNextDocId());
			targetDocMatItemUIModel.setNextDocName(sourceDocMatItemUIModel.getNextDocName());
			targetDocMatItemUIModel.setNextDocTypeValue(sourceDocMatItemUIModel.getNextDocTypeValue());
			targetDocMatItemUIModel.setNextProfDocType(sourceDocMatItemUIModel.getNextProfDocType());
			targetDocMatItemUIModel.setNextProfDocMatItemUUID(sourceDocMatItemUIModel.getNextProfDocMatItemUUID());
			targetDocMatItemUIModel.setNextProfDocId(sourceDocMatItemUIModel.getNextProfDocId());
			targetDocMatItemUIModel.setNextProfDocName(sourceDocMatItemUIModel.getNextProfDocName());
			targetDocMatItemUIModel.setNextProfDocTypeValue(sourceDocMatItemUIModel.getNextProfDocTypeValue());

			targetDocMatItemUIModel.setPrevProfDocType(sourceDocMatItemUIModel.getPrevProfDocType());
			targetDocMatItemUIModel.setPrevProfDocTypeValue(sourceDocMatItemUIModel.getPrevProfDocTypeValue());
			targetDocMatItemUIModel.setPrevProfDocMatItemUUID(sourceDocMatItemUIModel.getPrevProfDocMatItemUUID());
			targetDocMatItemUIModel.setPrevProfDocId(sourceDocMatItemUIModel.getPrevProfDocId());
			targetDocMatItemUIModel.setPrevProfDocName(sourceDocMatItemUIModel.getPrevProfDocName());
			targetDocMatItemUIModel.setReservedDocType(sourceDocMatItemUIModel.getReservedDocType());
			targetDocMatItemUIModel.setReservedMatItemUUID(sourceDocMatItemUIModel.getReservedMatItemUUID());
			targetDocMatItemUIModel.setReservedDocId(sourceDocMatItemUIModel.getReservedDocId());
			targetDocMatItemUIModel.setReservedDocName(sourceDocMatItemUIModel.getReservedDocName());
			targetDocMatItemUIModel.setReservedDocTypeValue(sourceDocMatItemUIModel.getReservedDocTypeValue());
		}
		targetDocMatItemUIModel.setRefMaterialSKUUUID(sourceDocMatItemUIModel.getRefMaterialSKUUUID());
		targetDocMatItemUIModel.setRefMaterialTemplateUUID(sourceDocMatItemUIModel.getRefMaterialTemplateUUID());
		targetDocMatItemUIModel.setRefMaterialSKUId(sourceDocMatItemUIModel.getRefMaterialSKUId());
		targetDocMatItemUIModel.setRefMaterialSKUName(sourceDocMatItemUIModel.getRefMaterialSKUName());
		targetDocMatItemUIModel.setPackageStandard(sourceDocMatItemUIModel.getPackageStandard());
		targetDocMatItemUIModel.setSerialId(sourceDocMatItemUIModel.getSerialId());
		targetDocMatItemUIModel.setProductionBatchNumber(sourceDocMatItemUIModel.getProductionBatchNumber());
		targetDocMatItemUIModel.setPurchaseBatchNumber(sourceDocMatItemUIModel.getPurchaseBatchNumber());
		targetDocMatItemUIModel.setItemPrice(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItemUIModel.getItemPrice()));
		targetDocMatItemUIModel.setUnitPrice(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItemUIModel.getUnitPrice()));
		targetDocMatItemUIModel.setItemPriceDisplay(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItemUIModel.getItemPriceDisplay()));
		targetDocMatItemUIModel.setUnitPriceDisplay(
				ServiceEntityDoubleHelper.trancateDoubleScale2(sourceDocMatItemUIModel.getUnitPriceDisplay()));
		targetDocMatItemUIModel.setMaterialStatus(sourceDocMatItemUIModel.getMaterialStatus());
		targetDocMatItemUIModel.setMaterialStatusValue(sourceDocMatItemUIModel.getMaterialStatusValue());
	}

	public DocMatItemNode findEndDocMatItemTillNext(DocMatItemNode homeMatItemNode) throws DocActionException {
		List<ServiceEntityNode> allNextMatItemList = findEndDocMatItemListTillNext(homeMatItemNode, false);
		if (ServiceCollectionsHelper.checkNullList(allNextMatItemList)) {
			return null;
		}
		return (DocMatItemNode) allNextMatItemList.get(0);
	}

	/**
	 * Try to find the next doc mat item recursively until the next node name
	 * matches
	 *
	 * @param homeMatItemNode
	 * @param backwardFlag    weather need to find next doc by retrieve by prev order
	 * @return
	 */
	public List<ServiceEntityNode> findEndDocMatItemListTillNext(DocMatItemNode homeMatItemNode, boolean backwardFlag) throws DocActionException {
		List<ServiceEntityNode> nextNodeList = new ArrayList<>();
		/*
		 * [Step2] Check if current node is already the end node
		 */
		if (homeMatItemNode.getNextDocType() == 0) {
			nextNodeList.add(homeMatItemNode);
			return nextNodeList;
		}
		/*
		 * [Step3] Get the next doc Item node list
		 */
		if (backwardFlag) {
			nextNodeList = this.getDefDocItemNodeList(homeMatItemNode.getNextDocType(), homeMatItemNode.getUuid(),
					IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID, homeMatItemNode.getClient());
		} else {
			nextNodeList = this.getDefDocItemNodeList(homeMatItemNode.getNextDocType(),
					homeMatItemNode.getNextDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
					homeMatItemNode.getClient());
		}
		// In case can't find next node list in run-time
		if (ServiceCollectionsHelper.checkNullList(nextNodeList)) {
			// In case already meet ends
			nextNodeList.add(homeMatItemNode);
			return nextNodeList;
		}

		/*
		 * [Step4] Check if next doc mat item meet the target model name
		 */
		List<ServiceEntityNode> nextResultList = new ArrayList<>();
		ServiceCollectionsHelper.traverseListInterrupt(nextNodeList, seNode -> {
			DocMatItemNode tempNextNode = (DocMatItemNode) seNode;
			List<ServiceEntityNode> tempSubNextNodeList = findEndDocMatItemListTillNext(tempNextNode, backwardFlag);
			if (!ServiceCollectionsHelper.checkNullList(tempSubNextNodeList)) {
				nextResultList.addAll(tempSubNextNodeList);
			}
			return true;
		});
		return nextResultList;
	}

	/**
	 * Trying best to find max number of previous doc mat item list to current doc mat item node.
	 * Trying to find by backward & forward way
	 *
	 * @param homeMatItemNode
	 * @param targetDocType
	 * @return
	 */
	public List<ServiceEntityNode> findMaxPrevDocMatItemList(DocMatItemNode homeMatItemNode, int targetDocType) {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		// Trying to find by forward
		List<ServiceEntityNode> prevNodeList =
				this.getDefDocItemNodeList(targetDocType, homeMatItemNode.getPrevDocMatItemUUID(),
						IServiceEntityNodeFieldConstant.UUID, homeMatItemNode.getClient());
		// Trying to find by backword
		List<ServiceEntityNode> prevNodeList2 = this.getDefDocItemNodeList(targetDocType, homeMatItemNode.getUuid(),
				IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID, homeMatItemNode.getClient());
		ServiceCollectionsHelper.mergeToList(resultList, prevNodeList);
		ServiceCollectionsHelper.mergeToList(resultList, prevNodeList2);
		return resultList;
	}

	/**
	 * Try to find the next doc mat item recursively until the target model name
	 *
	 * @param homeMatItemNode
	 * @param targetModelName
	 * @param backwardFlag    weather need to find next doc by retrieve by prev order
	 * @return
	 */
	public List<ServiceEntityNode> findNextDocMatItemList(DocMatItemNode homeMatItemNode, String targetModelName,
														  boolean backwardFlag) throws DocActionException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (ServiceEntityStringHelper.checkNullString(targetModelName) || homeMatItemNode == null) {
			return null;
		}
		if (ServiceEntityStringHelper.headerToLowerCase(targetModelName)
				.equals(ServiceEntityStringHelper.headerToLowerCase(homeMatItemNode.getNodeName()))) {
			resultList.add(homeMatItemNode);
			return resultList;
		}
		if (ServiceEntityStringHelper.headerToLowerCase(targetModelName)
				.equals(ServiceEntityStringHelper.headerToLowerCase(homeMatItemNode.getServiceEntityName()))) {
			resultList.add(homeMatItemNode);
			return resultList;
		}
		List<ServiceEntityNode> nextNodeList = null;
		if (backwardFlag) {
			nextNodeList = this.getDefDocItemNodeList(homeMatItemNode.getNextDocType(), homeMatItemNode.getUuid(),
					IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID, homeMatItemNode.getClient());
		} else {
			nextNodeList = this.getDefDocItemNodeList(homeMatItemNode.getNextDocType(),
					homeMatItemNode.getNextDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
					homeMatItemNode.getClient());
		}
		if (ServiceCollectionsHelper.checkNullList(nextNodeList)) {
			// In case already meet ends
			return null;
		}
		ServiceCollectionsHelper.traverseListInterrupt(nextNodeList, seNode -> {
			DocMatItemNode nextDocMatItemNode = (DocMatItemNode) seNode;
			List<ServiceEntityNode> subNextMatItemList =
					findNextDocMatItemList(nextDocMatItemNode, targetModelName, backwardFlag);
			if (!ServiceCollectionsHelper.checkNullList(subNextMatItemList)) {
				ServiceCollectionsHelper.mergeToList(resultList, subNextMatItemList);
			}
			return true;
		});
		return resultList;
	}

	public DocMatItemNode findTargetDocMatItemTillPrev(DocMatItemNode homeMatItemNode, List<String> targetModelNameList) throws DocActionException {
		List<ServiceEntityNode> allPrevMatItemList =
				findTargetDocMatItemListTillPrev(homeMatItemNode, targetModelNameList, false);
		if (ServiceCollectionsHelper.checkNullList(allPrevMatItemList)) {
			return null;
		}
		return (DocMatItemNode) allPrevMatItemList.get(0);
	}

	/**
	 * Find the previous mat item instance in recursive way, by matching the target model name, if not matched, then return null

	 * @return
	 * @throws DocActionException
	 */
	public DocMatItemNode findPrevTargetDocMatItemRecursive(DocMatItemNode homeMatItemNode, List<String> targetModelNameList) throws DocActionException {
		List<ServiceEntityNode> allPrevMatItemList =
				findTargetDocMatItemListTillPrev(homeMatItemNode, targetModelNameList, false);
		if (ServiceCollectionsHelper.checkNullList(allPrevMatItemList)) {
			return null;
		}
		return (DocMatItemNode) ServiceCollectionsHelper.filterOnline(allPrevMatItemList, item-> checkTargetModelNameMatches(targetModelNameList, (DocMatItemNode) item));
	}

	public DocMatItemNode findNextTargetDocMatItemRecursive(DocMatItemNode homeMatItemNode, List<String> targetModelNameList) throws DocActionException {
		List<ServiceEntityNode> allPrevMatItemList =
				findTargetDocMatItemListTillNext(homeMatItemNode, targetModelNameList, false, false);
		if (ServiceCollectionsHelper.checkNullList(allPrevMatItemList)) {
			return null;
		}
		return (DocMatItemNode) ServiceCollectionsHelper.filterOnline(allPrevMatItemList, item-> checkTargetModelNameMatches(targetModelNameList, (DocMatItemNode) item));
	}

	public List<ServiceEntityNode> findTargetDocMatItemListTillNext(DocMatItemNode homeMatItemNode,
																	List<String> targetModelNameList, boolean backwardFlag, boolean excludeFlag) throws DocActionException {
		return findTargetDocMatItemListTillDocFlowNext(homeMatItemNode, targetModelNameList, excludeFlag, docMatItemNode -> {
            if (backwardFlag) {
                return getDefDocItemNodeList(homeMatItemNode.getNextDocType(), homeMatItemNode.getUuid(),
                        IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID, homeMatItemNode.getClient());
            } else {
                return getDefDocItemNodeList(homeMatItemNode.getNextDocType(),
                        homeMatItemNode.getNextDocMatItemUUID(), IServiceEntityNodeFieldConstant.UUID,
                        homeMatItemNode.getClient());
            }
        }, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT);
	}

	public interface GetNextFlowMatItemList {

		List<ServiceEntityNode> execute(DocMatItemNode docMatItemNode) throws DocActionException;

	}

	/**
	 * Try to find the previous doc mat item recursively until the previous node name
	 * matches
	 *
	 * @param homeMatItemNode
	 * @param excludeFlag:    weather need to exclude other end doc list, strictly match the node name
	 * @return
	 */
	public List<ServiceEntityNode> findTargetDocMatItemListTillPrev(DocMatItemNode homeMatItemNode,
																	List<String> targetModelNameList, boolean excludeFlag) throws DocActionException {
		return findTargetDocMatItemListTillDocFlowNext(homeMatItemNode, targetModelNameList, excludeFlag, null, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV);
	}

	/**
	 * Recursively finds and returns a list of Doc Material item instance from the input document material item instance.
	 * The search continues until both the node name and service entity name match any of the provided target module names.
	 *
	 * @param homeMatItemNode the starting node of the document material item list
	 * @param targetModelNameList a list of target module names to match against node and service entity names
	 * @param excludeFlag a boolean flag indicating whether to exclude other end document lists, ensuring strict matching of node names
	 * @return a list of ServiceEntityNode items that match the criteria
	 * @throws DocActionException if there is an error during the document action process
	 */
	public List<ServiceEntityNode> findTargetDocMatItemListTillDocFlowNext(DocMatItemNode homeMatItemNode,
																	List<String> targetModelNameList, boolean excludeFlag, GetNextFlowMatItemList getNextFlowMatItemList, int docFlowDirection) throws DocActionException {
		if (ServiceCollectionsHelper.checkNullList(targetModelNameList)) {
			return null;
		}
		if (homeMatItemNode == null) {
			return null;
		}
		if (checkTargetModelNameMatches(targetModelNameList, homeMatItemNode)) {
			return null;
		}
		List<ServiceEntityNode> nextFlowNodeList = new ArrayList<>();
		/*
		 * [Step2] Check if current node is already the end node
		 */
		int nextDocType = DocFlowContextProxy.getDocContextDocType(homeMatItemNode, docFlowDirection);
		if (nextDocType == 0) {
			if (!excludeFlag) {
				nextFlowNodeList.add(homeMatItemNode);
				return nextFlowNodeList;
			}
		}
		String nextDocUUID = DocFlowContextProxy.getDocContextUUIDCoreValue(homeMatItemNode, docFlowDirection);
		/*
		 * [Step3] Get the next doc Item node list
		 */
		if (getNextFlowMatItemList != null) {
			nextFlowNodeList = getNextFlowMatItemList.execute(homeMatItemNode);
		} else {
			nextFlowNodeList =
					this.getDefDocItemNodeList(nextDocType, nextDocUUID,
							IServiceEntityNodeFieldConstant.UUID, homeMatItemNode.getClient());
		}
		// In case can't find next node list in run-time
		if (ServiceCollectionsHelper.checkNullList(nextFlowNodeList)) {
			// In case already meet ends
			if (!excludeFlag) {
				if (nextFlowNodeList == null) {
					return ServiceCollectionsHelper.asList(homeMatItemNode) ;
				} else {
					nextFlowNodeList.add(homeMatItemNode);
					return nextFlowNodeList;
				}
			}
		} else {
			List<ServiceEntityNode> resultList = new ArrayList<>();
			ServiceCollectionsHelper.traverseListInterrupt(nextFlowNodeList, seNode -> {
				DocMatItemNode nextFlowDocMatItemNode = (DocMatItemNode) seNode;
				if (checkTargetModelNameMatches(targetModelNameList, nextFlowDocMatItemNode)) {
					if (excludeFlag) {
						resultList.add(homeMatItemNode);
					} else {
						resultList.add(nextFlowDocMatItemNode);
					}
				}
				return true;
			});
			if (!ServiceCollectionsHelper.checkNullList(resultList)) {
				return resultList;
			}
		}
		/*
		 * [Step5] Call this API in recursive way
		 */
		List<ServiceEntityNode> prevResultList = new ArrayList<>();
		ServiceCollectionsHelper.traverseListInterrupt(nextFlowNodeList, seNode -> {
			DocMatItemNode tempNextNode = (DocMatItemNode) seNode;
			List<ServiceEntityNode> tempSubPrevNodeList =
					findTargetDocMatItemListTillPrev(tempNextNode, targetModelNameList, excludeFlag);
			if (!ServiceCollectionsHelper.checkNullList(tempSubPrevNodeList)) {
				prevResultList.addAll(tempSubPrevNodeList);
			}
			return true;
		});
		return prevResultList;
	}

	/**
	 * [Internal] Check if the 'node name' or 'service entity name' from the current docMatItemNode instances is included in the targetModelNameList.
	 * @param targetModelNameList
	 * @param docMatItemNode
	 * @return
	 */
	private boolean checkTargetModelNameMatches(List<String> targetModelNameList, DocMatItemNode docMatItemNode) {
		if (ServiceCollectionsHelper.checkNullList(targetModelNameList)) {
			return false;
		}
		for (String targetModelName : targetModelNameList) {
			if (ServiceEntityStringHelper.headerToLowerCase(targetModelName)
					.equals(ServiceEntityStringHelper.headerToLowerCase(docMatItemNode.getNodeName()))) {
				return true;
			}
			if (ServiceEntityStringHelper.headerToLowerCase(targetModelName)
					.equals(ServiceEntityStringHelper.headerToLowerCase(docMatItemNode.getServiceEntityName()))) {
				return true;
			}
		}
		return false;
	}

	public int getDocumentType(ServiceEntityNode serviceEntityNode) {
		String modelName = ServiceDocumentComProxy.getServiceEntityModelName(serviceEntityNode);
		if (modelName == null) {
			return 0;
		}
		return serviceDocumentComProxy.getDocumentTypeByModelName(modelName);
	}

	/**
	 * Utility method to get home document type by reading none empty doc mat item list
	 *
	 * @param docMatItemList
	 * @return
	 */
	public static int getHomeDocTypeByMatItemList(List<ServiceEntityNode> docMatItemList) {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return 0;
		}
		DocMatItemNode docMatItemNode = (DocMatItemNode) docMatItemList.get(0);
		return docMatItemNode.getHomeDocumentType();
	}

	/**
	 * Utility method to sort list of doc mat item list, Prioritization these items that already reserved by current `reservedDocMatUUID`.
	 *
	 * @param docMatItemList: the list of doc mat item list
	 * @param reservedDocMatUUID: the doc mat item UUID to reserve these doc mat item list
	 * @return a list of doc mat item items which has been sorted
	 */
	public static List<ServiceEntityNode> sortDocMatItemList(List<ServiceEntityNode> docMatItemList,
															 String reservedDocMatUUID) {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList) ||
				ServiceEntityStringHelper.checkNullString(reservedDocMatUUID)) {
			return docMatItemList;
		}
		List<ServiceEntityNode> subMatItemListWithReserved =
				ServiceCollectionsHelper.filterListOnline(docMatItemList, seNode -> {
					DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
					return reservedDocMatUUID.equals(docMatItemNode.getReservedMatItemUUID());
				}, false);
		List<ServiceEntityNode> resultList = ServiceCollectionsHelper.filterListOnline(docMatItemList, seNode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			return !reservedDocMatUUID.equals(docMatItemNode.getReservedMatItemUUID());
		}, false);
		if (!ServiceCollectionsHelper.checkNullList(subMatItemListWithReserved)) {
			resultList.addAll(0, subMatItemListWithReserved);
		}
		return resultList;
	}

	/**
	 * Wrapper method to get Service Document Extend model by any document
	 *
	 * @param seNode
	 * @param logonInfo
	 * @return
	 */
	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo)
			throws DocActionException {
		if (seNode == null || logonInfo == null) {
			return null;
		}
		String modelName = ServiceDocumentComProxy.getServiceEntityModelName(seNode);
		int documentType = getDocumentType(seNode);
		if (documentType == 0) {
			logger.error("No document registered for model:" + modelName);
			return null;
		}
		ServiceEntityManager seManager = getDocumentManager(documentType);
		if (seManager == null) {
			logger.error("No document registered for model:" + modelName);
			return null;
		}
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel =
				seManager.convToDocumentExtendUIModel(seNode, logonInfo);
		return serviceDocumentExtendUIModel;
	}

	/**
	 * Default Logic to conversion to ServiceDocumentExtendUIModel from
	 * DocMatItemUIModel
	 *
	 * @param docMatItemUIModel
	 * @param logonInfo
	 * @param documentType
	 * @return
	 * @throws ServiceEntityInstallationException
	 */
	public void convDocMatItemUIToDocExtUIModel(DocMatItemUIModel docMatItemUIModel,
												ServiceDocumentExtendUIModel serviceDocumentExtendUIModel,
												LogonInfo logonInfo, int documentType)
			throws ServiceEntityInstallationException {
		if (docMatItemUIModel != null) {
			if (!ServiceEntityStringHelper.checkNullString(docMatItemUIModel.getId())) {
				serviceDocumentExtendUIModel.setId(docMatItemUIModel.getId());
			}
			serviceDocumentExtendUIModel.setUuid(docMatItemUIModel.getUuid());
			serviceDocumentExtendUIModel.setParentNodeUUID(docMatItemUIModel.getParentNodeUUID());
			serviceDocumentExtendUIModel.setRootNodeUUID(docMatItemUIModel.getRootNodeUUID());
			serviceDocumentExtendUIModel.setDocumentType(documentType);
			if (logonInfo != null) {
				serviceDocumentExtendUIModel.setDocumentTypeValue(
						serviceDocumentComProxy.getDocumentTypeValue(documentType, logonInfo.getLanguageCode()));
			}
			serviceDocumentExtendUIModel.setRefMaterialSKUUUID(docMatItemUIModel.getRefMaterialSKUUUID());
			serviceDocumentExtendUIModel.setRefMaterialSKUId(docMatItemUIModel.getRefMaterialSKUId());
			serviceDocumentExtendUIModel.setRefMaterialSKUName(docMatItemUIModel.getRefMaterialSKUName());
			serviceDocumentExtendUIModel.setSerialId(docMatItemUIModel.getSerialId());
			serviceDocumentExtendUIModel.setProductionBatchNumber(docMatItemUIModel.getProductionBatchNumber());
			serviceDocumentExtendUIModel.setAmount(docMatItemUIModel.getAmount());
			serviceDocumentExtendUIModel.setAmountLabel(docMatItemUIModel.getAmountLabel());
			serviceDocumentExtendUIModel.setMaterialStatus(docMatItemUIModel.getMaterialStatus());
			serviceDocumentExtendUIModel.setMaterialStatusValue(docMatItemUIModel.getMaterialStatusValue());
			serviceDocumentExtendUIModel.setUpdatedById(docMatItemUIModel.getUpdatedById());
			serviceDocumentExtendUIModel.setUpdatedByName(docMatItemUIModel.getUpdatedByName());
			serviceDocumentExtendUIModel.setUpdatedByUUID(docMatItemUIModel.getUpdatedByUUID());
			serviceDocumentExtendUIModel.setUpdatedDate(docMatItemUIModel.getUpdatedDate());
			serviceDocumentExtendUIModel.setStatus(docMatItemUIModel.getItemStatus());
			serviceDocumentExtendUIModel.setStatusValue(docMatItemUIModel.getItemStatusValue());
			// Logic of default reference Date: updated date
			String referenceDate = docMatItemUIModel.getUpdatedDate();
			serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		}
	}

	/**
	 * The requested amount in the current document item should not exceeds the amount in the previous document item.
	 *
	 * @param request: document item request
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws DocActionException
	 */
	public String checkOveramountRequest(DocItemRequestJSONModel request, OveramountRequest overamountRequest)
			throws ServiceEntityConfigureException, MaterialException, DocActionException,
			ServiceEntityInstallationException {
		int homeDocType = overamountRequest.getDocumentType() > 0 ? overamountRequest.getDocumentType() :
				request.getDocumentType();
		DocumentContentSpecifier documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
		DocMatItemNode homeDocMatItemNode =
				(DocMatItemNode) documentContentSpecifier.getDocMatItem(request.getDocItemUUID(), request.getClient());
		DocumentContentSpecifier prevProfContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(homeDocMatItemNode.getPrevProfDocType());
		// in case no binding contract item
		if (prevProfContentSpecifier == null) {
			return ServiceJSONParser.genSimpleOKResponse();
		}
		DocMatItemNode prevProfDocMatItemNode =
				(DocMatItemNode) prevProfContentSpecifier.getDocMatItem(homeDocMatItemNode.getPrevProfDocMatItemUUID(),
						request.getClient());
		// in case no binding contract item
		if (prevProfDocMatItemNode == null) {
			return ServiceJSONParser.genSimpleOKResponse();
		}
		StorageCoreUnit requestUnit =
				new StorageCoreUnit(homeDocMatItemNode.getRefMaterialSKUUUID(), request.getRefUnitUUID(),
						Double.parseDouble(request.getAmount()));
		StorageCoreUnit storageUnit =
				new StorageCoreUnit(homeDocMatItemNode.getRefMaterialSKUUUID(), homeDocMatItemNode.getRefUnitUUID(),
						homeDocMatItemNode.getAmount());
		StorageCoreUnit compareResult = materialStockKeepUnitManager.mergeStorageUnitCore(requestUnit, storageUnit,
				StorageCoreUnit.OPERATOR_MINUS, homeDocMatItemNode.getClient());
		if (compareResult.getAmount() > 0) {
			String prevProfAmountLabel =
					materialStockKeepUnitManager.getAmountLabel(prevProfDocMatItemNode.getRefMaterialSKUUUID(),
							prevProfDocMatItemNode.getRefUnitUUID(), prevProfDocMatItemNode.getAmount(),
							prevProfDocMatItemNode.getClient());
			Class<?> exceptionClass =
					overamountRequest.getExceptionClass() != null ? overamountRequest.getExceptionClass() :
							DocActionException.class;
			int errorCode = overamountRequest.getExceptionCode() > 0 ? overamountRequest.getExceptionCode() :
					DocActionException.PARA2_OVER_AMOUNT_PREVPROF;
			String errorMessage =
					ServiceExceptionHelper.getErrorMessageWrapper(exceptionClass, errorCode, prevProfAmountLabel);
			return ServiceJSONParser.generateSimpleErrorJSON(errorMessage);
		}
		return ServiceJSONParser.genSimpleOKResponse();
	}

	/**
	 * The requested amount in the current document item should not exceeds the amount in the previous document item.
	 *
	 * @param request: document item request
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 * @throws DocActionException
	 */
	public String checkOveramountRequestCore(DocItemRequestJSONModel request, OveramountRequest overamountRequest)
			throws ServiceEntityConfigureException, MaterialException, DocActionException,
			ServiceEntityInstallationException {
		int homeDocType = overamountRequest.getDocumentType() > 0 ? overamountRequest.getDocumentType() :
				request.getDocumentType();
		DocumentContentSpecifier documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(homeDocType);
		DocMatItemNode homeDocMatItemNode =
				(DocMatItemNode) documentContentSpecifier.getDocMatItem(request.getDocItemUUID(), request.getClient());
		// TODO find api to get value by docFlowDirection
		DocumentContentSpecifier baseContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(homeDocMatItemNode.getPrevProfDocType());
		// in case no binding contract item
		if (baseContentSpecifier == null) {
			return ServiceJSONParser.genSimpleOKResponse();
		}
		DocMatItemNode baseDocMatItemNode =
				(DocMatItemNode) baseContentSpecifier.getDocMatItem(homeDocMatItemNode.getPrevProfDocMatItemUUID(),
						request.getClient());
		// in case no binding contract item
		if (baseDocMatItemNode == null) {
			return ServiceJSONParser.genSimpleOKResponse();
		}
		StorageCoreUnit requestUnit =
				new StorageCoreUnit(homeDocMatItemNode.getRefMaterialSKUUUID(), request.getRefUnitUUID(),
						Double.parseDouble(request.getAmount()));
		StorageCoreUnit storageUnit =
				new StorageCoreUnit(homeDocMatItemNode.getRefMaterialSKUUUID(), homeDocMatItemNode.getRefUnitUUID(),
						homeDocMatItemNode.getAmount());
		StorageCoreUnit compareResult = materialStockKeepUnitManager.mergeStorageUnitCore(requestUnit, storageUnit,
				StorageCoreUnit.OPERATOR_MINUS, homeDocMatItemNode.getClient());
		if (compareResult.getAmount() > 0) {
			String baseAmountLabel =
					materialStockKeepUnitManager.getAmountLabel(baseDocMatItemNode.getRefMaterialSKUUUID(),
							baseDocMatItemNode.getRefUnitUUID(), baseDocMatItemNode.getAmount(),
							baseDocMatItemNode.getClient());
			Class<?> exceptionClass =
					overamountRequest.getExceptionClass() != null ? overamountRequest.getExceptionClass() :
							DocActionException.class;
			int errorCode = overamountRequest.getExceptionCode() > 0 ? overamountRequest.getExceptionCode() :
					DocActionException.PARA2_OVER_AMOUNT_PREVPROF;
			String errorMessage =
					ServiceExceptionHelper.getErrorMessageWrapper(exceptionClass, errorCode, baseAmountLabel);
			return ServiceJSONParser.generateSimpleErrorJSON(errorMessage);
		}
		return ServiceJSONParser.genSimpleOKResponse();
	}

	public static class OveramountRequest {

		protected int documentType;

		protected Class<?> exceptionClass;

		protected int exceptionCode;

		protected int baseDocFlowDirection;

		public OveramountRequest() {
		}

		public OveramountRequest(int documentType, Class<?> exceptionClass, int exceptionCode) {
			this.documentType = documentType;
			this.exceptionClass = exceptionClass;
			this.exceptionCode = exceptionCode;
		}

		public OveramountRequest(int documentType, Class<?> exceptionClass, int exceptionCode,
								 int baseDocFlowDirection) {
			this.documentType = documentType;
			this.exceptionClass = exceptionClass;
			this.exceptionCode = exceptionCode;
			this.baseDocFlowDirection = baseDocFlowDirection;
		}

		public int getDocumentType() {
			return documentType;
		}

		public void setDocumentType(int documentType) {
			this.documentType = documentType;
		}

		public Class<?> getExceptionClass() {
			return exceptionClass;
		}

		public void setExceptionClass(Class<?> exceptionClass) {
			this.exceptionClass = exceptionClass;
		}

		public int getExceptionCode() {
			return exceptionCode;
		}

		public void setExceptionCode(int exceptionCode) {
			this.exceptionCode = exceptionCode;
		}

		public int getBaseDocFlowDirection() {
			return baseDocFlowDirection;
		}

		public void setBaseDocFlowDirection(int baseDocFlowDirection) {
			this.baseDocFlowDirection = baseDocFlowDirection;
		}
	}

	/**
	 * Provide the default way to set [ItemPrice] to docMatItemNode instance
	 *
	 * @param docMatItemNode
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void calculateDefItemPrice(DocMatItemNode docMatItemNode)
			throws ServiceEntityConfigureException, MaterialException {
		MaterialStockKeepUnit materialStockKeepUnit =
				getMaterialSKUFromDocMatItem(docMatItemNode);
		StorageCoreUnit storageCoreUnit =
				new StorageCoreUnit(docMatItemNode.getRefMaterialSKUUUID(), docMatItemNode.getRefUnitUUID(),
						docMatItemNode.getAmount());
		double itemPrice = materialStockKeepUnitManager.calculatePrice(storageCoreUnit, materialStockKeepUnit,
				docMatItemNode.getUnitPrice());
		docMatItemNode.setItemPrice(itemPrice);
	}

	/**
	 * Get Material SKU instance by retrieving the relative information from Document material item instance
	 * @param docMatItemNode
	 * @return
	 */
	public MaterialStockKeepUnit getMaterialSKUFromDocMatItem(DocMatItemNode docMatItemNode) throws ServiceEntityConfigureException {
		return (MaterialStockKeepUnit) materialStockKeepUnitManager.getEntityNodeByUUID(
						docMatItemNode.getRefMaterialSKUUUID(),
						MaterialStockKeepUnit.NODENAME, docMatItemNode.getClient());
	}

	/**
	 * Group Material Item List by root node
	 *
	 * @param rawDocMaterialItemList Raw Material item list
	 * @return Map of Material Item List by root node UUID
	 */
	public static Map<String, List<ServiceEntityNode>> mapMaterialItemListByRoot(
			List<ServiceEntityNode> rawDocMaterialItemList) {
		if (ServiceCollectionsHelper.checkNullList(rawDocMaterialItemList)) {
			return null;
		}
		Map<String, List<ServiceEntityNode>> resultMap = new HashMap<>();
		for (ServiceEntityNode seNode : rawDocMaterialItemList) {
			String rootNodeUUID = seNode.getRootNodeUUID();
			if (resultMap.containsKey(rootNodeUUID)) {
				List<ServiceEntityNode> tempSEList = resultMap.get(rootNodeUUID);
				ServiceCollectionsHelper.mergeToList(tempSEList, seNode);
			} else {
				resultMap.put(rootNodeUUID, ServiceCollectionsHelper.asList(seNode));
			}
		}
		return resultMap;
	}

	/**
	 * Utility method to calculate and summarize the gross price
	 *
	 * @param docMatItemNodeList
	 * @param displayFlag:       weather need to calculate display price
	 * @return
	 */
	public static double calculateGrossPrice(List<ServiceEntityNode> docMatItemNodeList, boolean displayFlag) {
		if (ServiceCollectionsHelper.checkNullList(docMatItemNodeList)) {
			return 0;
		}
		double grossAmount = 0;
		for (ServiceEntityNode seNode : docMatItemNodeList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			if (displayFlag) {
				grossAmount = grossAmount + docMatItemNode.getItemPriceDisplay();
			} else {
				grossAmount = grossAmount + docMatItemNode.getItemPrice();
			}
		}
		return grossAmount;
	}

	public static class GrossPricePair {

		private double grossPrice;

		private double grossPriceDisplay;

		public GrossPricePair() {
		}

		public GrossPricePair(double grossPrice, double grossPriceDisplay) {
			this.grossPrice = grossPrice;
			this.grossPriceDisplay = grossPriceDisplay;
		}

		public double getGrossPrice() {
			return grossPrice;
		}

		public void setGrossPrice(double grossPrice) {
			this.grossPrice = grossPrice;
		}

		public double getGrossPriceDisplay() {
			return grossPriceDisplay;
		}

		public void setGrossPriceDisplay(double grossPriceDisplay) {
			this.grossPriceDisplay = grossPriceDisplay;
		}
	}

	/**
	 * Utility method to calculate and summarize the gross price
	 *
	 * @param docMatItemNodeList
	 * @return
	 */
	public static GrossPricePair calculateGrossPrice(List<ServiceEntityNode> docMatItemNodeList) {
		if (ServiceCollectionsHelper.checkNullList(docMatItemNodeList)) {
			return new GrossPricePair();
		}
		double grossPrice = 0;
		double grossPriceDisplay = 0;
		for (ServiceEntityNode seNode : docMatItemNodeList) {
			DocMatItemNode docMatItemNode = (DocMatItemNode) seNode;
			grossPriceDisplay = grossPriceDisplay + docMatItemNode.getItemPriceDisplay();
			grossPrice = grossPrice + docMatItemNode.getItemPrice();
		}
		return new GrossPricePair(grossPrice, grossPriceDisplay);
	}

	public static List<String> pluckToRefMaterialSKUUUIDList(List<ServiceEntityNode> docMatItemList) {
		List<String> refMaterialSKUUUIDList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return refMaterialSKUUUIDList;
		}
		refMaterialSKUUUIDList = docMatItemList.stream().map(serviceEntityNode -> {
			DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityNode;
			return docMatItemNode.getRefMaterialSKUUUID();
		}).collect(Collectors.toList());
		return refMaterialSKUUUIDList;
	}

	/**
	 * Wrapper method to get all the reserved doc item list
	 *
	 * @param docMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getReservedDocMatItemList(List<ServiceEntityNode> docMatItemList,
															 ServiceEntityManager serviceEntityManager, String nodeName)
			throws ServiceEntityConfigureException {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return null;
		}
		List<ServiceEntityNode> resultList;
		List<String> uuidList = new ArrayList<>();
		String client = docMatItemList.get(0).getClient();
		for (ServiceEntityNode serviceEntityNode : docMatItemList) {
			ServiceCollectionsHelper.mergeToList(uuidList, serviceEntityNode.getUuid());
		}
		resultList = serviceEntityManager.getEntityNodeListByMultipleKey(uuidList,
				IServiceEntityCommonFieldConstant.RESEARVEDMATITEMUUID, nodeName, client, null);
		return resultList;
	}

	/**
	 * Delete Doc Mat Item instance in safe module
	 *
	 * @param docMatItemNode
	 */
	public void deleteDocMatItem(DocMatItemNode docMatItemNode, ServiceEntityManager serviceEntityManager,
								 SerialLogonInfo serialLogonInfo) throws DocActionException {
		// Delete prev-next relationship
		prevNextDocItemProxy.cleanPrevByNext(docMatItemNode.getPrevDocMatItemUUID(), docMatItemNode.getPrevDocType(),
				docMatItemNode, serialLogonInfo);
		prevNextDocItemProxy.cleanNextByPrev(docMatItemNode.getNextDocMatItemUUID(), docMatItemNode.getNextDocType(),
				docMatItemNode, serialLogonInfo);
		// Delete prev-next-prof relationship
		prevNextProfDocItemProxy.cleanPrevProfByNext(docMatItemNode.getPrevProfDocMatItemUUID(),
				docMatItemNode.getPrevDocType(),
				docMatItemNode, serialLogonInfo);
		prevNextProfDocItemProxy.cleanNextProfByPrev(docMatItemNode.getNextProfDocMatItemUUID(),
				docMatItemNode.getNextDocType(),
				docMatItemNode, serialLogonInfo);
		// Free reserve
		// reserveDocItemProxy.freeReserve()
		if (serviceEntityManager != null) {
			serviceEntityManager.deleteSENode(
					docMatItemNode, serialLogonInfo.getRefUserUUID(), serialLogonInfo.getResOrgUUID());
		}
	}

	/**
	 * Clear all the relationship for current doc mat item instance, it is preparation before delete this instance
	 * @param docMatItemNode
	 * @param serialLogonInfo
	 * @throws DocActionException
	 */
	public void clearDocMatItemRelationship(DocMatItemNode docMatItemNode,
								 SerialLogonInfo serialLogonInfo) throws DocActionException {
		// Delete previous relationship from main prev mat item uuid
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVED_BY);
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, StandardDocFlowDirectionProxy.DOCFLOW_TO_RESERVE_TARGET);
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV);
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, DOCFLOW_DIREC_NEXT);
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_PREV_PROF);
		docFlowContextProxy.clearDocMatItemRelationship(docMatItemNode, serialLogonInfo, StandardDocFlowDirectionProxy.DOCFLOW_DIREC_NEXT_PROF);
	}

	/**
	 * Wrapper method to get all the reserved doc item list
	 *
	 * @param docMatItemList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getPrevNextDocMatItemList(List<ServiceEntityNode> docMatItemList, int documentType,
															 int docFlowDirection)
			throws ServiceEntityConfigureException, DocActionException {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return null;
		}
		List<ServiceEntityNode> resultList;
		List<String> uuidList = new ArrayList<>();
		String client = docMatItemList.get(0).getClient();
		for (ServiceEntityNode serviceEntityNode : docMatItemList) {
			ServiceCollectionsHelper.mergeToList(uuidList, serviceEntityNode.getUuid());
		}
		ServiceEntityManager refDocumentManager = getDocumentManager(documentType);
		if (refDocumentManager == null) {
			return null;
		}
		DocumentContentSpecifier<?, ?, ?> documentContentSpecifier =
				docActionExecutionProxyFactory.getSpecifierByDocType(documentType);
		String nodeName = documentContentSpecifier.getDocNodeName();
		String keyName = IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID;
		if (docFlowDirection == DOCFLOW_DIREC_NEXT) {
			keyName = IServiceEntityCommonFieldConstant.NEXTDOCMATITEMUUID;
		}
		resultList = refDocumentManager.getEntityNodeListByMultipleKey(uuidList, keyName, nodeName, client, null);
		return resultList;
	}

	/**
	 * Batch clean doc mat item next relationship
	 *
	 * @param docMatItemList
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void clearDocItemNextInfo(List<ServiceEntityNode> docMatItemList, ServiceEntityManager serviceEntityManager,
									 String logonUserUUID, String organizationUUID) throws DocActionException {
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return;
		}
		for (ServiceEntityNode serviceEntityNode : docMatItemList) {
			if (serviceEntityNode instanceof DocMatItemNode) {
				DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityNode;
				docMatItemNode.setNextDocType(0);
				docMatItemNode.setNextDocMatItemUUID(ServiceEntityStringHelper.EMPTYSTRING);
				docMatItemNode.setNextDocMatItemArrayUUID(ServiceEntityStringHelper.EMPTYSTRING);
			}
		}
		updateBatchDocItemList(docMatItemList, serviceEntityManager, logonUserUUID, organizationUUID);
	}

	/**
	 * Utility method to batch update docMatItem List
	 *
	 * @param docMatItemList
	 * @param serviceEntityManager
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	public void updateBatchDocItemList(List<ServiceEntityNode> docMatItemList,
									   ServiceEntityManager serviceEntityManager, String logonUserUUID,
									   String organizationUUID) throws DocActionException {
		if (serviceEntityManager != null) {
			serviceEntityManager.updateSENodeList(docMatItemList, logonUserUUID, organizationUUID);
		}
		if (ServiceCollectionsHelper.checkNullList(docMatItemList)) {
			return;
		}
		// In case not provided serviceEntityManager
		AtomicInteger documentType = new AtomicInteger();
		ServiceCollectionsHelper.traverseListInterrupt(docMatItemList, serviceEntityNode -> {
			if (serviceEntityNode instanceof DocMatItemNode) {
				DocMatItemNode docMatItemNode = (DocMatItemNode) serviceEntityNode;
				if (docMatItemNode.getHomeDocumentType() > 0) {
					documentType.set(docMatItemNode.getHomeDocumentType());
					return true;
				}
			}
			return true;
		});
		ServiceEntityManager refDocumentManager = getDocumentManager(documentType.get());
		if (refDocumentManager == null) {
			return;
		}
        refDocumentManager.updateSENodeList(docMatItemList, logonUserUUID, organizationUUID);
    }

}
