package com.edu.business.persistence.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBO implements Serializable {

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


}
