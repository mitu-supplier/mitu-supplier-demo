package cn.forest.mall.mapper;

import cn.forest.mall.entity.SuppliersUpdate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SuppliersUpdateMapper extends BaseMapper<SuppliersUpdate> {

    /**
     * 查询变更记录
     *
     * @param map
     * @return
     */
    List<SuppliersUpdate> selectUpdateRecodeList(Map<String, Object> map);

    int batchAudit(@Param("idList") List<Long> idList, @Param("status") Integer status);
}
