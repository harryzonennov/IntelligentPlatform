package com.company.IntelligentPlatform.production.model;

import com.company.IntelligentPlatform.common.model.IDefDocumentResource;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;

/**
 * Model to
 * @author Zhang, Hang
 *
 */
public class ProdPlanTargetMatItem extends ProdOrderTargetMatItem {

	public final static String NODENAME = IServiceModelConstants.ProdPlanTargetMatItem;

	public final static String SENAME = ProductionPlan.SENAME;

	protected int processIndex;

	protected String refSerialId;

	public ProdPlanTargetMatItem() {
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.itemStatus = STATUS_INIT;
		this.homeDocumentType = IDefDocumentResource.DOCUMENT_TYPE_PRODUCTIONPLAN;
	}

	public int getProcessIndex() {
		return processIndex;
	}

	public void setProcessIndex(int processIndex) {
		this.processIndex = processIndex;
	}

	public String getRefSerialId() {
		return refSerialId;
	}

	public void setRefSerialId(String refSerialId) {
		this.refSerialId = refSerialId;
	}

}
