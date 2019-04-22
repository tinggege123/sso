package com.edu.sso.permission.service.impl;

import com.edu.sso.permission.service.RemissionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author wst
 * @date 2018/12/15 17:50
 **/
@Slf4j
@Service
public class RemissionsServiceTestImpl implements RemissionsService {

    /**
     * 获取权限参数
     *
     * @return
     */
    @Override
    public List<String> requestPermissions() {
        List<String> resultList = new ArrayList<>();
        resultList.add("view");
        return resultList;
    }
}
