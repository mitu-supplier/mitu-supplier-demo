package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.DeliveryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/deliveryCode")
public class DeliveryCodeController {

    @Autowired
    private DeliveryCodeService deliveryCodeService;

    /**
     * 分页查询
     *
     * @param page       页码
     * @param pageSize   每页大小
     * @param supplierId 供应商id
     * @param code       发货代号
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page,
                       @RequestParam("pageSize") Long pageSize,
                       @RequestParam(value = "supplierId", required = false) Long supplierId,
                       @RequestParam(value = "code", required = false) String code) {

        return deliveryCodeService.list(page, pageSize, supplierId, code);
    }

    /**
     * 根据供应商查询发货代号
     *
     * @param supplierId 供应商id
     * @return
     */
    @RequestMapping("/select")
    public Object select(@RequestParam("supplierId") Long supplierId) {
        return deliveryCodeService.select(supplierId);
    }

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping("/save")
    public Object save(HttpServletRequest request) {
        return deliveryCodeService.save(RequestMap.requestToMap(request));
    }
}
