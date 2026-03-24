package com.company.IntelligentPlatform.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.PostConstruct;

// TODO-LEGACY: import org.apache.commons.httpclient.HttpStatus; // replaced by local HttpStatus stub
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO-DAO: import ...LockObjectDAO;
// TODO-DAO: import platform.foundation.DAO.LogonUserDAO;
import com.company.IntelligentPlatform.common.service.ServiceEntityManager;
import com.company.IntelligentPlatform.common.service.ServiceExceptionHelper;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.model.IReferenceNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.IServiceEntityNodeFieldConstant;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
import com.company.IntelligentPlatform.common.model.LockObject;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.Organization;
import com.company.IntelligentPlatform.common.model.LockObjectConfigureProxy;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

/**
 * Logic Manager CLASS FOR Service Entity [LockObject]
 *
 * @author
 * @date Mon Jan 07 22:47:52 CST 2013
 * <p>
 * This class is generated automatically by platform automation register
 * tool
 */
@Service
@Transactional
public class LockObjectManager extends ServiceEntityManager {

    // TODO-DAO: @Autowired
    @Autowired
    LockObjectDAO lockObjectDAO; // TODO-DAO: stub

    @Autowired
    LockObjectConfigureProxy lockObjectConfigureProxy;

    public static final int LOCK_CHECK_OK = 1;

    public static final int LOCK_CHECK_NOTOK = 2;

    public static final int LOCK_CHECK_OTHERISSUE = 3;

    // TODO-DAO: @Autowired
    @Autowired
    LogonUserDAO logonUserDAO; // TODO-DAO: stub

    public LockObjectManager() {
        super.seConfigureProxy = new LockObjectConfigureProxy();
        // TODO-DAO: super.serviceEntityDAO = new LockObjectDAO();
    }

    @PostConstruct
    public void setServiceEntityDAO() {
        // TODO-DAO: super.setServiceEntityDAO(lockObjectDAO);
    }

    @PostConstruct
    public void setSeConfigureProxy() {
        super.setSeConfigureProxy(lockObjectConfigureProxy);
    }

