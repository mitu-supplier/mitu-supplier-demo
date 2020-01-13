package cn.forest.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysPermissionsRemote;

@Service("sysPermissionsService")
public class SysPermissionsService {

  @Autowired
  private SysPermissionsRemote sysPermissionsRemote;

  public Map<String, Object> getSysPermissionsList() {
    Object sysPermissionsList = sysPermissionsRemote.getSysPermissionsList();
    if (sysPermissionsList != null) {
      return ResultMessage.success(sysPermissionsList);
    }
    return null;
  }

  public Map<String, Object> getlistfirstLevel(Long page, Long pageSize, String name) {
    Object sysPermissionsList = sysPermissionsRemote.getlistfirstLevel(page, pageSize, name);
    if (sysPermissionsList != null) {
      return ResultMessage.success(sysPermissionsList);
    }
    return null;
  }

  public Map<String, Object> getlistChildren(Long parentId) {
    Object sysPermissionsList = sysPermissionsRemote.getlistChildren(parentId);
    if (sysPermissionsList != null) {
      return ResultMessage.success(sysPermissionsList);
    }
    return null;
  }

  public Map<String, Object> add(Map<String, ?> map) {
    int add = sysPermissionsRemote.add(map);
    if (add > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }
  
  public Map<String, Object> getById(Long id) {
    Object obj = sysPermissionsRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
  public Map<String, Object> delete(Long id) {
    int delete = sysPermissionsRemote.delete(id);
    if (delete > 0) {
      return ResultMessage.result("删除成功");
    }
    return ResultMessage.error("删除失败");
  }
  
  public Map<String, Object> update(Map<String, ?> map) {
    int add = sysPermissionsRemote.update(map);
    if (add > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }
  
  public Map<String, Object> getPermissByRoleId(Long roleId) {
    Object obj = sysPermissionsRemote.getPermissByRoleId(roleId);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
}
