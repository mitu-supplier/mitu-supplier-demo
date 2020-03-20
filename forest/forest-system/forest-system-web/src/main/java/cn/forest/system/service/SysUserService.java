package cn.forest.system.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.BCrypt;
import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.OrganizationRemote;
import cn.forest.system.remote.SysRoleRemote;
import cn.forest.system.remote.SysUserRemote;

@Service("sysUserService")
public class SysUserService {

  @Autowired
  private SysUserRemote sysUserRemote;
  
  @Autowired
  private OrganizationRemote organizationRemote;
  
  @Autowired
  private SysRoleRemote sysRoleRemote;
  
  @Autowired
  private RedisDao redisDao;
  

  public Map<String, Object> getList(Long page, Long pageSize,Long typeId,String name) {
    Object obj = sysUserRemote.getList(page, pageSize,typeId,name);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }

  public Map<String, Object> delete(Long id) {
    int delete = sysUserRemote.delete(id);
    if (delete > 0) {
      return ResultMessage.result("删除成功");
    }
    return ResultMessage.error("删除失败");
  }

  public Map<String, Object> add(Map<String, Object> map) {
    int add = sysUserRemote.add(map);
    if (add > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
    int delete = sysUserRemote.update(map);
    if (delete > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }

  public Map<String, Object> getById(Long id) {
    Map obj = (Map) sysUserRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
  public Map<String, Object> saveRole(Map<String, ?> map) {
    int saveRole = sysUserRemote.saveRole(map);
    if (saveRole > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }
  
  public Map<String, Object>  getOrgAll(){
    Object list = organizationRemote.list();
    if (list != null) {
      return ResultMessage.success(list);
    }
    return null;
  }
  
  public Map<String, Object>  getRoleAll(){
    Object list = sysRoleRemote.listAll();
    if (list != null) {
      return ResultMessage.success(list);
    }
    return null;
  }

  public Map<String, Object> getOnlineUser(HttpServletRequest request){
    Map<String, Object> user=new HashMap<String, Object>();
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    user.put("name", map.get("name"));
    if(Integer.parseInt(map.get("type").toString())==0) {
      Object orgByUserId = sysUserRemote.getOrgByUserId(Long.parseLong(map.get("id").toString()));
      user.put("org", orgByUserId);
    }
    user.put("roles", map.get("roles"));
    return ResultMessage.success(user);
  }
  
  public Map<String, Object> getUserByOrgId(Long page, Long pageSize,Long orgId,String name){
    
    Object userByOrgId = sysUserRemote.getUserByOrgId(page, pageSize, orgId, name);
    if (userByOrgId != null) {
      return ResultMessage.success(userByOrgId);
    }
    return null;
  }
}
