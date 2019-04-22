package com.edu.business.persistence.dao;

import com.edu.business.persistence.po.UserTokenPO;

import java.util.List;

/**
 * t_user_token表对应实体
 * 
 * @author shengting_wang
 */
public interface UserTokenDao {

    /**
     * @param record
     */
    int save(UserTokenPO record);

    /**
     * @param record
     */
    UserTokenPO queryByCondition(UserTokenPO record);

    /**
     * @param record
     */
    List<UserTokenPO> listByCondition(UserTokenPO record);

    /**
     * @param record
     */
    int updateById(UserTokenPO record);

    /**
     * 删除数据
     * @param userTokenPO
     * @return
     */
    int delete(UserTokenPO userTokenPO);

}