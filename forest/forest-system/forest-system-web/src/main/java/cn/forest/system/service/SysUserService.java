package cn.forest.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysUserRemote;

@Service("sysUserService")
public class SysUserService {

  @Autowired
  private SysUserRemote sysUserRemote;

  public Map<String, Object> getList(Long page, Long pageSize) {
    Object obj = sysUserRemote.getList(page, pageSize);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }

  public Map<String, Object> delete(Long id) {
    int delete = sysUserRemote.delete(id);
    if (delete > 0) {
      return ResultMessage.result("删除成功");
    }
    return ResultMessage.error("删除失败");
  }

  public Map<String, Object> add(Map<String, ?> map) {
    int add = sysUserRemote.add(map);
    if (add > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
    int delete = sysUserRemote.update(map);
    if (delete > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }

  public Map<String, Object> getById(Long id) {
    Object obj = sysUserRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
  public Map<String, Object> saveRole(Map<String, ?> map) {
    int saveRole = sysUserRemote.saveRole(map);
    if (saveRole > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }

}
