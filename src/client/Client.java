/**
 * Project Name:WebScan
 * File Name:Client.java
 * Package Name:client
 * Date:2014年1月9日下午8:31:08
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import databasesource.DatabaseSource;
import databasesource.DatabaseSourceDriver;
import databasesource.DatabaseSourceDriverFactory;
import datachecker.DataChecker;
import dataobject.ScanPlan;

/**
 * ClassName:Client <br/>
 * Date:     2014年1月9日 下午8:31:08 <br/>
 * @author   hzycaicai
 * @version  	 
 */
public class Client implements Observer{
	private static Logger logger = Logger.getLogger(Client.class.getName());
	private static final Client client = new Client();
	private ArrayList<ScanPlan> scanPlanList = null;
	private String userName = "root";
	private String passWd = "654321";
	protected DatabaseSourceDriver databaseSourceDriver = null;
	protected DatabaseSource databaseSource = null;
	protected int poolSize = 10;
	protected ExecutorService pool = Executors.newFixedThreadPool(2);
	
	
	private Client(){
		this.databaseSourceDriver = DatabaseSourceDriverFactory.getDatabaseSourceDriver(userName,passWd);
	}
	
	public static Client getClient(){
		return client;
	}
	
	public void start(){
		if(databaseSourceDriver==null){
			databaseSourceDriver = DatabaseSourceDriverFactory.getDatabaseSourceDriver(userName,passWd);
		}
		String filename = null;
		databaseSource = databaseSourceDriver.getNewDatabaseSource();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		this.scanPlanList = databaseSource.getScanPlanList();
		if(scanPlanList!=null){
			for(Iterator<ScanPlan> it = scanPlanList.iterator();it.hasNext();){
				ScanPlan scanPlan = it.next();
				System.out.println("plan"+scanPlan.getId()+" "+scanPlan.getWebSite());
				filename = "D:\\WebScan\\"+scanPlan.getId()+df.format(new Date())+".xml";
				DataChecker check = new DataChecker(scanPlan,filename);
				Thread thread = new Thread(check);
				//thread.start();
				this.pool.execute(thread);
				logger.log(Level.INFO,"add thread to the threadpool");
				//System.out.println(filename+" "+scanPlan.getId()+" "+scanPlan.getWebSite()+" "+scanPlan.getDateTime());
			}
		}
		pool.shutdown();
	}
	
	public void shutdown(){
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg!=null){
			System.out.println("Get notification from "+((ScanPlan)arg).getId()+" "+((ScanPlan)arg).getWebSite()+" status is:"+((ScanPlan)arg).getEndIbm());
		}
	}
}
