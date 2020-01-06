package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysDictionaryDataRemote;
import org.springframework.stereotype.Component;

@Component
public class SysDictionaryDataBack implements SysDictionaryDataRemote {

    @Override
    public Object selectByDateTypeCode(String code) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public Object selectByDateTypeId(Long id) {
        return null;
    }
}
