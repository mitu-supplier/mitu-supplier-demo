package cn.forest.mall.service;

import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.CamiloRemote;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.ProductsRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Object obj = productsRemote.list(map);
        if (obj != null) {
            Map pMap = (Map) obj;
            Object list = pMap.get("list");
            if (list != null) {
                List<Map<String, Object>> productList = (List<Map<String, Object>>) list;
                for (Map<String, Object> product : productList) {
                    // 品目名称
                    if (StringUtil.toString(product.get("catalogId")) != null) {
                        Long catalogId = Long.parseLong(StringUtil.toString(product.get("catalogId")));
                        Object catalogs = catalogsRemote.getById(catalogId);
                        if (catalogs != null) {
                            product.put("catalogName", JsonUtil.readTree(catalogs).path("name").asText());
                        }
                    }
                    // 卡密数量 失效时间
                    if (StringUtil.toString(product.get("id")) != null) {
                        Long productId = Long.parseLong(StringUtil.toString(product.get("id")));
                        Object camiloObj = camiloRemote.selectByProductId(productId);
                        if(camiloObj != null){
                            List camiloList = (List)camiloObj;
                            product.put("stock", camiloList.size());
                            product.put("failureTime", camiloList.size() == 0 ? "--" : ((Map)camiloList.get(0)).get("failureTime"));
                        }else{
                            product.put("stock", 0);
                            product.put("failureTime", "--");
                        }
                    }
                }
            }
            return ResultMessage.success(obj);
        }
        return null;
    }
}
