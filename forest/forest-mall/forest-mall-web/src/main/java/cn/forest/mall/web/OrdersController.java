package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@SysLogs(desc = "订单信息管理")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @SysLogs(desc = "订单列表查询")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return ordersService.list(request);
    }

    @SysLogs(desc = "保存订单")
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return ordersService.save(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "修改订单")
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return ordersService.update(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "获取订单详情")
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return ordersService.getById(id);
    }

    @SysLogs(desc = "批量删除订单")
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return ordersService.batchDelete(ids);
    }
}
