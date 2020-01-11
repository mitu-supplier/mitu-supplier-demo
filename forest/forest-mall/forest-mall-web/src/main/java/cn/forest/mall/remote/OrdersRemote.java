package cn.forest.mall.remote;

import cn.forest.mall.fallback.OrdersBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = OrdersBack.class)
public interface OrdersRemote {

    @RequestMapping("/orders/list")
    Object list(@RequestBody Map<String, Object> map);

    @RequestMapping("/orders/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/orders/save")
    int save(@RequestBody Map<String, ?> map);

    @RequestMapping("/orders/update")
    int update(@RequestBody Map<String, ?> map);

    @RequestMapping("/orders/batchDelete")
    int batchDelete(@RequestParam("ids") String ids);
}
