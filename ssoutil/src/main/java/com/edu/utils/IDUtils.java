package com.edu.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 各种id生成策略
 * <p>Title: IDUtils</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年7月22日下午2:32:10
 * @version 1.0
 */
public class IDUtils {

	/**
	 * 图片名生成
	 */
	public static String getImageName() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足三位前面补0
		String str = millis + String.format("%03d", end3);
		
		return str;
	}
	
	
	/**
	 * <p>Title: getLongId</p>
	 * <p>Description: 生成16位的id</p>
	 * @return
	 */
	public static Long getLongId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		
		//long millis = System.nanoTime();
		//加上3位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足3位前面补0
		String str = millis + String.format("%03d", end3);
		return new Long(str);
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 31);
	}
	public static int getShortID(){
		int max=9999999;
        int min=1000000;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	public static int getCode(){
		int max=999999;
        int min=100000;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	
	
}
