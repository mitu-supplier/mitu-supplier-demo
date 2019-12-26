package cn.forest.common.service.datasource;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
public class DatasourceAspect {

  private static String[] METHOD_PREFIX= {"get","find","select"};
  @Value("${spring.datasource.r-w-separation}")
  private Boolean rWSeparation;
  @Pointcut("execution(* cn.forest.system.service.server.*.*(..))")
  public void dataSource(){
      
  }
  
  @Before("dataSource()")
  public void controllerAspect(JoinPoint joinPoint) throws Exception{
    if(rWSeparation) {
      String methName = joinPoint.getSignature().getName();
      List<String> methodList = Arrays.asList(METHOD_PREFIX);
      boolean flg=false;
      for (String string : methodList) {
        if(methName.startsWith(string)) {
          System.out.println("slave1");
          DynamicDataSourceContextHolder.setDataSourceType("slave1");
          flg=true;
          break;
        }
      }
      if(!flg) {
        System.out.println("dataSource");
        DynamicDataSourceContextHolder.setDataSourceType("dataSource");
      }
    }
  }
  @After("dataSource()")
  public void restoreDataSource(JoinPoint point) {
    if(rWSeparation) {
      DynamicDataSourceContextHolder.clearDataSourceType();
    }
  }
}
