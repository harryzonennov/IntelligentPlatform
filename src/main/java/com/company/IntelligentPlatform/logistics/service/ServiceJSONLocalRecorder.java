package com.company.IntelligentPlatform.logistics.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ServiceJSONLocalRecorder {

	public static final String DEFAULT_FOLDER_URL = "c:/data";

	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
			"yyyyMMdd_hhmmss");

	/**
	 * [Internal method] Write the content into file by absolute file name
	 * 
	 * @param url
	 *            :Absolute file name
	 * @param content
	 *            :content in forms of String
	 * @param flag
	 *            indicate whether to overwrite the existed file
	 * @throws IOException
	 */
	public void wirteToFile(String url, String content, boolean overFlag)
			throws IOException {

		// File file = new File(url);
		// try {
		// if (file.isFile()) {
		// // In case file already exist
		// if(overFlag){
		// file = new File(file.getAbsolutePath());
		// }else{
		// return;
		// }
		// }
		// OutputStreamWriter os = null;
		// os = new OutputStreamWriter(new FileOutputStream(file));
		// os.write(content);
		// os.close();
		// } catch (IOException ex) {
		// throw new IOException("error when writing the JSON file");
		// }
	}

	public String generateDefaultFolderPath() throws IOException {
		String postTimeStamp = "JSON_" + TIMESTAMP_FORMAT.format(new Date());
		String folderURL = DEFAULT_FOLDER_URL + File.separator + postTimeStamp;
		File folder = new File(folderURL);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		return folderURL;
	}

}
