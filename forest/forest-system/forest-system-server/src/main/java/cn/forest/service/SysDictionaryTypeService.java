package cn.forest.service;

import cn.forest.system.entity.SysDictionaryType;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDictionaryTypeService extends IService<SysDictionaryType> {

    /**
     * 判断 code 或 name 是否存在
     *
     * @param sysDictionaryType
     * @return
     */
    boolean vaRepeat(SysDictionaryType sysDictionaryType);
}
