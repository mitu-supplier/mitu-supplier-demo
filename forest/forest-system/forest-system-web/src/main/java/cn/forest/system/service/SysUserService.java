package cn.forest.system.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysUserRemote;
import org.springframework.web.bind.annotation.RequestParam;

@Service("sysUserService")
public class SysUserService {

  @Autowired
  private SysUserRemote sysUserRemote;
  
  @Autowired
  private RedisDao redisDao;

  public Map<String, Object> getList(Long page, Long pageSize,String loginName, String name, String phone, String email) {
    Object obj = sysUserRemote.getList(page, pageSize, loginName, name, phone, email);
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

  public Map<String, Object> add(Map<String, ?> map) {
    String loginName = StringUtil.toString(map.get("loginName"));
    Object queryLoginName = sysUserRemote.getLoginName(loginName);
    if(queryLoginName != null){
      return ResultMessage.error("登录名已存在");
    }
    Object add = sysUserRemote.add(map);
    if(add != null){
      Map result = (Map) add;
      int num = Integer.parseInt(result.get(Constant.RESULT_NUM).toString());
      if(num > 0){
        return ResultMessage.result("添加成功");
      }
    }
    return ResultMessage.error("添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
    String loginName = StringUtil.toString(map.get("loginName"));
    Object queryLoginName = sysUserRemote.getLoginName(loginName);
    if(queryLoginName != null){
      Map userMap = (Map) queryLoginName;
      String id = StringUtil.toString(userMap.get("id"));
      if(id == null || !id.equals(StringUtil.toString(map.get("id")))){
        return ResultMessage.error("登录名已存在");
      }
    }
    int delete = sysUserRemote.update(map);
    if (delete > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }

  public Map<String, Object> getById(Long id) {
    Object obj = sysUserRemote.getById(id);
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

  public Map<String, Object> getOnlineUser(HttpServletRequest request){
    Map<String, Object> user=new HashMap<String, Object>();
    Map map = (Map) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    user.put("name", map.get("name"));
    return ResultMessage.success(user);
  }
}
