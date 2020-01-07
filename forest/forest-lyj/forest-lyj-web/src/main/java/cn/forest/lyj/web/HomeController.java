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
  public Map<String, Object> getProjectByOrgId(Long orgId,HttpServletRequest request){
      return homeService.getProjectByOrgId(orgId,request);
  }
  
  @RequestMapping(value = "/getProjectsCount")
  public Map<String, Object> getProjectsCount(Long orgId,Long id,HttpServletRequest request){
    return homeService.getProjectsCount(id, orgId, request);
  }
  
  @RequestMapping(value = "/getProjects")
  public Map<String, Object> getProjects(Long page, Long pageSize,Long orgId,Long id,HttpServletRequest request){
    return homeService.getProjects(page, pageSize, id, orgId, request);
  }
  
  
}
