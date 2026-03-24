package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.service.QualityInspectOrderManager;
import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.production.dto.ProdPickingOrderSearchModel;
import com.company.IntelligentPlatform.production.model.ProdOrderReportItem;
import com.company.IntelligentPlatform.production.model.ProdPickingOrder;
import com.company.IntelligentPlatform.production.model.ProdPickingRefMaterialItem;
import com.company.IntelligentPlatform.production.model.ProdPickingRefOrderItem;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.model.RegisteredProduct;
import com.company.IntelligentPlatform.logistics.model.WarehouseStoreItem;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Helper Class for Production order & relative resources data clean This Class
 * is only for Data Admin Scenario !
 * 
 * @author Zhang,Hang
 *
 */
@Service
public class ProductionOrderDataCleanHelper {

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdPickingOrderSearchProxy prodPickingOrderSearchProxy;
	
	@Autowired
	protected PurchaseContractManager purchaseContractManager;

	@Autowired
	protected InboundDeliveryManager inboundDeliveryManager;

	@Autowired
	protected QualityInspectOrderManager qualityInspectOrderManager;

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;



	
	public void clearProdPickingOrder(List<String> idList, String client) throws ServiceEntityConfigureException{
		if(!ServiceCollectionsHelper.checkNullList(idList)){
			for(String id: idList){
				prodPickingOrderManager
				.admDeleteEntityByKey(id,
						IServiceEntityNodeFieldConstant.ID, ProdPickingOrder.NODENAME);
			}
		}
	}


