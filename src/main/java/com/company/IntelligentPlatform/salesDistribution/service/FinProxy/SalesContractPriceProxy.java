package com.company.IntelligentPlatform.salesDistribution.service.FinProxy;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
import com.company.IntelligentPlatform.salesDistribution.service.SalesContractManager;
import com.company.IntelligentPlatform.salesDistribution.model.SalesContract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.platform.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.platform.model.ServiceEntityConfigureException;

@Service
public class SalesContractPriceProxy implements IFinanceAccountValueProxy{
	
	@Autowired
	SalesContractManager salesContractManager;

	@Override
	public double getAccountValue(String baseUUID, String client) throws FinanceAccountValueProxyException {
		try {
			SalesContract salesContract = (SalesContract) salesContractManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							SalesContract.NODENAME, client, null);		
			return salesContract.getGrossPrice();
		} catch (ServiceEntityConfigureException e) {
			throw new FinanceAccountValueProxyException(FinanceAccountValueProxyException.SYSTEM_ERROR);
		}
		
	}

}