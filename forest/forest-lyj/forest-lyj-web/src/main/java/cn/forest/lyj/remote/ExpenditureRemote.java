package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.ExpenditureBack;

@FeignClient(name = "forest-lyj-server", fallback = ExpenditureBack.class)
public interface ExpenditureRemote {
  @RequestMapping("/expenditure/list")
  Object list(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "projectName") String projectName,
      @RequestParam(value = "orgName") String orgName,@RequestParam(value = "orgId")Long orgId);

  @RequestMapping("/expenditure/getById")
  Object getById(@RequestParam("id") Long id);

  @RequestMapping("/expenditure/save")
  int save(Map<String, ?> map);

  @RequestMapping("/expenditure/update")
  int update(Map<String, ?> map);

  @RequestMapping("/expenditure/delete")
  int delete(@RequestParam("id") Long id);
  
  @RequestMapping("/expenditure/getExpenditureCount")
  Object getExpenditureCount(@RequestParam(value = "userId") Long userId,@RequestParam(value = "orgId")Long orgId,@RequestParam(value = "projectId")Long projectId);
  
}
