package com.edu.sso.permission.aop;

import com.edu.sso.permission.annotion.RequiresPermissions;
import com.edu.sso.permission.service.RemissionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 自定义权限校验AOP
 *
 * @author wst
 * @date 2018/12/15 16:06
 **/
@Slf4j
@Aspect
@Component
public class PermissionsAop extends AbstractAOP {

    @Resource
    private List<RemissionsService> remissionsServices;

    @Pointcut("@annotation(com.edu.sso.permission.annotion.RequiresPermissions)")
    public void validatePermission() {
    }

    private final static String ERROR_MSG = "权限不足";

    @Around("validatePermission()")
    public Object validateData(ProceedingJoinPoint joinPoint) throws Throwable {

        //自定义权限验证
        Boolean validateStatus = this.validateDataPermission(joinPoint);

        //权限验证不通过，直接返回
        if (!validateStatus) {
            return ERROR_MSG;
        }

        //进入主流程
        Object resultProcess = joinPoint.proceed(joinPoint.getArgs());

        return resultProcess;
    }

    /**
     * 自定义权限验证,true->通过，false->返回
     *
     * @param joinPoint
     */
    private Boolean validateDataPermission(ProceedingJoinPoint joinPoint) {
        try {
            Method sourceMethod = super.getSourceMethod(joinPoint);
            RequiresPermissions requiresPermissions = sourceMethod.getAnnotation(RequiresPermissions.class);
            String url = requiresPermissions.url();
            //数据防守，为空过不了校验
            if (StringUtils.isBlank(url)) {
                log.warn("[自定义权限验证] 传入参数 url为空");
                return Boolean.TRUE;
            }
            //去掉斜杆
            url = this.removeDiagonal(url);

            //注入多个bean，遍历所有的bean，找不到权限->权限不通过
            for (RemissionsService remissionsService : remissionsServices) {
                List<String> remissionList = remissionsService.requestPermissions();
                for (String remission : remissionList) {
                    String reallyRemission = this.removeDiagonal(remission);
                    if (reallyRemission.contains("*")) {
                        return Boolean.TRUE;
                    }
                    if (reallyRemission.equals(url)) {
                        return Boolean.TRUE;
                    }
                }
            }
        } catch (Exception e) {
            log.error("[自定义权限验证] 异常", e);
        }
        return Boolean.FALSE;
    }

    /**
     * 去除斜杆
     *
     * @param str
     * @return
     */
    private String removeDiagonal(String str) {
        if (StringUtils.isBlank(str)) {
            return StringUtils.EMPTY;
        }
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        return str;
    }
}
