package cn.forest.login.web;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.util.ResultMessage;
import cn.forest.login.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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
}
