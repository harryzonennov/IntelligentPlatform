package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.logistics.dto.DeliveryMatItemBatchGenRequest;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryException;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.service.OutboundDeliveryServiceModel;
import com.company.IntelligentPlatform.logistics.service.WarehouseStoreManager;
import com.company.IntelligentPlatform.logistics.model.OutboundDelivery;
import com.company.IntelligentPlatform.logistics.model.OutboundItem;
import com.company.IntelligentPlatform.production.model.ProdOrderItemReqProposal;
import com.company.IntelligentPlatform.production.model.RepairProdOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.logistics.dto.WarehouseStoreItemSearchModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.SearchConfigureException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.NodeNotFoundException;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.ArrayList;
import java.util.List;


@Service
public class RepairProdOrderToCrossOutboundProxy {

	@Autowired
	protected OutboundDeliveryManager outboundDeliveryManager;

	@Autowired
	protected RepairProdOrderManager repairProdOrderManager;

	@Autowired
	protected WarehouseStoreManager warehouseStoreManager;

	@Autowired
	protected ProductionOutboundDeliveryManager productionOutboundDeliveryManager;

	/**
	 * Entrance to generate out-bound delivery service model by warehouse store
	 * item list batch
	 *
	 * @param genRequest
	 * @throws ServiceEntityConfigureException
	 * @throws ServiceModuleProxyException
	 * @throws OutboundDeliveryException
	 * @throws LogonInfoException
	 * @throws MaterialException
	 * @throws ServiceEntityInstallationException
	 * @throws NodeNotFoundException
	 * @throws SearchConfigureException
	 */
	public List<OutboundDeliveryServiceModel> genOutboundFromStoreList(DeliveryMatItemBatchGenRequest genRequest, LogonInfo logonInfo)
            throws MaterialException, OutboundDeliveryException, ServiceModuleProxyException, ServiceEntityConfigureException,
            SearchConfigureException, NodeNotFoundException, ServiceEntityInstallationException, AuthorizationException, LogonInfoException, DocActionException {
		WarehouseStoreItemSearchModel searchModel = new WarehouseStoreItemSearchModel();
		searchModel.setUuid(ServiceEntityStringHelper.convStringListIntoMultiStringValue(genRequest.getUuidList()));
		if (ServiceCollectionsHelper.checkNullList(genRequest.getUuidList())) {
			throw new OutboundDeliveryException(OutboundDeliveryException.PARA_NO_WARESTORE_ITEM, "");
		}
		RepairProdOrder repairProdOrder = (RepairProdOrder) repairProdOrderManager
				.getEntityNodeByKey(genRequest.getBaseUUID(), IServiceEntityNodeFieldConstant.UUID, RepairProdOrder.NODENAME, logonInfo.getClient(),
						null);
		List<ServiceEntityNode> outboundDeliveryList = getExistOutboundDeliveryForCreationBatch(repairProdOrder, null);
		List<ServiceEntityNode> warehouseStoreItemList = warehouseStoreManager.searchStoreItemInternal(searchModel, logonInfo);
		return productionOutboundDeliveryManager
				.genOutboundFromStoreList(genRequest, warehouseStoreItemList, outboundDeliveryList, logonInfo, null, null);
	}

	/**
	 * Logic to get the Existed & Proper OutboundDelivery instance list for batch
	 * outbound delivery creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: OutboundDelivery already existed and binded to one of production order
	 * material items. 2nd: OutboundDelivery status should be 'Initial'. 3rd:
	 * OutboundDelivery should be purchase contract generation type
	 *
	 * @param repairProdOrder
	 * @param rawProdOrderItemProsalList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getExistOutboundDeliveryForCreationBatch(RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> rawProdOrderItemProsalList) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> outboundItemList = getExistOutboundItemListForCreationBatch(repairProdOrder,
				rawProdOrderItemProsalList);
		List<String> rootUUIDList = new ArrayList<>();
		List<ServiceEntityNode> outboundDeliveryList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(outboundItemList)) {
			outboundItemList.forEach(rawSENode -> {
				if (!rootUUIDList.contains(rawSENode.getRootNodeUUID())) {
					rootUUIDList.add(rawSENode.getRootNodeUUID());
				}
			});
			outboundDeliveryList = outboundDeliveryManager
					.getEntityNodeListByMultipleKey(rootUUIDList, IServiceEntityNodeFieldConstant.UUID, OutboundDelivery.NODENAME,
							repairProdOrder.getClient(), null);
		}

		/*
		 * [Step3] Traverse and process each in-bound delivery
		 */
		List<ServiceEntityNode> resultList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(outboundDeliveryList)) {
			for (ServiceEntityNode rawSENode : outboundDeliveryList) {
				OutboundDelivery outboundDelivery = (OutboundDelivery) rawSENode;
				if (outboundDelivery.getStatus() != OutboundDelivery.STATUS_INITIAL) {
					continue;
				}
				resultList.add(outboundDelivery);
			}
		}
		if (!ServiceCollectionsHelper.checkNullList(resultList)) {
			return resultList;
		}
		return resultList;
	}


	/**
	 * Logic to get the Existed & Proper OutboundDelivery Item instance list for
	 * batch in-bound delivery creation.
	 * <p>
	 * The "Proper" Standard:<br>
	 * 1th: OutboundDelivery already existed and binded to one of production
	 * material items. 2nd: OutboundDelivery status should be 'Initial'. 3rd:
	 * OutboundDelivery should be production order generation type
	 *
	 * @param repairProdOrder
	 * @param rawProdOrderItemProsalList
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	private List<ServiceEntityNode> getExistOutboundItemListForCreationBatch(RepairProdOrder repairProdOrder,
			List<ServiceEntityNode> rawProdOrderItemProsalList) throws ServiceEntityConfigureException {
		List<String> refOutboundItemUUIDList = new ArrayList<>();
		if (ServiceCollectionsHelper.checkNullList(rawProdOrderItemProsalList)) {
			rawProdOrderItemProsalList = repairProdOrderManager
					.getEntityNodeListByKey(repairProdOrder.getUuid(), IServiceEntityNodeFieldConstant.ROOTNODEUUID,
							ProdOrderItemReqProposal.NODENAME, repairProdOrder.getClient(), null);
		}
		if (!ServiceCollectionsHelper.checkNullList(rawProdOrderItemProsalList)) {
			// In case raw all production order material item list is
			// provided
			// as input paras.
			for (ServiceEntityNode seNode : rawProdOrderItemProsalList) {
				ProdOrderItemReqProposal prodOrderItemReqProposal = (ProdOrderItemReqProposal) seNode;
				if (!ServiceEntityStringHelper.checkNullString(prodOrderItemReqProposal.getNextDocMatItemUUID())
						&& prodOrderItemReqProposal.getNextDocType() == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY) {
					refOutboundItemUUIDList.add(prodOrderItemReqProposal.getNextDocMatItemUUID());
				}
			}
		}

		/*
		 * [Step2]:Get the in-bound delivery list
		 */
		List<ServiceEntityNode> outboundItemList = new ArrayList<>();
		if (!ServiceCollectionsHelper.checkNullList(refOutboundItemUUIDList)) {
			outboundItemList = outboundDeliveryManager
					.getEntityNodeListByMultipleKey(refOutboundItemUUIDList, IServiceEntityNodeFieldConstant.UUID,
							OutboundItem.NODENAME, repairProdOrder.getClient(), null);

		}
		return outboundItemList;
	}


}
