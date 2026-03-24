package com.company.IntelligentPlatform.common.controller;

// TODO-LEGACY: import org.flowable.engine.RuntimeService;
// TODO-LEGACY: import org.flowable.engine.TaskService;
// TODO-LEGACY: import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.ServiceBasicUtilityController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.AuthorizationException;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.FlowableProcessManager;
import com.company.IntelligentPlatform.common.service.FlowableTaskManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.LogonUserManager;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.ISystemActionCode;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceCollectionsHelper;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.ArrayList;
import java.util.List;

@Scope("session")
@Controller(value = "flowableTaskListController")
@RequestMapping(value = "/flowableTask")
public class FlowableTaskListController extends SEListController {

    public static final String AOID_RESOURCE = IServiceModelConstants.FlowRouter;

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected LogonUserManager logonUserManager;

    @Autowired
    protected ServiceBasicUtilityController serviceBasicUtilityController;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected TaskService taskService;

    // TODO-LEGACY: @Autowired

    // TODO-LEGACY:     protected RuntimeService runtimeService;

    @Autowired
    protected FlowableTaskManager flowableTaskManager;

    @Autowired
    protected FlowableProcessManager flowableProcessManager;

    @RequestMapping(value = "/loadModuleListService", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String loadModuleListService() {
        // TODO-LEGACY: Flowable BPM not yet migrated
        return ServiceJSONParser.genDefOKJSONObject(new ArrayList());
    }


    @RequestMapping(value = "/deleteProcessInstance", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String deleteProcessInstance(String processInstanceId) {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            flowableProcessManager.deleteProcessInstance(processInstanceId);
            return ServiceJSONParser.genDeleteOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

    @RequestMapping(value = "/reDeployment", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String reDeployment() {
        try {
            serviceBasicUtilityController.preCheckResourceAccessCore(AOID_RESOURCE, ISystemActionCode.ACID_EDIT);
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            flowableProcessManager.deployDefDocFlow();
            return ServiceJSONParser.genDeleteOKResponse();
        } catch (AuthorizationException | LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        }
    }

}
