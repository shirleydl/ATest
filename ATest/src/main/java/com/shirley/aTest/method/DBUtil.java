package com.shirley.aTest.method;

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
	public Connection getConnection(String driver,String url,String user,String password) throws Exception {
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
	 */
	public void release(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 *            字符串，要执行的sql语句 如果其中有变量的话，就用 ‘"+变量+"’
	 */
	public String query(String driver,String url,String user,String password,String sql) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(driver, url, user, password);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next() != false) {
				return resultSet.getObject(1).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(connection, preparedStatement, resultSet);
		}
		return "";
	}

	/**
	 * 数据库记录增删改的方法
	 * 
	 * @param sql
	 *            字符串，要执行的sql语句 如果其中有变量的话，就用 ‘"+变量+"’
	 */
	public String CUD(String driver,String url,String user,String password,String sql) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(driver, url, user, password);
			preparedStatement = connection.prepareStatement(sql);
			 int result=preparedStatement.executeUpdate();
			 return result+"";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			release(connection, preparedStatement, null);
		}
		return "";
	}
}
