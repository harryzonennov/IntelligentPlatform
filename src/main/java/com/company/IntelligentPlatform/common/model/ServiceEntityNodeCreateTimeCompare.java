package com.company.IntelligentPlatform.common.model;

import java.util.Comparator;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.company.IntelligentPlatform.common.model.*;

public class ServiceEntityNodeCreateTimeCompare implements Comparator<ServiceEntityNode>{

	@Override
	public int compare(ServiceEntityNode seNode1, ServiceEntityNode seNode2) {
		LocalDateTime createTime1 = seNode1.getCreatedTime();
		LocalDateTime createTime2 = seNode2.getCreatedTime();
		if(createTime1 != null && createTime2 != null){
			return createTime2.compareTo(createTime1);
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
