package cn.forest.system.service.server;

import cn.forest.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public Object getList(@RequestParam("page") Long page,
                        @RequestParam("pageSize") Long pageSize,
                        @RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "modelName", required = false) String modelName) {
    Page<SysExceptionLogs> pages=new Page<SysExceptionLogs>(page,pageSize);
    QueryWrapper<SysExceptionLogs> queryWrapper=new QueryWrapper<SysExceptionLogs>();
    if(!StringUtil.isBlank(userName)){
      queryWrapper.like("user_name", userName);
    }
    if(!StringUtil.isBlank(modelName)){
      queryWrapper.like("model_name", modelName);
    }
    queryWrapper.orderByDesc("create_time");
    IPage<SysExceptionLogs> selectPage = sysExceptionLogsMapper.selectPage(pages, queryWrapper);
    return new ResultPage<SysExceptionLogs>(selectPage);
  }
  
}
