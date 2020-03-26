package cn.forest.lyj.service;

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
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.ProjectsRemote;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("contractService")
public class ContractService {

  @Autowired
  private ContractRemote contractRemote;
  
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private ProjectsRemote projectsRemote;
  
  @Autowired
  private RedisDao redisDao;

  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String contractName,String projectName,String orgName,String leader) {
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
    
    Object list = contractRemote.list(page,pageSize,userId,contractName,projectName,orgName,leader,orgIds);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }

  public Map<String, Object> getById(Long id) {
      Object obj = contractRemote.getById(id);
      if (obj != null) {
          return ResultMessage.success(obj);
      }
      return null;
  }

  public Map<String, Object> save(Map map,HttpServletRequest request) {
      Map user = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
      map.put("userId", Long.parseLong(user.get("id").toString()));
      int save = contractRemote.save(map);
      return ResultMessage.result(save, "添加成功", "添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
      int update = contractRemote.update(map);
      return ResultMessage.result(update, "修改成功", "修改失败");
  }

  public Map<String, Object> delete(Long id) {
      int delete = contractRemote.delete(id);
      return ResultMessage.result(delete, "删除成功", "删除失败");
  }
  
  public Object exportList(HttpServletRequest request,String contractName,String projectName,String orgName,String leader,String token) {
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
    
    Object list = contractRemote.list(userId,contractName,projectName,orgName,leader,orgIds);
    if (list != null) {
        return list;
    }
    return null;
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
          Object byId = contractRemote.getById(Long.parseLong(jsonObject.get("id").toString()));
          if(byId!=null) {
            map.put("id",Long.parseLong(jsonObject.get("id").toString()));
          }else {
            meg+=",未找到项目资料:"+jsonObject.get("id");
            break;
          }
        }else {
          map.put("userId",Long.parseLong(user.get("id").toString()));
        }
        map.put("contractName",jsonObject.get("contractName").toString());
        if(jsonObject.get("projectId")!=null&&!StringUtils.isEmpty(jsonObject.get("projectId").toString())) {
          Map<String, Object> byId = (Map<String, Object>) projectsRemote.getById(Long.parseLong(jsonObject.get("projectId").toString()));
          if(byId!=null) {
            map.put("projectId",Long.parseLong(jsonObject.get("projectId").toString()));
            map.put("projectName",byId.get("projectName").toString());
          }else {
            meg+=",未找到项目:"+jsonObject.get("projectId");
            break;
          }
        }
        if(jsonObject.get("code")!=null&&!StringUtils.isEmpty(jsonObject.get("code").toString())) {
          Map<String, Object> org = (Map<String, Object>) organizationRemote.code(jsonObject.get("code").toString());
          if(org!=null) {
            map.put("orgName",org.get("name").toString());
            map.put("orgId",Long.parseLong(org.get("id").toString()));
          }else {
            meg+=",未找到部门:"+jsonObject.get("code");
            break;
          }
        }
        map.put("contractTotal", jsonObject.get("contractTotal")==null?"":jsonObject.get("contractTotal").toString());
        map.put("contractTime", jsonObject.get("contractTime")==null?"":jsonObject.get("contractTime").toString());
        map.put("contractLeader", jsonObject.get("contractLeader")==null?"":jsonObject.get("contractLeader").toString());
        map.put("contractLeaderPhone", jsonObject.get("contractLeaderPhone")==null?"":jsonObject.get("contractLeaderPhone").toString());
        if(jsonObject.get("contractType")!=null) {
           if(jsonObject.get("contractType").toString().equals("项目合同")) {
             map.put("contractType", 0);
           }
           if(jsonObject.get("contractType").toString().equals("其他资料")) {
             map.put("contractType", 1);
           }
        }
        list.add(map);
      }
      for (Map nap : list) {
        if(nap.get("id")!=null) {
          result_num+=contractRemote.update(nap);
        }else {
          result_num+=contractRemote.save(nap);
        }
      }
    } 
    return ResultMessage.result(result_num, "导入成功", meg);
  }
  
  
}
