package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.MaterialTypeSearchSubModel;
import com.company.IntelligentPlatform.common.dto.RegisteredProductSearchModel;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.model.CorporateCustomer;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class RegisteredProductSearchProxy extends ServiceSearchProxy {

    @Autowired
    protected MaterialManager materialManager;

    @Autowired
    protected MaterialTypeManager materialTypeManager;

    @Autowired
    protected MaterialTypeSearchProxy materialTypeSearchProxy;

    @Autowired
    protected MaterialSearchProxy materialSearchProxy;

    @Autowired
    protected MaterialStockKeepUnitSearchProxy materialStockKeepUnitSearchProxy;

    @Override
    public Class<?> getDocSearchModelCls() {
        return RegisteredProductSearchModel.class;
    }

    @Override
    public Class<?> getMatItemSearchModelCls() {
        return null;
    }

    @Override
    public String getAuthorizationResource() {
        return materialManager.getAuthorizationResource();
    }

    @Override
    public Map<Integer, String> getStatusMap(String languageCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public BSearchResponse searchDocList(SearchContext searchContext) throws SearchConfigureException,
            ServiceEntityConfigureException, ServiceEntityInstallationException, AuthorizationException,
            LogonInfoException {
        List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
        searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
        searchContext.setFuzzyFlag(true);
        RegisteredProductSearchModel registeredProductSearchModel = (RegisteredProductSearchModel) searchContext.getSearchModel();
        MaterialTypeSearchSubModel materialTypeSearchSubModel = registeredProductSearchModel != null ? registeredProductSearchModel.getMaterialType() : null;
        return materialSearchProxy.searchDocListCore(searchContext, materialTypeSearchSubModel, searchNodeConfigList);
    }

    @Override
    public List<BSearchNodeComConfigure> getBasicSearchNodeConfigureList(SearchContext searchContext) throws SearchConfigureException {
        // start node:[RegisteredProduct->RegisteredProductExtendProperty]
        List<BSearchNodeComConfigure> searchNodeConfigList =
                SearchModelConfigHelper.buildParentChildConfigure(RegisteredProduct.class,
                        RegisteredProductExtendProperty.class);
        // Search node:[RegisteredProduct->materialSKU]
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialStockKeepUnit.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(RegisteredProduct.FEILD_refMaterialSKUUUID).
                baseNodeInstId(RegisteredProduct.SENAME).build());
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialSKUUnitReference.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_TO_PARENT).
                baseNodeInstId(MaterialStockKeepUnit.SENAME).build());
        // Search node:[materialSKU->material]
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(Material.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID(MaterialStockKeepUnit.FIELD_REF_MATERIALUUID).
                baseNodeInstId(MaterialStockKeepUnit.SENAME).build());
        // search node [MaterialUnitReference->standardMaterialUnit]
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(StandardMaterialUnit.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_REFTO_SOURCE).baseNodeInstId(MaterialSKUUnitReference.NODENAME).build());
        // Search node:[materialSKU->corporateCustomer]
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(CorporateCustomer.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("supplierUUID").
                baseNodeInstId(MaterialStockKeepUnit.SENAME).build());
        // Search node:[material->materialType]
        searchNodeConfigList.add(SearchModelConfigHelper.genBuilder().modelClass(MaterialType.class).
                toBaseNodeType(SearchNodeMapping.TOBASENODE_OTHERS).mapFieldUUID("refMaterialType").
                baseNodeInstId(Material.SENAME).build());
        searchNodeConfigList.addAll(SearchModelConfigHelper.buildResUserOrgConfigure(MaterialStockKeepUnit.class, null));

        searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
                RegisteredProductActionLog.class, RegisteredProductActionLog.NODEINST_ACTION_INPROCESS,
                RegisteredProductActionLog.DOC_ACTION_SUBMIT,
                null, MaterialStockKeepUnit.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
                RegisteredProductActionLog.class, RegisteredProductActionLog.NODEINST_ACTION_APPROVE,
                RegisteredProductActionLog.DOC_ACTION_APPROVE,
                null, MaterialStockKeepUnit.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
                RegisteredProductActionLog.class, RegisteredProductActionLog.NODEINST_ACTION_ACTIVE,
                RegisteredProductActionLog.DOC_ACTION_ACTIVE,
                null, MaterialStockKeepUnit.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genActionNodeSearchNodeConfigureList(
                RegisteredProductActionLog.class, RegisteredProductActionLog.NODEINST_ACTION_ARCHIVE,
                RegisteredProductActionLog.DOC_ACTION_ARCHIVE,
                null, MaterialStockKeepUnit.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, Organization.class, Employee.class, RegisteredProductInvolveParty.PARTY_ROLE_PRODORG, RegisteredProductInvolveParty.NODEINST_PRODUCTBY,
                RegisteredProduct.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, Organization.class, Employee.class, RegisteredProductInvolveParty.PARTY_ROLE_PURORG, RegisteredProductInvolveParty.NODEINST_PURCHASEBY,
                RegisteredProduct.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, CorporateCustomer.class, IndividualCustomer.class, RegisteredProductInvolveParty.PARTY_ROLE_SUPPLIER, RegisteredProductInvolveParty.NODEINST_PURCHASEFROM,
                RegisteredProduct.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, Organization.class, Employee.class, RegisteredProductInvolveParty.PARTY_ROLE_SALESORG, RegisteredProductInvolveParty.NODEINST_SALESBY,
                RegisteredProduct.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, CorporateCustomer.class, IndividualCustomer.class, RegisteredProductInvolveParty.PARTY_ROLE_CUSTOMER, RegisteredProductInvolveParty.NODEINST_SALESTO,
                RegisteredProduct.SENAME
        ));
        searchNodeConfigList.addAll(SearchDocConfigHelper.genInvolvePartySearchNodeConfigureList(
                RegisteredProductInvolveParty.class, Organization.class, Employee.class, RegisteredProductInvolveParty.PARTY_ROLE_SUPPORTORG, RegisteredProductInvolveParty.NODEINST_SUPPORTBY,
                RegisteredProduct.SENAME
        ));
        return searchNodeConfigList;
    }

    @Override
    public BSearchResponse searchOnline(SearchContext searchContext)
            throws SearchConfigureException {
        try {
            List<BSearchNodeComConfigure> searchNodeConfigList = getBasicSearchNodeConfigureList(searchContext);
            searchContext.setFieldNameArray(SearchDocConfigHelper.genDefaultMaterialFieldNameArray());
            searchContext.setFuzzyFlag(true);
            RegisteredProductSearchModel registeredProductSearchModel = (RegisteredProductSearchModel) searchContext.getSearchModel();
            MaterialTypeSearchSubModel materialTypeSearchSubModel = registeredProductSearchModel != null ? registeredProductSearchModel.getMaterialType() : null;
            return materialStockKeepUnitSearchProxy.searchOnlineCore(searchContext, materialTypeSearchSubModel, searchNodeConfigList);
        } catch (ServiceEntityConfigureException | LogonInfoException | ServiceEntityInstallationException e) {
            throw new SearchConfigureException(SearchConfigureException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
    }

}

