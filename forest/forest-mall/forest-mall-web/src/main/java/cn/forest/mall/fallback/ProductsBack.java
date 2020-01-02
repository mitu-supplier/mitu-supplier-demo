package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductsRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductsBack implements ProductsRemote {

    @Override
    public Object list(Long page, Long pageSize) {
        return null;
    }

    @Override
    public Object save(Map<String, ?> map) {
        return null;
    }

    @Override
    public int update(Map<String, ?> map) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int batchAudit(Long ids, Integer auditStatus) {
        return 0;
    }
}
