package cn.forest.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysExceptionLogsVoRemote;

@Service("sysExceptionLogsVoService")
public class SysExceptionLogsVoService {

  @Autowired
  private SysExceptionLogsVoRemote sysExceptionLogsVoRemote;
  
  
  public Map<String, Object> getList(Long page, Long pageSize) {
    Object sysExceptionLogs = sysExceptionLogsVoRemote.getList(page, pageSize);
    if (sysExceptionLogs != null) {
      return ResultMessage.success(sysExceptionLogs);
    }
    return null;
  }
  
}
