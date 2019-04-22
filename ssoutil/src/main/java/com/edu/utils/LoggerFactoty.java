package com.edu.utils;

import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * 日志系统
 *
 * @author 王生挺
 * @date 2018年7月22日11:08:48
 */
public class LoggerFactoty {
	public static Logger getLogger(Class<?> clazz){
		return Logger.getLogger(clazz);
	}
	
	public static void DEBUG(Logger logger,String methodName,String massage,String... params){
		logger.debug("(MethodName: "+methodName+")"+"{"+massage+"}"+"{param:" + Arrays.toString(params) + "}");
	}
	
	public static void DEBUG(Logger logger, String url, int code, String result){
		logger.debug("(UTL: "+url+")"+"{状态码："+code+"}"+"{结果:" + result + "}");
	}
	
	public static void INFO(Logger logger,String methodName,String massage,String... params){
		logger.info("{"+massage+"}"+"{param:" + Arrays.toString(params) + "}");
	}
	
	//方法执行耗时日志
	public static void TRACE(Logger logger,String methodName,String massage,Long usedTime){
		logger.debug("(MethodName: "+methodName+")"+"{"+massage+"}"+"[usertime:" + usedTime + "ms]");
	}
	
	public static void ERROR(Logger logger,String methodName,String massage,Throwable t){
		logger.error("(MethodName: "+methodName+")"+"{"+massage+"}",t);
	}
	
	public static void ERROR(Logger logger,String methodName,String massage){
		logger.error("(MethodName: "+methodName+")"+"{"+massage+"}");
	}
}
