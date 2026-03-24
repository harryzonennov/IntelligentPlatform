package com.company.IntelligentPlatform.logistics.service;

import com.company.IntelligentPlatform.common.model.IMobileJSONConstant;

public interface ILogisticsMobileJSONConstant extends IMobileJSONConstant{

	public static final String ELE_VEHICLE_INFO = "vehicleInfo";

	public static final String ELE_VEH_RUN_INFO = "vehicleRun";

	public static final String ELE_VEH_RUN_UUID = "vehicleRunUUID";

	public static final String ELE_VEH_RUN_SITE_UUID = "vehicleRunSITEUUID";

	public static final String ELE_VEH_RUN_ID = "vehicleRunID";

	public static final String ELE_VEH_ID = "vehicleID";

	public static final String ELE_VEH_LOAD_STAUTS = "vehicleLoadStatus";

	public static final String ELE_BOOKINGNOTE_ID = "bookingNoteID";
	
	public static final String ELE_TRANSSITE_VERSION = "transSiteVersion";

	public static final String ELE_TRANSSITE = "transSite";

	public static final String ELE_TRANSSITE_LIST = "transSiteList";

	public static final String ELE_TRANSSITE_UUID = "transSiteUUID";

	public static final String ELE_TRANSSITE_ID = "transSiteID";

	public static final String ELE_SITE_ARRIVE_INDEX = "siteArriveIndex";

	public static final String ELE_SITE_ARRIVE_STATUS = "siteArriveStatus";

	public static final String ELE_VEHLOAD_LIST = "vehicleLoadList";

	public static final String ELE_VEHLOAD_UUID = "vehicleLoadItemUUID";

	public static final String ELE_VEHLOAD_UUIDLIST = "vehicleLoadItemUUIDList";

	public static final String APIKEY_LOGIN = "login";

	public static final String APIKEY_LOAD_VEH_RUN = "loadVehicleRunInfo";

	public static final String APIKEY_NEW_VEH_RUN = "newVehicleRunInfo";

	public static final String APIKEY_SAVE_VEH_RUN = "saveVehicleRunInfo";

	public static final String APIKEY_INSERT_SITE = "insertTransSite";

	public static final String APIKEY_DELETE_SITE = "deleteTransSite";

	public static final String APIKEY_ARRIVE_SITE = "arriveTransSite";

	public static final String APIKEY_INSERT_LOAD_MAN = "insertLoadItemManually";

	public static final String APIKEY_INSERT_LOAD_AUTO = "insertLoadItemAuto";

	public static final String APIKEY_DELETE_LOAD = "deleteLoadItem";

	public static final String APIKEY_LOAD_VEH_ITEM = "loadVehLoadItem";

	public static final String APIKEY_UNLOAD_VEH_ITEM = "unLoadVehLoadItem";

	public static final String APIKEY_GET_SITE_LIST = "getTransSiteListInfo";

	public static final String APIKEY_GET_ERRORCODE_LIST = "getErrorCodeList";

}
