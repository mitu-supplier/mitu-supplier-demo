package cn.forest.login.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.login.remote.SysUserRemote;

@Component
public class SysUserBack implements SysUserRemote {

  @Override
  public int update(Map<String, ?> map) {
    return 0;
  }

}
