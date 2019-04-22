package com.edu.ms.user.service.impl;

import com.edu.ms.common.bean.BaseServiceImpl;
import com.edu.ms.user.dao.SysUserDao;
import com.edu.ms.user.po.SysUserPO;
import com.edu.ms.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统用户实现层
 *
 * @author wst
 * @date 2018/12/26 21:06
 **/
@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUserPO> implements SysUserService {
}
