package cn.forest.mall.mapper;

import cn.forest.mall.entity.ProductsUpdate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductsUpdateMapper extends BaseMapper<ProductsUpdate> {

    List<ProductsUpdate> selectListByMap(Map<String, Object> map);

    int batchAudit(@Param("idList") List<Long> idList, @Param("auditStatus") Integer auditStatus);
}
