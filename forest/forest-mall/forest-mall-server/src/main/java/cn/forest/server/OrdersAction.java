package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Orders;
import cn.forest.mall.mapper.OrdersMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<Orders> orders = ordersMapper.selectList(null);
            PageInfo<Orders> productsPage = new PageInfo<Orders>(orders);
            return new ResultPage<Orders>(productsPage);
        }
        return null;
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id){
        return ordersMapper.selectById(id);
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
