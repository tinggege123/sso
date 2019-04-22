package com.edu.sso.service.impl;

import com.edu.sso.service.SsoHttpToServerService;
import com.edu.utils.EduResult;
import com.edu.utils.HttpClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 请求单点登录server
 *
 * @author wst
 * @date 2018/9/28 15:28
 **/
@Service
@Slf4j
public class SsoHttpToServerServiceImpl implements SsoHttpToServerService {

    @Override
    public EduResult getStatusByToken(String url, Map map) {
        log.info("[获取token状态]开始 url={}",url);
        HttpClientUtil instance = HttpClientUtil.getInstance();
        String resultStr = instance.sendHttpPost(url, map);
        ObjectMapper objectMapper = new ObjectMapper();
        EduResult result = null;
        try {
            result = objectMapper.readValue(resultStr, EduResult.class);
        } catch (IOException e) {
            log.info("[获取token状态]失败 err={}",e);
        }
        return result;
    }
}
