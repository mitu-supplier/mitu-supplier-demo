package cn.forest.system.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysLoginLogsService;

@RestController
@RequestMapping("sysLoginLogs")
@SysLogs(desc  = "登录日志管理")
public class SysLoginLogsController {

  @Autowired
  private SysLoginLogsService sysLoginLogsService;

  @RequestMapping("/list")
  @SysLogs(desc  = "登录日志列表")
  public Map<String, Object> getList(Long page, Long pageSize,String name) {
    return sysLoginLogsService.getList(page, pageSize,name);
  }

}
