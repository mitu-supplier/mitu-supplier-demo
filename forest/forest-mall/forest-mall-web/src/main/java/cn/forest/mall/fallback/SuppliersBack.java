package cn.forest.mall.fallback;

import cn.forest.mall.remote.SuppliersRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SuppliersBack implements SuppliersRemote {

    @Override
    public Object list(Map<String, Object> map) {
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
    public int updateStatus(Long id, Integer status) {
        return 0;
    }

    @Override
    public int batchAudit(String ids, Integer status) {
        return 0;
    }

    @Override
    public Object getAll() {
        return null;
    }
}
