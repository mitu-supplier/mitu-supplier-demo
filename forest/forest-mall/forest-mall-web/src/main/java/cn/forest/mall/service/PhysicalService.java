package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.ProductsRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class PhysicalService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ProductsRemote productsRemote;

    public Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            if (!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1) {
                map.put("supplierId", userInfoMap.get("typeId"));
            }
        }
        map.put("deliveryCode", "D_SW");
        map.put("auditStatus", 1);

        Object obj = productsRemote.list(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> updateInventory(Map<String, Object> paramsMap){
        Long id = paramsMap.get("productId") == null ? null : Long.parseLong(StringUtil.toString(paramsMap.get("productId")));
        Integer inventoryNum = paramsMap.get("inventoryNum") == null ? null : Integer.parseInt(StringUtil.toString(paramsMap.get("inventoryNum")));
        int i = productsRemote.updateInventory(id, inventoryNum);
        return ResultMessage.result(i, "修改成功", "修改失败");
    }
}
