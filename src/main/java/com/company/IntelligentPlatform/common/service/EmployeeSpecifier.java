package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.dto.EmployeeServiceUIModelExtension;
import com.company.IntelligentPlatform.common.dto.EmployeeUIModel;
import com.company.IntelligentPlatform.common.service.IAccountI18nPackage;
import com.company.IntelligentPlatform.common.controller.ServiceUIModelExtension;
import com.company.IntelligentPlatform.common.service.*;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.EmployeeActionNode;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.DocInvolveParty;
import com.company.IntelligentPlatform.common.model.DocMatItemNode;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeSpecifier extends DocumentContentSpecifier<EmployeeServiceModel, Employee, DocMatItemNode> {

    @Autowired
    protected EmployeeServiceUIModelExtension employeeServiceUIModelExtension;

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected EmployeeIdHelper employeeIdHelper;

    @Override
    public int getDocumentType() {
        return 0;
    }

    @Override
    public Integer getDocumentStatus(Employee employee) {
        return employee.getStatus();
    }

    @Override
    public ServiceEntityManager getDocumentManager() {
        return employeeManager;
    }

    @Override
    public Employee setDocumentStatus(
            DocActionExecutionProxy.ServiceEntityTargetStatus serviceEntityTargetStatus) {
        Employee employee = (Employee) serviceEntityTargetStatus.getServiceEntityNode();
        employee.setStatus(serviceEntityTargetStatus.getTargetStatus());
        return employee;
    }

    @Override
    public ServiceDefaultIdGenerateHelper getIdGenerateHelper() {
        return employeeIdHelper;
    }

    @Override
    public DocInvolveParty getDocInvolveParty(int partyRole, EmployeeServiceModel serviceModule) {
        return null;
    }

    @Override
    public DocInvolveParty getDocItemInvolveParty(int partyRole, ServiceModule serviceModule) {
        return null;
    }

    @Override
    public void setDocInvolveParty(EmployeeServiceModel serviceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public void setDocItemInvolveParty(ServiceModule itemServiceModule, DocInvolveParty docInvolveParty) {

    }

    @Override
    public List<?> getDocMatItemServiceModuleList(EmployeeServiceModel employeeServiceModel) {
        return null;
    }

    @Override
    public Map<Integer, String> getStatusMap(String lanCode) throws ServiceEntityInstallationException {
        return employeeManager.initStatusMap(lanCode);
    }

    @Override
    public Map<Integer, String> getInvolvePartyMap(String lanCode) throws ServiceEntityInstallationException {
        return null;
    }

    @Override
    public DocMetadata getDocMetadata() {
        return new DocMetadata(Employee.class, EmployeeServiceModel.class, EmployeeActionNode.class,
                null, null, null);
    }

    @Override
    public ServiceUIModelExtension getDocServiceUIModelExtension() {
        return employeeServiceUIModelExtension;
    }

    @Override
    public List<UIModelClassMap> getUIModelClassMap() {
        List<UIModelClassMap> uiModelClassMap = new ArrayList<>();
        uiModelClassMap.add(new UIModelClassMap(Employee.SENAME, EmployeeUIModel.class));
        return uiModelClassMap;
    }


    @Override
    public List<PropertyMap> getDefFieldProperPathMap() {
        List<PropertyMap> propertyMapList = new ArrayList<>();
        String basePath = IAccountI18nPackage.class.getResource("").getPath();
        propertyMapList.add(new PropertyMap(Employee.SENAME, basePath + "Employee"));
        propertyMapList.add(new PropertyMap("EmployeeOrg", basePath + "EmployeeOrg"));
        return propertyMapList;
    }

    @Override
    public void traverseMatItemNode(EmployeeServiceModel employeeServiceModel,
                                    DocActionExecutionProxy.DocItemActionExecution docItemActionCallback,
                                            SerialLogonInfo serialLogonInfo) throws DocActionException {

    }


}
