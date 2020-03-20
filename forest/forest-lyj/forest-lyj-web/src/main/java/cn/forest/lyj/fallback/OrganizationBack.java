package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.OrganizationRemote;

@Component
public class OrganizationBack implements OrganizationRemote {

  @Override
  public Object getlistfirstLevel(Long page, Long pageSize,String name) {
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
  public Object list() {
    return 0;
  }

  @Override
  public Object getOrgByUserId(Long userId) {
    return null;
  }

  @Override
  public Object code(String code) {
    return null;
  }

}
