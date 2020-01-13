package cn.forest.system.mapper;

import cn.forest.system.entity.SysDictionaryData;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysDictionaryDataMapper extends BaseMapper<SysDictionaryData> {
  
  List<SysDictionaryData> selectData(Map<String, Object> map);
}
