package com.briup.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.briup.common.BackUpImpl;
import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.woss.client.Gather;

/**
 * 完成数据采集，封装成一个装有BIDR对象的集合
 */
public class GatherImpl implements Gather {

	private String path;
	
	@Override
	public void init(Properties arg0) {
		path="src/com/briup/file/radwtmp";

	}

	/*
	 * 采集
	 */

	public Collection<BIDR> gather() throws Exception {
		// 读入数据文件
		FileReader reader = new FileReader(path);
		
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		Map<String, BIDR> map = new HashMap<String, BIDR>();
		List<BIDR> list = new ArrayList<BIDR>();
		// 遍历文件内容
		while ((line = br.readLine()) != null) {
			String[] arr = line.split("\\|");
			String user = arr[0].substring(1);// 用户名
			String NAS_ip = arr[1];// NAS服务器ip
			String flag = arr[2];// 登录标志，7,8
			String time = arr[3];// 时间
			String login_ip = arr[4];// 用户登录ip
			// 将数据保存进bidr对象
			// 通过flag标识，判断是上线还是下线
			if (flag.equals("7")) {
				BIDR bidr = new BIDR();
				bidr.setAAA_login_name(user);
				bidr.setNAS_ip(NAS_ip);
				Timestamp time_login = new Timestamp(Long.parseLong(time));
				bidr.setLogin_date(time_login);
				bidr.setLogin_ip(login_ip);

				if (!map.containsKey(login_ip)) {
					map.put(login_ip, bidr);
				}

			} else if (flag.equals("8")) {
				BIDR bidr = map.get(login_ip);
				Timestamp login_date = bidr.getLogin_date();// 获取上线时间
				Timestamp time_logOut = new Timestamp(Long.parseLong(time));// 获取下线时间
				int time_deration = (int) (time_logOut.getTime() - login_date
						.getTime());// 获取上线时长

				bidr.setTime_deration(time_deration);// 上线时长
				list.add(bidr);
				map.remove(login_ip);

			}

		}
		 if(br!=null) br.close();
		/*
		 * for (BIDR bidr : list) { System.out.println(bidr); }
		 * System.out.println(list.size());
		 */
		System.out.println("客户端采集完成"+list.size());
		new BackUpImpl().store("第一次备份", map, true);
		new LoggerImpl().warn("ok");
		return list;
	}
	/*
	 * public static void main(String[] args) { GatherImpl ga = new
	 * GatherImpl(); try { ga.gather(); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 */
}
