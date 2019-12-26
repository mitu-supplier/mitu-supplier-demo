package cn.forest.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.commom.jwt.TokenAuthenticationService;
import cn.forest.commom.redis.RedisDao;
import cn.forest.common.util.AddressIpUtil;
import cn.forest.common.util.DateUtil;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.login.remote.LoginRemote;
import cn.forest.login.remote.SysLoginLogsRemote;
import cn.forest.login.remote.SysUserRemote;

@Service("loginService")
public class LoginService {

  @Autowired
  private LoginRemote loginRemote;
  @Autowired
  private SysLoginLogsRemote sysLoginLogsRemote;
  @Autowired
  private SysUserRemote sysUserRemote;
  @Autowired
  private RedisDao redisDao;

  public Map<String, Object> getUser(String loginName, String password, HttpServletRequest request) {
    if (StringUtil.isBlank(loginName) || StringUtil.isBlank(password)) {
      return ResultMessage.error("用户或密码不能为空");
    }
    Object user = loginRemote.getUser(loginName.trim());
    if (user != null) {
      HashMap object = JsonUtil.toObject(JsonUtil.toJson(user), HashMap.class);
      String pass = StringUtil.toString(object.get("password"));
      if (pass != null && pass.equals(password.trim())) {
        String token = TokenAuthenticationService.addAuthentication(StringUtil.toString(object.get("loginName")));
        addSysLoginLogs(object, request);
        updateUser(object, request);
        redisDao.setKey(token, user);
        return ResultMessage.success(token);
      } else {
        return ResultMessage.error("用户或密码错误");
      }
    } else {
      return ResultMessage.error("用户或密码名错误");
    }
  }

  public int updateUser(Map map, HttpServletRequest request) {
    map.put("ip", AddressIpUtil.getIpAddr(request));
    map.put("loginTime", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
    return sysUserRemote.update(map);
  }

  public int addSysLoginLogs(Map map, HttpServletRequest request) {
    Map<String, Object> logsMap = new HashMap<String, Object>();
    logsMap.put("userId", map.get("id"));
    logsMap.put("ip", AddressIpUtil.getIpAddr(request));
    logsMap.put("loginName", map.get("loginName"));
    logsMap.put("userName", map.get("name"));
    return sysLoginLogsRemote.add(logsMap);
  }

}
