package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Cargo for transportation
 *
 * @author Zhang,Hang
 * @date 2013-1-16
 *
 */
@Entity
@Table(name = "Cargo", catalog = "platform")
public class Cargo extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.Cargo;

	public static int PACKAGETYPE_CARTON = 1;

	public static int PACKAGETYPE_WOODENCASE = 2;

	public static int PACKAGETYPE_BALE = 3;

	public static int PACKAGETYPE_BAG = 4;

	public static int PACKAGETYPE_BARREL = 5;

	public static int PACKAGETYPE_OTHERS = 6;

	public static int CARGOTYPE_LIGHT = 1;

	public static int CARGOTYPE_HEAVY = 2;

	public static int CARGOTYPE_MIXED = 3;

	public static final int REGULAR_TYPE_REGULAR = 1;

	public static final int REGULAR_TYPE_TEMP = 2;

	public Cargo() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.parentNodeUUID = super.uuid;
		this.rootNodeUUID = super.uuid;
		this.cargoType = CARGOTYPE_HEAVY;
		this.packageType = PACKAGETYPE_CARTON;
		this.regularType = REGULAR_TYPE_TEMP;
	}

	protected int packageType;

	protected int cargoType;

	protected String packageInfo;

	protected double weight;

	protected double volume;

	protected String externalID;

	protected int regularType;

	protected String skuNumber;

	public int getPackageType() {
		return packageType;
	}

	public void setPackageType(int packageType) {
		this.packageType = packageType;
	}

	public String getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(String packageInfo) {
		this.packageInfo = packageInfo;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public int getCargoType() {
		return cargoType;
	}

	public void setCargoType(int cargoType) {
		this.cargoType = cargoType;
	}

	public int getRegularType() {
		return regularType;
	}

	public void setRegularType(int regularType) {
		this.regularType = regularType;
	}

	public String getSkuNumber() {
		return skuNumber;
	}

	public void setSkuNumber(String skuNumber) {
		this.skuNumber = skuNumber;
	}

}
