package com.company.IntelligentPlatform.common.dto;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ISystemDefaultRole;
import com.company.IntelligentPlatform.common.model.Role;

/**
 * Proxy class for define the view name by authorization role
 * Pay attention: this class is cross-component
 * @author Zhang,Hang
 *
 */
@Service
public class RoleViewNameMapProxy {
	
	// [Pay attention] 
	/**
	 * Central Logic to return default view name by Role
	 * 
	 * @return
	 */
	public String getRoleDefaultViewName2(Role role) {
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_FIN_ACCOUNTANT)) {
			return "redirect:../finAccount/loadModuleList.html";
		}
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_FIN_CASHIER)) {
			return "redirect:../finAccount/loadModuleList.html";
		}
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_LCM_COUNTER)) {
			return "redirect:../logistics/getLogisctisMap.html";
		}
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_LCM_GUEST)) {
			return "forward:/logistics/getLogisctisMap.html";
		}
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_LCM_MANAGER)) {
			return "forward:/logistics/getLogisctisMap.html";
		}
		if (role.getId().equals(ISystemDefaultRole.ROLE_ID_SYS_ADM)) {
			return "redirect:../serviceExceptionRecord/loadModuleList.html";
		}
		return null;
	}

}
