package cn.forest.common.web.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.common.web.remote.SysExceptionLogsRemote;

@Component
public class SysExceptionLogsBack implements SysExceptionLogsRemote {

  @Override
  public int add(Map<String, ?> map) {
    return 0;
  }

}
