package cn.forest.login.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.forest.common.util.*;
import cn.forest.login.remote.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.forest.commom.jwt.TokenAuthenticationService;
import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;

@Service("loginService")
public class LoginService {

    @Autowired
    private LoginRemote loginRemote;

    @Autowired
    private SysLoginLogsRemote sysLoginLogsRemote;

    @Autowired
    private SysUserRemote sysUserRemote;

    @Autowired
    private SysRoleRemote sysRoleRemote;

    @Autowired
    private SysPermissionsRemote sysPermissionsRemote;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SuppliersRemote suppliersRemote;

    @Value("${login-private-key}")
    private String privateKey;

    public Map<String, Object> getUser(String loginName, String password, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtil.isBlank(loginName) || StringUtil.isBlank(password)) {
            return ResultMessage.error("用户或密码不能为空");
        }
        Object user = loginRemote.getUser(loginName.trim());
        if (user != null) {
            HashMap object = JsonUtil.toObject(JsonUtil.toJson(user), HashMap.class);
            String pass = StringUtil.toString(object.get("password"));
            try {
                password = RSAEncrypt.decrypt(privateKey, password.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pass != null && BCrypt.checkpw(password, pass)) {
                if("0".contentEquals(object.get("isStatus").toString())) {
              
                  String token = TokenAuthenticationService.addAuthentication(StringUtil.toString(object.get("loginName")));
                  HashMap userInfoMap = selectUserRoles(object);
                  addSysLoginLogs(object, request, userInfoMap);
                  updateUser(object, request);
                  Object type = userInfoMap.get("type");
                  Object typeId = userInfoMap.get("typeId");
                  if (type != null && Integer.parseInt(type.toString()) == 1) {
                      // 供应商
                      if (typeId == null) {
                          return ResultMessage.error("未查到供应商信息");
                      }
                      Object supplier = suppliersRemote.getById(Long.parseLong(typeId.toString()));
                      if (supplier == null) {
                          return ResultMessage.error("未查到供应商信息");
                      }
                      resultMap.put("isSupplier", 1);
                      JsonNode supplierJsonNode = JsonUtil.readTree(supplier);
                      int status = supplierJsonNode.path("status").asInt();
                      userInfoMap.put("supplierStatus", status);
                      resultMap.put("status", status);
                      if (status == 0) {
                          // 待审核
                          resultMap.put("message", "您的账号24h完成审核，请耐心等待");
                      } else if (status == 2) {
                          // 审核不通过
                          resultMap.put("message", "商户信息有误，请确认后重新提交");
                      } else if (status == -1) {
                          // 未填写完毕
                          resultMap.put("registerStep", supplierJsonNode.path("registerStep").asInt());
                          resultMap.put("message", "商户信息未填写完毕，请继续填写");
                      }
                  } else {
                      resultMap.put("isSupplier", 0);
                  }
               
                // 查询当前用户的角色权限
                redisDao.setKey(token, userInfoMap, TokenAuthenticationService.EXPIRATIONTIME);
                resultMap.put("token", token);
                return ResultMessage.success(resultMap);
              }else {
                return ResultMessage.error("用户被禁用，请联系管理员！");
              }
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

    public int addSysLoginLogs(Map map, HttpServletRequest request, HashMap hashMap) {
        List<Map<String, Object>> roles = (List) hashMap.get("roles");
        String roleName = "";
        if (roles != null) {
            roleName = roles.stream().map(e -> e.get("roleName").toString()).collect(Collectors.joining(","));
        }
        Map<String, Object> logsMap = new HashMap<String, Object>();
        logsMap.put("userId", map.get("id"));
        logsMap.put("ip", AddressIpUtil.getIpAddr(request));
        logsMap.put("loginName", map.get("loginName"));
        logsMap.put("userName", map.get("name"));
        logsMap.put("roleName", roleName);
        return sysLoginLogsRemote.add(logsMap);
    }

    public HashMap selectUserRoles(HashMap map) {
        Object id = map.get("id");
        if (id != null) {
            Long userId = Long.valueOf(String.valueOf(id));
            List sysRoleList = (List) sysRoleRemote.getUserRole(userId);
            map.put("roles", sysRoleList);
            Object permissionsList = sysPermissionsRemote.getPermissionByUserId(userId);
            map.put("permissions", permissionsList);
        }
        return map;
    }

    public Map<String, Object> loginOut(HttpServletRequest request) {
        boolean bool = redisDao.isNotKey(request.getHeader(Constant.HEADER_TOKEN_STRING));
        if (bool) {
            redisDao.deleteKey(request.getHeader(Constant.HEADER_TOKEN_STRING));
        }
        return ResultMessage.success("注销成功");
    }

    public Map<String, Object> editPass(String oldPass, String newPass, HttpServletRequest request) {
        HashMap userInfoMap = (HashMap) redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
        Object userId = userInfoMap.get("id");
        Map user = (Map) sysUserRemote.getById(Long.parseLong(userId.toString()));
        String pwd = user.get("password").toString();
        if (BCrypt.checkpw(oldPass, pwd)) {
            user.put("password", BCrypt.hashpw(newPass, BCrypt.gensalt()));
            int update = sysUserRemote.update(user);
            if (update > 0) {
                return ResultMessage.success("密码修改成功！");
            }
            return ResultMessage.error("密码修改失败！");
        } else {
            return ResultMessage.error("旧密码不正确！");
        }

    }
}
