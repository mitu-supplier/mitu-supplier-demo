package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.remote.ProductsRemote;
import cn.forest.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 列表查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        return productsService.list(page, pageSize);
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return productsService.save(RequestMap.requestToMap(request));
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
     * 审核
     *
     * @param request
     * @return
     */
    @RequestMapping("/audit")
    public Map<String, Object> audit(HttpServletRequest request) {
        return productsService.audit(request);
    }

    /**
     * 批量审核
     *
     * @param request
     * @return
     */
    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        return productsService.batchAudit(request);
    }

}
