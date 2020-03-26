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
import cn.forest.lyj.service.ExpenditureService;
import cn.forest.lyj.service.OrganizationService;
import net.sf.json.JSONArray;

@RestController
@RequestMapping("expenditure")
@SysLogs(desc  = "执行情况管理")
public class ExpenditureController {
  
  @Autowired
  private ExpenditureService expenditureService;
  @Autowired
  private OrganizationService organizationService;
  
  
  
  
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
  
  @RequestMapping(value = "/exportList")
  @SysLogs(desc = "导出执行情况")
  public void exportList(HttpServletRequest request,String projectName,String orgName,String token,HttpServletResponse response){
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> exportList = (List<Map<String, Object>>) expenditureService.exportList(request,projectName,orgName,token);
    List<List<Object>> rowList=new ArrayList<List<Object>>();
    List<Object> list=null;
    String[] titles= {"执行情况唯一标识","项目唯一标识","项目名称","科室编码","科室名称","支出时间","支出金额（元）","用途","乙方单位","状态"};
    for (Map<String, Object> map : exportList) {
        list=new ArrayList<Object>();
        Map<String, Object> org = organizationService.getById(Long.parseLong(map.get("orgId").toString()));
        Map orgMap = (Map) org.get("data");
        list.add(map.get("id"));
        list.add(map.get("projectId"));
        list.add(map.get("projectName"));
        list.add(orgMap.get("code"));
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
   
  @SysLogs(desc = "下载执行情况模板")
  @RequestMapping(value = "/template")
  public void  template(HttpServletRequest request,HttpServletResponse response){
    String[] titles= {"项目唯一标识","项目名称","科室编码","科室名称","支出时间","支出金额（元）","用途","乙方单位"};
    ExcelUtils.export("执行情况模板", null,titles,response);
  }
  
  @SysLogs(desc = "导入执行情况")
  @RequestMapping(value = "/importExcel")
  public Map<String, Object>  importExcel(HttpServletRequest request,HttpServletResponse response){
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
    String token = request.getParameter(Constant.HEADER_TOKEN_STRING);
    Map<String, String> fieldsMap = new HashMap<>();
    fieldsMap.put("执行情况唯一标识", "id");
    fieldsMap.put("项目唯一标识", "projectId");
    fieldsMap.put("项目名称", "projectName");
    fieldsMap.put("科室编码", "code");
    fieldsMap.put("科室名称", "orgName");
    fieldsMap.put("支出时间", "expenditureTime");
    fieldsMap.put("支出金额（元）", "expenditureTotal");
    fieldsMap.put("用途", "expenditureUsing");
    fieldsMap.put("乙方单位", "partyBUnits");
    try {
      JSONArray excelData = ExcelUtils.getExcelData(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), fieldsMap,8);
      return expenditureService.save(excelData, token);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
    
  }
  
  
  
}
