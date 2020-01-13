package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.ContractBack;

@FeignClient(name = "forest-lyj-server", fallback = ContractBack.class)
public interface ContractRemote {

  @RequestMapping("/contract/list")
  Object list(@RequestParam(value = "page") Long page, @RequestParam(value = "pageSize") Long pageSize,
      @RequestParam(value = "userId") Long userId,@RequestParam(value = "contractName")String contractName,
      @RequestParam(value = "projectName")String projectName,@RequestParam(value = "orgName")String orgName,
      @RequestParam(value = "leader")String leader,@RequestParam(value = "orgIds")String orgIds);

  @RequestMapping("/contract/getById")
  Object getById(@RequestParam("id") Long id);

  @RequestMapping("/contract/save")
  int save(Map<String, ?> map);

  @RequestMapping("/contract/update")
  int update(Map<String, ?> map);

  @RequestMapping("/contract/delete")
  int delete(@RequestParam("id") Long id);

}
