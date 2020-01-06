package cn.forest.system.service.server;

import java.util.ArrayList;
import java.util.List;

import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.service.SysUserRoleService;
import cn.forest.system.entity.SysUser;
import cn.forest.system.entity.SysUserRole;
import cn.forest.system.mapper.SysUserMapper;
import cn.forest.system.mapper.SysUserRoleMapper;

@RestController
@RequestMapping("/sys_user")
public class SysUserAction {

  @Autowired
  private SysUserMapper sysUserMapper;
  
  @Autowired
  private SysUserRoleMapper sysUserRoleMapper;

  @Autowired
  private SysUserRoleService sysUserRoleService;
  
  
  @RequestMapping("/list")
  public Object getList(Long page, Long pageSize) {
    Page<SysUser> ipage = new Page<SysUser>(page, pageSize);
    IPage<SysUser> selectPage = sysUserMapper.selectPage(ipage, null);
    /*
     * PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""))
     * ;
     */
    /* List<SysUser> selectList = sysUserMapper.selectList(null); */
    /* PageInfo<SysUser> selectPage=new PageInfo<SysUser>(selectList); */
    return new ResultPage<SysUser>(selectPage);
  }

  @RequestMapping("/delete")
  public int delete(Long id) {
    int deleteById = sysUserMapper.deleteById(id);
    return deleteById;
  }

  @RequestMapping("/add")
  public Object save(@RequestBody SysUser user) {
    if(user.getPassword() != null){
      user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }
    int insert = sysUserMapper.insert(user);
    return ResultSave.resultSave(insert, user);
  }

  @RequestMapping("/update")
  public int update(@RequestBody SysUser user) {
    return sysUserMapper.updateById(user);
  }

  @RequestMapping("/getById")
  public Object getById(Long id) {
    return sysUserMapper.selectById(id);
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

}
