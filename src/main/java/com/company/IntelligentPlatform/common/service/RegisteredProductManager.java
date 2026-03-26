package com.company.IntelligentPlatform.common.service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.IntelligentPlatform.common.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.company.IntelligentPlatform.common.repository.RegisteredProductRepository;
import com.company.IntelligentPlatform.common.service.JpaServiceEntityDAO;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class RegisteredProductManager extends ServiceEntityManager {

	public static final String METHOD_ConvRegisteredProductToUI = "convRegisteredProductToUI";

	public static final String METHOD_ConvUIToRegisteredProduct = "convUIToRegisteredProduct";

	public static final String METHOD_ConvPurchaseOrgPartyToUI = "convPurchaseOrgPartyToUI";

	public static final String METHOD_ConvProductOrgPartyToUI = "convProductOrgPartyToUI";

	public static final String METHOD_ConvRegisteredProductPartyToUI = "convRegisteredProductPartyToUI";

	public static final String METHOD_ConvUIToRegisteredProductParty = "convUIToRegisteredProductParty";

	public static final String METHOD_ConvProductOrganizationToUI = "convProductOrganizationToUI";

	public static final String METHOD_ConvProductOrgContactToUI = "convProductOrgContactToUI";

	public static final String METHOD_ConvSupportOrgPartyToUI = "convSupportOrgPartyToUI";

	public static final String METHOD_ConvSupportOrganizationToUI = "convSupportOrganizationToUI";

	public static final String METHOD_ConvSupportOrgContactToUI = "convSupportOrgContactToUI";

	public static final String METHOD_ConvUIToPurchaseOrgParty = "convUIToPurchaseOrgParty";

	public static final String METHOD_ConvMaterialStockKeepUnitToUI = "convMaterialStockKeepUnitToUI";

	public static final String METHOD_ConvPurchaseOrgContactToUI = "convPurchaseOrgContactToUI";

	public static final String METHOD_ConvPurchaseOrganizationToUI = "convPurchaseOrganizationToUI";

	public static final String METHOD_ConvRegisteredProductActionLogToUI = "convRegisteredProductActionLogToUI";

	public static final String METHOD_ConvDocumentToActionLogUI = "convDocumentToActionLogUI";

	public static final String METHOD_ConvUpdateByToActionLogUI = "convUpdateByToActionLogUI";

	public static final String METHOD_ConvSalesOrgPartyToUI = "convSalesOrgPartyToUI";

	public static final String METHOD_ConvUIToSalesOrgParty = "convUIToSalesOrgParty";

	public static final String METHOD_ConvSalesOrganizationToUI = "convSalesOrganizationToUI";

	public static final String METHOD_ConvSalesOrgContactToUI = "convSalesOrgContactToUI";

	public static final String METHOD_ConvCustomerPartyToUI = "convCustomerPartyToUI";

	public static final String METHOD_ConvUIToCustomerParty = "convUIToCustomerParty";

	public static final String METHOD_ConvCorporateCustomerToUI = "convCorporateCustomerToUI";

	public static final String METHOD_ConvCustomerContactToUI = "convCustomerContactToUI";

	public static final String METHOD_ConvSupplierPartyToUI = "convSupplierPartyToUI";

	public static final String METHOD_ConvUIToSupplierParty = "convUIToSupplierParty";

	public static final String METHOD_ConvUIToSupportOrgParty = "convUIToSupportOrgParty";

	public static final String METHOD_ConvUIToProductOrgParty = "convUIToProductOrgParty";

	public static final String METHOD_ConvCorporateSupplierToUI = "convCorporateSupplierToUI";

	public static final String METHOD_ConvSupplierContactToUI = "convSupplierContactToUI";

	public static final String METHOD_ConvRegisteredProductAttachmentToUI = "convRegisteredProductAttachmentToUI";

	@Autowired
	protected BsearchService bsearchService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected RegisteredProductRepository registeredProductDAO;
	@Autowired
	protected RegisteredProductConfigureProxy registeredProductConfigureProxy;

	@Autowired
	protected RegisteredProductIdHelper registeredProductIdHelper;

	@Autowired
	protected MaterialConfigureTemplateManager materialConfigureTemplateManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected MatDecisionValueSettingManager matDecisionValueSettingManager;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected MaterialActionNodeProxy materialActionNodeProxy;

	@Autowired
	protected DocActionNodeProxy docActionNodeProxy;

	@Autowired
	protected DocInvolvePartyProxy docInvolvePartyProxy;

	@Autowired
	protected RegisteredProductSearchProxy registeredProductSearchProxy;

	@Autowired
	protected MaterialManager materialManager;

	protected Logger logger = LoggerFactory.getLogger(RegisteredProductManager.class);

	private Map<String, Map<Integer, String>> traceStatusMapLan = new HashMap<>();

	private Map<String, Map<Integer, String>> actionCodeMapLan = new HashMap<>();

	@Override
	public ServiceEntityNode newRootEntityNode(String client)
			throws ServiceEntityConfigureException {
		RegisteredProduct registeredProduct = (RegisteredProduct) super
				.newRootEntityNode(client);
		String materialSKUId = registeredProductIdHelper.genDefaultId(client);
		registeredProduct.setId(materialSKUId);
		return registeredProduct;
	}

	public Map<Integer, String> initActionCode(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.actionCodeMapLan, RegisteredProductActionLogUIModel.class,
				"actionCode");
	}

	public Map<Integer, String> initTraceStatusMap(String languageCode)
			throws ServiceEntityInstallationException {
		return ServiceLanHelper.initDefLanguageMapUIModel(languageCode,
				this.traceStatusMapLan, MaterialStockKeepUnitUIModel.class,
				"traceStatus");
	}

	/**
	 * Core Logic to active register product, as well as update into persistence
	 * 
	 * @param registeredProduct
	 * @param logonUserUUID
	 * @param organizationUUID
	 * @return
	 * @throws RegisteredProductException
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public RegisteredProduct activeRegisterProduct(
			RegisteredProduct registeredProduct, int documentType,
			String refDocMatItemUUID, String refDocumentUUID,
			String logonUserUUID, String organizationUUID, String languageCode)
			throws RegisteredProductException,
			ServiceEntityInstallationException, ServiceEntityConfigureException {
		int[] invalidStatusArray = { RegisteredProduct.TRACESTATUS_ARCHIVE,
				RegisteredProduct.TRACESTATUS_INSERVICE, RegisteredProduct.TRACESTATUS_DELETE,
				RegisteredProduct.TRACESTATUS_WASTE };
		throwInvalidPrevStatus(registeredProduct, invalidStatusArray,
				languageCode);
		registeredProduct.setTraceStatus(RegisteredProduct.TRACESTATUS_ACTIVE);
		this.updateSENode(registeredProduct, logonUserUUID, organizationUUID);
		this.registerActionLog(registeredProduct,
				RegisteredProductActionLog.DOC_ACTION_ACTIVE, documentType,
				refDocMatItemUUID, refDocumentUUID, logonUserUUID,
				organizationUUID);
		return registeredProduct;
	}

	private void throwInvalidPrevStatus(RegisteredProduct registeredProduct,
			int[] invalidStatusArray, String languageCode)
			throws ServiceEntityInstallationException,
			RegisteredProductException {
		for (int invalidStatus : invalidStatusArray) {
			if (registeredProduct.getTraceStatus() == invalidStatus) {
				Map<Integer, String> traceStatusMap = initTraceStatusMap(languageCode);
				throw new RegisteredProductException(
						RegisteredProductException.PARA_ACTIVE_INVALID_STATUS,
						traceStatusMap.get(registeredProduct.getTraceStatus()));
			}
		}
	}

	public void registerActionLog(RegisteredProduct registeredProduct,
			int actionCode, int documentType, String refDocMatItemUUID,
			String refDocumentUUID, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException {
		docActionNodeProxy.updateDocActionWrapper(actionCode, RegisteredProductActionLog.NODENAME,
				refDocMatItemUUID, documentType, this, null, registeredProduct, logonUserUUID, organizationUUID);
	}

	public static List<Integer> getDocFlowDocTypeList() {
		List<Integer> resultList = new ArrayList<>();
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_PRODORDERREPORT);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
		resultList.add(IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
		return resultList;
	}

	/**
	 * Core Logic to create register product after self production
	 * 
	 * @param tempMaterialSKU
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws RegisteredProductException
	 */
	public List<ServiceEntityNode> createRegisteredProductWrapperForProduct(
			MaterialStockKeepUnit tempMaterialSKU,  String serialId, DocMatItemNode docMatItemNode,
			RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException, RegisteredProductException {
		/*
		 * [Step1] Check serial id duplicate
		 */
		if(tempMaterialSKU.getTraceMode() != MaterialStockKeepUnit.TRACEMODE_SINGLE){
			// in case not single trace model template SKU
			return null;
		}
		boolean checkFlag = checkSerialIdRegister(serialId, tempMaterialSKU.getUuid(), tempMaterialSKU.getClient());
		if(!checkFlag){
			throw new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID, tempMaterialSKU.getId(), serialId);
		}
		/*
		 * [Step2] initial registered product and initial copy properties from
		 * template
		 */
		List<ServiceEntityNode> resultList = new ArrayList<>();
		RegisteredProduct registeredProduct = (RegisteredProduct) newRootEntityNode(tempMaterialSKU
				.getClient());
		initRegisteredProductFromMaterialSKU(registeredProduct, tempMaterialSKU);
		if (!ServiceEntityStringHelper.checkNullString(serialId)) {
			registeredProduct.setSerialId(serialId);
		}
		resultList.add(registeredProduct);
		RegisteredProductInvolveParty productInvolveParty = genInvolveOrgPartyWrapper(
				registeredProduct,
				RegisteredProductInvolveParty.PARTY_ROLE_PRODORG, docMatItemNode,
				registeredProductInvolvePartyMatrix.getProductOrganization(), null);
		resultList.add(productInvolveParty);

		RegisteredProductInvolveParty supportInvolveParty = genInvolveOrgPartyWrapper(
				registeredProduct,
				RegisteredProductInvolveParty.PARTY_ROLE_SUPPORTORG, docMatItemNode,
				registeredProductInvolvePartyMatrix.getSupportOrganization(), null);
		resultList.add(supportInvolveParty);

		RegisteredProductInvolveParty salesInvolveParty = genInvolvePartyWrapper(
				registeredProduct,
				RegisteredProductInvolveParty.PARTY_ROLE_SALESORG, null, null, null);
		resultList.add(salesInvolveParty);

		RegisteredProductInvolveParty customerInvolveParty = genInvolvePartyWrapper(
				registeredProduct,
				RegisteredProductInvolveParty.PARTY_ROLE_CUSTOMER, null, null, null);
		resultList.add(customerInvolveParty);

		try {
			List<ServiceEntityNode> registeredProdExtendPropList = genExtendPropertyWrapper(
					registeredProduct, tempMaterialSKU);
			if (!ServiceCollectionsHelper
					.checkNullList(registeredProdExtendPropList)) {
				resultList.addAll(registeredProdExtendPropList);
			}
		} catch (MaterialException e) {

		}
		/*
		 * [Step2] update to persistence
		 */
		this.updateSENodeList(resultList, logonUserUUID, organizationUUID);
		return resultList;
	}

	/**
	 * Logic to get registered product instance by serialId (or optional by
	 * template material UUID)
	 * 
	 * @param serialId
	 * @param tempMaterialUUID
	 * @param client
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public RegisteredProduct getRegisteredProductBySerialId(String serialId,
			String tempMaterialUUID, String client)
			throws ServiceEntityConfigureException {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure key1 = new ServiceBasicKeyStructure(serialId,
				RegisteredProduct.FEILD_SERIALID);
		keyList.add(key1);
		if (!ServiceEntityStringHelper.checkNullString(tempMaterialUUID)) {
			ServiceBasicKeyStructure key2 = new ServiceBasicKeyStructure(
					tempMaterialUUID, "refMaterialSKUUUID");
			keyList.add(key2);
		}
		List<ServiceEntityNode> rawList = this.getEntityNodeListByKeyList(
				keyList, RegisteredProduct.NODENAME, client, null);
		if (ServiceCollectionsHelper.checkNullList(rawList)) {
			return null;
		}
		return (RegisteredProduct) rawList.get(0);
	}

	/**
	 * Utility method: just filter out [Registered product] instance out of registered product list after creation
	 * @param registeredProductList
	 * @return
	 */
    public static RegisteredProduct filterOutRegisteredProduct(List<ServiceEntityNode> registeredProductList){
		if(ServiceCollectionsHelper.checkNullList(registeredProductList)){
			return null;
		}
		List<ServiceEntityNode> resultList = registeredProductList.stream().filter(serviceEntityNode -> {
			return RegisteredProduct.SENAME.equals(serviceEntityNode.getServiceEntityName()) && RegisteredProduct.NODENAME.equals(serviceEntityNode.getNodeName());
		}).collect(Collectors.toList());
		if(ServiceCollectionsHelper.checkNullList(resultList)){
			return null;
		}else{
			return (RegisteredProduct) resultList.get(0);
		}
	}

	/**
	 * Determines whether the given material SKU instance is a registered product.
	 *
	 * @param materialStockKeepUnit the MaterialStockKeepUnit instance to check.
	 * @return {@code true} if the material SKU is a registered product, {@code false} otherwise.
	 */
	public static boolean checkRegisteredProduct(MaterialStockKeepUnit materialStockKeepUnit) {
		return RegisteredProduct.SENAME.equals(materialStockKeepUnit.getServiceEntityName());
	}

	public List<ServiceEntityNode> newRegisteredProductCore(MaterialStockKeepUnit tempMaterialSKU,
															DocMatItemNode docMatItemNode,  String serialId,
															 Map<Integer, Account> involvePartyMap) throws ServiceEntityConfigureException {
		RegisteredProduct registeredProduct = (RegisteredProduct) newRootEntityNode(tempMaterialSKU
				.getClient());
		if (!ServiceEntityStringHelper.checkNullString(serialId)) {
			registeredProduct.setSerialId(serialId);
		}
		initRegisteredProductFromMaterialSKU(registeredProduct, tempMaterialSKU);
		List<ServiceEntityNode> resultList = new ArrayList<>();
		resultList.add(registeredProduct);

		/*
		 * [Step3] initial involved parties
		 */
		List<ServiceEntityNode> involvePartyList = genInvolvePartyBatch(registeredProduct, docMatItemNode,
				involvePartyMap);
		if(!ServiceCollectionsHelper.checkNullList(involvePartyList)){
			resultList.addAll(involvePartyList);
		}
		try {
			List<ServiceEntityNode> registeredProdExtendPropList = genExtendPropertyWrapper(
					registeredProduct, tempMaterialSKU);
			if (!ServiceCollectionsHelper
					.checkNullList(registeredProdExtendPropList)) {
				resultList.addAll(registeredProdExtendPropList);
			}
		} catch (MaterialException e) {

		}
		return resultList;
	}

	public static class RegisteredProductInvolvePartyMatrix {

		protected CorporateCustomer corporateSupplier;

		protected IndividualCustomer supplierContact;

		protected Organization purchaseOrganization;

		protected Organization productOrganization;

		protected Organization supportOrganization;

		public RegisteredProductInvolvePartyMatrix corporateSupplier(CorporateCustomer corporateSupplier) {
			this.corporateSupplier = corporateSupplier;
			return this;
		}

		public RegisteredProductInvolvePartyMatrix supplierContact(IndividualCustomer supplierContact) {
			this.supplierContact = supplierContact;
			return this;
		}

		public RegisteredProductInvolvePartyMatrix purchaseOrganization(Organization purchaseOrganization) {
			this.purchaseOrganization = purchaseOrganization;
			return this;
		}

		public RegisteredProductInvolvePartyMatrix productOrganization(Organization productOrganization) {
			this.productOrganization = productOrganization;
			return this;
		}

		public RegisteredProductInvolvePartyMatrix supportOrganization(Organization supportOrganization) {
			this.supportOrganization = supportOrganization;
			return this;
		}

		public CorporateCustomer getCorporateSupplier() {
			return corporateSupplier;
		}

		public void setCorporateSupplier(CorporateCustomer corporateSupplier) {
			this.corporateSupplier = corporateSupplier;
		}

		public IndividualCustomer getSupplierContact() {
			return supplierContact;
		}

		public void setSupplierContact(IndividualCustomer supplierContact) {
			this.supplierContact = supplierContact;
		}

		public Organization getPurchaseOrganization() {
			return purchaseOrganization;
		}

		public void setPurchaseOrganization(Organization purchaseOrganization) {
			this.purchaseOrganization = purchaseOrganization;
		}

		public Organization getProductOrganization() {
			return productOrganization;
		}

		public void setProductOrganization(Organization productOrganization) {
			this.productOrganization = productOrganization;
		}

		public Organization getSupportOrganization() {
			return supportOrganization;
		}

		public void setSupportOrganization(Organization supportOrganization) {
			this.supportOrganization = supportOrganization;
		}
	}

	public List<ServiceEntityNode> createRegisteredProductWrapper(
			MaterialStockKeepUnit tempMaterialSKU, String serialId,
			DocMatItemNode docMatItemNode,
			RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix,
			String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException,
			RegisteredProductException {
		int documentType = docMatItemNode.getHomeDocumentType();
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT) {
			return createRegisteredProductWrapperForPurchase(tempMaterialSKU,
					serialId, docMatItemNode, registeredProductInvolvePartyMatrix, logonUserUUID,
					organizationUUID);
		}
		if (documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER) {
			return createRegisteredProductWrapperForProduct(tempMaterialSKU,
					serialId, docMatItemNode, registeredProductInvolvePartyMatrix,  logonUserUUID,
					organizationUUID);
		}
		// Should raise exception here.
		return null;
	}

	/**
	 * Core Logic to create register product after self production
	 * 
	 * @param tempMaterialSKU
	 * @return
	 * @throws ServiceEntityConfigureException
	 * @throws RegisteredProductException
	 */
	public List<ServiceEntityNode> createRegisteredProductWrapperForPurchase(
			MaterialStockKeepUnit tempMaterialSKU, String serialId,
			DocMatItemNode docMatItemNode,
			RegisteredProductInvolvePartyMatrix registeredProductInvolvePartyMatrix, String logonUserUUID,
			String organizationUUID) throws ServiceEntityConfigureException,
			RegisteredProductException {
		/*
		 * [Step1] Check serial id duplicate
		 */
		boolean checkFlag = checkSerialIdRegister(serialId, tempMaterialSKU.getUuid(), tempMaterialSKU.getClient());
		if(!checkFlag){
			throw new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID, tempMaterialSKU.getId(), serialId);
		}
		List<ServiceEntityNode> resultList;
		/*
		 * [Step2] initial registered product and initial copy properties from
		 * template
		 */
		Organization purchaseOrganization = registeredProductInvolvePartyMatrix.getPurchaseOrganization();
		CorporateCustomer corporateSupplier = registeredProductInvolvePartyMatrix.getCorporateSupplier();
		Map<Integer, Account> involvePartyMap = new HashMap<>();
		involvePartyMap.put(RegisteredProductInvolveParty.PARTY_ROLE_PURORG,
				purchaseOrganization);
		involvePartyMap.put(RegisteredProductInvolveParty.PARTY_ROLE_SALESORG,
				corporateSupplier);
		involvePartyMap.put(RegisteredProductInvolveParty.PARTY_ROLE_CUSTOMER,
				purchaseOrganization);
		resultList = newRegisteredProductCore(tempMaterialSKU, docMatItemNode, serialId, involvePartyMap);

		/*
		 * [Step4] update to persistence
		 */
		this.updateSENodeList(resultList, logonUserUUID, organizationUUID);
		return resultList;
	}

	/**
	 * Logic to check serial id, such as duplicate when register new registered product
	 * @param serialId
	 * @param refTemplateSKUUUID
	 * @param client
	 * @return
	 */
	private boolean checkSerialIdRegister(String serialId, String refTemplateSKUUUID, String client) throws ServiceEntityConfigureException, RegisteredProductException {
		if(!ServiceEntityStringHelper.checkNullString(serialId)){
			// except for empty serial id
			return true;
		}
		RegisteredProduct existedRegisteredProduct = getRegisteredProductBySerialId(
				serialId, refTemplateSKUUUID,
				client);
		if (existedRegisteredProduct != null) {
			return false;
		}else{
			return true;
		}
	}

	private List<ServiceEntityNode> genInvolvePartyBatch(RegisteredProduct registeredProduct,
														 DocMatItemNode docMatItemNode,  Map<Integer,
			Account> involvePartyMap) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> involvePartyList = new ArrayList<>();
		if(involvePartyMap == null || involvePartyMap.size() == 0){
			return null;
		}
		Set<Integer> keyList = involvePartyMap.keySet();
		for(Integer key: keyList){
			RegisteredProductInvolveParty involveParty = genInvolvePartyWrapper(
					registeredProduct,
					key,docMatItemNode,
					involvePartyMap.get(key), null);
			involvePartyList.add(involveParty);
		}
		return involvePartyList;
	}

	/**
	 * Logic to check if this Material SKU is registered product with emtpy serial id
	 * @param materialStockKeepUnit
	 * @return
	 */
	public boolean checkEmptyRegisteredProduct(MaterialStockKeepUnit materialStockKeepUnit){
		if(!RegisteredProductManager.checkRegisteredProduct(materialStockKeepUnit)){
			return false;
		}
		RegisteredProduct registeredProduct = (RegisteredProduct) materialStockKeepUnit;
		return ServiceEntityStringHelper.checkNullString(registeredProduct.getSerialId());
	}

	/**
	 * Core Logic to batch assign serial id to empty registered product
	 * @param serialIdList
	 * @param emptyRegisteredProductList
	 * @param logonUserUUID
	 * @param organizationUUID
	 */
	@Deprecated
	public void batchInsertSerialIdToEmptyRegProduct(List<String> serialIdList,
											  List<ServiceEntityNode> emptyRegisteredProductList,
											  String logonUserUUID, String organizationUUID) throws RegisteredProductException, ServiceEntityConfigureException {
		if(ServiceCollectionsHelper.checkNullList(serialIdList) || ServiceCollectionsHelper.checkNullList(emptyRegisteredProductList)){
			return;
		}
		int serialLen = serialIdList.size();
		int prodLen = emptyRegisteredProductList.size();
		List<ServiceEntityNode> updateList = new ArrayList<>();
		for(int i = 0; i < serialLen; i++ ){
			if(i >= prodLen){
				break;
			}
			RegisteredProduct registeredProduct = (RegisteredProduct) emptyRegisteredProductList.get(i);
			if(!checkEmptyRegisteredProduct(registeredProduct)){
				continue;
			}
			boolean checkRegister = this.checkSerialIdRegister(serialIdList.get(i),
					registeredProduct.getRefTemplateUUID(),
					registeredProduct.getClient());
			if(!checkRegister){
				throw new RegisteredProductException(RegisteredProductException.PARA2_DUPLICATE_SERIALID,
						registeredProduct.getId(), serialIdList.get(i));
			}
			registeredProduct.setSerialId(serialIdList.get(i));
			updateList.add(registeredProduct);
		}
		updateSENodeList(updateList, logonUserUUID, organizationUUID);
	}

	/**
	 * Core Logic to update registered product when sales operation executed
	 * 
	 * @param registeredProduct
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> updateRegisteredProductWrapperForSales(
			RegisteredProduct registeredProduct, DocMatItemNode docMatItemNode,
			Organization salesOrganization, Account customer, Account contact,
			String logonUserUUID, String organizationUUID)
			throws ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		RegisteredProductInvolveParty salesInvolveParty = genInvolvePartyWrapper(
				registeredProduct,
				RegisteredProductInvolveParty.PARTY_ROLE_SALESORG, docMatItemNode,
				salesOrganization, null);
		resultList.add(salesInvolveParty);
		RegisteredProductInvolveParty customerInvolveParty = this
				.genInvolvePartyWrapper(registeredProduct,
						RegisteredProductInvolveParty.PARTY_ROLE_CUSTOMER,docMatItemNode,
						customer, contact);
		resultList.add(customerInvolveParty);
		/*
		 * [Step2] update to persistence
		 */
		this.updateSENodeList(resultList, logonUserUUID, organizationUUID);
		return resultList;
	}

	/**
	 * Generate or update involve party by Organization & Employee information
	 * 
	 * @param registeredProduct
	 * @param partyRole
	 * @param organization
	 * @param contactEmployee
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public RegisteredProductInvolveParty genInvolveOrgPartyWrapper(
			RegisteredProduct registeredProduct, int partyRole, DocMatItemNode docMatItemNode,
			Organization organization, ServiceEntityNode contactEmployee)
			throws ServiceEntityConfigureException {
		RegisteredProductInvolveParty productInvolveParty = (RegisteredProductInvolveParty) docInvolvePartyProxy.genInvolveOrgParty(registeredProduct
				, partyRole, RegisteredProductInvolveParty.NODENAME, docMatItemNode,this, organization,
				contactEmployee);
		return productInvolveParty;
	}

	/**
	 * Generate or update involve party by Customer & Contact information
	 *
	 * @param registeredProduct
	 * @param partyRole
	 * @param customerAccount
	 * @param contact
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public RegisteredProductInvolveParty genInvolvePartyWrapper(
			RegisteredProduct registeredProduct, int partyRole, DocMatItemNode docMatItemNode,
			Account customerAccount, Account contact)
			throws ServiceEntityConfigureException {
		return (RegisteredProductInvolveParty) docInvolvePartyProxy.genInvolveParty(registeredProduct, partyRole,
				RegisteredProductInvolveParty.NODENAME, this, docMatItemNode, customerAccount, contact);
	}

	public List<ServiceEntityNode> genExtendPropertyWrapper(
			RegisteredProduct registeredProduct,
			MaterialStockKeepUnit tempMaterialSKU) throws MaterialException,
			ServiceEntityConfigureException {
		List<ServiceEntityNode> resultList = new ArrayList<>();
		List<ServiceEntityNode> extPropertySettingList = matDecisionValueSettingManager
				.getExtPropertySettingList(tempMaterialSKU);
		if (!ServiceCollectionsHelper.checkNullList(extPropertySettingList)) {
			for (ServiceEntityNode seNode : extPropertySettingList) {
				MatConfigExtPropertySetting matConfigExtPropertySetting = (MatConfigExtPropertySetting) seNode;
				RegisteredProductExtendProperty registeredProductExtendProperty = (RegisteredProductExtendProperty) newEntityNode(
						registeredProduct,
						RegisteredProductExtendProperty.NODENAME);
				registeredProductExtendProperty
						.setRefValueSettingUUID(matConfigExtPropertySetting
								.getUuid());
				registeredProductExtendProperty
						.setId(matConfigExtPropertySetting.getId());
				registeredProductExtendProperty
						.setName(matConfigExtPropertySetting.getName());
				registeredProductExtendProperty
						.setQualityInspectFlag(matConfigExtPropertySetting
								.getQualityInspectFlag());
				registeredProductExtendProperty
						.setMeasureFlag(matConfigExtPropertySetting
								.getMeasureFlag());
				registeredProductExtendProperty
						.setRefUnitUUID(matConfigExtPropertySetting
								.getRefUnitUUID());
				resultList.add(registeredProductExtendProperty);
			}
		}
		return resultList;
	}

	public void initRegisteredProductFromMaterialSKU(
			RegisteredProduct registeredProduct,
			MaterialStockKeepUnit tempMaterialSKU) {
		// set refMaterialSKU UUID
		registeredProduct.setRefMaterialSKUUUID(tempMaterialSKU.getUuid());
		// set reference date and production date
		registeredProduct.setReferenceDate(new Date());
		registeredProduct.setProductionDate(new Date());
		// Other properties copy
		registeredProduct.setLength(tempMaterialSKU.getLength());
		registeredProduct.setRefSupplierUUID(tempMaterialSKU
				.getRefSupplierUUID());
		registeredProduct.setPackageMaterialType(tempMaterialSKU
				.getPackageMaterialType());
		registeredProduct.setTraceStatus(tempMaterialSKU.getTraceStatus());
		registeredProduct.setProductionBatchNumber(tempMaterialSKU
				.getProductionBatchNumber());
		registeredProduct.setClient(tempMaterialSKU.getClient());
		registeredProduct.setBarcode(tempMaterialSKU.getBarcode());
		registeredProduct.setRetailPrice(tempMaterialSKU.getRetailPrice());
		registeredProduct.setRefVolumeUnit(tempMaterialSKU.getRefVolumeUnit());
		registeredProduct.setUnitCost(tempMaterialSKU.getUnitCost());
		registeredProduct.setVolume(tempMaterialSKU.getVolume());
		registeredProduct.setMainMaterialUnit(tempMaterialSKU
				.getMainMaterialUnit());
		registeredProduct.setCargoType(tempMaterialSKU.getCargoType());
		registeredProduct.setPurchasePrice(tempMaterialSKU.getPurchasePrice());
		registeredProduct.setId(tempMaterialSKU.getId());
		registeredProduct.setName(tempMaterialSKU.getName());
		registeredProduct.setGrossWeight(tempMaterialSKU.getGrossWeight());
		registeredProduct.setRefWeightUnit(tempMaterialSKU.getRefWeightUnit());
		registeredProduct.setNetWeight(tempMaterialSKU.getNetWeight());
		registeredProduct.setSupplyType(tempMaterialSKU.getSupplyType());
		registeredProduct.setPackageStandard(tempMaterialSKU
				.getPackageStandard());
		registeredProduct.setWidth(tempMaterialSKU.getWidth());
		registeredProduct.setQualityInspectFlag(tempMaterialSKU
				.getQualityInspectFlag());

		registeredProduct.setProductionPlace(tempMaterialSKU
				.getProductionPlace());

		registeredProduct.setRefLengthUnit(tempMaterialSKU.getRefLengthUnit());
		registeredProduct.setNote(tempMaterialSKU.getNote());
		registeredProduct.setHeight(tempMaterialSKU.getHeight());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convRegisteredProductToUI(RegisteredProduct registeredProduct,
			RegisteredProductUIModel registeredProductUIModel)
			throws ServiceEntityInstallationException {
		convRegisteredProductToUI(registeredProduct, registeredProductUIModel,
				null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convRegisteredProductToUI(RegisteredProduct registeredProduct,
										  RegisteredProductUIModel registeredProductUIModel,
										  LogonInfo logonInfo) throws ServiceEntityInstallationException {
		convRegisteredProductToUI(registeredProduct, registeredProductUIModel, null, logonInfo);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 */
	public void convRegisteredProductToUI(RegisteredProduct registeredProduct,
			RegisteredProductUIModel registeredProductUIModel, List<ServiceEntityNode> standardMaterialUnitList,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		if (registeredProduct != null) {
			DocFlowProxy.convServiceEntityNodeToUIModel(registeredProduct, registeredProductUIModel);
			registeredProductUIModel.setLength(registeredProduct.getLength());
			registeredProductUIModel.setRefSupplierUUID(registeredProduct
					.getRefSupplierUUID());
			registeredProductUIModel.setPackageMaterialType(registeredProduct
					.getPackageMaterialType());
			registeredProductUIModel.setTraceStatus(registeredProduct
					.getTraceStatus());
			if (logonInfo != null) {
				Map<Integer, String> traceStatusMap = this
						.initTraceStatusMap(logonInfo.getLanguageCode());
				registeredProductUIModel.setTraceStatusValue(traceStatusMap
						.get(registeredProduct.getTraceStatus()));
			}
			registeredProductUIModel.setProductionBatchNumber(registeredProduct
					.getProductionBatchNumber());
			if (registeredProduct.getValidToDate() != null) {
				registeredProductUIModel
						.setValidToDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(registeredProduct.getValidToDate()));
			}
			registeredProductUIModel.setBarcode(registeredProduct.getBarcode());
			registeredProductUIModel.setRetailPrice(registeredProduct
					.getRetailPrice());
			registeredProductUIModel.setRefVolumeUnit(registeredProduct
					.getRefVolumeUnit());
			registeredProductUIModel.setUnitCost(registeredProduct
					.getUnitCost());
			registeredProductUIModel.setVolume(registeredProduct.getVolume());
			registeredProductUIModel.setMainMaterialUnit(registeredProduct
					.getMainMaterialUnit());
			registeredProductUIModel.setCargoType(registeredProduct
					.getCargoType());
			registeredProductUIModel.setPurchasePrice(registeredProduct
					.getPurchasePrice());
			registeredProductUIModel.setId(registeredProduct.getId());
			registeredProductUIModel.setGrossWeight(registeredProduct
					.getGrossWeight());
			registeredProductUIModel.setRefWeightUnit(registeredProduct
					.getRefWeightUnit());
			registeredProductUIModel.setNetWeight(registeredProduct
					.getNetWeight());
			registeredProductUIModel.setSupplyType(registeredProduct
					.getSupplyType());
			registeredProductUIModel.setSupplyType(registeredProduct
					.getSupplyType());
			if(logonInfo != null){
				Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager
						.initSupplyTypeMap(logonInfo.getLanguageCode());
				registeredProductUIModel.setSupplyTypeValue(supplyTypeMap
						.get(registeredProduct.getSupplyType()));
			}
			registeredProductUIModel.setPackageStandard(registeredProduct
					.getPackageStandard());
			if (registeredProduct.getReferenceDate() != null) {
				registeredProductUIModel
						.setReferenceDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(registeredProduct.getReferenceDate()));
			}
			if(!ServiceCollectionsHelper.checkNullList(standardMaterialUnitList)){
				try {
					materialManager.setStandardUnitValueToUIModel(standardMaterialUnitList, registeredProduct, registeredProductUIModel);
				} catch (NoSuchFieldException | IllegalAccessException e) {
					logger.error(ServiceEntityStringHelper
							.genDefaultErrorMessage(e, ""));
				}
			}
			registeredProductUIModel.setSerialId(registeredProduct
					.getSerialId());
			if (registeredProduct.getProductionDate() != null) {
				registeredProductUIModel
						.setProductionDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(registeredProduct.getProductionDate()));
			}
			registeredProductUIModel.setWidth(registeredProduct.getWidth());
			registeredProductUIModel.setRefMaterialSKUUUID(registeredProduct
					.getRefMaterialSKUUUID());
			registeredProductUIModel.setQualityInspectFlag(registeredProduct
					.getQualityInspectFlag());
			registeredProductUIModel.setProductionPlace(registeredProduct
					.getProductionPlace());
			registeredProductUIModel.setRefLengthUnit(registeredProduct
					.getRefLengthUnit());
			registeredProductUIModel.setNote(registeredProduct.getNote());
			registeredProductUIModel.setHeight(registeredProduct.getHeight());
			if (registeredProduct.getValidFromDate() != null) {
				registeredProductUIModel
						.setValidFromDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(registeredProduct.getValidFromDate()));
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:registeredProduct
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToRegisteredProduct(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProduct rawEntity) {
		DocFlowProxy.convUIToServiceEntityNode(registeredProductUIModel, rawEntity);
		rawEntity.setLength(registeredProductUIModel.getLength());
		if(!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefSupplierUUID())){
			rawEntity.setRefSupplierUUID(registeredProductUIModel
					.getRefSupplierUUID());
		}
		rawEntity.setPackageMaterialType(registeredProductUIModel
				.getPackageMaterialType());
		rawEntity.setTraceStatus(registeredProductUIModel.getTraceStatus());
		rawEntity.setProductionBatchNumber(registeredProductUIModel
				.getProductionBatchNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getValidToDate())) {
			try {
				rawEntity.setValidToDate(DefaultDateFormatConstant.DATE_FORMAT
						.parse(registeredProductUIModel.getValidToDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setClient(registeredProductUIModel.getClient());
		rawEntity.setBarcode(registeredProductUIModel.getBarcode());
		rawEntity.setRetailPrice(registeredProductUIModel.getRetailPrice());
		rawEntity.setRefVolumeUnit(registeredProductUIModel.getRefVolumeUnit());
		rawEntity.setUnitCost(registeredProductUIModel.getUnitCost());
		rawEntity.setVolume(registeredProductUIModel.getVolume());
		rawEntity.setMainMaterialUnit(registeredProductUIModel
				.getMainMaterialUnit());
		rawEntity.setCargoType(registeredProductUIModel.getCargoType());
		rawEntity.setPurchasePrice(registeredProductUIModel.getPurchasePrice());
		rawEntity.setGrossWeight(registeredProductUIModel.getGrossWeight());
		rawEntity.setRefWeightUnit(registeredProductUIModel.getRefWeightUnit());
		rawEntity.setNetWeight(registeredProductUIModel.getNetWeight());
		rawEntity.setSupplyType(registeredProductUIModel.getSupplyType());
		if (registeredProductUIModel.getQualityInspectFlag() != 0) {
			rawEntity.setQualityInspectFlag(registeredProductUIModel
					.getQualityInspectFlag());
		}
		rawEntity.setPackageStandard(registeredProductUIModel
				.getPackageStandard());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getReferenceDate())) {
			try {
				rawEntity
						.setReferenceDate(DefaultDateFormatConstant.DATE_FORMAT
								.parse(registeredProductUIModel
										.getReferenceDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setSerialId(registeredProductUIModel.getSerialId());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getProductionDate())) {
			try {
				rawEntity
						.setProductionDate(DefaultDateFormatConstant.DATE_FORMAT
								.parse(registeredProductUIModel
										.getProductionDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setWidth(registeredProductUIModel.getWidth());
		rawEntity.setRefMaterialSKUUUID(registeredProductUIModel
				.getRefMaterialSKUUUID());
		rawEntity.setProductionPlace(registeredProductUIModel
				.getProductionPlace());
		rawEntity.setRefLengthUnit(registeredProductUIModel.getRefLengthUnit());
		rawEntity.setNote(registeredProductUIModel.getNote());
		rawEntity.setHeight(registeredProductUIModel.getHeight());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getValidFromDate())) {
			try {
				rawEntity
						.setValidFromDate(DefaultDateFormatConstant.DATE_FORMAT
								.parse(registeredProductUIModel
										.getValidFromDate()));
			} catch (ParseException e) {
				// do nothing
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToPurchaseOrgParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefPurchaseOrgUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel
					.getRefPurchaseOrgUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getPurchaseOrganizationName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getPurchaseOrganizationId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getPurchaseOrganizationTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getPurchaseOrganizationAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getPurchaseOrganizationBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getPurchaseOrganizationEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getPurchaseOrganizationTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getPurchaseOrganizationTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getPurchaseOrgContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getPurchaseOrgContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getPurchaseOrgContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getPurchaseOrgContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getPurchaseOrgContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getPurchaseOrgContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getPurchaseOrgContactWeixin());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToSalesOrgParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefSalesOrgUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel.getRefSalesOrgUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getSalesOrganizationName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getSalesOrganizationId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getSalesOrganizationTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getSalesOrganizationAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getSalesOrganizationBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getSalesOrganizationEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getSalesOrganizationTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getSalesOrganizationTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getSalesOrgContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getSalesOrgContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getSalesOrgContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getSalesOrgContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getSalesOrgContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getSalesOrgContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getSalesOrgContactWeixin());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToCustomerParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefCustomerUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel.getRefCustomerUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getCorporateCustomerName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getCorporateCustomerId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getCorporateCustomerTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getCorporateCustomerAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getCorporateCustomerBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getCorporateCustomerEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getCorporateCustomerTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getCorporateCustomerTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getCustomerContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getCustomerContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getCustomerContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getCustomerContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getCustomerContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getCustomerContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getCustomerContactWeixin());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToSupplierParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefSupplierUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel.getRefSupplierUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getCorporateSupplierName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getCorporateSupplierId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getCorporateSupplierTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getCorporateSupplierAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getCorporateSupplierBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getCorporateSupplierEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getCorporateSupplierTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getCorporateSupplierTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getSupplierContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getSupplierContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getSupplierContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getSupplierContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getSupplierContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getSupplierContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getSupplierContactWeixin());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToProductOrgParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefProductOrgUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel.getRefProductOrgUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getProductOrganizationName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getProductOrganizationId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getProductOrganizationTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getProductOrganizationAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getProductOrganizationBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getProductOrganizationEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getProductOrganizationTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getProductOrganizationTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getSupplierContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getProductOrgContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getProductOrgContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getProductOrgContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getProductOrgContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getProductOrgContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getProductOrgContactWeixin());
	}

	/**
	 * [Internal method] Convert from UI model to se
	 * model:registeredProductInvolveParty
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convUIToSupportOrgParty(
			RegisteredProductUIModel registeredProductUIModel,
			RegisteredProductInvolveParty rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getRefSupportOrgUUID())) {
			rawEntity.setRefUUID(registeredProductUIModel.getRefSupportOrgUUID());
		}
		rawEntity.setRefPartyName(registeredProductUIModel
				.getSupportOrganizationName());
		rawEntity.setRefPartyId(registeredProductUIModel
				.getSupportOrganizationId());
		rawEntity.setRefPartyTaxNumber(registeredProductUIModel
				.getSupportOrganizationTaxNumber());
		rawEntity.setRefPartyAddress(registeredProductUIModel
				.getSupportOrganizationAddress());
		rawEntity.setRefPartyBankAccount(registeredProductUIModel
				.getSupportOrganizationBankAccount());
		rawEntity.setRefPartyEmail(registeredProductUIModel
				.getSupportOrganizationEmail());
		rawEntity.setRefPartyFax(registeredProductUIModel
				.getSupportOrganizationTaxNumber());
		rawEntity.setRefPartyTelephone(registeredProductUIModel
				.getSupportOrganizationTaxNumber());
		if (!ServiceEntityStringHelper.checkNullString(registeredProductUIModel
				.getSupportOrgContactUUID())) {
			rawEntity.setRefPartyContactUUID(registeredProductUIModel
					.getSupportOrgContactUUID());
		}
		rawEntity.setRefPartyContactId(registeredProductUIModel
				.getSupportOrgContactId());
		rawEntity.setRefPartyContactName(registeredProductUIModel
				.getSupportOrgContactName());
		rawEntity.setRefPartyContactEmail(registeredProductUIModel
				.getSupportOrgContactEmail());
		rawEntity.setRefPartyContactMobile(registeredProductUIModel
				.getSupportOrgContactMobile());
		rawEntity.setRefPartyContactWeixin(registeredProductUIModel
				.getSupportOrgContactWeixin());
	}

	public List<ServiceEntityNode> searchInternal(
			RegisteredProductSearchModel searchModel, String client)
			throws SearchConfigureException, ServiceEntityConfigureException,
			NodeNotFoundException, ServiceEntityInstallationException {
		List<BSearchNodeComConfigure> searchNodeConfigList = new ArrayList<BSearchNodeComConfigure>();
		// Search node:[purchaseOrgParty]
		BSearchNodeComConfigure searchNodeConfig0 = new BSearchNodeComConfigure();
		searchNodeConfig0.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig0.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig0
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_PurchaseOrgParty);
		searchNodeConfig0.setStartNodeFlag(false);
		searchNodeConfig0
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		List<SearchConfigPreCondition> purchaseOrgPartyConditions = new ArrayList<>();
		SearchConfigPreCondition purchaseOrgCondition1 = new SearchConfigPreCondition();
		purchaseOrgCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		purchaseOrgCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_PURORG);
		purchaseOrgPartyConditions.add(purchaseOrgCondition1);
		searchNodeConfig0.setPreConditions(purchaseOrgPartyConditions);
		searchNodeConfig0.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig0);
		// Search node:[registeredProduct]
		BSearchNodeComConfigure searchNodeConfig1 = new BSearchNodeComConfigure();
		searchNodeConfig1.setSeName(RegisteredProduct.SENAME);
		searchNodeConfig1.setNodeName(RegisteredProduct.NODENAME);
		searchNodeConfig1.setNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfig1.setStartNodeFlag(true);
		searchNodeConfigList.add(searchNodeConfig1);

		// Search node:[purchaseOrgParty]
		BSearchNodeComConfigure searchNodeConfig1a = new BSearchNodeComConfigure();
		searchNodeConfig1a.setSeName(MaterialStockKeepUnit.SENAME);
		searchNodeConfig1a.setNodeName(MaterialStockKeepUnit.NODENAME);
		searchNodeConfig1a.setNodeInstID(MaterialStockKeepUnit.SENAME);
		searchNodeConfig1a.setStartNodeFlag(false);
		searchNodeConfig1a
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig1a.setMapBaseFieldName("refMaterialSKUUUID");
		searchNodeConfig1a.setMapSourceFieldName("uuid");
		searchNodeConfig1a.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig1a);
		// Search node:[registeredProductExtendProperty]
		BSearchNodeComConfigure searchNodeConfig2 = new BSearchNodeComConfigure();
		searchNodeConfig2.setSeName(RegisteredProductExtendProperty.SENAME);
		searchNodeConfig2.setNodeName(RegisteredProductExtendProperty.NODENAME);
		searchNodeConfig2
				.setNodeInstID(RegisteredProductExtendProperty.NODENAME);
		searchNodeConfig2.setStartNodeFlag(false);
		searchNodeConfig2
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig2.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig2);

		// Search node:[salesOrgParty]
		BSearchNodeComConfigure searchNodeConfig3 = new BSearchNodeComConfigure();
		searchNodeConfig3.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig3.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig3
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SalesOrgParty);
		searchNodeConfig3.setStartNodeFlag(false);
		searchNodeConfig3
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		List<SearchConfigPreCondition> salesOrgPartyConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition salesOrgPartyCondition1 = new SearchConfigPreCondition();
		salesOrgPartyCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		salesOrgPartyCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_SALESORG);
		salesOrgPartyConditions.add(salesOrgPartyCondition1);
		searchNodeConfig3.setPreConditions(salesOrgPartyConditions);
		searchNodeConfig3.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig3);

		// Search node:[customerParty]
		BSearchNodeComConfigure searchNodeConfig4 = new BSearchNodeComConfigure();
		searchNodeConfig4.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig4.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig4
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_CustomerParty);
		searchNodeConfig4.setStartNodeFlag(false);
		searchNodeConfig4
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig4.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig4);
		// Search node:[supplierParty]
		BSearchNodeComConfigure searchNodeConfig5 = new BSearchNodeComConfigure();
		searchNodeConfig5.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig5.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig5
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SupplierParty);
		searchNodeConfig5.setStartNodeFlag(false);
		List<SearchConfigPreCondition> supplerPartyConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition supplerPartyCondition1 = new SearchConfigPreCondition();
		supplerPartyCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		supplerPartyCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_SUPPLIER);
		supplerPartyConditions.add(supplerPartyCondition1);
		searchNodeConfig5.setPreConditions(supplerPartyConditions);
		searchNodeConfig5
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		searchNodeConfig5.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig5);
		// Search node:[purchaseOrgContact]
		BSearchNodeComConfigure searchNodeConfig6 = new BSearchNodeComConfigure();
		searchNodeConfig6.setSeName(LogonUser.SENAME);
		searchNodeConfig6.setNodeName(LogonUser.NODENAME);
		searchNodeConfig6
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_PurchaseOrgContact);
		searchNodeConfig6.setStartNodeFlag(false);
		searchNodeConfig6
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig6.setMapBaseFieldName("refPartyContactUUID");
		searchNodeConfig6
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig6
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_PurchaseOrgParty);
		searchNodeConfigList.add(searchNodeConfig6);
		// Search node:[salesOrganization]
		BSearchNodeComConfigure searchNodeConfig7 = new BSearchNodeComConfigure();
		searchNodeConfig7.setSeName(Organization.SENAME);
		searchNodeConfig7.setNodeName(Organization.NODENAME);
		searchNodeConfig7
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SalesOrganization);
		searchNodeConfig7.setStartNodeFlag(false);
		searchNodeConfig7
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig7
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_SalesOrgParty);
		searchNodeConfigList.add(searchNodeConfig7);
		// Search node:[purchaseOrganization]
		BSearchNodeComConfigure searchNodeConfig8 = new BSearchNodeComConfigure();
		searchNodeConfig8.setSeName(Organization.SENAME);
		searchNodeConfig8.setNodeName(Organization.NODENAME);
		searchNodeConfig8
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_PurchaseOrganization);
		searchNodeConfig8.setStartNodeFlag(false);
		searchNodeConfig8
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig8
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_PurchaseOrgParty);
		searchNodeConfigList.add(searchNodeConfig8);
		// Search node:[corporateSupplier]
		BSearchNodeComConfigure searchNodeConfig9 = new BSearchNodeComConfigure();
		searchNodeConfig9.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig9.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig9
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_CorporateSupplier);
		searchNodeConfig9.setStartNodeFlag(false);
		searchNodeConfig9
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig9
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_SupplierParty);

		List<SearchConfigPreCondition> supplierPartyConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition supplierPartyCondition1 = new SearchConfigPreCondition();
		supplierPartyCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		supplierPartyCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_SUPPLIER);
		purchaseOrgPartyConditions.add(supplierPartyCondition1);
		searchNodeConfig9.setPreConditions(supplierPartyConditions);
		searchNodeConfigList.add(searchNodeConfig9);
		// Search node:[salesOrgContact]
		BSearchNodeComConfigure searchNodeConfig10 = new BSearchNodeComConfigure();
		searchNodeConfig10.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig10.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig10
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SalesOrgContact);
		searchNodeConfig10.setStartNodeFlag(false);
		searchNodeConfig10
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig10.setMapBaseFieldName("refPartyContactUUID");
		searchNodeConfig10
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig10
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_SalesOrgParty);
		searchNodeConfigList.add(searchNodeConfig10);
		// Search node:[corporateCustomer]
		BSearchNodeComConfigure searchNodeConfig11 = new BSearchNodeComConfigure();
		searchNodeConfig11.setSeName(CorporateCustomer.SENAME);
		searchNodeConfig11.setNodeName(CorporateCustomer.NODENAME);
		searchNodeConfig11
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_CorporateCustomer);
		searchNodeConfig11.setStartNodeFlag(false);
		searchNodeConfig11
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE);
		searchNodeConfig11
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_CustomerParty);
		searchNodeConfigList.add(searchNodeConfig11);
		// Search node:[customerContact]
		BSearchNodeComConfigure searchNodeConfig12 = new BSearchNodeComConfigure();
		searchNodeConfig12.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig12.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig12
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_CustomerContact);
		searchNodeConfig12.setStartNodeFlag(false);
		searchNodeConfig12
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig12.setMapBaseFieldName("refPartyContactUUID");
		searchNodeConfig12
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig12
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_CustomerParty);
		searchNodeConfigList.add(searchNodeConfig12);
		// Search node:[supplierContact]
		BSearchNodeComConfigure searchNodeConfig13 = new BSearchNodeComConfigure();
		searchNodeConfig13.setSeName(IndividualCustomer.SENAME);
		searchNodeConfig13.setNodeName(IndividualCustomer.NODENAME);
		searchNodeConfig13
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SupplierContact);
		searchNodeConfig13.setStartNodeFlag(false);
		searchNodeConfig13
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS);
		searchNodeConfig13.setMapBaseFieldName("refPartyContactUUID");
		searchNodeConfig13
				.setMapSourceFieldName(IServiceEntityNodeFieldConstant.UUID);
		searchNodeConfig13
				.setBaseNodeInstID(RegisteredProductSearchModel.NODE_InstId_SupplierParty);
		searchNodeConfigList.add(searchNodeConfig13);

		// Search node:[productOrgParty]
		BSearchNodeComConfigure searchNodeConfig14 = new BSearchNodeComConfigure();
		searchNodeConfig14.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig14.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig14
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_ProductOrgParty);
		searchNodeConfig14.setStartNodeFlag(false);
		searchNodeConfig14
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		List<SearchConfigPreCondition> productOrgPartyConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition productPartyCondition1 = new SearchConfigPreCondition();
		productPartyCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		productPartyCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_PRODORG);
		productOrgPartyConditions.add(productPartyCondition1);
		searchNodeConfig14.setPreConditions(productOrgPartyConditions);
		searchNodeConfig14.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig14);

		// Search node:[productOrgParty]
		BSearchNodeComConfigure searchNodeConfig15 = new BSearchNodeComConfigure();
		searchNodeConfig15.setSeName(RegisteredProductInvolveParty.SENAME);
		searchNodeConfig15.setNodeName(RegisteredProductInvolveParty.NODENAME);
		searchNodeConfig15
				.setNodeInstID(RegisteredProductSearchModel.NODE_InstId_SupportOrgParty);
		searchNodeConfig15.setStartNodeFlag(false);
		searchNodeConfig15
				.setToBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT);
		List<SearchConfigPreCondition> supportOrgPartyConditions = new ArrayList<SearchConfigPreCondition>();
		SearchConfigPreCondition supportPartyCondition1 = new SearchConfigPreCondition();
		supportPartyCondition1
				.setFieldName(RegisteredProductInvolveParty.FIELD_PARTYROLE);
		supportPartyCondition1
				.setFieldValue(RegisteredProductInvolveParty.PARTY_ROLE_SUPPORTORG);
		supportOrgPartyConditions.add(supportPartyCondition1);
		searchNodeConfig15.setPreConditions(supportOrgPartyConditions);
		searchNodeConfig15.setBaseNodeInstID(RegisteredProduct.SENAME);
		searchNodeConfigList.add(searchNodeConfig15);
		List<ServiceEntityNode> resultList = bsearchService.doSearch(
				searchModel, searchNodeConfigList, client, true);
		return resultList;
	}

	@PostConstruct
	public void setServiceEntityDAO() {
		super.setServiceEntityDAO(new JpaServiceEntityDAO(entityManager, registeredProductDAO));
	}

	@PostConstruct
	public void setSeConfigureProxy() {
		super.setSeConfigureProxy(registeredProductConfigureProxy);
	}
	
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			RegisteredProductUIModel registeredProductUIModel) {
		convMaterialStockKeepUnitToUI(materialStockKeepUnit, registeredProductUIModel, null);
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convMaterialStockKeepUnitToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			RegisteredProductUIModel registeredProductUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			registeredProductUIModel.setRefMaterialSKUId(materialStockKeepUnit
					.getId());
			registeredProductUIModel
					.setRefMaterialSKUName(materialStockKeepUnit.getName());
			registeredProductUIModel.setPackageStandard(materialStockKeepUnit
					.getPackageStandard());
			registeredProductUIModel.setMainMaterialUnit(materialStockKeepUnit
					.getMainMaterialUnit());
			registeredProductUIModel.setSupplyType(materialStockKeepUnit
					.getSupplyType());
			registeredProductUIModel.setCargoType(materialStockKeepUnit
					.getCargoType());
			try {
				Map<Integer, String> qualityInspectFlagMap = materialStockKeepUnitManager
						.initMaterialQualityInspectFlagMap(logonInfo.getLanguageCode());
				registeredProductUIModel
						.setQualityInspectValue(qualityInspectFlagMap
								.get(materialStockKeepUnit
										.getQualityInspectFlag()));
			} catch (ServiceEntityInstallationException e) {
				// just continue
			}
			// Important:quality inspect flag only for template material SKU
			registeredProductUIModel
					.setQualityInspectFlag(materialStockKeepUnit
							.getQualityInspectFlag());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convPurchaseOrgPartyToUI(
			RegisteredProductInvolveParty purchaseOrgParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (purchaseOrgParty != null) {
			registeredProductUIModel.setRefPurchaseOrgUUID(purchaseOrgParty
					.getRefUUID());
			registeredProductUIModel
					.setPurchaseOrganizationName(purchaseOrgParty
							.getRefPartyName());
			registeredProductUIModel.setPurchaseOrganizationId(purchaseOrgParty
					.getRefPartyId());
			registeredProductUIModel
					.setPurchaseOrganizationTaxNumber(purchaseOrgParty
							.getRefPartyTaxNumber());
			registeredProductUIModel
					.setPurchaseOrganizationAddress(purchaseOrgParty
							.getRefPartyAddress());
			registeredProductUIModel
					.setPurchaseOrganizationBankAccount(purchaseOrgParty
							.getRefPartyBankAccount());
			registeredProductUIModel
					.setPurchaseOrganizationEmail(purchaseOrgParty
							.getRefPartyEmail());
			registeredProductUIModel
					.setPurchaseOrganizationFax(purchaseOrgParty
							.getRefPartyFax());
			registeredProductUIModel
					.setPurchaseOrganizationTelephone(purchaseOrgParty
							.getRefPartyTelephone());
			registeredProductUIModel.setPurchaseOrgContactUUID(purchaseOrgParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setPurchaseOrgContactId(purchaseOrgParty
					.getRefPartyContactId());
			registeredProductUIModel.setPurchaseOrgContactName(purchaseOrgParty
					.getRefPartyContactName());
			registeredProductUIModel
					.setPurchaseOrgContactEmail(purchaseOrgParty
							.getRefPartyContactEmail());
			registeredProductUIModel
					.setPurchaseOrgContactMobile(purchaseOrgParty
							.getRefPartyContactMobile());
			registeredProductUIModel
					.setPurchaseOrgContactWeixin(purchaseOrgParty
							.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPurchaseOrganizationToUI(Organization purchaseOrganization,
			RegisteredProductUIModel registeredProductUIModel) {
		if (purchaseOrganization != null) {
			registeredProductUIModel
					.setPurchaseOrganizationName(purchaseOrganization.getName());
			registeredProductUIModel
					.setPurchaseOrganizationId(purchaseOrganization.getId());
			registeredProductUIModel
					.setPurchaseOrganizationTaxNumber(purchaseOrganization
							.getTaxNumber());
			registeredProductUIModel
					.setPurchaseOrganizationAddress(purchaseOrganization
							.getAddress());
			registeredProductUIModel
					.setPurchaseOrganizationBankAccount(purchaseOrganization
							.getBankAccount());
			registeredProductUIModel
					.setPurchaseOrganizationEmail(purchaseOrganization
							.getEmail());
			registeredProductUIModel
					.setPurchaseOrganizationFax(purchaseOrganization.getFax());
			registeredProductUIModel
					.setPurchaseOrganizationTelephone(purchaseOrganization
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convPurchaseOrgContactToUI(Employee purchaseOrgContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (purchaseOrgContact != null) {
			registeredProductUIModel.setPurchaseOrgContactId(purchaseOrgContact
					.getId());
			registeredProductUIModel
					.setPurchaseOrgContactName(purchaseOrgContact.getName());
			registeredProductUIModel
					.setPurchaseOrgContactEmail(purchaseOrgContact.getEmail());
			registeredProductUIModel
					.setPurchaseOrgContactMobile(purchaseOrgContact.getMobile());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSalesOrgPartyToUI(
			RegisteredProductInvolveParty salesOrgParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (salesOrgParty != null) {
			registeredProductUIModel.setRefSalesOrgUUID(salesOrgParty
					.getRefUUID());

			registeredProductUIModel.setSalesOrganizationName(salesOrgParty
					.getRefPartyName());
			registeredProductUIModel.setSalesOrganizationId(salesOrgParty
					.getRefPartyId());
			registeredProductUIModel
					.setSalesOrganizationTaxNumber(salesOrgParty
							.getRefPartyTaxNumber());
			registeredProductUIModel.setSalesOrganizationAddress(salesOrgParty
					.getRefPartyAddress());
			registeredProductUIModel
					.setSalesOrganizationBankAccount(salesOrgParty
							.getRefPartyBankAccount());
			registeredProductUIModel.setSalesOrganizationEmail(salesOrgParty
					.getRefPartyEmail());
			registeredProductUIModel.setSalesOrganizationFax(salesOrgParty
					.getRefPartyFax());
			registeredProductUIModel
					.setSalesOrganizationTelephone(salesOrgParty
							.getRefPartyTelephone());
			registeredProductUIModel.setSalesOrgContactUUID(salesOrgParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setSalesOrgContactId(salesOrgParty
					.getRefPartyContactId());
			registeredProductUIModel.setSalesOrgContactName(salesOrgParty
					.getRefPartyContactName());
			registeredProductUIModel.setSalesOrgContactEmail(salesOrgParty
					.getRefPartyContactEmail());
			registeredProductUIModel.setSalesOrgContactMobile(salesOrgParty
					.getRefPartyContactMobile());
			registeredProductUIModel.setSalesOrgContactWeixin(salesOrgParty
					.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSalesOrganizationToUI(Organization salesOrganization,
			RegisteredProductUIModel registeredProductUIModel) {
		if (salesOrganization != null) {
			registeredProductUIModel.setSalesOrganizationName(salesOrganization
					.getName());
			registeredProductUIModel.setSalesOrganizationId(salesOrganization
					.getId());
			registeredProductUIModel
					.setSalesOrganizationTaxNumber(salesOrganization
							.getTaxNumber());
			registeredProductUIModel
					.setSalesOrganizationAddress(salesOrganization.getAddress());
			registeredProductUIModel
					.setSalesOrganizationBankAccount(salesOrganization
							.getBankAccount());
			registeredProductUIModel
					.setSalesOrganizationEmail(salesOrganization.getEmail());
			registeredProductUIModel.setSalesOrganizationFax(salesOrganization
					.getFax());
			registeredProductUIModel
					.setSalesOrganizationTelephone(salesOrganization
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSalesOrgContactToUI(Employee salesOrgContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (salesOrgContact != null) {
			registeredProductUIModel.setSalesOrgContactId(salesOrgContact
					.getId());
			registeredProductUIModel.setSalesOrgContactName(salesOrgContact
					.getName());
			registeredProductUIModel.setSalesOrgContactEmail(salesOrgContact
					.getEmail());
			registeredProductUIModel.setSalesOrgContactMobile(salesOrgContact
					.getMobile());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convProductOrgPartyToUI(
			RegisteredProductInvolveParty productOrgParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (productOrgParty != null) {
			registeredProductUIModel.setRefProductOrgUUID(productOrgParty
					.getRefUUID());
			registeredProductUIModel.setProductOrganizationName(productOrgParty
					.getRefPartyName());
			registeredProductUIModel.setProductOrganizationId(productOrgParty
					.getRefPartyId());
			registeredProductUIModel
					.setProductOrganizationTaxNumber(productOrgParty
							.getRefPartyTaxNumber());
			registeredProductUIModel
					.setProductOrganizationAddress(productOrgParty
							.getRefPartyAddress());
			registeredProductUIModel
					.setProductOrganizationBankAccount(productOrgParty
							.getRefPartyBankAccount());
			registeredProductUIModel
					.setProductOrganizationEmail(productOrgParty
							.getRefPartyEmail());
			registeredProductUIModel.setProductOrganizationFax(productOrgParty
					.getRefPartyFax());
			registeredProductUIModel
					.setProductOrganizationTelephone(productOrgParty
							.getRefPartyTelephone());
			registeredProductUIModel.setProductOrgContactUUID(productOrgParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setProductOrgContactId(productOrgParty
					.getRefPartyContactId());
			registeredProductUIModel.setProductOrgContactName(productOrgParty
					.getRefPartyContactName());
			registeredProductUIModel.setProductOrgContactEmail(productOrgParty
					.getRefPartyContactEmail());
			registeredProductUIModel.setProductOrgContactMobile(productOrgParty
					.getRefPartyContactMobile());
			registeredProductUIModel.setProductOrgContactWeixin(productOrgParty
					.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProductOrganizationToUI(Organization productOrganization,
			RegisteredProductUIModel registeredProductUIModel) {
		if (productOrganization != null) {
			registeredProductUIModel
					.setSalesOrganizationName(productOrganization.getName());
			registeredProductUIModel
					.setProductOrganizationId(productOrganization.getId());
			registeredProductUIModel
					.setProductOrganizationTaxNumber(productOrganization
							.getTaxNumber());
			registeredProductUIModel
					.setProductOrganizationAddress(productOrganization
							.getAddress());
			registeredProductUIModel
					.setProductOrganizationBankAccount(productOrganization
							.getBankAccount());
			registeredProductUIModel
					.setProductOrganizationEmail(productOrganization.getEmail());
			registeredProductUIModel
					.setProductOrganizationFax(productOrganization.getFax());
			registeredProductUIModel
					.setProductOrganizationTelephone(productOrganization
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convProductOrgContactToUI(Employee salesOrgContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (salesOrgContact != null) {
			registeredProductUIModel.setProductOrgContactId(salesOrgContact
					.getId());
			registeredProductUIModel.setProductOrgContactName(salesOrgContact
					.getName());
			registeredProductUIModel.setProductOrgContactEmail(salesOrgContact
					.getEmail());
			registeredProductUIModel.setProductOrgContactMobile(salesOrgContact
					.getMobile());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convSupportOrgPartyToUI(
			RegisteredProductInvolveParty productOrgParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (productOrgParty != null) {
			registeredProductUIModel.setRefSupportOrgUUID(productOrgParty
					.getRefUUID());
			registeredProductUIModel.setSupportOrganizationName(productOrgParty
					.getRefPartyName());
			registeredProductUIModel.setSupportOrganizationId(productOrgParty
					.getRefPartyId());
			registeredProductUIModel
					.setSupportOrganizationTaxNumber(productOrgParty
							.getRefPartyTaxNumber());
			registeredProductUIModel
					.setSupportOrganizationAddress(productOrgParty
							.getRefPartyAddress());
			registeredProductUIModel
					.setSupportOrganizationBankAccount(productOrgParty
							.getRefPartyBankAccount());
			registeredProductUIModel
					.setSupportOrganizationEmail(productOrgParty
							.getRefPartyEmail());
			registeredProductUIModel.setSupportOrganizationFax(productOrgParty
					.getRefPartyFax());
			registeredProductUIModel
					.setSupportOrganizationTelephone(productOrgParty
							.getRefPartyTelephone());
			registeredProductUIModel.setSupportOrgContactUUID(productOrgParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setSupportOrgContactId(productOrgParty
					.getRefPartyContactId());
			registeredProductUIModel.setSupportOrgContactName(productOrgParty
					.getRefPartyContactName());
			registeredProductUIModel.setSupportOrgContactEmail(productOrgParty
					.getRefPartyContactEmail());
			registeredProductUIModel.setSupportOrgContactMobile(productOrgParty
					.getRefPartyContactMobile());
			registeredProductUIModel.setSupportOrgContactWeixin(productOrgParty
					.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSupportOrganizationToUI(Organization productOrganization,
			RegisteredProductUIModel registeredProductUIModel) {
		if (productOrganization != null) {
			registeredProductUIModel
					.setSalesOrganizationName(productOrganization.getName());
			registeredProductUIModel
					.setSupportOrganizationId(productOrganization.getId());
			registeredProductUIModel
					.setSupportOrganizationTaxNumber(productOrganization
							.getTaxNumber());
			registeredProductUIModel
					.setSupportOrganizationAddress(productOrganization
							.getAddress());
			registeredProductUIModel
					.setSupportOrganizationBankAccount(productOrganization
							.getBankAccount());
			registeredProductUIModel
					.setSupportOrganizationEmail(productOrganization.getEmail());
			registeredProductUIModel
					.setSupportOrganizationFax(productOrganization.getFax());
			registeredProductUIModel
					.setSupportOrganizationTelephone(productOrganization
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convSupportOrgContactToUI(Employee salesOrgContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (salesOrgContact != null) {
			registeredProductUIModel.setSupportOrgContactId(salesOrgContact
					.getId());
			registeredProductUIModel.setSupportOrgContactName(salesOrgContact
					.getName());
			registeredProductUIModel.setSupportOrgContactEmail(salesOrgContact
					.getEmail());
			registeredProductUIModel.setSupportOrgContactMobile(salesOrgContact
					.getMobile());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convSupplierPartyToUI(
			RegisteredProductInvolveParty supplierParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (supplierParty != null) {
			registeredProductUIModel.setRefSupplierUUID(supplierParty
					.getRefUUID());
			registeredProductUIModel.setCorporateSupplierName(supplierParty
					.getRefPartyName());
			registeredProductUIModel.setCorporateSupplierId(supplierParty
					.getRefPartyId());
			registeredProductUIModel
					.setCorporateSupplierTaxNumber(supplierParty
							.getRefPartyTaxNumber());
			registeredProductUIModel.setCorporateSupplierAddress(supplierParty
					.getRefPartyAddress());
			registeredProductUIModel
					.setCorporateSupplierBankAccount(supplierParty
							.getRefPartyBankAccount());
			registeredProductUIModel.setCorporateSupplierEmail(supplierParty
					.getRefPartyEmail());
			registeredProductUIModel.setCorporateSupplierFax(supplierParty
					.getRefPartyFax());
			registeredProductUIModel
					.setCorporateSupplierTelephone(supplierParty
							.getRefPartyTelephone());
			registeredProductUIModel.setSupplierContactUUID(supplierParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setSupplierContactId(supplierParty
					.getRefPartyContactId());
			registeredProductUIModel.setSupplierContactName(supplierParty
					.getRefPartyContactName());
			registeredProductUIModel.setSupplierContactEmail(supplierParty
					.getRefPartyContactEmail());
			registeredProductUIModel.setSupplierContactMobile(supplierParty
					.getRefPartyContactMobile());
			registeredProductUIModel.setSupplierContactWeixin(supplierParty
					.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convCorporateSupplierToUI(CorporateCustomer corporateSupplier,
			RegisteredProductUIModel registeredProductUIModel) {
		if (corporateSupplier != null) {
			registeredProductUIModel.setCorporateSupplierName(corporateSupplier
					.getName());
			registeredProductUIModel.setCorporateSupplierId(corporateSupplier
					.getId());
			registeredProductUIModel
					.setCorporateSupplierTaxNumber(corporateSupplier
							.getTaxNumber());
			registeredProductUIModel
					.setCorporateSupplierAddress(corporateSupplier.getAddress());
			registeredProductUIModel
					.setCorporateSupplierBankAccount(corporateSupplier
							.getBankAccount());
			registeredProductUIModel
					.setCorporateSupplierEmail(corporateSupplier.getEmail());
			registeredProductUIModel.setCorporateSupplierFax(corporateSupplier
					.getFax());
			registeredProductUIModel
					.setCorporateSupplierTelephone(corporateSupplier
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convSupplierContactToUI(IndividualCustomer supplierContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (supplierContact != null) {
			registeredProductUIModel.setSupplierContactId(supplierContact
					.getId());
			registeredProductUIModel.setSupplierContactName(supplierContact
					.getName());
			registeredProductUIModel.setSupplierContactEmail(supplierContact
					.getEmail());
			registeredProductUIModel.setSupplierContactMobile(supplierContact
					.getMobile());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convCustomerPartyToUI(
			RegisteredProductInvolveParty customerParty,
			RegisteredProductUIModel registeredProductUIModel) {
		if (customerParty != null) {
			registeredProductUIModel.setRefCustomerUUID(customerParty
					.getRefUUID());
			registeredProductUIModel.setCorporateCustomerName(customerParty
					.getRefPartyName());
			registeredProductUIModel.setCorporateCustomerId(customerParty
					.getRefPartyId());
			registeredProductUIModel
					.setCorporateCustomerTaxNumber(customerParty
							.getRefPartyTaxNumber());
			registeredProductUIModel.setCorporateCustomerAddress(customerParty
					.getRefPartyAddress());
			registeredProductUIModel
					.setCorporateCustomerBankAccount(customerParty
							.getRefPartyBankAccount());
			registeredProductUIModel.setCorporateCustomerEmail(customerParty
					.getRefPartyEmail());
			registeredProductUIModel.setCorporateCustomerFax(customerParty
					.getRefPartyFax());
			registeredProductUIModel
					.setCorporateCustomerTelephone(customerParty
							.getRefPartyTelephone());
			registeredProductUIModel.setCustomerContactUUID(customerParty
					.getRefPartyContactUUID());
			registeredProductUIModel.setCustomerContactId(customerParty
					.getRefPartyContactId());
			registeredProductUIModel.setCustomerContactName(customerParty
					.getRefPartyContactName());
			registeredProductUIModel.setCustomerContactEmail(customerParty
					.getRefPartyContactEmail());
			registeredProductUIModel.setCustomerContactMobile(customerParty
					.getRefPartyContactMobile());
			registeredProductUIModel.setCustomerContactWeixin(customerParty
					.getRefPartyContactWeixin());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	@Deprecated
	public void convCorporateCustomerToUI(CorporateCustomer corporateCustomer,
			RegisteredProductUIModel registeredProductUIModel) {
		if (corporateCustomer != null) {
			registeredProductUIModel.setCorporateCustomerName(corporateCustomer
					.getName());
			registeredProductUIModel.setCorporateCustomerId(corporateCustomer
					.getId());
			registeredProductUIModel
					.setCorporateCustomerTaxNumber(corporateCustomer
							.getTaxNumber());
			registeredProductUIModel
					.setCorporateCustomerAddress(corporateCustomer.getAddress());
			registeredProductUIModel
					.setCorporateCustomerBankAccount(corporateCustomer
							.getBankAccount());
			registeredProductUIModel
					.setCorporateCustomerEmail(corporateCustomer.getEmail());
			registeredProductUIModel.setCorporateCustomerFax(corporateCustomer
					.getFax());
			registeredProductUIModel
					.setCorporateCustomerTelephone(corporateCustomer
							.getTelephone());
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convCustomerContactToUI(IndividualCustomer customerContact,
			RegisteredProductUIModel registeredProductUIModel) {
		if (customerContact != null) {
			registeredProductUIModel.setCustomerContactId(customerContact
					.getId());
			registeredProductUIModel.setCustomerContactName(customerContact
					.getName());
			registeredProductUIModel.setCustomerContactEmail(customerContact
					.getEmail());
			registeredProductUIModel.setCustomerContactMobile(customerContact
					.getMobile());
		}
	}

	public void convRegisteredProductActionLogToUI(
			RegisteredProductActionLog registeredActionLog,
			RegisteredProductActionLogUIModel registeredActionLogUIModel)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		convRegisteredProductActionLogToUI(registeredActionLog,
				registeredActionLogUIModel, null);
	}

	public void convRegisteredProductActionLogToUI(
			RegisteredProductActionLog registeredActionLog,
			RegisteredProductActionLogUIModel registeredActionLogUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException,
			ServiceEntityConfigureException {
		if (registeredActionLog != null) {
			docActionNodeProxy.convDocActionNodeToUI(registeredActionLog, registeredActionLogUIModel);
			registeredActionLogUIModel.setActionCode(registeredActionLog
					.getActionCode());
			if (logonInfo != null) {
				Map<Integer, String> actionCodeMap = this
						.initActionCode(logonInfo.getLanguageCode());
				registeredActionLogUIModel.setActionCodeValue(actionCodeMap
						.get(registeredActionLog.getActionCode()));
			}
			registeredActionLogUIModel.setUpdateFieldsArray(registeredActionLog
					.getUpdateFieldsArray());
			registeredActionLogUIModel.setUpdatedByUUID(registeredActionLog
					.getLastUpdateBy());
			registeredActionLogUIModel.setDocumentType(registeredActionLog
					.getDocumentType());
			if (logonInfo != null) {
				registeredActionLogUIModel
						.setDocumentTypeValue(serviceDocumentComProxy
								.getDocumentTypeValue(
										registeredActionLog.getDocumentType(),
										logonInfo.getLanguageCode()));
			}
			if (registeredActionLog.getLastUpdateTime() != null) {
				registeredActionLogUIModel
						.setUpdatedDate(DefaultDateFormatConstant.DATE_FORMAT
								.format(registeredActionLog.getLastUpdateTime()));
			}
		}
	}

	public void convUpdateByToActionLogUI(LogonUser logonUser,
			RegisteredProductActionLogUIModel registeredActionLogUIModel) {
		if (logonUser != null && registeredActionLogUIModel != null) {
			registeredActionLogUIModel.setUpdatedById(logonUser.getId());
			registeredActionLogUIModel.setUpdatedByName(logonUser.getName());
		}
	}

	public void convDocumentToActionLogUI(ServiceEntityNode document,
			RegisteredProductActionLogUIModel registeredActionLogUIModel) {
		if (document != null && registeredActionLogUIModel != null) {
			registeredActionLogUIModel.setDocumentId(document.getId());
			registeredActionLogUIModel.setDocumentName(document.getName());
		}
	}

	public void convRegisteredProductAttachmentToUI(
			RegisteredProductAttachment registeredProductAttachment,
			RegisteredProductAttachmentUIModel registeredProductAttachmentUIModel) {
		if (registeredProductAttachment != null) {
			registeredProductAttachmentUIModel
					.setUuid(registeredProductAttachment.getUuid());
			registeredProductAttachmentUIModel
					.setAttachmentTitle(registeredProductAttachment.getName());
			registeredProductAttachmentUIModel
					.setAttachmentDescription(registeredProductAttachment
							.getNote());
			registeredProductAttachmentUIModel
					.setFileType(registeredProductAttachment.getFileType());
		}
	}

	@Override
	public ServiceSearchProxy getSearchProxy() {
		return registeredProductSearchProxy;
	}

}
