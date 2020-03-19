package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.SuppliersService;
import cn.forest.mall.service.SuppliersUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier_update")
@SysLogs(desc = "供应商信息修改管理")
public class SupplierUpdateController {

    @Autowired
    private SuppliersUpdateService suppliersUpdateService;

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 修改商户注册信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "修改商户注册信息")
    @RequestMapping("/updateRegisterInfo")
    public Map<String, Object> updateRegisterInfo(HttpServletRequest request) {
        return suppliersUpdateService.updateRegisterInfo(request);
    }

    /**
     * 查询变更记录
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "查询供应商变更记录")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return suppliersUpdateService.list(request);
    }

    /**
     * 修改审核
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "供应商修改审核")
    @RequestMapping("/audit")
    public Map<String, Object> audit(HttpServletRequest request) {
        return suppliersUpdateService.audit(request);
    }

    /**
     * 获取审核记录列表
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "获取供应商修改审核记录列表")
    @RequestMapping("/getAuditList")
    public Map<String, Object> getAuditList(@RequestParam("id") Long id) {
        return suppliersUpdateService.getAuditList(id);
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "供应商修改审核查看详情")
    @RequestMapping("/view")
    public Map<String, Object> view(@RequestParam("id") Long id) {
        return suppliersUpdateService.view(id);
    }

    /**
     * 判断是否为供应商
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "判断是否为供应商")
    @RequestMapping("/isSupplier")
    public Map<String, Object> isSupplier(HttpServletRequest request) {
        if (suppliersService.isSupplier(request)) {
            return ResultMessage.success(1);
        }
        return ResultMessage.success(0);
    }

    /**
     * 批量审核
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "供应商修改批量审核")
    @RequestMapping("/batchAudit")
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        return suppliersUpdateService.batchAudit(request);
    }
}
