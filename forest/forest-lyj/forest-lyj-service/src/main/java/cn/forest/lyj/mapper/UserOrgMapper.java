package cn.forest.lyj.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.forest.lyj.entity.UserOrg;

public interface UserOrgMapper extends BaseMapper<UserOrg>{

  List<UserOrg> getOrgByUserId(Map<String, Object> map);
  
  
}
