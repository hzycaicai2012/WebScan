/**
 * Project Name:WebScan
 * File Name:XMLAnalyser.java
 * Package Name:util
 * Date:2014年1月12日上午12:56:11
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName:XMLAnalyser
 *
 * @author   hzycaicai
 * @version  	 
 */
public class XMLAnalyser {
	private static Logger logger = Logger.getLogger(XMLAnalyser.class.getName());
	private String fileName;
	
	public XMLAnalyser(String fileName){
		this.fileName = fileName;
	}
	
	public void Analyser(){
		String content = getFileContent();
		if(content!=null){
			//Analyse the content and return the CheckResult
		}
	}
	
	private String getFileContent(){
		if(fileName!=null){
			File file = new File(fileName);
			BufferedReader reader = null;
			String content = null;
			try{
				reader = new BufferedReader(new FileReader(file));
				String temp = null;
				while((temp = reader.readLine())!=null){
					content+=temp;
				}
				reader.close();
				return content;
			} catch(IOException ex){
				logger.log(Level.SEVERE,"Read the XML file",ex);
			}
		}
		return null;
	}
}
