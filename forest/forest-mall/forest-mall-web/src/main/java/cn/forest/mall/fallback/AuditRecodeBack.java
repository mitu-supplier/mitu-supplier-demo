package cn.forest.mall.fallback;

import cn.forest.mall.remote.AuditRecodeRemote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AuditRecodeBack implements AuditRecodeRemote {

    @Override
    public Object list(Map<String, ?> map) {
        return null;
    }

    @Override
    public int save(Map<String, ?> map) {
        return 0;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int batchSave(List<Map<String, Object>> list) {
        return 0;
    }

    @Override
    public Object selectByBusinessId(Long businessId, String auditTypes) {
        return null;
    }
}
