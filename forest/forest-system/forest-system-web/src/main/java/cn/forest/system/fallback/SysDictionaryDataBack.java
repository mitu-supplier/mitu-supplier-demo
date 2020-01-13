package cn.forest.system.fallback;

import cn.forest.system.remote.SysDictionaryDataRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysDictionaryDataBack implements SysDictionaryDataRemote {

    @Override
    public Object list(Long page, Long pageSize, Long type,String name) {
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
    public int delete(Long id) {
        return 0;
    }
}
