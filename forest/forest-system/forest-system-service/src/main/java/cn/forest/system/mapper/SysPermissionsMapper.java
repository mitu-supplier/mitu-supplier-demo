package cn.forest.system.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.system.entity.SysPermissions;

public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {

  
  public List<SysPermissions> getPermissionByRoleId(Map<String, Object> map);
}
