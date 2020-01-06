package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysSequenceRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SysSequenceBack implements SysSequenceRemote {

    @Override
    public Object save(Map<String, ?> map) {
        return null;
    }

    @Override
    public String getSeqValue(String name) {
        return null;
    }

    @Override
    public int countByName(String name) {
        return 0;
    }
}
