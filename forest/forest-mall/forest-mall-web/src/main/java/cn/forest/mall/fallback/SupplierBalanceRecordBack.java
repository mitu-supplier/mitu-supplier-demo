package cn.forest.mall.fallback;

import cn.forest.mall.remote.SupplierBalanceRecordRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SupplierBalanceRecordBack implements SupplierBalanceRecordRemote {

    @Override
    public Object balanceList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Object recordList(Map<String, Object> map) {
        return null;
    }

    @Override
    public int save(Map<String, Object> map) {
        return 0;
    }
}
