package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// TODO-LEGACY: import platform.foundation.InstModel.PageButtonConfigure;
// TODO-LEGACY: import platform.foundation.InstModel.PageConfigure;
// TODO-LEGACY: import platform.foundation.InstModel.ServiceControllerMethodInstallPiece;
// TODO-LEGACY: import platform.foundation.InstModel.ServiceViewControlInstallPiece;
import com.company.IntelligentPlatform.common.controller.PageButtonConfigure;
import com.company.IntelligentPlatform.common.controller.PageConfigure;
import com.company.IntelligentPlatform.common.controller.ServiceControllerMethodInstallPiece;
import com.company.IntelligentPlatform.common.controller.ServiceViewControlInstallPiece;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.SearchConfigConnectCondition;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Compound configure class for UI Model Node
 * 
 * @author Zhang Hang
 * 
 */
public class UIModelNodeMapConfigure {

	/**
	 * base node is the parent node of current node
	 */
	public static final int TOBASENODE_TO_PARENT = 1;

	/**
	 * base node is the root node of current node
	 */
	public static final int TOBASENODE_TO_ROOT = 2;

	/**
	 * base node is direct child node of current node
	 */
	public static final int TOBASENODE_TO_CHILD = 3;
	/**
	 * base node is target reference node of current node
	 */
	public static final int TOBASENODE_REFTO_TARGET = 4;

	/**
	 * current node is target reference node of base node
	 */
	public static final int TOBASENODE_REFTO_SOURCE = 5;
	/**
	 * other relationship
	 */
	public static final int TOBASENODE_OTHERS = 6;
	/**
	 * base node is the to root child node of current node
	 */
	public static final int TOBASENODE_ROOT_TO_CHILD = 7;

	/**
	 * list embedded category:no list at all [default value]
	 */
	public static final int LIST_CATE_NOLIST = 0;

	/**
	 * list embedded category:sub node instance as list embedded view and could
	 * be edit separately
	 */
	public static final int LIST_CATE_EDIT = 1;

	/**
	 * list embedded category:sub node instance as list embedded view and could
	 * be chosen
	 */
	public static final int LIST_CATE_CHOOSE = 2;

	/**
	 * list embedded category:sub node instance as list embedded view and could
	 * be chosen as multiple items
	 */
	public static final int LIST_CATE_MULTICHOOSE = 3;

	/**
	 * list embedded category:sub node instance as list embedded view and could
	 * be edit separately
	 */
	public static final int LIST_CATE_DISPLAYONLY = 4;

	protected String seName;

	protected String nodeName;

	protected String nodeInstID;

	protected String baseNodeInstID;

	protected String mapFieldName;

	protected String mapBaseFieldName;

	/**
	 * Indicate whether this node is the start host node
	 */
	protected boolean hostNodeFlag = false;

	/**
	 * Indicate whether this node need to be edit
	 */
	protected boolean editNodeFlag = false;

	protected List<SearchConfigConnectCondition> connectionConditions = new ArrayList<>();

	protected List<UIModelNodeConfigPreCondition> preConditions = new ArrayList<>();

	protected List<PageButtonConfigure> pageButtonConfigureList = new ArrayList<>();

	protected ServiceEntityConfigureMap seConfigureMap;
	
	protected IGetSENode<?> getSENodeCallback;
	
	protected Function<ServiceEntityNode, List<ServiceEntityNode>> getSENodeListCallback;

	protected String seNodeVarName;

	protected String toUIModelMethodHead;

	protected String fromUIModelMethodHead;

	protected List<UIModelFieldInfo> uiModelFieldList = new ArrayList<UIModelFieldInfo>();

	protected int toBaseNodeType;

	protected String retrieveCompoundMethodHead;

	protected String retrieveCompoundMethodBody;

	protected String retrieveBufferMethodHead;

	protected String retrieveBufferMethodBody;

	protected String toBufferMethodHead;

	protected String toBufferMethodBody;
	
	// Reference to map node uuid
	protected String refMapNodeUUID;

	protected List<ServiceViewControlInstallPiece> installPieceList = new ArrayList<ServiceViewControlInstallPiece>();

	protected List<ServiceControllerMethodInstallPiece> installMethodPieceList = new ArrayList<ServiceControllerMethodInstallPiece>();

