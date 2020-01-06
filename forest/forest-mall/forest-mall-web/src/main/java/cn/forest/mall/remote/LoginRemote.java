package cn.forest.mall.remote;

import cn.forest.mall.fallback.LoginBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "forest-system-server", fallback = LoginBack.class)
public interface LoginRemote {

  @RequestMapping("/login/getUser")
  public Object getUser(@RequestParam("loginName") String loginName);
}
