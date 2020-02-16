package cn.forest.mall.fallback;

import cn.forest.mall.remote.ReceivingAccountRemote;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReceivingAccountBack implements ReceivingAccountRemote {

    @Override
    public Object list(Integer type) {
        return null;
    }

    @Override
    public int saveAll(List<Map<String, Object>> mapList) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }
}
