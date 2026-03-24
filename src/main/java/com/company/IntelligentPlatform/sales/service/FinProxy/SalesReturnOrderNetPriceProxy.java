package com.company.IntelligentPlatform.sales.service.FinProxy;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
import com.company.IntelligentPlatform.sales.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.sales.model.SalesReturnOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class SalesReturnOrderNetPriceProxy implements IFinanceAccountValueProxy{
	
	@Autowired
	SalesReturnOrderManager salesReturnOrderManager;

	@Override
	public double getAccountValue(String baseUUID, String client) throws FinanceAccountValueProxyException {
		try {
			SalesReturnOrder salesReturnOrder = (SalesReturnOrder) salesReturnOrderManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							SalesReturnOrder.NODENAME, client, null);		
			return salesReturnOrder.getGrossPrice();
		} catch (ServiceEntityConfigureException e) {
			throw new FinanceAccountValueProxyException(FinanceAccountValueProxyException.SYSTEM_ERROR);
		}
		
	}

}