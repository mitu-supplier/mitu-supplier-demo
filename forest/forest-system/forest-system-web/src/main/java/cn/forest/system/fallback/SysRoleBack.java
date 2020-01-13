package cn.forest.system.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysRoleRemote;

@Component
public class SysRoleBack implements SysRoleRemote {

  @Override
  public Object getSysRoleList(Long page, Long pageSize, String roleName, String roleCode) {
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
  public int saveRolePermiss(Map<String, ?> map) {
    return 0;
  }

  @Override
  public Object getRoleByUserId(Long userId) {
    return null;
  }

}
