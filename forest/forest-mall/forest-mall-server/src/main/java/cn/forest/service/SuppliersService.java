package cn.forest.service;

import cn.forest.mall.entity.Suppliers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SuppliersService extends IService<Suppliers> {

    /**
     * 条件分页查询
     *
     * @param map
     * @return
     */
    Object list(Map<String, Object> map);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改供应商状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);
}