	protected void printProdPickingOrderWrapper(
			List<ServiceEntityNode> prodPickingRefOrderItemList, String client)
			throws ServiceEntityConfigureException {
		List<Object> uuidList = new ArrayList<>();		
		if (!ServiceCollectionsHelper
				.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				uuidList.add(seNode.getUuid());
			}
		}else{
			return;
		}
		// Search picking reference material item list
		List<ServiceBasicKeyStructure> nextkeyList = buildKeyStructureListByUUID(
				uuidList, IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		List<ServiceEntityNode> pickingRefMatItemList = prodPickingOrderManager
				.getEntityNodeListByKeyList(nextkeyList,
						ProdPickingRefMaterialItem.NODENAME, null);
		if (!ServiceCollectionsHelper.checkNullList(pickingRefMatItemList)) {
			printDocItemTemplateWrapper(pickingRefMatItemList,
					IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
		}
		// Check Node ProdPicking Order, if no sub node, then delete them
		Map<String, List<?>> parentNodeUUIDMap = new HashMap<>();
		parentNodeUUIDMap.put(IServiceEntityNodeFieldConstant.UUID, uuidList);
		ProdPickingOrderSearchModel prodPickingOrderSearchModel = new ProdPickingOrderSearchModel();
		List<ServiceEntityNode> rawProdPickingOrderList = new ArrayList<>();
		try {
			rawProdPickingOrderList = prodPickingOrderSearchProxy.searchDocListCore(
					prodPickingOrderSearchModel, parentNodeUUIDMap, client);
		} catch (SearchConfigureException | ServiceEntityInstallationException e) {
			// do nothing
		}
		if (ServiceCollectionsHelper.checkNullList(rawProdPickingOrderList)) {
			return;
		}
		for (ServiceEntityNode seNode : rawProdPickingOrderList) {
			List<ServiceEntityNode> subRefOrderItemList = prodPickingOrderManager
					.getEntityNodeListByKey(seNode.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdPickingRefOrderItem.NODENAME, client, null);
			if (ServiceCollectionsHelper.checkNullList(subRefOrderItemList)) {
				// In case no sub node, then delete this picking order
				this.printDocItem(seNode);
			}
		}
	}

	protected void deleteProdPickingOrderWrapper(
			List<ServiceEntityNode> prodPickingRefOrderItemList, String client)
			throws ServiceEntityConfigureException {
		List<Object> uuidList = new ArrayList<>();
		if (!ServiceCollectionsHelper
				.checkNullList(prodPickingRefOrderItemList)) {
			for (ServiceEntityNode seNode : prodPickingRefOrderItemList) {
				uuidList.add(seNode.getUuid());
			}
		}else{
			return;
		}
		// Search picking reference material item list
		List<ServiceBasicKeyStructure> nextkeyList = buildKeyStructureListByUUID(
				uuidList, IServiceEntityNodeFieldConstant.PARENTNODEUUID);
		List<ServiceEntityNode> pickingRefMatItemList = prodPickingOrderManager
				.getEntityNodeListByKeyList(nextkeyList,
						ProdPickingRefMaterialItem.NODENAME, null);
		if (!ServiceCollectionsHelper.checkNullList(pickingRefMatItemList)) {
			deleteDocItemTemplateWrapper(pickingRefMatItemList,
					IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
		}
		// Check Node ProdPicking Order, if no sub node, then delete them
		Map<String, List<?>> parentNodeUUIDMap = new HashMap<String, List<?>>();
		parentNodeUUIDMap.put(IServiceEntityNodeFieldConstant.UUID, uuidList);
		ProdPickingOrderSearchModel prodPickingOrderSearchModel = new ProdPickingOrderSearchModel();
		List<ServiceEntityNode> rawProdPickingOrderList = new ArrayList<>();
		try {
			rawProdPickingOrderList = prodPickingOrderSearchProxy.searchDocListCore(
					prodPickingOrderSearchModel, parentNodeUUIDMap, client);
		} catch (SearchConfigureException | ServiceEntityInstallationException e) {
			// do nothing
		}
		if (ServiceCollectionsHelper.checkNullList(rawProdPickingOrderList)) {
			return;
		}
		for (ServiceEntityNode seNode : rawProdPickingOrderList) {
			List<ServiceEntityNode> subRefOrderItemList = prodPickingOrderManager
					.getEntityNodeListByKey(seNode.getUuid(),
							IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdPickingRefOrderItem.NODENAME, client, null);
			if (ServiceCollectionsHelper.checkNullList(subRefOrderItemList)) {
				// In case no sub node, then delete this picking order
				prodPickingOrderManager.admDeleteEntityByKey(seNode.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						seNode.getNodeName());
			}
		}
	}

	protected void printDocItemTemplateWrapper(
			List<ServiceEntityNode> docItemList, int documentType) {
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return;
		}
		boolean stopFlag = false;
		boolean skipPreCheckFlag = false;
		Function<ServiceEntityNode, ServiceEntityNode> docItemFunc = seNode -> {
			try {
				this.printDocItem(seNode);
			} catch (Exception e) {
				// do nothing
			}
			return seNode;
		};
		Function<ServiceEntityNode, ServiceEntityNode> documentFunc = parentNode -> {
			try {
				printDocument(parentNode);
			} catch (ServiceEntityConfigureException e1) {
				return null;
			}
			return null;
		};
//		traceDocItemToPrevTemplate(
//				docItemList,
//				serviceItemList -> {
//					return preCheckByDocType(serviceItemList, null,
//							docItemFunc, documentFunc, stopFlag, documentType);
//				}, docItemFunc, documentFunc, stopFlag, skipPreCheckFlag, documentType);
		traceDocItemToNextTemplate(
				docItemList,
				serviceItemList -> {
					return preCheckByDocType(serviceItemList, null,
							docItemFunc, documentFunc, stopFlag, documentType);
				}, docItemFunc, documentFunc, false, skipPreCheckFlag, documentType);

	}
	

	protected void deleteDocItemTemplateWrapper(
			List<ServiceEntityNode> docItemList, int documentType) {
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return;
		}
		
		boolean stopFlag = false;
		Function<ServiceEntityNode, ServiceEntityNode> docItemFunc = seNode -> {
			try {
				ServiceEntityManager refDocManager = serviceEntityManagerFactoryInContext.getManagerBySEName(seNode.getServiceEntityName());
				refDocManager.admDeleteEntityByKey(seNode.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						seNode.getNodeName());
			} catch (Exception e) {
				// do nothing
			}
			return seNode;
		};
		Function<ServiceEntityNode, ServiceEntityNode> documentFunc = parentNode -> {
			try {
				ServiceEntityManager refDocManager = serviceEntityManagerFactoryInContext.getManagerBySEName(parentNode.getServiceEntityName());
				refDocManager.admDeleteEntityByKey(parentNode.getUuid(),
						IServiceEntityNodeFieldConstant.UUID,
						parentNode.getNodeName());
			} catch (ServiceEntityConfigureException e1) {
				return null;
			}
			return null;
		};

//		traceDocItemToPrevTemplate(
//				docItemList,
//				serviceItemList -> {
//					return preCheckByDocType(serviceItemList, null,
//							docItemFunc, documentFunc, stopFlag, documentType);
//				}, docItemFunc, documentFunc, stopFlag, false, documentType);

		traceDocItemToNextTemplate(
				docItemList,
				serviceItemList -> {
					return preCheckByDocType(serviceItemList, null,
							docItemFunc, documentFunc, stopFlag, documentType);
				}, docItemFunc, documentFunc, stopFlag, false, documentType);

	}


