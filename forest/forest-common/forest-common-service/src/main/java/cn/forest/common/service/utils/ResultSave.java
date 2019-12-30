package cn.forest.common.service.utils;

import java.util.HashMap;
import java.util.Map;

import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;

public class ResultSave {

  
  public static Object resultSave(int num,Object object) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put(Constant.RESULT_NUM, num);
    map.put(Constant.RESULT, JsonUtil.toJson(object));
    return map;
  }
  
}
