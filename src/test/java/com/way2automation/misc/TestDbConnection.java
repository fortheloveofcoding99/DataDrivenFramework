package com.way2automation.misc;

import java.sql.SQLException;

import com.way2automation.utilities.DbManager;

public class TestDbConnection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		DbManager.setDbConnection();
		System.out.println(DbManager.getMysqlQuery("select * from selenium;"));

	}

}
