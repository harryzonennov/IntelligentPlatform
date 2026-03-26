package com.company.IntelligentPlatform.common.model;

import com.company.IntelligentPlatform.common.model.*;
import com.company.IntelligentPlatform.common.model.*;

public class ServiceCrossDocEventMonitor extends ServiceEntityNode {

	public static final String NODENAME = IServiceModelConstants.ServiceCrossDocEventMonitor;

	public static final String SENAME = ServiceDocumentSetting.SENAME;

	public static final int TRIG_PARENTMODE_ALL = 1;

	public static final int TRIG_PARENTMODE_ONE = 2;

	public static final int TRIG_DOCACT_SCEN_SELECTITEM = 1;

	public ServiceCrossDocEventMonitor() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		this.triggerParentMode = TRIG_PARENTMODE_ALL;
		this.triggerDocActionScenario = TRIG_DOCACT_SCEN_SELECTITEM;
	}
	
	protected int targetActionCode;

	protected int triggerHomeActionCode;

	protected int triggerDocActionScenario;

	protected int triggerParentMode;

	public int getTargetActionCode() {
		return targetActionCode;
	}

	public void setTargetActionCode(int targetActionCode) {
		this.targetActionCode = targetActionCode;
	}

	public int getTriggerHomeActionCode() {
		return triggerHomeActionCode;
	}

	public void setTriggerHomeActionCode(int triggerHomeActionCode) {
		this.triggerHomeActionCode = triggerHomeActionCode;
	}

	public int getTriggerDocActionScenario() {
		return triggerDocActionScenario;
	}

	public void setTriggerDocActionScenario(int triggerDocActionScenario) {
		this.triggerDocActionScenario = triggerDocActionScenario;
	}

	public int getTriggerParentMode() {
		return triggerParentMode;
	}

	public void setTriggerParentMode(int triggerParentMode) {
		this.triggerParentMode = triggerParentMode;
	}
}
