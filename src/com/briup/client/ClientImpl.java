package com.briup.client;


import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.client.Client;

/**
 * AAA服务器客户端
 */
public class ClientImpl implements Client{
	private static String ip;
	private static int port;
	

	@Override
	public void init(Properties arg0) {
		ip = "127.0.0.1";
		port = 9898;
	}
	
	/*
	 *向服务器发送采集好的数据
	 */
	@Override
	public void send(Collection<BIDR> arg0) throws Exception {
		//ip = "127.0.0.1";
		//port = 9898;
		Socket socket = new Socket(ip,port);
		
		OutputStream os = socket.getOutputStream();
		
		//因为发送的是list集合，所以用对象流
		ObjectOutputStream oos = new ObjectOutputStream(os);
		
		oos.writeObject(arg0);
		oos.flush();
		if(socket!=null) socket.close();
		if(oos!=null) oos.close();
		System.out.println("客户端已经发送成功");
		
	}
}

