package com.way2automation.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbManager 
{
	
	public static Connection con = null;
	
	//mySql
	
	public static void setDbConnection() throws ClassNotFoundException, SQLException
	{
		try {
		Class.forName(TestConfig.mysqldriver);//Returns the Class object associated with the class.
		con = DriverManager.getConnection(TestConfig.mysqlurl,TestConfig.mysqluserName,TestConfig.mysqlpassword);//Attempts to establish a connection to the given database URL.
		
		if(!con.isClosed())
			System.out.println("Sucessfully connected to MySql");
		}catch(Exception e)
		{
			System.err.println("Exception: " + e.getMessage());
		}
	}
	
	public static List<String> getMysqlQuery(String query) throws SQLException
	{
		Statement st = con.createStatement();//Creates a Statement object for sendingSQL statements to the database.
		ResultSet rs = st.executeQuery(query);
		List<String> values1 = new ArrayList<String>();
		while(rs.next())
		{
			values1.add(rs.getString(1));
		}
		
		return values1;
	}
}
