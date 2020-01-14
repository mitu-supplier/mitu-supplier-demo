package cn.forest.mall.fallback;

import cn.forest.mall.remote.DeliveryCodeRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeliveryCodeBack implements DeliveryCodeRemote {

    @Override
    public Object list(Long page, Long pageSize, Long supplierId, String code) {
        return null;
    }

    @Override
    public Object select(Long supplierId) {
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
}
