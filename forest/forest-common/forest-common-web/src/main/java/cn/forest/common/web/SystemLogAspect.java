package cn.forest.common.web;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.AddressIpUtil;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.web.remote.SysExceptionLogsRemote;
import cn.forest.common.web.remote.SysLogsRemote;
import cn.forest.common.web.util.SysLogs;

@Aspect
@Component
public class SystemLogAspect {
  
  @Autowired
  private SysLogsRemote  sysLogsRemote;
  @Autowired
  private SysExceptionLogsRemote sysExceptionLogsRemote;
  @Autowired
  private RedisDao redisDao;
  
  
  @Before("@annotation(cn.forest.common.web.util.SysLogs)")
  public void controllerAspect(JoinPoint joinPoint) throws Exception{
    HttpServletRequest request = getRequest();
    Map<String, Object> saveSysLogs = saveSysLogs(joinPoint, request);
    sysLogsRemote.add(saveSysLogs);
  }

  @AfterThrowing(value="@annotation(cn.forest.common.web.util.SysLogs)",throwing = "e")
  public void AfterThrowing(JoinPoint joinPoint,Throwable e)  throws Exception{
    HttpServletRequest request = getRequest();
    Map<String, Object> saveSysExceptionLogs = saveSysExceptionLogs(joinPoint, request, e);
    sysExceptionLogsRemote.add(saveSysExceptionLogs);
  }

  private Map<String, Object> saveSysExceptionLogs(JoinPoint joinPoint, HttpServletRequest request,Throwable e)
      throws ClassNotFoundException, Exception {
    Map<String, String> joinPointMap = joinPointMap(joinPoint, request);
    Class<?> targetClass = Class.forName(joinPointMap.get("cla"));
    String classDesc=targetClass.getAnnotation(SysLogs.class).desc();
    String desc = JsonUtil.toJson(e);
    return params(joinPoint, request, joinPointMap, classDesc, desc);
  }

  private Map<String, Object> params(JoinPoint joinPoint, HttpServletRequest request, Map<String, String> joinPointMap,
      String classDesc, String desc) {
    HashMap userInfoMap = (HashMap)redisDao.getValue(request.getHeader(Constant.HEADER_TOKEN_STRING));
    List<Map<String, Object>> roles = (List) userInfoMap.get("roles");
    String roleName="";
    if(roles!=null) {
      roleName= roles.stream().map(e ->e.get("roleName").toString()).collect(Collectors.joining(","));
    }
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("roleName", roleName);
    map.put("userId", Long.parseLong(userInfoMap.get("id").toString()));
    map.put("userName", userInfoMap.get("name").toString());
    map.put("loginName", userInfoMap.get("loginName").toString());
    map.put("modelName", classDesc);
    map.put("ip", AddressIpUtil.getIpAddr(request));
    map.put("url", request.getRequestURI());
    map.put("method", joinPointMap.get("method"));
    map.put("methodType", joinPointMap.get("methodType"));
    map.put("description", desc);
    if("POST".equals(joinPointMap.get("methodType"))) {
      map.put("args", JsonUtil.toJson(RequestMap.requestToMap(request)));
    }else {
      if(joinPoint.getArgs().length>0) {
        map.put("args", Arrays.toString(joinPoint.getArgs()));
      }
    }
    return map;
  }

  private HttpServletRequest getRequest() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return attributes.getRequest();
  }

  private Map<String, Object> saveSysLogs(JoinPoint joinPoint, HttpServletRequest request)
      throws ClassNotFoundException, Exception {
    Map<String, String> joinPointMap = joinPointMap(joinPoint, request);
    Class<?> targetClass = Class.forName(joinPointMap.get("cla"));
    String classDesc=targetClass.getAnnotation(SysLogs.class).desc();
    String desc = getControllerMethodDescription(joinPoint,targetClass, joinPointMap.get("method"));
    return params(joinPoint, request, joinPointMap, classDesc, desc);
  }
  
  
  public Map<String, String> joinPointMap(JoinPoint joinPoint,HttpServletRequest request){
    Map<String, String> map=new HashMap<String, String>();
    map.put("method", joinPoint.getSignature().getName());
    map.put("cla", joinPoint.getTarget().getClass().getName());
    map.put("methodType", request.getMethod());
    return map;
  }
  
  
  public static String getControllerMethodDescription(JoinPoint joinPoint,Class<?> targetClass,String methodName) throws Exception {
    Method[] methods = targetClass.getMethods();
    String description = "";
    for (Method method:methods) {
        if (method.getName().equals(methodName)){
            description = method.getAnnotation(SysLogs.class).desc();
            break;
        }
    }
    return description;
  }
  
}