	protected void traceProdPickingMatItem(
			List<ServiceEntityNode> prodPickingMatItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			String client) {
		if (ServiceCollectionsHelper.checkNullList(prodPickingMatItemList)) {
			return;
		}
		List<Object> parentNodeUUIDList = new ArrayList<>();
		for (ServiceEntityNode seNode : prodPickingMatItemList) {
			parentNodeUUIDList.add(seNode.getParentNodeUUID());
		}
		this.traceDocItemToPrevTemplate(prodPickingMatItemList, precheckFunc,
				docItemFunc, documentFunc, false, true,
				IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
//		this.traceDocItemToNextTemplate(prodPickingMatItemList, precheckFunc,
//				docItemFunc, documentFunc, false, true,
//				IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER);
		List<ServiceBasicKeyStructure> parentNodekeyList = buildKeyStructureListByUUID(
				parentNodeUUIDList, null);
		List<ServiceEntityNode> parentNodeList;
		try {
			parentNodeList = prodPickingOrderManager
					.getEntityNodeListByKeyList(parentNodekeyList,
							ProdPickingRefOrderItem.NODENAME, null);
			if (!ServiceCollectionsHelper.checkNullList(parentNodeList)) {
				for (ServiceEntityNode parentNode : parentNodeList) {
					if (documentFunc != null) {
						documentFunc.apply(parentNode);
					}
				}
			}
		} catch (ServiceEntityConfigureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void tracePurchaseContractMatItem(
			List<ServiceEntityNode> purchaseContractMatItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			String client) {
		if (ServiceCollectionsHelper.checkNullList(purchaseContractMatItemList)) {
			return;
		}
		List<Object> parentNodeUUIDList = new ArrayList<>();
		for (ServiceEntityNode seNode : purchaseContractMatItemList) {
			parentNodeUUIDList.add(seNode.getParentNodeUUID());
		}
		// Stop to further step
		this.traceDocItemToNextTemplate(purchaseContractMatItemList, precheckFunc,
				docItemFunc, documentFunc, true, true,
				IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT);		
	}

	protected void traceQualityInspectItem(
			List<ServiceEntityNode> qualityInspectItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag) {
		if (ServiceCollectionsHelper.checkNullList(qualityInspectItemList)) {
			return;
		}
		for (ServiceEntityNode seNode : qualityInspectItemList) {
			QualityInspectMatItem qualityInspectMatItem = (QualityInspectMatItem) seNode;
			try {
				RegisteredProduct registeredProduct = (RegisteredProduct) registeredProductManager
						.getEntityNodeByKey(
								qualityInspectMatItem.getRefMaterialSKUUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								RegisteredProduct.NODENAME, null);
				if (registeredProduct != null) {
					if (docItemFunc != null) {
						docItemFunc.apply(registeredProduct);
					}
				}
			} catch (ServiceEntityConfigureException e) {
				// just continue
			}

		}

		this.traceDocItemToNextTemplate(qualityInspectItemList, precheckFunc,
				docItemFunc, documentFunc, stopFlag, true,
				IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER);

	}
	
	protected void traceProductReportItem(
			List<ServiceEntityNode> prodOrderReportItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag) {
		if (ServiceCollectionsHelper.checkNullList(prodOrderReportItemList)) {
			return;
		}
		for (ServiceEntityNode seNode : prodOrderReportItemList) {
			ProdOrderReportItem prodOrderReportItem = (ProdOrderReportItem) seNode;
			try {
				RegisteredProduct registeredProduct = (RegisteredProduct) registeredProductManager
						.getEntityNodeByKey(
								prodOrderReportItem.getRefMaterialSKUUUID(),
								IServiceEntityNodeFieldConstant.UUID,
								RegisteredProduct.NODENAME, null);
				if (registeredProduct != null) {
					if (docItemFunc != null) {
						docItemFunc.apply(registeredProduct);
					}
				}
			} catch (ServiceEntityConfigureException e) {
				// just continue
			}
			if (docItemFunc != null) {
				docItemFunc.apply(prodOrderReportItem);
			}
		}
		

		this.traceDocItemToNextTemplate(prodOrderReportItemList, precheckFunc,
				docItemFunc, documentFunc, stopFlag, true,
				IDefDocumentResource.DOCUMENT_TYPE_PRODORDERREPORT);

	}
	
	

	protected void traceWarehouseStoreItem(
			List<ServiceEntityNode> warehouseStoreItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag) {
		if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
			return;
		}
		List<ServiceEntityNode> outboundItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : warehouseStoreItemList) {
			WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) seNode;
			try {
				OutboundItem outboundItem = (OutboundItem) outboundDeliveryManager
						.getEntityNodeByKey(warehouseStoreItem.getUuid(),
								"refStoreItemUUID",
								OutboundItem.NODENAME, null);
				outboundItemList.add(outboundItem);
				if (docItemFunc != null) {
					docItemFunc.apply(warehouseStoreItem);
				}
			} catch (ServiceEntityConfigureException e) {
				continue;
			}
		}
		if (ServiceCollectionsHelper.checkNullList(outboundItemList)) {
			return;
		}
		this.traceDocItemToNextTemplate(outboundItemList, precheckFunc,
				docItemFunc, documentFunc, stopFlag, false,
				IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY);
	}

