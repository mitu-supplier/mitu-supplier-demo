package cn.forest.mall.fallback;

import cn.forest.mall.remote.OrdersRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrdersBack implements OrdersRemote {

    @Override
    public Object list(Map<String, Object> map) {
        return null;
    }

    @Override
    public Object getById(Long id) {
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
    public int batchDelete(String ids) {
        return 0;
    }
}
