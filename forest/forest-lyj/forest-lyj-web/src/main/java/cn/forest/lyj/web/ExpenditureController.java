package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.ExpenditureService;

@RestController
@RequestMapping("expenditure")
@SysLogs(desc  = "执行情况管理")
public class ExpenditureController {
  
  @Autowired
  private ExpenditureService expenditureService;

  @RequestMapping(value = "/list")
  @SysLogs(desc = "执行情况列表")
  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName){
      return expenditureService.list(page, pageSize,request,projectName,orgName);
  }

  @RequestMapping(value = "/getById")
  @SysLogs(desc = "获取一条执行情况")
  public Map<String, Object> getById(@RequestParam("id") Long id){
      return  expenditureService.getById(id);
  }

  @RequestMapping(value = "/save")
  @SysLogs(desc = "保存执行情况")
  public Map<String, Object> save(HttpServletRequest request){
      return expenditureService.save(RequestMap.requestToMap(request),request);
  }

  @RequestMapping(value = "/update")
  @SysLogs(desc = "修改执行情况")
  public Map<String, Object> update(HttpServletRequest request){
      return expenditureService.update(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/delete")
  @SysLogs(desc = "删除执行情况")
  public Map<String, Object> delete(@RequestParam("id") Long id){
      return expenditureService.delete(id);
  }
}
