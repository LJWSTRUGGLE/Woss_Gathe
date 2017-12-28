package com.briup.common;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.util.BackUP;
import com.briup.util.Logger;
import com.briup.woss.WossModule;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;
/**
 * 配置模块
 */
public class Configuration implements com.briup.util.Configuration{
	private static Properties properties;
	private static WossModule wossModule;
	private static Map<String, Object> map;
	static{
		
		map = new HashMap<String, Object>();
		
		 try {
			//创建解析器
			SAXReader reader=new SAXReader();
			Document document = reader.read("src/com/briup/file/conf.xml");
			//获取根节点
			Element root= document.getRootElement();
			//遍历root获取子节点
			List<Element> list = root.elements();
			for (Element ele1 : list) {
				String className = ele1.attributeValue("class");
				//标签名
				String name = ele1.getName();
				//通过反射获取类的实例对象
				wossModule = (WossModule)Class.forName(className).newInstance();
				properties = new Properties();
				//遍历二级子标签
				List<Element> ele2 = ele1.elements();
				for (Element element : ele2) {
					//获取标签名
					String name2 = element.getName();
					//获取文本节点内容
					String value2 = element.getText();
					properties.setProperty(name2, value2);
					
				}
				wossModule.init(properties);
				map.put(name, wossModule);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	public WossModule getModule(String name) throws Exception {
		
		WossModule wossModule = (WossModule) map.get(name);
		return wossModule;
		
	}
	
	 
	@Override
	public BackUP getBackup() throws Exception {
		BackUP module = (BackUP) getModule("bacpup");
		return module;
		
	}

	@Override
	public Client getClient() throws Exception {
		Client client = (Client) getModule("client");
		return client;
	}

	@Override
	public DBStore getDBStore() throws Exception {
		DBStore dbStore = (DBStore) getModule("dbstore");
		return dbStore;
	}

	@Override
	public Gather getGather() throws Exception {
		Gather gather = (Gather) getModule("gather");
		return gather;
	}

	@Override
	public Logger getLogger() throws Exception {
		Logger logger = (Logger) getModule("logger");
		return logger;
	}

	@Override
	public Server getServer() throws Exception {
		Server server = (Server) getModule("server");
		return server;
	}

}
