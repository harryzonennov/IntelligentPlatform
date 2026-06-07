package com.company.IntelligentPlatform.platform.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.platform.service.CorporateCustomerManager;
import com.company.IntelligentPlatform.platform.service.MaterialStockKeepUnitManager;
import com.company.IntelligentPlatform.platform.service.MaterialTypeManager;
import com.company.IntelligentPlatform.platform.service.StandardMaterialUnitManager;
import com.company.IntelligentPlatform.platform.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.platform.controller.LogonActionController;
import com.company.IntelligentPlatform.platform.controller.SEListController;
import com.company.IntelligentPlatform.platform.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.platform.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.platform.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.platform.model.ServiceEntityNode;

@Scope("session")
@Controller(value = "materialSKUUnitListController")
@RequestMapping(value = "/materialSKUUnit")
public class MaterialSKUUnitListController extends SEListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;
	
	@Autowired
	protected MaterialStockKeepUnitManager materialStockKeepUnitManager;

	@Autowired
	protected StandardMaterialUnitManager standardMaterialUnitManager;

	@Autowired
	protected CorporateCustomerManager corporateCustomerManager;

	@Autowired
	protected MaterialTypeManager materialTypeManager;

	@Autowired
	protected MaterialSKUUnitServiceUIModelExtension materialSKUUnitServiceUIModelExtension;

	protected List<MaterialSKUUnitUIModel> getModuleListCore(
			List<ServiceEntityNode> rawList)
			throws ServiceEntityInstallationException,
			ServiceEntityConfigureException, ServiceModuleProxyException {
		return serviceBasicUtilityController.convUIModuleList(MaterialSKUUnitUIModel.class, rawList,
				materialStockKeepUnitManager, materialSKUUnitServiceUIModelExtension);
	}

}
