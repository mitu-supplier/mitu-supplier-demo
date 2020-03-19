package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.PhysicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 实物库存
 */
@RestController
@RequestMapping("/physical")
@SysLogs(desc = "实物库存管理")
public class PhysicalController {

    @Autowired
    private PhysicalService physicalService;

    @SysLogs(desc = "实物库存列表")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request){
        return physicalService.list(request);
    }

    @SysLogs(desc = "修改实物库存")
    @RequestMapping("/updateInventory")
    public Map<String, Object> updateInventory(HttpServletRequest request){
        return physicalService.updateInventory(RequestMap.requestToMap(request));
    }
}
