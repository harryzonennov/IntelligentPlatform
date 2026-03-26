package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceFlowModel]
 *
 * @author
 * @date Tue May 11 00:58:59 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class ServiceFlowModelConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("platform.foundation");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
//Init configuration of ServiceFlowModel [ROOT] node
        ServiceEntityConfigureMap serviceFlowModelConfigureMap = new ServiceEntityConfigureMap();
        serviceFlowModelConfigureMap.setParentNodeName(" ");
        serviceFlowModelConfigureMap.setNodeName(ServiceFlowModel.NODENAME);
        serviceFlowModelConfigureMap.setNodeType(ServiceFlowModel.class);
        serviceFlowModelConfigureMap.setTableName(ServiceFlowModel.SENAME);
        serviceFlowModelConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceFlowModelConfigureMap.addNodeFieldMap("actionCode", int.class);
        serviceFlowModelConfigureMap.addNodeFieldMap("serviceUIModelId", java.lang.String.class);
        serviceFlowModelConfigureMap.addNodeFieldMap("documentType", int.class);
        serviceFlowModelConfigureMap.addNodeFieldMap("refRouterUUID", java.lang.String.class);
        seConfigureMapList.add(serviceFlowModelConfigureMap);
//Init configuration of ServiceFlowModel [ServiceFlowCondGroup] node
        ServiceEntityConfigureMap serviceFlowCondGroupConfigureMap = new ServiceEntityConfigureMap();
        serviceFlowCondGroupConfigureMap.setParentNodeName(ServiceFlowModel.NODENAME);
        serviceFlowCondGroupConfigureMap.setNodeName(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupConfigureMap.setNodeType(ServiceFlowCondGroup.class);
        serviceFlowCondGroupConfigureMap.setTableName(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondGroupConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        seConfigureMapList.add(serviceFlowCondGroupConfigureMap);
//Init configuration of ServiceFlowModel [ServiceFlowCondField] node
        ServiceEntityConfigureMap serviceFlowCondFieldConfigureMap = new ServiceEntityConfigureMap();
        serviceFlowCondFieldConfigureMap.setParentNodeName(ServiceFlowCondGroup.NODENAME);
        serviceFlowCondFieldConfigureMap.setNodeName(ServiceFlowCondField.NODENAME);
        serviceFlowCondFieldConfigureMap.setNodeType(ServiceFlowCondField.class);
        serviceFlowCondFieldConfigureMap.setTableName(ServiceFlowCondField.NODENAME);
        serviceFlowCondFieldConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceFlowCondFieldConfigureMap.addNodeFieldMap("nodeInstId", java.lang.String.class);
        serviceFlowCondFieldConfigureMap.addNodeFieldMap("fieldName", java.lang.String.class);
        serviceFlowCondFieldConfigureMap.addNodeFieldMap("valueOperator", int.class);
        serviceFlowCondFieldConfigureMap.addNodeFieldMap("fieldType", java.lang.String.class);
        serviceFlowCondFieldConfigureMap.addNodeFieldMap("targetValue", java.lang.String.class);
        seConfigureMapList.add(serviceFlowCondFieldConfigureMap);
// End
        super.setSeConfigMapList(seConfigureMapList);
    }

}
