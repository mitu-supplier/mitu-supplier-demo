package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.OrganizationService;

@RestController
@RequestMapping("organization")
@SysLogs(desc  = "部门管理")
public class OrganizationController {

  @Autowired
  private OrganizationService organizationService;
  
  @RequestMapping("/getlistfirstLevel")
  @SysLogs(desc  = "部门列表")
  public Map<String, Object> getlistfirstLevel(Long page, Long pageSize,String name) {
    return organizationService.getlistfirstLevel(page, pageSize,name);
  }

  @RequestMapping("/getlistChildren")
  @SysLogs(desc  = "部门子集列表")
  public Map<String, Object> getlistChildren(Long parentId) {
    return organizationService.getlistChildren(parentId);
  }

  @RequestMapping("/add")
  @SysLogs(desc = "添加部门")
  public Map<String, Object> add(HttpServletRequest request) {
    return organizationService.add(RequestMap.requestToMap(request));
  }
  
  
  @RequestMapping("/delete")
  @SysLogs(desc = "删除部门")
  public Map<String, Object> delete(Long id) {
    return organizationService.delete(id);
  }
  
  
  @RequestMapping("/getById")
  @SysLogs(desc = "查询部门")
  public Map<String, Object> getById(Long id) {
    return organizationService.getById(id);
  }
  
  @RequestMapping("/update")
  @SysLogs(desc = "修改部门")
  public Map<String, Object> update(HttpServletRequest request) {
    return organizationService.update(RequestMap.requestToMap(request));
  }
  
  
}
