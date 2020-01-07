package cn.forest.system.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysLogsVoService;

@RestController
@RequestMapping("sysLogs")
@SysLogs(desc  = "操作日志管理")
public class SysLogsController {

  @Autowired
  private SysLogsVoService sysLogsVoService;
  
  @RequestMapping("list")
  @SysLogs(desc  = "操作日志列表")
  public Map<String, Object> getList(Long page,Long pageSize,String name){
    return sysLogsVoService.getList(page, pageSize,name);
  }
  
}
