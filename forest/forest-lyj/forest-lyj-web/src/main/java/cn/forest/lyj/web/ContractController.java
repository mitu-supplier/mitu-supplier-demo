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
import cn.forest.lyj.service.ContractService;
import cn.forest.lyj.service.OrganizationService;
import cn.forest.lyj.service.ProjectsService;
import net.sf.json.JSONArray;

@RestController
@RequestMapping("contract")
@SysLogs(desc  = "项目资料管理")
public class ContractController {
  
  @Autowired
  private ContractService contractService;
  
  @Autowired
  private OrganizationService organizationService;
  
  @Autowired
  private  ProjectsService projectsService;


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
  public void exportList(HttpServletRequest request,HttpServletResponse response, String contractName,String projectName,String orgName,String leader,String token){
    List<Map<String, Object>>  exportList = (List<Map<String, Object>>) contractService.exportList(request, contractName, projectName, orgName, leader,token);
    List<List<Object>> rowList=new ArrayList<List<Object>>();
    List<Object> list=null;
    String[] titles= {"项目资料唯一标识","项目资料名称","项目唯一标识","项目名称","科室编码","科室名称","资料类型","总金额（元）","负责人","联系方式","签订时间","状态"};
    for (Map<String, Object> map : exportList) {
      list=new ArrayList<Object>();
      Map<String, Object> org = organizationService.getById(Long.parseLong(map.get("orgId").toString()));
      Map orgMap = (Map) org.get("data");
      list.add(map.get("id"));
      list.add(map.get("contractName"));
      list.add(map.get("projectId"));
      list.add(map.get("projectName"));
      list.add(orgMap.get("code"));
      list.add(map.get("orgName"));
      int type=Integer.parseInt(map.get("contractType").toString());
      if(type==0) {
        list.add("项目合同");
      }else if(type==1){
        list.add("其他资料");
      }
      list.add(map.get("contractTotal"));
      list.add(map.get("contractLeader"));
      list.add(map.get("contractLeaderPhone"));
      list.add(map.get("contractTime"));
      list.add(Integer.parseInt(map.get("status").toString())==0?"暂存":"归档");
      rowList.add(list);
    }
    ExcelUtils.export("项目资料", rowList,titles,response);
    
  }
  
  @SysLogs(desc = "下载项目资料模板")
  @RequestMapping(value = "/template")
  public void  template(HttpServletRequest request,HttpServletResponse response){
    String[] titles= {"项目资料唯一标识","项目资料名称","项目唯一标识","项目名称","科室编码","科室名称","资料类型","总金额（元）","负责人","联系方式","签订时间"};
    ExcelUtils.export("项目资料模板", null,titles,response);
  }
  
  @SysLogs(desc = "导入项目资料")
  @RequestMapping(value = "/importExcel")
  public Map<String, Object>  importExcel(HttpServletRequest request,HttpServletResponse response){
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
    String token = request.getParameter(Constant.HEADER_TOKEN_STRING);
    Map<String, String> fieldsMap = new HashMap<>();
    fieldsMap.put("项目资料唯一标识", "id");
    fieldsMap.put("项目资料名称", "contractName");
    fieldsMap.put("项目唯一标识", "projectId");
    fieldsMap.put("项目名称", "projectName");
    fieldsMap.put("科室编码", "code");
    fieldsMap.put("科室名称", "orgName");
    fieldsMap.put("资料类型", "contractType");
    fieldsMap.put("总金额（元）", "contractTotal");
    fieldsMap.put("负责人", "contractLeader");
    fieldsMap.put("联系方式", "contractLeaderPhone");
    fieldsMap.put("签订时间", "contractTime");
    try {
      JSONArray excelData = ExcelUtils.getExcelData(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), fieldsMap,11);
      return contractService.save(excelData, token);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  
}
