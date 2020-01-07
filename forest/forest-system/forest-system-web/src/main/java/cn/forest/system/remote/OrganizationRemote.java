package cn.forest.system.remote;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.system.fallback.OrganizationBack;


@FeignClient(name = "forest-lyj-server", fallback = OrganizationBack.class)
public interface OrganizationRemote {

  
  @RequestMapping("/organization/list")
  Object list();
  
  @RequestMapping("/organization/getById")
  public Object getById(@RequestParam(value = "id") Long id);
  
}
