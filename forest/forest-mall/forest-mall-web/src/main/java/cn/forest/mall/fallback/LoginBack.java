package cn.forest.mall.fallback;

import cn.forest.mall.remote.LoginRemote;
import org.springframework.stereotype.Component;

@Component
public class LoginBack implements LoginRemote {

  @Override
  public Object getUser(String loginName) {
    return null;
  }

}
