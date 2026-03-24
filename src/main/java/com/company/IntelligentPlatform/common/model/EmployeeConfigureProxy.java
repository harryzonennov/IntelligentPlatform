package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [Employee]
 * 
 * @author
 * @date Fri Aug 01 16:58:04 CST 2014
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
@Repository
public class EmployeeConfigureProxy extends ServiceEntityConfigureProxy {

	@Override
	public void initConfig() {
		super.initConfig();
		super.setPackageName("platform.foundation");
		List<ServiceEntityConfigureMap> seConfigureMapList = Collections
				.synchronizedList(new ArrayList<>());
		// Init configuration of Employee [ROOT] node
		ServiceEntityConfigureMap employeeConfigureMap = new ServiceEntityConfigureMap();
		employeeConfigureMap.setParentNodeName(" ");
		employeeConfigureMap.setNodeName("ROOT");
		employeeConfigureMap.setNodeType(Employee.class);
		employeeConfigureMap.setTableName("Employee");
		employeeConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
		employeeConfigureMap.addNodeFieldMap("accountType", int.class);
		employeeConfigureMap.addNodeFieldMap("address", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("telephone",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("fax", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("email", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("webPage", java.lang.String.class);
		employeeConfigureMap
				.addNodeFieldMap("postcode", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("countryName",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("stateName",
				java.lang.String.class);
		employeeConfigureMap
				.addNodeFieldMap("cityName", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("refCityUUID",
				java.lang.String.class);
		employeeConfigureMap
				.addNodeFieldMap("townZone", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("subArea", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("streetName",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("houseNumber",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("regularType", int.class);
		employeeConfigureMap.addNodeFieldMap("taxNumber",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("bankAccount",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("depositBank",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("mobile", java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("operateType", int.class);
		employeeConfigureMap.addNodeFieldMap("identification",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("workRole", int.class);
		employeeConfigureMap.addNodeFieldMap("jobLevel", int.class);
		employeeConfigureMap.addNodeFieldMap("boardDate", java.util.Date.class);
		employeeConfigureMap.addNodeFieldMap("birthDate", java.util.Date.class);
		employeeConfigureMap.addNodeFieldMap("age", int.class);
		employeeConfigureMap.addNodeFieldMap("gender", int.class);
		employeeConfigureMap.addNodeFieldMap("driverLicenseType", int.class);
		employeeConfigureMap.addNodeFieldMap("driverLicenseNumber",
				java.lang.String.class);
		employeeConfigureMap.addNodeFieldMap("status", int.class);
		
		seConfigureMapList.add(employeeConfigureMap);
		// Init configuration of Employee [EmployeeOrgReference] node
		ServiceEntityConfigureMap employeeOrgReferenceConfigureMap = new ServiceEntityConfigureMap();
		employeeOrgReferenceConfigureMap.setParentNodeName("ROOT");
		employeeOrgReferenceConfigureMap.setNodeName("EmployeeOrgReference");
		employeeOrgReferenceConfigureMap
				.setNodeType(EmployeeOrgReference.class);
		employeeOrgReferenceConfigureMap.setTableName("EmployeeOrgReference");
		employeeOrgReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		employeeOrgReferenceConfigureMap.addNodeFieldMap("employeeRole",
				int.class);
		seConfigureMapList.add(employeeOrgReferenceConfigureMap);
		// Init configuration of Employee [EmpLogonUserReference] node
		ServiceEntityConfigureMap empLogonUserReferenceConfigureMap = new ServiceEntityConfigureMap();
		empLogonUserReferenceConfigureMap.setParentNodeName("ROOT");
		empLogonUserReferenceConfigureMap.setNodeName("EmpLogonUserReference");
		empLogonUserReferenceConfigureMap
				.setNodeType(EmpLogonUserReference.class);
		empLogonUserReferenceConfigureMap.setTableName("EmpLogonUserReference");
		empLogonUserReferenceConfigureMap.setFieldList(super
				.getBasicReferenceFieldMap());
		employeeOrgReferenceConfigureMap.addNodeFieldMap("mainFlag",
				int.class);
		seConfigureMapList.add(empLogonUserReferenceConfigureMap);

		// Init configuration of Employee [employeeAttachment] node
		ServiceEntityConfigureMap employeeAttachmentConfigureMap = new ServiceEntityConfigureMap();
		employeeAttachmentConfigureMap.setParentNodeName(Employee.NODENAME);
		employeeAttachmentConfigureMap.setNodeName(EmployeeAttachment.NODENAME);
		employeeAttachmentConfigureMap.setNodeType(EmployeeAttachment.class);
		employeeAttachmentConfigureMap
				.setTableName(EmployeeAttachment.NODENAME);
		employeeAttachmentConfigureMap.setFieldList(super
				.getBasicSENodeFieldMap());
		employeeAttachmentConfigureMap.addNodeFieldMap("content", byte[].class);
		employeeAttachmentConfigureMap.addNodeFieldMap("fileType",
				java.lang.String.class);
		seConfigureMapList.add(employeeAttachmentConfigureMap);

		// Init configuration of Material [LogonUserActionNode] node
		ServiceEntityConfigureMap employeeActionNodeConfigureMap = new ServiceEntityConfigureMap();
		employeeActionNodeConfigureMap.setParentNodeName(Employee.NODENAME);
		employeeActionNodeConfigureMap.setNodeName(EmployeeActionNode.NODENAME);
		employeeActionNodeConfigureMap.setNodeType(EmployeeActionNode.class);
		employeeActionNodeConfigureMap
				.setTableName(EmployeeActionNode.NODENAME);
		employeeActionNodeConfigureMap.setFieldList(super
				.getBasicActionCodeNodeMap());
		seConfigureMapList.add(employeeActionNodeConfigureMap);
		// End
		super.setSeConfigMapList(seConfigureMapList);
	}

}
