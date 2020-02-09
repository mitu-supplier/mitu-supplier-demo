package cn.forest.system.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysUserRemote;

@Component
public class SysUserBack implements SysUserRemote {

  @Override
  public Object getList(Long page, Long pageSize, String loginName, String name, String phone, String email) {
    return null;
  }

  @Override
  public int delete(Long id) {
    return 0;
  }

  @Override
  public Object add(Map<String, ?> map) {
    return null;
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
  public Object getLoginName(String loginName) {
    return null;
  }
}
