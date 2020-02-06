package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductsRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductsBack implements ProductsRemote {

    @Override
    public Object list(Map<String, ?> map) {
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
    public int batchAudit(String ids, Integer auditStatus) {
        return 0;
    }

    @Override
    public Object selectByCode(String code) {
        return null;
    }

    @Override
    public int batchDelete(String ids) {
        return 0;
    }

    @Override
    public Object selectList(Integer auditStatus, Long supplierId) {
        return null;
    }
}
