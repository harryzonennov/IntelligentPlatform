package com.company.IntelligentPlatform.common.service;

import java.util.Date;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO-DAO: import ...ServiceExceptionRecordDAO;

import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecord;
import com.company.IntelligentPlatform.common.model.ServiceExceptionRecordConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

/**
 * Logic Manager CLASS FOR Service Entity [ServiceExceptionRecord]
 *
 * @author
 * @date Mon Jul 22 11:03:55 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class ServiceExceptionRecordManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired

    // TODO-DAO:     ServiceExceptionRecordDAO serviceExceptionRecordDAO;

    @Autowired
    ServiceExceptionRecordConfigureProxy serviceExceptionRecordConfigureProxy;

    public ServiceExceptionRecordManager() {
        super.seConfigureProxy = new ServiceExceptionRecordConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new ServiceExceptionRecordDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(serviceExceptionRecordDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(serviceExceptionRecordConfigureProxy);
    }

    public ServiceExceptionRecord newManuallyRecord(String client)
            throws ServiceEntityConfigureException {
        ServiceExceptionRecord serviceExceptionRecord = (ServiceExceptionRecord) newRootEntityNode(client);
        serviceExceptionRecord
                .setSourceType(ServiceExceptionRecord.SOURCE_TYPE_MANUAL);
        return serviceExceptionRecord;
    }

    /**
     * If case System exception raise, Auto record exception
     *
     * @param exceptionName
     * @param category
     * @param note
     * @param source
     * @param resEmployeeUUID
     */
    public void autoRecord(String exceptionName, int category, String note,
                           String source, String resEmployeeUUID, String orgUUID,
                           StackTraceElement[] traceList) {
        ServiceExceptionRecord serviceExceptionRecord = new ServiceExceptionRecord();
        serviceExceptionRecord.setNodeLevel(ServiceEntityNode.NODELEVEL_ROOT);
        serviceExceptionRecord.setNodeName(ServiceEntityNode.NODENAME_ROOT);
        // For root node set parent uuid & root uuid as itself
        serviceExceptionRecord.setParentNodeUUID(serviceExceptionRecord
                .getUuid());
        serviceExceptionRecord
                .setRootNodeUUID(serviceExceptionRecord.getUuid());
        serviceExceptionRecord.setNote(note);
        serviceExceptionRecord.setName(exceptionName);
        serviceExceptionRecord.setCategory(category);
        serviceExceptionRecord.setSource(source);
        if (traceList != null && traceList.length > 0) {
            serviceExceptionRecord
                    .setCallStack(convExceptionTraceListToString(traceList));
        }
        serviceExceptionRecord.setReporterUUID(resEmployeeUUID);
        serviceExceptionRecord.setResEmployeeUUID(resEmployeeUUID);
        serviceExceptionRecord.setResOrgUUID(orgUUID);
        serviceExceptionRecord
                .setSourceType(ServiceExceptionRecord.SOURCE_TYPE_AUTO);
        java.time.LocalDateTime nowTime = java.time.LocalDateTime.now();
        serviceExceptionRecord.setCreatedTime(nowTime);
        serviceExceptionRecord.setLastUpdateTime(nowTime);
        serviceExceptionRecord.setCreatedBy(resEmployeeUUID);
        serviceExceptionRecord.setLastUpdateBy(resEmployeeUUID);
        this.serviceEntityDAO.insertEntity(serviceExceptionRecord);
    }

    /**
     * Default way to auto record exception information
     *
     * @param exception
     * @param category
     * @param note
     * @param source
     * @param resEmployeeUUID
     * @param orgUUID
     */
    public void autoRecord(Exception exception, int category, String note,
                           String source, String resEmployeeUUID, String orgUUID) {
        ServiceExceptionRecord serviceExceptionRecord = new ServiceExceptionRecord();
        serviceExceptionRecord.setNodeLevel(ServiceEntityNode.NODELEVEL_ROOT);
        serviceExceptionRecord.setNodeName(ServiceEntityNode.NODENAME_ROOT);
        // For root node set parent uuid & root uuid as itself
        serviceExceptionRecord.setParentNodeUUID(serviceExceptionRecord
                .getUuid());
        serviceExceptionRecord
                .setRootNodeUUID(serviceExceptionRecord.getUuid());
        serviceExceptionRecord.setNote("Exception type:"
                + exception.getClass().getName() + " " + note);
        serviceExceptionRecord.setName(exception.getClass().getSimpleName());
        serviceExceptionRecord.setCategory(category);
        serviceExceptionRecord.setSource(source);
        serviceExceptionRecord
                .setCallStack(convExceptionTraceListToString(exception
                        .getStackTrace()));
        serviceExceptionRecord.setReporterUUID(resEmployeeUUID);
        serviceExceptionRecord.setResOrgUUID(orgUUID);
        serviceExceptionRecord.setResEmployeeUUID(resEmployeeUUID);
        serviceExceptionRecord
                .setSourceType(ServiceExceptionRecord.SOURCE_TYPE_AUTO);
        java.time.LocalDateTime nowTime = java.time.LocalDateTime.now();
        serviceExceptionRecord.setCreatedTime(nowTime);
        serviceExceptionRecord.setLastUpdateTime(nowTime);
        serviceExceptionRecord.setCreatedBy(resEmployeeUUID);
        serviceExceptionRecord.setLastUpdateBy(resEmployeeUUID);
        this.serviceEntityDAO.insertEntity(serviceExceptionRecord);
    }

    /**
     * Convert the exception call stack trace list to JSON-format string
     *
     * @param traceList
     * @return
     */
    public String convExceptionTraceListToString(StackTraceElement[] traceList) {
        String jsonCommand = "[";
        for (StackTraceElement element : traceList) {
            String command = "{";
            command = command + "\"className\":\"" + element.getClassName()
                    + "\",";
            command = command + "\"fileName\":\"" + element.getFileName()
                    + "\",";
            command = command + "\"lineNumber\":" + element.getLineNumber()
                    + ",";
            command = command + "\"methodName\":\"" + element.getMethodName()
                    + "\",";
            command = command + "}";
        }
        jsonCommand = jsonCommand + "]";
        return jsonCommand;
    }
}
