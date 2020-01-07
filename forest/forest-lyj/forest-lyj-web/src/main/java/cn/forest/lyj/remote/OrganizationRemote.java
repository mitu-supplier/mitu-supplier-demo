package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.OrganizationBack;

@FeignClient(name = "forest-lyj-server", fallback = OrganizationBack.class)
public interface OrganizationRemote {

  @RequestMapping("/organization/listfirstLevel")
  public Object getlistfirstLevel(@RequestParam(value = "page") Long page,
      @RequestParam(value = "pageSize") Long pageSize,@RequestParam(value = "name") String name);

  @RequestMapping("/organization/listChildren")
  public Object getlistChildren(@RequestParam(value = "parentId") Long parentId);

  @RequestMapping("/organization/add")
  public int add(Map<String, ?> map);

  @RequestMapping("/organization/getById")
  public Object getById(@RequestParam(value = "id") Long id);

  @RequestMapping("/organization/delete")
  public int delete(@RequestParam(value = "id") Long id);

  @RequestMapping("/organization/update")
  public int update(Map<String, ?> map);
  
  @RequestMapping("/organization/list")
  public Object list();

}
