package com.edu.sso.service;

import com.edu.utils.EduResult;

import java.util.Map;

/**
 * 请求单点登录中心
 *
 * @author wst
 * @date 2018/9/28 15:27
 **/
public interface SsoHttpToServerService {

    EduResult getStatusByToken(String url, Map map);
}
