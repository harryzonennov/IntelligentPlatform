package com.company.IntelligentPlatform.common.controller;

import net.sf.json.JSONObject;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.company.IntelligentPlatform.common.controller.LogonActionController;
import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.FlowableTaskUIModel;
import com.company.IntelligentPlatform.common.service.ServiceEntityInstallationException;
import com.company.IntelligentPlatform.common.service.LogonInfoException;
import com.company.IntelligentPlatform.common.service.ServiceJSONParser;
import com.company.IntelligentPlatform.common.service.FlowableTaskManager;
import com.company.IntelligentPlatform.common.service.ServiceFlowRuntimeEngine;
import com.company.IntelligentPlatform.common.service.ServiceModuleProxyException;
import com.company.IntelligentPlatform.common.model.LogonUser;
import com.company.IntelligentPlatform.common.model.ServiceEntityConfigureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("session")
@Controller(value = "serviceFlowRuntimeEngineController")
@RequestMapping(value = "/serviceFlowRuntime")
public class ServiceFlowRuntimeEngineController  extends SEEditorController {

    @Autowired
    protected LogonActionController logonActionController;

    @Autowired
    protected ServiceFlowRuntimeEngine serviceFlowRuntimeEngine;

    @Autowired
    protected FlowableTaskManager flowableTaskManager;

    private ServiceFlowRuntimeEngine.FlowBusinessKey parseToBusinessKeyModel(String request) {
        JSONObject jsonObject = JSONObject.fromObject(request);
        @SuppressWarnings("rawtypes")
        Map<String, Class> classMap = new HashMap<>();
        return (ServiceFlowRuntimeEngine.FlowBusinessKey) JSONObject.toBean(jsonObject,
                ServiceFlowRuntimeEngine.FlowBusinessKey.class, classMap);
    }

    @RequestMapping(value = "/checkInvolveTaskStatus", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String checkTaskStatus(@RequestBody String request) {
        ServiceFlowRuntimeEngine.FlowBusinessKey businessKeyModel = parseToBusinessKeyModel(request);
        try {
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(businessKeyModel.getDocumentType(),
                    businessKeyModel.getUuid());
            ServiceFlowRuntimeEngine.InvolveTaskResult involveTaskResult = serviceFlowRuntimeEngine.checkInvolveTaskResult(businessKey
                    , logonUser.getUuid());
            return ServiceJSONParser.genDefOKJSONObject(involveTaskResult.getInvolveTaskStatus());
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        }
    }

    @RequestMapping(value = "/getInvolveTaskList", produces = "text/html;charset=UTF-8")
    public @ResponseBody
    String getInvolveTaskList(@RequestBody String request) {
        ServiceFlowRuntimeEngine.FlowBusinessKey businessKeyModel = parseToBusinessKeyModel(request);
        try {
            LogonUser logonUser = logonActionController.getLogonUser();
            if (logonUser == null) {
                throw new LogonInfoException(
                        LogonInfoException.TYPE_NO_LOGON_USER);
            }
            String businessKey = ServiceFlowRuntimeEngine.encodeBusinessKey(businessKeyModel.getDocumentType(),
                    businessKeyModel.getUuid());
            List<Task> taskList = serviceFlowRuntimeEngine.getInvolveTaskList(businessKey);
            List<FlowableTaskUIModel> flowableTaskUIModelList =
                    flowableTaskManager.getModuleListCore(taskList,
                    logonActionController.getLogonInfo());
            return ServiceJSONParser.genDefOKJSONArray(flowableTaskUIModelList);
        } catch (LogonInfoException e) {
            return e.generateSimpleErrorJSON();
        } catch (ServiceEntityInstallationException | ServiceEntityConfigureException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getMessage());
        } catch (ServiceModuleProxyException e) {
            return ServiceJSONParser.generateSimpleErrorJSON(e.getErrorMessage());
        }
    }

}
