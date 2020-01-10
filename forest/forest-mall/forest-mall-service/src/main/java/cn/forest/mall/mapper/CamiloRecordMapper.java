package cn.forest.mall.mapper;

import cn.forest.mall.entity.CamiloRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface CamiloRecordMapper extends BaseMapper<CamiloRecord> {

    /**
     * 卡密导入记录查询
     *
     * @param map
     * @return
     */
    List<CamiloRecord> recordlist(Map<String, Object> map);
}
