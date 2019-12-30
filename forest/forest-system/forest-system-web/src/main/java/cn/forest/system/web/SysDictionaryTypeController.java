package cn.forest.system.web;

import cn.forest.common.util.RequestMap;
import cn.forest.system.service.SysDictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/sysDictionaryType")
public class SysDictionaryTypeController {

    @Autowired
    private SysDictionaryTypeService sysDictionaryTypeService;

    @RequestMapping(value = "/list")
    public Map<String, Object> list(){
        return sysDictionaryTypeService.list();
    }

    @RequestMapping(value = "/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id){
        return  sysDictionaryTypeService.getById(id);
    }

    @RequestMapping(value = "/save")
    public Map<String, Object> save(HttpServletRequest request){
        return sysDictionaryTypeService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/update")
    public Map<String, Object> update(HttpServletRequest request){
        return sysDictionaryTypeService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping(value = "/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id){
        return sysDictionaryTypeService.delete(id);
    }
}
