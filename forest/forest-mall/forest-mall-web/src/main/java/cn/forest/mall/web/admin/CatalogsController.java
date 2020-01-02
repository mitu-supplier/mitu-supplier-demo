package cn.forest.mall.web.admin;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.CatalogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/catalogs")
@SysLogs(desc = "品目管理")
public class CatalogsController {

    @Autowired
    private CatalogsService catalogsService;

    @SysLogs(desc = "根据层级获取品目信息")
    @RequestMapping(value = "/getChildrenByLevel", method = RequestMethod.GET)
    public Map<String, Object> getChildrenByLevel(Long id) {
        return catalogsService.getChildrenByLevel(id);
    }

    @SysLogs(desc = "新增品目信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(HttpServletRequest request) {
        return catalogsService.save(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "修改品目信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Map<String, Object> update(HttpServletRequest request) {
        return catalogsService.update(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "禁用品目")
    @RequestMapping(value = "/disable", method = RequestMethod.PUT)
    public Map<String, Object> disable(Long id) {
        return catalogsService.disable(id);
    }
}
