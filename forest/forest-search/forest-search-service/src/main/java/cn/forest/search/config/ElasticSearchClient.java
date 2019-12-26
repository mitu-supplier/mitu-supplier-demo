package cn.forest.search.config;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchClient {
  
  private String hostName;
  
  private int port;
  
  private String clusterName;
  
  private boolean enable;

  TransportClient client = null;
  
  @SuppressWarnings("resource")
  public ElasticSearchClient(String hostName,int port,String clusterName,boolean enable) {
    this.hostName = hostName;
    this.port = port;
    this.clusterName = clusterName;
    this.enable = enable;
    try {
      Settings build = Settings.builder().put("cluster.name", clusterName).build();
      client = new PreBuiltTransportClient(build).addTransportAddress(new TransportAddress(InetAddress.getByName(hostName),port));
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  public TransportClient getConnection() {
    if (client == null) {
      synchronized (ElasticSearchClient.class) {
        if (client == null) {
          new ElasticSearchClient(hostName, port, clusterName,enable);
        }
      }
    }
    return client;
  }
  
}
