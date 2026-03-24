package com.company.IntelligentPlatform.finance.service;

public interface IFinanceAccountValueProxy {
	
	public double getAccountValue(String baseUUID, String client) throws FinanceAccountValueProxyException;

}
