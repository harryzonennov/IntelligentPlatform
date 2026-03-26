package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.model.ProdOrderSupplyWarehouse;
import com.company.IntelligentPlatform.production.model.ProductionOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.WarehouseManager;
import com.company.IntelligentPlatform.common.model.Warehouse;
import com.company.IntelligentPlatform.common.controller.PageHeaderModel;
import com.company.IntelligentPlatform.common.service.DocPageHeaderModelProxy;
import com.company.IntelligentPlatform.common.model.SimpleSEJSONRequest;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;

@Service
public class ProdOrderSupplyWarehouseManager {
	
    @Autowired
    protected ProductionOrderManager productionOrderManager;
    
    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;
    
    @Autowired
    protected WarehouseManager warehouseManager;

	@Autowired
	protected DocPageHeaderModelProxy docPageHeaderModelProxy;

	protected Logger logger = LoggerFactory.getLogger(ProdOrderSupplyWarehouseManager.class);

	public List<PageHeaderModel> getPageHeaderModelList(SimpleSEJSONRequest request, String client)
			throws ServiceEntityConfigureException {
		DocPageHeaderModelProxy.DocPageHeaderInputPara docPageHeaderInputPara =
				new DocPageHeaderModelProxy.DocPageHeaderInputPara(request.getBaseUUID(), ProductionOrder.NODENAME,
						request.getUuid(), ProdOrderSupplyWarehouse.NODENAME, productionOrderManager);
		docPageHeaderInputPara.setGenBaseModelList((DocPageHeaderModelProxy.GenBaseModelList<ProductionOrder>) productionOrder -> {
            // How to get the base page header model list
            return docPageHeaderModelProxy.getDocPageHeaderModelList(productionOrder,  null);
        });
		docPageHeaderInputPara.setGenHomePageModel((DocPageHeaderModelProxy.GenHomePageModel<ProdOrderSupplyWarehouse>) (prodOrderSupplyWarehouse, pageHeaderModel) -> {
            // How to render current page header
            Warehouse warehouse = (Warehouse) warehouseManager.getEntityNodeByUUID(prodOrderSupplyWarehouse.getRefUUID(), Warehouse.NODENAME, prodOrderSupplyWarehouse.getClient());
if (warehouse != null) {
                pageHeaderModel.setHeaderName(warehouse.getId());
            }
            return pageHeaderModel;
        });
		return docPageHeaderModelProxy.getPageHeaderModelList(docPageHeaderInputPara, client);
	}
	
}
