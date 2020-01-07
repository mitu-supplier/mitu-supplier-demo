package cn.forest.lyj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.lyj.entity.Contract;

public interface ContractMapper extends BaseMapper<Contract> {

  List<Contract> getContractList(Map<String, Object> map);
  
  Contract getContractById(@Param("id") Long id);
}
