package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialTemplateItemUIModel;
import com.company.IntelligentPlatform.production.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocumentContentSpecifier;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceCommonDataFormatter;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class BillOfMaterialTemplateItemManager {

	public static final String METHOD_ConvProdWorkCenterToItemUI = "convProdWorkCenterToItemUI";

	public static final String METHOD_ConvBillOfMaterialTemplateItemToUI = "convBillOfMaterialTemplateItemToUI";

	public static final String METHOD_ConvUIToBillOfMaterialTemplateItem = "convUIToBillOfMaterialTemplateItem";

	public static final String METHOD_ConvSubBomTemplateToItemUI = "convSubBOMToItemUI";

	public static final String METHOD_ConvItemMaterialToUI = "convItemMaterialToUI";

	public static final String MEMTHOD_ConvParentBOMTemplateToUI = "convParentBOMTemplateToUI";

	public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";

	public static final String METHOD_ConvProcessRouteProcessItemToUI = "convProcessRouteProcessItemToUI";

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProcessRouteOrderManager processRouteOrderManager;

	@Autowired
	protected ProdProcessManager prodProcessManager;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;
	
	@Autowired
	protected BillOfMaterialTemplateManager billOfMaterialTemplateManager;
	
	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected BillOfMaterialTemplateSpecifier billOfMaterialTemplateSpecifier;

	@Autowired
	protected DocFlowProxy docFlowProxy;
	
	protected Logger logger = LoggerFactory.getLogger(BillOfMaterialTemplateItemManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), BillOfMaterialTemplate.NODENAME,
						request.getUuid(), BillOfMaterialTemplateItem.NODENAME, billOfMaterialTemplateManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<BillOfMaterialTemplate>) billOfMaterialTemplate -> {
            // How to get the base page header model list
            return docPageHeaderModelProxy.getDocPageHeaderModelList(billOfMaterialTemplate,
                            IServiceEntityNodeFieldConstant.ID);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<BillOfMaterialTemplateItem>) (billOfMaterialTemplateItem, pageHeaderModel) ->
				docPageHeaderModelProxy.getDefDocMaterialItemPageHeaderModel(billOfMaterialTemplateItem, pageHeaderModel));
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}

	public BillOfMaterialTemplateItem newBOMTemplateItemFromParent(String baseUUID, String client) throws ServiceEntityConfigureException, DocActionException {
		return billOfMaterialTemplateSpecifier.newEntityNodeFromBaseUUID(new DocumentContentSpecifier.
				InitSubServiceEntityRequest<>(BillOfMaterialTemplate.NODENAME, BillOfMaterialTemplateItem.NODENAME, baseUUID, (parentNode, parentItem, newItem) -> {
			BillOfMaterialTemplate billOfMaterialTemplate = (BillOfMaterialTemplate) parentNode;
			int layer = 1;
			String refParentItemUUID = ServiceEntityStringHelper.EMPTYSTRING;
			if (parentItem != null) {
				layer = parentItem.getLayer() + 1;
				refParentItemUUID = parentItem.getUuid();
			}
			String defId = billOfMaterialTemplateManager
					.newBomItemId(billOfMaterialTemplate);
			newItem.setId(defId);
			newItem.setRefParentItemUUID(refParentItemUUID);
			newItem.setLayer(layer);
			return newItem;
		}), client);
	}
	
	public void convBillOfMaterialTemplateItemToUI(
			BillOfMaterialTemplateItem billOfMaterialTemplateItem,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel)
			throws ServiceEntityInstallationException {
		convBillOfMaterialTemplateItemToUI(billOfMaterialTemplateItem, billOfMaterialTemplateItemUIModel, null);
	}

	public void convBillOfMaterialTemplateItemToUI(
			BillOfMaterialTemplateItem billOfMaterialTemplateItem,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		if (billOfMaterialTemplateItem != null) {
			docFlowProxy.convDocMatItemToUI(billOfMaterialTemplateItem, billOfMaterialTemplateItemUIModel, logonInfo);
			billOfMaterialTemplateItemUIModel.setId(billOfMaterialTemplateItem.getId());
			billOfMaterialTemplateItemUIModel.setNote(billOfMaterialTemplateItem.getNote());
			try {
				String refUnitName = materialStockKeepUnitManager
						.getRefUnitName(
								billOfMaterialTemplateItem.getRefMaterialSKUUUID(),
								billOfMaterialTemplateItem.getRefUnitUUID(),
								billOfMaterialTemplateItem.getClient());
				billOfMaterialTemplateItemUIModel.setRefUnitName(refUnitName);
			} catch (MaterialException | ServiceEntityConfigureException e) {
				// just skip
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
			}
            if(logonInfo != null){
				Map<Integer, String> statusMap =
						billOfMaterialTemplateManager.initStatusMap(logonInfo.getLanguageCode());
				billOfMaterialTemplateItemUIModel.setItemStatusValue(statusMap
						.get(billOfMaterialTemplateItem.getItemStatus()));
			}
			billOfMaterialTemplateItemUIModel.setLayer(billOfMaterialTemplateItem.getLayer());
			billOfMaterialTemplateItemUIModel.setRefParentItemUUID(billOfMaterialTemplateItem
					.getRefParentItemUUID());
			billOfMaterialTemplateItemUIModel.setItemCategory(billOfMaterialTemplateItem
					.getItemCategory());
			billOfMaterialTemplateItemUIModel.setLeadTimeOffset(billOfMaterialTemplateItem
					.getLeadTimeOffset());
			billOfMaterialTemplateItemUIModel.setTheoLossRate(billOfMaterialTemplateItem
					.getTheoLossRate());
			String theoLossRateValue = ServiceCommonDataFormatter
					.formatPercentageValue(billOfMaterialTemplateItem
							.getTheoLossRate()/100);
			billOfMaterialTemplateItemUIModel.setTheoLossRateValue(theoLossRateValue);
			billOfMaterialTemplateItemUIModel.setRefSubBOMUUID(billOfMaterialTemplateItem
					.getRefSubBOMUUID());
			billOfMaterialTemplateItemUIModel.setRefWocUUID(billOfMaterialTemplateItem.getRefWocUUID());
			Map<Integer, String> categoryMap = serviceDropdownListHelper
					.getUIDropDownMap(MaterialUIModel.class, "materialCategory");
			billOfMaterialTemplateItemUIModel.setItemCategoryValue(categoryMap
					.get(billOfMaterialTemplateItem.getItemCategory()));
		}
	}

	public void convUIToBillOfMaterialTemplateItem(BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel, BillOfMaterialTemplateItem rawEntity
			) {
		docFlowProxy.convUIToDocMatItem(billOfMaterialTemplateItemUIModel, rawEntity);
		rawEntity.setId(billOfMaterialTemplateItemUIModel.getId());
		rawEntity.setNote(billOfMaterialTemplateItemUIModel.getNote());
		rawEntity.setLayer(billOfMaterialTemplateItemUIModel.getLayer());
		rawEntity.setRefParentItemUUID(billOfMaterialTemplateItemUIModel.getRefParentItemUUID());
		if (!ServiceEntityStringHelper
				.checkNullString(billOfMaterialTemplateItemUIModel.getRefMaterialSKUUUID())) {
			rawEntity.setRefMaterialSKUUUID(billOfMaterialTemplateItemUIModel.getRefMaterialSKUUUID());
		}
		rawEntity.setLeadTimeOffset(billOfMaterialTemplateItemUIModel
				.getLeadTimeOffset());
		// Check loss rate is valid
		rawEntity.setTheoLossRate(calculateValidLossRate(billOfMaterialTemplateItemUIModel.getTheoLossRate()));
		rawEntity
				.setRefSubBOMUUID(billOfMaterialTemplateItemUIModel.getRefSubBOMUUID());
		rawEntity.setRefWocUUID(billOfMaterialTemplateItemUIModel.getRefWocUUID());
		rawEntity.setRefRouteProcessItemUUID(billOfMaterialTemplateItemUIModel.getRefRouteProcessItemUUID());
	}
	
	/**
	 * Calculate valid loss rate from raw loss rate
	 * @param lossRate
	 * @return
	 */
	public static double calculateValidLossRate(double lossRate){
		if(lossRate < 0){
			return 0;
		}
		if(lossRate > 100){
			return 100;
		}
		return lossRate;
	}

	public void convSubBOMToItemUI(BillOfMaterialTemplate subBOM,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		if (subBOM != null) {
			billOfMaterialTemplateItemUIModel.setRefSubBOMId(subBOM.getId());
		}		
	}

	public void convProcessRouteProcessItemToUI(
			ProcessRouteProcessItem processRouteProcessItem,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		if (processRouteProcessItem != null) {
			billOfMaterialTemplateItemUIModel
					.setRefRouteProcessIndex(processRouteProcessItem
							.getProcessIndex());
			billOfMaterialTemplateItemUIModel
					.setRefRouteProcessItemUUID(processRouteProcessItem
							.getUuid());
		}
	}

	public void convProdProcessToUI(ProdProcess prodProcess,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		if (prodProcess != null) {
			billOfMaterialTemplateItemUIModel.setRefProdProcessId(prodProcess.getId());
			billOfMaterialTemplateItemUIModel.setRefProdProcessName(prodProcess
					.getName());
		}
	}

	/**
	 * Compound convert bill of material item to UI Model
	 * 
	 * @param billOfMaterialTemplateItem
	 * @param billOfMaterialTemplateItemUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void convComProcessItemToUI(BillOfMaterialTemplateItem billOfMaterialTemplateItem,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel)
			throws ServiceEntityConfigureException {
		if (billOfMaterialTemplateItem != null && billOfMaterialTemplateItemUIModel != null) {
			if (billOfMaterialTemplateItem.getRefRouteProcessItemUUID() == null
					|| billOfMaterialTemplateItem.getRefRouteProcessItemUUID().equals(
							ServiceEntityStringHelper.EMPTYSTRING)) {
				return;
			}
			ProcessRouteProcessItem processRouteProcessItem = (ProcessRouteProcessItem) processRouteOrderManager
					.getEntityNodeByKey(
							billOfMaterialTemplateItem.getRefRouteProcessItemUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProcessRouteProcessItem.NODENAME,
							billOfMaterialTemplateItem.getClient(), null);
			ProdProcess prodProcess = (ProdProcess) prodProcessManager
					.getEntityNodeByKey(processRouteProcessItem.getRefUUID(),
							IServiceEntityNodeFieldConstant.UUID,
							ProdProcess.NODENAME,
							billOfMaterialTemplateItem.getClient(), null);
			convProcessRouteProcessItemToUI(processRouteProcessItem,
					billOfMaterialTemplateItemUIModel);
			convProdProcessToUI(prodProcess, billOfMaterialTemplateItemUIModel);
		}
	}

	public void convParentBOMTemplateToUI(BillOfMaterialTemplate billOfMaterialTemplate,
									   BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		convParentBOMTemplateToUI(billOfMaterialTemplate, billOfMaterialTemplateItemUIModel, null);
	}

	public void convParentBOMTemplateToUI(BillOfMaterialTemplate billOfMaterialTemplate,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel, LogonInfo logonInfo) {
		if (billOfMaterialTemplate != null) {
			billOfMaterialTemplateItemUIModel.setParentItemId(billOfMaterialTemplate
					.getId());
			billOfMaterialTemplateItemUIModel.setParentDocId(billOfMaterialTemplate
					.getId());
			billOfMaterialTemplateItemUIModel.setParentDocName(billOfMaterialTemplate
					.getName());
			// TODO remove this field status
			billOfMaterialTemplateItemUIModel.setItemStatus(billOfMaterialTemplate.getStatus());
			billOfMaterialTemplateItemUIModel.setParentDocStatus(billOfMaterialTemplate.getStatus());
			if(logonInfo != null){
				try {
					// TODO remove this field status
					Map<Integer, String> statusMap = billOfMaterialTemplateManager.initStatusMap(logonInfo.getLanguageCode());
					billOfMaterialTemplateItemUIModel.setItemStatusValue(statusMap.get(billOfMaterialTemplate.getStatus()));
					billOfMaterialTemplateItemUIModel.setParentDocStatusValue(statusMap.get(billOfMaterialTemplate.getStatus()));
				} catch (ServiceEntityInstallationException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "status"));
				}
			}
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(
								billOfMaterialTemplate.getRefMaterialSKUUUID(),
								billOfMaterialTemplate.getRefUnitUUID(),
								billOfMaterialTemplate.getAmount(),
								billOfMaterialTemplate.getClient());
				billOfMaterialTemplateItemUIModel.setParentItemAmountLabel(amountLabel);
			} catch (MaterialException | ServiceEntityConfigureException e) {
				// just skip
			}	
		}
	}

	public void convItemMaterialToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		convItemMaterialToUI(materialStockKeepUnit, billOfMaterialTemplateItemUIModel, null);
	}

	public void convItemMaterialToUI(
			MaterialStockKeepUnit materialStockKeepUnit,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel, LogonInfo logonInfo) {
		if (materialStockKeepUnit != null) {
			docFlowProxy.convMaterialSKUToItemUI(materialStockKeepUnit, billOfMaterialTemplateItemUIModel);
			billOfMaterialTemplateItemUIModel.setFixLeadTime(materialStockKeepUnit
					.getFixLeadTime());
			billOfMaterialTemplateItemUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
			if(logonInfo != null){
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					billOfMaterialTemplateItemUIModel.setSupplyTypeValue(supplyTypeMap.get(materialStockKeepUnit.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// Log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"));
				}
			}
			
		}
	}

	public void convProdWorkCenterToItemUI(ProdWorkCenter prodWorkCenter,
										   BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		if (prodWorkCenter != null) {
			billOfMaterialTemplateItemUIModel.setRefWocName(prodWorkCenter.getName());
			billOfMaterialTemplateItemUIModel.setRefWocId(prodWorkCenter.getId());
		}
	}

	public void convParentItemMaterialToUI(
			MaterialStockKeepUnit parentItemMaterial,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		if (parentItemMaterial != null) {
			billOfMaterialTemplateItemUIModel
					.setParentItemMaterialName(parentItemMaterial.getName());
			billOfMaterialTemplateItemUIModel
					.setParentItemMaterialSKUId(parentItemMaterial.getId());
			billOfMaterialTemplateItemUIModel
					.setParentItemMaterialSKUName(parentItemMaterial.getName());
		}
	}

	public void convUIToParentItemMaterial(MaterialStockKeepUnit rawEntity,
			BillOfMaterialTemplateItemUIModel billOfMaterialTemplateItemUIModel) {
		rawEntity
				.setName(billOfMaterialTemplateItemUIModel.getParentItemMaterialName());
		rawEntity.setId(billOfMaterialTemplateItemUIModel.getParentItemMaterialSKUId());
	}

}
