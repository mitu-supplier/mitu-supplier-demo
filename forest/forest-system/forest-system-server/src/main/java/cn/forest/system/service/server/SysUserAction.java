package cn.forest.system.service.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.forest.common.util.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.service.SysUserRoleService;
import cn.forest.service.UserOrgService;
import cn.forest.system.entity.SysRole;
import cn.forest.system.entity.SysUser;
import cn.forest.system.entity.SysUserRole;
import cn.forest.system.entity.UserOrg;
import cn.forest.system.mapper.SysRoleMapper;
import cn.forest.system.mapper.SysUserMapper;
import cn.forest.system.mapper.SysUserRoleMapper;
import cn.forest.system.mapper.UserOrgMapper;

@RestController
@RequestMapping("/sys_user")
public class SysUserAction {

  @Autowired
  private SysUserMapper sysUserMapper;
  
  @Autowired
  private SysUserRoleMapper sysUserRoleMapper;

  @Autowired
  private SysUserRoleService sysUserRoleService;
  
  @Autowired
  private SysRoleMapper sysRoleMapper;
  
  @Autowired
  private UserOrgService userOrgService;
  
  @Autowired
  private UserOrgMapper userOrgMapper;
  
  
  @RequestMapping("/list")
  public Object getList(Long page, Long pageSize,Long typeId,String name) {
    Page<SysUser> ipage = new Page<SysUser>(page, pageSize);
    QueryWrapper<SysUser> wrapper=new QueryWrapper<SysUser>();
    if(typeId!=null) {
      wrapper.eq("type_id", typeId);
    }
    if(!StringUtils.isEmpty(name)) {
      wrapper.like("login_name", name).or().like("name", name);
    }
    IPage<SysUser> selectPage = sysUserMapper.selectPage(ipage, wrapper);
    
    List<SysUser> records = selectPage.getRecords();    
    records.forEach( e -> {
      Map<String, Object> map=new HashMap<String, Object>();
      map.put("userId",e.getId());
      List<SysRole> roleByUserId = sysRoleMapper.getUserRole(map);
      if(!CollectionUtils.isEmpty(roleByUserId)) {
        e.setRoleNames(roleByUserId.stream().map(t ->t.getRoleName()).collect(Collectors.joining(",")));
      }
      List<UserOrg> orgByUserId = userOrgMapper.getOrgByUserId(map);
      if(!CollectionUtils.isEmpty(orgByUserId)) {
        e.setOrgNames(orgByUserId.stream().map(t ->t.getOrgName()).collect(Collectors.joining(",")));
      }
    });
    /*
     * PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""))
     * ;
     */
    /* List<SysUser> selectList = sysUserMapper.selectList(null); */
    /* PageInfo<SysUser> selectPage=new PageInfo<SysUser>(selectList); */
    
    return new ResultPage<SysUser>(selectPage);
  }

  private List<SysRole> getRoleName() {
    // TODO Auto-generated method stub
    return null;
  }

  @RequestMapping("/delete")
  public int delete(Long id) {
    int deleteById = sysUserMapper.deleteById(id);
    return deleteById;
  }

  @RequestMapping("/add")
  public int save(@RequestBody SysUser user) {
    if(!StringUtils.isEmpty(user.getPassword())){
      user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }
    int insert = sysUserMapper.insert(user);
    if(insert>0) {
      String roleIds = user.getRoleIds();
      if(!StringUtils.isEmpty(roleIds)) {
        String[] roles = roleIds.split(",");
        SysUserRole userRole=null;
        List<SysUserRole> list=new ArrayList<SysUserRole>();
        for (String roleId : roles) {
          userRole=new SysUserRole();
          userRole.setUserId(user.getId());
          userRole.setRoleId(Long.parseLong(roleId));
          list.add(userRole);
        }
        sysUserRoleService.saveBatch(list);
      }
      String orgIds = user.getOrgIds();
      if(!StringUtils.isEmpty(orgIds)) {
        String[] orgs = orgIds.split(",");
        UserOrg userOrg=null;
        List<UserOrg> list=new ArrayList<UserOrg>();
        for (String orgId : orgs) {
          userOrg=new UserOrg();
          userOrg.setUserId(user.getId());
          userOrg.setOrgId(Long.parseLong(orgId));
          list.add(userOrg);
        }
        userOrgService.saveBatch(list);
      }
    }
    return insert;
  }

