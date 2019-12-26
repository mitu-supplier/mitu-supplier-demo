package cn.forest.search.service;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;

import cn.forest.search.entity.MappingVo;

public interface SearchService {

  /**
   * 查询一条
   * @param index 索引
   * @param type  类型
   * @param doc  文档id
   * @return
   */
  public Map getByDocId(String index,String type,String doc);
  
  
  /**
   * 创建索引
   * @param index  索引名称
   * @param type  类型
   * @return
   */
  public boolean createindex(String index);
  
  /**
         * 判断索引存不存在
   * @param index 索引
   * @return 存在true，不存在false
   */
  public boolean indexIsExists(String index);
  
  /**
   * 判断指定的索引的类型是否存在
   * @param index  索引
   * @param type  类型
   * @return  存在：true; 不存在：false;
   */
  public boolean isExistsType(String index,String type);
  
  
  /**
   * 创建mapping 映射
   * @param index 索引
   * @param type 类型
   * @param mappingVos 映射实体
   * @return
   */
  public boolean createMapping(String index,String type,List<MappingVo> mappingVos);
  
  /**
   * 删除索引
   * @param index 索引
   * @return true 删除成功，false 删除失败
   */
  public boolean deleteIndex(String index);
  
  /**
        * 删除一条数据
   * @param index 索引
   * @param type 类别
   * @param id  文档id
   * @return true 删除成功，false 删除失败
   */
  public String delete(String index,String type,String id);
  
  
  /**
   * 根据索引，类别，json数据添加
   * @param index 索引
   * @param type 类型
   * @param json  添加的json传
   */
  public String insert(String index,String type,String json); 
  
  /**
   * 根据索引，类别，map数据添加
   * @param index 索引
   * @param type 类型
   * @param map 添加的map
   * @return
   */
  public String insert(String index,String type,Map<String, Object> map);
  
  /**
   * 根据索引，类别，文档id，json数据添加
   * @param index 索引
   * @param type 类型
   * @param id 文档id
   * @param json  添加的json串
   */
  public String insert(String index,String type,String id,String json); 
  
  /**
   * 根据索引，类别，文档id，map数据添加
   * @param index 索引
   * @param type 类型
   * @param id 文档id
   * @param map 添加的map
   * @return
   */
  public String insert(String index,String type,String id,Map<String, Object> map);
  
  /**
   * 根据索引，类别，文档id，json数据修改
   * @param index 索引
   * @param type 类型
   * @param id 文档id
   * @param json 修改的json串
   * @return
   */
  public String update(String index,String type,String id,String json);
  
  /**
   * 根据索引，类别，文档id，map数据修改
   * @param index 索引
   * @param type 类型
   * @param id 文档id
   * @param map 修改的map
   * @return
   */
  public String update(String index,String type,String id,Map<String, Object> map);
  
  
  /**
   * 模糊并且查询
   * 
   * GET spe/product/_search
      {"query":{"bool":{"must":[{"match":{"emallId":11179}},{"match":{"brandId":1973}}]}}}
   * 
   * 
   * @param index 索引
   * @param type 类型
   * @param queryMap 查询的map key=字段 value=值
   */
  public SearchResponse select_and_match(String index,String type,Map<String, Object> map);
  
  /**
   * 
   * @param index 索引
   * @param type 类型
   * @param map 查询的map key=字段 value=值
   * @param field 分组的字段
   * @param key  是否根据key值排序 null：不排序，false：count 排序，true：key排序
   * @param order 排序方式 null：无，false ：倒序，true：升序
   * @return
   */
  public SearchResponse select_and_match_aggs(String index,String type,Map<String, Object> map,String field,Boolean key,Boolean order);
  
  
  
  
}
