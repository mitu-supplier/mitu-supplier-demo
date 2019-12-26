package cn.forest.login.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.login.remote.SysLoginLogsRemote;

@Component
public class SysLoginLogsBack implements SysLoginLogsRemote {

  @Override
  public int add(Map<String, ?> map) {
    return 0;
  }

}
