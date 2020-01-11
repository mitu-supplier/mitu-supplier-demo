package cn.forest.mall.remote;

import cn.forest.mall.fallback.SupplierBalanceRecordBack;
import cn.forest.mall.fallback.SuppliersBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = SupplierBalanceRecordBack.class)
public interface SupplierBalanceRecordRemote {

    /**
     * 商户余额列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/supplierBalanceRecord/balanceList")
    Object balanceList(Map<String, Object> map);

    /**
     * 商户直充记录查询
     *
     * @param map
     * @return
     */
    @RequestMapping("/supplierBalanceRecord/recordList")
    Object recordList(Map<String, Object> map);

    @RequestMapping("/supplierBalanceRecord/save")
    int save(Map<String, Object> map);
}
