package com.company.IntelligentPlatform.production.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalUIModel;
import com.company.IntelligentPlatform.production.dto.ProdPlanItemReqProposalServiceUIModelExtension;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemUIModel;
import com.company.IntelligentPlatform.production.dto.ProductionPlanItemServiceUIModelExtension;
import com.company.IntelligentPlatform.production.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.service.StorageCoreUnit;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.ServiceDocumentExtendUIModel;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceDocumentComProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.IDocumentNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityCommonFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;


@Service
public class ProductionPlanItemManager {

	public static final String METHOD_ConvProductionPlanItemToUI = "convProductionPlanItemToUI";

	public static final String METHOD_ConvUIToProductionPlanItem = "convUIToProductionPlanItem";

	public static final String METHOD_ConvProductionPlanToItemUI = "convProductionPlanToItemUI";

	public static final String METHOD_ConvPlanMaterialSKUToItemUI = "convPlanMaterialSKUToItemUI";

	public static final String METHOD_ConvItemMaterialSKUToUI = "convItemMaterialSKUToUI";

	public static final String METHOD_ConvProdPlanItemReqProposalToUI = "convProdPlanItemReqProposalToUI";

	public static final String METHOD_ConvUIToProdPlanItemReqProposal = "convUIToProdPlanItemReqProposal";

	public static final String METHOD_ConvProductionPlanToProposalUI = "convProductionPlanToProposalUI";

	public static final String METHOD_ConvProductionPlanItemToProposalUI = "convProductionPlanItemToProposalUI";

	@Autowired
	protected ProductionPlanConfigureProxy productionPlanConfigureProxy;

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected ProductionPlanManager productionPlanManager;

	@Autowired
	protected ProdPlanItemReqProposalManager prodPlanItemReqProposalManager;

	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected BillOfMaterialOrderManager billOfMaterialOrderManager;

	@Autowired
	protected ServiceDocumentComProxy serviceDocumentComProxy;

	@Autowired
	protected ProdPickingRefMaterialItemManager prodPickingRefMaterialItemManager;

	@Autowired
	protected ProductionPlanItemServiceUIModelExtension productionPlanItemServiceUIModelExtension;

	@Autowired
	protected ProdPlanItemReqProposalServiceUIModelExtension prodPlanItemReqProposalServiceUIModelExtension;

	@Autowired
	protected ProductionOrderItemManager productionOrderItemManager;

	@Autowired
	protected ProductionOrderManager productionOrderManager;

	@Autowired
	protected ProdPickingOrderManager prodPickingOrderManager;

	@Autowired
	protected ProdOrderItemReqProposalManager prodOrderItemReqProposalManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	@Autowired
	protected DocFlowProxy docFlowProxy;

	protected Logger logger = LoggerFactory.getLogger(ProductionPlanItemManager.class);

	Map<String, Map<Integer, String>> itemStatusMapLan = new HashMap<String, Map<Integer, String>>();

	public Map<Integer, String> initItemStatusMap(String languageCode) throws ServiceEntityInstallationException {
		return productionOrderItemManager.initItemStatusMap(languageCode);
	}

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ProductionPlan.NODENAME,
						request.getUuid(), ProductionPlanItem.NODENAME, productionPlanManager);
		docPageHeaderInputPara.setGenBaseModelList(
				(DocPageHeaderModelProxy.GenBaseModelList<ProductionPlan>) productionPlan -> {
					// How to get the base page header model list
					List<PageHeaderModel> pageHeaderModelList =
							docPageHeaderModelProxy.getDocPageHeaderModelList(productionPlan,  null);
					return pageHeaderModelList;
				});
		docPageHeaderInputPara.setGenHomePageModel(
				(DocPageHeaderModelProxy.GenHomePageModel<ProductionPlanItem>) (billOfMaterialItem, pageHeaderModel) -> {
					// How to render current page header
					MaterialStockKeepUnit materialStockKeepUnit = null;
					try {
						materialStockKeepUnit = materialStockKeepUnitManager
								.getMaterialSKUWrapper(
										billOfMaterialItem.getRefMaterialSKUUUID(),
										billOfMaterialItem.getClient(), null);
					} catch (ServiceComExecuteException e) {
						logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
					}
					if (materialStockKeepUnit != null) {
						pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
					}
					return pageHeaderModel;
				});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}


