package com.company.IntelligentPlatform.production.dto;

/**
 * Internal model class for Gantt proposal value
 * @author Zhang,hang
 *
 */
public class ProductOrderGanttProposalValue {
	
	public static final String CUSCLASS_GANTTRED = "ganttRed";
	
	public static final String CUSCLASS_GANTTGREEN = "ganttGreen";
	
	public static final String CUSCLASS_GANTTBLUE = "ganttBlue";
	
	protected String from;
	
	protected String to;
	
	protected String label;
	
	protected String customClass;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCustomClass() {
		return customClass;
	}

	public void setCustomClass(String customClass) {
		this.customClass = customClass;
	}
	
}
