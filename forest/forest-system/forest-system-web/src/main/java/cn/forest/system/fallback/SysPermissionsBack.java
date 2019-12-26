package cn.forest.system.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysPermissionsRemote;

@Component
public class SysPermissionsBack implements SysPermissionsRemote {

  @Override
  public Object getSysPermissionsList() {
    return null;
  }

  @Override
  public Object getlistfirstLevel(Long page, Long pageSize) {
    return null;
  }

  @Override
  public Object getlistChildren(Long parentId) {
    return null;
  }

  @Override
  public int add(Map<String, ?> map) {
    return 0;
  }

  @Override
  public Object getById(Long id) {
    return null;
  }

  @Override
  public int delete(Long id) {
    return 0;
  }

  @Override
  public int update(Map<String, ?> map) {
    return 0;
  }

  @Override
  public Object getPermissByRoleId(Long roleId) {
    return 0;
  }

}
