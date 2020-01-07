package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.PlanService;

@RestController
@RequestMapping("plan")
@SysLogs(desc  = "计划管理")
public class PlanController {

  @Autowired
  private PlanService planService;

  @RequestMapping(value = "/list")
  @SysLogs(desc = "计划管理列表")
  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName){
      return planService.list(page, pageSize,request,projectName,orgName);
  }

  @RequestMapping(value = "/getById")
  @SysLogs(desc = "获取一条计划")
  public Map<String, Object> getById(@RequestParam("id") Long id){
      return  planService.getById(id);
  }

  @RequestMapping(value = "/save")
  @SysLogs(desc = "保存计划")
  public Map<String, Object> save(HttpServletRequest request){
      return planService.save(RequestMap.requestToMap(request),request);
  }

  @RequestMapping(value = "/update")
  @SysLogs(desc = "修改计划")
  public Map<String, Object> update(HttpServletRequest request){
      return planService.update(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/delete")
  @SysLogs(desc = "删除计划")
  public Map<String, Object> delete(@RequestParam("id") Long id){
      return planService.delete(id);
  }
}
