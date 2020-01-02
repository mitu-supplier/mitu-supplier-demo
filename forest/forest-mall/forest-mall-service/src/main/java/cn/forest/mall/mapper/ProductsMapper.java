package cn.forest.mall.mapper;

import cn.forest.mall.entity.Products;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductsMapper extends BaseMapper<Products> {

    int batchAudit(@Param("idList") List<Long> idList, @Param("auditStatus") Integer auditStatus);
}
