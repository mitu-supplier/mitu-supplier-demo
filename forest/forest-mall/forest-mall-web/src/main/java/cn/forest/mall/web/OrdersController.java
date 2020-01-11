package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request){
        return ordersService.list(request);
    }

    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request){
        return ordersService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request){
        return ordersService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id){
        return ordersService.getById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return ordersService.batchDelete(ids);
    }
}
