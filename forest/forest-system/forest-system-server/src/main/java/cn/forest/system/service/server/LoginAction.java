package cn.forest.system.service.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.forest.system.entity.SysUser;
import cn.forest.system.mapper.SysUserMapper;

/**
 * @author 51997
 *
 */
@RestController
@RequestMapping("/login")
public class LoginAction {

  @Autowired
  private SysUserMapper sysUserMapper;

  @RequestMapping("/getUser")
  public Object getLoginUser(String loginName) {
    QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
    queryWrapper.eq("login_name", loginName);
    return sysUserMapper.selectOne(queryWrapper);
  }

}
