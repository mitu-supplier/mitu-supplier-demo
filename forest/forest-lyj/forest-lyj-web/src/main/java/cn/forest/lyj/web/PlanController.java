package cn.forest.lyj.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.forest.common.Constant;
import cn.forest.common.util.ExcelUtils;
import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.OrganizationService;
import cn.forest.lyj.service.PlanService;
import cn.forest.lyj.service.ProjectsService;
import net.sf.json.JSONArray;

@RestController
@RequestMapping("plan")
@SysLogs(desc  = "计划管理")
public class PlanController {

  @Autowired
  private PlanService planService;

  @Autowired
  private OrganizationService organizationService;
  
  @Autowired
  private  ProjectsService projectsService;
  
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
  
  @SysLogs(desc = "下载支出计划模板")
  @RequestMapping(value = "/template")
  public void  template(HttpServletRequest request,HttpServletResponse response){
    String[] titles= {"支出计划唯一标识","项目唯一标识","项目名称","科室编码","科室名称","计划支出时间","计划支出金额（元）","用途"};
    ExcelUtils.export("支出计划模板", null,titles,response);
  }
  
  @SysLogs(desc = "导出支出计划")
  @RequestMapping(value = "/exportList")
  public void list(HttpServletRequest request,HttpServletResponse response, String projectName,String orgName,String token){
    List<Map<String, Object>>  exportList = (List<Map<String, Object>>)  planService.exportList(request, projectName, orgName, token);
    List<List<Object>> rowList=new ArrayList<List<Object>>();
    List<Object> list=null;
    String[] titles= {"支出计划唯一标识","项目唯一标识","项目名称","科室编码","科室名称","计划支出年","计划支出月","计划支出金额（元）","用途","状态"};
    for (Map<String, Object> map : exportList) {
      list=new ArrayList<Object>();
      Map<String, Object> org = organizationService.getById(Long.parseLong(map.get("orgId").toString()));
      Map orgMap = (Map) org.get("data");
      list.add(map.get("id"));
      list.add(map.get("projectId"));
      list.add(map.get("projectName"));
      list.add(orgMap.get("code"));
      list.add(map.get("orgName"));
      list.add(map.get("year"));
      list.add(map.get("month"));
      list.add(map.get("planTotal"));
      list.add(map.get("planUsing"));
      list.add(Integer.parseInt(map.get("status").toString())==0?"暂存":"归档");
      rowList.add(list);
    }
    ExcelUtils.export("支出计划", rowList,titles,response);
  }
  
  @SysLogs(desc = "导入支出计划")
  @RequestMapping(value = "/importExcel")
  public Map<String, Object>  importExcel(HttpServletRequest request,HttpServletResponse response){
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
    String token = request.getParameter(Constant.HEADER_TOKEN_STRING);
    Map<String, String> fieldsMap = new HashMap<>();
    fieldsMap.put("支出计划唯一标识", "id");
    fieldsMap.put("项目唯一标识", "projectId");
    fieldsMap.put("项目名称", "projectName");
    fieldsMap.put("科室编码", "code");
    fieldsMap.put("科室名称", "orgName");
    fieldsMap.put("计划支出年", "year");
    fieldsMap.put("计划支出月", "month");
    fieldsMap.put("计划支出金额（元）", "planTotal");
    fieldsMap.put("用途", "planUsing");
    try {
      JSONArray excelData = ExcelUtils.getExcelData(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), fieldsMap,9);
      return planService.save(excelData, token);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
    
  }
}
