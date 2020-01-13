package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.HomeService;
import cn.forest.lyj.service.OrganizationService;
import cn.forest.lyj.service.ProjectsService;

@RestController
@RequestMapping("home")
@SysLogs(desc  = "项目监管")
public class HomeController {

  @Autowired
  private HomeService homeService;
  
  @RequestMapping("/getOrg")
  public Map<String, Object> getOrg(HttpServletRequest request) {
    return homeService.getOrg(request);
  }
  
  
  @RequestMapping(value = "/getProjectByOrgId")
  public Map<String, Object> getProjectByOrgId(Long orgId,Integer year,Long parentId,HttpServletRequest request){
      return homeService.getProjectByOrgId(orgId,request,year,parentId);
  }
  
  @RequestMapping(value = "/getProjectsCount")
  public Map<String, Object> getProjectsCount(Long orgId,Long id,Integer year,HttpServletRequest request){
    return homeService.getProjectsCount(id, orgId,year, request);
  }
  
  @RequestMapping(value = "/getProjects")
  public Map<String, Object> getProjects(Long page, Long pageSize,Long orgId,Long id,HttpServletRequest request,Integer year){
    return homeService.getProjects(page, pageSize, id, orgId, request,year);
  }
  
  @RequestMapping(value = "/getOnlineUser")
  public Map<String, Object> getOnlineUser(HttpServletRequest request,Long id,String type) {
    return homeService.getOnlineUser(request,id,type);
  }
  
  
}
