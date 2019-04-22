package com.edu.ms.common.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * controller抽象层，为json请求方式，非表单
 *
 * @author wst
 * @date 2019/1/2 20:25
 **/
@Slf4j
public class BaseController<D extends BaseService<T>, T> {

    @Autowired
    private D baseService;

    @ResponseBody
    @RequestMapping("save")
    public EduResult save(T req) {
        try {
            baseService.save(req);
            return EduResult.ok();
        } catch (Exception e) {
            log.error("[{}类保存数据] 失败 req={}", req.getClass(), req, e);
            return EduResult.error();
        }

    }

    @ResponseBody
    @RequestMapping("queryByCondition")
    public T queryByCondition(T req) {
        try {
            return baseService.queryByCondition(req);
        } catch (Exception e) {
            log.error("[{}类查询单个数据] 失败 req={}", req.getClass(), req, e);
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("listByCondition")
    public List<T> listByCondition(T req) {
        try {
            return baseService.listByCondition(req);
        } catch (Exception e) {
            log.error("[{}类查询数据列表] 失败 req={}", req.getClass(), req, e);
            return new ArrayList<>(0);
        }
    }

    @ResponseBody
    @RequestMapping("updateById")
    public EduResult updateById(T record) {
        try {
            baseService.updateById(record);
            return EduResult.ok();
        } catch (Exception e) {
            log.error("[{}类修改数据] 失败 req={}", record.getClass(), record, e);
            return EduResult.error();
        }
    }

    @ResponseBody
    @RequestMapping("deleteById")
    public EduResult deleteById(T record) {
        try {
            baseService.deleteById(record);
            return EduResult.ok();
        } catch (Exception e) {
            log.error("[{}类修改数据] 失败 req={}", record.getClass(), record, e);
            return EduResult.error();
        }
    }



    @ResponseBody
    @RequestMapping("/dataGrid")
    public DataGrid<T> dataGrid(T reqPO) {
        try {
            DataGrid<T> dataGrid = new DataGrid<>();
            dataGrid.setRows(baseService.findPage(reqPO));
            dataGrid.setTotal(baseService.countAll(reqPO));
            return dataGrid;
        } catch (Exception e) {
            log.info("[{}类分页查询数据] req={}", reqPO.getClass(), reqPO, e);
            return new DataGrid();
        }
    }
}
