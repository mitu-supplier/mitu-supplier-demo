package cn.forest.system.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysDictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/sysDictionaryType")
@SysLogs(desc = "字典类型")
public class SysDictionaryTypeController {

    @Autowired
    private SysDictionaryTypeService sysDictionaryTypeService;

    @RequestMapping(value = "/list")
    @SysLogs(desc = "字典类型列表")
    public Map<String, Object> list(Long page, Long pageSize,String name){
        return sysDictionaryTypeService.list(page, pageSize,name);
    }

    @RequestMapping(value = "/getById")
    @SysLogs(desc = "获取一条字典类型")
    public Map<String, Object> getById(@RequestParam("id") Long id){
        return  sysDictionaryTypeService.getById(id);
    }

    @RequestMapping(value = "/save")
    @SysLogs(desc = "保存字典类型")
    public Map<String, Object> save(HttpServletRequest request){
        return sysDictionaryTypeService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/update")
    @SysLogs(desc = "修改字典类型")
    public Map<String, Object> update(HttpServletRequest request){
        return sysDictionaryTypeService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/delete")
    @SysLogs(desc = "删除字典类型")
    public Map<String, Object> delete(@RequestParam("id") Long id){
        return sysDictionaryTypeService.delete(id);
    }
}
