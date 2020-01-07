package cn.forest.server;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.lyj.entity.Expenditure;
import cn.forest.lyj.entity.Organization;
import cn.forest.lyj.entity.Plan;
import cn.forest.lyj.entity.ProjectVo;
import cn.forest.lyj.entity.Projects;
import cn.forest.lyj.mapper.ExpenditureMapper;
import cn.forest.lyj.mapper.PlanMapper;
import cn.forest.lyj.mapper.ProjectsMapper;

@RestController
@RequestMapping("/projects")
public class ProjectsAction {
  
  @Autowired
  private ProjectsMapper projectsMapper;
  
  @Autowired
  private PlanMapper planMapper;
  
  @Autowired
  private ExpenditureMapper expenditureMapper;

  @RequestMapping("/list")
  public Object list(Long page, Long pageSize,Long userId,String projectName,String orgName,String leader,Long orgId) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    map.put("orgId", orgId);
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    List<Projects> projectsList = projectsMapper.getProjectsList(map);
    PageInfo<Projects> pageInfo = new PageInfo<Projects>(projectsList);
    List<Projects> list = pageInfo.getList();
    if(!CollectionUtils.isEmpty(list)) {
      for (Projects projects : list) {
        QueryWrapper<Expenditure> queryWrapper = new QueryWrapper<Expenditure>();
        queryWrapper.eq("project_id", projects.getId());
        List<Expenditure> expendList = expenditureMapper.selectList(queryWrapper);
        BigDecimal total=new BigDecimal("0");
        for (Expenditure et : expendList) {
          total=total.add(new BigDecimal(et.getExpenditureTotal()+""));
        }
        projects.setUsedTotal(total);
        projects.setLaveTotal(new BigDecimal(projects.getBudget()+"").subtract(new BigDecimal(projects.getUsedTotal()+"")));
        projects.setPer(new BigDecimal(projects.getUsedTotal()+"").divide(new BigDecimal(projects.getBudget()+""),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
      }
    }
    return new ResultPage<Projects>(pageInfo);
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      return projectsMapper.getProjectsById(id);
  }

  @RequestMapping("/save")
  public int save(@RequestBody Projects projects) {
      return projectsMapper.insert(projects);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Projects projects) {
    return projectsMapper.updateById(projects);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
    return projectsMapper.deleteById(id);
      
  }
  
  @RequestMapping("/getUserAll")
  public Object getUserAll(Long userId) {
    QueryWrapper<Projects> queryWrapper = new QueryWrapper<Projects>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.orderByDesc("id");
    return projectsMapper.selectList(queryWrapper);
   }
  
  @RequestMapping("/getProjectByOrgId")
  public Object getProjectByOrgId(Long orgId) {
    QueryWrapper<Projects> queryWrapper = new QueryWrapper<Projects>();
    if(orgId!=null) {
      queryWrapper.eq("org_id", orgId);
    }
    return projectsMapper.selectList(queryWrapper);
   }
  
  @RequestMapping("/getProjectsCount")
  public Object getProjectsCount(Long orgId,Long userId,Long id) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("orgId", orgId);
    map.put("userId", userId);
    map.put("id", id);
    return projectsMapper.getProjectsCount(map);
   }
  
  @RequestMapping("/getProjects")
  public Object getProjects(Long orgId,Long userId,Long id,Long page, Long pageSize) { 
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("orgId", orgId);
    map.put("userId", userId);
    map.put("id", id);
    List<Projects> projects = projectsMapper.getProjects(map);
    PageInfo<Projects> pageInfo = new PageInfo<Projects>(projects);
    
    return new ResultPage<Projects>(pageInfo);
  }
}
