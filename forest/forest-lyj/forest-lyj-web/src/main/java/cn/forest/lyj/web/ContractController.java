package cn.forest.lyj.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.ExcelUtils;
import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.ContractService;

@RestController
@RequestMapping("contract")
@SysLogs(desc  = "项目资料管理")
public class ContractController {
  
  @Autowired
  private ContractService contractService;


  @RequestMapping(value = "/list")
  @SysLogs(desc = "项目资料列表")
  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String contractName,String projectName,String orgName,String leader){
      return contractService.list(page, pageSize,request,contractName,projectName,orgName,leader);
  }

  @RequestMapping(value = "/getById")
  @SysLogs(desc = "获取项目资料")
  public Map<String, Object> getById(@RequestParam("id") Long id){
      return  contractService.getById(id);
  }

  @RequestMapping(value = "/save")
  @SysLogs(desc = "保存项目资料")
  public Map<String, Object> save(HttpServletRequest request){
      return contractService.save(RequestMap.requestToMap(request),request);
  }

  @RequestMapping(value = "/update")
  @SysLogs(desc = "修改合同")
  public Map<String, Object> update(HttpServletRequest request){
      return contractService.update(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/delete")
  @SysLogs(desc = "删除项目资料")
  public Map<String, Object> delete(@RequestParam("id") Long id){
      return contractService.delete(id);
  }
  
  
  @SysLogs(desc = "导出项目资料")
  @RequestMapping(value = "/exportList")
  public void exportList(HttpServletRequest request,String contractName,String projectName,String orgName,String leader){
    Map<String, Object> exportList = contractService.exportList(request, contractName, projectName, orgName, leader);
    List<List<Object>> rowList=new ArrayList<List<Object>>();
    List<Object> list=null;
    String[] titles= {"项目资料唯一标识","项目资料名称","项目唯一标识","项目名称","科室编码","科室名称","资料类型","总金额（元）","负责人","联系方式","签订时间"};
  }
  
  @SysLogs(desc = "下载项目资料模板")
  @RequestMapping(value = "/template")
  public void  template(HttpServletRequest request,HttpServletResponse response){
    String[] titles= {"项目资料唯一标识","项目资料名称","项目唯一标识","项目名称","科室编码","科室名称","资料类型","总金额（元）","负责人","联系方式","签订时间"};
    ExcelUtils.export("项目资料模板", null,titles,response);
  }
}
