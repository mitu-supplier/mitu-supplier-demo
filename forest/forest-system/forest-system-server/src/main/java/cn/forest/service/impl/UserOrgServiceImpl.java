package cn.forest.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.forest.service.UserOrgService;
import cn.forest.system.entity.UserOrg;
import cn.forest.system.mapper.UserOrgMapper;

@Service("userOrgService")
public class UserOrgServiceImpl extends ServiceImpl<UserOrgMapper,UserOrg> implements UserOrgService{

}
