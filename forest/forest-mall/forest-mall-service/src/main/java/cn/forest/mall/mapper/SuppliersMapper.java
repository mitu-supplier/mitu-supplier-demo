package cn.forest.mall.mapper;

import cn.forest.mall.entity.Suppliers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface SuppliersMapper extends BaseMapper<Suppliers> {

    List<Suppliers> selectListByMap(Map<String, Object> map);
}
