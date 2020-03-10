package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.OrderItem;
import cn.forest.mall.entity.Orders;
import cn.forest.mall.mapper.OrderItemMapper;
import cn.forest.mall.mapper.OrdersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/orders")
@RestController
public class OrdersAction {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        QueryWrapper<Orders> catalogsQueryWrapper = new QueryWrapper<>();
        Object supplierId = map.get("supplierId");
        if(!StringUtil.isBlank(supplierId)){
            catalogsQueryWrapper.eq("supplier_id", Long.parseLong(supplierId.toString()));
        }
        Object supplierName = map.get("supplierName");
        if(!StringUtil.isBlank(supplierName)){
            catalogsQueryWrapper.like("supplier_name", supplierName.toString());
        }
        Object code = map.get("code");
        if(!StringUtil.isBlank(code)){
            catalogsQueryWrapper.like("code", code.toString());
        }
        Object startTime = map.get("startTime");
        if(!StringUtil.isBlank(startTime)){
            catalogsQueryWrapper.ge("created_at", startTime.toString());
        }
        Object endTime = map.get("endTime");
        if(!StringUtil.isBlank(endTime)){
            catalogsQueryWrapper.le("created_at", endTime.toString());
        }
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            Page<Orders> ipage = new Page<Orders>(Long.parseLong(map.get("page").toString()), Long.parseLong(map.get("pageSize").toString()));
            IPage<Orders> selectPage = ordersMapper.selectPage(ipage, catalogsQueryWrapper);
            return new ResultPage<Orders>(selectPage);
        }
        return null;
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id){
        Orders orders = ordersMapper.selectById(id);
        if(orders != null){
            QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", orders.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
            orders.setItemList(orderItems);
        }
        return orders;
    }

    @RequestMapping("/save")
    public int save(@RequestBody Orders orders){
        return ordersMapper.insert(orders);
    }

    @RequestMapping("/update")
    public int update(@RequestBody Orders orders){
        return ordersMapper.updateById(orders);
    }

    @RequestMapping("/batchDelete")
    public int batchDelete(@RequestParam("ids") String ids){
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                ordersMapper.deleteById(Long.parseLong(str));
            }
            return 1;
        }
        return 0;
    }
}
