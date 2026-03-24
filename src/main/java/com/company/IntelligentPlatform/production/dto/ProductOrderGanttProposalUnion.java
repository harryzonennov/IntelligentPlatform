package com.company.IntelligentPlatform.production.dto;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.controller.SEUIComModel;

/**
 * model class for Production order Gantt proposal
 * @author Zhang,hang
 *
 */
public class ProductOrderGanttProposalUnion extends SEUIComModel{
	
	protected String name;
	
	protected String desc;
	
	protected List<ProductOrderGanttProposalValue> values = new ArrayList<ProductOrderGanttProposalValue>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public List<ProductOrderGanttProposalValue> getValues() {
		return values;
	}

	public void setValues(List<ProductOrderGanttProposalValue> values) {
		this.values = values;
	}

	public void addValue(ProductOrderGanttProposalValue value){
		values.add(value);
	}
	
}
