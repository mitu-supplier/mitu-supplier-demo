package cn.forest.system.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysUserRemote;

@Component
public class SysUserBack implements SysUserRemote {

  @Override
  public Object getList(Long page, Long pageSize,Long typeId,String name) {
    return null;
  }

  @Override
  public int delete(Long id) {
    return 0;
  }

  @Override
  public int add(Map<String, Object> map) {
    return 0;
  }

  @Override
  public int update(Map<String, ?> map) {
    return 0;
  }

  @Override
  public Object getById(Long id) {
    return null;
  }

  @Override
  public int saveRole(Map<String, ?> map) {
    return 0;
  }

  @Override
  public Object getOrgByUserId(Long userId) {
    return null;
  }

}
