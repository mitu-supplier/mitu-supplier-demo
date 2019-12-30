package cn.forest.system.web;

import cn.forest.common.util.RequestMap;
import cn.forest.system.service.SysDictionaryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/sysDictionaryData")
public class sysDictionaryDataController {

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    @RequestMapping(value = "/list")
    public Map<String, Object> list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") Long type) {
        return sysDictionaryDataService.list(page, pageSize, type);
    }

    @RequestMapping(value = "/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return sysDictionaryDataService.getById(id);
    }

    @RequestMapping(value = "/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return sysDictionaryDataService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return sysDictionaryDataService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return sysDictionaryDataService.delete(id);
    }
}
