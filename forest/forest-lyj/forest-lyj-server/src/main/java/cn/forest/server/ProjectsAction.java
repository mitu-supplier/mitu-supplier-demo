package cn.forest.server;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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

  
  
  @RequestMapping("/exportList")
  public Object exportList(Long userId,String projectName,String orgName,String leader,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    List<Projects> projectsList = projectsMapper.getProjectsList(map);
    if(!CollectionUtils.isEmpty(projectsList)) {
      for (Projects projects : projectsList) {
        QueryWrapper<Expenditure> queryWrapper = new QueryWrapper<Expenditure>();
        queryWrapper.eq("project_id", projects.getId());
        queryWrapper.eq("status", 1);
        List<Expenditure> expendList = expenditureMapper.selectList(queryWrapper);
        BigDecimal total=new BigDecimal("0");
        for (Expenditure et : expendList) {
          total=total.add(new BigDecimal(et.getExpenditureTotal()+""));
        }
        projects.setUsedTotal(total);
        projects.setLaveTotal(new BigDecimal(projects.getBudget()+"").subtract(new BigDecimal(projects.getUsedTotal()+"")));
        if(projects.getBudget().compareTo(BigDecimal.ZERO)==0) {
          projects.setPer(BigDecimal.ZERO);
        }else {
          projects.setPer(new BigDecimal(projects.getUsedTotal()+"").divide(new BigDecimal(projects.getBudget()+""),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
        }
      }
    }
    return projectsList;
  }
  
  @RequestMapping("/list")
  public Object list(Long page, Long pageSize,Long userId,String projectName,String orgName,String leader,String orgIds) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("userId", userId);
    map.put("projectName", projectName);
    map.put("orgName", orgName);
    map.put("leader", leader);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    List<Projects> projectsList = projectsMapper.getProjectsList(map);
    PageInfo<Projects> pageInfo = new PageInfo<Projects>(projectsList);
    List<Projects> list = pageInfo.getList();
    if(!CollectionUtils.isEmpty(list)) {
      for (Projects projects : list) {
        QueryWrapper<Projects> parentqueryWrapper = new QueryWrapper<Projects>();
        parentqueryWrapper.eq("parent_id", projects.getId());
        List<Projects> selectList = projectsMapper.selectList(parentqueryWrapper);
        for (Projects projects2 : selectList) {
          
          projects2.setUsedTotal(BigDecimal.ZERO);
          projects2.setLaveTotal(projects2.getBudget());
          projects2.setPer(BigDecimal.ZERO);
            QueryWrapper<Projects> parentqueryWrapper2 = new QueryWrapper<Projects>();
            parentqueryWrapper2.eq("parent_id", projects2.getId());
            List<Projects> selectList2 = projectsMapper.selectList(parentqueryWrapper2);
            for (Projects projects3 : selectList2) {
              projects3.setUsedTotal(BigDecimal.ZERO);
              projects3.setLaveTotal(projects3.getBudget());
              projects3.setPer(BigDecimal.ZERO);
            }
            projects2.setChildren(selectList2);
        }
        projects.setChildren(selectList);
        
        QueryWrapper<Expenditure> queryWrapper = new QueryWrapper<Expenditure>();
        queryWrapper.eq("project_id", projects.getId());
        queryWrapper.eq("status", 1);
        List<Expenditure> expendList = expenditureMapper.selectList(queryWrapper);
        BigDecimal total=new BigDecimal("0");
        for (Expenditure et : expendList) {
          total=total.add(new BigDecimal(et.getExpenditureTotal()+""));
        }
        projects.setUsedTotal(total);
        projects.setLaveTotal(new BigDecimal(projects.getBudget()+"").subtract(new BigDecimal(projects.getUsedTotal()+"")));
        if(projects.getBudget().compareTo(BigDecimal.ZERO)==0) {
          projects.setPer(BigDecimal.ZERO);
        }else {
          projects.setPer(new BigDecimal(projects.getUsedTotal()+"").divide(new BigDecimal(projects.getBudget()+""),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
        }
        
      }
    }
    return new ResultPage<Projects>(pageInfo);
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      Projects projectsById = projectsMapper.getProjectsById(id);
      QueryWrapper<Expenditure> queryWrapper = new QueryWrapper<Expenditure>();
      queryWrapper.eq("project_id", projectsById.getId());
      queryWrapper.eq("status", 1);
      List<Expenditure> expendList = expenditureMapper.selectList(queryWrapper);
      BigDecimal total=new BigDecimal("0");
      for (Expenditure et : expendList) {
        total=total.add(new BigDecimal(et.getExpenditureTotal()+""));
      }
      projectsById.setUsedTotal(total);
      projectsById.setLaveTotal(new BigDecimal(projectsById.getBudget()+"").subtract(new BigDecimal(projectsById.getUsedTotal()+"")));
      if(projectsById.getBudget().compareTo(BigDecimal.ZERO)==0) {
        projectsById.setPer(BigDecimal.ZERO);
      }else {
        projectsById.setPer(new BigDecimal(projectsById.getUsedTotal()+"").divide(new BigDecimal(projectsById.getBudget()+""),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
      }
      return projectsById;
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
  public Object getUserAll(Long userId,Long orgId,Integer year) {
    QueryWrapper<Projects> queryWrapper = new QueryWrapper<Projects>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("project_level", 1);
    queryWrapper.eq("parent_id", 0);
    queryWrapper.eq("status", 1);
    if (orgId!=null) {
      queryWrapper.eq("org_id", orgId);
    }
    if (year!=null) {
      queryWrapper.eq("year", year);
    }
    queryWrapper.orderByDesc("id");
    return projectsMapper.selectList(queryWrapper);
   }
  
  @RequestMapping("/getProjectByOrgId")
  public Object getProjectByOrgId(Long orgId,Integer year,Long parentId) {
    QueryWrapper<Projects> queryWrapper = new QueryWrapper<Projects>();
    if(orgId!=null) {
      queryWrapper.eq("org_id", orgId);
    }
    if(year!=null) {
      queryWrapper.eq("year", year);
    }
    if(parentId!=null) {
      queryWrapper.eq("parent_id", 0);
    }
    queryWrapper.eq("status", 1);
    return projectsMapper.selectList(queryWrapper);
   }
  
  @RequestMapping("/getProjectsCount")
  public Object getProjectsCount(Long orgId,Long userId,Long id,String orgIds,Integer year) {
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("orgId", orgId);
    map.put("userId", userId);
    map.put("id", id);
    map.put("year", year);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    return projectsMapper.getProjectsCount(map);
   }
  
  @RequestMapping("/getProjects")
  public Object getProjects(Long orgId,Long userId,Long id,Long page, Long pageSize,String orgIds,Integer year,Double num) { 
    PageHelper.startPage(Integer.parseInt(page+""),Integer.parseInt(pageSize+""));
    Map<String, Object> map=new HashMap<String, Object>();
    map.put("orgId", orgId);
    map.put("userId", userId);
    map.put("id", id);
    if(!StringUtils.isEmpty(orgIds)) {
      map.put("orgIds", orgIds.split(","));
    }
    map.put("num", num==null?0.3:num);
    map.put("year", year);
    List<Projects> projects = projectsMapper.getProjects(map);
    PageInfo<Projects> pageInfo = new PageInfo<Projects>(projects);
    
    return new ResultPage<Projects>(pageInfo);
  }
  
  @RequestMapping("/getProjectsByParentId")
  public Object getProjectsByParentId(Long parentId) {
    List<Projects> projectsByParentId = projectsMapper.getProjectsByParentId(parentId);
    for (Projects projects : projectsByParentId) {
      QueryWrapper<Projects> parentqueryWrapper = new QueryWrapper<Projects>();
      parentqueryWrapper.eq("parent_id", projects.getId());
      List<Projects> selectList = projectsMapper.selectList(parentqueryWrapper);
      if(!CollectionUtils.isEmpty(selectList)) {
        projects.setHasChildren(true);
      }else {
        projects.setHasChildren(false);
      }
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
    return projectsByParentId;
  }
  
}
