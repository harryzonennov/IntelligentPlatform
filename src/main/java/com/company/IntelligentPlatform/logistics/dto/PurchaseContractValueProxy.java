package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
import com.company.IntelligentPlatform.logistics.service.PurchaseContractManager;
import com.company.IntelligentPlatform.logistics.model.PurchaseContract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class PurchaseContractValueProxy implements IFinanceAccountValueProxy{
	
	@Autowired
	PurchaseContractManager purchaseContractManager;

	@Override
	public double getAccountValue(String baseUUID, String client) throws FinanceAccountValueProxyException {
		try {
			PurchaseContract purchaseContract = (PurchaseContract) purchaseContractManager
					.getEntityNodeByUUID(baseUUID,
							PurchaseContract.NODENAME, client);
			return purchaseContract.getGrossPrice();
		} catch (ServiceEntityConfigureException e) {
			throw new FinanceAccountValueProxyException(FinanceAccountValueProxyException.SYSTEM_ERROR);
		}
		
	}

}
