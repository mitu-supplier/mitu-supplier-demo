package cn.forest.search.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.search.entity.MappingVo;
import cn.forest.search.service.SearchService;

@RestController
@RequestMapping("search")
public class SearchAction {
  
   @Autowired
   private SearchService searchService;
   
   @RequestMapping("/is_index")
   public Object is_index(String index) {
     return searchService.indexIsExists(index);
   }
   @RequestMapping("/is_index_type")
   public Object is_index_type(String index,String type) {
     return searchService.isExistsType(index, type);
   }
   
   @RequestMapping("/create_index")
   public Object create_index() {
     String index="spe";
     String type="product";
     List<MappingVo> mappingVo= new ArrayList<MappingVo>();
     MappingVo mappingVo2=new MappingVo();
     mappingVo2.setField("name");
     mappingVo2.setType("text");
     mappingVo2.setParticiple(2);
     mappingVo.add(mappingVo2);
     
     mappingVo2=new MappingVo();
     mappingVo2.setField("model");
     mappingVo2.setType("text");
     mappingVo2.setParticiple(2);
     mappingVo.add(mappingVo2);
     
     mappingVo2=new MappingVo();
     mappingVo2.setField("brandName");
     mappingVo2.setType("text");
     mappingVo2.setParticiple(2);
     mappingVo.add(mappingVo2);
     
     mappingVo2=new MappingVo();
     mappingVo2.setField("id");
     mappingVo2.setType("keyword");
     mappingVo2.setParticiple(3);
     mappingVo.add(mappingVo2);
     return searchService.createMapping(index, type, mappingVo);
   }
   
   @RequestMapping("/save")
   public Object save() {
     Map<String, Object> map=new HashMap<String, Object>();
     map.put("id", "2");
     map.put("name", "云儿宝贝");
     map.put("model", "YEBB45522023324");
     map.put("brandName", "母婴外出用品");
     
     String insert = searchService.insert("spe", "product", map);
     return insert;
   }
   
   @RequestMapping("/deleteIndex")
   public Object deleteIndex() {
     boolean deleteIndex = searchService.deleteIndex("spe");
     return deleteIndex;
   }
   
   @RequestMapping("/delete")
   public Object delete() {
     return searchService.delete("spe", "product", "LD7Y22wBsT1THS8chcif");
   }
   
   @RequestMapping("/searchMatch")
   public Object searchMatch() {
     Map<String, Object> map=new HashMap<String, Object>();
     map.put("name", "云儿");
     map.put("brandName", "母婴外出用品");
     SearchResponse select_and_match = searchService.select_and_match("spe", "product", map);
     SearchHits hits = select_and_match.getHits();
     SearchHit[] hits2 = hits.getHits();
     List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
     if(hits2.length>0) {
       for(SearchHit sh:hits2) {
         list.add(sh.getSourceAsMap());
       }
     }
     return  list;
   }
}
