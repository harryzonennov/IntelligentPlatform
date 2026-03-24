package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.ServiceUIModule;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Repository to manage all the search proxy instance
 */
@Service
public class ServiceUIModelRepository {

	/**
	 * Registration Area to register executable Units here
	 */
	@Qualifier("purchaseContractServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT)
	protected ServiceUIModule purchaseContractServiceUIModel;

	@Qualifier("purchaseRequestServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST)
	protected ServiceUIModule purchaseRequestServiceUIModel;

	@Qualifier("billOfMaterialTemplateServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE)
	protected ServiceUIModule billOfMaterialTemplateServiceUIModel;

	@Qualifier("billOfMaterialOrderServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER)
	protected ServiceUIModule billOfMaterialOrderServiceUIModel;

	@Qualifier("purchaseReturnOrderServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER)
	protected ServiceUIModule purchaseReturnOrderServiceUIModel;

	@Qualifier("inboundDeliveryServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY)
	protected ServiceUIModule inboundDeliveryServiceUIModel;

	@Qualifier("outboundDeliveryServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY)
	protected ServiceUIModule outboundDeliveryServiceUIModel;

	@Qualifier("inventoryTransferOrderServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER)
	protected ServiceUIModule inventoryTransferOrderServiceUIModel;

	@Qualifier("salesContractServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT)
	protected ServiceUIModule salesContractServiceUIModel;

	@Qualifier("salesReturnOrderServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER)
	protected ServiceUIModule salesReturnOrderServiceUIModel;

	@Qualifier("salesForcastServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST)
	protected ServiceUIModule salesForcastServiceUIModel;

	@Qualifier("productionOrderServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER)
	protected ServiceUIModule productionOrderServiceUIModel;

	@Qualifier("productionPlanServiceUIModel")
	@Autowired(required = false)
	@IServiceUIModuleProp(documentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN)
	protected ServiceUIModule productionPlanServiceUIModel;

	/**
	 * ===============end of register area===============
	 */

	protected Logger logger = LoggerFactory.getLogger(ServiceUIModelRepository.class);

	protected Map<String, Map<String, String>> serviceUIModelMapLan = new HashMap<>();

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	public Map<String, String> loadServiceUIModelMap(String languageCode)
			throws ServiceEntityInstallationException {
		String resourcePath = this.getClass().getResource("").getPath() + "ServiceUIModelConfigure";
		return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
				this.serviceUIModelMapLan, resourcePath);
	}


	/**
	 * Logic to get search proxy instance by registereed id
	 * @param serviceUIModelId
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public ServiceUIModule getServiceUIModelById(String serviceUIModelId)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		Field field = ServiceEntityFieldsHelper.getServiceField(this.getClass(), serviceUIModelId);
		field.setAccessible(true);
		return (ServiceUIModule)field.get(this);
	}

	/**
	 * Get ServiceUIModel by document type
	 * @param documentType
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ServiceUIModule  getServiceUIModelByDocumentType(int documentType)
			throws IllegalArgumentException {
		List<ServiceUIModule> resultList = getServiceUIModelByDocumentType(documentType, true);
		if(ServiceCollectionsHelper.checkNullList(resultList)){
			return null;
		} else {
			return resultList.get(0);
		}
	}

	public Map<Integer, String> getAccessDocumentType(String languageCode) throws ServiceEntityInstallationException {
		Map<Integer, String> rawDocTypeMap = serviceDocumentComProxy.getDocumentTypeMap(true, languageCode);
		Map<Integer, String> docTypeMap = new HashMap<>();
		List<Field> allFieldList = this.getServiceUIModelFieldList();
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return null;
		}
		for(Field field:allFieldList){
			field.setAccessible(true);
			try {
				IServiceUIModuleProp serviceUIModuleProp = field.getAnnotation(IServiceUIModuleProp.class);
				if(serviceUIModuleProp == null){
					continue;
				}
				String documentName = rawDocTypeMap.get(serviceUIModuleProp.documentType());
				docTypeMap.put(serviceUIModuleProp.documentType(), documentName);
			} catch (IllegalArgumentException e) {
				// log the issue and configure
			}
		}
		return docTypeMap;
	}

	public List<ServiceUIModule>  getServiceUIModelByDocumentType(int documentType, boolean skipFast)
			throws IllegalArgumentException {
		List<Field> allFieldList = this.getServiceUIModelFieldList();
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return new ArrayList<ServiceUIModule>();
		}
		List<ServiceUIModule> resultList = new ArrayList<>();
		for(Field field:allFieldList){
			field.setAccessible(true);
			try {
				IServiceUIModuleProp serviceUIModuleProp = field.getAnnotation(IServiceUIModuleProp.class);
				if(serviceUIModuleProp == null){
					continue;
				}
				if(documentType != serviceUIModuleProp.documentType()){
					continue;
				}
				ServiceUIModule serviceUIModule = (ServiceUIModule) field.get(this);
				if(serviceUIModule != null){
					resultList.add(serviceUIModule);
				}
				if(skipFast){
					return resultList;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// log the issue and configure
			}
		}
		return resultList;
	}

	/**
	 * Get all the active (not nul) service UI Model instance list
	 * @return
	 */
	public List<ServiceUIModule> getAllServiceUIModelList(){
		return ServiceEntityFieldsHelper.getDefRepoInstList(ServiceUIModule.class, this);
	}

	private List<Field> getServiceUIModelFieldList(){
		List<Field> allFieldList = ServiceEntityFieldsHelper.getFieldsList(ServiceUIModelRepository.class);
		if(ServiceCollectionsHelper.checkNullList(allFieldList)){
			return null;
		}
		List<Field> resultFieldList = new ArrayList<>();
		for(Field field:allFieldList){
			field.setAccessible(true);
			IServiceUIModuleProp serviceUIModuleProp = field.getAnnotation(IServiceUIModuleProp.class);
			if(serviceUIModuleProp == null){
				continue;
			}
			resultFieldList.add(field);
		}
		return resultFieldList;

	}


}
