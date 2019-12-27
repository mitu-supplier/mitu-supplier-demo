package cn.forest.login.fallback;

import cn.forest.login.remote.SysRoleRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysRoleBack implements SysRoleRemote {

  @Override
  public Object getRoleByUserId(Long userId) {
    return null;
  }

}
