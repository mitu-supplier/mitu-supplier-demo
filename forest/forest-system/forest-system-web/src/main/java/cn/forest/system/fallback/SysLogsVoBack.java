package cn.forest.system.fallback;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.SysLogsVoRemote;

@Component
public class SysLogsVoBack implements SysLogsVoRemote {

  @Override
  public Object getList(Long page, Long pageSize,String name) {
    return null;
  }

}
