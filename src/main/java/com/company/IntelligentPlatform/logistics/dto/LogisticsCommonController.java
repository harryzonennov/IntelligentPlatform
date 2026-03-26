package com.company.IntelligentPlatform.logistics.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.company.IntelligentPlatform.common.controller.SEEditorController;
import com.company.IntelligentPlatform.common.controller.LogonActionController;

@Scope("session")
@Controller(value = "logisticsCommonController")
@RequestMapping(value = "/logistics")
public class LogisticsCommonController extends SEEditorController {

	
	@Autowired
	protected LogonActionController logonActionController;

}
