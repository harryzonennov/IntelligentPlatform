package com.company.IntelligentPlatform.production.service;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;
import com.company.IntelligentPlatform.production.model.ProdPlanTargetMatItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ProdPlanTarSubItemManager {
	
    @Autowired
    protected ProductionOrderManager productionOrderManager;
    
    @Autowired
    protected ProdPlanTargetItemManager prodPlanTargetItemManager;
    
    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;
    
    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public List<PageHeaderModel> getPageHeaderModelList(
			ProdOrderTarSubItem prodOrderTarSubItem, String client)
			throws ServiceEntityConfigureException {
		ProdPlanTargetMatItem prodPlanTargetMatItem = (ProdPlanTargetMatItem) productionOrderManager
				.getEntityNodeByKey(prodOrderTarSubItem.getParentNodeUUID(),
						IServiceEntityNodeFieldConstant.UUID,
						ProdPlanTargetMatItem.NODENAME, client, null);
		int index = 0;
		List<PageHeaderModel> resultList = new ArrayList<PageHeaderModel>();
		if (prodOrderTarSubItem != null) {
			List<PageHeaderModel> pageHeaderModelList = prodPlanTargetItemManager
					.getPageHeaderModelList(prodPlanTargetMatItem, client);
			if (!ServiceCollectionsHelper.checkNullList(pageHeaderModelList)) {
				resultList.addAll(pageHeaderModelList);
				index = pageHeaderModelList.size();
			}
			PageHeaderModel itemHeaderModel = null;
			try {
				itemHeaderModel = getPageHeaderModel(
						prodOrderTarSubItem, index);
			} catch (ServiceComExecuteException e) {
				logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
			}
			if (itemHeaderModel != null) {
				resultList.add(itemHeaderModel);
			}
		}
		return resultList;
	}

	protected PageHeaderModel getPageHeaderModel(
			ProdOrderTarSubItem prodOrderTarSubItem, int index) throws ServiceEntityConfigureException, ServiceComExecuteException {
		if (prodOrderTarSubItem == null) {
			return null;
		}
		PageHeaderModel pageHeaderModel = new PageHeaderModel();
		pageHeaderModel.setPageTitle("prodOrderTarSubItemPageTitle");
		pageHeaderModel.setNodeInstId(ProdOrderTarSubItem.NODENAME);
		pageHeaderModel.setUuid(prodOrderTarSubItem.getUuid());
		MaterialStockKeepUnit materialStockKeepUnit = materialStockKeepUnitManager
				.getMaterialSKUWrapper(
						prodOrderTarSubItem.getRefMaterialSKUUUID(),
						prodOrderTarSubItem.getClient(), null);
		if(materialStockKeepUnit != null){
			pageHeaderModel.setHeaderName(MaterialStockKeepUnitManager.getMaterialIdentifier(materialStockKeepUnit, false));
		}
		pageHeaderModel.setIndex(index);
		return pageHeaderModel;
	}

	
	
}
