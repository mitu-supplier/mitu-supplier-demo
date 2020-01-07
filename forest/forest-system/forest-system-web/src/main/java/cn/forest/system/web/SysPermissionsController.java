package cn.forest.system.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysPermissionsService;

@RestController
@RequestMapping("sysPermissions")
@SysLogs(desc  = "菜单管理")
public class SysPermissionsController {
  
  @Autowired
  private SysPermissionsService sysPermissionsService;

  
  
  @RequestMapping("/list")
  @SysLogs(desc  = "菜单列表")
  public Map<String, Object> getList() {
    return sysPermissionsService.getSysPermissionsList();
  }
  
  @RequestMapping("/getlistfirstLevel")
  @SysLogs(desc  = "菜单一级列表")
  public Map<String, Object> getlistfirstLevel(Long page, Long pageSize,String name) {
    return sysPermissionsService.getlistfirstLevel(page, pageSize,name);
  }

  @RequestMapping("/getlistChildren")
  @SysLogs(desc  = "菜单子集列表")
  public Map<String, Object> getlistChildren(Long parentId) {
    return sysPermissionsService.getlistChildren(parentId);
  }

  @RequestMapping("/add")
  @SysLogs(desc = "添加菜单")
  public Map<String, Object> add(HttpServletRequest request) {
    return sysPermissionsService.add(RequestMap.requestToMap(request));
  }
  
  
  @RequestMapping("/delete")
  @SysLogs(desc = "删除菜单")
  public Map<String, Object> delete(Long id) {
    return sysPermissionsService.delete(id);
  }
  
  
  @RequestMapping("/getById")
  @SysLogs(desc = "查询菜单")
  public Map<String, Object> getById(Long id) {
    return sysPermissionsService.getById(id);
  }
  
  @RequestMapping("/update")
  @SysLogs(desc = "修改菜单")
  public Map<String, Object> update(HttpServletRequest request) {
    return sysPermissionsService.update(RequestMap.requestToMap(request));
  }
  
}
