package cn.forest.mall.fallback;

import cn.forest.mall.remote.CompanyRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CompanyBack implements CompanyRemote {

    @Override
    public Object list(Long page, Long pageSize, String code, String name) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int save(Map<String, ?> Object) {
        return 0;
    }

    @Override
    public int update(Map<String, ?> Object) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public Object getAll() {
        return null;
    }

    @Override
    public int batchDelete(String ids) {
        return 0;
    }

    @Override
    public int vaName(Long id, String name) {
        return 0;
    }
}
