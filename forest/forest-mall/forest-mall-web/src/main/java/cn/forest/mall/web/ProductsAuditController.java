package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/products_audit")
public class ProductsAuditController {

    @Autowired
    private ProductsService productsService;

    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return productsService.list(RequestMap.requestToMap(request));
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
