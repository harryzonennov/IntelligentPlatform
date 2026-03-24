package com.company.IntelligentPlatform.common.model;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.time.LocalDateTime;

import com.company.IntelligentPlatform.common.model.*;

public class ServiceEntityNodeLastUpdateTimeCompare implements Comparator<ServiceEntityNode>{

	@Override
	public int compare(ServiceEntityNode seNode1, ServiceEntityNode seNode2) {
		LocalDateTime lstTime1 = seNode1.getLastUpdateTime();
		LocalDateTime lstTime2 = seNode2.getLastUpdateTime();
		if(lstTime1 == null && lstTime2 == null){
			return 0;
		}
		if(lstTime1 != null && lstTime2 != null){
			if(lstTime1 == lstTime2){
				return 0; 
			}
			return lstTime2.compareTo(lstTime1);
		}else{
			return -1;
		}
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

	public Comparator<ServiceEntityNode> reversed() {
		return null;
	}

	public Comparator<ServiceEntityNode> thenComparing(
			Comparator<? super ServiceEntityNode> arg0) {
		return null;
	}

	public <U extends Comparable<? super U>> Comparator<ServiceEntityNode> thenComparing(
			Function<? super ServiceEntityNode, ? extends U> arg0) {
		return null;
	}

	public <U> Comparator<ServiceEntityNode> thenComparing(
			Function<? super ServiceEntityNode, ? extends U> arg0,
			Comparator<? super U> arg1) {
		return null;
	}

	public Comparator<ServiceEntityNode> thenComparingDouble(
			ToDoubleFunction<? super ServiceEntityNode> arg0) {
		return null;
	}

	public Comparator<ServiceEntityNode> thenComparingInt(
			ToIntFunction<? super ServiceEntityNode> arg0) {
		return null;
	}

	public Comparator<ServiceEntityNode> thenComparingLong(
			ToLongFunction<? super ServiceEntityNode> arg0) {
		return null;
	}

}
