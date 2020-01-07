package cn.forest.login.web;

import cn.forest.login.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> list(HttpServletRequest request){
        return permissionService.getUserPermissionList(request);
    }
    
    @RequestMapping(value = "/button", method = RequestMethod.GET)
    public Map<String, Object> button(HttpServletRequest request,String code){
        return permissionService.getRoleButton(request,code);
    }
}
