package cn.forest.mall.service;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.OrdersRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service("ordersService")
public class OrdersService {

    @Autowired
    private OrdersRemote ordersRemote;

    public Map<String, Object> list(HttpServletRequest request) {
        Object list = ordersRemote.list(RequestMap.requestToMap(request));
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, Object> map) {
        int save = ordersRemote.save(map);
        return ResultMessage.result(save, "保存成功", "保存失败");
    }

    public Map<String, Object> update(Map<String, Object> map) {
        int update = ordersRemote.update(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> getById(Long id) {
        Object obj = ordersRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> batchDelete(String ids) {
        int i = ordersRemote.batchDelete(ids);
        return ResultMessage.result(i, "删除成功", "删除失败");
    }
}
