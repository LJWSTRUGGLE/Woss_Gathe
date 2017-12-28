package com.briup.main;

import java.util.Collection;

import com.briup.common.Configuration;
import com.briup.util.BIDR;

/**
 * 客户端
 */
public class ClientMain{
	public static void main(String[] args) throws Exception {
		System.out.println("这是客户端。。。");
		Configuration configuration = new Configuration();
		Collection<BIDR> list = configuration.getGather().gather();
		System.out.println(list.size());
		configuration.getClient().send(list);
		//Collection<BIDR> list = new GatherImpl().gather();
		//new ClientImpl().send(list);
		
	
	}
}
