package com.company.IntelligentPlatform.common.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.company.IntelligentPlatform.common.controller.DoubleEditor;
import com.company.IntelligentPlatform.common.controller.IntegerEditor;
import com.company.IntelligentPlatform.common.model.DefaultDateFormatConstant;

/**
 * Basic Service Entity UI Controller
 * 
 * @author Zhang,Hang
 * 
 * @date Dec 15, 2012
 */
public class SEBasicController {

	public static final String MESSAGE_TOKEN = "msg";
	
	public static final String COMMON_NONIMP_PAGE = "ComNonImplement";

	protected String successPage;

	protected String errorPage = "CommonError";	

	protected String logonErrorPage = "LogonAction";

	public static final int UIFLAG_STANDARD = 1;

	public static final int UIFLAG_FRAME = 2;

	public static final int UIFLAG_CHOOSER = 3;
	
	public static final String ENV_DEV = "dev";
	
	public static final String ENV_PROD = "prod";

	public static final String LABEL_UIFLAG = "uiFlag";
	
	public static final String LABEL_ENV = "env";
	
	@Value(value="${env}")
	protected String env;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(
				DefaultDateFormatConstant.DATE_MIN_FORMAT, true);
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(int.class, new IntegerEditor());
		binder.registerCustomEditor(double.class, new DoubleEditor());
	}

	public String getSuccessPage() {
		return successPage;
	}

	public void setSuccessPage(String successPage) {
		this.successPage = successPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getLogonErrorPage() {
		return logonErrorPage;
	}

	public void setLogonErrorPage(String logonErrorPage) {
		this.logonErrorPage = logonErrorPage;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

}
