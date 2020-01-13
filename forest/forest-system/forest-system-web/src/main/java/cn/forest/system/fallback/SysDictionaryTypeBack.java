package cn.forest.system.fallback;

import cn.forest.system.remote.SysDictionaryTypeRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysDictionaryTypeBack implements SysDictionaryTypeRemote {


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

    @Override
    public Object list(Long page, Long pageSize,String name) {
      return null;
    }

    @Override
    public Object getAll() {
      return null;
    }
}