	protected void traceInboundItemList(
			List<ServiceEntityNode> inboundItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag) {
		if (ServiceCollectionsHelper.checkNullList(inboundItemList)) {
			return;
		}
		List<ServiceEntityNode> warehouseStoreItemList = new ArrayList<>();
		for (ServiceEntityNode seNode : inboundItemList) {
			InboundItem inboundItem = (InboundItem) seNode;
			try {
				WarehouseStoreItem warehouseStoreItem = (WarehouseStoreItem) warehouseStoreManager
						.getEntityNodeByKey(inboundItem.getUuid(),
								"refInboundItemUUID",
								OutboundItem.NODENAME, null);
				warehouseStoreItemList.add(warehouseStoreItem);
				if (docItemFunc != null) {
					docItemFunc.apply(inboundItem);
				}
			} catch (ServiceEntityConfigureException e) {
				continue;
			}
		}
		if (ServiceCollectionsHelper.checkNullList(warehouseStoreItemList)) {
			return;
		}
		this.traceDocItemToNextTemplate(warehouseStoreItemList, precheckFunc,
				docItemFunc, documentFunc, stopFlag, false,
				IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM);
	}

	protected List<ServiceEntityNode> preCheckByDocType(
			List<ServiceEntityNode> docItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag, int documentType) {
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return null;
		}
		String client = docItemList.get(0).getClient();
		ServiceEntityNode seNode = docItemList.get(0);
		if (WarehouseStoreItem.NODENAME.equals(seNode.getNodeName())) {
			this.traceWarehouseStoreItem(docItemList, precheckFunc,
					docItemFunc, documentFunc, stopFlag);
			return null;
		}
		if (InboundItem.NODENAME.equals(seNode.getNodeName())) {
			this.traceInboundItemList(docItemList, precheckFunc, docItemFunc,
					documentFunc, stopFlag);
			return null;
		}
		if (QualityInspectMatItem.NODENAME.equals(seNode.getNodeName())) {
			this.traceQualityInspectItem(docItemList, precheckFunc,
					docItemFunc, documentFunc, stopFlag);
			return null;
		}
		if (PurchaseContractMaterialItem.NODENAME.equals(seNode.getNodeName())) {
			this.tracePurchaseContractMatItem(docItemList, precheckFunc,
					docItemFunc, documentFunc, client);
			return null;
		}
		if (ProdPickingRefMaterialItem.NODENAME.equals(seNode.getNodeName())) {
			this.traceProdPickingMatItem(docItemList, precheckFunc,
					docItemFunc, documentFunc, client);
			return null;
		}
		if (ProdOrderReportItem.NODENAME.equals(seNode.getNodeName())) {
			this.traceProductReportItem(docItemList, precheckFunc, docItemFunc, documentFunc, stopFlag);
			return null;
		}
		return docItemList;
	}

	/**
	 * Trace Doc Item list to Next item
	 * 
	 * @param docItemList
	 * @param docItemFunc
	 *            : document item call-back function
	 * @param documentFunc
	 *            : document root call-back function
	 * @param documentType
	 */
	protected Map<Integer, List<Object>> traceDocItemToNextTemplate(
			List<ServiceEntityNode> docItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag, boolean skipPreCheck, int documentType) {
		/*
		 * [Step1] Traverse current doc item list, and build the next doc item
		 * map
		 */
		if (!skipPreCheck) {
			//docItemList = precheckFunc.apply(docItemList);
			preCheckByDocType(docItemList, null,
					docItemFunc, documentFunc, stopFlag, documentType);
		}
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return null;
		}
		Map<Integer, List<Object>> nextDocItemUUIDMap = new HashMap<>();
		List<Object> parentDocUUIDList = new ArrayList<>();
		for (ServiceEntityNode seNode : docItemList) {
			int nextDocType = serviceDocumentComProxy.getNextDocType(seNode);
			if (nextDocType == 0) {
				continue;
			}
			List<Object> nextDocItemUUIDList = nextDocItemUUIDMap
					.get(nextDocType);
			parentDocUUIDList.add(seNode.getParentNodeUUID());
			if (nextDocItemUUIDList == null) {
				nextDocItemUUIDList = new ArrayList<>();
				String nextDocItemUUID = serviceDocumentComProxy
						.getNextMatItemUUID(seNode);
				if (!ServiceEntityStringHelper.checkNullString(nextDocItemUUID)) {
					nextDocItemUUIDList.add(nextDocItemUUID);
					nextDocItemUUIDMap.put(nextDocType, nextDocItemUUIDList);
				}
			} else {
				String nextDocItemUUID = serviceDocumentComProxy
						.getNextMatItemUUID(seNode);
				if (!ServiceEntityStringHelper.checkNullString(nextDocItemUUID)) {
					nextDocItemUUIDList.add(nextDocItemUUID);
				}
			}
			/*
			 * [Step2] Using call back to trace current doc item
			 */
			if (docItemFunc != null) {
				seNode = docItemFunc.apply(seNode);
			}
		}
		/*
		 * [Step3] Check and process the parent node, if necessary
		 */
		if (!ServiceCollectionsHelper.checkNullList(parentDocUUIDList)) {
			List<ServiceBasicKeyStructure> parentNodekeyList = buildKeyStructureListByUUID(
					parentDocUUIDList, null);
			ServiceEntityManager curDocManager = serviceDocumentComProxy
					.getDocumentManager(documentType);
			if (curDocManager != null) {
				List<ServiceEntityNode> parentNodeList;
				try {
					parentNodeList = curDocManager.getEntityNodeListByKeyList(
							parentNodekeyList, ServiceEntityNode.NODENAME_ROOT,
							null);
					if (!ServiceCollectionsHelper.checkNullList(parentNodeList)) {
						for (ServiceEntityNode parentNode : parentNodeList) {
							if (documentFunc != null) {
								documentFunc.apply(parentNode);
							}
						}
					}
				} catch (ServiceEntityConfigureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// In case stop flag, just stop trace to next doc and return
		if (stopFlag) {
			return nextDocItemUUIDMap;
		}

		/*
		 * [Step4] Navigate to next doc item
		 */
		Set<Integer> keySet = nextDocItemUUIDMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Integer docType = it.next();
			List<Object> nextDocItemUUIDList = nextDocItemUUIDMap.get(docType);
			if (ServiceCollectionsHelper.checkNullList(nextDocItemUUIDList)) {
				continue;
			}
			List<ServiceBasicKeyStructure> nextkeyList = buildKeyStructureListByUUID(
					nextDocItemUUIDList, null);
			ServiceEntityManager refNextDocManager = serviceDocumentComProxy
					.getDocumentManager(docType);
			String targetNodeName = serviceDocumentComProxy
					.getDocumentMaterialItemNodeName(docType);
			try {
				List<ServiceEntityNode> nextDocItemList = refNextDocManager
						.getEntityNodeListByKeyList(nextkeyList,
								targetNodeName, null);
				this.traceDocItemToNextTemplate(nextDocItemList, precheckFunc,
						docItemFunc, documentFunc, stopFlag, false, docType);
			} catch (ServiceEntityConfigureException e) {
				// just continue;
			}
		}
		return nextDocItemUUIDMap;
	}

	protected void printDocItem(ServiceEntityNode seNode)
			throws ServiceEntityConfigureException {
		StringBuffer setenceBuffer = new StringBuffer();
		setenceBuffer.append("---" + seNode.getNodeName() + "--uuid:"
				+ seNode.getUuid());
		String refMaterialSKUUUID = ServiceEntityFieldsHelper
				.getStrServiceFieldValueWrapper(seNode,
						IServiceEntityCommonFieldConstant.REFMATERIALSKUUUID);
		if (!ServiceEntityStringHelper.checkNullString(refMaterialSKUUUID)) {
			MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
					.getEntityNodeByKey(refMaterialSKUUUID,
							IServiceEntityNodeFieldConstant.UUID,
							MaterialStockKeepUnit.NODENAME, seNode.getClient(),
							null);
			if (materialStockKeepUnit != null) {
				setenceBuffer.append("-MAT:" + materialStockKeepUnit.getId());
			}
			double amount = serviceDocumentComProxy.getAmount(seNode);
			if (amount > 0) {
				setenceBuffer.append("-amount:" + amount);
			}
		}
		System.out.println(setenceBuffer.toString());
	}

	protected void printDocument(ServiceEntityNode document)
			throws ServiceEntityConfigureException {
		StringBuffer setenceBuffer = new StringBuffer();
		if (document == null) {
			return;
		}
		String nodeInstId = document.getServiceEntityName();
		if (!ServiceEntityNode.NODENAME_ROOT.equals(document.getNodeName())) {
			nodeInstId = document.getNodeName();
		}
		setenceBuffer.append("**-" + nodeInstId + "--ID:" + document.getId());
		System.out.println(setenceBuffer.toString());

	}

	/**
	 * Trace Doc Item list to Previous item
	 * 
	 * @param docItemList
	 * @param docItemFunc
	 *            : document item call-back function
	 * @param documentFunc
	 *            : document root call-back function
	 * @param documentType
	 */
	protected Map<Integer, List<Object>> traceDocItemToPrevTemplate(
			List<ServiceEntityNode> docItemList,
			Function<List<ServiceEntityNode>, List<ServiceEntityNode>> precheckFunc,
			Function<ServiceEntityNode, ServiceEntityNode> docItemFunc,
			Function<ServiceEntityNode, ServiceEntityNode> documentFunc,
			boolean stopFlag, boolean skipPreCheck, int documentType) {
		/*
		 * [Step1] Traverse current doc item list, and build the next doc item
		 * map
		 */
		if (!skipPreCheck) {
			preCheckByDocType(docItemList, null,
					docItemFunc, documentFunc, stopFlag, documentType);
		}
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return null;
		}
		Map<Integer, List<Object>> prevDocItemUUIDMap = new HashMap<>();
		List<Object> parentDocUUIDList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(docItemList)) {
			return null;
		}
		for (ServiceEntityNode seNode : docItemList) {
			int prevDocType = serviceDocumentComProxy.getPrevDocType(seNode);
			if (prevDocType == 0) {
				continue;
			}
			List<Object> prevDocItemUUIDList = prevDocItemUUIDMap
					.get(prevDocType);
			if (!ServiceEntityStringHelper.checkNullString(seNode
					.getParentNodeUUID())) {
				parentDocUUIDList.add(seNode.getParentNodeUUID());
			}
			if (prevDocItemUUIDList == null) {
				prevDocItemUUIDList = new ArrayList<>();
				String prevDocItemUUID = serviceDocumentComProxy
						.getPrevMatItemUUID(seNode);
				if (!ServiceEntityStringHelper.checkNullString(prevDocItemUUID)) {
					prevDocItemUUIDList.add(prevDocItemUUID);
					prevDocItemUUIDMap.put(prevDocType, prevDocItemUUIDList);
				}
			} else {
				String prevDocItemUUID = serviceDocumentComProxy
						.getPrevMatItemUUID(seNode);
				if (!ServiceEntityStringHelper.checkNullString(prevDocItemUUID)) {
					prevDocItemUUIDList.add(prevDocItemUUID);
				}
			}
			/*
			 * [Step2] Delete current doc item uuid
			 */
			if (docItemFunc != null) {
				seNode = docItemFunc.apply(seNode);
			}
		}
		/*
		 * [Step3] Check and process the parent node, if necessary
		 */
		if (!ServiceCollectionsHelper.checkNullList(parentDocUUIDList)) {
			List<ServiceBasicKeyStructure> parentNodekeyList = buildKeyStructureListByUUID(
					parentDocUUIDList, null);
			ServiceEntityManager curDocManager = serviceDocumentComProxy
					.getDocumentManager(documentType);
			if (curDocManager != null) {
				List<ServiceEntityNode> parentNodeList;
				try {
					parentNodeList = curDocManager.getEntityNodeListByKeyList(
							parentNodekeyList, ServiceEntityNode.NODENAME_ROOT,
							null);
					if (!ServiceCollectionsHelper.checkNullList(parentNodeList)) {
						for (ServiceEntityNode parentNode : parentNodeList) {
							if (documentFunc != null) {
								documentFunc.apply(parentNode);
							}
						}
					}
				} catch (ServiceEntityConfigureException e) {

				}

			}
		}

		if (stopFlag) {
			return prevDocItemUUIDMap;
		}

		/*
		 * [Step4] Navigate to next doc item
		 */
		Set<Integer> keySet = prevDocItemUUIDMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			Integer docType = it.next();
			List<Object> prevDocItemUUIDList = prevDocItemUUIDMap.get(docType);
			if (ServiceCollectionsHelper.checkNullList(prevDocItemUUIDList)) {
				continue;
			}
			List<ServiceBasicKeyStructure> prevkeyList = buildKeyStructureListByUUID(
					prevDocItemUUIDList, null);
			ServiceEntityManager refNextDocManager = serviceDocumentComProxy
					.getDocumentManager(docType);
			String targetNodeName = serviceDocumentComProxy
					.getDocumentMaterialItemNodeName(docType);
			try {
				List<ServiceEntityNode> prevDocItemList = refNextDocManager
						.getEntityNodeListByKeyList(prevkeyList,
								targetNodeName, null);
				this.traceDocItemToPrevTemplate(prevDocItemList, precheckFunc,
						docItemFunc, documentFunc, stopFlag, false, docType);
			} catch (ServiceEntityConfigureException e) {
				// just continue;
			}
		}
		return prevDocItemUUIDMap;
	}

	public List<ServiceBasicKeyStructure> buildKeyStructureListByUUID(
			List<Object> docItemUUIDList, String keyName) {
		List<ServiceBasicKeyStructure> keyList = new ArrayList<>();
		ServiceBasicKeyStructure keyStructure = new ServiceBasicKeyStructure();
		if (ServiceEntityStringHelper.checkNullString(keyName)) {
			keyStructure.setKeyName(IServiceEntityNodeFieldConstant.UUID);
		} else {
			keyStructure.setKeyName(keyName);
		}
		keyStructure.setMultipleValueList(docItemUUIDList);
		keyList.add(keyStructure);
		return keyList;
	}

}
