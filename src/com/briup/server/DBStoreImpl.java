package com.briup.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.server.DBStore;

/**
 * 执行入库操作
 */
public class DBStoreImpl implements DBStore {

	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	

	@Override
	public void init(Properties arg0) {
		driver = "oracle.jdbc.driver.OracleDriver";
		url = "jdbc:oracle:thin:@127.0.0.1:1521:XE ";
		username = "ljw";
		password = "ljw";
	}

	/*
	 * 入库
	 */
	@Override
	public void saveToDB(Collection<BIDR> arg0) throws Exception {
		/*driver = "oracle.jdbc.driver.OracleDriver";
		url = "jdbc:oracle:thin:@127.0.0.1:1521:XE ";
		username = "ljw";
		password = "ljw";*/
		//注册驱动
		Class.forName(driver);
		//获取连接对象
		Connection connection = DriverManager.getConnection(url, username,
				password);
		//设置手动提交
		connection.setAutoCommit(false);
		List<BIDR> list = (List<BIDR>) arg0;
		//为了防止ps对象出现最大游标溢出问题，需要将ps对象实时关闭
		for (int i = 0; i < list.size(); i++) {
			BIDR bidr = list.get(i);
			// 获取日期是当前月份的某一天
			int day = bidr.getLogin_date().getDate();
			String sql = "insert into t_detail_" + day + " values(?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, bidr.getAAA_login_name());
			ps.setString(2, bidr.getLogin_ip());
			ps.setTimestamp(3, bidr.getLogin_date());
			ps.setTimestamp(4, bidr.getLogout_date());
			ps.setString(5, bidr.getNAS_ip());
			ps.setInt(6, bidr.getTime_deration() / 1000 / 60);
			// 执行 sql 语句
			ps.execute();
			// 提交数据
			connection.commit();
			// 关闭资源
			ps.close();

		}
		if(connection!=null) connection.close();
		System.out.println("数据已经入库完成");
	}

}
