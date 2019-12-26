package cn.forest.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestMap {
  public static Map<String, Object> requestToMap(HttpServletRequest request) {
    Enumeration<String> parameterNames = request.getParameterNames();
    Hashtable<String, Object> map = new Hashtable<String, Object>();
    while (parameterNames.hasMoreElements()) {
      String nextElement = parameterNames.nextElement();
      map.put(nextElement, request.getParameter(nextElement));
    }
    return map;
  }
}
