package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.DeliveryCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/deliveryCode")
@SysLogs(desc = "发货代号管理")
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
    @SysLogs(desc = "发货代号列表查询")
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
    @SysLogs(desc = "根据供应商查询发货代号")
    @RequestMapping("/select")
    public Object select(@RequestParam("supplierId") Long supplierId) {
        return deliveryCodeService.select(supplierId);
    }

    /**
     * 新增
     *
     * @return
     */
    @SysLogs(desc = "新增发货代号")
    @RequestMapping("/save")
    public Object save(HttpServletRequest request) {
        return deliveryCodeService.save(RequestMap.requestToMap(request));
    }
}
