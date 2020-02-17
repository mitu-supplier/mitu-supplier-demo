package cn.forest.service.impl;

import cn.forest.mall.entity.Catalogs;
import cn.forest.mall.mapper.CatalogsMapper;
import cn.forest.service.CatalogsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("catalogsService")
public class CatalogsServiceImpl extends ServiceImpl<CatalogsMapper, Catalogs> implements CatalogsService {

    @Autowired
    private CatalogsMapper catalogsMapper;

    @Override
    public List<Catalogs> getChildrenByLevel(Long id) {
        QueryWrapper<Catalogs> queryWrapper = new QueryWrapper<Catalogs>();
        queryWrapper.eq("usable", 1);
        if (id != null) {
            queryWrapper.eq("parent_id", id);
        } else {
            queryWrapper.eq("tree_depth", 1);
        }
        return catalogsMapper.selectList(queryWrapper);
    }

    @Override
    public int saveCatalogs(Catalogs catalogs) {
      
        Catalogs parent = catalogsMapper.selectById(catalogs.getParentId());
        catalogs.setIsParent("false");
        if (parent == null) {
            // 根节点
            catalogs.setTreeDepth(0);
            catalogs.setParentId(0l);
        } else {
            catalogs.setTreeDepth(parent.getTreeDepth() + 1);
            
            if ("false".equals(parent.getIsParent())) {
                parent.setIsParent("true");
                catalogsMapper.updateById(catalogs);
            }
        }
        int insert = catalogsMapper.insert(catalogs);
        if (insert > 0) {
          
          if (parent != null) {
            catalogs.setTreeIds(parent.getTreeIds()+""+catalogs.getId()+"-");
            catalogs.setTreeNames(parent.getTreeNames()+""+catalogs.getName()+"-");
          }else {
            catalogs.setTreeIds("-"+catalogs.getId()+"-");
            catalogs.setTreeNames("-"+catalogs.getName()+"-");
          }
          catalogsMapper.updateById(catalogs);
        }
        return insert;
    }

    @Override
    public int updateCatalogs(Catalogs catalogs) {
        
        Catalogs parent = catalogsMapper.selectById(catalogs.getParentId());
        Catalogs old_catalog = catalogsMapper.selectById(catalogs.getId());
        catalogs.setTreeNames(parent.getTreeNames()+""+catalogs.getName()+"-");
        int update = catalogsMapper.updateById(catalogs);
        if (update > 0) {
            // 查询所有的子节点（包括自己）
            QueryWrapper<Catalogs> queryWrapper = new QueryWrapper<Catalogs>();
            queryWrapper.like("tree_ids", "-" + catalogs.getId() + "-");
            queryWrapper.orderByAsc("tree_depth");
            List<Catalogs> list = catalogsMapper.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(list)) {
                for (Catalogs c : list) {
                     c.setTreeNames(c.getTreeNames().replaceAll("-"+old_catalog.getName()+"-", "-"+catalogs.getName()+"-"));
                     catalogsMapper.updateById(c);
                }
            }
        }
        return update;
    }

    
}
