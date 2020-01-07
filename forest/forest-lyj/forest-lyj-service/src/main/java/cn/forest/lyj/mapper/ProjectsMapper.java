package cn.forest.lyj.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.lyj.entity.ProjectVo;
import cn.forest.lyj.entity.Projects;

public interface ProjectsMapper extends BaseMapper<Projects> {

    List<Projects> getProjectsList(Map<String, Object> map);
    
    Projects getProjectsById(@Param("id") Long id);
    
    
    ProjectVo getProjectsCount(Map<String, Object> map);
    
    
    List<Projects> getProjects(Map<String, Object> map);
  
}
