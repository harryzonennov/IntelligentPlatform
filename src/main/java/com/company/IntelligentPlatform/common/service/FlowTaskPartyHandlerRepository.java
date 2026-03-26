package com.company.IntelligentPlatform.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.ServiceLanHelper;
import com.company.IntelligentPlatform.common.service.LogonUserOrgException;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.SerialLogonInfo;
import com.company.IntelligentPlatform.common.model.ServiceFlowException;
import com.company.IntelligentPlatform.common.model.ServiceFlowRuntimeException;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;
import com.company.IntelligentPlatform.common.model.ServiceEntityFieldsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowTaskPartyHandlerRepository {

    @Qualifier("financeDeptManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler financeDeptManagerPartyHandler;

    @Qualifier("deptManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler deptManagerPartyHandler;

    @Qualifier("financeAccountantPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler financeAccountantPartyHandler;

    @Qualifier("companyManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler companyManagerPartyHandler;

    @Qualifier("companyBoardChairmanPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler companyBoardChairmanPartyHandler;

    @Qualifier("companyViceManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler companyViceManagerPartyHandler;

    @Qualifier("productionDeptManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler productionDeptManagerPartyHandler;

    @Qualifier("purchaseDeptManagerPartyHandler")
    @Autowired(required = false)
    protected IFlowTaskPartyHandler purchaseDeptManagerPartyHandler;

    /**
     * ===============end of register area===============
     */

    protected Logger logger = LoggerFactory.getLogger(FlowTaskPartyHandlerRepository.class);

    protected Map<String, Map<String, String>> taskPartyHandlerMapLan = new HashMap<>();

    public Map<String, String> loadFlowTaskPartyHandlerMap(String languageCode)
            throws ServiceEntityInstallationException {
        String resourcePath = this.getClass().getResource("").getPath() + "FlowTaskPartyHandlerConfigure";
        return ServiceLanHelper.initDefLanguageStrMapResource(languageCode,
                this.taskPartyHandlerMapLan, resourcePath);
    }

    /**
     * Logic to get search proxy instance by registereed id
     * @param taskPartyHandlerId
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public IFlowTaskPartyHandler getFlowTaskPartyHandlerById(String taskPartyHandlerId)
            throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException {
        Field field = ServiceEntityFieldsHelper.getServiceField(this.getClass(), taskPartyHandlerId);
        field.setAccessible(true);
        return (IFlowTaskPartyHandler)field.get(this);
    }

    /**
     * Get all the active (not nul) service UI Model instance list
     * @return
     */
    public List<IFlowTaskPartyHandler> getAllFlowTaskPartyHandlerList(){
        return ServiceEntityFieldsHelper.getDefRepoInstList(
                IFlowTaskPartyHandler.class, this);
    }

    public LogonUser getTargetUserWrapper(IFlowTaskGetLogonUserExecutor logonUserExecutor,
                                          SerialLogonInfo serialLogonInfo) throws ServiceFlowException {
        try {
            if(serialLogonInfo == null){
                throw new ServiceFlowRuntimeException(ServiceFlowRuntimeException.PARA_NOPARA_LOGONINFO,
                        this.getClass().getSimpleName());
            }
            return logonUserExecutor.getTargetLogonUser(serialLogonInfo.getHomeOrganizationUUID(), serialLogonInfo);
        } catch (LogonUserOrgException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceFlowException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getErrorMessage());
        } catch (ServiceEntityConfigureException e) {
            logger.error(ServiceEntityStringHelper.genDefaultErrorMessage(e, ""));
            throw new ServiceFlowException(ServiceFlowRuntimeException.PARA_SYSTEM_ERROR, e.getMessage());
        }
    }
}
