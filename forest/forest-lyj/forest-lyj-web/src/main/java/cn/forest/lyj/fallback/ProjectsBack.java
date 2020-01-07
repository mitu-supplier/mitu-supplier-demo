package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.ProjectsRemote;
@Component
public class ProjectsBack implements ProjectsRemote {

  @Override
  public Object list(Long page, Long pageSize,Long userId,String projectName,String orgName,String leader,Long orgId) {
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
  public Object getUserAll(Long userId) {
    return null;
  }

  @Override
  public Object getProjectByOrgId(Long orgId) {
    return null;
  }

  @Override
  public Object getProjectsCount(Long orgId, Long userId, Long id) {
    return null;
  }

  @Override
  public Object getProjects(Long page, Long pageSize,Long orgId, Long userId, Long id) {
    return null;
  }

}
