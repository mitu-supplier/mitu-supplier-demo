package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysDictionaryTypeRemote;
import org.springframework.stereotype.Component;

@Component
public class SysDictionaryTypeBack implements SysDictionaryTypeRemote {

    @Override
    public Object getAll() {
        return null;
    }

    @Override
    public Object selectByPrefix(String prefix) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }
}
