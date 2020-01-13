package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.ExpenditureRemote;

@Component
public class ExpenditureBack implements ExpenditureRemote {

  @Override
  public Object list(Long page, Long pageSize, Long userId,String projectName,String orgName,String orgIds) {
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
  public Object getExpenditureCount(Long userId, Long orgId, Long projectId,String orgIds,Integer year) {
    return null;
  }

  @Override
  public Object exportList(Long userId, String projectName, String orgName, String orgIds) {
    return null;
  }

}
