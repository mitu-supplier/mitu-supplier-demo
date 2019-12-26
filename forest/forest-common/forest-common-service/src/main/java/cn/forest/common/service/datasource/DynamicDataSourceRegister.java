package cn.forest.common.service.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

  // 指定默认数据源
  private static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
  // 默认数据源
  private DataSource defaultDataSource;
  // 用户自定义数据源
  private Map<String, DataSource> slaveDataSources = new HashMap<>();

  @Override
  public void setEnvironment(Environment environment) {
    initDefaultDataSource(environment);
    initslaveDataSources(environment);
  }

  private void initDefaultDataSource(Environment env) {
    // 读取主数据源
    Map<String, Object> dsMap = new HashMap<>();
    dsMap.put("driver", env.getProperty("spring.datasource.master.driver-class-name"));
    dsMap.put("url", env.getProperty("spring.datasource.master.url"));
    dsMap.put("username", env.getProperty("spring.datasource.master.username"));
    dsMap.put("password", env.getProperty("spring.datasource.master.password"));
    defaultDataSource = buildDataSource(dsMap);
  }

  private void initslaveDataSources(Environment env) {
    // 读取配置文件获取更多数据源
    String dsPrefixs = env.getProperty("spring.datasource.names");
    for (String dsPrefix : dsPrefixs.split(",")) {
      // 多个数据源
      Map<String, Object> dsMap = new HashMap<>();
      dsMap.put("driver", env.getProperty("spring.datasource." + dsPrefix + ".driver-class-name"));
      dsMap.put("url", env.getProperty("spring.datasource." + dsPrefix + ".url"));
      dsMap.put("username", env.getProperty("spring.datasource." + dsPrefix + ".username"));
      dsMap.put("password", env.getProperty("spring.datasource." + dsPrefix + ".password"));
      DataSource ds = buildDataSource(dsMap);
      slaveDataSources.put(dsPrefix, ds);
    }
  }

  public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
    try {
      Object type = dataSourceMap.get("type");
      if (type == null) {
        type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
      }
      Class<? extends DataSource> dataSourceType;
      dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
      String driverClassName = dataSourceMap.get("driver").toString();
      String url = dataSourceMap.get("url").toString();
      String username = dataSourceMap.get("username").toString();
      String password = dataSourceMap.get("password").toString();
      // 自定义DataSource配置
      DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
          .username(username).password(password).type(dataSourceType);
      return factory.build();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    // 添加默认数据源
    targetDataSources.put("dataSource", this.defaultDataSource);
    // 添加其他数据源
    targetDataSources.putAll(slaveDataSources);

    // 创建DynamicDataSource
    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    beanDefinition.setBeanClass(DynamicDataSource.class);
    beanDefinition.setSynthetic(true);
    MutablePropertyValues mpv = beanDefinition.getPropertyValues();
    mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
    mpv.addPropertyValue("targetDataSources", targetDataSources);
    // 注册 - BeanDefinitionRegistry
    registry.registerBeanDefinition("dataSource", beanDefinition);
  }

}
