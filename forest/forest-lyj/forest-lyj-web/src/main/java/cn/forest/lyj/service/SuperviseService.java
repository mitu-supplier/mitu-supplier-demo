package cn.forest.lyj.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.SuperviseRemote;

@Service("superviseService")
public class SuperviseService {
  
  @Autowired
  private SuperviseRemote superviseRemote;
  
  @Autowired
  private RedisDao redisDao;
  
  @Autowired
  private OrganizationRemote organizationRemote;
  
  public Map<String, Object> projectList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName,String leader) {
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        flg=true;
        break;
      }
    }
    Long orgId=null;
    if(flg) {
      orgId=Long.parseLong(map.get("typeId").toString());
    }   
    Object list = superviseRemote.projectsList(page,pageSize,null,projectName,orgName,leader,orgId);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }
  
  
  public Map<String, Object> contractList(Long page, Long pageSize,HttpServletRequest request,String contractName,String projectName,String orgName,String leader) {
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        flg=true;
        break;
      }
    }
    Long orgId=null;
    if(flg) {
      orgId=Long.parseLong(map.get("typeId").toString());
    }   
    Object list = superviseRemote.contractList(page,pageSize,null,contractName,projectName,orgName,leader,orgId);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }
  
  
  public Map<String, Object> planList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName) {
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        flg=true;
        break;
      }
    }
    Long orgId=null;
    if(flg) {
      orgId=Long.parseLong(map.get("typeId").toString());
    }   
    Object list = superviseRemote.planList(page, pageSize, null, projectName, orgName, orgId);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }
  
  
  public Map<String, Object> expenditureList(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName) {
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) map.get("roles");
    boolean flg=false;
    for (Map<String, Object> roleMap : roles) {
      String object = roleMap.get("roleCode").toString();
      if(object.indexOf("KS_")>-1) {
        flg=true;
        break;
      }
    }
    Long orgId=null;
    if(flg) {
      orgId=Long.parseLong(map.get("typeId").toString());
    }   
    Object list = superviseRemote.expenditureList(page, pageSize, null, projectName, orgName, orgId);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }
}
