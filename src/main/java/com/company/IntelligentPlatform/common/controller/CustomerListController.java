package com.company.IntelligentPlatform.common.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.IntelligentPlatform.common.dto.CustomerJSONModel;
import com.company.IntelligentPlatform.common.service.CustomerManager;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.dto.AccountObjectJSONModel;
import com.company.IntelligentPlatform.common.dto.AccountTypeSelect;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceDropdownListHelper;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.CityManager;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONDataConstants;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.IndividualCustomer;
import com.company.IntelligentPlatform.common.model.IDefResourceAuthorizationObject;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.City;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Scope("session")
@Controller(value = "customerListController")
@RequestMapping(value = "/customer")
public class CustomerListController extends AccountListController {

	@Autowired
	protected ServiceDropdownListHelper serviceDropdownListHelper;

	@Autowired
	protected CustomerManager customerManager;

	@Autowired
	protected CityManager cityManager;

	@Autowired
	protected LogonActionController logonActionController;

	@Autowired
	protected ServiceBasicUtilityController serviceBasicUtilityController;

	public static final String REQ_FIELD_ROLE = "role";

	public static final String AOID_RESOURCE = IDefResourceAuthorizationObject.AOID_COR_CUSTOMER;

	protected String getAccountTypeMessage(String key) throws IOException {
		String path = AccountTypeSelect.class.getResource("").getPath();
		Map<String, String> accountTypeSource = serviceDropdownListHelper
				.getDropDownMap(path, AccountTypeSelect.class.getSimpleName(), ServiceLanHelper.getDefault());
		String value = accountTypeSource.get(key);
		return value;
	}

	/**
	 * Get all the possible account object instance
	 *
	 * @param request
	 * @return
	 * @throws ServiceEntityConfigureException
	 */
	@RequestMapping(value = "/getCustomerByType", produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getCustomerByType(@RequestBody AccountObjectJSONModel request) {
		try {
			String accountObjectUUID = request.getUuid();
			int accountType = request.getAccountType();
			serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
			LogonUser logonUser = logonActionController.getLogonUser();
			if (logonUser == null) {
				throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
			}
			Account accountObject;
			accountObject = customerManager.getAllCustomer(accountObjectUUID, logonUser.getClient(), accountType);
			if (accountObject == null) {
				return ServiceJSONDataConstants.DEF_UNKONWON_ERROR_JSON;
			} else {
				CustomerJSONModel customerJSONModel = new CustomerJSONModel();
				City city = (City) cityManager
						.getEntityNodeByKey(accountObject.getRefCityUUID(), IServiceEntityNodeFieldConstant.UUID, City.NODENAME, null);
				String mobile = null;
				if (accountObject.getServiceEntityName().equals(IndividualCustomer.SENAME)) {
					IndividualCustomer individualCustomer = (IndividualCustomer) accountObject;
					mobile = individualCustomer.getMobile();
				}
				convCustomerObjectJSONModle(customerJSONModel, accountObject, city, mobile);
				return ServiceJSONParser.genDefOKJSONObject(customerJSONModel);
			}
		} catch (ServiceEntityConfigureException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
		} catch (LogonInfoException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		} catch (AuthorizationException e) {
			return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
		}
	}

	protected void convCustomerObjectJSONModle(CustomerJSONModel customerJSONModel, Account customer, City city, String mobile) {
		if (customer != null) {
			customerJSONModel.setUuid(customer.getUuid());
			customerJSONModel.setId(customer.getId());
			customerJSONModel.setName(customer.getName());
			customerJSONModel.setAddress(customer.getAddress());
			customerJSONModel.setTelephone(customer.getTelephone());
			if (mobile != null && !mobile.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
				customerJSONModel.setMobile(mobile);
			} else {
				customerJSONModel.setMobile(customer.getTelephone());
			}
			customerJSONModel.setPostcode(customer.getPostcode());
			if (city != null) {
				customerJSONModel.setCityName(city.getName());
			}
			customerJSONModel.setAccountType(accountManager.getAccountType(customer));
			customerJSONModel.setRefCityUUID(customer.getRefCityUUID());
			customerJSONModel.setTownZone(customer.getTownZone());
			customerJSONModel.setSubArea(customer.getSubArea());
			customerJSONModel.setStreetName(customer.getStreetName());
			customerJSONModel.setHouseNumber(customer.getHouseNumber());
		}
	}

}
