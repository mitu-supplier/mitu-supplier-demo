package cn.forest.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.lyj.entity.Organization;
import cn.forest.lyj.mapper.OrganizationMapper;
import cn.forest.lyj.mapper.UserOrgMapper;

@RestController
@RequestMapping("/organization")
public class OrganizationAction {

  @Autowired
  private OrganizationMapper organizationMapper;
  
  @Autowired
  private UserOrgMapper userOrgMapper;
  
  @RequestMapping("/list")
  public Object list() {
    QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>();
    queryWrapper.orderByDesc("id");
    queryWrapper.select(new String[] {"name","id","code"});
    return organizationMapper.selectList(queryWrapper);
  }
  
  @RequestMapping("/listfirstLevel")
  public Object listfirstLevel(Long page, Long pageSize,String name) {
    Page<Organization> pages = new Page<Organization>(page, pageSize);
    QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>();
    queryWrapper.orderByDesc("id");
    if(!StringUtils.isEmpty(name)) {
      queryWrapper.like("name", name).or().like("code", name);
    }
    queryWrapper.eq("tree_depth", 1);
    IPage<Organization> selectPage = organizationMapper.selectPage(pages, queryWrapper);
    selectPage.getRecords().forEach(e -> {
      if ("true".equals(e.getIsParent())) {
        e.setHasChildren(true);
      }
    });
    return new ResultPage<Organization>(selectPage);
  }

  @RequestMapping("/listChildren")
  public Object listChildren(Long parentId) {
    QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>();
    queryWrapper.eq("parent_id", parentId);
    List<Organization> selectList = organizationMapper.selectList(queryWrapper);
    return selectList;
  }
  
  @RequestMapping("/add")
  public int save(@RequestBody Organization organization) {
    if(organization.getParentId()!=null) {
      Organization sp = organizationMapper.selectById(organization.getParentId());
      if(sp!=null) {
        organization.setTreeDepth(sp.getTreeDepth()+1);
        if("false".equals(sp.getIsParent())) {
          sp.setIsParent("true");
          organizationMapper.updateById(sp);
        }
      }
    }
    return organizationMapper.insert(organization);
  }
  
  @RequestMapping("/getById")
  public Organization save(Long id) {
    return organizationMapper.selectById(id);
  }
  
  @RequestMapping("/delete")
  public int delete(Long id) {
    int deleteById = organizationMapper.deleteById(id);
    return deleteById;
  }
  
  @RequestMapping("/update")
  public int update(@RequestBody Organization organization) {
    return organizationMapper.updateById(organization);
  }
  
  
  @RequestMapping("/getOrgByUserId")
  public Object getOrgByUserId(Long userId) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    return userOrgMapper.getOrgByUserId(map);
  }
  
  @RequestMapping("/code")
  public Object getOrgByUserId(String code) {
     QueryWrapper<Organization> queryWrapper = new QueryWrapper<Organization>();
    queryWrapper.eq("code", code);
    return organizationMapper.selectOne(queryWrapper);
  }
}
