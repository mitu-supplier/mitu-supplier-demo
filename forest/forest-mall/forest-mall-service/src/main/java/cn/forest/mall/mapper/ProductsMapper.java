package cn.forest.mall.mapper;

import cn.forest.mall.entity.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProductsMapper extends BaseMapper<Products> {

    int batchAudit(@Param("idList") List<Long> idList, @Param("auditStatus") Integer auditStatus);

    /**
     * 条件查询
     *
     * @param map
     * @return
     */
    List<Products> selectListByMap(Map<String, Object> map);
}
