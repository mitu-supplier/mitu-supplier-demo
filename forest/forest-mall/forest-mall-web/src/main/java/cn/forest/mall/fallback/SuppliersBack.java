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
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return 0;
    }
}
