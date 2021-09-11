package com.ma.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;



public class Dbservice {
	static Connection CONN;

	public void getConnection() throws IOException {
		if (CONN == null) {
			Properties props = new Properties();// 创建�?个properties
			InputStream in =  Dbservice.class.getResourceAsStream("database.properties");
			props.load(in); // 把文件database.properties的内容读入对象props 
			in.close();
			String drivers = props.getProperty("jdbc.drivers");

			if (drivers != null) {
				try {
					Class.forName(drivers).newInstance();
					String url = props.getProperty("jdbc.url");
					String userName = "AUTEK";
					String password = "FLYVIDEO";
					CONN = DriverManager.getConnection(url, userName, password);

					System.out.println("数据库连接成�?");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("数据库连接失�?");
				}
			} else {
				System.out.println("数据库驱动不存在");
			}
		}
	}
	

	
	
	public ArrayList<String> getResult(String accno) throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		Statement smt = CONN.createStatement();
		ResultSet rs = smt
				.executeQuery("SELECT ACCUSER,ACCNAME,ACCNO,PointNo FROM Zl WHERE (ACCNO = '" + accno + "')");
		while (rs.next()) {
			list.add(rs.getString("ACCUSER"));
			list.add(rs.getString("ACCNAME"));
			list.add(rs.getString("ACCNO"));
			list.add(rs.getString("PointNo"));
		}
		smt.close();
		rs.close();
		return list;
	}
	
	public int updateNra(String accno) throws SQLException{
		if(accno.startsWith("NRA")) {
			return 1;
		}		
		Statement smt =   CONN.createStatement();
		int count = smt.executeUpdate("update Zl set accno = 'NRA"+ accno  +"' WHERE (ACCNO = '"+ accno +"')");
		smt.close();
		return count;
	}
	
	public int updatebak(String accno) throws SQLException{
		String args = accno.replace("NRA", "");
		Statement smt =   CONN.createStatement();
		int count = smt.executeUpdate("update Zl set accno = '"+ args  +"' WHERE (ACCNO = '"+ accno +"')");
		smt.close();
		return count;
	}
}
