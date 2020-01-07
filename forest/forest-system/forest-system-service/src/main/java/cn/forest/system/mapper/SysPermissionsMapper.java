package cn.forest.system.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.system.entity.SysPermissions;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionsMapper extends BaseMapper<SysPermissions> {

  
  public List<SysPermissions> getPermissionByRoleId(Map<String, Object> map);

  public List<SysPermissions> getPermissionByUserId(@Param("userId") Long userId);
  
  public List<SysPermissions> getRoleButton(Map<String, Object> map);
}
