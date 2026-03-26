package com.company.IntelligentPlatform.common.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.company.IntelligentPlatform.common.service.ServiceEntityException;
import com.company.IntelligentPlatform.common.service.ServiceEntityRuntimeException;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceEntityStringHelper {

	public static final String EMPTYSTRING = "";

	public static final String ZEROSTRING = "0";

	public static final String TRUE = "true";

	public static final String FALSE = "false";

	public static String headerToUpperCase(String str) {
		String header = getHeader(str);
		return str.replaceFirst(header, header.toUpperCase());
	}

	public static String headerToLowerCase(String str) {
		String header = getHeader(str);
		return str.replaceFirst(header, header.toLowerCase());
	}

	public static String getHeader(String str) {
		return str.substring(0, 1);
	}

	public static String removePostfix(String str, String postfix) {
		if (ServiceEntityStringHelper.checkNullString(str) || ServiceEntityStringHelper.checkNullString(postfix)) {
			return str;
		}
		if (str.endsWith(postfix)) {
			return str.substring(0, str.length() - postfix.length());
		}
		return str;
	}

	/**
	 * Check weather the string variable is null value
	 *
	 * @param rawString
	 * @return
	 */
	public static boolean checkNullString(String rawString) {
        return rawString == null || rawString.equals(EMPTYSTRING);
    }

	/**
	 * Check weather the string variable is null value including string equals zero string
	 * @param rawString
	 * @return
	 */
	public static boolean checkNullStringExtend(String rawString) {
		boolean checkNullCore = checkNullString(rawString);
		if(checkNullCore){
			return true;
		}
		return rawString.equals(ZEROSTRING);
	}

	public static int getUppderCaseTimes(String str) {
		if (str == null) {
			return 0;
		}
		int localTimes = 1;
		for (int i = str.length() - 1; i >= 0; i--) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				localTimes = localTimes + 1;
			} else {
				continue;
			}
		}
		return localTimes;
	}

	/**
	 * Generate Default Error message
	 *
	 * @param sourceException
	 * @param addErrorMessage
	 * @return
	 */
	public static String genDefaultErrorMessage(ServiceEntityException sourceException, String addErrorMessage) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[").append(sourceException.getClass().getSimpleName()).append("]:");
		if (!ServiceEntityStringHelper.checkNullString(addErrorMessage)) {
			strBuffer.append(addErrorMessage).append(":");
		}
		if (!ServiceEntityStringHelper.checkNullString(sourceException.getErrorMessage())) {
			strBuffer.append(sourceException.getErrorMessage());
		}
		return strBuffer.toString();
	}

	/**
	 * Generate Default Error message
	 *
	 * @param sourceException
	 * @param addErrorMessage
	 * @return
	 */
	public static String genDefaultErrorMessage(ServiceEntityRuntimeException sourceException, String addErrorMessage) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[").append(sourceException.getClass().getSimpleName()).append("]:");
		if (!ServiceEntityStringHelper.checkNullString(addErrorMessage)) {
			strBuffer.append(addErrorMessage).append(":");
		}
		if (!ServiceEntityStringHelper.checkNullString(sourceException.getErrorMessage())) {
			strBuffer.append(sourceException.getErrorMessage());
		}
		return strBuffer.toString();
	}

	/**
	 * Generate Default Error message
	 *
	 * @param sourceException
	 * @param addErrorMessage
	 * @return
	 */
	public static String genDefaultErrorMessage(Exception sourceException, String addErrorMessage) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[").append(sourceException.getClass().getSimpleName()).append("]:");
		if (!ServiceEntityStringHelper.checkNullString(addErrorMessage)) {
			strBuffer.append(addErrorMessage).append(":");
		}
		if(sourceException instanceof InvocationTargetException){
			Throwable targetException = ((InvocationTargetException) sourceException).getTargetException();
			if(targetException != null){
				strBuffer.append(targetException);
			}
		}
		if (!ServiceEntityStringHelper.checkNullString(sourceException.getMessage())) {
			strBuffer.append(sourceException.getMessage());
		}
		return strBuffer.toString();
	}

	public static String mergeStrWhenNotNull(String rawStr, String addedStr){
		if(!ServiceEntityStringHelper.checkNullString(addedStr)){
			rawStr = rawStr + addedStr;
		}
		return rawStr;
	}

	/**
	 * Remove the special separate code in string, with function to add CamelFlag
	 *
	 * @param rawString
	 * @param separate
	 * @param camelFlag
	 * @return
	 */
	public static String removeStringSeparate(String rawString, String separate, boolean camelFlag) {
		if (checkNullString(separate)) {
			return rawString;
		}
		String[] rawInnerArray = rawString.split(separate);
		if (rawInnerArray == null) {
			return rawString;
		}
		if (rawInnerArray.length == 1) {
			return rawString;
		}
		String tempResultUnion = ServiceEntityStringHelper.EMPTYSTRING;
		for (String innerUnion : rawInnerArray) {
			if (camelFlag) {
				tempResultUnion = tempResultUnion + ServiceEntityStringHelper.headerToUpperCase(innerUnion);
			} else {
				tempResultUnion = tempResultUnion + innerUnion;
			}
		}
		return tempResultUnion;
	}

	public static int getBackwardUpperCaseIndex(String str, int times) {
		if (str == null) {
			return 0;
		}
		int localTimes = 1;
		for (int i = str.length() - 1; i >= 0; i--) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				if (localTimes == times) {
					return i;
				}
				localTimes = localTimes + 1;
			} else {
				continue;
			}
		}
		return 0;
	}

	public static String getSubStringBackUpperIndex(String str, int times) {
		if (str == null) {
			return null;
		}
		int startIndex = getBackwardUpperCaseIndex(str, times);
		return str.substring(startIndex);
	}

	public static String getDefModelId(String refSEName, String refNodeName){
		if (ServiceEntityNode.NODENAME_ROOT.equals(refNodeName)) {
			return refSEName;
		} else {
			return refNodeName;
		}
	}

	/**
	 * If Input String is combined by sub string by dot('.'). then get the
	 * @param rawString
	 * @return
	 */
	public static String getParentSubStringByDot(String rawString) {
		if (ServiceEntityStringHelper.checkNullString(rawString)) {
			return EMPTYSTRING;
		}
		int lastDot = rawString.lastIndexOf(".");
		if (lastDot == 0 || lastDot == -1) {
			return EMPTYSTRING;
		}
		return rawString.substring(0, lastDot);
	}

	/**
	 * Check if the input string is valid 11-number long moblie number
	 *
	 * @param rawString
	 * @return
	 */
	public static boolean checkMobileNumberRegex(String rawString) {
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(rawString);
		return matcher.matches();
	}

	public static double SimilarDegree(String strA, String strB) {
		String newStrA = removeSign(strA);
		String newStrB = removeSign(strB);
		int temp = Math.max(newStrA.length(), newStrB.length());
		int temp2 = longestCommonSubstring(newStrA, newStrB).length();
		return temp2 * 1.0 / temp;
	}

	private static String removeSign(String str) {
		StringBuffer sb = new StringBuffer();
		for (char item : str.toCharArray())
			if (charReg(item)) {
				sb.append(item);
			}
		return sb.toString();
	}

	/**
	 * Check if field value equals standard value or field value contains standard value if place holder '*' is attached
	 * to header & rear
	 * @param fieldValue
	 * @param standardValue
	 * @return
	 */
	public static boolean checkEqualOrContain(String fieldValue,
										String standardValue){
		if(fieldValue == null){
			return false;
		}
		int checkType = 0;
		String coreValue = standardValue;
		if(standardValue.startsWith("*") && standardValue.endsWith("*")){
			coreValue = standardValue.substring(1, coreValue.length() - 1);
			checkType = 1;
		}
		if(standardValue.endsWith("*")){
			coreValue = ServiceEntityStringHelper.getSubString(standardValue, 0, coreValue.length() - 1);
			checkType = 3;
		}
		if(standardValue.startsWith("*")){
			coreValue = ServiceEntityStringHelper.getSubString(standardValue, 1, coreValue.length() );
			checkType = 2;
		}
		if(checkType == 0){
			// In case need to equal preciously
			return fieldValue.equals(coreValue);
		}else{
			// In case check contains with place holder
			return fieldValue.contains(coreValue);
		}
	}

	/**
	 * Wrapper method to get Sub string
	 * @param rawStr
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static String getSubString(String rawStr, int startPos, int endPos) {
		if (ServiceEntityStringHelper.checkNullString(rawStr)) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		if (startPos == 0 && endPos == 0) {
			return rawStr;
		}
		if (endPos > 0) {
			int length = rawStr.length();
			if (length > endPos) {
				return rawStr.substring(startPos, endPos);
			} else {
				return rawStr.substring(startPos, length);
			}
		} else {
			return rawStr.substring(startPos);
		}
	}

	/**
	 * Get random string, the max length is 32
	 *
	 * @param length
	 */
	public static String getRandomString(int length) {
		String uuid = UUID.randomUUID().toString();
		if (length <= 0) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		if (length >= 32) {
			return uuid;
		}
		return uuid.substring(0, length);
	}

	/**
	 * Generate UUID
	 * @return
	 */
	public static String genUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	private static boolean charReg(char charValue) {
		return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A'
				&& charValue <= 'Z') || (charValue >= '0' && charValue <= '9');

	}

	private static String longestCommonSubstring(String strA, String strB) {
		char[] chars_strA = strA.toCharArray();
		char[] chars_strB = strB.toCharArray();
		int m = chars_strA.length;
		int n = chars_strB.length;
		int[][] matrix = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (chars_strA[i - 1] == chars_strB[j - 1]) {
					matrix[i][j] = matrix[i - 1][j - 1] + 1;
				} else {
					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
				}
			}
		}
		char[] result = new char[matrix[m][n]];
		int currentIndex = result.length - 1;
		while (matrix[m][n] != 0) {
			if (matrix[n] == matrix[n - 1])
				n--;
			else
				if (matrix[m][n] == matrix[m - 1][n])
					m--;
				else {
					result[currentIndex] = chars_strA[m - 1];
					currentIndex--;
					n--;
					m--;
				}
		}
		return new String(result);
	}

	/**
	 * Logic to convert String array to String format
	 *
	 * @param strList
	 * @return
	 */
	public static String convListToString(List<String> strList) {
		if (ServiceCollectionsHelper.checkNullList(strList)) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		String content = strList.get(0);
		if (strList.size() == 1) {
			return content;
		}
		for (int i = 1; i < strList.size(); i++) {
			content = content + "," + strList.get(i);
		}
		return content;
	}

	/**
	 * Logic to convert String array to String format
	 *
	 * @param array
	 * @return
	 */
	public static String convArrayToString(String[] array) {
		if (array == null || array.length == 0) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		String content = array[0];
		if (array.length == 1) {
			return content;
		}
		for (int i = 1; i < array.length; i++) {
			content = content + "," + array[i];
		}
		return content;
	}

	/**
	 * Logic to convert String array to String format
	 *
	 * @param array
	 * @return
	 */
	public static String convArrayToString(String[] array, String seperator) {
		if (array == null || array.length == 0) {
			return ServiceEntityStringHelper.EMPTYSTRING;
		}
		String content = array[0];
		if (array.length == 1) {
			return content;
		}
		for (int i = 1; i < array.length; i++) {
			content = content + seperator + array[i];
		}
		return content;
	}

	public static String[] convStringToArray(String content) {
		if (ServiceEntityStringHelper.checkNullString(content)) {
			return null;
		}
		return content.split(",");
	}

	public static List<String>  convStringToStrList(String content) {
		if (ServiceEntityStringHelper.checkNullString(content)) {
			return null;
		}
		String[] rawStrArray = convStringToArray(content);
		List<String> resultList = new ArrayList<>();
		if(!ServiceCollectionsHelper.checkNullArray(rawStrArray)){
			Collections.addAll(resultList, rawStrArray);
		}
		return resultList;
	}

	/**
	 * Convert String list into Multi String value seperated with space, usually be used for search case.
	 *
	 * @param rawStringList
	 * @return
	 */
	public static String convStringListIntoMultiStringValue(List<String> rawStringList) {
		if (ServiceCollectionsHelper.checkNullList(rawStringList)) {
			return null;
		}
		int length = rawStringList.size();
		if (length == 1) {
			return rawStringList.get(0);
		}
		String result = ServiceEntityStringHelper.EMPTYSTRING;
		for (int index = 0; index < length; index++) {
			if (index < length - 1) {
				result = result + rawStringList.get(index) + " ";
			} else {
				result = result + rawStringList.get(index);
			}
		}
		return result;
	}

}
