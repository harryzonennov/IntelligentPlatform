package com.company.IntelligentPlatform.common.model;

// TODO-LEGACY: import platform.foundation.Controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * Page compound information module
 * 
 * @author Zhang,Hang
 * @date 2013-4-13
 * 
 */
public class PageComInfo {

	public static final String DEF_SUBPATH = "search.html";

	protected String pageName;

	protected String subPath;

	protected int accessIndex;

	protected SEUIComModel searchModel;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public int getAccessIndex() {
		return accessIndex;
	}

	public void setAccessIndex(int accessIndex) {
		this.accessIndex = accessIndex;
	}

	public SEUIComModel getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(SEUIComModel searchModel) {
		this.searchModel = searchModel;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

}