	/**
	 * Indicate how the sub node is embedded as list view inside source node
	 */
	protected int listEmbededCategory;
	
	protected boolean attachmentNodeFlag;

	/**
	 * IF this node need selection function
	 */
	protected boolean selectScenaFlag = false;

	/**
	 * IF this node need buffer function on service
	 */
	protected boolean bufferScenaFlag = false;

	/**
	 * IF this node need default ID helper service
	 */
	protected boolean idHelperFlag = false;

	protected List<String> dropdownFieldList = new ArrayList<>();

	protected List<String> readOnlyFieldList = new ArrayList<>();

	protected Map<String, String> mandatoryFieldList = new HashMap<>();

	protected Class<?> subNodeUIModelCls;

	protected Class<?> subNodeSearchModelCls;

	protected PageConfigure pageConfigure;

	/**
	 * Define whether this node is finance object and key in map for finance
	 * object
	 */
	protected int finAccObjectKey;

	/**
	 * Define whether this node is finance object and label value in map for
	 * finance object
	 */
	protected String finAccObjectLabel;

	protected ServiceEntityManager serviceEntityManager;
	
	/**
	 * where the data conversion logic should take place
	 */
	protected Object logicManager;

	protected String convToUIMethod;

	protected Class<?>[] convToUIMethodParas;

	protected String convUIToMethod;

	protected Class<?>[] convUIToMethodParas;

	protected String uiModelName;

	protected String uiModelPackage;

	protected String searchModelName;

	protected String searchModelPackage;

	protected String nodeManagerName;

	protected String nodeManagerPackage;

	protected String toUIModelMethodHeadConstant;

	protected String fromUIModelMethodHeadConstant;
	
	protected String initModelName;
	
	/**
	 * Fields used to generate UI model
	 */

	protected String[] initModelFieldsArray;
	
	protected String srcFolderPath;
	
	protected String refSelectURL;
	
	protected String client;

	public List<String> getReadOnlyFieldList() {
		return readOnlyFieldList;
	}

	public void setReadOnlyFieldList(List<String> readOnlyFieldList) {
		this.readOnlyFieldList = readOnlyFieldList;
	}

	public void addReadOnlyFieldList(String field) {
		if (this.readOnlyFieldList == null) {
			this.readOnlyFieldList = new ArrayList<String>();
		}
		for (String tmpField : this.readOnlyFieldList) {
			if (tmpField.equals(field)) {
				return;
			}
		}
		readOnlyFieldList.add(field);
	}

	protected HashMap<String, String> dateFieldMap = new HashMap<>();

	public String getSeName() {
		return seName;
	}

