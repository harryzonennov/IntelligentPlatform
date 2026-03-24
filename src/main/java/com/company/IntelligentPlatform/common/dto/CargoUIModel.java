package com.company.IntelligentPlatform.common.dto;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;

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
