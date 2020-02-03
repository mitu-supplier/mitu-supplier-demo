package cn.forest.system.service.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.forest.common.Constant;
import cn.forest.common.util.StringUtil;
import cn.forest.system.entity.SysUser;
import cn.forest.system.entity.SysUserRole;
import cn.forest.system.mapper.SysUserMapper;
import org.apache.commons.collections.FastArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.service.SysRolePermissionsService;
import cn.forest.system.entity.SysRole;
import cn.forest.system.entity.SysRolePermissions;
import cn.forest.system.mapper.SysRoleMapper;
import cn.forest.system.mapper.SysRolePermissionsMapper;
import cn.forest.system.mapper.SysUserRoleMapper;

@RestController
@RequestMapping("sys_role")
public class SysRoleAction {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRolePermissionsMapper sysRolePermissionsMapper;

    @Autowired
    private SysRolePermissionsService sysRolePermissionsService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping(value = "/list")
    public Object getList(@RequestParam(value = "page") Long page,
                          @RequestParam(value = "pageSize") Long pageSize,
                          @RequestParam(value = "roleName", required = false) String roleName,
                          @RequestParam(value = "roleCode", required = false) String roleCode) {
        Page<SysRole> pages = new Page<SysRole>(page, pageSize);
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(roleName)) {
            sysRoleQueryWrapper.like("role_name", roleName);
        }
        if (!StringUtil.isBlank(roleCode)) {
            sysRoleQueryWrapper.like("role_code", roleCode);
        }
        IPage<SysRole> selectPage = sysRoleMapper.selectPage(pages, sysRoleQueryWrapper);
        return new ResultPage<SysRole>(selectPage);
    }


    @RequestMapping(value = "/add")
    public int getList(@RequestBody SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }

    @RequestMapping(value = "/getById")
    public SysRole getList(Long id) {
        return sysRoleMapper.selectById(id);
    }

    @RequestMapping("/delete")
    public int delete(Long id) {
        int deleteById = sysRoleMapper.deleteById(id);
        return deleteById;
    }

    @RequestMapping("/update")
    public int update(@RequestBody SysRole role) {
        return sysRoleMapper.updateById(role);
    }


    @RequestMapping("/getRoleByUserId")
    public Object getRoleByUserId(Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return sysRoleMapper.getRoleByUserId(map);
    }


    @RequestMapping("/getUserRole")
    public Object getUserRole(Long userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return sysRoleMapper.getUserRole(map);
    }

    @RequestMapping("/saveRolePermiss")
    public int saveRolePermiss(@RequestBody SysRolePermissions sysRolePermissions) {
        int result = 0;
        if (!StringUtils.isEmpty(sysRolePermissions.getPermissionsIds())) {
            QueryWrapper<SysRolePermissions> wrapper = new QueryWrapper<SysRolePermissions>();
            wrapper.eq("role_id", sysRolePermissions.getRoleId());
            sysRolePermissionsMapper.delete(wrapper);
            String[] permissionsIds = sysRolePermissions.getPermissionsIds().split(",");
            SysRolePermissions srp = null;
            List<SysRolePermissions> entityList = new ArrayList<SysRolePermissions>();
            for (String permissionsId : permissionsIds) {
                srp = new SysRolePermissions();
                srp.setPermissionsId(Long.parseLong(permissionsId));
                srp.setRoleId(sysRolePermissions.getRoleId());
                entityList.add(srp);
            }
            boolean saveBatch = sysRolePermissionsService.saveBatch(entityList);
            result = saveBatch ? 1 : 0;
        }
        return result;
    }

    @RequestMapping("/saveSupplierRole")
    public int saveSupplierRole(@RequestParam("supplierIds") String supplierIds, @RequestParam("permissionIds") String permissionIds) {
        if (StringUtil.isNotBlank(supplierIds) && StringUtil.isNotBlank(permissionIds)) {
            for (String s : supplierIds.split(",")) {
                QueryWrapper<SysUser> qw = new QueryWrapper<>();
                qw.eq("type_id", Long.parseLong(s));
                List<SysUser> sysUsers = sysUserMapper.selectList(qw);
                if (!CollectionUtils.isEmpty(sysUsers)) {
                    SysUserRole userRole = null;
                    for (String permissionId : permissionIds.split(",")) {
                        userRole = new SysUserRole();
                        userRole.setUserId(sysUsers.get(0).getId());
                        userRole.setRoleId(Long.parseLong(permissionId));
                        sysUserRoleMapper.insert(userRole);
                    }
                }
            }
            return 1;
        }
        return 0;
    }

    @RequestMapping("/getAll")
    public Object getAll() {
        return sysRoleMapper.selectList(new QueryWrapper<SysRole>());
    }

    /**
     * 校验名字或者code是否在重复
     *
     * @param id
     * @param roleName
     * @param roleCode
     * @return
     */
    @RequestMapping("/vaNameOrCode")
    public int vaNameOrCode(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "roleName", required = false) String roleName, @RequestParam(value = "roleCode", required = false) String roleCode) {
        int result = 0;
        QueryWrapper<SysRole> qw = null;
        // 角色名
        if(StringUtil.isNotBlank(roleName)){
            qw = new QueryWrapper<>();
            qw.eq("role_name", roleName);
            if(id != null){
                qw.ne("id", id);
            }
            List<SysRole> sysRoles = sysRoleMapper.selectList(qw);
            if(!CollectionUtils.isEmpty(sysRoles)){
                result += sysRoles.size();
            }
        }
        // code
        if(StringUtil.isNotBlank(roleCode)){
            qw = new QueryWrapper<>();
            qw.eq("role_code", roleCode);
            if(id != null){
                qw.ne("id", id);
            }
            List<SysRole> sysRoles = sysRoleMapper.selectList(qw);
            if(!CollectionUtils.isEmpty(sysRoles)){
                result += sysRoles.size();
            }
        }
        return result;
    }
}