    /**
     * pre-check if the object list could be locked, without actually lock, for
     * lockobject with same logonUserUUID, just ignore it
     *
     * @param seList
     * @return List<ServiceEntityNode> the already lock object list by this
     * input parameters
     * @throws ServiceEntityConfigureException
     */
    public List<ServiceEntityNode> preLockServiceEntityList(
            List<ServiceEntityNode> seList, String logonUserUUID)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> preLockList = new ArrayList<>();
        for (ServiceEntityNode seNode : seList) {
            if (seNode != null) {
                List<ServiceEntityNode> backNodeList = lockObjectDAO
                        .getEntityNodeListByKey(seNode.getUuid(),
                                IReferenceNodeFieldConstant.REFUUID,
                                ServiceEntityNode.NODENAME_ROOT);
                if (backNodeList.size() > 0) {
                    preLockList.addAll(backNodeList);
                }
            }
        }
        List<ServiceEntityNode> lockResult = new ArrayList<>();
        for (ServiceEntityNode lockSENode : preLockList) {
            if (logonUserUUID.equals(lockSENode.getResEmployeeUUID())) {
                continue;
            }
            lockResult.add(lockSENode);
        }
        return lockResult;
    }

    /**
     * API to add edit lock for editing SE objects
     *
     * @param seList
     * @param logonUser : current login user uuid, if the existed with same logonUser
     *                      UUID, just ignore it
     * @throws LockObjectFailureException
     * @throws ServiceEntityConfigureException
     * @throws LogonInfoException
     */
    @Transactional
    public void lockServiceEntityList(List<ServiceEntityNode> seList,
                                      LogonUser logonUser, Organization organization)
            throws LockObjectFailureException, ServiceEntityConfigureException,
            LogonInfoException {
        if (logonUser == null) {
            throw new LogonInfoException(LogonInfoException.TYPE_NO_LOGON_USER);
        }
        synchronized (this) {
            // check lock exist
            for (ServiceEntityNode seNode : seList) {
                if (seNode != null) {
                    List<ServiceEntityNode> backNodeList = lockObjectDAO
                            .getEntityNodeListByKey(seNode.getUuid(),
                                    IReferenceNodeFieldConstant.REFUUID,
                                    ServiceEntityNode.NODENAME_ROOT);
                    if (backNodeList.size() > 0) {
                        for (ServiceEntityNode lockNode : backNodeList) {
                            if (!logonUser.getUuid().equals(
                                    lockNode.getResEmployeeUUID())) {
                                throw new LockObjectFailureException(
                                        LockObjectFailureException.TYPE_LOCK_FAILURE,
                                        seNode.getNodeName() + seNode.getUuid());
                            }
                        }
                    }
                }
            }
            // get user information
            // get logon information
            String orgUUID = ServiceEntityStringHelper.EMPTYSTRING;
            if (organization != null) {
                orgUUID = organization.getUuid();
            }
            // Add lock to each module
            List<ServiceEntityNode> lockList = Collections
                    .synchronizedList(new ArrayList<>());
            for (ServiceEntityNode seNode : seList) {
                if (seNode != null) {
                    LockObject lockObject = (LockObject) this
                            .newRootEntityNode(logonUser.getClient());
                    this.buildReferenceNode(seNode, lockObject,
                            ServiceEntityFieldsHelper.getCommonPackage(seNode
                                    .getClass()));
                    lockObject.setResEmployeeUUID(logonUser.getUuid());
                    lockObject.setResOrgUUID(orgUUID);
                    lockList.add(lockObject);
                }
            }
            lockObjectDAO.insertEntity(lockList);
        }
    }

    /**
     * free all locked object
     *
     * @param logonUser
     * @throws ServiceEntityConfigureException
     */
    @Transactional
    public void unLockSEListByLogonUser(LogonUser logonUser)
            throws ServiceEntityConfigureException {
        synchronized (this) {
            List<ServiceEntityNode> backNodeList = lockObjectDAO
                    .getEntityNodeListByKey(
                            logonUser.getUuid(),
                            IServiceEntityNodeFieldConstant.RESPONSIBLE_EMPLOYEEUUID,
                            ServiceEntityNode.NODENAME_ROOT);
            if (backNodeList != null && backNodeList.size() > 0) {
                // In case find lock, unlock it
                for (ServiceEntityNode lockSENode : backNodeList) {
                    LockObject lockObject = (LockObject) lockSENode;
                    lockObjectDAO.deleteEntityNodeByKey(lockObject.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID,
                            ServiceEntityNode.NODENAME_ROOT);
                }
            }
        }
    }

    /**
     * free all locked object
     *
     * @throws ServiceEntityConfigureException
     */
    @Transactional
    public void unLockAllSEList()
            throws ServiceEntityConfigureException {
        synchronized (this) {
            List<ServiceEntityNode> backNodeList = lockObjectDAO
                    .getEntityNodeListByKey(
                            null,
                            null,
                            ServiceEntityNode.NODENAME_ROOT);
            if (backNodeList != null && backNodeList.size() > 0) {
                // In case find lock, unlock it
                for (ServiceEntityNode lockSENode : backNodeList) {
                    LockObject lockObject = (LockObject) lockSENode;
                    lockObjectDAO.deleteEntityNodeByKey(lockObject.getUuid(),
                            IServiceEntityNodeFieldConstant.UUID,
                            ServiceEntityNode.NODENAME_ROOT);
                }
            }
        }
    }


    @Transactional
    public void unLockServiceEntityList(List<ServiceEntityNode> seList)
            throws ServiceEntityConfigureException {
        synchronized (this) {
            for (ServiceEntityNode seNode : seList) {
                List<ServiceEntityNode> backNodeList = lockObjectDAO
                        .getEntityNodeListByKey(seNode.getUuid(),
                                IReferenceNodeFieldConstant.REFUUID,
                                ServiceEntityNode.NODENAME_ROOT);
                // seNode has 1 to 1 relationship to lock object
                if (backNodeList != null && backNodeList.size() > 0) {
                    // In case find lock, unlock it
                    for (ServiceEntityNode lockSENode : backNodeList) {
                        LockObject lockObject = (LockObject) lockSENode;
                        lockObjectDAO.deleteEntityNodeByKey(
                                lockObject.getUuid(),
                                IServiceEntityNodeFieldConstant.UUID,
                                ServiceEntityNode.NODENAME_ROOT);
                    }
                } else {
                    // In case no lock found, do nothing.
                }
            }
        }
    }

    /**
     * [Admin method] should not be invoked by application unlock all of the
     * object in system
     *
     * @throws ServiceEntityConfigureException
     */
    public void admUnlockAllObject(String client)
            throws ServiceEntityConfigureException {
        List<ServiceEntityNode> backNodeList = getEntityNodeListByKey(null,
                null, ServiceEntityNode.NODENAME_ROOT, client, null);
        for (ServiceEntityNode se : backNodeList) {
            lockObjectDAO.deleteEntityNodeByKey(se.getUuid(),
                    IServiceEntityNodeFieldConstant.UUID,
                    ServiceEntityNode.NODENAME_ROOT);
        }
    }

    /**
     * generate the JSON format result for locking object
     *
     * @return
     * @throws ServiceEntityConfigureException
     */
    public String genJSONLockCheckResult(
            List<ServiceEntityNode> lockObjectList, String name, String id,
            String uuid) throws ServiceEntityConfigureException {
        String msg = ServiceEntityStringHelper.EMPTYSTRING;
        final String rcHead = "RC";
        final String msgHead = "MSG";
        final String nameHead = "NAME";
        final String idHead = "ID";
        final String uuidHead = "UUID";
        final String lockedEmpID = "lockedEmpID";
        if (lockObjectList == null || lockObjectList.size() == 0) {
            msg = "can lock the object";
            String content = ServiceEntityStringHelper.EMPTYSTRING;
            content = content + "{";
            content = content + genJSONFieldPairStr(rcHead, HttpStatus.SC_OK + "") + ",\n";
            content = content + genJSONFieldPairStr(msgHead, msg) + ",\n";
            content = content + genJSONFieldPairStr(nameHead, name) + ",\n";
            content = content + genJSONFieldPairStr(idHead, id) + ",\n";
            content = content + genJSONFieldPairStr(uuidHead, uuid) + "\n";
            content = content + "}";
            return content;
        } else {
            // by default using the first lock object information
            LockObject lockObject = (LockObject) lockObjectList.get(0);
            List<ServiceEntityNode> tmpUserList = logonUserDAO
                    .getEntityNodeListByKey(lockObject.getResEmployeeUUID(),
                            IServiceEntityNodeFieldConstant.UUID,
                            LogonUser.NODENAME);
            if (tmpUserList != null && tmpUserList.size() > 0) {
                try {
                    LogonUser logonUser = (LogonUser) tmpUserList.get(0);
                    String errorMessageFormat = ServiceExceptionHelper
                            .getErrorMessage(
                                    LockObjectFailureException.class,
                                    LockObjectFailureException.TYPE_ALREADY_LOCK_BYOTHER);
                    msg = String.format(errorMessageFormat, logonUser.getId());
                } catch (ServiceEntityInstallationException e) {
                    throw new ServiceEntityConfigureException(ServiceEntityConfigureException.PARA_SYSTEM_WRONG,
                            e.getMessage());
                }
            }
            String content = ServiceEntityStringHelper.EMPTYSTRING;
            content = content + "{";
            content = content + genJSONFieldPairStr(rcHead, "2") + ",\n";
            content = content + genJSONFieldPairStr(msgHead, msg) + ",\n";
            content = content + genJSONFieldPairStr(nameHead, name) + ",\n";
            content = content + genJSONFieldPairStr(idHead, id) + ",\n";
            content = content + genJSONFieldPairStr(uuidHead, uuid);
            if (tmpUserList != null && tmpUserList.size() > 0) {
                LogonUser logonUser = (LogonUser) tmpUserList.get(0);
                content = content + ",\n"
                        + genJSONFieldPairStr(lockedEmpID, logonUser.getId())
                        + "\n";
            }
            content = content + "}";
            return content;
        }
    }

    /**
     * generate the JSON format result for locking object in case other issues
     *
     * @param msg :exception message
     * @return
     */
    public String genJSONLockCheckOtherIssue(String msg) {
        final String rcHead = "RC";
        final String msgHead = "MSG";
        String content = ServiceEntityStringHelper.EMPTYSTRING;
        content = content + "{";
        content = content + genJSONFieldPairStr(rcHead, "1") + ",\n";
        content = content + genJSONFieldPairStr(msgHead, msg) + ",\n";
        return content;
    }

    /**
     * [Internal method] generate JSON value pair by string type field
     *
     * @param fieldName
     * @param value
     * @return
     */
    protected String genJSONFieldPairStr(String fieldName, String value) {
        String content = "\"" + fieldName + "\":";
        content = content + "\"" + value + "\"";
        return content;
    }

}
