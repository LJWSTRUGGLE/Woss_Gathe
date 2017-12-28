package com.briup.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.woss.server.Server;
/**
 * 中央服务器
 */
public class ServerImpl implements Server{
	private static ServerSocket ss = null;
	private int port;
	@Override
	public void init(Properties arg0) {
		port = 9898;
	}

	@Override
	public Collection<BIDR> revicer() throws Exception {
		//接收客户端传来的数据
		//port = 9898;
		 ss = new ServerSocket(port);
		//等待发送过来的请求
		Socket socket = ss.accept();
		InputStream is=socket.getInputStream();
		ObjectInputStream ois=new ObjectInputStream(is);
		Collection<BIDR> list = (Collection<BIDR>) ois.readObject();
		shutdown();
		System.out.println("服务器端已经接收完数据");
		return list;
	}

	@Override
	public void shutdown() {
		
			try {
				if (ss!=null) ss.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
	}

}
