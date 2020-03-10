package cn.forest.order.remote;

import cn.forest.common.RemoteResponse;
import cn.forest.order.dto.MerchantOrder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


    /**
     * 创建商户订单
     *
     * @return
     */
    @RequestMapping("/createMerchantOrder")
    public RemoteResponse createMerchantOrder(@RequestBody MerchantOrder merchantOrder) {
        return null;
    }
}
