package cn.forest.lyj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.lyj.entity.Expenditure;
import cn.forest.lyj.entity.ExpenditureVo;

public interface ExpenditureMapper extends BaseMapper<Expenditure> {
 
  List<Expenditure> getExpenditureList(Map<String, Object> map);
  
  Expenditure getExpenditureById(@Param("id") Long id);
  
  
  ExpenditureVo getExpenditureCount(Map<String, Object> map);
}
