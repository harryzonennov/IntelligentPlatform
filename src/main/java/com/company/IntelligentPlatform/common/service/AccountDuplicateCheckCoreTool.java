package com.company.IntelligentPlatform.common.service;

import com.company.IntelligentPlatform.common.model.Account;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Account default check duplicate 
 * @author Zhang,hang
 *
 */
public class AccountDuplicateCheckCoreTool {
	
	public static final double NAME_SIMILAR_DEGRESS = 0.75;
	
	public static boolean checkDefaultByName(Account account, Account rawAccount){
		if(account.getName() == null || rawAccount.getName() == null){
			return false;
		}
		double similarDegree = ServiceEntityStringHelper.SimilarDegree(account.getName(), rawAccount.getName());
		if(similarDegree > NAME_SIMILAR_DEGRESS){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkDefaultByTelephone(Account account, Account rawAccount){
		if(account.getTelephone() == null || rawAccount.getTelephone() == null){
			return false;
		}
		if(account.getTelephone().equals(rawAccount.getTelephone())){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkDefaultByMobile(String mobile1, String mobile2){
		if(mobile1 == null || mobile2 == null){
			return false;
		}
		if(mobile1.equals(mobile2)){
			return true;
		}else{
			return false;
		}
	}

}
