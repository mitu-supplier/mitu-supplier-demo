package cn.forest.system.mapper;

import cn.forest.system.entity.SysDictionaryData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryDataMapper extends BaseMapper<SysDictionaryData> {

    /**
     * 根据类型code查询字典数据
     *
     * @param code
     * @return
     */
    List<SysDictionaryData> selectByDateTypeCode(@Param("code") String code);
}
