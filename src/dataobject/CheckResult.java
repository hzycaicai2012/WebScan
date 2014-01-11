/**
 * Project Name:WebScan
 * File Name:CheckResult.java
 * Package Name:dataobject
 * Date:2014年1月9日下午10:31:44
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package dataobject;
/**
 * ClassName:CheckResult
 *
 * @author   hzycaicai
 * @version  	 
 */
public class CheckResult {

	private int status = 0;
	private int scanPlanId;
	private String name = null;
	private String risk = null;
	private String cause = null;
	private String text = null;
	private String description = null;
	private String WASC = null;
	private String CVE = null;
	private String CWE = null;
	private String recommend = null;
	private String severity = null;
	private int sysId;
	
	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getScanPlanId(){
		return this.scanPlanId;
	}
	
	public void setScanPlanId(int id){
		this.scanPlanId = id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getRisk(){
		return this.risk;
	}
	
	public void setRisk(String risk){
		this.risk = risk;
	}
	
	public String getCause(){
		return this.cause;
	}
	
	public void setCause(String cause){
		this.cause = cause;
	}
	
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getWASC(){
		return this.WASC;
	}
	
	public void setWASC(String WASC){
		this.WASC = WASC;
	}
	
	public String getCVE(){
		return this.CVE;
	}
	
	public void setCVE(String CVE){
		this.CVE = CVE;
	}
	
	public String getCWE(){
		return this.CWE;
	}
	
	public void setCWE(String CWE){
		this.CWE = CWE;
	}
	
	public String getRecommend(){
		return this.recommend;
	}
	
	public void setRecommend(String recommend){
		this.recommend = recommend;
	}
	
	public String getSeverity(){
		return this.severity;
	}
	
	public void setSeverity(String severity){
		this.severity = severity;
	}
	
	public int getSysId(){
		return this.sysId;
	}
	
	public void setSysId(int id){
		this.sysId = id;
	}
	
}
