package com.company.IntelligentPlatform.logistics.dto;

import com.company.IntelligentPlatform.logistics.model.InboundItem;
import com.company.IntelligentPlatform.common.model.Cargo;
import com.company.IntelligentPlatform.common.controller.SEUIComModel;
import com.company.IntelligentPlatform.common.controller.ISEDropDownResourceMapping;
import com.company.IntelligentPlatform.common.controller.ISEUIModelMapping;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;

/**
 * Inbound delivery UI Model
 ** 
 * @author
 * @date Wed Oct 16 19:00:12 CST 2013
 * 
 *       This class is generated automatically by platform automation register
 *       tool
 */
public class DeliveryCargoUIModel extends SEUIComModel {

	@ISEUIModelMapping(fieldName = "uuid", seName = Cargo.SENAME, nodeName = Cargo.NODENAME, hiddenFlag = true)
	protected String uuid;

	@ISEUIModelMapping(fieldName = "name", seName = Cargo.SENAME, nodeName = Cargo.NODENAME)
	protected String cargoName;

	@ISEUIModelMapping(fieldName = "address", seName = IServiceModelConstants.BookingNote, nodeName = IServiceModelConstants.BookingNoteCargo)
	protected double amount;

	@ISEUIModelMapping(fieldName = "weight", seName = IServiceModelConstants.BookingNote, nodeName = IServiceModelConstants.BookingNoteCargo)
	protected double weight;

	@ISEUIModelMapping(fieldName = "volume", seName = IServiceModelConstants.BookingNote, nodeName = IServiceModelConstants.BookingNoteCargo)
	protected double volume;

	@ISEUIModelMapping(fieldName = "declaredValue", seName = IServiceModelConstants.BookingNote, nodeName = IServiceModelConstants.BookingNoteCargo)
	protected double declaredValue;

	/**
	 * value should be consistence with Delivery document type
	 */
	@ISEUIModelMapping(fieldName = "documentType", seName = InboundItem.SENAME, nodeName = InboundItem.NODENAME)
	@ISEDropDownResourceMapping(resouceMapping = "Delivery_documentType", valueFieldName = "documentTypeValue")
	protected int documentType;

	@ISEUIModelMapping(showOnEditor = false)
	protected String documentTypeValue;

	protected String documentUUID;

	protected String documentID;

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

	public int getDocumentType() {
		return documentType;
	}

	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTypeValue() {
		return documentTypeValue;
	}

	public void setDocumentTypeValue(String documentTypeValue) {
		this.documentTypeValue = documentTypeValue;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public String getDocumentID() {
		return documentID;
	}

	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}

}