	public void setSeName(String seName) {
		this.seName = seName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeInstID() {
		return nodeInstID;
	}

	public void setNodeInstID(String nodeInstID) {
		this.nodeInstID = nodeInstID;
	}

	public String getBaseNodeInstID() {
		return baseNodeInstID;
	}

	public void setBaseNodeInstID(String baseNodeInstID) {
		this.baseNodeInstID = baseNodeInstID;
	}

	public String getMapFieldName() {
		return mapFieldName;
	}

	public void setMapFieldName(String mapFieldName) {
		this.mapFieldName = mapFieldName;
	}

	public String getMapBaseFieldName() {
		return mapBaseFieldName;
	}

	public void setMapBaseFieldName(String mapBaseFieldName) {
		this.mapBaseFieldName = mapBaseFieldName;
	}

	public boolean isHostNodeFlag() {
		return hostNodeFlag;
	}

	public void setHostNodeFlag(boolean hostNodeFlag) {
		this.hostNodeFlag = hostNodeFlag;
	}

	public boolean isEditNodeFlag() {
		return editNodeFlag;
	}

	public void setEditNodeFlag(boolean editNodeFlag) {
		this.editNodeFlag = editNodeFlag;
	}

	public List<SearchConfigConnectCondition> getConnectionConditions() {
		return connectionConditions;
	}

	public void setConnectionConditions(
			List<SearchConfigConnectCondition> connectionConditions) {
		this.connectionConditions = connectionConditions;
	}

	public List<UIModelNodeConfigPreCondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(
			List<UIModelNodeConfigPreCondition> preConditions) {
		this.preConditions = preConditions;
	}

	public ServiceEntityConfigureMap getSeConfigureMap() {
		return seConfigureMap;
	}

	public void setSeConfigureMap(ServiceEntityConfigureMap seConfigureMap) {
		this.seConfigureMap = seConfigureMap;
	}

	public String getSeNodeVarName() {
		return seNodeVarName;
	}

	public void setSeNodeVarName(String seNodeVarName) {
		this.seNodeVarName = seNodeVarName;
	}

	public String getToUIModelMethodHead() {
		return toUIModelMethodHead;
	}

	public void setToUIModelMethodHead(String toUIModelMethodHead) {
		this.toUIModelMethodHead = toUIModelMethodHead;
	}

	public String getFromUIModelMethodHead() {
		return fromUIModelMethodHead;
	}

	public void setFromUIModelMethodHead(String fromUIModelMethodHead) {
		this.fromUIModelMethodHead = fromUIModelMethodHead;
	}

	public List<UIModelFieldInfo> getUiModelFieldList() {
		return uiModelFieldList;
	}

	public void setUiModelFieldList(List<UIModelFieldInfo> uiModelFieldList) {
		this.uiModelFieldList = uiModelFieldList;
	}

	public int getToBaseNodeType() {
		return toBaseNodeType;
	}

	public void setToBaseNodeType(int toBaseNodeType) {
		this.toBaseNodeType = toBaseNodeType;
	}

	public String getRetrieveBufferMethodHead() {
		return retrieveBufferMethodHead;
	}

	public void setRetrieveBufferMethodHead(String retrieveBufferMethodHead) {
		this.retrieveBufferMethodHead = retrieveBufferMethodHead;
	}

	public String getRetrieveBufferMethodBody() {
		return retrieveBufferMethodBody;
	}

	public void setRetrieveBufferMethodBody(String retrieveBufferMethodBody) {
		this.retrieveBufferMethodBody = retrieveBufferMethodBody;
	}

	public String getRetrieveCompoundMethodHead() {
		return retrieveCompoundMethodHead;
	}

	public void setRetrieveCompoundMethodHead(String retrieveCompoundMethodHead) {
		this.retrieveCompoundMethodHead = retrieveCompoundMethodHead;
	}

	public String getRetrieveCompoundMethodBody() {
		return retrieveCompoundMethodBody;
	}

	public void setRetrieveCompoundMethodBody(String retrieveCompoundMethodBody) {
		this.retrieveCompoundMethodBody = retrieveCompoundMethodBody;
	}

	public List<ServiceViewControlInstallPiece> getInstallPieceList() {
		return installPieceList;
	}

	public void setInstallPieceList(
			List<ServiceViewControlInstallPiece> installPieceList) {
		this.installPieceList = installPieceList;
	}

	public void addToInstallPiece(ServiceViewControlInstallPiece installPiece) {
		this.installPieceList.add(installPiece);
	}

	public boolean isSelectScenaFlag() {
		return selectScenaFlag;
	}

	public void setSelectScenaFlag(boolean selectScenaFlag) {
		this.selectScenaFlag = selectScenaFlag;
	}

	public boolean isBufferScenaFlag() {
		return bufferScenaFlag;
	}

	public void setBufferScenaFlag(boolean bufferScenaFlag) {
		this.bufferScenaFlag = bufferScenaFlag;
	}

	public List<String> getDropdownFieldList() {
		return dropdownFieldList;
	}

	public void setDropdownFieldList(List<String> dropdownFieldList) {
		this.dropdownFieldList = dropdownFieldList;
	}

	public String getToBufferMethodHead() {
		return toBufferMethodHead;
	}

	public void setToBufferMethodHead(String toBufferMethodHead) {
		this.toBufferMethodHead = toBufferMethodHead;
	}

	public String getToBufferMethodBody() {
		return toBufferMethodBody;
	}

	public void setToBufferMethodBody(String toBufferMethodBody) {
		this.toBufferMethodBody = toBufferMethodBody;
	}

	public void addDropdownFieldList(String field) {
		boolean duplicateFlag = false;
		for (String fieldName : this.dropdownFieldList) {
			if (fieldName.equals(field)) {
				duplicateFlag = true;
				break;
			}
		}
		if (!duplicateFlag) {
			this.dropdownFieldList.add(field);
		}
	}

	public void addUIModelFieldInfo(UIModelFieldInfo uiModelFieldInfo) {
		this.uiModelFieldList.add(uiModelFieldInfo);
	}

	public HashMap<String, String> getDateFieldMap() {
		return dateFieldMap;
	}

	public void setDateFieldMap(HashMap<String, String> dateFieldMap) {
		this.dateFieldMap = dateFieldMap;
	}

	public void putDateFieldMap(String fieldName, String dateStringField) {
		if (this.dateFieldMap.containsKey(fieldName)) {
			return;
		}
		this.dateFieldMap.put(fieldName, dateStringField);
	}

	public List<PageButtonConfigure> getPageButtonConfigureList() {
		return pageButtonConfigureList;
	}

	public void setPageButtonConfigureList(
			List<PageButtonConfigure> pageButtonConfigureList) {
		this.pageButtonConfigureList = pageButtonConfigureList;
	}

	public int getListEmbededCategory() {
		return listEmbededCategory;
	}

	public void setListEmbededCategory(int listEmbededCategory) {
		this.listEmbededCategory = listEmbededCategory;
	}

	public boolean getAttachmentNodeFlag() {
		return attachmentNodeFlag;
	}

	public void setAttachmentNodeFlag(boolean attachmentNodeFlag) {
		this.attachmentNodeFlag = attachmentNodeFlag;
	}

	public Class<?> getSubNodeUIModelCls() {
		return subNodeUIModelCls;
	}

	public void setSubNodeUIModelCls(Class<?> subNodeUIModelCls) {
		this.subNodeUIModelCls = subNodeUIModelCls;
	}

	public Class<?> getSubNodeSearchModelCls() {
		return subNodeSearchModelCls;
	}

	public void setSubNodeSearchModelCls(Class<?> subNodeSearchModelCls) {
		this.subNodeSearchModelCls = subNodeSearchModelCls;
	}

	public List<ServiceControllerMethodInstallPiece> getInstallMethodPieceList() {
		return installMethodPieceList;
	}

	public void setInstallMethodPieceList(
			List<ServiceControllerMethodInstallPiece> installMethodPieceList) {
		this.installMethodPieceList = installMethodPieceList;
	}

	public void addInstallMethodPieceList(
			ServiceControllerMethodInstallPiece serviceControllerMethodInstallPiece) {
		this.installMethodPieceList.add(serviceControllerMethodInstallPiece);
	}

	public boolean isIdHelperFlag() {
		return idHelperFlag;
	}

	public void setIdHelperFlag(boolean idHelperFlag) {
		this.idHelperFlag = idHelperFlag;
	}

	public PageConfigure getPageConfigure() {
		return pageConfigure;
	}

	public void setPageConfigure(PageConfigure pageConfigure) {
		this.pageConfigure = pageConfigure;
	}

	public int getFinAccObjectKey() {
		return finAccObjectKey;
	}

	public void setFinAccObjectKey(int finAccObjectKey) {
		this.finAccObjectKey = finAccObjectKey;
	}

	public String getFinAccObjectLabel() {
		return finAccObjectLabel;
	}

	public void setFinAccObjectLabel(String finAccObjectLabel) {
		this.finAccObjectLabel = finAccObjectLabel;
	}

	public Map<String, String> getMandatoryFieldList() {
		return mandatoryFieldList;
	}

	public void setMandatoryFieldList(Map<String, String> mandatoryFieldList) {
		this.mandatoryFieldList = mandatoryFieldList;
	}

	public ServiceEntityManager getServiceEntityManager() {
		return serviceEntityManager;
	}

	public void setServiceEntityManager(
			ServiceEntityManager serviceEntityManager) {
		this.serviceEntityManager = serviceEntityManager;
	}

	public Object getLogicManager() {
		return logicManager;
	}

	public void setLogicManager(Object logicManager) {
		this.logicManager = logicManager;
	}

	public String getConvToUIMethod() {
		return convToUIMethod;
	}

	public void setConvToUIMethod(String convToUIMethod) {
		this.convToUIMethod = convToUIMethod;
	}

	public String getConvUIToMethod() {
		return convUIToMethod;
	}

	public void setConvUIToMethod(String convUIToMethod) {
		this.convUIToMethod = convUIToMethod;
	}

	public Class<?>[] getConvToUIMethodParas() {
		return convToUIMethodParas;
	}

	public void setConvToUIMethodParas(Class<?>[] convToUIMethodParas) {
		this.convToUIMethodParas = convToUIMethodParas;
	}

	public Class<?>[] getConvUIToMethodParas() {
		return convUIToMethodParas;
	}

	public void setConvUIToMethodParas(Class<?>[] convUIToMethodParas) {
		this.convUIToMethodParas = convUIToMethodParas;
	}

	public String getRefMapNodeUUID() {
		return refMapNodeUUID;
	}

	public void setRefMapNodeUUID(String refMapNodeUUID) {
		this.refMapNodeUUID = refMapNodeUUID;
	}

	public String getUiModelName() {
		return uiModelName;
	}

	public void setUiModelName(String uiModelName) {
		this.uiModelName = uiModelName;
	}

	public String getUiModelPackage() {
		return uiModelPackage;
	}

	public void setUiModelPackage(String uiModelPackage) {
		this.uiModelPackage = uiModelPackage;
	}

	public String getSearchModelName() {
		return searchModelName;
	}

	public void setSearchModelName(String searchModelName) {
		this.searchModelName = searchModelName;
	}

	public String getSearchModelPackage() {
		return searchModelPackage;
	}

	public void setSearchModelPackage(String searchModelPackage) {
		this.searchModelPackage = searchModelPackage;
	}

	public String getNodeManagerName() {
		return nodeManagerName;
	}

	public void setNodeManagerName(String nodeManagerName) {
		this.nodeManagerName = nodeManagerName;
	}

	public String getNodeManagerPackage() {
		return nodeManagerPackage;
	}

	public void setNodeManagerPackage(String nodeManagerPackage) {
		this.nodeManagerPackage = nodeManagerPackage;
	}

	public String getToUIModelMethodHeadConstant() {
		return toUIModelMethodHeadConstant;
	}

	public void setToUIModelMethodHeadConstant(
			String toUIModelMethodHeadConstant) {
		this.toUIModelMethodHeadConstant = toUIModelMethodHeadConstant;
	}

	public String getFromUIModelMethodHeadConstant() {
		return fromUIModelMethodHeadConstant;
	}

	public void setFromUIModelMethodHeadConstant(
			String fromUIModelMethodHeadConstant) {
		this.fromUIModelMethodHeadConstant = fromUIModelMethodHeadConstant;
	}

	public String getInitModelName() {
		return initModelName;
	}

	public void setInitModelName(String initModelName) {
		this.initModelName = initModelName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String[] getInitModelFieldsArray() {
		return initModelFieldsArray;
	}

	public void setInitModelFieldsArray(String[] initModelFieldsArray) {
		this.initModelFieldsArray = initModelFieldsArray;
	}

	public String getSrcFolderPath() {
		return srcFolderPath;
	}

	public void setSrcFolderPath(String srcFolderPath) {
		this.srcFolderPath = srcFolderPath;
	}

	public String getRefSelectURL() {
		return refSelectURL;
	}

	public void setRefSelectURL(String refSelectURL) {
		this.refSelectURL = refSelectURL;
	}

	public IGetSENode getGetSENodeCallback() {
		return getSENodeCallback;
	}

	public void setGetSENodeCallback(IGetSENode getSENodeCallback) {
		this.getSENodeCallback = getSENodeCallback;
	}

	public Function<ServiceEntityNode, List<ServiceEntityNode>> getGetSENodeListCallback() {
		return getSENodeListCallback;
	}

	public void setGetSENodeListCallback(
			Function<ServiceEntityNode, List<ServiceEntityNode>> getSENodeListCallback) {
		this.getSENodeListCallback = getSENodeListCallback;
	}


	/**
	 * Executor for Doc root action
	 */
	public interface IGetSENode<T extends ServiceEntityNode> {
		/**
		 * This method is called in the impersonation execution context.
		 *
		 * @return The return value
		 */
		T execute(T serviceEntityNode) throws ServiceEntityConfigureException;
	}
}
