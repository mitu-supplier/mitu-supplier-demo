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
import cn.forest.system.entity.SysExceptionLogs;
import cn.forest.system.mapper.SysExceptionLogsMapper;

@RestController
@RequestMapping("sys_exception_logs")
public class SysExceptionLogsAction {

  @Autowired
  private SysExceptionLogsMapper sysExceptionLogsMapper;
  
  @RequestMapping("/add")
  public int add(@RequestBody SysExceptionLogs sysExceptionLogs) {
    return sysExceptionLogsMapper.insert(sysExceptionLogs);
  }
  
  @RequestMapping("/list")
  public Object getList(Long page,Long pageSize,String name) {
    Page<SysExceptionLogs> pages=new Page<SysExceptionLogs>(page,pageSize);
    QueryWrapper<SysExceptionLogs> queryWrapper=new QueryWrapper<SysExceptionLogs>();
    if(!StringUtils.isEmpty(name)) {
      queryWrapper.like("user_name", name).or().like("login_name", name);
    }
    queryWrapper.orderByDesc("create_time");
    IPage<SysExceptionLogs> selectPage = sysExceptionLogsMapper.selectPage(pages, queryWrapper);
    return new ResultPage<SysExceptionLogs>(selectPage);
  }
  
}
