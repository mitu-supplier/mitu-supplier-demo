package cn.forest.system.fallback;

import org.springframework.stereotype.Component;

import cn.forest.system.remote.OrganizationRemote;


@Component
public class OrganizationBack implements OrganizationRemote {

  @Override
  public Object list() {
    return null;
  }

  @Override
  public Object getById(Long id) {
    return null;
  }


  

}
