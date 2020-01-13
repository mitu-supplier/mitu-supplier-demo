package cn.forest.lyj.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.ExcelUtils;
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
  public Map<String, Object> getUserAll(HttpServletRequest request,Long orgId,String type,Long id){
      return projectsService.getUserAll(request,orgId,type,id);
  }
  
  @RequestMapping(value = "/getProjectsByParentId")
  public Map<String, Object> getProjectsByParentId(Long id){
      return projectsService.getProjectsByParentId(id);
  }
  
  @SysLogs(desc = "导出项目")
  @RequestMapping(value = "/exportList")
  public void  exportList(HttpServletRequest request,String projectName,String orgName,String leader,String token,HttpServletResponse response){
       List<Map<String, Object>> exportList = (List<Map<String, Object>>) projectsService.exportList(request, projectName, orgName, leader,token);
       List<List<Object>> rowList=new ArrayList<List<Object>>();
       List<Object> list=null;
       String[] titles= {"项目名称","科室名称","年度","总预算（元）","资金来源","项目负责人","联系电话","执行进度","已执行金额（元）","剩余金额（元）","状态"};
       for (Map<String, Object> map : exportList) {
           list=new ArrayList<Object>();
           list.add(map.get("projectName"));
           list.add(map.get("orgName"));
           list.add(map.get("year"));
           list.add(map.get("budget"));
           list.add(map.get("projectSource"));
           list.add(map.get("projectLeader"));
           list.add(map.get("leaderPhone"));
           list.add(map.get("per").toString()+"%");
           list.add(map.get("usedTotal"));
           list.add(map.get("laveTotal"));
           list.add(Integer.parseInt(map.get("status").toString())==0?"暂存":"归档");
           rowList.add(list);
       }
       ExcelUtils.export("项目数据", rowList,titles,response);
  }
  
}
