package com.company.IntelligentPlatform.common.model;

import java.io.Serializable;

/**
 * Extended run time settings
 * @author Zhang,Hang
 *
 */
public class ExtendedSettings  implements Serializable {
	
	private boolean infoSwitch;

	public boolean getInfoSwitch() {
		return infoSwitch;
	}

	public void setInfoSwitch(boolean infoSwitch) {
		this.infoSwitch = infoSwitch;
	}

}
