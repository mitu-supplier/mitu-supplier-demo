package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductDeliveryStatusDataRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductDeliveryStatusDataBack implements ProductDeliveryStatusDataRemote {

    @Override
    public Object list() {
        return null;
    }

    @Override
    public int save(Map<String, ?> map) {
        return 0;
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
    public Object selectByProductId(Long productId) {
        return null;
    }
}
