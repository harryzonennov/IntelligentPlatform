package com.company.IntelligentPlatform.common.model;

public interface IStandardMaterialTypeList {

	public static final String ID_CLOTHES = "clothes";

	public static final String ID_SHOES = "shoes";

	public static final String ID_METAL = "metal";

	public static final String ID_FOOD = "food";

	public static final String ID_ALCOHOL = "alcohol";

	public static final String ID_BEVERAGE = "beverage";

	// relation ship
	public static final String ID_SHOES_PARENT = ID_CLOTHES;

	// relation ship
	public static final String ID_ALCOHOL_PARENT = ID_BEVERAGE;

	// relation ship
	public static final String ID_BEVERAGE_PARENT = ID_FOOD;

}
