/**
 * Project Name:WebScan
 * File Name:SimpleDatabaseSourceDriver.java
 * Package Name:databasesource
 * Date:2014��1��10������12:13:05
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/
/**
 * Project Name:WebScan
 * File Name:SimpleDatabaseSourceDriver.java
 * Package Name:databasesource
 * Date:2014��1��10������12:13:05
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
 */

package databasesource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataobject.ScanPlan;

/**
 * ClassName:SimpleDatabaseSourceDriver
 *
 * @author   hzycaicai
 * @version  	 
 */

public class SimpleDatabaseSourceDriver extends DatabaseSourceDriver {
	private static Logger logger = Logger.getLogger(SimpleDatabaseSourceDriver.class.getName());
    protected String prefetchConnectionURL;
    protected String writeConnectionURL;
    
    protected ArrayList<ScanPlan> scanPlanList = null;
    
    public SimpleDatabaseSourceDriver(String prefetchURL, String writeURL){
    	this.prefetchConnectionURL = prefetchURL;
    	this.writeConnectionURL = writeURL;
    }
    
    protected void startPrefetch(Connection readConn) throws SQLException {
        scanPlanList = new ArrayList<ScanPlan>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = df.format(new Date());
        
        PreparedStatement pstmt = readConn.prepareStatement("select fid, faddress, ftype, fdatetime, fend, fendibm, Sys_id from TB_SCANPLAN where fdatetime <= ? and fendibm = 0 order by fdatetime");
        PreparedStatement uptmt;
        pstmt.setString(1, currentTime);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
        	ScanPlan plan = new ScanPlan();
        	int Id = rs.getInt(1);
        	plan.setId(Id);
        	plan.setWebSite(rs.getString(2));
        	plan.setType(rs.getInt(3));
        	plan.setDateTime(rs.getDate(4));
        	plan.setEnd(rs.getInt(5));
        	plan.setEndIbm(rs.getInt(6));
        	plan.setSysId(rs.getInt(7));
        	scanPlanList.add(plan);
        	uptmt = readConn.prepareStatement("update TB_SCANPLAN set fendibm = 1 where fid = ?");
        	uptmt.setInt(1, Id);
        	uptmt.executeUpdate();
        	uptmt.close();
        }
        rs.close();
        pstmt.close();
    }
    
    public void updateScanPlan(int fid, int status){
    	try{
    		if(prefetchConnectionURL != null){
	    		Connection conn = DriverManager.getConnection(prefetchConnectionURL);
	    		PreparedStatement pstmt = conn.prepareStatement("update TB_SCANPLAN set fendibm = ? where fid = ?");
	    		pstmt.setInt(1, status);
	            pstmt.setInt(2, fid);
	            pstmt.executeQuery();
	            pstmt.close();
    		}
    	} catch(SQLException ex){
    		logger.log(Level.SEVERE, "update db:", ex);
    	}
    }
	@Override
	public DatabaseSource getNewDatabaseSource() {
		try{
			if(prefetchConnectionURL != null){
				Connection preConn = DriverManager.getConnection(prefetchConnectionURL);
				logger.info("start db prefetch...");
				startPrefetch(preConn);
				logger.info("prefetch ok~");
			}
			Connection writeConn = DriverManager.getConnection(writeConnectionURL);
			logger.info("new connection~");
			SimpleDatabaseSource dbs = new SimpleDatabaseSource(writeConn);
			dbs.scanPlanList = this.scanPlanList;
			return dbs;
			
		} catch (SQLException ex) {
            logger.log(Level.SEVERE, "create database connection", ex);
        }
		return null;
	}
}
