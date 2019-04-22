package com.edu.sso.permission.service;

import java.util.List;

/**
 * 权限接口类
 *
 * @author wst
 * @date 2018/12/15 17:28
 **/
public interface RemissionsService {

    /**
     * 获取权限参数
     * @return
     */
    List<String> requestPermissions();
}
