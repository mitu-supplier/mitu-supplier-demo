package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
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
public class PhysicalController {

    @Autowired
    private PhysicalService physicalService;

    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request){
        return physicalService.list(request);
    }

    @RequestMapping("/updateInventory")
    public Map<String, Object> updateInventory(HttpServletRequest request){
        return physicalService.updateInventory(RequestMap.requestToMap(request));
    }
}
