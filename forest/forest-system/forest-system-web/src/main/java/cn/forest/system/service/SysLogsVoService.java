package cn.forest.system.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysLogsVoRemote;

@Service("sysLogsVoService")
public class SysLogsVoService {

  @Autowired
  private SysLogsVoRemote sysLogsVoRemote;
  public Map<String, Object> getList(Long page,Long PageSize,String name){
    Object list = sysLogsVoRemote.getList(page, PageSize,name);
    if (list != null) {
      return ResultMessage.success(list);
    }
    return null;
  }
  
  
}
