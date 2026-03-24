package com.company.IntelligentPlatform.common.model;

public class ServiceUILabelRequest extends ServiceEntityJSONData {
	
	public static final String FIELD_WIN_TITLE = "windowTitle";
	
	public static final String FIELD_WIN_COMMENT = "windowComment";

	protected String windowTitle;
	
	protected String windowComment;

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle;
	}

	public String getWindowComment() {
		return windowComment;
	}

	public void setWindowComment(String windowComment) {
		this.windowComment = windowComment;
	}
	
}
