package cn.forest.service;

import cn.forest.system.entity.SysDictionaryData;
import cn.forest.system.entity.SysDictionaryType;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDictionaryDataService extends IService<SysDictionaryData> {

    /**
     * 判断 code 或 name 是否存在
     *
     * @param sysDictionaryData
     * @return
     */
    boolean vaRepeat(SysDictionaryData sysDictionaryData);

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    int delete(Long id);
}
