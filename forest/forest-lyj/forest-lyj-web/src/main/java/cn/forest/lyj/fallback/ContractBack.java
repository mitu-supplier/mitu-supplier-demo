package cn.forest.lyj.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.ContractRemote;

@Component
public class ContractBack implements ContractRemote {

  @Override
  public Object list(Long page, Long pageSize, Long userId, String contractName, String projectName, String orgName,
      String leader,Long orgId) {
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

}
