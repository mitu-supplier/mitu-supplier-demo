package cn.forest.lyj.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.ContractRemote;
import cn.forest.lyj.remote.ExpenditureRemote;
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.PlanRemote;
import cn.forest.lyj.remote.ProjectsRemote;
import cn.forest.lyj.remote.SysUserRemote;

@Service("homeService")
public class HomeService {
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private ProjectsRemote projectsRemote;
  
  @Autowired
  private ExpenditureRemote expenditureRemote;
  
  @Autowired
  private ContractRemote contractRemote;
  
  @Autowired
  private PlanRemote planRemote;
  
  @Autowired
  private SysUserRemote sysUserRemote;
  
  @Autowired
  private RedisDao redisDao;
  
  
  public Map<String, Object> getOrg(HttpServletRequest request) {
    
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean ks_flg=false;
    boolean gl_flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("GL_")>-1) {
        gl_flg=true;
        break;
      }
    }
    Long orgId=null;
    Object organizationList=null;
    if(gl_flg) {
      organizationList= organizationRemote.list();
    }else {
      List<Map> orgByUserId = (List) organizationRemote.getOrgByUserId(Long.parseLong(map.get("id").toString()));
      List list=new ArrayList<>();
      for (Map object : orgByUserId) {
        Map<String, Object> e=new HashMap<String, Object>();
        e.put("id", object.get("orgId"));
        e.put("name", object.get("orgName"));
        list.add(e);
      }
      organizationList = list;
    }
    if (organizationList != null) {
      return ResultMessage.success(organizationList);
    }
    return null;
  }
  
  public Map<String, Object> getProjectByOrgId(Long orgId,HttpServletRequest request,Integer year,Long parentId){
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    Long userId=Long.parseLong(map.get("id").toString());
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean ks_flg=false;
    boolean gl_flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        ks_flg=true;
        break;
      }
    }
    
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("GL_")>-1) {
        gl_flg=true;
        break;
      }
    }
    Object projectByOrgId=null;
    if(ks_flg||gl_flg) {
      projectByOrgId= projectsRemote.getProjectByOrgId(orgId,year,parentId);
    }else {
      projectByOrgId=projectsRemote.getUserAll(userId,orgId,year);
    }
     
    if (projectByOrgId != null) {
      return ResultMessage.success(projectByOrgId);
     }
     return null;
  }
  
  public Map<String, Object> getProjectsCount(Long id,Long orgId,Integer year,HttpServletRequest request){
    Map<String, Object> resultMap=new HashMap<String, Object>();
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    Long userId=Long.parseLong(map.get("id").toString());
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean ks_flg=false;
    boolean gl_flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        ks_flg=true;
        break;
      }
    }
    
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("GL_")>-1) {
        gl_flg=true;
        break;
      }
    }
    String orgIds="";
    
    if(gl_flg) {
      userId=null;
    }else {
      List<?> orgList = (List) organizationRemote.getOrgByUserId(userId);
      orgIds = orgList.stream().map(t ->((Map<String, Object>) t).get("orgId").toString()).collect(Collectors.joining(","));
      if(ks_flg) {
        userId=null;
      }
    }
    Long projectId=id;
    Map projectsCount = (Map) projectsRemote.getProjectsCount(orgId, userId, id, orgIds,year);
    BigDecimal project_total=new BigDecimal("0");
    if(projectsCount!=null) {
      project_total= new BigDecimal(projectsCount.get("total").toString());
      resultMap.put("number", projectsCount.get("number"));
      resultMap.put("total", projectsCount.get("total"));
    }
    
    
    Map expenditureCount = (Map) expenditureRemote.getExpenditureCount(userId, orgId, projectId,orgIds,year);
    BigDecimal expenditure_total=new BigDecimal("0");
    if(expenditureCount!=null) {
      expenditure_total= new BigDecimal(expenditureCount.get("total").toString());
    }
     
    
    resultMap.put("expenditure_total", expenditure_total);
    resultMap.put("subtract_total", project_total.subtract(expenditure_total));
    
    if (projectsCount != null) {
      return ResultMessage.success(resultMap);
     }
     return null;
  }
  
  
  public Map<String, Object> getProjects(Long page, Long pageSize,Long id,Long orgId,HttpServletRequest request,Integer year){
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    Long userId=Long.parseLong(map.get("id").toString());
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean ks_flg=false;
    boolean gl_flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        ks_flg=true;
        break;
      }
    }
    
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("GL_")>-1) {
        gl_flg=true;
        break;
      }
    }
    String orgIds="";
    if(gl_flg) {
      userId=null;
    }else {
      List<?> orgList = (List) organizationRemote.getOrgByUserId(userId);
      orgIds = orgList.stream().map(t ->((Map<String, Object>) t).get("orgId").toString()).collect(Collectors.joining(","));
      if(ks_flg) {
        userId=null;
      }
    }
    List<Map<String, Object>> selectData = (List<Map<String, Object>>) sysUserRemote.selectData("YJYZ", "YZ");
    Double num=null;
    if(!CollectionUtils.isEmpty(selectData)) {
      Map<String, Object> res = selectData.get(0);
      num = Double.parseDouble(res.get("name").toString());
      
    }
    
    Object projects = projectsRemote.getProjects(page, pageSize, orgId, userId, id,orgIds,year,num);
    if (projects != null) {
      return ResultMessage.success(projects);
     }
     return null;
  }
  
  
  public Map<String, Object> getOnlineUser(HttpServletRequest request,Long id,String type){
    Map<String, Object> user=new HashMap<String, Object>();
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    user.put("name", map.get("name"));
    Long userId=Long.parseLong(map.get("id").toString());
    if(Integer.parseInt(map.get("type").toString())==0) {
      if(!StringUtils.isEmpty(type)) {
          if(type.equals("project")) {
            Map obj = (Map) projectsRemote.getById(id);
            if(obj!=null) {
              userId=Long.parseLong(obj.get("userId").toString());
            }
          }
          if(type.equals("contract")) {
            Map obj = (Map) contractRemote.getById(id);
            if(obj!=null) {
              userId=Long.parseLong(obj.get("userId").toString());
            }
          }
          if(type.equals("plan")) {
            Map obj = (Map) planRemote.getById(id);
            if(obj!=null) {
              userId=Long.parseLong(obj.get("userId").toString());
            }
          }
          
          if(type.equals("expenditure")) {
            Map obj = (Map) expenditureRemote.getById(id);
            if(obj!=null) {
              userId=Long.parseLong(obj.get("userId").toString());
            }
          }
      }
      Object orgByUserId = sysUserRemote.getOrgByUserId(userId);
      user.put("org", orgByUserId);
    }
    return ResultMessage.success(user);
  }
  
}
