package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.dto.EmpLogonUserUIModel;
import com.company.IntelligentPlatform.common.dto.EmployeeOrgUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.common.model.EmployeeOrgReference;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.util.Map;

@Service
@Transactional
public class EmployeeOrgManager {

    public static final String METHOD_ConvUIToEmployeeOrgReference = "convUIToEmployeeOrgReference";

    public static final String METHOD_ConvEmployeeOrgToEmployeeOrgUIModel = "convEmployeeOrgToEmployeeOrgUIModel";

    public static final String METHOD_ConvOrganizationToEmployeeOrgUIModel = "convOrganizationToEmployeeOrgUIModel";

    @Autowired
    protected OrganizationManager organizationManager;

    @Autowired
    protected EmployeeManager employeeManager;

    protected Logger logger = LoggerFactory.getLogger(EmployeeOrgManager.class);

    public void convUIToEmployeeOrgReference(EmployeeOrgUIModel employeeOrgUIModel,
                                             EmployeeOrgReference rawEntity) {
        if (rawEntity != null & employeeOrgUIModel != null) {
            DocFlowProxy.convUIToServiceEntityNode(employeeOrgUIModel, rawEntity);
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgUIModel
                    .getRefUUID())) {
                rawEntity.setRefUUID(employeeOrgUIModel.getRefUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgUIModel
                    .getRefNodeName())) {
                rawEntity.setRefNodeName(employeeOrgUIModel.getRefNodeName());
            }
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgUIModel
                    .getRefSEName())) {
                rawEntity.setRefSEName(employeeOrgUIModel.getRefSEName());
            }
            rawEntity.setEmployeeRole(employeeOrgUIModel.getEmployeeRole());
        }
    }

    public void convEmployeeOrgToEmployeeOrgUIModel(
            EmployeeOrgReference employeeOrgReference,
            EmployeeOrgUIModel employeeOrgUIModel, LogonInfo logonInfo) {
        if (employeeOrgReference != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(employeeOrgReference,
                    employeeOrgUIModel);
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgReference
                    .getRefUUID())) {
                employeeOrgUIModel
                        .setRefUUID(employeeOrgReference.getRefUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgReference
                    .getRefNodeName())) {
                employeeOrgUIModel.setRefNodeName(employeeOrgReference
                        .getRefNodeName());
            }
            if (!ServiceEntityStringHelper.checkNullString(employeeOrgReference
                    .getRefSEName())) {
                employeeOrgUIModel.setRefSEName(employeeOrgReference
                        .getRefSEName());
            }
            employeeOrgUIModel.setEmployeeRole(employeeOrgReference
                    .getEmployeeRole());
            if(logonInfo != null){
                try {
                    Map<Integer, String> workRoleMap = employeeManager.initWorkRoleMap(logonInfo.getLanguageCode(),
                            logonInfo.getClient(), false);
                    employeeOrgUIModel.setEmployeeRoleValue(workRoleMap.get(employeeOrgReference.getEmployeeRole()));
                } catch (ServiceEntityInstallationException e) {
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "workRole"));
                }
            }
        }
    }

    public void convOrganizationToEmployeeOrgUIModel(Organization organization,
                                                     EmployeeOrgUIModel employeeOrgUIModel, LogonInfo logonInfo) {
        if (organization != null) {
            employeeOrgUIModel.setOrganizationId(organization.getId());
            employeeOrgUIModel.setOrganizationName(organization.getName());
            employeeOrgUIModel.setOrganizationFunction(organization
                    .getOrganizationFunction());
            if(logonInfo != null){
                try {
                    Map<Integer, String> functionMap = organizationManager
                            .initOrgFunctionMap(logonInfo.getLanguageCode(), organization.getClient(), true);
                    employeeOrgUIModel.setOrganizationFunctionValue(functionMap
                            .get(organization.getOrganizationFunction()));
                } catch (ServiceEntityInstallationException e) {
                    // Just continue;
                    logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, "organizationFunction"));
                }
            }
        }
    }

}
