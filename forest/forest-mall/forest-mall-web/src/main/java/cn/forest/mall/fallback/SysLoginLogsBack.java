package cn.forest.mall.fallback;

import cn.forest.mall.remote.SysLoginLogsRemote;
import org.springframework.stereotype.Component;

@Component
public class SysLoginLogsBack implements SysLoginLogsRemote {

    @Override
    public Object count7Days() {
        return null;
    }
}
