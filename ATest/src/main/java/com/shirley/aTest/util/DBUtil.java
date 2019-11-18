package com.shirley.aTest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	/**
	 * 得到数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(String driver, String url, String user, String password) throws Exception {
		// 加载运行时类对象
		Class.forName(driver);
		// 通过DriverManager得到连接
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	/**
	 * 释放资源的方法
	 * 
	 * @param connection
	 * @param statement
	 * @param resultSet
	 * @throws SQLException
	 */
	public void release(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 *            字符串，要执行的sql语句 如果其中有变量的话，就用 ‘"+变量+"’
	 * @throws Exception
	 * 
	 */
	public String query(String driver, String url, String user, String password, String sql) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String result = "";
		connection = getConnection(driver, url, user, password);
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.next() != false) {
			result = resultSet.getObject(1).toString();
		}
		release(connection, preparedStatement, resultSet);
		return result + "";
	}

	/**
	 * 数据库记录增删改的方法
	 * 
	 * @param sql
	 *            字符串，要执行的sql语句 如果其中有变量的话，就用 ‘"+变量+"’
	 * @throws Exception
	 */
	public String CUD(String driver, String url, String user, String password, String sql) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		connection = getConnection(driver, url, user, password);
		preparedStatement = connection.prepareStatement(sql);
		result = preparedStatement.executeUpdate();
		release(connection, preparedStatement, null);
		return result + "";
	}
}
