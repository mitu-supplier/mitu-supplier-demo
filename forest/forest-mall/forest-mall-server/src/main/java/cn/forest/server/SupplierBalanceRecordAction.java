package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Products;
import cn.forest.mall.entity.SupplierBalanceRecord;
import cn.forest.mall.mapper.SupplierBalanceRecordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supplierBalanceRecord")
public class SupplierBalanceRecordAction {

    @Autowired
    private SupplierBalanceRecordMapper supplierBalanceRecordMapper;

    /**
     * 商户余额列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/balanceList")
    public Object balanceList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<SupplierBalanceRecord> supplierBalanceRecords = supplierBalanceRecordMapper.balanceList(map);
            PageInfo<SupplierBalanceRecord> supplierBalanceRecordsPage = new PageInfo<SupplierBalanceRecord>(supplierBalanceRecords);
            return new ResultPage<SupplierBalanceRecord>(supplierBalanceRecordsPage);
        }
        return null;
    }

    /**
     * 商户直充记录查询
     *
     * @param map
     * @return
     */
    @RequestMapping("/recordList")
    public Object recordList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<SupplierBalanceRecord> recordlist = supplierBalanceRecordMapper.recordlist(map);
            PageInfo<SupplierBalanceRecord> recordPageInfo = new PageInfo<SupplierBalanceRecord>(recordlist);
            return new ResultPage<SupplierBalanceRecord>(recordPageInfo);
        }
        return null;
    }
}
