package cn.forest.mall.mapper;

import cn.forest.mall.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper extends BaseMapper<OrderItem> {

    List<OrderItem> selectByProduct(@Param("productCode") String productCode);
}
