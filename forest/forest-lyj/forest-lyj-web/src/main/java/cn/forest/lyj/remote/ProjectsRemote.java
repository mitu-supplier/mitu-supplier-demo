package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.ProjectsBack;

@FeignClient(name = "forest-lyj-server", fallback = ProjectsBack.class)
public interface ProjectsRemote {

  @RequestMapping("/projects/list")
  Object list(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "leader") String leader,
      @RequestParam(value = "orgId") Long orgId);

  @RequestMapping("/projects/getById")
  Object getById(@RequestParam("id") Long id);

  @RequestMapping("/projects/save")
  int save(Map<String, ?> map);

  @RequestMapping("/projects/update")
  int update(Map<String, ?> map);

  @RequestMapping("/projects/delete")
  int delete(@RequestParam("id") Long id);

  @RequestMapping("/projects/getUserAll")
  Object getUserAll(@RequestParam(value = "userId") Long userId);
  
  @RequestMapping("/projects/getProjectByOrgId")
  Object getProjectByOrgId(@RequestParam(value = "orgId") Long orgId);
  
  @RequestMapping("/projects/getProjectsCount")
  Object getProjectsCount(@RequestParam(value = "orgId") Long orgId,@RequestParam(value = "userId") Long userId,@RequestParam(value = "id") Long id);
 
  @RequestMapping("/projects/getProjects")
  Object getProjects(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,@RequestParam(value = "orgId") Long orgId,@RequestParam(value = "userId") Long userId,@RequestParam(value = "id") Long id);
 
  
  
}
