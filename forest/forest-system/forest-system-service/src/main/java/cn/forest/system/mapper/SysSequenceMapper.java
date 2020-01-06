package cn.forest.system.mapper;

import cn.forest.system.entity.SysSequence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysSequenceMapper extends BaseMapper<SysSequence> {

    /**
     * 根据名称获取Seq值
     *
     * @param name
     * @return
     */
    String getSeqValue(String name);

    /**
     * 查询名称数量
     *
     * @param name
     * @return
     */
    int countByName(String name);
}
