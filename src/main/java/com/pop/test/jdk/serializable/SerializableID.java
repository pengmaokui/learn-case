package com.pop.test.jdk.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * Created by pengmaokui on 2017/11/22.
 */
public class SerializableID {

	/**
	 * 不一样的序列化id会反序列化报错
	 */
	@Test
	public void writeObject() {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setName("name");

		try{

			FileOutputStream fout = new FileOutputStream("/Users/pengmaokui/request.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(requestDTO);
			oos.close();
			System.out.println("WriteDone");

		}catch(Exception ex){
			ex.printStackTrace();
		}


	}

	/**
	 * 修改serialId 后执行
	 */
	@Test
	public void readObject() {
		RequestDTO requestDTO;
		try{

			FileInputStream fin = new FileInputStream("/Users/pengmaokui/request.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			requestDTO = (RequestDTO) ois.readObject();
			ois.close();

			System.out.println(requestDTO.getName());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 父类没有继承Serializable接口 数据不会被序列化
	 * 同时反序列化时会执行父类的无参构造函数，没定义就是jdk生成的无参构造函数
	 */
	@Test
	public void SerialObject() {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setParentName("parentName");
		requestDTO.setName("name");

		try{

			FileOutputStream fout = new FileOutputStream("/Users/pengmaokui/request.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(requestDTO);
			oos.close();
			System.out.println("WriteDone");

			FileInputStream fin = new FileInputStream("/Users/pengmaokui/request.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			requestDTO = (RequestDTO) ois.readObject();
			ois.close();

			System.out.println(requestDTO.getParentName());
		}catch(Exception ex){
			ex.printStackTrace();
		}


	}
}
