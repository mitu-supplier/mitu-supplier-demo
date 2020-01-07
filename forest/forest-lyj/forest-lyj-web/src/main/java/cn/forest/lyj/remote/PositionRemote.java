package cn.forest.lyj.remote;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.PositionBack;


@FeignClient(name = "forest-lyj-server", fallback = PositionBack.class)
public interface PositionRemote {

  @RequestMapping("/position/list")
  Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize);

  @RequestMapping("/position/getById")
  Object getById(@RequestParam("id") Long id);

  @RequestMapping("/position/save")
  int save(Map<String, ?> map);

  @RequestMapping("/position/update")
  int update(Map<String, ?> map);

  @RequestMapping("/position/delete")
  int delete(@RequestParam("id") Long id);
  
}
