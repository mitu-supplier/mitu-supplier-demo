package cn.forest.common.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("springUtil")
@Lazy(false)
public class SpringUtil implements ApplicationContextAware, DisposableBean{
  /** applicationContext */
  private static ApplicationContext applicationContext;
  /**
   * 不可实例化
   */
  /*private SpringUtil() {
  }*/
  public void setApplicationContext(ApplicationContext applicationContext) {
    SpringUtil.applicationContext = applicationContext;
  }
  public void destroy() throws Exception {
    applicationContext = null;
  }
  /**
   * 获取applicationContext
   * 
   * @return applicationContext
   */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }
  /**
   * 获取实例
   * 
   * @param name
   *            Bean名称
   * @return 实例
   */
  public static Object getBean(String name) {
    Assert.hasText(name);
    return applicationContext.getBean(name);
  }
  /**
   * 获取实例
   * 
   * @param name
   *            Bean名称
   * @param requiredType
   *            Bean类型
   * @return 实例
   */
  public static <T> T getBean( Class<T> requiredType) {
    Assert.notNull(requiredType);
    return applicationContext.getBean(requiredType);
  }
  

}
