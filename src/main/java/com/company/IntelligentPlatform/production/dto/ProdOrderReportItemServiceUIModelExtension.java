package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderReportItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.RegisteredProductManager;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtensionUnion;
import com.company.IntelligentPlatform.common.controller.UIModelNodeMapConfigure;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceEntityManagerFactoryInContext;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Service
public class ProdOrderReportItemServiceUIModelExtension extends
		ServiceUIModelExtension {

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected RegisteredProductManager registeredProductManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ServiceEntityManagerFactoryInContext serviceEntityManagerFactoryInContext;

	public List<ServiceUIModelExtension> getChildUIModelExtensions() {
		List<ServiceUIModelExtension> resultList = new ArrayList<ServiceUIModelExtension>();
		return resultList;
	}

	@Override
	public List<ServiceUIModelExtensionUnion> genUIModelExtensionUnion() {
		List<ServiceUIModelExtensionUnion> resultList = new ArrayList<>();
		List<UIModelNodeMapConfigure> uiModelNodeMapList = new ArrayList<>();
		ServiceUIModelExtensionUnion prodOrderReportItemExtensionUnion = new ServiceUIModelExtensionUnion();
		prodOrderReportItemExtensionUnion
				.setNodeInstId(ProdOrderReportItem.NODENAME);
		prodOrderReportItemExtensionUnion
				.setNodeName(ProdOrderReportItem.NODENAME);

		// UI Model Configure of node:[reserved Order MatItem]
		UIModelNodeMapConfigure reservedMatItemMap = new UIModelNodeMapConfigure();
		reservedMatItemMap.setBaseNodeInstID(ProdOrderReportItem.NODENAME);
		reservedMatItemMap.setNodeInstID("reservedMatItem");
		reservedMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		// UI Model Configure of node:[SalesContractMaterialItem]
		reservedMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdOrderReportItem prodOrderReportItem = (ProdOrderReportItem) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(prodOrderReportItem
									.getReservedDocType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentItemNode = null;
					try {
						String targetNodeName = serviceDocumentComProxy
								.getDocumentMaterialItemNodeName(prodOrderReportItem
										.getReservedDocType());
						documentItemNode = refDocumentManager
								.getEntityNodeByKey(prodOrderReportItem
										.getReservedMatItemUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										targetNodeName, prodOrderReportItem
												.getClient(), null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(reservedMatItemMap);

		// UI Model Configure of node:[Prev Order MatItem]
		UIModelNodeMapConfigure prevMatItemMap = new UIModelNodeMapConfigure();
		prevMatItemMap.setBaseNodeInstID(ProdOrderReportItem.NODENAME);
		prevMatItemMap.setNodeInstID("refPrevMatItem");
		prevMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		prevMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdOrderReportItem prodOrderReportItem = (ProdOrderReportItem) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(prodOrderReportItem
									.getPrevDocType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentItemNode = null;
					try {
						String targetNodeName = serviceDocumentComProxy
								.getDocumentMaterialItemNodeName(prodOrderReportItem
										.getPrevDocType());
						documentItemNode = refDocumentManager
								.getEntityNodeByKey(prodOrderReportItem
										.getPrevDocMatItemUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										targetNodeName, prodOrderReportItem
												.getClient(), null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(prevMatItemMap);

		// UI Model Configure of node:[ref Next MatItem]
		UIModelNodeMapConfigure nextMatItemMap = new UIModelNodeMapConfigure();
		nextMatItemMap.setBaseNodeInstID(ProdOrderReportItem.NODENAME);
		nextMatItemMap.setNodeInstID("refNextMatItem");
		nextMatItemMap
				.setToBaseNodeType(UIModelNodeMapConfigure.TOBASENODE_OTHERS);
		nextMatItemMap
				.setGetSENodeCallback(rawSENode -> {
					ProdOrderReportItem prodOrderReportItem = (ProdOrderReportItem) rawSENode;
					ServiceEntityManager refDocumentManager = serviceDocumentComProxy
							.getDocumentManager(prodOrderReportItem
									.getNextDocType());
					if (refDocumentManager == null) {
						return null;
					}
					ServiceEntityNode documentItemNode = null;
					try {
						String targetNodeName = serviceDocumentComProxy
								.getDocumentMaterialItemNodeName(prodOrderReportItem
										.getNextDocType());
						documentItemNode = refDocumentManager
								.getEntityNodeByKey(prodOrderReportItem
										.getNextDocMatItemUUID(),
										IServiceEntityNodeFieldConstant.UUID,
										targetNodeName, prodOrderReportItem
												.getClient(), null);
						return documentItemNode;
					} catch (ServiceEntityConfigureException e) {
						return null;
					}
				});
		uiModelNodeMapList.add(nextMatItemMap);

		prodOrderReportItemExtensionUnion
				.setUiModelNodeMapList(uiModelNodeMapList);
		resultList.add(prodOrderReportItemExtensionUnion);
		return resultList;
	}

}
