package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.ProjectsService;

@RestController
@RequestMapping("projects")
@SysLogs(desc  = "项目管理")
public class ProjectsController {
  @Autowired
  private ProjectsService projectsService;

  @RequestMapping(value = "/list")
  @SysLogs(desc = "项目管理列表")
  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName,String leader){
      return projectsService.list(page, pageSize,request,projectName,orgName,leader);
  }

  @RequestMapping(value = "/getById")
  @SysLogs(desc = "获取一条项目")
  public Map<String, Object> getById(@RequestParam("id") Long id){
      return  projectsService.getById(id);
  }

  @RequestMapping(value = "/save")
  @SysLogs(desc = "保存项目")
  public Map<String, Object> save(HttpServletRequest request){
      return projectsService.save(RequestMap.requestToMap(request),request);
  }

  @RequestMapping(value = "/update")
  @SysLogs(desc = "修改项目")
  public Map<String, Object> update(HttpServletRequest request){
      return projectsService.update(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/delete")
  @SysLogs(desc = "删除项目")
  public Map<String, Object> delete(@RequestParam("id") Long id){
      return projectsService.delete(id);
  }
  
  @RequestMapping(value = "/getUserAll")
  public Map<String, Object> getUserAll(HttpServletRequest request){
      return projectsService.getUserAll(request);
  }
  
  
  
}
