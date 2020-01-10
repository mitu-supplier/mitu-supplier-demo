package cn.forest.mall.mapper;

import cn.forest.mall.entity.SupplierBalanceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface SupplierBalanceRecordMapper extends BaseMapper<SupplierBalanceRecord> {

    /**
     * 查询直充记录
     *
     * @param map
     * @return
     */
    List<SupplierBalanceRecord> recordlist(Map<String, Object> map);

    /**
     * 查询商户余额
     *
     * @param map
     * @return
     */
    List<SupplierBalanceRecord> balanceList(Map<String, Object> map);
}
