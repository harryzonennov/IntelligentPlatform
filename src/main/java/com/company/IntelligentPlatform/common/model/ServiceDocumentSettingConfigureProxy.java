package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

/**
 * Configure Proxy CLASS FOR Service Entity [ServiceDocumentSetting]
 *
 * @author
 * @date Fri Dec 24 22:50:30 CST 2021
 * <p>
 * This class is generated automatically by platform automation register tool
 */
@Repository
public class ServiceDocumentSettingConfigureProxy extends ServiceEntityConfigureProxy {

    @Override
    public void initConfig() {
        super.initConfig();
        super.setPackageName("platform.foundation");
        List<ServiceEntityConfigureMap> seConfigureMapList =
                Collections.synchronizedList(new ArrayList<>());
        //Init configuration of ServiceDocumentSetting [ROOT] node
        ServiceEntityConfigureMap serviceDocumentSettingConfigureMap = new ServiceEntityConfigureMap();
        serviceDocumentSettingConfigureMap.setParentNodeName(" ");
        serviceDocumentSettingConfigureMap.setNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocumentSettingConfigureMap.setNodeType(ServiceDocumentSetting.class);
        serviceDocumentSettingConfigureMap.setTableName(ServiceDocumentSetting.SENAME);
        serviceDocumentSettingConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocumentSettingConfigureMap.addNodeFieldMap("documentType", java.lang.String.class);
        serviceDocumentSettingConfigureMap.addNodeFieldMap("validToDate", java.util.Date.class);
        serviceDocumentSettingConfigureMap.addNodeFieldMap("refServiceEntityName", java.lang.String.class);
        serviceDocumentSettingConfigureMap.addNodeFieldMap("refNodeName", java.lang.String.class);
        seConfigureMapList.add(serviceDocumentSettingConfigureMap);
        //Init configuration of ServiceDocumentSetting [ServiceDocumentReportTemplate] node
        ServiceEntityConfigureMap serviceDocumentReportTemplateConfigureMap = new ServiceEntityConfigureMap();
        serviceDocumentReportTemplateConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocumentReportTemplateConfigureMap.setNodeName(ServiceDocumentReportTemplate.NODENAME);
        serviceDocumentReportTemplateConfigureMap.setNodeType(ServiceDocumentReportTemplate.class);
        serviceDocumentReportTemplateConfigureMap.setTableName(ServiceDocumentReportTemplate.NODENAME);
        serviceDocumentReportTemplateConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocumentReportTemplateConfigureMap.addNodeFieldMap("content", byte[].class);
        serviceDocumentReportTemplateConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(serviceDocumentReportTemplateConfigureMap);
        //Init configuration of ServiceDocumentReportTemplate [ServiceDocExcelUploadTemplate] node
        ServiceEntityConfigureMap serviceDocExcelUploadTemplateConfigureMap = new ServiceEntityConfigureMap();
        serviceDocExcelUploadTemplateConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocExcelUploadTemplateConfigureMap.setNodeName(ServiceDocExcelUploadTemplate.NODENAME);
        serviceDocExcelUploadTemplateConfigureMap.setNodeType(ServiceDocExcelUploadTemplate.class);
        serviceDocExcelUploadTemplateConfigureMap.setTableName(ServiceDocExcelUploadTemplate.NODENAME);
        serviceDocExcelUploadTemplateConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocExcelUploadTemplateConfigureMap.addNodeFieldMap("content", byte[].class);
        serviceDocExcelUploadTemplateConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(serviceDocExcelUploadTemplateConfigureMap);
        //Init configuration of ServiceDocumentReportTemplate [ServiceDocExcelDownloadTemplate] node
        ServiceEntityConfigureMap serviceDocExcelDownloadTemplateConfigureMap = new ServiceEntityConfigureMap();
        serviceDocExcelDownloadTemplateConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocExcelDownloadTemplateConfigureMap.setNodeName(ServiceDocExcelDownloadTemplate.NODENAME);
        serviceDocExcelDownloadTemplateConfigureMap.setNodeType(ServiceDocExcelDownloadTemplate.class);
        serviceDocExcelDownloadTemplateConfigureMap.setTableName(ServiceDocExcelDownloadTemplate.NODENAME);
        serviceDocExcelDownloadTemplateConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocExcelDownloadTemplateConfigureMap.addNodeFieldMap("content", byte[].class);
        serviceDocExcelDownloadTemplateConfigureMap.addNodeFieldMap("fileType", java.lang.String.class);
        seConfigureMapList.add(serviceDocExcelDownloadTemplateConfigureMap);
        //Init configuration of ServiceDocumentSetting [ServiceCrossDocConfigure] node
        ServiceEntityConfigureMap serviceCrossDocConfigureConfigureMap = new ServiceEntityConfigureMap();
        serviceCrossDocConfigureConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceCrossDocConfigureConfigureMap.setNodeName(ServiceCrossDocConfigure.NODENAME);
        serviceCrossDocConfigureConfigureMap.setNodeType(ServiceCrossDocConfigure.class);
        serviceCrossDocConfigureConfigureMap.setTableName(ServiceCrossDocConfigure.NODENAME);
        serviceCrossDocConfigureConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceCrossDocConfigureConfigureMap.addNodeFieldMap("targetDocType", java.lang.String.class);
        serviceCrossDocConfigureConfigureMap.addNodeFieldMap("crossDocRelationType", int.class);
        seConfigureMapList.add(serviceCrossDocConfigureConfigureMap);
        //Init configuration of ServiceDocumentSetting [ServiceCrossDocEventMonitor] node
        ServiceEntityConfigureMap serviceCrossDocEventMonitorConfigureMap = new ServiceEntityConfigureMap();
        serviceCrossDocEventMonitorConfigureMap.setParentNodeName(ServiceCrossDocConfigure.NODENAME);
        serviceCrossDocEventMonitorConfigureMap.setNodeName(ServiceCrossDocEventMonitor.NODENAME);
        serviceCrossDocEventMonitorConfigureMap.setNodeType(ServiceCrossDocEventMonitor.class);
        serviceCrossDocEventMonitorConfigureMap.setTableName(ServiceCrossDocEventMonitor.NODENAME);
        serviceCrossDocEventMonitorConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceCrossDocEventMonitorConfigureMap.addNodeFieldMap("targetActionCode", int.class);
        serviceCrossDocEventMonitorConfigureMap.addNodeFieldMap("triggerHomeActionCode", int.class);
        serviceCrossDocEventMonitorConfigureMap.addNodeFieldMap("triggerDocActionScenario", int.class);
        serviceCrossDocEventMonitorConfigureMap.addNodeFieldMap("triggerParentMode", int.class);
        seConfigureMapList.add(serviceCrossDocEventMonitorConfigureMap);
        //Init configuration of ServiceDocumentSetting [ServiceDocActionConfigure] node
        ServiceEntityConfigureMap serviceDocActionConfigureConfigureMap = new ServiceEntityConfigureMap();
        serviceDocActionConfigureConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocActionConfigureConfigureMap.setNodeName(ServiceDocActionConfigure.NODENAME);
        serviceDocActionConfigureConfigureMap.setNodeType(ServiceDocActionConfigure.class);
        serviceDocActionConfigureConfigureMap.setTableName(ServiceDocActionConfigure.NODENAME);
        serviceDocActionConfigureConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocActionConfigureConfigureMap.addNodeFieldMap("customSwitch", int.class);
        serviceDocActionConfigureConfigureMap.addNodeFieldMap("jsonContent", java.lang.String.class);
        seConfigureMapList.add(serviceDocActionConfigureConfigureMap);
        //Init configuration of ServiceDocumentSetting [ServiceDocActConfigureItem] node
        ServiceEntityConfigureMap serviceDocActConfigureItemConfigureMap = new ServiceEntityConfigureMap();
        serviceDocActConfigureItemConfigureMap.setParentNodeName(ServiceDocActionConfigure.NODENAME);
        serviceDocActConfigureItemConfigureMap.setNodeName(ServiceDocActConfigureItem.NODENAME);
        serviceDocActConfigureItemConfigureMap.setNodeType(ServiceDocActConfigureItem.class);
        serviceDocActConfigureItemConfigureMap.setTableName(ServiceDocActConfigureItem.NODENAME);
        serviceDocActConfigureItemConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocActConfigureItemConfigureMap.addNodeFieldMap("preStatus", java.lang.String.class);
        serviceDocActConfigureItemConfigureMap.addNodeFieldMap("targetStatus", int.class);
        serviceDocActConfigureItemConfigureMap.addNodeFieldMap("authorAction", java.lang.String.class);
        serviceDocActConfigureItemConfigureMap.addNodeFieldMap("actionCode", int.class);
        seConfigureMapList.add(serviceDocActConfigureItemConfigureMap);
        //Init configuration of ServiceDocumentSetting [CrossCopyDocConfigure] node
        ServiceEntityConfigureMap crossCopyDocConfigureConfigureMap = new ServiceEntityConfigureMap();
        crossCopyDocConfigureConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        crossCopyDocConfigureConfigureMap.setNodeName(CrossCopyDocConfigure.NODENAME);
        crossCopyDocConfigureConfigureMap.setNodeType(CrossCopyDocConfigure.class);
        crossCopyDocConfigureConfigureMap.setTableName(CrossCopyDocConfigure.NODENAME);
        crossCopyDocConfigureConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        crossCopyDocConfigureConfigureMap.addNodeFieldMap("targetDocType", int.class);
        crossCopyDocConfigureConfigureMap.addNodeFieldMap("cloneAttachmentFlag", int.class);
        crossCopyDocConfigureConfigureMap.addNodeFieldMap("triggerSourceActionCode", int.class);
        crossCopyDocConfigureConfigureMap.addNodeFieldMap("activeCustomSwitch", int.class);
        seConfigureMapList.add(crossCopyDocConfigureConfigureMap);
        //Init configuration of ServiceDocumentSetting [CrossCopyInvolveParty] node
        ServiceEntityConfigureMap crossCopyInvolvePartyConfigureMap = new ServiceEntityConfigureMap();
        crossCopyInvolvePartyConfigureMap.setParentNodeName(CrossCopyDocConfigure.NODENAME);
        crossCopyInvolvePartyConfigureMap.setNodeName(CrossCopyInvolveParty.NODENAME);
        crossCopyInvolvePartyConfigureMap.setNodeType(CrossCopyInvolveParty.class);
        crossCopyInvolvePartyConfigureMap.setTableName(CrossCopyInvolveParty.NODENAME);
        crossCopyInvolvePartyConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        crossCopyInvolvePartyConfigureMap.addNodeFieldMap("sourcePartyRole", int.class);
        crossCopyInvolvePartyConfigureMap.addNodeFieldMap("targetPartyRole", int.class);
        crossCopyInvolvePartyConfigureMap.addNodeFieldMap("logonPartyFlag", int.class);
        seConfigureMapList.add(crossCopyInvolvePartyConfigureMap);


        ServiceEntityConfigureMap serviceDocDeletionSettingMap = new ServiceEntityConfigureMap();
        serviceDocDeletionSettingMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocDeletionSettingMap.setNodeName(ServiceDocDeletionSetting.NODENAME);
        serviceDocDeletionSettingMap.setNodeType(ServiceDocDeletionSetting.class);
        serviceDocDeletionSettingMap.setTableName(ServiceDocDeletionSetting.NODENAME);
        serviceDocDeletionSettingMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocDeletionSettingMap.addNodeFieldMap("refServiceEntityName", String.class);
        serviceDocDeletionSettingMap.addNodeFieldMap("refNodeName", String.class);
        serviceDocDeletionSettingMap.addNodeFieldMap("nodeInstId", String.class);
        serviceDocDeletionSettingMap.addNodeFieldMap("deletionStrategy", int.class);
        serviceDocDeletionSettingMap.addNodeFieldMap("admDeleteStatus", String.class);
        seConfigureMapList.add(serviceDocDeletionSettingMap);
        //Init configuration of ServiceDocumentSetting [ServiceDocInitConfigure] node
        ServiceEntityConfigureMap serviceDocInitConfigureConfigureMap = new ServiceEntityConfigureMap();
        serviceDocInitConfigureConfigureMap.setParentNodeName(ServiceDocumentSetting.NODENAME);
        serviceDocInitConfigureConfigureMap.setNodeName(ServiceDocInitConfigure.NODENAME);
        serviceDocInitConfigureConfigureMap.setNodeType(ServiceDocInitConfigure.class);
        serviceDocInitConfigureConfigureMap.setTableName(ServiceDocInitConfigure.NODENAME);
        serviceDocInitConfigureConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocInitConfigureConfigureMap.addNodeFieldMap("refServiceEntityName", String.class);
        serviceDocInitConfigureConfigureMap.addNodeFieldMap("refNodeName", String.class);
        serviceDocInitConfigureConfigureMap.addNodeFieldMap("nodeInstId", String.class);
        serviceDocInitConfigureConfigureMap.addNodeFieldMap("configMeta", String.class);
        seConfigureMapList.add(serviceDocInitConfigureConfigureMap);
        //Init configuration of ServiceDocumentSetting [CrossCopyInvolveParty] node
        ServiceEntityConfigureMap serviceDocServiceDocInitInvolvePartyConfigureMap = new ServiceEntityConfigureMap();
        serviceDocServiceDocInitInvolvePartyConfigureMap.setParentNodeName(ServiceDocInitConfigure.NODENAME);
        serviceDocServiceDocInitInvolvePartyConfigureMap.setNodeName(ServiceDocInitInvolveParty.NODENAME);
        serviceDocServiceDocInitInvolvePartyConfigureMap.setNodeType(ServiceDocInitInvolveParty.class);
        serviceDocServiceDocInitInvolvePartyConfigureMap.setTableName(ServiceDocInitInvolveParty.NODENAME);
        serviceDocServiceDocInitInvolvePartyConfigureMap.setFieldList(super.getBasicSENodeFieldMap());
        serviceDocServiceDocInitInvolvePartyConfigureMap.addNodeFieldMap("partyRole", int.class);
        serviceDocServiceDocInitInvolvePartyConfigureMap.addNodeFieldMap("logonPartyFlag", int.class);
        seConfigureMapList.add(serviceDocServiceDocInitInvolvePartyConfigureMap);
        // End
        super.setSeConfigMapList(seConfigureMapList);
    }


}
