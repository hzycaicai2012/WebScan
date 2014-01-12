/**
 * Project Name:WebScan
 * File Name:DatabaseSourceDriver.java
 * Package Name:databasesource
 * Date:2014年1月9日下午9:20:13
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package databasesource;

import java.util.ArrayList;

/**
 * ClassName:DatabaseSourceDriver
 * 
 * @author   hzycaicai
 * @version  	 
 */
public abstract class DatabaseSourceDriver {
	public abstract DatabaseSource getNewDatabaseSource();
	/*
	 * update the status of the items in the prefetch database
	 */
	public abstract void updateScanPlan(int fid, int status);
	/*
	 * update the items in status 1 while not appear in checkresult database.
	 */
	public abstract void updateScanPlan(ArrayList<Integer> list);
	
}
