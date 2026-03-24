package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;


public class ActionCodeUIModel extends SEUIComModel implements Comparable<ActionCodeUIModel> {
	
	protected boolean selectedFlag = false;

	public boolean isSelectedFlag() {
		return selectedFlag;
	}

	public void setSelectedFlag(boolean selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

	@Override
	public int compareTo(ActionCodeUIModel other) {	
		if(this.getName() != null){
			return this.getName().compareTo(other.getName());
		}
		return 0;
	}	

}
