package cn.forest.lyj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.SuperviseBack;

@FeignClient(name = "forest-lyj-server", fallback = SuperviseBack.class)
public interface SuperviseRemote {

  
  @RequestMapping("/projects/list")
  Object projectsList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "leader") String leader,
      @RequestParam(value = "orgId") Long orgId);
  
  @RequestMapping("/contract/list")
  Object contractList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "contractName")String contractName,
      @RequestParam(value = "projectName")String projectName,@RequestParam(value = "orgName")String orgName,
      @RequestParam(value = "leader")String leader,@RequestParam(value = "orgId") Long orgId);
  
  @RequestMapping("/plan/list")
  Object planList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "orgId") Long orgId);
  
  @RequestMapping("/expenditure/list")
  Object expenditureList(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "orgId") Long orgId);
}
