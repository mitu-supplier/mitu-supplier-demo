package cn.forest.system.fallback;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysLoginLogsRemote;

@Component
public class SysLoginLogsBack implements SysLoginLogsRemote {

  @Override
  public Object getList(Long page, Long pageSize) {
    return null;
  }

}
