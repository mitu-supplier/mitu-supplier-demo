package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysUserRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysUserBack implements SysUserRemote {

    @Override
    public Object add(Map<String, ?> map) {
        return null;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int saveRole(Map<String, ?> map) {
        return 0;
    }

    @Override
    public int update(Map<String, ?> map) {
        return 0;
    }

    @Override
    public Object selectByTypeId(Long typeId) {
        return null;
    }
}