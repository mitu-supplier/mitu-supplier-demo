package cn.forest.mall.fallback;

import cn.forest.mall.remote.CatalogsRemote;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CatalogsFallBack implements CatalogsRemote {

    @Override
    public Object getChildrenByLevel(Long id) {
        return null;
    }

    @Override
    public int save(Map<String, ?> map) {
        return 0;
    }

    @Override
    public int upadte(Map<String, ?> map) {
        return 0;
    }

    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public int disable(Long id) {
        return 0;
    }
}
