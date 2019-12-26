package cn.forest.search.service.impl;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.forest.search.config.ElasticSearchClient;
import cn.forest.search.entity.MappingVo;
import cn.forest.search.service.SearchService;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

  @Autowired
  private ElasticSearchClient elasticSearchClient;

  @Override
  public Map getByDocId(String index, String type, String id) {
    TransportClient connection = elasticSearchClient.getConnection();
    return connection.prepareGet(index, type, id).get().getSourceAsMap();
  }


  @Override
  public boolean createindex(String index) {
    TransportClient connection = elasticSearchClient.getConnection();
    boolean indexIsExists = indexIsExists(index);
    if(!indexIsExists) {
      connection.admin().indices().prepareCreate(index).execute().actionGet();
      indexIsExists = indexIsExists(index);
    }
    return indexIsExists;
  }

  @Override
  public boolean indexIsExists(String index) {
    TransportClient connection = elasticSearchClient.getConnection();
    IndicesExistsResponse actionGet = connection.admin().indices()
        .exists(new IndicesExistsRequest(new String[] { index })).actionGet();
    return actionGet.isExists();
  }

  @Override
  public boolean isExistsType(String index, String type) {
    TransportClient connection = elasticSearchClient.getConnection();
    TypesExistsResponse actionGet = connection.admin().indices()
        .typesExists(new TypesExistsRequest(new String[] { index }, type)).actionGet();
    return actionGet.isExists();
  }

  @Override
  public boolean createMapping(String index, String type, List<MappingVo> mappingVos) {
    XContentBuilder mapping=null;
    TransportClient connection = elasticSearchClient.getConnection();
    boolean indexIsExists = indexIsExists(index);
    if(!indexIsExists) {
      indexIsExists = createindex(index);
    }
    if(indexIsExists) {
      try {
        mapping=XContentFactory.jsonBuilder();
        mapping.startObject().startObject("properties");
        if(!CollectionUtils.isEmpty(mappingVos)){
          for (MappingVo mappingVo : mappingVos) {
            String field = mappingVo.getField();
            Integer participle = mappingVo.getParticiple();
            String typeMapping = mappingVo.getType();
            if(participle==1){
              mapping.startObject(field)
                .field("type",typeMapping)
                .field("analyzer","ik_max_word")
                .field("search_analyzer", "ik_smart")
                .endObject();
            }else if(participle==2){
              mapping.startObject(field)
                  .field("type",typeMapping)
                  .field("analyzer","ik_max_word")
                  .field("search_analyzer", "ik_max_word")
                  .endObject();
            }else{
              mapping.startObject(field)
                  .field("type",typeMapping)
                  .endObject();
            }
          }
        }
        mapping.endObject().endObject();
        PutMappingRequest putMappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
        PutMappingResponse actionGet = connection.admin().indices().putMapping(putMappingRequest).actionGet();
        return actionGet.isAcknowledged();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return false;
      
    }
    
    return false;
  }

  @Override
  public boolean deleteIndex(String index) {
    TransportClient connection = elasticSearchClient.getConnection();
    boolean indexIsExists = indexIsExists(index);
    if(indexIsExists){
      DeleteIndexResponse actionGet = connection.admin().indices().prepareDelete(index).execute().actionGet();
      indexIsExists=actionGet.isAcknowledged();
    }
    return indexIsExists;
  }
  
  @Override
  public String delete(String index, String type, String id) {
    TransportClient connection = elasticSearchClient.getConnection();
    DeleteResponse deleteResponse = connection.prepareDelete(index, type, id).get();
    return deleteResponse.getId();
  }
  @Override
  public String insert(String index, String type, String json) {
    return save(index, type, null, json);
  }

  @Override
  public String insert(String index, String type, Map<String, Object> map) {
    return save(index, type, null, map);
  }

  @Override
  public String insert(String index, String type, String id, String json) {
    return save(index, type, id, json);
  }

  @Override
  public String insert(String index, String type, String id, Map<String, Object> map) {
    return save(index, type, id, map);
  }

  @Override
  public String update(String index, String type, String id, String json) {
    return update(index, type, id, json);
  }

  @Override
  public String update(String index, String type, String id, Map<String, Object> map) {
    return update(index, type, id, map);
  }

  @Override
  public SearchResponse select_and_match(String index, String type, Map<String, Object> map) {
    TransportClient connection = elasticSearchClient.getConnection();
    BoolQueryBuilder boolQueryBuilder = boolQueryBuilder(map);
    System.out.println(boolQueryBuilder.toString());
    SearchResponse searchResponse = connection.prepareSearch(index).setTypes(type).setQuery(boolQueryBuilder).get();
    return searchResponse;
  }

  
  
  @Override
  public SearchResponse select_and_match_aggs(String index, String type, Map<String, Object> map, String field,
      Boolean key, Boolean order) {
    return null;
  }
  
  public BoolQueryBuilder boolQueryBuilder(Map<String, Object> map) {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    map.forEach((k,v)->{
      MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(k, v);
      boolQuery.must(matchQuery);
    });
    return boolQuery;
  }
  
  public String save(String index,String type,String id,Object object) {
    TransportClient connection = elasticSearchClient.getConnection();
    IndexRequestBuilder prepareIndex=null;
    if(id==null) {
      prepareIndex= connection.prepareIndex(index, type);
    }else {
      prepareIndex= connection.prepareIndex(index, type, id);
    }
    IndexResponse indexResponse=null;
    if(object instanceof String) {
      indexResponse = prepareIndex.setSource(object.toString()).get();
    }else {
      Map map=(Map)object;
      indexResponse = prepareIndex.setSource(map).get();
    }
    return indexResponse.getId();
  }
  
  
  public String update(String index,String type,String id,Object object) {
    TransportClient connection = elasticSearchClient.getConnection();
    UpdateRequestBuilder prepareUpdate = connection.prepareUpdate(index, type, id);
    UpdateResponse updateResponse=null;
    if(object instanceof String) {
      updateResponse= prepareUpdate.setDoc(object.toString()).get();
    }else {
      Map map=(Map)object;
      updateResponse=prepareUpdate.setDoc(map).get();
    }
    return updateResponse.getId();
  }

  
  
  
}
