package cn.forest.lyj.fallback;


import org.springframework.stereotype.Component;

import cn.forest.lyj.remote.SysUserRemote;


@Component
public class SysUserBack implements SysUserRemote {


  @Override
  public Object getOrgByUserId(Long userId) {
    return null;
  }

  @Override
  public Object selectData(String typeCode, String dataCode) {
    return null;
  }

}
