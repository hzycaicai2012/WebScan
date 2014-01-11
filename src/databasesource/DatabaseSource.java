/**
 * Project Name:WebScan
 * File Name:DatabaseSource.java
 * Package Name:databasesource
 * Date:2014��1��9������9:24:54
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package databasesource;

import java.util.ArrayList;

import dataobject.CheckResult;
import dataobject.ScanPlan;

/**
 * ClassName:DatabaseSource
 *
 * @author   hzycaicai
 * @version  	 
 */
public abstract class DatabaseSource {
	
	/*
	 * return the arraylist of the scanplan
	 */
	public abstract ArrayList<ScanPlan> getScanPlanList();
	
	/*
	 * write back the checkresult to the database 
	 */
	public abstract void writeCheckRes(CheckResult res);
	
	public abstract void beginTransaction();

    public abstract void commit();

    public abstract void rollback();
    
    public abstract void shutdown();

}
