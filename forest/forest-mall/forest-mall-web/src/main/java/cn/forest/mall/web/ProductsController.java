package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.SysDictionaryDataRemote;
import cn.forest.mall.service.ProductsService;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private SysDictionaryDataRemote sysDictionaryDataRemote;

    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return productsService.list(request);
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return productsService.save(request);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return productsService.getById(id);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return productsService.update(RequestMap.requestToMap(request));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return productsService.delete(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return productsService.batchDelete(ids);
    }

    @RequestMapping("/getCatalogs")
    public Map<String, Object> getCatalogs() {
        Object list = catalogsRemote.getAll();
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }

    @RequestMapping("/getDelivery_type")
    public Map<String, Object> getDelivery_type() {
        Object list = sysDictionaryDataRemote.selectByDateTypeCode("DELIVERY_TYPE");
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }
}
