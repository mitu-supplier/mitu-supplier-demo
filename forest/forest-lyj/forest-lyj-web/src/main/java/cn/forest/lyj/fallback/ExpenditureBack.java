package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.ExpenditureRemote;

@Component
public class ExpenditureBack implements ExpenditureRemote {

  @Override
  public Object list(Long page, Long pageSize, Long userId,String projectName,String orgName,Long orgId) {
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
  public Object getExpenditureCount(Long userId, Long orgId, Long projectId) {
    return null;
  }

}
