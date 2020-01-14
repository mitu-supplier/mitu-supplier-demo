package cn.forest.mall.service;

import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.DeliveryCodeRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("deliveryCodeService")
public class DeliveryCodeService {

    @Autowired
    private DeliveryCodeRemote deliveryCodeRemote;

    public Map<String, Object> list(Long page, Long pageSize, Long supplierId, String code){
        Object list = deliveryCodeRemote.list(page, pageSize, supplierId, code);
        if(list != null){
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> select(Long supplierId){
        Object select = deliveryCodeRemote.select(supplierId);
        if(select != null){
            return ResultMessage.success(select);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, Object> map){
        Object save = deliveryCodeRemote.save(map);
        if(save != null){
            Map saveMap = (Map) save;
            return ResultMessage.result(Integer.parseInt(saveMap.get(Constant.RESULT_NUM).toString()), "保存成功", "保存失败");
        }
        return ResultMessage.error("保存失败");
    }

}
