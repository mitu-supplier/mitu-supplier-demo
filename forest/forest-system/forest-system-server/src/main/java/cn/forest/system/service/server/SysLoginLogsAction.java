package cn.forest.system.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysLoginLogs;
import cn.forest.system.mapper.SysLoginLogsMapper;

@RestController
@RequestMapping("sys_login_logs")
public class SysLoginLogsAction {

  @Autowired
  private SysLoginLogsMapper sysLoginLogsMapper;

  @RequestMapping("/add")
  public int add(@RequestBody SysLoginLogs loginLogs) {
    return sysLoginLogsMapper.insert(loginLogs);
  }

  @RequestMapping("/list")
  public Object getList(Long page, Long pageSize,String name) {
    Page<SysLoginLogs> pages = new Page<SysLoginLogs>(page, pageSize);
    QueryWrapper<SysLoginLogs> queryWrapper=new QueryWrapper<SysLoginLogs>();
    if(!StringUtils.isEmpty(name)) {
      queryWrapper.like("user_name", name).or().like("login_name", name);
    }
    queryWrapper.orderByDesc("create_time");
    IPage<SysLoginLogs> selectPage = sysLoginLogsMapper.selectPage(pages, queryWrapper);
    return new ResultPage<SysLoginLogs>(selectPage);
  }

}
