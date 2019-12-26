package cn.forest.system.fallback;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysExceptionLogsVoRemote;

@Component
public class SysExceptionLogsVoBack implements SysExceptionLogsVoRemote {

  @Override
  public Object getList(Long page, Long pageSize) {
    return null;
  }

}
