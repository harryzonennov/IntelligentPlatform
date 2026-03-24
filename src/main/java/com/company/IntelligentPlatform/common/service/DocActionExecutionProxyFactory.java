package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class DocActionExecutionProxyFactory {

    /**
     * Registration Area to register executable Units here
     */
    @Qualifier("outboundDeliveryActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy outboundDeliveryActionExecutionProxy;

    @Qualifier("inboundDeliveryActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy inboundDeliveryActionExecutionProxy;

    @Qualifier("warehouseStoreActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy warehouseStoreActionExecutionProxy;

    @Qualifier("inventoryTransferOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy inventoryTransferOrderActionExecutionProxy;

    @Qualifier("inventoryCheckOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy inventoryCheckOrderActionExecutionProxy;

    @Qualifier("purchaseContractActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy purchaseContractActionExecutionProxy;

    @Qualifier("purchaseRequestActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy purchaseRequestActionExecutionProxy;

    @Qualifier("purchaseReturnOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy purchaseReturnOrderActionExecutionProxy;

    @Qualifier("salesContractActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy salesContractActionExecutionProxy;

    @Qualifier("salesForcastActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy salesForcastActionExecutionProxy;

    @Qualifier("salesReturnOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy salesReturnOrderActionExecutionProxy;

    @Qualifier("inquiryActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy inquiryActionExecutionProxy;

    @Qualifier("qualityInspectOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy qualityInspectOrderActionExecutionProxy;

    @Qualifier("billOfMaterialTemplateExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy billOfMaterialTemplateExecutionProxy;

    @Qualifier("billOfMaterialOrderExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy billOfMaterialOrderExecutionProxy;

    @Qualifier("wasteProcessOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy wasteProcessOrderActionExecutionProxy;

    @Qualifier("productionOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy productionOrderActionExecutionProxy;

    @Qualifier("productionPlanActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy productionPlanActionExecutionProxy;

    @Qualifier("prodPickingOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy prodPickingOrderActionExecutionProxy;

    @Qualifier("prodReturnOrderActionExecutionProxy")
    @Autowired(required = false)
    protected DocActionExecutionProxy prodReturnOrderActionExecutionProxy;


    public Map<Integer, String> getDocActionMapByDocType(int documentType, String languageCode)
            throws DocActionException, ServiceEntityInstallationException {
        DocActionExecutionProxy docActionExecutionProxy = this.getDocActionExecutionProxyByDocType(documentType);
        if(docActionExecutionProxy == null){
            throw new DocActionException(DocActionException.PARA_MISS_CONFIG, documentType);
        }
        return docActionExecutionProxy.getActionCodeMap(languageCode);
    }

    public DocActionExecutionProxy getDocActionExecutionProxyByDocType(int documentType){
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASERETURNORDER){
            return purchaseReturnOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASEREQUEST){
            return purchaseRequestActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_INQUIRY){
            return inquiryActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PURCHASECONTRACT){
            return purchaseContractActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_INBOUNDDELIVERY){
            return inboundDeliveryActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_OUTBOUNDDELIVERY){
            return outboundDeliveryActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_TRANSFER){
            return inventoryTransferOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_INVENTORY_CHECKORDER){
            return inventoryCheckOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESCONTRACT){
            return salesContractActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESFORCAST){
            return salesForcastActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_SALESRETURNORDER){
            return salesReturnOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_QUALITYINSPECTORDER){
            return qualityInspectOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALORDER){
            return billOfMaterialOrderExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_BILLOFMATERIALTEMPLATE){
            return billOfMaterialTemplateExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_WAREHOUSESTOREITEM){
            return warehouseStoreActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_WASTEPROCESSORDER){
            return wasteProcessOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONORDER){
            return productionOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN){
            return productionPlanActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODPICKINGORDER){
            return prodPickingOrderActionExecutionProxy;
        }
        if(documentType == IDefDocumentResource.DOCUMENT_TYPE_PRODRETURNORDER){
            return prodReturnOrderActionExecutionProxy;
        }


        return null;
    }

    /**
     * Get all the active (not nul) search proxy instance list
     * @return
     */
    public List<DocActionExecutionProxy> getAllDocExecutionProxyList(){
        return ServiceEntityFieldsHelper.getDefRepoInstList(DocActionExecutionProxy.class, this);
    }

    /**
     * Logic to get search proxy instance by registered id
     * @param searchProxyId
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public DocActionExecutionProxy getDocActionExecutionProxyById(String searchProxyId)
            throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException {
        Field field = ServiceEntityFieldsHelper.getServiceField(this.getClass(), searchProxyId);
        field.setAccessible(true);
        return (DocActionExecutionProxy)field.get(this);
    }

    public Map<Integer, String> getStatusMapByDocType(int documentType, String lanCode)
            throws ServiceEntityInstallationException {
        DocActionExecutionProxy docActionExecutionProxy =
                getDocActionExecutionProxyByDocType(documentType);
        if(docActionExecutionProxy != null){
            return docActionExecutionProxy.getDocumentContentSpecifier().getStatusMap(lanCode);
        }
        return null;
    }

    public Map<Integer, String> getDocActionConfigureMapByDocType(int documentType, String lanCode)
            throws ServiceEntityInstallationException {
        DocActionExecutionProxy docActionExecutionProxy =
                getDocActionExecutionProxyByDocType(documentType);
        if(docActionExecutionProxy != null){
            return docActionExecutionProxy.getActionCodeMap(lanCode);
        }
        return null;
    }


    public DocumentContentSpecifier getSpecifierByDocType(int documentType)
            throws DocActionException {
       DocActionExecutionProxy docActionExecutionProxy =
               getDocActionExecutionProxyByDocType(documentType);
        if(docActionExecutionProxy != null){
            return docActionExecutionProxy.getDocumentContentSpecifier();
        }
        return null;

    }



}
