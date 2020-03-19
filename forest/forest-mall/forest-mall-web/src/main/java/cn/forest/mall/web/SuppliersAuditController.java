package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier_audit")
@SysLogs(desc = "供应商信息审核管理")
public class SuppliersAuditController {

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 查看商户审核列表
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "查看商户审核列表")
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
    @SysLogs(desc = "供应商审核")
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
    @SysLogs(desc = "查看审核记录")
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(HttpServletRequest request) {
        return suppliersService.getAuditList(RequestMap.requestToMap(request));
    }

    /**
     * 根据code获取字典表数据
     *
     * @return
     */
    @SysLogs(desc = "根据code获取字典表数据")
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
    @SysLogs(desc = "查看详情")
    @RequestMapping("/view")
    public Map<String, Object> view(@RequestParam("id") Long id) {
        return suppliersService.view(id);
    }

    /**
     * 获取所有的角色
     *
     * @return
     */
    @SysLogs(desc = "获取所有的角色")
    @RequestMapping("/getPermissions")
    public Map<String, Object> getPermissions() {
        return suppliersService.getPermissions();
    }

    @SysLogs(desc = "批量审核")
    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        return suppliersService.batchAudit(request);
    }
}
