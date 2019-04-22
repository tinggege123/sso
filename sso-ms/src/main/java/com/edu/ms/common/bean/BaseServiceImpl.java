package com.edu.ms.common.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 抽象service实现层
 *
 * @author wst
 * @date 2019/1/2 19:34
 **/
@Slf4j
public class BaseServiceImpl<D extends BaseDao<T>, T> implements BaseService<T> {

    @Autowired
    private D baseDao;

    /**
     * 保存数据
     *
     * @param record 保存参数PO
     * @return 影响行数
     */
    @Override
    public int save(T record) {
        log.info("[{}类保存数据] 开始 req={}", record.getClass(), record);
        int result = baseDao.save(record);
        log.info("[{}类保存数据] 结束 req={}  result={}", record.getClass(), record, result);
        return result;
    }

    /**
     * 根据参数查询信息
     *
     * @param record 查询参数PO
     * @return 返回参数
     */
    @Override
    public T queryByCondition(T record) {
        log.info("[{}类查询单个数据] 开始 req={}", record.getClass(), record);
        T result = baseDao.queryByCondition(record);
        log.info("[{}类查询单个数据] 结束 req={} result={}", record.getClass(), record, result);
        return result;
    }

    /**
     * 根据参数查询信息
     *
     * @param record 查询参数PO
     * @return 返回参数
     */
    @Override
    public List<T> listByCondition(T record) {
        log.info("[{}类查询数据列表] 开始 req={}", record.getClass(), record);
        List<T> result = baseDao.listByCondition(record);
        log.info("[{}类查询数据列表] 结束 req={} result={}", record.getClass(), record, result);
        return result;
    }

    /**
     * 根据id修改值
     *
     * @param record 修改参数
     * @return 影响行数
     */
    @Override
    public int updateById(T record) {
        log.info("[{}类修改数据] 开始 req={}", record.getClass(), record);
        int result = baseDao.updateById(record);
        log.info("[{}类修改数据] 结束 req={} result={}", record.getClass(), record, result);
        return result;
    }

    /**
     * 分页查询参数
     *
     * @param reqPO 查询参数PO
     * @return 返回参数
     */
    @Override
    public List<T> findPage(T reqPO) {
        log.info("[{}类分页查询数据] 开始 req={}", reqPO.getClass(), reqPO);
        List<T> resultList = baseDao.findPage(reqPO);
        log.info("[{}类分页查询数据] 结束 req={} resp={}", reqPO.getClass(), reqPO, resultList);
        return resultList;
    }

    /**
     * 计算总数
     *
     * @param reqPO 查询参数PO
     * @return 返回参数
     */
    @Override
    public Integer countAll(T reqPO) {
        log.info("[{}类查询数据总数] 开始 req={}", reqPO.getClass(), reqPO);
        Integer result = baseDao.countAll(reqPO);
        log.info("[{}类查询数据总数] 结束 req={} resp={}", reqPO.getClass(), reqPO, result);
        return result;
    }

    /**
     * 删除数据
     *
     * @param reqPO
     * @return
     */
    @Override
    public Integer deleteById(T reqPO) {
        log.info("[{}类删除数据] 开始 req={}", reqPO.getClass(), reqPO);
        Integer result = baseDao.deleteById(reqPO);
        log.info("[{}类删除数据]结束 req={} resp={}", reqPO.getClass(), reqPO, result);
        return result;
    }
}
