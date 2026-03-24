package com.company.IntelligentPlatform.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.IntelligentPlatform.common.dto.EmpLogonUserUIModel;
import com.company.IntelligentPlatform.common.service.DocActionException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.StandardSwitchProxy;
import com.company.IntelligentPlatform.common.service.DocFlowProxy;
import com.company.IntelligentPlatform.common.service.DocItemProxy;
import com.company.IntelligentPlatform.common.service.LogonUserException;
import com.company.IntelligentPlatform.common.model.EmpLogonUserReference;
import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.model.LogonInfo;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;
import com.company.IntelligentPlatform.common.model.ServiceBasicKeyStructure;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

@Service
@Transactional
public class EmpLogonUserManager {

    public static final String METHOD_ConvUIToEmpLogonUserReference = "convUIToEmpLogonUserReference";

    public static final String METHOD_ConvEmpLogonToEmpLogonUserUI = "convEmpLogonToEmpLogonUserUI";

    public static final String METHOD_ConvLogonUserToEmpLogonUserUI = "convLogonUserToEmpLogonUserUI";

    @Autowired
    protected StandardSwitchProxy standardSwitchProxy;

    @Autowired
    protected EmployeeManager employeeManager;

    @Autowired
    protected DocItemProxy docItemProxy;

    /**
     * Performs a pre-save validation to check if the logon user is already assigned
     * to another employee.
     *
     * @return The original {@code EmpLogonUserServiceModel} if no duplicates are detected.
     * @throws DocActionException If duplicate logon user assignments are found or a system error occurs.
     */
    public EmpLogonUserServiceModel preCheckDuplicate(EmpLogonUserServiceModel empLogonUserServiceModel, SerialLogonInfo serialLogonInfo) throws DocActionException {
        // Check if the the `mainFlag` value is switch on
        EmpLogonUserReference empLogonUserReference = empLogonUserServiceModel.getEmpLogonUserReference();
        List<ServiceEntityNode> duplicateItemList = docItemProxy.getDuplicateItemList(empLogonUserReference, null, IReferenceNodeFieldConstant.REFUUID,employeeManager);
        if (!ServiceCollectionsHelper.checkNullList(duplicateItemList)){
            try {
                List<String> duplicateIdList = pluckDuplicateEmployeeIds(duplicateItemList, serialLogonInfo.getClient());
                if (!ServiceCollectionsHelper.checkNullList(duplicateIdList)) {
                    String duplicateId = String.join(", ", duplicateIdList);
                    throw new LogonUserException(LogonUserException.PARA_SYSTEM_ERROR, duplicateId);
                }
            } catch (LogonUserException e) {
                throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
            }
        }
        return empLogonUserServiceModel;
    }

    private List<String> pluckDuplicateEmployeeIds(List<ServiceEntityNode> duplicateItemList, String client) throws DocActionException {
        try {
            List<ServiceBasicKeyStructure> keyList = duplicateItemList.stream()
                    .map(employee -> new ServiceBasicKeyStructure(employee.getRootNodeUUID(), IServiceEntityNodeFieldConstant.ROOTNODEUUID))
                    .collect(Collectors.toList());
            List<ServiceEntityNode> employeeList = employeeManager.getEntityNodeListByKeyList(keyList, Employee.NODENAME, client, null);
            return ServiceCollectionsHelper.pluckList(employeeList, IServiceEntityNodeFieldConstant.ID);
        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (NoSuchFieldException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }

    public EmpLogonUserServiceModel updateUniqueDefaultLogonUser(EmpLogonUserServiceModel empLogonUserServiceModel, SerialLogonInfo serialLogonInfo) throws DocActionException {
        try {
            // Check if the the `mainFlag` value is switch on
            EmpLogonUserReference empLogonUserReference = empLogonUserServiceModel.getEmpLogonUserReference();
            if (empLogonUserReference.getMainFlag() != StandardSwitchProxy.SWITCH_ON) {
                return empLogonUserServiceModel;
            }
            List<ServiceEntityNode> allEmpLogonUserList = employeeManager.getEntityNodeListByParentUUID(empLogonUserReference.getParentNodeUUID(),
                    EmpLogonUserReference.NODENAME, empLogonUserReference.getClient());
            docItemProxy.exclusiveExeSelectItemList(ServiceCollectionsHelper.asList(empLogonUserReference), allEmpLogonUserList, null, (serviceEntityNode, serialLogonInfo1) -> {
                EmpLogonUserReference otherEmpLogonUser = (EmpLogonUserReference) serviceEntityNode;
                otherEmpLogonUser.setMainFlag(StandardSwitchProxy.SWITCH_OFF);
                return null;
            }, serialLogonInfo);

        } catch (ServiceEntityConfigureException e) {
            throw new DocActionException(DocActionException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        }
        return empLogonUserServiceModel;
    }

    public void convLogonUserToEmpLogonUserUI(LogonUser logonUser,
                                              EmpLogonUserUIModel empLogonUserUIModel) {
        if (logonUser != null) {
            empLogonUserUIModel.setLogonUserId(logonUser.getId());
            empLogonUserUIModel.setLogonUserName(logonUser.getName());
        }
    }

    public void convUIToEmpLogonUserReference(EmpLogonUserUIModel empLogonUserUIModel,
                                              EmpLogonUserReference rawEntity) {
        if (rawEntity != null & empLogonUserUIModel != null) {
            DocFlowProxy.convUIToServiceEntityNode(empLogonUserUIModel, rawEntity);
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserUIModel
                    .getRefUUID())) {
                rawEntity.setRefUUID(empLogonUserUIModel.getRefUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserUIModel
                    .getRefNodeName())) {
                rawEntity.setRefNodeName(empLogonUserUIModel.getRefNodeName());
            }
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserUIModel
                    .getRefSEName())) {
                rawEntity.setRefSEName(empLogonUserUIModel.getRefSEName());
            }
            rawEntity.setMainFlag(empLogonUserUIModel.getMainFlag());
        }
    }

    /**
     * [Internal method] Convert from SE model to UI model
     *
     * @return <code>SEUIComModel</code> instance
     */
    public void convEmpLogonToEmpLogonUserUI(
            EmpLogonUserReference empLogonUserReference,
            EmpLogonUserUIModel empLogonUserUIModel, LogonInfo logonInfo) throws ServiceEntityInstallationException {
        if (empLogonUserReference != null) {
            DocFlowProxy.convServiceEntityNodeToUIModel(empLogonUserReference, empLogonUserUIModel);
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserReference
                    .getRefUUID())) {
                empLogonUserUIModel
                        .setRefUUID(empLogonUserReference.getRefUUID());
            }
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserReference
                    .getRefNodeName())) {
                empLogonUserUIModel.setRefNodeName(empLogonUserReference
                        .getRefNodeName());
            }
            if (!ServiceEntityStringHelper.checkNullString(empLogonUserReference
                    .getRefSEName())) {
                empLogonUserUIModel.setRefSEName(empLogonUserReference
                        .getRefSEName());
            }
            empLogonUserUIModel.setMainFlag(empLogonUserReference.getMainFlag());
            if (logonInfo != null) {
                String mainFlagValue = standardSwitchProxy.getSwitchValue(empLogonUserReference.getMainFlag(), logonInfo.getLanguageCode());
                empLogonUserUIModel.setMainFlagValue(mainFlagValue);
            }
        }
    }

    public Map<Integer, String> initMainFlagMap(String languageCode)
            throws ServiceEntityInstallationException {
        return standardSwitchProxy.getSimpleSwitchMap(languageCode);
    }

}
