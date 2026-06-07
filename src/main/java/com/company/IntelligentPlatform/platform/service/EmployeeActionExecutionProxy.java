package com.company.IntelligentPlatform.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.platform.dto.EmployeeServiceUIModelExtension;
import com.company.IntelligentPlatform.platform.dto.EmployeeUIModel;
import com.company.IntelligentPlatform.platform.service.*;
import com.company.IntelligentPlatform.platform.model.Employee;
import com.company.IntelligentPlatform.platform.model.EmployeeActionNode;
import com.company.IntelligentPlatform.platform.model.ISystemActionCode;
import com.company.IntelligentPlatform.platform.model.SerialLogonInfo;
import com.company.IntelligentPlatform.platform.model.DocMatItemNode;
import com.company.IntelligentPlatform.platform.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityStringHelper;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeActionExecutionProxy
        extends DocActionExecutionProxy<EmployeeServiceModel, Employee, DocMatItemNode> {

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected EmployeeServiceUIModelExtension employeeServiceUIModelExtension;

    @Autowired
    protected EmployeeSpecifier employeeSpecifier;

    public static final String PROPERTY_ACTIONCODE_FILE = "Employee_actionCode";

    protected Logger logger = LoggerFactory.getLogger(EmployeeActionExecutionProxy.class);

    @Override
    public Map<Integer, String> getActionCodeMap(String lanCode) throws ServiceEntityInstallationException {
        String path = EmployeeUIModel.class.getResource("").getPath();
        return systemDefDocActionCodeProxy.getActionCodeMapCustom(lanCode, path, PROPERTY_ACTIONCODE_FILE, null);
    }

    @Override
    public List<DocActionConfigure> getDefDocActionConfigureList() {
        List<DocActionConfigure> defDocActionConfigureList = ServiceCollectionsHelper.asList(
                new DocActionConfigure(EmployeeActionNode.DOC_ACTION_ACTIVE,
                        Employee.STATUS_ACTIVE,
                        ServiceCollectionsHelper.asList(Employee.STATUS_INIT),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(EmployeeActionNode.DOC_ACTION_REINIT,
                        Employee.STATUS_INIT,
                        ServiceCollectionsHelper.asList(Employee.STATUS_ACTIVE),
                        ISystemActionCode.ACID_EDIT),
                new DocActionConfigure(EmployeeActionNode.DOC_ACTION_ARCHIVE,
                        Employee.STATUS_ARCHIVED,
                        ServiceCollectionsHelper.asList(Employee.STATUS_INIT, Employee.STATUS_ACTIVE),
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
    public DocumentContentSpecifier<EmployeeServiceModel, Employee, DocMatItemNode> getDocumentContentSpecifier() {
        return employeeSpecifier;
    }

    @Override
    public DocSplitMergeRequest<Employee, DocMatItemNode> getDocMergeRequest() {
        return null;
    }

    @Override
    public CrossDocConvertRequest<EmployeeServiceModel, DocMatItemNode, ?> getCrossDocCovertRequest() {
        return null;
    }

    @Override
    public ServiceEntityManager getServiceEntityManager() {
        return employeeManager;
    }

    public void executeActionCore(EmployeeServiceModel employeeServiceModel, int docActionCode,
                                  SerialLogonInfo serialLogonInfo)
            throws ServiceModuleProxyException, DocActionException {
        super.defExecuteActionCore(employeeServiceModel, docActionCode,
                null, null, serialLogonInfo);
    }

}
