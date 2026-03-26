package com.company.IntelligentPlatform.production.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureMap;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureProxy;

/**
*Configure Proxy CLASS FOR Service Entity [ProdWorkCenter]
*
*@author
*@date Wed May 06 02:11:04 CST 2020
*
*This class is generated automatically by platform automation register tool
*/
@Repository
public class ProdWorkCenterConfigureProxy extends ServiceEntityConfigureProxy {

@Override
public void initConfig() {
super.initConfig();
super.setPackageName("net.thorstein.production");
List<ServiceEntityConfigureMap> seConfigureMapList = Collections.synchronizedList(new ArrayList<ServiceEntityConfigureMap>());
//Init configuration of ProdWorkCenter [ROOT] node
ServiceEntityConfigureMap prodWorkCenterConfigureMap = new ServiceEntityConfigureMap();
prodWorkCenterConfigureMap.setParentNodeName(" ");
prodWorkCenterConfigureMap.setNodeName(ProdWorkCenter.NODENAME);
prodWorkCenterConfigureMap.setNodeType(ProdWorkCenter.class);
prodWorkCenterConfigureMap.setTableName(ProdWorkCenter.SENAME);
prodWorkCenterConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
prodWorkCenterConfigureMap.addNodeFieldMap("accountType", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("address", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("telephone", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("mobile", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("fax", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("email", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("webPage", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("postcode", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("countryName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("stateName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("cityName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refCityUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("townZone", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("subArea", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("streetName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("houseNumber", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("regularType", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("taxNumber", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("bankAccount", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("depositBank", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("addressOnMap", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("fullName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("longitude", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("latitude", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("contactMobileNumber", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("tags", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("parentOrganizationUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("organType", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("organLevel", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("mainContactUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refCashierUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refAccountantUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refFinOrgUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("organizationFunction", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refOrganizationFunction", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("fullName", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("keyWorkCenterFlag", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refCostCenterUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("usageNote", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("refGroupUUID", java.lang.String.class );
prodWorkCenterConfigureMap.addNodeFieldMap("capacityCalculateType", int.class );
prodWorkCenterConfigureMap.addNodeFieldMap("keyWorkCenter", boolean.class );
seConfigureMapList.add(prodWorkCenterConfigureMap);
//Init configuration of ProdWorkCenter [ProdWorkCenterCalendarItem] node
ServiceEntityConfigureMap prodWorkCenterCalendarItemConfigureMap = new ServiceEntityConfigureMap();
prodWorkCenterCalendarItemConfigureMap.setParentNodeName(ProdWorkCenter.NODENAME);
prodWorkCenterCalendarItemConfigureMap.setNodeName(ProdWorkCenterCalendarItem.NODENAME);
prodWorkCenterCalendarItemConfigureMap.setNodeType(ProdWorkCenterCalendarItem.class);
prodWorkCenterCalendarItemConfigureMap.setTableName(ProdWorkCenterCalendarItem.NODENAME);
prodWorkCenterCalendarItemConfigureMap.setFieldList(super.getBasicReferenceFieldMap());
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("startDate", java.util.Date.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("endDate", java.util.Date.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("occupyRate", double.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("taskType", int.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("seriesFlag", boolean.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("occupyResources", java.lang.String.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("seriesStartDate", java.util.Date.class );
prodWorkCenterCalendarItemConfigureMap.addNodeFieldMap("seriesEndDate", java.util.Date.class );
seConfigureMapList.add(prodWorkCenterCalendarItemConfigureMap);
//Init configuration of ProdWorkCenter [ProdWorkCenterResItem] node
ServiceEntityConfigureMap prodWorkCenterResItemConfigureMap = new ServiceEntityConfigureMap();
prodWorkCenterResItemConfigureMap.setParentNodeName(ProdWorkCenter.NODENAME);
prodWorkCenterResItemConfigureMap.setNodeName(ProdWorkCenterResItem.NODENAME);
prodWorkCenterResItemConfigureMap.setNodeType(ProdWorkCenterResItem.class);
prodWorkCenterResItemConfigureMap.setTableName(ProdWorkCenterResItem.NODENAME);
prodWorkCenterResItemConfigureMap.setFieldList(super.getBasicReferenceFieldMap());
prodWorkCenterResItemConfigureMap.addNodeFieldMap("keyResourceFlag", int.class );
seConfigureMapList.add(prodWorkCenterResItemConfigureMap);
// End
super.setSeConfigMapList(seConfigureMapList);
}

}
