package com.company.IntelligentPlatform.logistics.model;

import java.util.Comparator;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;


public class WarehouseStoreItemCompare
implements Comparator<WarehouseStoreItem> {

	@Override
	public int compare(WarehouseStoreItem storeItem1, WarehouseStoreItem storeItem2) {
		LocalDateTime inboundDate1 = storeItem1.getInboundDate();
		LocalDateTime inboundDate2 = storeItem2.getInboundDate();
		long intime1 = 0;
		long intime2 = 0;
		if(inboundDate1 != null){
			intime1 = inboundDate1.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
		}
		if(inboundDate2 != null){
			intime2 = inboundDate2.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
		}
		long diff = intime1 - intime2;
		return  (int)diff;
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

	public Comparator<WarehouseStoreItem> reversed() {
		return null;
	}

	public Comparator<WarehouseStoreItem> thenComparing(
			Comparator<? super WarehouseStoreItem> arg0) {
		return null;
	}

	public <U extends Comparable<? super U>> Comparator<WarehouseStoreItem> thenComparing(
			Function<? super WarehouseStoreItem, ? extends U> arg0) {
		return null;
	}

	public <U> Comparator<WarehouseStoreItem> thenComparing(
			Function<? super WarehouseStoreItem, ? extends U> arg0,
			Comparator<? super U> arg1) {
		return null;
	}

	public Comparator<WarehouseStoreItem> thenComparingDouble(
			ToDoubleFunction<? super WarehouseStoreItem> arg0) {
		return null;
	}

	public Comparator<WarehouseStoreItem> thenComparingInt(
			ToIntFunction<? super WarehouseStoreItem> arg0) {
		return null;
	}

	public Comparator<WarehouseStoreItem> thenComparingLong(
			ToLongFunction<? super WarehouseStoreItem> arg0) {
		return null;
	}

}
