/**
 * Project Name:WebScan
 * File Name:DataChecker.java
 * Package Name:datachecker
 * Date:2014年1月10日上午1:36:44
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package datachecker;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.SimpleXMLAnalyser;
import util.XMLAnalyser;
import dataobject.ScanPlan;

/**
 * ClassName:DataChecker
 *
 * @author   hzycaicai
 * @version  	 
 */
public class DataChecker extends Observable implements Runnable{
	private static Logger logger = Logger.getLogger(DataChecker.class.getName());
	//private DatabaseSource databaseSource = null;
	
	private ScanPlan plan = null;
	private String fileName = null;

	public DataChecker(ScanPlan plan, String fileName/*, DatabaseSource databaseSource*/){
		this.plan = plan;
		this.fileName = fileName;
		//this.databaseSource = databaseSource;
	}
	
	@Override
	public void run(){
		if(this.plan != null && this.fileName != null){
			System.out.println("Thread"+plan.getId()+" is running:"+plan.getWebSite());
			Runtime run = Runtime.getRuntime();
			try{
				String str = "D:\\Program Files\\IBM\\AppScan Standard\\AppScanCMD.exe exec /starting_url "+
									plan.getWebSite()+" /report_file "+this.fileName+" /report_type xml";
				Process proc = run.exec(str);
				int exitVal = proc.waitFor();
				//System.out.println("exitVal:"+exitVal);
				if(exitVal<0){
					notifyRes(-1);
				}
				else{
					XMLAnalyser analyser = new SimpleXMLAnalyser(plan.getId(),plan.getSysId(),this.fileName);
					notifyRes(2);
					this.setChanged();
					this.notifyObservers(analyser.analyse());
				}
			} catch(Exception ex){
				notifyRes(-1);
				logger.log(Level.SEVERE, "run cmd error:",ex);
			}
			System.out.println("Thread"+plan.getId()+" is exiting:"+plan.getWebSite());
		}
	}
	
	public void notifyRes(int status){
		if(plan!=null){
			plan.setEndIbm(status);
			this.setChanged();
			this.notifyObservers(plan);
		}
	}
}
