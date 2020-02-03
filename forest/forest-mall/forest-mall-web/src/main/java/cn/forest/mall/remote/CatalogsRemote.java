package cn.forest.mall.remote;

import cn.forest.mall.fallback.CatalogsFallBack;
import cn.forest.mall.service.CatalogsService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = CatalogsFallBack.class)
public interface CatalogsRemote {

    @RequestMapping("/catalogs/getChildrenByLevel")
    Object getChildrenByLevel(@RequestParam(value = "id") Long id);

    @RequestMapping("/catalogs/save")
    int save(Map<String, ?> map);

    @RequestMapping("/catalogs/update")
    int upadte(Map<String, ?> map);

    @RequestMapping("/catalogs/getById")
    Object getById(@RequestParam(value = "id") Long id);

    @RequestMapping("/catalogs/disable")
    int disable(@RequestParam(value = "id") Long id);

    @RequestMapping("/catalogs/delete")
    int delete(@RequestParam(value = "id") Long id);

    @RequestMapping("/catalogs/getAll")
    Object getAll();

    @RequestMapping("/catalogs/getAllUseable")
    Object getAllUseable();

    /**
     * 校验名称编码重复
     *
     * @param id
     * @param name
     * @param code
     * @return
     */
    @RequestMapping("/catalogs/vaNameOrCode")
    boolean vaNameOrCode(@RequestParam(value = "id", required = false) Long id,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "code", required = false) String code,
                         @RequestParam(value = "parentId", required = false) Long parentId);
}
