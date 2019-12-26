package cn.forest.common.web.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.common.web.remote.SysLogsRemote;

@Component
public class SysLogsBack implements SysLogsRemote {

  @Override
  public int add(Map<String, ?> map) {
    return 0;
  }

}
