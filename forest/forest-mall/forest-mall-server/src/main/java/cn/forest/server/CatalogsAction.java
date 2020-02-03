package cn.forest.server;

import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Catalogs;
import cn.forest.mall.mapper.CatalogsMapper;
import cn.forest.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
public class CatalogsAction {

    @Autowired
    private CatalogsService catalogsService;
    
    @Autowired
    private CatalogsMapper catalogsMapper;

    
    @RequestMapping("/getAll")
    public Object getAll() {
        QueryWrapper<Catalogs> queryWrapper=new QueryWrapper<Catalogs>();
        queryWrapper.orderByAsc(new String[] {"id"});
        return catalogsMapper.selectList(queryWrapper);
    }
    
    @RequestMapping("/getChildrenByLevel")
    public Object getChildrenByLevel(Long id) {
        return catalogsService.getChildrenByLevel(id);
    }

    @RequestMapping("/getById")
    public Object getById(Long id) {
        return catalogsService.getById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody Catalogs catalogs){
        return catalogsService.saveCatalogs(catalogs);
    }

    @RequestMapping("/update")
    public int update(@RequestBody Catalogs catalogs){
        return catalogsService.updateCatalogs(catalogs);
    }
    
    @RequestMapping("/delete")
    public int delete(Long id){
        Catalogs catalogs = catalogsMapper.selectById(id);
        int delete = catalogsMapper.deleteById(id);
        if(delete>0) {
          QueryWrapper<Catalogs> queryWrapper=new QueryWrapper<Catalogs>();
          queryWrapper.eq("parent_id", catalogs.getParentId());
          List<Catalogs> selectList = catalogsMapper.selectList(queryWrapper);
          if(CollectionUtils.isEmpty(selectList)) {
             Catalogs selectById = catalogsMapper.selectById(catalogs.getParentId());
             selectById.setIsParent("false");
             catalogsMapper.updateById(selectById);
          }
        }
        return delete;
    }

    @RequestMapping("/disable")
    public int disable(Long id){
        boolean disable = false;
        Catalogs catalogs = catalogsService.getById(id);
        if(catalogs != null){
            catalogs.setUsable(0);
            disable = catalogsService.updateById(catalogs);
        }
        if(disable){
            return 1;
        }
        return 0;
    }

    @RequestMapping("/getAllUseable")
    public Object getAllUseable() {
        QueryWrapper<Catalogs> queryWrapper=new QueryWrapper<Catalogs>();
        queryWrapper.eq("usable",1);
        queryWrapper.orderByAsc(new String[] {"id"});
        return catalogsMapper.selectList(queryWrapper);
    }

    @RequestMapping("/vaNameOrCode")
    public boolean vaNameOrCode(@RequestParam(value = "id", required = false) Long id,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "code", required = false)String code,
                                @RequestParam(value = "parentId", required = false)String parentId){
        if(StringUtil.isBlank(name) && StringUtil.isBlank(code)){
            return false;
        }
        QueryWrapper<Catalogs> queryWrapper=new QueryWrapper<Catalogs>();
        if(id != null){
            queryWrapper.ne("id", id);
        }
        if(parentId != null){
            queryWrapper.eq("parent_id",parentId);
        }
        if(StringUtil.isNotBlank(name)){
            queryWrapper.eq("name", name);
            List<Catalogs> catalogs = catalogsMapper.selectList(queryWrapper);
            if(!CollectionUtils.isEmpty(catalogs)){
                return false;
            }
        }
        if(StringUtil.isNotBlank(code)){
            queryWrapper.eq("code", code);
            if(id != null){
                queryWrapper.ne("id", id);
            }
            List<Catalogs> catalogs = catalogsMapper.selectList(queryWrapper);
            if(!CollectionUtils.isEmpty(catalogs)){
                return false;
            }
        }
        return true;
    }
}
