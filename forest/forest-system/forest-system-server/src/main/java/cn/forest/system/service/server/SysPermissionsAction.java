package cn.forest.system.service.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysPermissions;
import cn.forest.system.mapper.SysPermissionsMapper;

@RestController
@RequestMapping("sys_permissions")
public class SysPermissionsAction {
  @Autowired
  private SysPermissionsMapper sysPermissionsMapper;
  
  

  @RequestMapping("/list")
  public Object getSysPermissions() {
    QueryWrapper<SysPermissions> queryWrapper = new QueryWrapper<SysPermissions>();
    queryWrapper.orderByAsc(new String[]{"tree_depth","priority"});
    return sysPermissionsMapper.selectList(queryWrapper);
  }
  

  @RequestMapping("/listfirstLevel")
  public Object listfirstLevel(Long page, Long pageSize) {
    Page<SysPermissions> pages = new Page<SysPermissions>(page, pageSize);
    QueryWrapper<SysPermissions> queryWrapper = new QueryWrapper<SysPermissions>();
    queryWrapper.eq("tree_depth", 1);
    IPage<SysPermissions> selectPage = sysPermissionsMapper.selectPage(pages, queryWrapper);
    selectPage.getRecords().forEach(e -> {
      if ("true".equals(e.getIsParent())) {
        e.setHasChildren(true);
      }
    });
    return new ResultPage<SysPermissions>(selectPage);
  }

  @RequestMapping("/listChildren")
  public Object listChildren(Long parentId) {
    QueryWrapper<SysPermissions> queryWrapper = new QueryWrapper<SysPermissions>();
    queryWrapper.eq("parent_id", parentId);
    List<SysPermissions> selectList = sysPermissionsMapper.selectList(queryWrapper);
    return selectList;
  }
  
  @RequestMapping("/add")
  public int save(@RequestBody SysPermissions sysPermissions) {
    if(sysPermissions.getParentId()!=null) {
      SysPermissions sp = sysPermissionsMapper.selectById(sysPermissions.getParentId());
      if(sp!=null) {
        sysPermissions.setTreeDepth(sp.getTreeDepth()+1);
        if("false".equals(sp.getIsParent())) {
          sp.setIsParent("true");
          sysPermissionsMapper.updateById(sp);
        }
      }
    }
    return sysPermissionsMapper.insert(sysPermissions);
  }
  
  @RequestMapping("/getById")
  public SysPermissions save(Long id) {
    return sysPermissionsMapper.selectById(id);
  }
  
  @RequestMapping("/delete")
  public int delete(Long id) {
    int deleteById = sysPermissionsMapper.deleteById(id);
    return deleteById;
  }
  
  @RequestMapping("/update")
  public int update(@RequestBody SysPermissions sysPermissions) {
    return sysPermissionsMapper.updateById(sysPermissions);
  }
  
  @RequestMapping("/getPermissByRoleId")
  public Object getPermissByRoleId(Long roleId) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("roleId",roleId);
    return sysPermissionsMapper.getPermissionByRoleId(map);
  }

}
