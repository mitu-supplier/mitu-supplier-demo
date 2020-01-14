package cn.forest.mall.remote;

import cn.forest.mall.fallback.DeliveryCodeBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = DeliveryCodeBack.class)
public interface DeliveryCodeRemote {

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param supplierId
     * @param code
     * @return
     */
    @RequestMapping("/deliveryCode/list")
    public Object list(@RequestParam("page") Long page,
                       @RequestParam("pageSize") Long pageSize,
                       @RequestParam(value = "supplierId", required = false) Long supplierId,
                       @RequestParam(value = "code", required = false) String code);

    /**
     * 根据供应商查询发货代号
     *
     * @param supplierId 供应商id
     * @return
     */
    @RequestMapping("/deliveryCode/select")
    Object select(@RequestParam(value = "supplierId") Long supplierId);

    /**
     * 新增
     *
     * @param map 发货代号信息
     * @return
     */
    @RequestMapping("/deliveryCode/save")
    Object save(@RequestBody Map<String, ?> map);

    /**
     * 通过id修改
     *
     * @param map
     * @return
     */
    @RequestMapping("/deliveryCode/update")
    int update(@RequestBody Map<String, ?> map);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deliveryCode/delete")
    int delete(@RequestParam("id") Long id);
}
