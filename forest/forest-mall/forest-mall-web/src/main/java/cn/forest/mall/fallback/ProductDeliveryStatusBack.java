package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductDeliveryStatusRemote;
import org.springframework.stereotype.Component;

@Component
public class ProductDeliveryStatusBack implements ProductDeliveryStatusRemote {

    @Override
    public int saveByProductId(Long productId, String deliveryStatus, Integer type) {
        return 0;
    }

    @Override
    public int deleteByProductId(Long productId, Integer type) {
        return 0;
    }

    @Override
    public Object selectByProductId(Long productId, Integer type) {
        return null;
    }
}
