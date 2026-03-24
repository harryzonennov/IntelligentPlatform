package com.company.IntelligentPlatform.common.model;

import java.util.ArrayList;
import java.util.List;

import com.company.IntelligentPlatform.common.model.*;

public class CompressScriptMapping {
	
	protected String outputFile;
	
	protected List<CompressScriptFileUnion> inputFile = new ArrayList<CompressScriptFileUnion>();

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public List<CompressScriptFileUnion> getInputFile() {
		return inputFile;
	}

	public void setInputFile(List<CompressScriptFileUnion> inputFile) {
		this.inputFile = inputFile;
	}
	
	public void addInputFile(String fileName){
		if(!ServiceCollectionsHelper.checkNullList(inputFile)){
			boolean hitFlag = false;
			for(CompressScriptFileUnion fileUnion:inputFile){
				if(fileName.equals(fileUnion.getFileName())){
					hitFlag = true;
					break;
				}
			}
			if(!hitFlag){
				CompressScriptFileUnion newFileUnion = new CompressScriptFileUnion(fileName);
				inputFile.add(newFileUnion);
			}
		}else{
			// Add union directly
			CompressScriptFileUnion newFileUnion = new CompressScriptFileUnion(fileName);
			inputFile.add(newFileUnion);
		}
	}

}
