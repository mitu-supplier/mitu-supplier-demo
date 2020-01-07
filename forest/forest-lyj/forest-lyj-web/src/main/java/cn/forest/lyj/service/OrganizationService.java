package cn.forest.lyj.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.OrganizationRemote;

@Service("organizationService")
public class OrganizationService {

  @Autowired
  private OrganizationRemote organizationRemote;
  
  public Map<String, Object> getlistfirstLevel(Long page, Long pageSize,String name) {
    Object organizationList = organizationRemote.getlistfirstLevel(page, pageSize,name);
    if (organizationList != null) {
      return ResultMessage.success(organizationList);
    }
    return null;
  }

  public Map<String, Object> getlistChildren(Long parentId) {
    Object organizationList = organizationRemote.getlistChildren(parentId);
    if (organizationList != null) {
      return ResultMessage.success(organizationList);
    }
    return null;
  }

  public Map<String, Object> add(Map<String, ?> map) {
    int add = organizationRemote.add(map);
    if (add > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }
  
  public Map<String, Object> getById(Long id) {
    Object obj = organizationRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
  public Map<String, Object> delete(Long id) {
    int delete = organizationRemote.delete(id);
    if (delete > 0) {
      return ResultMessage.result("删除成功");
    }
    return ResultMessage.error("删除失败");
  }
  
  public Map<String, Object> update(Map<String, ?> map) {
    int add = organizationRemote.update(map);
    if (add > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }
  
  
}
