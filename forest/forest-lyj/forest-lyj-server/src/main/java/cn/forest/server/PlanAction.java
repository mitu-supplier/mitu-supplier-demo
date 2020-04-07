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
import cn.forest.lyj.entity.Plan;
import cn.forest.lyj.mapper.PlanMapper;

@RestController
@RequestMapping("/plan")
public class PlanAction {
  @Autowired
  private PlanMapper planMapper;

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
    List<Plan> projectsList =planMapper.getPlanList(map);
    PageInfo<Plan> pageInfo = new PageInfo<Plan>(projectsList);
    return new ResultPage<Plan>(pageInfo);
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      return planMapper.getPlanById(id);
  }

  @RequestMapping("/save")
  public int save(@RequestBody Plan plan) {
      return planMapper.insert(plan);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Plan plan) {
    return planMapper.updateById(plan);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
    return planMapper.deleteById(id);
      
  }
  
  @RequestMapping("/exportList")
  public Object exportList(Long userId,String projectName,String orgName,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    return planMapper.getPlanList(map);
  }
  
  @RequestMapping("/getPlanListNoUsed")
  public Object list(Integer year,Integer month) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("year", year);
    map.put("month", month);
    String day="";
    if(month.toString().length()==1) {
      day=year+"-"+"0"+month;
    }else {
      day=year+"-"+month;
    }
    map.put("day", day);
    
    List<Plan> projectsList =planMapper.getPlanListNoUsed(map);
    return projectsList;
  }
}
