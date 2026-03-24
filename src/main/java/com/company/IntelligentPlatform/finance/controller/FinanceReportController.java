package com.company.IntelligentPlatform.finance.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.company.IntelligentPlatform.finance.dto.FinAccountUIModel;
import com.company.IntelligentPlatform.finance.service.FinAccountManager;
import com.company.IntelligentPlatform.finance.service.FinAccountTitleManager;
import com.company.IntelligentPlatform.finance.model.FinAccount;
import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;
import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

@Scope("session")
@Controller(value = "financeReportController")
@RequestMapping(value = "/financeReport")
public class FinanceReportController extends SEEditorController {
	@Autowired
	FinAccountManager finAccountManager;

	@Autowired
	FinAccountTitleManager finAccountTitleManager;

	protected Date date;

	protected String accountTitleNameStr;

	FinAccountTitle accountTitle;

	@RequestMapping(value = "/loadModuleList")
	public ModelAndView loadModuleList(String keyValue, String keyName) {
		List<FinAccountUIModel> accountUIModelsList = new ArrayList<FinAccountUIModel>();
		// getUI model

		ModelAndView mav = new ModelAndView();
		try {

			mav.setViewName("FinanceReport");
			mav.addObject("financeReport", accountUIModelsList);
			return mav;
		} catch (Exception e) {
			// TODO: handle exception
			mav = new ModelAndView();
			mav.setViewName(getErrorPage());
			mav.addObject(MESSAGE_TOKEN, e.getMessage());
			return mav;
		}

	}

	/**
	 * Convert Account to FinAccountUIModel
	 * 
	 * @param account
	 * @param accountUIModel
	 * @throws ServiceEntityConfigureException
	 */
	public void converAccountToAccountUIModel(FinAccount account,
			FinAccountUIModel accountUIModel)
			throws ServiceEntityConfigureException {
		accountUIModel.setUuid(account.getUuid());
		accountUIModel.setId(account.getId());
		accountUIModel.setAccountObjectName(account.getAccountObject());
		accountUIModel.setDocumentType(IDefDocumentResource.DOCUMENT_TYPE_RECEIPT);
		accountUIModel.setFinAccountType(FinAccountTitle.FIN_ACCOUNTTYPE_DEBIT);
		accountUIModel.setPaymentType(account.getPaymentType());
		accountUIModel.setAmount(account.getAmount());
		accountUIModel.setAccountTitleUUID(account.getAccountTitleUUID());
		accountUIModel.setNote(account.getNote());
		date = new Date();			
		accountUIModel.setDateStr(DefaultDateFormatConstant.DATE_MIN_FORMAT.format(date));
		accountUIModel.setAccountTitleName(finAccountTitleManager
				.getAccountTitleName(account));
	}
}
