package com.company.IntelligentPlatform.common.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class ServiceMD5EncodeProxy implements IServiceEncodeProxy {

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	private String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	public String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public String getEncodeValue(String rawValue) throws ServiceEncodeException {
		if (rawValue == null
				|| rawValue.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
			return null;
		} else {
			String value = null;
			try {
				value = new String(rawValue);
				MessageDigest md = MessageDigest.getInstance("MD5");
				value = byteToString(md.digest(rawValue.getBytes()));
			} catch (NoSuchAlgorithmException ex) {
				throw new ServiceEncodeException(
						ServiceEncodeException.PARA_SYSTEM_WRONG,
						ex.getMessage());
			}
			return value;
		}
	}

}
