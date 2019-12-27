package cn.forest.login.fallback;

import cn.forest.login.remote.SysPermissionsRemote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SysPermissionsBack implements SysPermissionsRemote {

  @Override
  public Object getPermissByRoleId(Long roleId) {
    return 0;
  }

  @Override
  public Object getPermissionByUserId(Long userId) {
    return 0;
  }
}
