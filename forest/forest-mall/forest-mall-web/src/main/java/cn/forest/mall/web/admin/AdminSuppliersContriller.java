package cn.forest.mall.web.admin;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/admin/supplier")
public class AdminSuppliersContriller {

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 查看商户列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return suppliersService.list(RequestMap.requestToMap(request));
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
     * 修改
     *
     * @param request
     * @return
     */
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return suppliersService.update(RequestMap.requestToMap(request));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return suppliersService.delete(id);
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

    /**
     * 根据code获取字典表数据
     *
     * @param code
     * @return
     */
    @RequestMapping("/getDictionaryData")
    public Map<String, Object> getDictionaryData(@RequestParam("code") String code) {
        return suppliersService.getDictionaryData(code);
    }
}
