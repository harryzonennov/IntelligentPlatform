package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.company.IntelligentPlatform.common.service.MaterialException;
import com.company.IntelligentPlatform.common.service.RegisteredProductException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.model.SimpleSEMessageResponse;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends
		ResponseEntityExceptionHandler {

	@ExceptionHandler({ RegisteredProductException.class,
			MaterialException.class })
	public @ResponseBody String handleMaterialException(RuntimeException ex,
			WebRequest request) {
		List<SimpleSEMessageResponse> errorMessageList = new ArrayList<SimpleSEMessageResponse>();
		SimpleSEMessageResponse messageResponse = new SimpleSEMessageResponse();
		ServiceEntityRuntimeException serviceEntityRuntimeException = (ServiceEntityRuntimeException) ex;
		messageResponse.setMessageContent(serviceEntityRuntimeException
				.getErrorMessage());
		messageResponse
				.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
		messageResponse.setRefException(serviceEntityRuntimeException);
		messageResponse.setErrorCode(serviceEntityRuntimeException
				.getErrorCode());
		errorMessageList.add(messageResponse);
		return ServiceJSONParser.generateErrorJSONMessageArray(errorMessageList);
	}
	
	@ExceptionHandler({UnexpectedRollbackException.class, TransactionException.class})
	public @ResponseBody String handleTransactionException(TransactionException ex,
			WebRequest request) {
		List<SimpleSEMessageResponse> errorMessageList = new ArrayList<SimpleSEMessageResponse>();
		SimpleSEMessageResponse messageResponse = new SimpleSEMessageResponse();
		messageResponse.setMessageContent(ex.getMessage());
		messageResponse
				.setMessageLevel(SimpleSEMessageResponse.MESSAGELEVEL_ERROR);
		errorMessageList.add(messageResponse);
		return ServiceJSONParser.generateErrorJSONMessageArray(errorMessageList);
	}

}
