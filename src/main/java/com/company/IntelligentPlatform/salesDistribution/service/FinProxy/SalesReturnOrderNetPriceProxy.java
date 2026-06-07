package com.company.IntelligentPlatform.salesDistribution.service.FinProxy;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
import com.company.IntelligentPlatform.salesDistribution.service.SalesReturnOrderManager;
import com.company.IntelligentPlatform.salesDistribution.model.SalesReturnOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

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