  @RequestMapping("/update")
  public int update(@RequestBody SysUser user) {
    
    String roleIds = user.getRoleIds();
    if(!StringUtils.isEmpty(roleIds)) {
      QueryWrapper<SysUserRole> wrapper=new QueryWrapper<SysUserRole>();
      wrapper.eq("user_id", user.getId());
      sysUserRoleMapper.delete(wrapper);
      
      String[] roles = roleIds.split(",");
      SysUserRole userRole=null;
      List<SysUserRole> list=new ArrayList<SysUserRole>();
      for (String roleId : roles) {
        userRole=new SysUserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(Long.parseLong(roleId));
        list.add(userRole);
      }
      sysUserRoleService.saveBatch(list);
    }
      String orgIds = user.getOrgIds();
      if(!StringUtils.isEmpty(orgIds)) {
        QueryWrapper<UserOrg> orgwrapper=new QueryWrapper<UserOrg>();
        orgwrapper.eq("user_id", user.getId());
        userOrgMapper.delete(orgwrapper);
        String[] orgs = orgIds.split(",");
        UserOrg userOrg=null;
        List<UserOrg> list=new ArrayList<UserOrg>();
        for (String orgId : orgs) {
          userOrg=new UserOrg();
          userOrg.setUserId(user.getId());
          userOrg.setOrgId(Long.parseLong(orgId));
          list.add(userOrg);
        }
        userOrgService.saveBatch(list);
      }
    return sysUserMapper.updateById(user);
  }

  @RequestMapping("/getById")
  public Object getById(Long id) {
    SysUser sysUser = sysUserMapper.selectById(id);
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId",sysUser.getId());
    List<SysRole> roleByUserId = sysRoleMapper.getUserRole(map);
    if(!CollectionUtils.isEmpty(roleByUserId)) {
      sysUser.setSysRoleList(roleByUserId);
    }
    List<UserOrg> orgByUserId = userOrgMapper.getOrgByUserId(map);
    if(!CollectionUtils.isEmpty(orgByUserId)) {
      sysUser.setOrgIds(orgByUserId.stream().map(t ->t.getOrgId().toString()).collect(Collectors.joining(",")));
      sysUser.setOrgNames(orgByUserId.stream().map(t ->t.getOrgName().toString()).collect(Collectors.joining(",")));
    }
    return sysUser;
  }
  
  @RequestMapping("/saveRole")
  public int saveRole(@RequestBody SysUserRole sysUserRole) {
    int result=0;
    if(!StringUtils.isEmpty(sysUserRole.getRoleIds())) {
      QueryWrapper<SysUserRole> wrapper=new QueryWrapper<SysUserRole>();
      wrapper.eq("user_id", sysUserRole.getUserId());
      sysUserRoleMapper.delete(wrapper);
      String[] roleIds = sysUserRole.getRoleIds().split(",");
      SysUserRole userRole=null;
      List<SysUserRole> list=new ArrayList<SysUserRole>();
      for (String roleId : roleIds) {
        userRole=new SysUserRole();
        userRole.setUserId(sysUserRole.getUserId());
        userRole.setRoleId(Long.parseLong(roleId));
        list.add(userRole);
      }
      boolean saveBatch = sysUserRoleService.saveBatch(list);
      result=saveBatch?1:0;
    }
    return result;
  }

  @RequestMapping("/getOrgByUserId")
  public Object getOrgByUserId(Long userId) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    return userOrgMapper.getOrgByUserId(map);
  }
}
