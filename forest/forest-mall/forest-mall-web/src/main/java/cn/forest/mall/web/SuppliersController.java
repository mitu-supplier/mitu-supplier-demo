package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.remote.SuppliersRemote;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 查看商户列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(Map<String, Object> map) {
        return suppliersService.list(map);
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return suppliersService.getById(id);
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public Map<String, Object> register(HttpServletRequest request) {
        return suppliersService.register(RequestMap.requestToMap(request));
    }

    /**
     * 审核
     *
     * @param request
     * @return
     */
    @RequestMapping("/audit")
    public Map<String, Object> audit(HttpServletRequest request) {
        return suppliersService.audit(request);
    }

    /**
     * 查看审核记录
     *
     * @param page
     * @param pageSize
     * @param businessId
     * @return
     */
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("businessId") Long businessId) {
        return suppliersService.getAuditList(page, pageSize, businessId);
    }
}
