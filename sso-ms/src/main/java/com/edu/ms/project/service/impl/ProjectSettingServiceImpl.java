package com.edu.ms.project.service.impl;

import com.edu.ms.common.bean.BaseServiceImpl;
import com.edu.ms.project.dao.ProjectSettingDao;
import com.edu.ms.project.po.ProjectSettingPO;
import com.edu.ms.project.service.ProjectSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 子项目配置service实现层
 *
 * @author wst
 * @date 2019/1/5 11:41
 **/
@Slf4j
@Service
public class ProjectSettingServiceImpl extends BaseServiceImpl<ProjectSettingDao, ProjectSettingPO> implements ProjectSettingService {
}
