package cn.forest.lyj.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.ExpenditureRemote;
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.ProjectsRemote;

@Service("homeService")
public class HomeService {
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private ProjectsRemote projectsRemote;
  
  @Autowired
  private ExpenditureRemote expenditureRemote;
  
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
        orgId=Long.parseLong(map.get("typeId").toString());
        List list=new ArrayList<>();
        Object byId = organizationRemote.getById(orgId);
        list.add(byId);
        organizationList=list;
    }
    if (organizationList != null) {
      return ResultMessage.success(organizationList);
    }
    return null;
  }
  
  public Map<String, Object> getProjectByOrgId(Long orgId,HttpServletRequest request){
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
      projectByOrgId= projectsRemote.getProjectByOrgId(orgId);
    }else {
      projectByOrgId=projectsRemote.getUserAll(userId);
    }
     
    if (projectByOrgId != null) {
      return ResultMessage.success(projectByOrgId);
     }
     return null;
  }
  
  public Map<String, Object> getProjectsCount(Long id,Long orgId,HttpServletRequest request){
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
    if(id==null&&orgId==null) {
      if(gl_flg) {
        userId=null;
      }else {
        if(ks_flg) {
          userId=null;
          orgId=Long.parseLong(map.get("typeId").toString());
        }
      }
    }else {
       if(id==null) {
         if(gl_flg||ks_flg) {
           userId=null;
         }
       }else {
         orgId=null;
         userId=null;
       }
    }
    Long projectId=id;
    Map projectsCount = (Map) projectsRemote.getProjectsCount(orgId, userId, id);
    BigDecimal project_total=new BigDecimal("0");
    if(projectsCount!=null) {
      project_total= new BigDecimal(projectsCount.get("total").toString());
      resultMap.put("number", projectsCount.get("number"));
      resultMap.put("total", projectsCount.get("total"));
    }
    
    
    Map expenditureCount = (Map) expenditureRemote.getExpenditureCount(userId, orgId, projectId);
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
  
  
  public Map<String, Object> getProjects(Long page, Long pageSize,Long id,Long orgId,HttpServletRequest request){
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
    if(id==null&&orgId==null) { //第一次加载
      if(gl_flg) {// 大权限
        userId=null;
      }else { 
        if(ks_flg) { //小权限查看自己部门
          userId=null;
          orgId=Long.parseLong(map.get("typeId").toString());
        }
        //否则自己
      }
    }else {
       if(id==null) {//部门
         if(gl_flg||ks_flg) {
           userId=null;
         }
       }else {//否则
         orgId=null;
         userId=null;
       }
    }
    Object projects = projectsRemote.getProjects(page, pageSize, orgId, userId, id);
    if (projects != null) {
      return ResultMessage.success(projects);
     }
     return null;
    
    
  }
  
}
