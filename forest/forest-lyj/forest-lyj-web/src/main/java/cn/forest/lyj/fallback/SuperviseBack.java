package cn.forest.lyj.fallback;

import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.SuperviseRemote;

@Component
public class SuperviseBack implements SuperviseRemote {

  @Override
  public Object projectsList(Long page, Long pageSize, Long userId, String projectName, String orgName, String leader,Long orgId) {
    return null;
  }

  @Override
  public Object contractList(Long page, Long pageSize, Long userId, String contractName, String projectName,
      String orgName, String leader,Long orgId) {
    return null;
  }

  @Override
  public Object planList(Long page, Long pageSize, Long userId, String projectName, String orgName,Long orgId) {
    return null;
  }

  @Override
  public Object expenditureList(Long page, Long pageSize, Long userId, String projectName, String orgName,Long orgId) {
    return null;
  }

}
