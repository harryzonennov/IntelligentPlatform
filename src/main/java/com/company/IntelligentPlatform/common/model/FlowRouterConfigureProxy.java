package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [FlowRouter]
 *
 * @author
 * @date Tue May 11 00:42:59 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class FlowRouterConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("platform.foundation");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
//Init configuration of FlowRouter [ROOT] node
        ServiceEntityConfigureMap flowRouterConfigureMap = new ServiceEntityConfigureMap();
        flowRouterConfigureMap.setParentNodeName(" ");
        flowRouterConfigureMap.setNodeName(FlowRouter.NODENAME);
        flowRouterConfigureMap.setNodeType(FlowRouter.class);
        flowRouterConfigureMap.setTableName(FlowRouter.SENAME);
        flowRouterConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        flowRouterConfigureMap.addNodeFieldMap("serialFlag", int.class);
        seConfigureMapList.add(flowRouterConfigureMap);
//Init configuration of FlowRouter [FlowRouterExtendClass] node
        ServiceEntityConfigureMap flowRouterExtendClassConfigureMap = new ServiceEntityConfigureMap();
        flowRouterExtendClassConfigureMap.setParentNodeName(FlowRouter.NODENAME);
        flowRouterExtendClassConfigureMap.setNodeName(FlowRouterExtendClass.NODENAME);
        flowRouterExtendClassConfigureMap.setNodeType(FlowRouterExtendClass.class);
        flowRouterExtendClassConfigureMap.setTableName(FlowRouterExtendClass.NODENAME);
        flowRouterExtendClassConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        flowRouterExtendClassConfigureMap.addNodeFieldMap("extendClassFullName", java.lang.String.class);
        flowRouterExtendClassConfigureMap.addNodeFieldMap("extendClassId", java.lang.String.class);
        flowRouterExtendClassConfigureMap.addNodeFieldMap("processIndex", int.class);
        flowRouterExtendClassConfigureMap.addNodeFieldMap("refDirectAssigneeUUID", java.lang.String.class);
        seConfigureMapList.add(flowRouterExtendClassConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }


}
