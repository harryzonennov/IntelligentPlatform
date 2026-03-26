package com.company.IntelligentPlatform.production.service;

import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUpdateLogUIModel;
import com.company.IntelligentPlatform.production.dto.BillOfMaterialItemUpdateLogUIModel;
import com.company.IntelligentPlatform.production.model.BillOfMaterialItemUpdateLog;
import com.company.IntelligentPlatform.production.model.BillOfMaterialOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialUIModel;
import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.common.model.MaterialStockKeepUnit;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceCommonDataFormatter;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityDoubleHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.Map;

@Service
public class BillOfMaterialItemUpdateLogManager {

    public static final String METHOD_ConvProdWorkCenterToItemUI = "convProdWorkCenterToItemUI";

    public static final String METHOD_ConvBillOfMaterialItemUpdateLogToUI = "convBillOfMaterialItemUpdateLogToUI";

    public static final String METHOD_ConvSubBomOrderToItemUI = "convSubBOMToItemUI";

    public static final String METHOD_ConvItemMaterialToUI = "convItemMaterialToUI";

    public static final String MEMTHOD_ConvParentBOMOrderToUI = "convParentBOMOrderToUI";

    public static final String METHOD_ConvProdProcessToUI = "convProdProcessToUI";

    public static final String METHOD_ConvProcessRouteProcessItemToUI = "convProcessRouteProcessItemToUI";

    @Autowired
    protected ServiceDropdownListHelper serviceDropdownListHelper;

    @Autowired
    protected BillOfMaterialItemManager billOfMaterialItemManager;

    @Autowired
    protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

    @Autowired
    protected BillOfMaterialOrderManager billOfMaterialOrderManager;

    protected Logger logger = LoggerFactory.getLogger(BillOfMaterialItemUpdateLogManager.class);

    public void convBillOfMaterialItemUpdateLogToUI(
            BillOfMaterialItemUpdateLog billOfMaterialItemUpdateLog,
            BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel)
            throws ServiceEntityInstallationException {
        convBillOfMaterialItemUpdateLogToUI(billOfMaterialItemUpdateLog, billOfMaterialItemUpdateLogUIModel, null);
    }

