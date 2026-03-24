package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.Employee;
import com.company.IntelligentPlatform.common.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Replaces: ThorsteinPlatform EmployeeManager (ServiceEntityManager subclass)
 */
@Service
@Transactional
public class EmployeeService extends ServiceEntityService {

	@Autowired
	protected EmployeeRepository employeeRepository;

	public Employee create(Employee employee, String userUUID, String orgUUID) {
		employee.setStatus(Employee.STATUS_INIT);
		employee.setAccountType(Employee.ACCOUNTTYPE_EMPLOYEE);
		return insertSENode(employeeRepository, employee, userUUID, orgUUID);
	}

	@Transactional(readOnly = true)
	public Employee getByUuid(String uuid) {
		return getEntityNodeByUUID(employeeRepository, uuid);
	}

	@Transactional(readOnly = true)
	public List<Employee> getByClient(String client) {
		return employeeRepository.findByClient(client);
	}

	public Employee update(Employee employee, String userUUID, String orgUUID) {
		return updateSENode(employeeRepository, employee, userUUID, orgUUID);
	}

	public void setStatus(String uuid, int status, String userUUID, String orgUUID) {
		Employee employee = employeeRepository.findById(uuid).orElseThrow();
		employee.setStatus(status);
		updateSENode(employeeRepository, employee, userUUID, orgUUID);
	}

	public void delete(String uuid) {
		deleteSENode(employeeRepository, uuid);
	}

}
