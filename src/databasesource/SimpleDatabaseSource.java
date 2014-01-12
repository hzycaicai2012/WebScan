/**
 * Project Name:WebScan
 * File Name:SimpleDatabaseSource.java
 * Package Name:databasesource
 * Date:2014年1月10日上午12:49:25
 * Copyright (c) 2014, hzycaicai@163.com All Rights Reserved.
 *
*/

package databasesource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataobject.CheckResult;
import dataobject.ScanPlan;

/**
 * ClassName:SimpleDatabaseSource
 *
 * @author   hzycaicai
 * @version  	 
 */
public class SimpleDatabaseSource extends DatabaseSource {
	private static Logger logger = Logger.getLogger(SimpleDatabaseSource.class.getName());
	protected ArrayList<ScanPlan> scanPlanList = null;
	protected Connection writeConn;
	
	protected PreparedStatement writeCheckResPstmt = null;
	protected PreparedStatement updateScanPlanPstmt = null;

    protected static final int maxBatch = 30000;
    protected int batchCount = 0;
	
	public SimpleDatabaseSource(Connection conn){
		this.writeConn = conn;
	}
	
	/*
	 * @see databasesource.DatabaseSource#getScanPlanList()
	 * return the scanPlanList
	 */
	@Override
	public ArrayList<ScanPlan> getScanPlanList() {
		return this.scanPlanList;
	}

	/*
	 * @see databasesource.DatabaseSource#writeCheckRes(dataobject.CheckResult)
	 * write back the check result
	 * for test, just write back right now
	 * for use, need add each statement to the batch and commit together
	 */
	@Override
	public void writeCheckRes(CheckResult res) {
		try{
			if(writeCheckResPstmt == null){
				this.writeCheckResPstmt = writeConn.prepareStatement("insert into TB_CHECKRESULT"
						+ " (SCANPLAN_ID, FName, FsecurityRisk, Fcause, Ftext, FtestDescription, FWASC, FCVE, FCWE, FfixRecommendation, FSeverity, SYS_ID)"
						+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			}
			writeCheckResPstmt.setInt(1, res.getScanPlanId());
			writeCheckResPstmt.setString(2, res.getName());
			writeCheckResPstmt.setString(3, res.getRisk());
			writeCheckResPstmt.setString(4, res.getCause());
			writeCheckResPstmt.setString(5, res.getText());
			writeCheckResPstmt.setString(6, res.getDescription());
			writeCheckResPstmt.setString(7, res.getWASC());
			writeCheckResPstmt.setString(8, res.getCVE());
			writeCheckResPstmt.setString(9, res.getCWE());
			writeCheckResPstmt.setString(10, res.getRecommend());
			writeCheckResPstmt.setString(11, res.getSeverity());
			writeCheckResPstmt.setInt(12, res.getSysId());
			writeCheckResPstmt.execute();
			//writeConn.commit();
			//writeCheckResPstmt.addBatch();
           // batchCount++;
            //if (batchCount >= maxBatch) {
             //   commit();
            //}
		} catch(SQLException ex) {
            logger.log(Level.SEVERE, "write check result:", ex);
        }
	}

	@Override
	public void beginTransaction() {
		try {
            writeConn.setAutoCommit(false);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "begin transaction write connection", ex);
        }
	}

	@Override
	public void commit() {
		try{
			updateBatch();
			writeConn.commit();
		} catch (SQLException ex) {
            logger.log(Level.SEVERE, "commit write connection", ex);
        }

	}

	@Override
	public void rollback() {
		try {
            writeConn.rollback();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "rollback write connection", ex);
        }
	}

	@Override
	public void shutdown() {
		try{
			commit();
			if(writeCheckResPstmt != null){
				writeCheckResPstmt.close();
			}
			writeConn.close();
		} catch(SQLException ex){
			logger.log(Level.SEVERE, "close write connection:", ex);
		}

	}

	protected void updateBatch() throws SQLException{
		if(writeCheckResPstmt != null){
			writeCheckResPstmt.executeBatch();
		}
		batchCount = 0;
	}

	/*
	 * @see databasesource.DatabaseSource#getScannedList()
	 * get the distinct id, which indicate the items has finished
	 * this method is for the client.reset()
	 */
	@Override
	public ArrayList<Integer> getScannedList() {
		try{
			ArrayList<Integer> list = new ArrayList<Integer>();
			PreparedStatement pstmt = writeConn.prepareStatement("select distinct SCANPLAN_ID from tb_checkresult");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				list.add(rs.getInt(1));
			}
	        rs.close();
	        pstmt.close();
	        return list;
		} catch(SQLException ex) {
            logger.log(Level.SEVERE, "write check result:", ex);
        }
		return null;
	}
}
