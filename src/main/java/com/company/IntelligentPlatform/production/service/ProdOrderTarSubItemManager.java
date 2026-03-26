package com.company.IntelligentPlatform.production.service;

import java.util.List;

import com.company.IntelligentPlatform.production.model.ProdOrderTarSubItem;
import com.company.IntelligentPlatform.production.model.ProdOrderTargetMatItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.service.ServiceComExecuteException;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ProdOrderTarSubItemManager {
	
    @Autowired
    protected ProductionOrderManager productionOrderManager;
    
    @Autowired
    protected ProdOrderTargetMatItemManager prodOrderTargetMatItemManager;
    
    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;
    
    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	protected Logger logger = LoggerFactory.getLogger(ProdOrderTarSubItemManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ProdOrderTargetMatItem.NODENAME,
						request.getUuid(), ProdOrderTarSubItem.NODENAME, productionOrderManager);
		docPageHeaderInputPara.setGenBaseModelList(new DocPageHeaderModelProxy.GenBaseModelList<ProdOrderTargetMatItem>() {
			@Override
			public List<com.company.IntelligentPlatform.common.controller.PageHeaderModel> execute(ProdOrderTargetMatItem prodOrderTargetMatItem) throws ServiceEntityConfigureException {
				// How to get the base page header model list
				return prodOrderTargetMatItemManager.getPageHeaderModelList(DocPageHeaderModelProxy.getDefRequest(prodOrderTargetMatItem), client);
			}
		});
		docPageHeaderInputPara.setGenHomePageModel(new DocPageHeaderModelProxy.GenHomePageModel<ProdOrderTarSubItem>() {
			@Override
			public com.company.IntelligentPlatform.common.controller.PageHeaderModel execute(ProdOrderTarSubItem prodOrderTarSubItem, com.company.IntelligentPlatform.common.controller.PageHeaderModel pageHeaderModel) throws ServiceEntityConfigureException {
				// How to render current page header
				MaterialStockKeepUnit materialStockKeepUnit = null;
				try {
					materialStockKeepUnit = materialStockKeepUnitManager
							.getMaterialSKUWrapper(prodOrderTarSubItem.getRefMaterialSKUUUID(), prodOrderTarSubItem.getClient(), null);
				} catch (ServiceComExecuteException e) {
					logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
				}
				if (materialStockKeepUnit != null) {
					pageHeaderModel.setHeaderName(MaterialStockKeepUnitManager.getMaterialIdentifier(materialStockKeepUnit, false));
				}
				return pageHeaderModel;
			}
		});
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}
	
	
}
