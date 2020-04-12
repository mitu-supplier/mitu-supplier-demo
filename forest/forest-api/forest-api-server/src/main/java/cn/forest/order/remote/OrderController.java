package cn.forest.order.remote;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.RemoteResponseForest;
import cn.forest.order.dto.MerchantOrder;

@RestController
public class OrderController {


    /**
     * 创建商户订单
     *
     * @return
     */
    @RequestMapping("/createMerchantOrder")
    public RemoteResponseForest createMerchantOrder(@RequestBody MerchantOrder merchantOrder) {
        return null;
    }
}
