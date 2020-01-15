package cn.forest.system.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.system.entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser> {
  
  List<SysUser> getUserByOrgId(Map<String, Object> map);
}
