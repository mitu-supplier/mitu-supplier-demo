package cn.forest.mall.web;

import cn.forest.mall.service.ProductsUpdateService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/products_update")
public class ProductsUpdateController {

    @Autowired
    private ProductsUpdateService productsUpdateService;

    /**
     * 商品修改审核列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return productsUpdateService.list(request);
    }

    /**
     * 修改商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return productsUpdateService.update(request);
    }

    /**
     * 商品信息修改审核
     *
     * @param request
     * @return
     */
    @RequestMapping("/audit")
    public Map<String, Object> audit(HttpServletRequest request) {
        return productsUpdateService.audit(request);
    }

    /**
     * 获取审核记录列表
     *
     * @param id
     * @return
     */
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(@RequestParam("id") Long id) {
        return productsUpdateService.getAuditList(id);
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public Map<String, Object> view(@RequestParam("id") Long id) {
        return productsUpdateService.view(id);
    }

    /**
     * 批量审核
     *
     * @param request
     * @return
     */
    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        return productsUpdateService.batchAudit(request);
    }
}
