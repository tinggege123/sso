package com.edu.utils;

import org.apache.commons.lang3.StringUtils;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * sso的工具类
 *
 * @author 王生挺
 * @date 2018年7月21日23:35:23
 */
public class SsoUtil {
	public static Map<String,Object> getDataFromRequest(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> enu = request.getParameterNames();  
		while(enu.hasMoreElements()){  
			String paraName=(String)enu.nextElement(); 
			System.out.println(paraName);
			map.put(paraName,request.getParameter(paraName));
			System.out.println(paraName+": "+request.getParameter(paraName));  
		}
	    return map;
	}
	/**
	 * 返回一个该项目的ip加端口
	 * @return
	 */
	public static String getIpAddressAndPort() {
		String ipadd = "";
		try{
			MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			String host = InetAddress.getLocalHost().getHostAddress();
			ipadd = "http" + "://" + host;

		}catch (Exception e){
			e.printStackTrace();
		}
		ipadd = StringUtils.isBlank(ipadd)?"http://127.0.0.1":ipadd;
		return ipadd;
	}

}
