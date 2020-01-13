package cn.forest.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysLoginLogsRemote;

@Service("sysLoginLogsService")
public class SysLoginLogsService {

  @Autowired
  private SysLoginLogsRemote sysLoginLogsRemote;

  public Map<String, Object> getList(Long page, Long pageSize, String userName) {
    Object sysLoginLogsList = sysLoginLogsRemote.getList(page, pageSize, userName);
    if (sysLoginLogsList != null) {
      return ResultMessage.success(sysLoginLogsList);
    }
    return null;
  }

}
