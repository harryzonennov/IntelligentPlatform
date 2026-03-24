package com.company.IntelligentPlatform.finance.service;

import com.company.IntelligentPlatform.finance.model.FinAccountTitle;

/**
 * Finance Service Generator Implementation
 * 
 * @author DylanYang
 * @date May 01 2013
 */
public class FinAccountTitleIdGenerator implements FinanceServiceGenerator {
	// Splitting AccountTitle Id
	int[] splitAccountTitleId;
	// split String Variable
	String s1, s2, s3;
	// Generated AccountTitle Id
	int genAccountTitleId;
	// sortedAccountTitleId
	int sortedAccountTitleId;

	@Override
	public int accountTitleIdGenerator(int[] subAccountTitleIdArray,
			int parentAccountTitleId, int genCategoryType) {
		splitAccountTitleId = new int[subAccountTitleIdArray.length];
		for (int i = 0; i < subAccountTitleIdArray.length; i++) {
			try {
				if (genCategoryType == FinAccountTitle.CATEGORY_SECONDARY) {
					// split by parentId & '00'
					splitAccountTitleId[i] = Integer.parseInt((Integer
							.toString(subAccountTitleIdArray[i])).substring(4,
							6).trim());
					s1 = (Integer.toString(subAccountTitleIdArray[i]))
							.substring(0, 4);
					s2 = (Integer.toString(subAccountTitleIdArray[i]))
							.substring(6, 8);
				} else if (genCategoryType == FinAccountTitle.CATEGORY_SUBSIDIARY) {
					// split by parentId & '00'
					splitAccountTitleId[i] = Integer.parseInt((Integer
							.toString(subAccountTitleIdArray[i])).substring(6,
							8).trim());
					s1 = (Integer.toString(subAccountTitleIdArray[i]))
							.substring(0, 4);
					s2 = (Integer.toString(subAccountTitleIdArray[i]))
							.substring(4, 6);
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
		// Invoke InsertSort()
		// int[] a = new int[]{8,2,4,9,3,6,7,10};
		splitAccountTitleId = sort(splitAccountTitleId);
		// Judge splitAccountTitleId Sequence
		if (checkSequenceArray(splitAccountTitleId)) {
			// Get Max splitAccountTitleId
			sortedAccountTitleId = getMaxValue(splitAccountTitleId);
			System.out.println("Max split Id:" + sortedAccountTitleId);
		} else {
			// Get Max Nonsequence Value
			sortedAccountTitleId = getNonsequenceMiniValue(splitAccountTitleId);
			System.out.println("NonsequenceMiniValue Id:"
					+ sortedAccountTitleId);
		}
		// Generate Secondary AccountTitle Id by generation Category Type
		// Judge maxSplitAccountTitleId < 10
		if (genCategoryType == FinAccountTitle.CATEGORY_SECONDARY) {
			if (sortedAccountTitleId / 10 == 0) {
				s3 = s1 + '0' + ++sortedAccountTitleId + s2;
			} else if (sortedAccountTitleId / 10 >= 0) {
				s3 = s1 + ++sortedAccountTitleId + s2;
			}
		} else if (genCategoryType == FinAccountTitle.CATEGORY_SUBSIDIARY) {
			if (sortedAccountTitleId / 10 == 0) {
				s3 = s1 + s2 + '0' + ++sortedAccountTitleId;
			} else if (sortedAccountTitleId / 10 >= 0) {
				s3 = s1 + s2 + sortedAccountTitleId;
			}
		}
		genAccountTitleId = Integer.parseInt(s3.trim());
		System.out.println(parentAccountTitleId);
		for (int k : subAccountTitleIdArray) {
			System.out.println(k + " ");
		}
		for (int k : splitAccountTitleId) {
			System.out.println(k);
		}
		return genAccountTitleId;
	}

	/**
	 * Sort
	 * 
	 * @param sortArray
	 * @return sortArray
	 */
	public int[] sort(int[] sortArray) {
		for (int index = 1; index < sortArray.length; index++) {
			int subIndex = index;
			int currentData = sortArray[index]; // wait insert data
			while ((subIndex > 0) && (sortArray[subIndex - 1] > currentData)) {
				sortArray[subIndex] = sortArray[subIndex - 1];
				subIndex--;
				sortArray[subIndex] = currentData; // insert fittable place
			}
		}
		return sortArray;
	}

	/**
	 * Get Max Value
	 * 
	 * @param sortedArray
	 * @return
	 */
	public int getMaxValue(int[] sortedArray) {
		int max = -9999;
		for (int i = 0; i < sortedArray.length; i++) {
			if (max < sortedArray[i]) {
				max = sortedArray[i];
			}
		}
		return max;
	}

	/**
	 * Get minimum Value
	 * 
	 * @param sortedArray
	 * @return
	 */
	public int getMiniValue(int[] sortedArray) {
		int min = 9999;
		for (int i = 0; i < sortedArray.length; i++) {
			if (min > sortedArray[i]) {
				min = sortedArray[i];
			}
		}
		return min;
	}

	/**
	 * Check SequenceArray
	 * 
	 * @param sortedArray
	 * @return
	 */
	public boolean checkSequenceArray(int[] sortedArray) {
		int index = sortedArray.length - 1;
		// Squence sign
		int flag = 0;
		for (int i = 0; i <= index; i++) {
			if (index <= i)
				break;
			// Judge array sequence
			if ((sortedArray[i + 1] - sortedArray[i]) == 1) {
				continue;
			} else {
				flag = 1;
			}
		}
		if (flag == 1) {
			return false;
		}
		return true;
	}

	/**
	 * Get Nonsequence Minimum Value
	 * 
	 * @param sortedArray
	 * @return nonsequenceMinValue
	 */
	public int getNonsequenceMiniValue(int[] sortedArray) {
		int index = sortedArray.length - 1;
		int nonsequenceArray[] = new int[2];
		int nonsequenceMinValue;
		for (int i = 0; i <= index; i++) {
			if (index <= i)
				break;
			// Judge array sequence
			if ((sortedArray[i + 1] - sortedArray[i]) == 1) {
				continue;
			} else {
				nonsequenceArray[0] = sortedArray[i];
				nonsequenceArray[1] = sortedArray[i + 1];
				break;
			}
		}
		nonsequenceArray = sort(nonsequenceArray);
		nonsequenceMinValue = getMiniValue(nonsequenceArray);
		return nonsequenceMinValue;
	}
}
