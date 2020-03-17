package cn.forest.system.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.system.service.SysPermissionsService;
import cn.forest.system.service.SysRoleService;
import cn.forest.system.service.SysUserService;

@RestController
@RequestMapping("/sysUser")
@SysLogs(desc = "用户管理")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;


    @SysLogs(desc = "用户列表")
    @RequestMapping("/list")
    public Map<String, Object> getList(@RequestParam(value = "page") Long page,
                                       @RequestParam(value = "pageSize") Long pageSize,
                                       @RequestParam(value = "loginName", required = false) String loginName,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "phone", required = false) String phone,
                                       @RequestParam(value = "email", required = false) String email) {
        return sysUserService.getList(page, pageSize, loginName, name, phone, email);
    }

    @SysLogs(desc = "删除用户")
    @RequestMapping("/delete")
    public Map<String, Object> delete(Long id) {
        return sysUserService.delete(id);
    }

    @SysLogs(desc = "添加用户")
    @RequestMapping("/add")
    public Map<String, Object> add(HttpServletRequest request) {
        return sysUserService.add(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "修改用户")
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return sysUserService.update(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "查询用户")
    @RequestMapping("/getById")
    public Map<String, Object> getById(Long id) {
        return sysUserService.getById(id);
    }

    @SysLogs(desc = "获取用户角色")
    @RequestMapping("/getRoleByUserId")
    public Map<String, Object> getRoleByUserId(Long userId) {
        return sysRoleService.getRoleByUserId(userId);
    }

    @SysLogs(desc = "添加用户角色")
    @RequestMapping("/saveRole")
    public Map<String, Object> saveRole(HttpServletRequest request) {
        return sysUserService.saveRole(RequestMap.requestToMap(request));
    }

    @RequestMapping("/getOnlineUser")
    public Map<String, Object> getOnlineUser(HttpServletRequest request) {
        return sysUserService.getOnlineUser(request);
    }

    @RequestMapping("/updatePassword")
    public Map<String, Object> updatePassword(HttpServletRequest request){
        return sysUserService.updatePassword(RequestMap.requestToMap(request));
    }
}
