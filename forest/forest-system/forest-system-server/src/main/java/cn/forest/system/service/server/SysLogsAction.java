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
import cn.forest.system.entity.SysLogs;
import cn.forest.system.mapper.SysLogsMapper;

@RestController
@RequestMapping("sys_logs")
public class SysLogsAction {
   
  @Autowired
  private SysLogsMapper sysLogsMapper;
  
  
  @RequestMapping("list")
  public Object getList(@RequestParam("page") Long page,
                        @RequestParam("pageSize") Long pageSize,
                        @RequestParam(value = "userName", required = false) String userName,
                        @RequestParam(value = "modelName", required = false) String modelName) {
    Page<SysLogs> pages=new Page<SysLogs>(page,pageSize);
    QueryWrapper<SysLogs> queryWrapper=new QueryWrapper<SysLogs>();
    if(!StringUtil.isBlank(userName)){
      queryWrapper.like("user_name", userName);
    }
    if(!StringUtil.isBlank(modelName)){
      queryWrapper.like("model_name", modelName);
    }
    queryWrapper.orderByDesc("create_time");
    IPage<SysLogs> selectPage = sysLogsMapper.selectPage(pages, queryWrapper);
    return new ResultPage<SysLogs>(selectPage);
  }
  
  @RequestMapping("add")
  public int add(@RequestBody SysLogs sysLogs) {
    return sysLogsMapper.insert(sysLogs);
  }
}
