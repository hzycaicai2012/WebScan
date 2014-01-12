/**
 * Project Name:WebScan
 * File Name:Client.java
 * Package Name:client
 * Date:2014年1月9日下午8:31:08
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package client;

import java.util.ArrayList;
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
import dataobject.CheckResult;
import dataobject.ScanPlan;

/**
 * ClassName:Client <br/>
 * Date:     2014年1月9日 下午8:31:08 <br/>
 * @author   hzycaicai
 * @version  	 
 */
public class Client implements Observer{
	private static Logger logger = Logger.getLogger(Client.class.getName());
	//private static final Client client = new Client();
	private ArrayList<ScanPlan> scanPlanList = null;
	private String userName = "root";
	private String passWd = "654321";
	protected DatabaseSourceDriver databaseSourceDriver = null;
	protected DatabaseSource databaseSource = null;
	protected int poolSize = 10;
	protected ExecutorService pool = Executors.newFixedThreadPool(poolSize);
	
	/*
	 * initilize the databaseSourceDriver and databaseSource
	 */
	public Client(){
		this.databaseSourceDriver = DatabaseSourceDriverFactory.getDatabaseSourceDriver(userName,passWd);
		this.databaseSource = databaseSourceDriver.getNewDatabaseSource();
	}
	
	/*public static Client getClient(){
		return client;
	}*/
	/*
	 * every time this method is called, it prefetch the scanPlanList, mark their status to 1
	 * for each plan create a thread added to the threadpool to run the safey check tool and analyse the result
	 * now the shutdown method is in the start method to shutdown the system safely
	 */
	public void start(){
		if(databaseSourceDriver==null){
			databaseSourceDriver = DatabaseSourceDriverFactory.getDatabaseSourceDriver(userName,passWd);
		}
		String filename = null;
		this.scanPlanList = databaseSource.getScanPlanList();
		if(scanPlanList!=null){
			for(Iterator<ScanPlan> it = scanPlanList.iterator();it.hasNext();){
				ScanPlan scanPlan = it.next();
				System.out.println("plan"+scanPlan.getId()+" "+scanPlan.getWebSite());
				filename = "D:\\WebScan\\"+scanPlan.getId()+".xml";
				DataChecker check = new DataChecker(scanPlan,filename);
				check.addObserver(this);
				Thread thread = new Thread(check);
				//thread.start();
				this.pool.execute(thread);
				logger.log(Level.INFO,"add thread to the threadpool");
				//System.out.println(filename+" "+scanPlan.getId()+" "+scanPlan.getWebSite()+" "+scanPlan.getDateTime());
			}
		}
		shutdown();
	}
	
	/*
	 * shutdown the threadpool and waitfor all the thread to be terminated and then reset the database
	 */
	public void shutdown(){
		pool.shutdown();
		while(!pool.isTerminated());
		reset();
	}

	/*
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * if the object is scanplan then update the status of the scanplan
	 * if the object is checkresult list, then write back the result
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ScanPlan){
			int Id = ((ScanPlan)arg).getId();
			int status = ((ScanPlan)arg).getEndIbm();
			System.out.println("Notification from Thread"+Id+" to change status to:"+status);
			databaseSourceDriver.updateScanPlan(Id,status);
		}
		else if(arg instanceof ArrayList<?>){
			System.out.println("Notification of ArrayList");
			ArrayList<CheckResult> resList = ((ArrayList<CheckResult>)arg);
			for(Iterator<CheckResult> it = resList.iterator();it.hasNext();){
				CheckResult res = it.next();
				System.out.println(res.getName()+" "+res.getRisk());
				databaseSource.writeCheckRes(res);
			}
		}	
	}
	
	/*
	 * in case for the crash and some unexpected exceptions
	 * To recover the status of the plans whose status is running while actually not
	 * add to the GUI as a button,can only called after the threadpool is safely shutdown
	 */
	public void reset(){
		System.out.println("reseting the database:");
		ArrayList<Integer> list = databaseSource.getScannedList();
		for(Iterator<Integer> it = list.iterator();it.hasNext();){
			System.out.print(it.next()+" ");
		}
		System.out.println();
		databaseSourceDriver.updateScanPlan(list);
	} 
}
