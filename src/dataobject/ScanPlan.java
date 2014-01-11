/**
 * Project Name:WebScan
 * File Name:ScanPlan.java
 * Package Name:dataobject
 * Date:2014年1月9日下午9:27:47
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package dataobject;

import java.util.Date;

/**
 * ClassName:ScanPlan
 *
 * @author  hzycaicai
 * @version  	 
 */
public class ScanPlan {
	private int id;
	private String webSite = null;
	private int type;
	private Date dateTime;
	private int end;
	private int endIbm;
	private int sysId;
	
	/**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * @param id the id to set
     */
    public void setWebSite(String site) {
        this.webSite = site;
    }
    
    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
    /**
     * @return the dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    
    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }
    
    /**
     * @return the endIbm
     */
    public int getEndIbm() {
        return endIbm;
    }

    /**
     * @param endIbm the endIbm to set
     */
    public void setEndIbm(int endIbm) {
        this.endIbm = endIbm;
    }
    
    /**
     * @return the sysId
     */
    public int getSysId() {
        return sysId;
    }

    /**
     * @param sysId the sysId to set
     */
    public void setSysId(int sysId) {
        this.sysId = sysId;
    }
    
}
