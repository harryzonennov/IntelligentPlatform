package com.company.IntelligentPlatform.common.model;



public class ServiceExceptionRecord extends ServiceEntityNode {

	public static final String NODENAME = ServiceEntityNode.NODENAME_ROOT;

	public static final String SENAME = IServiceModelConstants.ServiceExceptionRecord;

	public ServiceExceptionRecord() {
		super.nodeName = NODENAME;
		super.serviceEntityName = SENAME;
		priority = PRIORITY_NORMAL;
		category = CATEGORY_UNKNOWN;
		status = STATUS_INITIAL;
		id = uuid.substring(0, 6);
		this.createdTime = java.time.LocalDateTime.now();
		this.lastUpdateTime = java.time.LocalDateTime.now();
	}

	protected String callStack;

	protected String source;

	protected String processorUUID;

	protected String reporterUUID;

	protected String processorName;

	protected String reporterName;

	protected int category;

	protected int priority;

	protected int status;

	protected int sourceType;

	public static int CATEGORY_NORMAL = 1;

	public static int CATEGORY_DATA_ERROR = 2;

	public static int CATEGORY_TECH_ERROR = 3;

	public static int CATEGORY_UNKNOWN = 6;

	public static int PRIORITY_LOW = 1;

	public static int PRIORITY_NORMAL = 2;

	public static int PRIORITY_HIGH = 3;

	public static int PRIORITY_VERY_HIGH = 4;

	public static int STATUS_INITIAL = 1;

	public static int STATUS_INPROCESS = 2;

	public static int STATUS_AUTHORACTION = 3;

	public static int STATUS_COMPLETED = 4;

	public static int STATUS_CONFIRMED = 5;

	public static int STATUS_WASTED = 6;

	public static int STATUS_IGNORED = 7;

	public static int SOURCE_TYPE_AUTO = 1;

	public static int SOURCE_TYPE_MANUAL = 2;

	public String getCallStack() {
		return callStack;
	}

	public void setCallStack(String callStack) {
		this.callStack = callStack;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProcessorUUID() {
		return processorUUID;
	}

	public void setProcessorUUID(String processorUUID) {
		this.processorUUID = processorUUID;
	}

	public String getReporterUUID() {
		return reporterUUID;
	}

	public void setReporterUUID(String reporterUUID) {
		this.reporterUUID = reporterUUID;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

}
