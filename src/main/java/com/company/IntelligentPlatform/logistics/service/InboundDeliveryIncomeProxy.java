package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.finance.service.FinanceAccountValueProxyException;
import com.company.IntelligentPlatform.finance.service.IFinanceAccountValueProxy;
import com.company.IntelligentPlatform.logistics.service.InboundDeliveryManager;
import com.company.IntelligentPlatform.logistics.model.InboundDelivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;


@Service
public class InboundDeliveryIncomeProxy implements IFinanceAccountValueProxy{
	
	@Autowired
	InboundDeliveryManager inboundDeliveryManager;

	@Override
	public double getAccountValue(String baseUUID, String client) throws FinanceAccountValueProxyException {
		try {
			InboundDelivery inboundDelivery = (InboundDelivery) inboundDeliveryManager
					.getEntityNodeByKey(baseUUID,
							IServiceEntityNodeFieldConstant.UUID,
							InboundDelivery.NODENAME, client,null);		
			return inboundDelivery.getGrossInboundFee();
		} catch (ServiceEntityConfigureException e) {
			throw new FinanceAccountValueProxyException(FinanceAccountValueProxyException.SYSTEM_ERROR);
		}
		
	}

}