    public void convBillOfMaterialItemUpdateLogToUI(
            BillOfMaterialItemUpdateLog billOfMaterialItemUpdateLog,
            BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel, LogonInfo logonInfo)
            throws ServiceEntityInstallationException {
        if (billOfMaterialItemUpdateLog != null) {
            if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItemUpdateLog
                    .getUuid())) {
                billOfMaterialItemUpdateLogUIModel.setUuid(billOfMaterialItemUpdateLog.getUuid());
            }
            if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItemUpdateLog
                    .getParentNodeUUID())) {
                billOfMaterialItemUpdateLogUIModel.setParentNodeUUID(billOfMaterialItemUpdateLog
                        .getParentNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItemUpdateLog
                    .getRootNodeUUID())) {
                billOfMaterialItemUpdateLogUIModel.setRootNodeUUID(billOfMaterialItemUpdateLog
                        .getRootNodeUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(billOfMaterialItemUpdateLog
                    .getClient())) {
                billOfMaterialItemUpdateLogUIModel.setClient(billOfMaterialItemUpdateLog
                        .getClient());
            }
            billOfMaterialItemUpdateLogUIModel.setRefMaterialSKUUUID(billOfMaterialItemUpdateLog
                    .getRefMaterialSKUUUID());
            billOfMaterialItemUpdateLogUIModel.setUpdateAmount(ServiceEntityDoubleHelper.trancateDoubleScale4(billOfMaterialItemUpdateLog.getUpdateAmount()));
            billOfMaterialItemUpdateLogUIModel.setUpdateRefUnitUUID(billOfMaterialItemUpdateLog
                    .getUpdateRefUnitUUID());
            try {
                String refUnitName = materialStockKeepUnitManager
                        .getRefUnitName(
                                billOfMaterialItemUpdateLog.getRefMaterialSKUUUID(),
                                billOfMaterialItemUpdateLog.getUpdateRefUnitUUID(),
                                billOfMaterialItemUpdateLog.getClient());
                billOfMaterialItemUpdateLogUIModel.setUpdateRefUnitName(refUnitName);
            } catch (MaterialException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
            } catch (ServiceEntityConfigureException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "refUnitName"));
            }
            billOfMaterialItemUpdateLogUIModel.setLayer(billOfMaterialItemUpdateLog.getLayer());
            billOfMaterialItemUpdateLogUIModel.setRefParentItemUUID(billOfMaterialItemUpdateLog
                    .getRefParentItemUUID());
            billOfMaterialItemUpdateLogUIModel.setItemCategory(billOfMaterialItemUpdateLog
                    .getItemCategory());
            billOfMaterialItemUpdateLogUIModel.setLeadTimeOffset(billOfMaterialItemUpdateLog
                    .getLeadTimeOffset());
            billOfMaterialItemUpdateLogUIModel.setUpdateTheoLossRate(billOfMaterialItemUpdateLog
                    .getUpdateTheoLossRate());
            String theoLossRateValue = ServiceCommonDataFormatter
                    .formatPercentageValue(billOfMaterialItemUpdateLog
                            .getUpdateTheoLossRate()/100);
            billOfMaterialItemUpdateLogUIModel.setUpdateTheoLossRateValue(theoLossRateValue);
            billOfMaterialItemUpdateLogUIModel.setRefSubBOMUUID(billOfMaterialItemUpdateLog
                    .getRefSubBOMUUID());
            billOfMaterialItemUpdateLogUIModel.setRefWocUUID(billOfMaterialItemUpdateLog.getRefWocUUID());
            Map<Integer, String> categoryMap = serviceDropdownListHelper
                    .getUIDropDownMap(MaterialUIModel.class, "materialCategory");
            billOfMaterialItemUpdateLogUIModel.setItemCategoryValue(categoryMap
                    .get(billOfMaterialItemUpdateLog.getItemCategory()));
            try {
                String updataeAmountLabel = materialStockKeepUnitManager
                        .getAmountLabel(
                                billOfMaterialItemUpdateLog.getRefMaterialSKUUUID(),
                                billOfMaterialItemUpdateLog.getUpdateRefUnitUUID(),
                                billOfMaterialItemUpdateLog.getUpdateAmount(),
                                billOfMaterialItemUpdateLog.getClient());
                billOfMaterialItemUpdateLogUIModel.setUpdateAmountLabel(updataeAmountLabel);
            } catch (MaterialException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
            } catch (ServiceEntityConfigureException e) {
                // just skip
                logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "amountLabel"));
            }
        }
    }

    public void convItemMaterialToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel) {
        convItemMaterialToUI(materialStockKeepUnit, billOfMaterialItemUpdateLogUIModel, null);
    }

    public void convItemMaterialToUI(
            MaterialStockKeepUnit materialStockKeepUnit,
            BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel, LogonInfo logonInfo) {
        if (materialStockKeepUnit != null) {
            billOfMaterialItemUpdateLogUIModel
                    .setRefMaterialSKUName(materialStockKeepUnit.getName());
            billOfMaterialItemUpdateLogUIModel.setRefMaterialSKUId(materialStockKeepUnit
                    .getId());
            billOfMaterialItemUpdateLogUIModel.setFixLeadTime(materialStockKeepUnit
                    .getFixLeadTime());
            billOfMaterialItemUpdateLogUIModel.setPackageStandard(materialStockKeepUnit.getPackageStandard());
            billOfMaterialItemUpdateLogUIModel.setSupplyType(materialStockKeepUnit.getSupplyType());
            if(logonInfo != null){
                try {
                    Map<Integer, String> supplyTypeMap = materialStockKeepUnitManager.initSupplyTypeMap(logonInfo.getLanguageCode());
                    billOfMaterialItemUpdateLogUIModel.setSupplyTypeValue(supplyTypeMap.get(materialStockKeepUnit.getSupplyType()));
                } catch (ServiceEntityInstallationException e) {
                    // Log error and continue
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "supplyType"));
                }
            }

        }
    }

    public void convSubBOMToItemUI(BillOfMaterialOrder subBOM,
                                   BillOfMaterialItemUpdateLogUIModel billOfMaterialItemUpdateLogUIModel) {
        if (subBOM != null) {
            billOfMaterialItemUpdateLogUIModel.setRefSubBOMId(subBOM.getId());
        }
    }

}
