package com.briup.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Properties;




import com.briup.util.BIDR;
import com.briup.util.BackUP;
/**
 * 备份模块
 */
public class BackUpImpl implements BackUP{


	@Override
	public void init(Properties arg0) {
	}
	
	//读取
	@Override
	public Object load(String arg0, boolean arg1) throws Exception {
		if (arg1) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/BackUp"));
			Map<String, BIDR> map=(Map<String, BIDR>) ois.readObject();
			for (String key : map.keySet()) {
				if (key.equals(arg0)) {
					return map.get(key);
				}
			}
			ois.close();
		}
		
		
		
		
		return null;
		
	}
	
	//存储
	@Override
	public void store(String arg0, Object arg1, boolean arg2) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/BackUp"));
		
		oos.writeObject(arg1);
		oos.flush();
		System.out.println("备份完成");
		oos.close();
	}

}
