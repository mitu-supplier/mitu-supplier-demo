package cn.forest.inventory.remote;

import cn.forest.common.RemoteResponse;
import cn.forest.inventory.dto.CamiloDTO;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存管理
 */
@RestController
public class InventoryController {

    /**
     * 获取卡密接口
     * 通过商户产品标识获取卡密，支持幂等。通过businessType和orderNo决定请求的唯一性
     *
     * @param businessType      业务类型
     * @param orderNo           业务订单号
     * @param merchantId        商户标识
     * @param merchantProductNo 商户产品编号
     * @return
     */
    public RemoteResponse<CamiloDTO> outStore(String businessType, String orderNo, Integer merchantId, String merchantProductNo) {
        RemoteResponse<CamiloDTO> result = new RemoteResponse<>();

        return result;
    }
}
