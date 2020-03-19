package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.SupplierBalanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplierBalanceRecord")
@SysLogs(desc = "商户余额管理")
public class SupplierBalanceRecordController {

    @Autowired
    private SupplierBalanceRecordService supplierBalanceRecordService;

    /**
     * 商户余额列表
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商户余额列表")
    @RequestMapping("/balanceList")
    public Object balanceList(HttpServletRequest request) {
        return supplierBalanceRecordService.balanceList(request);
    }

    /**
     * 商户直充记录
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商户直充记录")
    @RequestMapping("/recordList")
    public Object recordList(HttpServletRequest request) {
        return supplierBalanceRecordService.recordList(request);
    }

    /**
     * 保存
     * @param request
     * @return
     */
    @SysLogs(desc = "保存商户余额信息")
    @RequestMapping("/save")
    public Object save(HttpServletRequest request) {
        return supplierBalanceRecordService.save(request);
    }

    /**
     * 修改商户余额报警值
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "修改商户余额报警值")
    @RequestMapping("/updateAlertBalance")
    public Map<String, Object> updateAlertBalance(HttpServletRequest request) {
        return supplierBalanceRecordService.updateAlertBalance(RequestMap.requestToMap(request));
    }
}
