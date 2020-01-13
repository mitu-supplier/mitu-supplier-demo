package cn.forest.lyj.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.forest.lyj.fallback.SysUserBack;



@FeignClient(name = "forest-system-server", fallback = SysUserBack.class)
public interface SysUserRemote {

  @RequestMapping("/sys_user/getOrgByUserId")
  public Object getOrgByUserId(@RequestParam(value = "userId") Long userId);
  
  @RequestMapping("/sysDictionaryData/selectData")
  public Object selectData(@RequestParam(value = "typeCode") String typeCode,@RequestParam(value = "dataCode") String dataCode);
  
}


