package com.company.IntelligentPlatform.production.dto;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class BillOfMaterialOrderVersionCompare implements Comparator<BillOfMaterialOrderUIModel>{

	@Override
	public int compare(BillOfMaterialOrderUIModel order1, BillOfMaterialOrderUIModel order2) {
		int version1 = order1.getVersionNumber();
		int version2 = order2.getVersionNumber();
		if(version1 == 0 && version2 == 0){
			return 0;
		}
		if(version1 == version2){
			return order2.getPatchNumber() - order1.getPatchNumber();
		}
		return version2 - version1;
	}

	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
			Function<? super T, ? extends U> arg0) {
		return null;
	}

	public static <T, U> Comparator<T> comparing(
			Function<? super T, ? extends U> arg0, Comparator<? super U> arg1) {
		return null;
	}

	public static <T> Comparator<T> comparingDouble(
			ToDoubleFunction<? super T> arg0) {
		return null;
	}

	public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> arg0) {
		return null;
	}

	public static <T> Comparator<T> comparingLong(ToLongFunction<? super T> arg0) {
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
		return null;
	}

	public static <T> Comparator<T> nullsFirst(Comparator<? super T> arg0) {
		return null;
	}

	public static <T> Comparator<T> nullsLast(Comparator<? super T> arg0) {
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
		return null;
	}

	public Comparator<BillOfMaterialOrderUIModel> reversed() {
		return null;
	}

	public Comparator<BillOfMaterialOrderUIModel> thenComparing(
			Comparator<? super BillOfMaterialOrderUIModel> arg0) {
		return null;
	}

	public <U extends Comparable<? super U>> Comparator<BillOfMaterialOrderUIModel> thenComparing(
			Function<? super BillOfMaterialOrderUIModel, ? extends U> arg0) {
		return null;
	}

	public <U> Comparator<BillOfMaterialOrderUIModel> thenComparing(
			Function<? super BillOfMaterialOrderUIModel, ? extends U> arg0,
			Comparator<? super U> arg1) {
		return null;
	}

	public Comparator<BillOfMaterialOrderUIModel> thenComparingDouble(
			ToDoubleFunction<? super BillOfMaterialOrderUIModel> arg0) {
		return null;
	}

	public Comparator<BillOfMaterialOrderUIModel> thenComparingInt(
			ToIntFunction<? super BillOfMaterialOrderUIModel> arg0) {
		return null;
	}

	public Comparator<BillOfMaterialOrderUIModel> thenComparingLong(
			ToLongFunction<? super BillOfMaterialOrderUIModel> arg0) {
		return null;
	}

}
