package cn.forest.system.service.server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  

  @RequestMapping(value = "/list")
  public Object getList(Long page, Long pageSize) {
    Page<SysRole> pages = new Page<SysRole>(page, pageSize);
    IPage<SysRole> selectPage = sysRoleMapper.selectPage(pages, null);
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
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId",userId);
    return sysRoleMapper.getRoleByUserId(map);
  }
  
  
  @RequestMapping("/saveRolePermiss")
  public int saveRolePermiss(@RequestBody SysRolePermissions sysRolePermissions) {
    int result=0;
    if(!StringUtils.isEmpty(sysRolePermissions.getPermissionsIds())) {
      QueryWrapper<SysRolePermissions> wrapper=new QueryWrapper<SysRolePermissions>();
      wrapper.eq("role_id", sysRolePermissions.getRoleId());
      sysRolePermissionsMapper.delete(wrapper);
        String[] permissionsIds = sysRolePermissions.getPermissionsIds().split(",");
        SysRolePermissions srp=null;
        List<SysRolePermissions> entityList=new ArrayList<SysRolePermissions>();
        for (String permissionsId : permissionsIds) {
          srp=new SysRolePermissions();
          srp.setPermissionsId(Long.parseLong(permissionsId));
          srp.setRoleId(sysRolePermissions.getRoleId());
          entityList.add(srp);
        }
        boolean saveBatch = sysRolePermissionsService.saveBatch(entityList);
        result=saveBatch?1:0;
    }
    return result;
  }
  
}
