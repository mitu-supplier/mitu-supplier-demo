package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.SuperviseService;

@RestController
@RequestMapping("supervise")
@SysLogs(desc  = "项目监管")
public class SuperviseController {

  @Autowired
  private SuperviseService superviseService;
  
  
  @RequestMapping(value = "/projectList")
  @SysLogs(desc = "项目监管列表")
  public Map<String, Object> projectList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName,String leader){
      return superviseService.projectList(page, pageSize,request,projectName,orgName,leader);
  }

  @RequestMapping(value = "/contractList")
  @SysLogs(desc = "合同监管列表")
  public Map<String, Object> contractList(Long page, Long pageSize,HttpServletRequest request,String contractName,String projectName,String orgName,String leader){
      return superviseService.contractList(page, pageSize, request, contractName, projectName, orgName, leader);
  }

  @RequestMapping(value = "/planList")
  @SysLogs(desc = "计划监管列表")
  public Map<String, Object> planList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName){
      return superviseService.planList(page, pageSize, request, projectName, orgName);
  }
  
  
  @RequestMapping(value = "/expenditureList")
  @SysLogs(desc = "执行情况监管列表")
  public Map<String, Object> expenditureList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName){
      return superviseService.expenditureList(page, pageSize, request, projectName, orgName);
  }
  
}
