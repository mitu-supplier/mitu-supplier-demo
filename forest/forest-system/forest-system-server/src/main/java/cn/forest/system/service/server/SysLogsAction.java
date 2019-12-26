package cn.forest.system.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysLogs;
import cn.forest.system.mapper.SysLogsMapper;

@RestController
@RequestMapping("sys_logs")
public class SysLogsAction {
   
  @Autowired
  private SysLogsMapper sysLogsMapper;
  
  
  @RequestMapping("list")
  public Object getList(Long page,Long pageSize) {
    Page<SysLogs> pages=new Page<SysLogs>(page,pageSize);
    QueryWrapper<SysLogs> queryWrapper=new QueryWrapper<SysLogs>();
    queryWrapper.orderByDesc("create_time");
    IPage<SysLogs> selectPage = sysLogsMapper.selectPage(pages, queryWrapper);
    return new ResultPage<SysLogs>(selectPage);
  }
  
  @RequestMapping("add")
  public int add(@RequestBody SysLogs sysLogs) {
    return sysLogsMapper.insert(sysLogs);
  }
}
