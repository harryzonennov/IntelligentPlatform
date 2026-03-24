package com.company.IntelligentPlatform.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.company.IntelligentPlatform.common.controller.SEListController;
import com.company.IntelligentPlatform.common.service.LogonUserMessageException;
import com.company.IntelligentPlatform.common.service.LogonUserMessageManager;

@Scope("session")
@Controller(value = "messageCategoryListController")
@RequestMapping(value = "/messageCategory")
public class MessageCategoryListController extends SEListController {
	
	@Autowired
	protected LogonUserMessageManager logonUserMessageManager;
	
	protected String getListViewName(int uiFlag) {
		if (uiFlag == UIFLAG_STANDARD) {
			return "MessageCategoryList";
		}
		if (uiFlag == UIFLAG_CHOOSER) {
			return "MessageCategoryChooser";
		}
		return "MessageCategoryList";
	}
	
	@RequestMapping(value = "/loadModuleList")
	public ModelAndView loadModuleList() {
		return loadModuleCore(UIFLAG_STANDARD);
	}
	
	@RequestMapping(value = "/loadModuleChooser")
	public ModelAndView loadModuleChooser() {
		return loadModuleCore(UIFLAG_CHOOSER);
	}
	
	public ModelAndView loadModuleCore(int uiFlag) {
		try{
			Map<Integer, String> categoryMap = logonUserMessageManager.getAllCategoryMap();
			List<MessageCategoryUIModel> messageCategoryList = new ArrayList<MessageCategoryUIModel>();
			Set<Integer> keys = categoryMap.keySet();
			for(Iterator<Integer> iterator = keys.iterator(); iterator.hasNext();){
				Integer key = iterator.next();
				String value = categoryMap.get(key);
				MessageCategoryUIModel messageCategoryUIModel = new MessageCategoryUIModel();
				messageCategoryUIModel.setMessageCategoryValue(value);
				messageCategoryUIModel.setMessageCategory(key);
				messageCategoryList.add(messageCategoryUIModel);
			}
			ModelAndView mav = new ModelAndView();
			mav.setViewName(getListViewName(uiFlag));			
			mav.addObject("messageCategoryList", messageCategoryList);
			mav.addObject(LABEL_UIFLAG, uiFlag);
			return mav;
		}catch(LogonUserMessageException ex){
			ModelAndView mav = new ModelAndView();
			mav.setViewName(getErrorPage());
			mav.addObject(MESSAGE_TOKEN, ex.getErrorMessage());
			mav.addObject(LABEL_UIFLAG, uiFlag);
			return mav;
		}
	}

}
