package cn.forest.system.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysDictionaryDataService;
import cn.forest.system.service.SysDictionaryTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/sysDictionaryData")
@SysLogs(desc = "字典数据")
public class SysDictionaryDataController {

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;
    
    @Autowired
    private SysDictionaryTypeService sysDictionaryTypeService;

    @RequestMapping(value = "/list")
    @SysLogs(desc = "字典数据列表")
    public Map<String, Object> list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") Long type,@RequestParam("name") String name) {
        return sysDictionaryDataService.list(page, pageSize, type,name);
    }

    @RequestMapping(value = "/getById")
    @SysLogs(desc = "获取一条字典数据")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return sysDictionaryDataService.getById(id);
    }

    @RequestMapping(value = "/save")
    @SysLogs(desc = "保存字典数据")
    public Map<String, Object> save(HttpServletRequest request) {
        return sysDictionaryDataService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/update")
    @SysLogs(desc = "修改字典数据")
    public Map<String, Object> update(HttpServletRequest request) {
        return sysDictionaryDataService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/delete")
    @SysLogs(desc = "删除字典数据")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return sysDictionaryDataService.delete(id);
    }
    
    @RequestMapping(value = "/getDataType")
    @SysLogs(desc = "获取字典数据类型")
    public Map<String, Object> getDataType() {
        return sysDictionaryTypeService.getAll();
    }
}
