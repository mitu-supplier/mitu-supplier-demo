package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.ProjectsRemote;
@Component
public class ProjectsBack implements ProjectsRemote {

  @Override
  public Object list(Long page, Long pageSize,Long userId,String projectName,String orgName,String leader,String orgIds) {
    return null;
  }

  @Override
  public Object getById(Long id) {
    return null;
  }

  @Override
  public int save(Map<String, ?> map) {
    return 0;
  }

  @Override
  public int update(Map<String, ?> map) {
    return 0;
  }

  @Override
  public int delete(Long id) {
    return 0;
  }

  @Override
  public Object getUserAll(Long userId,Long orgId,Integer year) {
    return null;
  }

  @Override
  public Object getProjectByOrgId(Long orgId,Integer year,Long parentId) {
    return null;
  }

  @Override
  public Object getProjectsCount(Long orgId, Long userId, Long id,String orgIds,Integer year) {
    return null;
  }

  @Override
  public Object getProjects(Long page, Long pageSize,Long orgId, Long userId, Long id,String orgIds,Integer year,Double num) {
    return null;
  }

  @Override
  public Object getProjectsByParentId(Long parentId) {
    return null;
  }

  @Override
  public Object exportList(Long userId, String projectName, String orgName, String leader, String orgIds) {
    return null;
  }

}
