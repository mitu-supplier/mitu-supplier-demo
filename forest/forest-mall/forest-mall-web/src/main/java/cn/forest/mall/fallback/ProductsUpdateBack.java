package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductsUpdateRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductsUpdateBack implements ProductsUpdateRemote {

    @Override
    public Object save(Map<String, ?> map) {
        return null;
    }

    @Override
    public int updateById(Map<String, ?> map) {
        return 0;
    }

    @Override
    public Object selectUpdateRecodeList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int batchAudit(String ids, Integer auditStatus) {
        return 0;
    }

    @Override
    public Object selectList(Integer status, Long supplierId) {
        return null;
    }
}
