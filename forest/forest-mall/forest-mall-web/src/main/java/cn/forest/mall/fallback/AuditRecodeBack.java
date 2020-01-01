package cn.forest.mall.fallback;

import cn.forest.mall.remote.AuditRecodeRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuditRecodeBack implements AuditRecodeRemote {

    @Override
    public Object list(Long page, Long pageSize, Long businessId, Integer auditType) {
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
}