package com.company.IntelligentPlatform.finance.service;

/**
 * Account Service Generator Interface
 * 
 * @author DylanYang
 * @date May 01 2013
 * 
 */
public interface FinanceServiceGenerator {
	/**
	 * [INTERFACE] Generate AccountTitle Id Generator type
	 * 
	 * @param subAccountTitleIdArray
	 * @param parentAccountTitleId
	 * @param genCategoryType
	 * @return
	 */
	public int accountTitleIdGenerator(int[] subAccountTitleIdArray,
			int parentAccountTitleId, int genCategoryType);

}
