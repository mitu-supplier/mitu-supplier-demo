package cn.forest.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.lyj.entity.Expenditure;
import cn.forest.lyj.mapper.ExpenditureMapper;

@RestController
@RequestMapping("/expenditure")
public class ExpenditureAction {
  @Autowired
  private ExpenditureMapper expenditureMapper;

  
  
  @RequestMapping("/exportList")
  public Object exportList(Long userId,String projectName,String orgName,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    List<Expenditure> projectsList =expenditureMapper.getExpenditureList(map);
    return projectsList;
  }
  @RequestMapping("/list")
  public Object list(Long page, Long pageSize,Long userId,String projectName,String orgName,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    List<Expenditure> projectsList =expenditureMapper.getExpenditureList(map);
    PageInfo<Expenditure> pageInfo = new PageInfo<Expenditure>(projectsList);
    return new ResultPage<Expenditure>(pageInfo);
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      return expenditureMapper.getExpenditureById(id);
  }

  @RequestMapping("/save")
  public int save(@RequestBody Expenditure expenditure) {
      return expenditureMapper.insert(expenditure);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Expenditure expenditure) {
    return expenditureMapper.updateById(expenditure);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
    return expenditureMapper.deleteById(id);
      
  }
  
  
  @RequestMapping("/getExpenditureCount")
  public Object getExpenditureCount(Long orgId,Long userId,Long projectId,String orgIds,Integer year) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("orgId", orgId);
    map.put("userId", userId);
    map.put("projectId", projectId);
    map.put("year", year);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    return expenditureMapper.getExpenditureCount(map);
      
  }
  
}
