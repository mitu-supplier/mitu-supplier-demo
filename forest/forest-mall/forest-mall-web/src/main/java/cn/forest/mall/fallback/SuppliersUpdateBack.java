package cn.forest.mall.fallback;

import cn.forest.mall.remote.SuppliersUpdateRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SuppliersUpdateBack implements SuppliersUpdateRemote {

    @Override
    public Object getBySupplierId(Long supplierId, Integer status) {
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
    public Object selectUpdateRecodeList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int batchAudit(String ids, Integer status) {
        return 0;
    }

    @Override
    public Object selectList(Integer status, Long supplierId) {
        return null;
    }
}
