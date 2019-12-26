package cn.forest.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.forest.service.SysUserRoleService;
import cn.forest.system.entity.SysUserRole;
import cn.forest.system.mapper.SysUserRoleMapper;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper,SysUserRole> implements SysUserRoleService {

}
