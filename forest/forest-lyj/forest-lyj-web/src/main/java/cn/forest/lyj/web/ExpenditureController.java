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
import cn.forest.lyj.service.ExpenditureService;

@RestController
@RequestMapping("expenditure")
@SysLogs(desc  = "执行情况管理")
public class ExpenditureController {
  
  @Autowired
  private ExpenditureService expenditureService;

  
  
  @RequestMapping(value = "/exportList")
  @SysLogs(desc = "导出执行情况")
  public void exportList(HttpServletRequest request,String projectName,String orgName,String token,HttpServletResponse response){
    System.out.println(token);
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> exportList = (List<Map<String, Object>>) expenditureService.exportList(request,projectName,orgName,token);
    List<List<Object>> rowList=new ArrayList<List<Object>>();
    List<Object> list=null;
    String[] titles= {"项目名称","科室名称","支出时间","支出金额（元）","用途","乙方单位","状态"};
    for (Map<String, Object> map : exportList) {
        list=new ArrayList<Object>();
        list.add(map.get("projectName"));
        list.add(map.get("orgName"));
        list.add(map.get("expenditureTime"));
        list.add(map.get("expenditureTotal"));
        list.add(map.get("expenditureUsing"));
        list.add(map.get("partyBUnits"));
        list.add(Integer.parseInt(map.get("status").toString())==0?"暂存":"归档");
        rowList.add(list);
    }
    ExcelUtils.export("执行情况", rowList,titles,response);
    
    
  }
  
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
