package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.CamiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/camilo")
public class CamiloController {

    @Autowired
    private CamiloService camiloService;

    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request){
        return camiloService.list(RequestMap.requestToMap(request));
    }

    @RequestMapping("/recordList")
    public Map<String, Object> recordList(HttpServletRequest request){
        return camiloService.recordList(RequestMap.requestToMap(request));
    }
}
