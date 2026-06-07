package com.company.IntelligentPlatform.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.CorporateCustomerServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.dto.CorporateCustomerUIModel;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.service.CrossDocConvertRequest;
import com.company.IntelligentPlatform.platform.service.DocSplitMergeRequest;
import com.company.IntelligentPlatform.platform.service.ServiceEntityManager;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.CorporateCustomer;
import com.company.IntelligentPlatform.platform.model.CorporateCustomerActionNode;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

import java.util.List;
import java.util.Map;

@Service
public class CorporateCustomerActionExecutionProxy
        extends DocActionExecutionProxy<CorporateCustomerServiceModel, CorporateCustomer, DocMatItemNode> {

    @Autowired
    protected CorporateCustomerManager corporateCustomerManager;

    @Autowired
    protected CorporateCustomerServiceUIModelExtension corporateCustomerServiceUIModelExtension;

    @Autowired
    protected CorporateCustomerSpecifier corporateCustomerSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "CorporateCustomer_actionCode";

    protected Logger logger = LoggerFactory.getLogger(CorporateCustomerActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = CorporateCustomerUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(CorporateCustomerActionNode.DOC_ACTION_ACTIVE,
                        CorporateCustomer.STATUS_INUSE,
                        ServiceCollectionsHelper.asList(CorporateCustomer.STATUS_INITIAL),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(CorporateCustomerActionNode.DOC_ACTION_REINIT,
                        CorporateCustomer.STATUS_INITIAL,
                        ServiceCollectionsHelper.asList(CorporateCustomer.STATUS_INUSE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(CorporateCustomerActionNode.DOC_ACTION_ARCHIVE,
                        CorporateCustomer.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(CorporateCustomer.STATUS_INITIAL, CorporateCustomer.STATUS_INUSE),
                        ISystemActionCode.ACID_EDIT));
        return defDocActionConfigureList;
    }

    @Override
    public List<DocActionConfigure> getCustomDocActionConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getDefCrossDocActConfigureList() {
        return null;
    }

    @Override
    public List<CrossDocActConfigure> getCustomCrossDocActConfigureList(String client)
            throws ServiceModuleProxyException, ServiceEntityConfigureException {
        return null;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getDefCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public Map<Integer, CrossCopyDocConversionConfig> getCustomCrossCopyDocConversionConfigMap() {
        return null;
    }

    @Override
    public DocumentContentSpecifier<CorporateCustomerServiceModel, CorporateCustomer, DocMatItemNode> getDocumentContentSpecifier() {
        return corporateCustomerSpecifier;
    }

    @Override
    public DocSplitMergeRequest<CorporateCustomer, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<CorporateCustomerServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return corporateCustomerManager;
    }

    public void executeActionCore(CorporateCustomerServiceModel corporateCustomerServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(corporateCustomerServiceModel, docActionCode,
                (corporateCustomer, serialLogonInfo1) -> corporateCustomer, null, serialLogonInfo);
    }

}
