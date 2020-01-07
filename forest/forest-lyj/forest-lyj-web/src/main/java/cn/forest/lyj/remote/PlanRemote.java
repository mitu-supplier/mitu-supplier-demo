package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.PlanBack;

@FeignClient(name = "forest-lyj-server", fallback = PlanBack.class)
public interface PlanRemote {

  @RequestMapping("/plan/list")
  Object list(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "orgId") Long orgId);

  @RequestMapping("/plan/getById")
  Object getById(@RequestParam("id") Long id);

  @RequestMapping("/plan/save")
  int save(Map<String, ?> map);

  @RequestMapping("/plan/update")
  int update(Map<String, ?> map);

  @RequestMapping("/plan/delete")
  int delete(@RequestParam("id") Long id);
}
