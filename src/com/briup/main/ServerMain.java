package com.briup.main;

import java.util.Collection;

import com.briup.common.Configuration;
import com.briup.util.BIDR;

/**
 * 服务器端
 */
public class ServerMain {
	public static void main(String[] args) throws Exception {
		System.out.println("这是服务器：  ");
		Configuration configuration = new Configuration();
		Collection<BIDR> list = configuration.getServer().revicer();
		configuration.getDBStore().saveToDB(list);
		//Collection<BIDR> list = new ServerImpl().revicer();
		//new DBStoreImpl().saveToDB(list);
	}
}
