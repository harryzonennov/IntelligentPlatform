package com.company.IntelligentPlatform.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.company.IntelligentPlatform.common.model.ISQLSepcifyAttribute;
import com.company.IntelligentPlatform.common.model.IServiceModelConstants;
import com.company.IntelligentPlatform.common.model.ServiceEntityNode;
@Entity
@Table(name = "ServiceAccountDuplicateCheckResource", catalog = "platform")
public class ServiceAccountDuplicateCheckResource extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceAccountDuplicateCheckResource;

	public static final int SWITCH_ON = 1;

	public static final int SWITCH_OFF = 2;

    public static final int IMPLMENTTYPE_STANDARD = 1;

	public static final int IMPLMENTTYPE_CUSTOMER = 2;

	public static final int LOGICRELA_AND = 1;

	public static final int LOGICRELA_OR = 2;

	protected int refAccountType;

	protected int executedOrder;

	protected int switchFlag;

	@ISQLSepcifyAttribute(fieldLength = ISQLSepcifyAttribute.FIELDLENGTH_LONG)
	protected String implementClassName;

	protected int implementType;

	protected int logicRelationship;

	public ServiceAccountDuplicateCheckResource(){
		this.nodeName = NODENAME;
		this.serviceEntityName = SENAME;
		this.switchFlag = SWITCH_OFF;
		this.executedOrder = 1;
	}

	public int getRefAccountType() {
		return refAccountType;
	}

	public void setRefAccountType(int refAccountType) {
		this.refAccountType = refAccountType;
	}

	public int getExecutedOrder() {
		return executedOrder;
	}

	public void setExecutedOrder(int executedOrder) {
		this.executedOrder = executedOrder;
	}

	public int getSwitchFlag() {
		return switchFlag;
	}

	public void setSwitchFlag(int switchFlag) {
		this.switchFlag = switchFlag;
	}

	public String getImplementClassName() {
		return implementClassName;
	}

	public void setImplementClassName(String implementClassName) {
		this.implementClassName = implementClassName;
	}

	public int getImplementType() {
		return implementType;
	}

	public void setImplementType(int implementType) {
		this.implementType = implementType;
	}

	public int getLogicRelationship() {
		return logicRelationship;
	}

	public void setLogicRelationship(int logicRelationship) {
		this.logicRelationship = logicRelationship;
	}

}
