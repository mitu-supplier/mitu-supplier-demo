package cn.forest.login.fallback;

import cn.forest.login.remote.SuppliersRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SuppliersBack implements SuppliersRemote {

    @Override
    public Object getById(Long id) {
        return null;
    }
}
