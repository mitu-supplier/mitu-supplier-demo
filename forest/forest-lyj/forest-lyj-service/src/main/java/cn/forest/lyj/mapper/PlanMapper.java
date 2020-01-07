package cn.forest.lyj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.lyj.entity.Plan;

public interface PlanMapper extends BaseMapper<Plan> {

  List<Plan> getPlanList(Map<String, Object> map);
  
  Plan getPlanById(@Param("id") Long id);
}
