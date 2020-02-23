package cn.forest.mall.fallback;

import cn.forest.mall.remote.ProductPicRemote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductPicBack implements ProductPicRemote {

    @Override
    public Object selectByProduct(Long productId, Integer type) {
        return null;
    }

    @Override
    public int deleteByProduct(Long productId, Integer type) {
        return 0;
    }

    @Override
    public void save(List<Map<String, Object>> list) {

    }
}
