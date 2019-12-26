package cn.forest.login.fallback;

import org.springframework.stereotype.Component;

import cn.forest.login.remote.LoginRemote;

@Component
public class LoginBack implements LoginRemote {

  @Override
  public Object getUser(String loginName) {
    return null;
  }

}
