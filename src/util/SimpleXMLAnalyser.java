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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataobject.CheckResult;

/**
 * ClassName:XMLAnalyser
 *
 * @author   hzycaicai
 * @version  	 
 */
public class SimpleXMLAnalyser extends XMLAnalyser{
	private static Logger logger = Logger.getLogger(SimpleXMLAnalyser.class.getName());
	private String fileName;
	private int fid;
	private int sysId;
	private String regName = "<name>([\\s\\S]*?)</name>";
	private String regTestDesc = "<testDescription>([\\s\\S]*?)</testDescription>";
	private String regSecurityRisk = "<securityRisk>([\\s\\S]*?)</securityRisk>";
	private String regCause = "<cause>([\\s\\S]*?)</cause>";
	private String regText = "<testTechnicalDescription>([\\s\\S]*?)</testTechnicalDescription>";
	private String regWASC = "<threatClassification>([\\s\\S]*?)</threatClassification>";
	//private static final String regCVE = "";
	private String regCWE = "<cwe>([\\s\\S]*?)</cwe>";
	private String regFixRecommend = "<fixRecommendations>([\\s\\S]*?)</fixRecommendations>";
	private String regSeverity = "<Severity>([\\s\\S]*?)</Severity>";
	private String delChar = "<([\\s\\S]*?)>";
	private ArrayList<CheckResult> checkResList = null;
	
	public SimpleXMLAnalyser(int fid, int sysId, String fileName){
		this.fid = fid;
		this.sysId = sysId;
		this.fileName = fileName;
	}
	
	
	/*
	 * @see util.XMLAnalyser#analyse()
	 * Analyze the XML file and may throw exceptions for string index out of bounds
	 * Use regex expressions to match the content and get the check result
	 */
	public ArrayList<CheckResult> analyse() throws Exception{
		String content = getFileContent();
		checkResList = new ArrayList<CheckResult>();
		if(content!=null){
			int start = content.indexOf("<IssueTypes>");
			int end = content.indexOf("</IssueTypes>");
			//if(start>=0&&end>=0&&start<end){
			content = content.substring(start, end);
			if(content!=null){
				String[] seg = content.split("</IssueType>");
				String temp;
				for(int i=0;i<(seg.length-1);++i){
					CheckResult checkRes = new CheckResult();
					checkRes.setScanPlanId(fid);
					System.out.println("ThreadId:"+fid);
					checkRes.setSysId(sysId);
					seg[i] = seg[i].trim();
					temp = match(regName, seg[i]);
					checkRes.setName(temp);
					System.out.println("Name:"+temp);
					
					temp = match(regSecurityRisk,seg[i]);
					checkRes.setRisk(temp);
					System.out.println("SecurityRisk:"+temp);
					
					temp = match(regCause,seg[i]);
					checkRes.setCause(temp);
					System.out.println("Cause:"+temp);
					
					temp = match(regText,seg[i]);
					temp = replace(delChar,"",temp);
					temp = temp.trim();
					temp = replace(" ","",temp);
					checkRes.setText(temp);
					System.out.println("Text:"+temp);
					
					temp = match(regTestDesc, seg[i]);
					checkRes.setDescription(temp);
					System.out.println("TestDesc:"+temp);
					
					temp = match(regWASC,seg[i]);
					temp = match(regName,temp);
					checkRes.setWASC(temp);
					System.out.println("WASC:"+temp);
					
					temp = match(regCWE,seg[i]);
					temp = replace(delChar,"",temp);
					temp = temp.trim();
					checkRes.setCWE(temp);
					System.out.println("CWE:"+temp);
					
					temp = match(regFixRecommend,seg[i]);
					temp = replace(delChar,"",temp);
					temp = temp.trim();
					checkRes.setRecommend(temp);
					System.out.println("FixRecommend:"+temp);
					
					temp = match(regSeverity,seg[i]);
					System.out.println("Severity:"+temp);
					checkRes.setSeverity(temp);
					checkRes.setStatus(1);
					checkRes.setCVE("");
					checkResList.add(checkRes);
				}
				return checkResList;
			}
		}
		return null;
	}
	
	/*
	 * read the file and get the content in the XML file
	 */
	private String getFileContent(){
		if(fileName!=null){
			BufferedReader reader = null;
			String content = null;
			try{
				//for Chinese, using the encoding UTF-8
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
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
	
	/*
	 * method for regex expression match
	 */
	private String match(String regex, String content){
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(content);
		if(matcher.find()){
			return matcher.group(1);
		}
		return null;
	}
	
	/*
	 * method for regex expression replace
	 */
	private String replace(String regex, String replace, String content){
		if(content!=null&&regex!=null){
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(content);
			String temp = matcher.replaceAll(replace);
			return temp;
		}
		return null;
	}
	
}
