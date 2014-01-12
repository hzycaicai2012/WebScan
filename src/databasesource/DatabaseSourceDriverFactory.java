/**
 * Project Name:WebScan
 * File Name:DatabaseSourceDriverFactory.java
 * Package Name:databasesource
 * Date:2014年1月10日上午1:15:45
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package databasesource;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ClassName:DatabaseSourceDriverFactory
 *
 * @author   hzycaicai
 * @version  	 
 */
public class DatabaseSourceDriverFactory {
	//private static final String prefetchDriverClassName = "org.sqlite.JDBC";
	//private static final String writeDriverClassName = "org.sqlite.JDBC";
	private static final String prefetchDriverClassName = "com.mysql.jdbc.Driver";
	private static final String writeDriverClassName = "com.mysql.jdbc.Driver";
	//private static final String prefetchConnectionString = "jdbc:sqlite:C:\\Users\\hzycaicai\\Desktop\\roadHZ_2.db3";
	//private static final String writeConnectionString = "jdbc:sqlite:C:\\Users\\hzycaicai\\Desktop\\roadHZ_2.db3";
	private static final String prefetchConnectionString = "jdbc:mysql://127.0.0.1:3306/security";
	private static final String writeConnectionString = "jdbc:mysql://127.0.0.1:3306/security";
	
	private static Logger logger = Logger.getLogger(DatabaseSourceDriverFactory.class.getName());
	
	/*
	 * get databaseSourceDriver for sqlite~
	 */
	public static DatabaseSourceDriver getDatabaseSourceDriver(){
		 try{
			 Class.forName(prefetchDriverClassName);
			 Class.forName(writeDriverClassName);
			 return new SimpleDatabaseSourceDriver(prefetchConnectionString, writeConnectionString);
		 } catch(Exception ex){
			 logger.log(Level.SEVERE, "get database source:", ex);
		 }
		 return null;
	}
	/*
	 * get databaseSourceDriver for mysql~
	 */
	public static DatabaseSourceDriver getDatabaseSourceDriver(String userName, String passWd){
		try{
			 Class.forName(prefetchDriverClassName);
			 Class.forName(writeDriverClassName);
			 //System.out.print("get mysql source Driver~");
			 return new MysqlDatabaseSourceDriver(prefetchConnectionString, writeConnectionString, userName, passWd);
		 } catch(Exception ex){
			 logger.log(Level.SEVERE, "get database source:", ex);
		 }
		 return null;
	}
}
