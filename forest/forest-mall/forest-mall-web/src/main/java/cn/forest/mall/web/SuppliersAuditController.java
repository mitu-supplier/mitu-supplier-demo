package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier_audit")
public class SuppliersAuditController {

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 查看商户审核列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return suppliersService.list(request);
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
     * @param request
     * @return
     */
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(HttpServletRequest request) {
        return suppliersService.getAuditList(RequestMap.requestToMap(request));
    }

    /**
     * 根据code获取字典表数据
     *
     * @return
     */
    @RequestMapping("/getDictionaryData")
    public Map<String, Object> getDictionaryData() {
        return suppliersService.getDictionaryData();
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/view")
    public Map<String, Object> view(@RequestParam("id") Long id) {
        return suppliersService.view(id);
    }

    /**
     * 获取所有的角色
     *
     * @return
     */
    @RequestMapping("/getPermissions")
    public Map<String, Object> getPermissions() {
        return suppliersService.getPermissions();
    }

    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request){
        return suppliersService.batchAudit(request);
    }
}
