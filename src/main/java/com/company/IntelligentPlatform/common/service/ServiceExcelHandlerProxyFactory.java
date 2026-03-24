package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceExcelHandlerProxyFactory {

    /**
     * Registration Area to register executable Units here
     */
    @Qualifier("inquiryMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy inquiryMaterialItemExcelHelper;

    @Qualifier("inboundItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy inboundItemExcelHelper;

    @Qualifier("outboundItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy outboundItemExcelHelper;

    @Qualifier("purchaseContractMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy purchaseContractMaterialItemExcelHelper;

    @Qualifier("purchaseRequestMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy purchaseRequestMaterialItemExcelHelper;

    @Qualifier("purchaseReturnMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy purchaseReturnMaterialItemExcelHelper;

    @Qualifier("warehouseStoreItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy warehouseStoreItemExcelHelper;

    @Qualifier("salesForcastMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy salesForcastMaterialItemExcelHelper;

    @Qualifier("salesContractMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy salesContractMaterialItemExcelHelper;

    @Qualifier("materialListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy materialListExcelHandler;

    @Qualifier("salesReturnMaterialItemExcelHelper")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy salesReturnMaterialItemExcelHelper;

    @Qualifier("materialStockKeepUnitListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy materialStockKeepUnitListExcelHandler;

    @Qualifier("registeredProductListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy registeredProductListExcelHandler;

    @Qualifier("employeeListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy employeeListExcelHandler;

    @Qualifier("logonUserListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy logonUserListExcelHandler;

    @Qualifier("corporateCustomerListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy corporateCustomerListExcelHandler;

    @Qualifier("corporateSupplierListExcelHandler")
    @Autowired(required = false)
    protected ServiceExcelHandlerProxy corporateSupplierListExcelHandler;

    public ServiceExcelHandlerProxy getServiceExcelHandler(String modelId) {
        String localModelId = ServiceEntityStringHelper.headerToUpperCase(modelId);
        if (localModelId.equals(IServiceModelConstants.InquiryMaterialItem)) {
            return inquiryMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.InboundItem)) {
            return inboundItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.OutboundItem)) {
            return outboundItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.PurchaseContractMaterialItem)) {
            return purchaseContractMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.PurchaseRequestMaterialItem)) {
            return purchaseRequestMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.PurchaseReturnMaterialItem)) {
            return purchaseReturnMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.WarehouseStoreItem)) {
            return warehouseStoreItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.SalesForcastMaterialItem)) {
            return salesForcastMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.SalesReturnMaterialItem)) {
            return salesReturnMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.SalesContractMaterialItem)) {
            return salesContractMaterialItemExcelHelper;
        }
        if (localModelId.equals(IServiceModelConstants.Material)) {
            return materialListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.MaterialStockKeepUnit)) {
            return materialStockKeepUnitListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.RegisteredProduct)) {
            return registeredProductListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.LogonUser)) {
            return logonUserListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.Employee)) {
            return employeeListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.CorporateCustomer)) {
            return corporateCustomerListExcelHandler;
        }
        if (localModelId.equals(IServiceModelConstants.CorporateSupplier)) {
            return corporateSupplierListExcelHandler;
        }
        return null;
    }


}