//	public List<PageHeaderModel> getPageHeaderModelList(ProductionPlanItem productionPlanItem, String client)
//			throws ServiceEntityConfigureException {
//		ProductionPlan productionPlan = (ProductionPlan) productionPlanManager
//				.getEntityNodeByKey(productionPlanItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
//						ProductionPlan.NODENAME, client, null);
//		int index = 0;
//		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
//		if (productionPlan != null) {
//			// In case current production order item is the direct production
//			// item for order
//			PageHeaderModel orderHeaderModel = productionPlanManager.getPageHeaderModel(productionPlan, index);
//			if (orderHeaderModel != null) {
//				index++;
//				resultList.add(orderHeaderModel);
//			}
//			PageHeaderModel itemHeaderModel = getPageHeaderModel(productionPlanItem, index);
//			if (itemHeaderModel != null) {
//				resultList.add(itemHeaderModel);
//			}
//		} else {
//			ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) productionPlanManager
//					.getEntityNodeByKey(productionPlanItem.getParentNodeUUID(), IServiceEntityNodeFieldConstant.UUID,
//							ProdPlanItemReqProposal.NODENAME, client, null);
//			// In case current production order item is the sub item for
//			// proposal
//			if (prodPlanItemReqProposal != null) {
//				List<PageHeaderModel> pageHeaderModelList = prodPlanItemReqProposalManager
//						.getPageHeaderModelList(prodPlanItemReqProposal, client);
//				if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
//					resultList.addAll(pageHeaderModelList);
//					index = pageHeaderModelList.size();
//				}
//				PageHeaderModel itemHeaderModel = getPageHeaderModel(productionPlanItem, index);
//				if (itemHeaderModel != null) {
//					resultList.add(itemHeaderModel);
//				}
//			}
//		}
//		return resultList;
//	}
//
//	protected PageHeaderModel getPageHeaderModel(ProductionPlanItem productionPlanItem, int index)
//			throws ServiceEntityConfigureException {
//		if (productionPlanItem == null) {
//			return null;
//		}
//		PageHeaderModel pageHeaderModel = new PageHeaderModel();
//		pageHeaderModel.setPageTitle("productionPlanItemTitle");
//		pageHeaderModel.setNodeInstId(ProductionPlanItem.NODENAME);
//		pageHeaderModel.setUuid(productionPlanItem.getUuid());
//		MaterialStockKeepUnit materialStockKeepUnit = (MaterialStockKeepUnit) materialStockKeepUnitManager
//				.getEntityNodeByKey(productionPlanItem.getRefMaterialSKUUUID(), IServiceEntityNodeFieldConstant.UUID,
//						MaterialStockKeepUnit.NODENAME, productionPlanItem.getClient(), null);
//		if (materialStockKeepUnit != null) {
//			pageHeaderModel.setHeaderName(materialStockKeepUnit.getName());
//		}
//		pageHeaderModel.setIndex(index);
//		return pageHeaderModel;
//	}

	public void convProductionPlanItemToUI(ProductionPlanItem productionPlanItem,
			ProductionPlanItemUIModel productionPlanItemUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		convProductionPlanItemToUI(productionPlanItem, productionPlanItemUIModel, null);
	}

	public void convProductionPlanItemToUI(ProductionPlanItem productionPlanItem,
			ProductionPlanItemUIModel productionPlanItemUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		if (productionPlanItem != null) {
			docFlowProxy.convDocMatItemToUI(productionPlanItem, productionPlanItemUIModel, logonInfo);
			productionPlanItemUIModel.setId(productionPlanItem.getId());
			productionPlanItemUIModel.setParentNodeUUID(productionPlanItem.getParentNodeUUID());
			productionPlanItemUIModel.setNote(productionPlanItem.getNote());
			productionPlanItemUIModel.setRefMaterialSKUUUID(productionPlanItem.getRefMaterialSKUUUID());
			productionPlanItemUIModel.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(productionPlanItem.getAmount()));
			productionPlanItemUIModel.setAmountWithLossRate(productionPlanItem.getAmountWithLossRate());
			productionPlanItemUIModel.setItemPrice(productionPlanItem.getItemPrice());
			productionPlanItemUIModel.setUnitPrice(productionPlanItem.getUnitPrice());
			productionPlanItemUIModel.setActualAmount(productionPlanItem.getActualAmount());
			productionPlanItemUIModel.setRefUnitUUID(productionPlanItem.getRefUnitUUID());
			productionPlanItemUIModel.setRefUnitName(productionPlanItem.getRefUnitUUID());
			productionPlanItemUIModel.setSelfLeadTime(productionPlanItem.getSelfLeadTime());
			productionPlanItemUIModel.setComLeadTime(productionPlanItem.getComLeadTime());
			if (productionPlanItem.getPlanStartPrepareDate() != null) {
				productionPlanItemUIModel.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlanItem.getPlanStartPrepareDate()));
			}
			if (productionPlanItem.getPlanStartDate() != null) {
				productionPlanItemUIModel
						.setPlanStartDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlanItem.getPlanStartDate()));
			}
			if (productionPlanItem.getPlanEndDate() != null) {
				productionPlanItemUIModel
						.setPlanEndDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(productionPlanItem.getPlanEndDate()));
			}
			productionPlanItemUIModel.setItemStatus(productionPlanItem.getItemStatus());
			productionPlanItemUIModel.setAvailableAmount(productionPlanItem.getAvailableAmount());
			productionPlanItemUIModel.setInStockAmount(productionPlanItem.getInStockAmount());
			productionPlanItemUIModel.setToPickAmount(productionPlanItem.getToPickAmount());
			productionPlanItemUIModel.setInProcessAmount(productionPlanItem.getInProcessAmount());
			productionPlanItemUIModel.setSuppliedAmount(productionPlanItem.getSuppliedAmount());
			productionPlanItemUIModel.setLackInPlanAmount(productionPlanItem.getLackInPlanAmount());
			productionPlanItemUIModel.setPickedAmount(productionPlanItem.getPickedAmount());
			productionPlanItemUIModel.setPickStatus(productionPlanItem.getPickStatus());
			if (logonInfo != null) {
				Map<Integer, String> itemStatusMap = this.initItemStatusMap(logonInfo.getLanguageCode());
				productionPlanItemUIModel.setItemStatusValue(itemStatusMap.get(productionPlanItem.getItemStatus()));
				Map<Integer, String> statusMap = prodPickingOrderManager.initStatusMap(logonInfo.getLanguageCode());
				productionPlanItemUIModel.setPickStatusValue(statusMap.get(productionPlanItem.getPickStatus()));
			}
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setAmountLabel(amountLabel);

				String actualAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getActualAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setActualAmountLabel(actualAmountLabel);
				String amountLossRateLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getAmountWithLossRate(), productionPlanItem.getClient());
				productionPlanItemUIModel.setAmountWithLossRateLabel(amountLossRateLabel);
				String inProcessAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getInProcessAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setInProcessAmountLabel(inProcessAmountLabel);

				String inStockAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getInStockAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setInStockAmountLabel(inStockAmountLabel);

				String toPickAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getToPickAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setToPickAmountLabel(toPickAmountLabel);

				String pickedAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getPickedAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setPickedAmountLabel(pickedAmountLabel);

				String lackInPlanLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getLackInPlanAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setLackInPlanAmountLabel(lackInPlanLabel);

				String availableAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getAvailableAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setAvailableAmountLabel(availableAmountLabel);

				String suppliedAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlanItem.getRefMaterialSKUUUID(), productionPlanItem.getRefUnitUUID(),
								productionPlanItem.getSuppliedAmount(), productionPlanItem.getClient());
				productionPlanItemUIModel.setSuppliedAmountLabel(suppliedAmountLabel);

			} catch (MaterialException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, productionPlanItem.getUuid()), e);
			}
			productionPlanItemUIModel.setRefBOMItemUUID(productionPlanItem.getRefBOMItemUUID());
		}
	}

	public void convProductionPlanToItemUI(ProductionPlan productionPlan, ProductionPlanItemUIModel productionPlanItemUIModel) {
		if (productionPlan != null) {
			productionPlanItemUIModel.setPlanId(productionPlan.getId());
			productionPlanItemUIModel.setPlanPlanStartTime(productionPlan.getPlanStartTime() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(productionPlan.getPlanStartTime().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			productionPlanItemUIModel.setPlanPlanEndTime(productionPlan.getPlanEndTime() != null ? DefaultDateFormatConstant.DATE_FORMAT.format(java.util.Date.from(productionPlan.getPlanEndTime().atZone(java.time.ZoneId.systemDefault()).toInstant())) : null);
			try {
				String amountLabel = materialStockKeepUnitManager
						.getAmountLabel(productionPlan.getRefMaterialSKUUUID(), productionPlan.getRefUnitUUID(),
								productionPlan.getAmount(), productionPlan.getClient());
				productionPlanItemUIModel.setPlanAmountLabel(amountLabel);
			} catch (MaterialException e) {
				// just skip
			} catch (ServiceEntityConfigureException e) {
				// just skip
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to se model:productionPlanItem
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProductionPlanItem(ProductionPlanItemUIModel productionPlanItemUIModel, ProductionPlanItem rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getUuid())) {
			rawEntity.setUuid(productionPlanItemUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(productionPlanItemUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(productionPlanItemUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getClient())) {
			rawEntity.setClient(productionPlanItemUIModel.getClient());
		}
		rawEntity.setActualAmount(productionPlanItemUIModel.getActualAmount());
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getPlanStartPrepareDate())) {
			try {
				rawEntity.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanItemUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setAmountWithLossRate(productionPlanItemUIModel.getAmountWithLossRate());
		rawEntity.setComLeadTime(productionPlanItemUIModel.getComLeadTime());
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getPlanStartDate())) {
			try {
				rawEntity.setPlanStartDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanItemUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getActualStartDate())) {
			try {
				rawEntity.setActualStartDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanItemUIModel.getActualStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setRefBOMItemUUID(productionPlanItemUIModel.getRefBOMItemUUID());
		rawEntity.setRefMaterialSKUUUID(productionPlanItemUIModel.getRefMaterialSKUUUID());
		rawEntity.setNote(productionPlanItemUIModel.getNote());
		rawEntity.setId(productionPlanItemUIModel.getId());
		rawEntity.setRefUnitUUID(productionPlanItemUIModel.getRefUnitUUID());
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getActualEndDate())) {
			try {
				rawEntity.setActualEndDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanItemUIModel.getActualEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setSelfLeadTime(productionPlanItemUIModel.getSelfLeadTime());
		rawEntity.setClient(productionPlanItemUIModel.getClient());
		rawEntity.setAmount(productionPlanItemUIModel.getAmount());
		if (!ServiceEntityStringHelper.checkNullString(productionPlanItemUIModel.getPlanEndDate())) {
			try {
				rawEntity.setPlanEndDate(DefaultDateFormatConstant.DATE_FORMAT.parse(productionPlanItemUIModel.getPlanEndDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convItemMaterialSKUToUI(MaterialStockKeepUnit itemMaterialSKU, ProductionPlanItemUIModel productionPlanItemUIModel,
										LogonInfo logonInfo) {
		if (itemMaterialSKU != null) {
			productionPlanItemUIModel.setRefMaterialSKUName(itemMaterialSKU.getName());
			productionPlanItemUIModel.setRefMaterialSKUId(itemMaterialSKU.getId());

			productionPlanItemUIModel.setPackageStandard(itemMaterialSKU.getPackageStandard());
			productionPlanItemUIModel.setSupplyType(itemMaterialSKU.getSupplyType());
			if (logonInfo != null) {
				try {
					Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
					productionPlanItemUIModel.setSupplyTypeValue(supplyTypeMap.get(itemMaterialSKU.getSupplyType()));
				} catch (ServiceEntityInstallationException e) {
					// log error and continue
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
			}
		}
	}


	public void convPlanMaterialSKUToItemUI(MaterialStockKeepUnit materialStockKeepUnit,
			ProductionPlanItemUIModel productionPlanItemUIModel) {
		if (materialStockKeepUnit != null) {
			productionPlanItemUIModel.setPlanMaterialSKUId(materialStockKeepUnit.getId());
			productionPlanItemUIModel.setPlanMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convMaterialStockKeepUnitToUI(MaterialStockKeepUnit materialStockKeepUnit,
			ProductionPlanItemUIModel productionPlanItemUIModel) {
		if (materialStockKeepUnit != null) {
			productionPlanItemUIModel.setRefMaterialSKUId(materialStockKeepUnit.getId());
			productionPlanItemUIModel.setRefMaterialSKUName(materialStockKeepUnit.getName());
		}
	}

	public void convProductionPlanToProposalUI(ProductionPlan productionPlan,
			ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		prodPlanItemReqProposalUIModel.setPlanId(productionPlan.getId());
		prodPlanItemReqProposalUIModel.setPlanStatus(productionPlan.getStatus());
		try {
			if (logonInfo != null) {
				Map<Integer, String> statusMap = productionPlanManager.initStatusMap(logonInfo.getLanguageCode());
				prodPlanItemReqProposalUIModel.setPlanStatusValue(statusMap.get(productionPlan.getStatus()));
			}
		} catch (ServiceEntityInstallationException e) {
			// log error and continue
			logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, IDocumentNodeFieldConstant.STATUS), e);
		}
	}

	public void convProductionPlanItemToProposalUI(ProductionPlanItem productionPlanItem,
			ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		prodPlanItemReqProposalUIModel.setParentItemId(productionPlanItem.getId());
	}

	/**
	 * [Internal method] Convert from SE model to UI model
	 *
	 * @return <code>SEUIComModel</code> instance
	 * @throws ServiceEntityInstallationException
	 * @throws ServiceEntityConfigureException
	 */
	public void convProdPlanItemReqProposalToUI(ProdPlanItemReqProposal prodPlanItemReqProposal,
			ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException, ServiceEntityConfigureException {
		if (prodPlanItemReqProposal != null) {
			docFlowProxy.convDocMatItemToUI(prodPlanItemReqProposal, prodPlanItemReqProposalUIModel, logonInfo);
			prodPlanItemReqProposalUIModel.setRefUUID(prodPlanItemReqProposal.getRefUUID());
			if (prodPlanItemReqProposal.getPlanStartPrepareDate() != null) {
				prodPlanItemReqProposalUIModel.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPlanItemReqProposal.getPlanStartPrepareDate()));
			}
			if (prodPlanItemReqProposal.getPlanStartDate() != null) {
				prodPlanItemReqProposalUIModel
						.setPlanStartDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPlanItemReqProposal.getPlanStartDate()));
			}
			if (prodPlanItemReqProposal.getPlanEndDate() != null) {
				prodPlanItemReqProposalUIModel
						.setPlanEndDate(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPlanItemReqProposal.getPlanEndDate()));
			}
			if (prodPlanItemReqProposal.getActualStartDate() != null) {
				prodPlanItemReqProposalUIModel.setActualStartDate(
						DefaultDateFormatConstant.DATE_MIN_FORMAT.format(prodPlanItemReqProposal.getActualStartDate()));
			}

			prodPlanItemReqProposalUIModel.setSelfLeadTime(prodPlanItemReqProposal.getSelfLeadTime());
			prodPlanItemReqProposal.setComLeadTime(prodPlanItemReqProposal.getComLeadTime());
			prodPlanItemReqProposalUIModel.setStatus(prodPlanItemReqProposal.getItemStatus());
			prodPlanItemReqProposalUIModel.setRefUnitUUID(prodPlanItemReqProposal.getRefUnitUUID());
			prodPlanItemReqProposalUIModel.setRefMaterialSKUUUID(prodPlanItemReqProposal.getRefMaterialSKUUUID());
			prodPlanItemReqProposalUIModel.setRefWarehouseUUID(prodPlanItemReqProposal.getRefWarehouseUUID());
			prodPlanItemReqProposalUIModel.setRefBOMItemUUID(prodPlanItemReqProposal.getRefBOMItemUUID());
			if (prodPlanItemReqProposal.getPlanStartDate() != null) {
				prodPlanItemReqProposalUIModel
						.setPlanStartDate(DefaultDateFormatConstant.DATE_FORMAT.format(prodPlanItemReqProposal.getPlanStartDate()));
			}
			prodPlanItemReqProposalUIModel
					.setAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodPlanItemReqProposal.getAmount()));
			if (prodPlanItemReqProposal.getActualStartPrepareDate() != null) {
				prodPlanItemReqProposalUIModel.setActualStartPrepareDate(
						DefaultDateFormatConstant.DATE_FORMAT.format(prodPlanItemReqProposal.getActualStartPrepareDate()));
			}
			if (logonInfo != null) {
				Map<Integer, String> itemStatusMap = this.initItemStatusMap(logonInfo.getLanguageCode());
				prodPlanItemReqProposalUIModel.setItemStatusValue(itemStatusMap.get(prodPlanItemReqProposal.getItemStatus()));
			}
			prodPlanItemReqProposalUIModel.setItemStatus(prodPlanItemReqProposal.getItemStatus());
			prodPlanItemReqProposalUIModel.setDocumentType(prodPlanItemReqProposal.getDocumentType());
			if (logonInfo != null) {
				Map<Integer, String> documentTypeMap = productionPlanManager.initDocumentTypeMap(logonInfo.getLanguageCode());
				prodPlanItemReqProposalUIModel.setDocumentTypeValue(documentTypeMap.get(prodPlanItemReqProposal.getDocumentType()));
			}
			prodPlanItemReqProposalUIModel.setComLeadTime(prodPlanItemReqProposal.getComLeadTime());
			prodPlanItemReqProposalUIModel.setItemIndex(prodPlanItemReqProposal.getItemIndex());
			prodPlanItemReqProposalUIModel
					.setInProcessAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodPlanItemReqProposal.getInProcessAmount()));
			prodPlanItemReqProposalUIModel
					.setInStockAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodPlanItemReqProposal.getInStockAmount()));
			prodPlanItemReqProposalUIModel
					.setPickedAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodPlanItemReqProposal.getPickedAmount()));
			prodPlanItemReqProposalUIModel.setPickStatus(prodPlanItemReqProposal.getPickStatus());
			prodPlanItemReqProposalUIModel
					.setSuppliedAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(prodPlanItemReqProposal.getSuppliedAmount()));
			if (logonInfo != null) {
				Map<Integer, String> pickStatusMap = prodPickingOrderManager.initStatusMap(logonInfo.getLanguageCode());
				prodPlanItemReqProposalUIModel.setPickStatusValue(pickStatusMap.get(prodPlanItemReqProposal.getPickStatus()));
			}

			/*
			 * [Step5] Area to calculate all kinds of amount label
			 */
			try {
				String inProcessAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getRefUnitUUID(),
								prodPlanItemReqProposal.getInProcessAmount(), prodPlanItemReqProposal.getClient());
				prodPlanItemReqProposalUIModel.setInProcessAmountLabel(inProcessAmountLabel);

				String inStockAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getRefUnitUUID(),
								prodPlanItemReqProposal.getInStockAmount(), prodPlanItemReqProposal.getClient());
				prodPlanItemReqProposalUIModel.setInStockAmountLabel(inStockAmountLabel);

				String toPickAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getRefUnitUUID(),
								prodPlanItemReqProposal.getToPickAmount(), prodPlanItemReqProposal.getClient());
				prodPlanItemReqProposalUIModel.setToPickAmountLabel(toPickAmountLabel);

				String pickedAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getRefUnitUUID(),
								prodPlanItemReqProposal.getPickedAmount(), prodPlanItemReqProposal.getClient());
				prodPlanItemReqProposalUIModel.setPickedAmountLabel(pickedAmountLabel);

				String suppliedAmountLabel = materialStockKeepUnitManager
						.getAmountLabel(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getRefUnitUUID(),
								prodPlanItemReqProposal.getSuppliedAmount(), prodPlanItemReqProposal.getClient());
				prodPlanItemReqProposalUIModel.setSuppliedAmountLabel(suppliedAmountLabel);

			} catch (MaterialException e) {
				// log error and continue
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, prodPlanItemReqProposal.getUuid()), e);
			}
		}
	}

	/**
	 * [Internal method] Convert from UI model to SE
	 *
	 * @return <code>SEUIComModel</code> instance
	 */
	public void convUIToProdPlanItemReqProposal(ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel,
			ProdPlanItemReqProposal rawEntity) {
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getUuid())) {
			rawEntity.setUuid(prodPlanItemReqProposalUIModel.getUuid());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getParentNodeUUID())) {
			rawEntity.setParentNodeUUID(prodPlanItemReqProposalUIModel.getParentNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getRootNodeUUID())) {
			rawEntity.setRootNodeUUID(prodPlanItemReqProposalUIModel.getRootNodeUUID());
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getClient())) {
			rawEntity.setClient(prodPlanItemReqProposalUIModel.getClient());
		}
		rawEntity.setRefUUID(prodPlanItemReqProposalUIModel.getRefUUID());
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getPlanStartPrepareDate())) {
			try {
				rawEntity.setPlanStartPrepareDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getPlanStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getActualStartDate())) {
			try {
				rawEntity.setActualStartDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getActualStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setSelfLeadTime(prodPlanItemReqProposalUIModel.getSelfLeadTime());
		rawEntity.setRefUnitUUID(prodPlanItemReqProposalUIModel.getRefUnitUUID());
		rawEntity.setRefWarehouseUUID(prodPlanItemReqProposalUIModel.getRefWarehouseUUID());
		rawEntity.setRefMaterialSKUUUID(prodPlanItemReqProposalUIModel.getRefMaterialSKUUUID());
		rawEntity.setRefBOMItemUUID(prodPlanItemReqProposalUIModel.getRefBOMItemUUID());
		rawEntity.setRefUUID(prodPlanItemReqProposalUIModel.getRefUUID());
		if (prodPlanItemReqProposalUIModel.getItemStatus() > 0) {
			rawEntity.setItemStatus(prodPlanItemReqProposalUIModel.getItemStatus());
		}

		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getPlanStartDate())) {
			try {
				rawEntity.setPlanStartDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getPlanStartDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setAmount(prodPlanItemReqProposalUIModel.getAmount());
		if (!ServiceEntityStringHelper.checkNullString(prodPlanItemReqProposalUIModel.getActualStartPrepareDate())) {
			try {
				rawEntity.setActualStartPrepareDate(
						DefaultDateFormatConstant.DATE_FORMAT.parse(prodPlanItemReqProposalUIModel.getActualStartPrepareDate()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
			} catch (ParseException e) {
				// do nothing
			}
		}
		rawEntity.setComLeadTime(prodPlanItemReqProposalUIModel.getComLeadTime());
		rawEntity.setItemIndex(prodPlanItemReqProposalUIModel.getItemIndex());
		if (prodPlanItemReqProposalUIModel.getDocumentType() > 0) {
			rawEntity.setDocumentType(prodPlanItemReqProposalUIModel.getDocumentType());
		}
	}

	public ServiceDocumentExtendUIModel convProdPlanItemReqProposalToDocExtUIModel(
			ProdPlanItemReqProposalUIModel prodProdPlanItemReqProposalUIModel, LogonInfo logonInfo)
			throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		docFlowProxy.convDocMatItemUIToDocExtUIModel(prodProdPlanItemReqProposalUIModel, serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM);
		serviceDocumentExtendUIModel.setRefUIModel(prodProdPlanItemReqProposalUIModel);
		serviceDocumentExtendUIModel.setName(prodProdPlanItemReqProposalUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setStatus(prodProdPlanItemReqProposalUIModel.getItemStatus());
		serviceDocumentExtendUIModel.setStatusValue(prodProdPlanItemReqProposalUIModel.getItemStatusValue());
		String referenceDate = prodProdPlanItemReqProposalUIModel.getPlanStartDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	public ServiceDocumentExtendUIModel convProductionPlanItemToDocExtUIModel(ProductionPlanItemUIModel productionPlanItemUIModel,
			LogonInfo logonInfo) throws ServiceEntityInstallationException {
		ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = new ServiceDocumentExtendUIModel();
		serviceDocumentExtendUIModel.setRefUIModel(productionPlanItemUIModel);
		docFlowProxy.convDocMatItemUIToDocExtUIModel(productionPlanItemUIModel, serviceDocumentExtendUIModel, logonInfo,
				IDefDocumentResource.DOCUMENT_TYPE_PRODUCTPLANITEM);
		serviceDocumentExtendUIModel.setParentNodeUUID(productionPlanItemUIModel.getParentNodeUUID());
		serviceDocumentExtendUIModel.setId(productionPlanItemUIModel.getId());
		serviceDocumentExtendUIModel.setName(productionPlanItemUIModel.getRefMaterialSKUName());
		serviceDocumentExtendUIModel.setStatus(productionPlanItemUIModel.getItemStatus());
		serviceDocumentExtendUIModel.setStatusValue(productionPlanItemUIModel.getItemStatusValue());
		String referenceDate = productionPlanItemUIModel.getPlanStartDate();
		serviceDocumentExtendUIModel.setReferenceDate(referenceDate);
		return serviceDocumentExtendUIModel;
	}

	/**
	 * Entrance method to update production order item
	 *
	 * @param productionPlanItemServiceModel
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void postUpdateProductionPlanItem(ProductionPlanItemServiceModel productionPlanItemServiceModel)
            throws ServiceEntityConfigureException, MaterialException, DocActionException {
		/*
		 * [Step1] Update Info for Production Order Item
		 */
		ProductionPlanItem productionPlanItem = productionPlanItemServiceModel.getProductionPlanItem();
		List<String> warehouseUUIDList = productionOrderManager
				.getWarehouseUUIDList(productionPlanItem.getRootNodeUUID(), productionPlanItem.getClient());
		refreshPlanItemStatus(productionPlanItemServiceModel, warehouseUUIDList);
		/*
		 * [Step2] Update each prodPlanItemReqProposal
		 */
		//		if (!ServiceCollectionsHelper
		//				.checkNullList(productionPlanItemServiceModel
		//						.getProdPlanItemReqProposalList())) {
		//			for (ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel : productionPlanItemServiceModel
		//					.getProdPlanItemReqProposalList()) {
		//				ProdPlanItemReqProposal prodPlanItemReqProposal = prodPlanItemReqProposalServiceModel
		//						.getProdPlanItemReqProposal();
		//				ProdOrderItemReqProposal prodOrderItemReqProposal = prodPlanItemReqProposalManager
		//						.getRelativeOrderItemProposal(prodPlanItemReqProposal);
		//				prodOrderItemReqProposalManager
		//						.refreshItemStatus(prodOrderItemReqProposal);
		//
		//				// Update reference document
		//				prodOrderItemReqProposalManager.updateProposalRefDocInfo(
		//						prodOrderItemReqProposal, null);
		//				updatePlanProposalRefDocInfo(prodOrderItemReqProposal,
		//						prodPlanItemReqProposal);
		//			}
		//		}
	}

	/**
	 * Update proposal reference doc information logic
	 *
	 * @param prodOrderItemReqProposal
	 * @throws MaterialException
	 * @throws ServiceEntityConfigureException
	 */
	public void updatePlanProposalRefDocInfo(ProdOrderItemReqProposal prodOrderItemReqProposal,
			ProdPlanItemReqProposal prodPlanItemReqProposal) throws MaterialException, ServiceEntityConfigureException {
		prodPlanItemReqProposal.setRefUUID(prodOrderItemReqProposal.getRefUUID());
		prodPlanItemReqProposal.setDocumentType(prodOrderItemReqProposal.getDocumentType());
	}

	public List<ServiceEntityNode> getAllEndDocMatItemList(ProdPlanItemReqProposal prodPlanItemReqProposal,
			boolean checkMaterialStatus) throws ServiceEntityConfigureException, DocActionException {
		ProdOrderItemReqProposal prodOrderItemReqProposal = prodPlanItemReqProposalManager
				.getRelativeOrderItemProposal(prodPlanItemReqProposal);
		return prodOrderItemReqProposalManager.getAllEndDocMatItemList(prodOrderItemReqProposal, checkMaterialStatus);
	}

	//	/**
	//	 * Refresh Item status by calculation, call this method only in edit mode,
	//	 * not in list mode
	//	 *
	//	 * @param productionPlanItem
	//	 * @param prodItemPropsalList
	//	 *            : all the sub proposal list under item
	//	 * @throws ServiceEntityConfigureException
	//	 * @throws MaterialException
	//	 */
	//	public void refreshPlanItemStatus(ProductionPlanItem productionPlanItem,
	//			List<String> warehouseUUIDList)
	//			throws ServiceEntityConfigureException, MaterialException {
	//		/*
	//		 * [Step1] Find the relative down-stream production Order item
	//		 */
	//		ProductionOrderItem productionOrderItem = getRelativeOrderItem(productionPlanItem);
	//
	//		/*
	//		 * [Step2] Refresh production order item status
	//		 */
	//		productionOrderItemManager.refreshOrderItemStatus(productionOrderItem,
	//				warehouseUUIDList);
	//		/*
	//		 * [Step3] Copy order item information back to plan item
	//		 */
	//		updateOrderItemToPlanItem(productionOrderItem, productionPlanItem);
	//	}

	/**
	 * Refresh Item status by calculation, call this method only in edit mode,
	 * not in list mode
	 *
	 * @param productionPlanItemServiceModel
	 * @param warehouseUUIDList              : all warehouse UUID list
	 * @throws ServiceEntityConfigureException
	 * @throws MaterialException
	 */
	public void refreshPlanItemStatus(ProductionPlanItemServiceModel productionPlanItemServiceModel,
			List<String> warehouseUUIDList) throws ServiceEntityConfigureException, MaterialException, DocActionException {
		ProductionPlanItem productionPlanItem = productionPlanItemServiceModel.getProductionPlanItem();
		List<ProdPlanItemReqProposalServiceModel> prodPlanItemReqProposalList = productionPlanItemServiceModel
				.getProdPlanItemReqProposalList();
		List<StorageCoreUnit> reservedStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		List<StorageCoreUnit> freeStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		List<StorageCoreUnit> inprocessStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		List<StorageCoreUnit> toPickStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		List<StorageCoreUnit> pickedStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		List<StorageCoreUnit> allSuppliedStorageCoreUnitList = new ArrayList<StorageCoreUnit>();
		if (!ServiceCollectionsHelper.checkNullList(prodPlanItemReqProposalList)) {
			for (ProdPlanItemReqProposalServiceModel prodPlanItemReqProposalServiceModel : prodPlanItemReqProposalList) {
				ProdPlanItemReqProposal prodPlanItemReqProposal = prodPlanItemReqProposalServiceModel.getProdPlanItemReqProposal();
				prodPlanItemReqProposalManager.refreshItemStatus(prodPlanItemReqProposal);
				StorageCoreUnit reservedStorageCoreUnit = new StorageCoreUnit(prodPlanItemReqProposal.getRefMaterialSKUUUID(),
						prodPlanItemReqProposal.getRefUnitUUID(), prodPlanItemReqProposal.getInStockAmount());
				if (reservedStorageCoreUnit != null && reservedStorageCoreUnit.getAmount() > 0) {
					allSuppliedStorageCoreUnitList.add(reservedStorageCoreUnit);
					reservedStorageCoreUnitList.add(reservedStorageCoreUnit);
				}
				StorageCoreUnit freeStorageCoreUnit = prodPickingRefMaterialItemManager
						.getFreeStoreAmount(prodPlanItemReqProposal.getRefMaterialSKUUUID(), prodPlanItemReqProposal.getClient(),
								warehouseUUIDList);
				if (freeStorageCoreUnit != null && freeStorageCoreUnit.getAmount() > 0) {
					freeStorageCoreUnitList.add(freeStorageCoreUnit);
				}
				StorageCoreUnit inProcessStorageCoreUnit = new StorageCoreUnit(prodPlanItemReqProposal.getRefMaterialSKUUUID(),
						prodPlanItemReqProposal.getRefUnitUUID(), prodPlanItemReqProposal.getInProcessAmount());
				if (inProcessStorageCoreUnit != null && inProcessStorageCoreUnit.getAmount() > 0) {
					allSuppliedStorageCoreUnitList.add(inProcessStorageCoreUnit);
					inprocessStorageCoreUnitList.add(inProcessStorageCoreUnit);
				}
				StorageCoreUnit toOutStorageUnit = new StorageCoreUnit(prodPlanItemReqProposal.getRefMaterialSKUUUID(),
						prodPlanItemReqProposal.getRefUnitUUID(), prodPlanItemReqProposal.getToPickAmount());
				if (toOutStorageUnit != null && toOutStorageUnit.getAmount() > 0) {
					allSuppliedStorageCoreUnitList.add(toOutStorageUnit);
					toPickStorageCoreUnitList.add(toOutStorageUnit);
				}
				StorageCoreUnit pickedStorageUnit = new StorageCoreUnit(prodPlanItemReqProposal.getRefMaterialSKUUUID(),
						prodPlanItemReqProposal.getRefUnitUUID(), prodPlanItemReqProposal.getPickedAmount());
				if (pickedStorageUnit != null && pickedStorageUnit.getAmount() > 0) {
					allSuppliedStorageCoreUnitList.add(pickedStorageUnit);
					pickedStorageCoreUnitList.add(pickedStorageUnit);
				}
			}
		}
		/*
		 * [Step3.1] Calculate: to Pick amount
		 */
		StorageCoreUnit toPickStorageUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(toPickStorageCoreUnitList, productionPlanItem.getClient());
		if (toPickStorageUnit != null) {
			productionPlanItem.setToPickAmount(toPickStorageUnit.getAmount());
		} else {
			productionPlanItem.setInStockAmount(0);
		}
		// [Step3.2] Calculate: picked amount
		StorageCoreUnit pickedStorageUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(pickedStorageCoreUnitList, productionPlanItem.getClient());
		if (pickedStorageUnit != null) {
			productionPlanItem.setPickedAmount(pickedStorageUnit.getAmount());
		} else {
			productionPlanItem.setPickedAmount(0);
		}
		// [Step3.3] Calculate: reserved storage
		StorageCoreUnit inStockStorageCoreUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(reservedStorageCoreUnitList, productionPlanItem.getClient());
		if (inStockStorageCoreUnit != null) {
			productionPlanItem.setInStockAmount(inStockStorageCoreUnit.getAmount());
		} else {
			productionPlanItem.setInStockAmount(0);
		}
		// [Step3.4] calculate in process
		StorageCoreUnit inprocessStorageCoreUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(inprocessStorageCoreUnitList, productionPlanItem.getClient());
		if (inprocessStorageCoreUnit != null) {
			productionPlanItem.setInProcessAmount(inprocessStorageCoreUnit.getAmount());
		} else {
			productionPlanItem.setInProcessAmount(0);
		}
		// [Step3.5] calculate supplied
		StorageCoreUnit suppliedStorageCoreUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(allSuppliedStorageCoreUnitList, productionPlanItem.getClient());
		if (suppliedStorageCoreUnit != null) {
			productionPlanItem.setSuppliedAmount(suppliedStorageCoreUnit.getAmount());
		} else {
			productionPlanItem.setSuppliedAmount(0);
		}
		// [Step3.6] calculate free storage
		StorageCoreUnit freeStorageCoreUnit = materialStockKeepUnitManager
				.mergeStorageUnitCore(freeStorageCoreUnitList, productionPlanItem.getClient());
		if (freeStorageCoreUnit != null) {
			productionPlanItem.setAvailableAmount(freeStorageCoreUnit.getAmount());
		}


		/*
		 * [Step4] Try to check and set not-in-plan amount and status
		 */
		ProductionOrderItemManager.updateItemStatus(productionPlanItem);
	}

	public void updateOrderItemToPlanItem(ProductionOrderItem productionOrderItem, ProductionPlanItem productionPlanItem) {
		productionPlanItem.setPickedAmount(productionOrderItem.getPickedAmount());
		productionPlanItem.setToPickAmount(productionOrderItem.getToPickAmount());
		productionPlanItem.setInProcessAmount(productionOrderItem.getInProcessAmount());
		productionPlanItem.setSuppliedAmount(productionOrderItem.getSuppliedAmount());
		productionPlanItem.setInStockAmount(productionOrderItem.getInStockAmount());
		productionPlanItem.setLackInPlanAmount(productionOrderItem.getLackInPlanAmount());
		ProductionOrderItemManager.updateItemStatus(productionPlanItem);
	}

	/**
	 * Logic to get one relative downstream production order item instance
	 *
	 * @param productionPlanItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public ProductionOrderItem getRelativeOrderItem(ProductionPlanItem productionPlanItem) throws ServiceEntityConfigureException {
		List<ServiceEntityNode> orderItemList = getRelativeOrderItemList(productionPlanItem);
		if (ServiceCollectionsHelper.checkNullList(orderItemList)) {
			return null;
		} else {
			return (ProductionOrderItem) orderItemList.get(0);
		}
	}

	/**
	 * Logic to get all possible downstream production order item list
	 *
	 * @param productionPlanItem
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	public List<ServiceEntityNode> getRelativeOrderItemList(ProductionPlanItem productionPlanItem)
			throws ServiceEntityConfigureException {
		if (productionPlanItem == null) {
			logger.error("No Plan Item ");
			return null;
		}
		List<ServiceEntityNode> orderItemList = productionOrderManager
				.getEntityNodeListByKey(productionPlanItem.getUuid(), IServiceEntityCommonFieldConstant.PREVDOCMATITEMUUID,
						ProductionOrderItem.NODENAME, productionPlanItem.getClient(), null);
		if (orderItemList == null) {
			logger.error("No Order Item by:" + productionPlanItem.getUuid());
			return null;
		}
		return orderItemList;
	}

	public ServiceDocumentExtendUIModel convToDocumentExtendUIModel(ServiceEntityNode seNode, LogonInfo logonInfo) {
		if (seNode == null) {
			return null;
		}
		if (ProductionPlanItem.NODENAME.equals(seNode.getNodeName())) {
			ProductionPlanItem productionPlanItem = (ProductionPlanItem) seNode;
			try {
				ProductionPlanItemUIModel productionPlanItemUIModel = (ProductionPlanItemUIModel) productionPlanManager
						.genUIModelFromUIModelExtension(ProductionPlanItemUIModel.class,
								productionPlanItemServiceUIModelExtension.genUIModelExtensionUnion().get(0), productionPlanItem,
								logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convProductionPlanItemToDocExtUIModel(
						productionPlanItemUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionPlanItem.NODENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionPlanItem.NODENAME));
			}
		}
		if (ProdPlanItemReqProposal.NODENAME.equals(seNode.getNodeName())) {
			ProdPlanItemReqProposal prodPlanItemReqProposal = (ProdPlanItemReqProposal) seNode;
			try {
				ProdPlanItemReqProposalUIModel prodPlanItemReqProposalUIModel = (ProdPlanItemReqProposalUIModel) productionPlanManager
						.genUIModelFromUIModelExtension(ProdPlanItemReqProposalUIModel.class,
								prodPlanItemReqProposalServiceUIModelExtension.genUIModelExtensionUnion().get(0), prodPlanItemReqProposal,
								logonInfo, null);
				ServiceDocumentExtendUIModel serviceDocumentExtendUIModel = convProdPlanItemReqProposalToDocExtUIModel(
						prodPlanItemReqProposalUIModel, logonInfo);
				return serviceDocumentExtendUIModel;
			} catch (ServiceModuleProxyException | ServiceEntityConfigureException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionPlanItem.NODENAME));
			} catch (ServiceEntityInstallationException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ProductionPlanItem.NODENAME));
			}
		}
		return null;
	}

}
