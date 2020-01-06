package cn.forest.mall.fallback;

import cn.forest.mall.remote.CamiloRemote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CamiloBack implements CamiloRemote {

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
    public int batchSave(List<Map<String, ?>> mapList) {
        return 0;
    }

    @Override
    public Object selectByProductId(Long productId) {
        return null;
    }

    @Override
    public int countByProductId(Long productId) {
        return 0;
    }
}