package cn.forest.system.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.system.entity.SysRole;

public interface SysRoleMapper extends BaseMapper<SysRole> {

  
  List<SysRole> getRoleByUserId(Map<String, Object> map);
  
}
