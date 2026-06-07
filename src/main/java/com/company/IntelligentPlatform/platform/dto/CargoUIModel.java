package com.company.IntelligentPlatform.platform.dto;

import com.company.IntelligentPlatform.platform.controller.SEUIComModel;
import com.company.IntelligentPlatform.platform.controller.ISEDropDownResourceMapping;

public class CargoUIModel extends SEUIComModel{
	
	@ISEDropDownResourceMapping(resouceMapping = "Cargo_cargoType", valueFieldName = "cargoTypeValue")
	protected int cargoType;

	public int getCargoType() {
		return cargoType;
	}

	public void setCargoType(int cargoType) {
		this.cargoType = cargoType;
	}

}
