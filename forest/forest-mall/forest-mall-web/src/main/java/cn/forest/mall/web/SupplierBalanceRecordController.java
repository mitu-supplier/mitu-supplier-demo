package cn.forest.mall.web;

import cn.forest.mall.service.SupplierBalanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/supplierBalanceRecord")
public class SupplierBalanceRecordController {

    @Autowired
    private SupplierBalanceRecordService supplierBalanceRecordService;

    /**
     * 商户余额列表
     *
     * @param request
     * @return
     */
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
    @RequestMapping("/recordList")
    public Object recordList(HttpServletRequest request) {
        return supplierBalanceRecordService.recordList(request);
    }

    @RequestMapping("/save")
    public Object save(HttpServletRequest request){
        return supplierBalanceRecordService.save(request);
    }
}
