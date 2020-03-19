package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/products_audit")
@SysLogs(desc = "商品信息审核管理")
public class ProductsAuditController {

    @Autowired
    private ProductsService productsService;

    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商品审核列表查询")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return productsService.list(request, "audit");
    }

    /**
     * 审核
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商品审核")
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
    @SysLogs(desc = "商品批量审核")
    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        return productsService.batchAudit(request);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "获取商品详情")
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return productsService.getById(id);
    }

    /**
     * 查看审核记录
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "查看商品审核记录")
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(HttpServletRequest request) {
        return productsService.getAuditList(RequestMap.requestToMap(request));
    }
}
