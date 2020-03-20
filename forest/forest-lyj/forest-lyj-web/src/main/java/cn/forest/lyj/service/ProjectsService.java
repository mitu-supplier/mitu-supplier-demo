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
import org.springframework.util.StringUtils;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.ContractRemote;
import cn.forest.lyj.remote.ExpenditureRemote;
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.PlanRemote;
import cn.forest.lyj.remote.ProjectsRemote;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("projectsService")
public class ProjectsService {
  @Autowired
  private ProjectsRemote projectsRemote;
  
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private ExpenditureRemote expenditureRemote;
  
  @Autowired
  private ContractRemote contractRemote;
  
  @Autowired
  private PlanRemote planRemote;
  
  @Autowired
  private RedisDao redisDao;

  @SuppressWarnings("unchecked")
  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName,String leader) {
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
    String orgIds=null;
    
    if(gl_flg) {
      userId=null;
    }else {
      if(ks_flg) {
        List<?> orgList = (List) organizationRemote.getOrgByUserId(userId);
        orgIds = orgList.stream().map(t ->((Map<String, Object>) t).get("orgId").toString()).collect(Collectors.joining(","));
        userId=null;
      }
    }
    Object list = projectsRemote.list(page,pageSize,userId,projectName,orgName,leader,orgIds);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }

  public Map<String, Object> getById(Long id) {
      Object obj = projectsRemote.getById(id);
      if (obj != null) {
          return ResultMessage.success(obj);
      }
      return null;
  }

  public Map<String, Object> save(Map map,HttpServletRequest request) {
      Map user = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
      map.put("userId", Long.parseLong(user.get("id").toString()));
      int save = projectsRemote.save(map);
      return ResultMessage.result(save, "添加成功", "添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
      int update = projectsRemote.update(map);
      return ResultMessage.result(update, "修改成功", "修改失败");
  }

  public Map<String, Object> delete(Long id) {
      int delete = projectsRemote.delete(id);
      return ResultMessage.result(delete, "删除成功", "删除失败");
  }
  
  public Map<String, Object> getUserAll(HttpServletRequest request,Long orgId,String type,Long id) {
    Map user = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    Long userId = Long.parseLong(user.get("id").toString());
    if(!StringUtils.isEmpty(type)) {
      /*
       * if(type.equals("project")) { Map obj = (Map) projectsRemote.getById(id);
       * if(obj!=null) { userId=Long.parseLong(obj.get("userId").toString()); } }
       */
      if(type.equals("contract")&&id!=null) {
        Map obj = (Map) contractRemote.getById(id);
        if(obj!=null) {
          userId=Long.parseLong(obj.get("userId").toString());
        }
      }
      if(type.equals("plan")&&id!=null) {
        Map obj = (Map) planRemote.getById(id);
        if(obj!=null) {
          userId=Long.parseLong(obj.get("userId").toString());
        }
      }
      
      if(type.equals("expenditure")&&id!=null) {
        Map obj = (Map) expenditureRemote.getById(id);
        if(obj!=null) {
          userId=Long.parseLong(obj.get("userId").toString());
        }
      }
  }
    
    Object all = projectsRemote.getUserAll(userId, orgId,null);
    if (all != null) {
      return ResultMessage.success(all);
     }
  return null;
  }
  
  public Map<String, Object> getProjectsByParentId(Long parentId){
    Object object = projectsRemote.getProjectsByParentId(parentId);
    if (object != null) {
      return ResultMessage.success(object);
    }
    return null;
  }
  
  public Object exportList(HttpServletRequest request,String projectName,String orgName,String leader,String token) {
    Map map = (Map) redisDao.getValue(token);
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
    String orgIds=null;
    
    if(gl_flg) {
      userId=null;
    }else {
      if(ks_flg) {
        List<?> orgList = (List) organizationRemote.getOrgByUserId(userId);
        orgIds = orgList.stream().map(t ->((Map<String, Object>) t).get("orgId").toString()).collect(Collectors.joining(","));
        userId=null;
      }
    }
    Object list = projectsRemote.exportList(userId,projectName,orgName,leader,orgIds);
    
    return list;
  }
  
  
  public Map<String, Object> save(JSONArray jsonArray,String key) {
    Map user = (Map) redisDao.getValue(key); 
    int result_num=0;
    String meg="导入失败";
    if(jsonArray!=null) {
      List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
      Map<String, Object> map=null;
      for (int i = 0; i < jsonArray.size(); i++) {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        map=new HashMap<String, Object>();
        if(jsonObject.get("id")!=null&&!StringUtils.isEmpty(jsonObject.get("id").toString())) {
          Object byId = projectsRemote.getById(Long.parseLong(jsonObject.get("id").toString()));
          if(byId!=null) {
            map.put("id",Long.parseLong(jsonObject.get("id").toString()));
          }else {
            meg+=",未找到项目:"+jsonObject.get("id");
            break;
          }
        }
        if(jsonObject.get("orgCode")!=null&&!StringUtils.isEmpty(jsonObject.get("orgCode").toString())) {
          Map<String, Object> org = (Map<String, Object>) organizationRemote.code(jsonObject.get("orgCode").toString());
          if(org!=null) {
            map.put("orgName",org.get("name").toString());
            map.put("orgId",Long.parseLong(org.get("id").toString()));
          }else {
            meg+=",未找到部门:"+jsonObject.get("orgCode");
            break;
          }
        }
        map.put("projectName", jsonObject.get("projectName")==null?"":jsonObject.get("projectName").toString());
        map.put("budget", jsonObject.get("total")==null?"":new BigDecimal(jsonObject.get("total").toString()));
        map.put("projectLeader", jsonObject.get("projectUser")==null?"":jsonObject.get("projectUser").toString());
        map.put("leaderPhone", jsonObject.get("phone")==null?"":jsonObject.get("phone").toString());
        map.put("projectSource", jsonObject.get("source")==null?"":jsonObject.get("source").toString());
        map.put("year", jsonObject.get("year")==null?"":Integer.parseInt(jsonObject.get("year").toString()));
        map.put("userId",Long.parseLong(user.get("id").toString()));
        list.add(map);
      }
      
      for (Map nap : list) {
        if(nap.get("id")!=null) {
          result_num+=projectsRemote.update(nap);
        }else {
          result_num+=projectsRemote.save(nap);
        }
      }
    }
    
    return ResultMessage.result(result_num, "导入成功", meg);
  }
}
