package cn.forest.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.forest.service.SysRolePermissionsService;
import cn.forest.system.entity.SysRolePermissions;
import cn.forest.system.mapper.SysRolePermissionsMapper;

@Service("sysRolePermissionsService")
public class SysRolePermissionsServiceImpl extends ServiceImpl<SysRolePermissionsMapper, SysRolePermissions> implements SysRolePermissionsService {

}
