package cn.forest.service;

import cn.forest.mall.entity.Catalogs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CatalogsService extends IService<Catalogs> {

    List<Catalogs> getChildrenByLevel(Long id);

    int saveCatalogs(Catalogs catalogs);

    int updateCatalogs(Catalogs catalogs);
}
