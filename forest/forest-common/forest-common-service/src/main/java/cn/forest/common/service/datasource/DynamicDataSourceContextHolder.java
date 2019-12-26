package cn.forest.common.service.datasource;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {

  private static final ThreadLocal<String> THREAD_LOCAL=new ThreadLocal<String>();
  
  
  //设置数据源
  public static void setDataSourceType(String dataSourceType) {
    THREAD_LOCAL.set(dataSourceType);
  }

  //获取数据源
  public static String getDataSourceType() {
      return THREAD_LOCAL.get();
  }

  //清除数据源
  public static void clearDataSourceType() {
    THREAD_LOCAL.remove();
  }

  
  
}
