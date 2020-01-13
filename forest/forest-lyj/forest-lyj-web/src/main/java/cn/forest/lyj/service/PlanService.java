package cn.forest.lyj.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.OrganizationRemote;
import cn.forest.lyj.remote.PlanRemote;

@Service("planService")
public class PlanService {

  @Autowired
  private PlanRemote planRemote;
  
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private RedisDao redisDao;

  public Map<String, Object> list(Long page, Long pageSize,HttpServletRequest request,String projectName,String orgName) {
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
    
    Object list = planRemote.list(page,pageSize,userId,projectName,orgName,orgIds);
    if (list != null) {
        return ResultMessage.success(list);
    }
    return null;
  }

  public Map<String, Object> getById(Long id) {
      Object obj = planRemote.getById(id);
      if (obj != null) {
          return ResultMessage.success(obj);
      }
      return null;
  }

  public Map<String, Object> save(Map map,HttpServletRequest request) {
      Map user = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
      map.put("userId", Long.parseLong(user.get("id").toString()));
      int save = planRemote.save(map);
      return ResultMessage.result(save, "添加成功", "添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
      int update = planRemote.update(map);
      return ResultMessage.result(update, "修改成功", "修改失败");
  }

  public Map<String, Object> delete(Long id) {
      int delete = planRemote.delete(id);
      return ResultMessage.result(delete, "删除成功", "删除失败");
  }
  
}
