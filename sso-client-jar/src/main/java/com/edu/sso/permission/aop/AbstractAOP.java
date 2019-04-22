package com.edu.sso.permission.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AOP抽象类
 *
 * @author wst
 * @date 2018/12/28 15:28
 **/
public class AbstractAOP {

    /**
     * 根据ProceedingJoinPoint获取到当前注解的方法的Method
     */
    protected Method getSourceMethod(JoinPoint joinPoint) throws NoSuchMethodException {
        Method proxyMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return joinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
    }

    /**
     * 获取被切方法的返回值类型
     */
    protected Class getProceedResultClass(JoinPoint joinPoint) throws ClassNotFoundException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getReturnType();
    }

    /**
     * 获取方法名
     */
    protected String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    /**
     * 获取接口名
     */
    protected String getInterfaceName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    /**
     * 获取参数方法名
     */
    protected String getParamName(JoinPoint joinPoint) {
        return joinPoint.getArgs()[0].getClass().getName();
    }

    /**
     * 获取当前的bean的名称
     */
    protected String getBeanName(JoinPoint joinPoint) throws NoSuchMethodException {
        Class<?> declaringClass = this.getSourceMethod(joinPoint).getDeclaringClass();
        if (declaringClass.getAnnotation(Service.class) != null) {
            return declaringClass.getAnnotation(Service.class).value();
        }
        if (declaringClass.getAnnotation(Component.class) != null) {
            return declaringClass.getAnnotation(Component.class).value();
        }
        return null;
    }

    /**
     * 获取field成员对象
     *
     * @param object    主对象
     * @param fieldName 成员对象名称
     */
    protected Object getFieldObject(Object object, String fieldName) {
        Class clazz = object.getClass();
        // 获取响应的属性值
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field field;
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                continue;
            }
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                return null;
            }
            if (field.isAccessible()) {
                field.setAccessible(false);
            }
            return value;
        }
        return null;
    }

    protected Object getFieldsObject(Object object, String fieldSet) {
        Object actualResult = object;
        String[] fields = fieldSet.split("\\.");
        for (int i = 1; i < fields.length && actualResult != null; i++) {
            // 过滤i = 0的情况，此时为result，为约定值
            // 遍历获取值，获取到最后一个值，此时为实际表达式的最终值
            actualResult = this.getFieldObject(actualResult, fields[i]);
        }
        return actualResult;
    }
}
