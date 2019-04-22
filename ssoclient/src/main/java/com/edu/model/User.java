package com.edu.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 主键id
	 */
	private Integer id;
	/*
	 * 用户账号（手机号码）
	 */
	private String username;
	/*
	 * 用户昵称
	 */
	private String realname;
	/*
	 * 密码
	 */
	private String password;
	/*
	 * 用户是否可用
	 */
	private String is_use;
	/*
	 * 注册日期
	 */
	private Date login_date;
	
	/*
	 * 加密的salt
	 */
	private String salt;
	/*
	 * 移动端登录的令牌
	 */
	private String token;
	
	private String school_uid;

	//经度
	private double longitude;
	//纬度
	private double latitude;
	//图片的url
	private String picture_url;
	//用户的性别
	private String sex;
	//用户的年龄
	private int age;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIs_use() {
		return is_use;
	}

	public void setIs_use(String is_use) {
		this.is_use = is_use;
	}


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSchool_uid() {
		return school_uid;
	}

	public void setSchool_uid(String school_uid) {
		this.school_uid = school_uid;
	}

	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPicture_url() {
		return picture_url;
	}

	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
