package cn.forest.server;

import cn.forest.mall.entity.Catalogs;
import cn.forest.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
public class CatalogsAction {

    @Autowired
    private CatalogsService catalogsService;

    @RequestMapping("/getChildrenByLevel")
    public Object getChildrenByLevel(Long id) {
        return catalogsService.getChildrenByLevel(id);
    }

    @RequestMapping("/getById")
    public Object getById(Long id) {
        return catalogsService.getById(id);
    }

    @RequestMapping("/save")
    public int save(Catalogs catalogs){
        return catalogsService.saveCatalogs(catalogs);
    }

    @RequestMapping("/update")
    public int update(Catalogs catalogs){
        return catalogsService.updateCatalogs(catalogs);
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
}
