package cn.forest.login.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.login.fallback.LoginBack;

@FeignClient(name = "forest-system-server", fallback = LoginBack.class)
public interface LoginRemote {

  @RequestMapping("/login/getUser")
  public Object getUser(@RequestParam("loginName") String loginName);
}
