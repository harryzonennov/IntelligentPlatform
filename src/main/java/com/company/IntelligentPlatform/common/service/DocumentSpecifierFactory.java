package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class DocumentSpecifierFactory {

    /**
     * Registration Area to register executable Units here
     */
    @Qualifier("billOfMaterialOrderSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier billOfMaterialOrderSpecifier;

    @Qualifier("billOfMaterialTemplateSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier billOfMaterialTemplateSpecifier;

    @Qualifier("employeeSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier employeeSpecifier;

    @Qualifier("inboundDeliverySpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier inboundDeliverySpecifier;

    @Qualifier("inquirySpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier inquirySpecifier;

    @Qualifier("inventoryTransferOrderSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier inventoryTransferOrderSpecifier;

    @Qualifier("logonUserSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier logonUserSpecifier;

    @Qualifier("materialTypeSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier materialTypeSpecifier;

    @Qualifier("materialSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier materialSpecifier;

    @Qualifier("corporateSupplierSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier corporateSupplierSpecifier;

    @Qualifier("corporateCustomerSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier corporateCustomerSpecifier;

    @Qualifier("materialStockKeepUnitSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier materialStockKeepUnitSpecifier;

    @Qualifier("outboundDeliverySpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier outboundDeliverySpecifier;

    @Qualifier("purchaseContractSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier purchaseContractSpecifier;

    @Qualifier("purchaseRequestSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier purchaseRequestSpecifier;

    @Qualifier("purchaseReturnOrderSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier purchaseReturnOrderSpecifier;

    @Qualifier("qualityInspectOrderSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier qualityInspectOrderSpecifier;

    @Qualifier("registeredProductSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier registeredProductSpecifier;

    @Qualifier("salesContractSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier salesContractSpecifier;

    @Qualifier("salesForcastSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier salesForcastSpecifier;

    @Qualifier("salesReturnOrderSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier salesReturnOrderSpecifier;

    @Qualifier("warehouseStoreSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier warehouseStoreSpecifier;

    @Qualifier("warehouseStoreSpecifier")
    @Autowired(required = false)
    protected DocumentContentSpecifier wasteProcessOrderSpecifier;

    public DocumentContentSpecifier getDocumentSpecifier(String modelId){
        String localModelId = ServiceEntityStringHelper.headerToUpperCase(modelId);
        if(localModelId.equals(IServiceModelConstants.BillOfMaterialOrder)){
            return billOfMaterialOrderSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.BillOfMaterialTemplate)){
            return billOfMaterialTemplateSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.Employee)){
            return employeeSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.LogonUser)){
            return logonUserSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.MaterialType)){
            return materialTypeSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.Material)){
            return materialSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.MaterialStockKeepUnit)){
            return materialStockKeepUnitSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.OutboundDelivery)){
            return outboundDeliverySpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.PurchaseRequest)){
            return purchaseRequestSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.PurchaseContract)){
            return purchaseContractSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.PurchaseReturnOrder)){
            return purchaseReturnOrderSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.InboundDelivery)){
            return inboundDeliverySpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.CorporateCustomer)){
            return corporateCustomerSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.CorporateSupplier)){
            return corporateSupplierSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.Inquiry)){
            return inquirySpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.QualityInspectOrder)){
            return qualityInspectOrderSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.RegisteredProduct)){
            return registeredProductSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.SalesContract)){
            return salesContractSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.SalesForcast)){
            return salesForcastSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.SalesReturnOrder)){
            return salesReturnOrderSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.WarehouseStore)){
            return warehouseStoreSpecifier;
        }
        if(localModelId.equals(IServiceModelConstants.WasteProcessOrder)){
            return wasteProcessOrderSpecifier;
        }
        return null;
    }

}
