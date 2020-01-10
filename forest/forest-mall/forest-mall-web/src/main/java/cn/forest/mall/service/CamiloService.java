package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.CamiloRemote;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.ProductsRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("camiloService")
public class CamiloService {

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private CamiloRemote camiloRemote;

    public Map<String, Object> list(Map<String, Object> map) {
        Object obj = camiloRemote.selectProductCamiloList(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> recordList(Map<String, Object> map) {
        Object obj = camiloRemote.recordList(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }
}
