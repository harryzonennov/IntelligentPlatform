package com.company.IntelligentPlatform.common.model;

import org.springframework.web.multipart.MultipartFile;

import com.company.IntelligentPlatform.common.model.*;

public class AttachmentConstantHelper {
	
	public static final String TYPE_PDF = "pdf";
	
	public static final String TYPE_DOC = "doc";
	
	public static final String TYPE_DOCX = "docx";
	
	public static final String TYPE_PPT = "ppt";
	
	public static final String TYPE_XLS = "xls";
	
	public static final String TYPE_XLSX = "xlsx";
	
	public static final String TYPE_JPEG = "jpeg";
	
	public static final String TYPE_JPG = "jpg";
	
	public static final String TYPE_PNG = "png";
	
	public static final String TYPE_GIF = "gif";
	
	public static final String TYPE_XML = "xml";
	
	public static final String TYPE_JRXML = "jrxml";
	
	/**
	 * Logic to git correct file type from file post fix.
	 * @param postFix
	 * @return
	 */
	public static String getFileTypeFromPostFix(String postFix){
		if(ServiceEntityStringHelper.checkNullString(postFix)){
			return null;
		}
		if(postFix.equals(TYPE_XLS) || postFix.equals("xlsx")){
			return TYPE_XLS;
		}
		if(postFix.equals(TYPE_DOC) || postFix.equals("docx")){
			return TYPE_DOC;
		}
		if(postFix.equals(TYPE_PPT) || postFix.equals("pptx")){
			return TYPE_PPT;
		}
		if(postFix.equals(TYPE_GIF) || postFix.equals("GIF")){
			return TYPE_GIF;
		}
		if(postFix.equals(TYPE_PDF) ){
			return TYPE_PDF;
		}
		if(postFix.equals(TYPE_JPEG) ){
			return TYPE_JPEG;
		}
		if(postFix.equals(TYPE_PNG)){
			return TYPE_PNG;
		}		
		return null;
	}
	
	public static String getFileTypeFromPostFix(MultipartFile multipartFile){
		String fileType = getFileTypeFromPostFix(multipartFile.getContentType());
		if(fileType != null){
			return fileType;
		}
		String[] fileNames = multipartFile.getOriginalFilename().split("\\.");
		return fileNames[fileNames.length - 1];
	}
}
