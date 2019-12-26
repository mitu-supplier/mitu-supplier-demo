package cn.forest.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfig {

  @Value("${spring.es.host}")
  private String hostName;

  @Value("${spring.es.port}")
  private int port;
  
  @Value("${spring.es.clusterName}")
  private String clusterName;
  
  @Value("${spring.es.enable}")
  private boolean enable;
  
  @Bean
  public ElasticSearchClient getElasticSearchClient() {
    ElasticSearchClient elasticSearchClient = new ElasticSearchClient(hostName, port, clusterName,enable);
    return elasticSearchClient;
  }
}
