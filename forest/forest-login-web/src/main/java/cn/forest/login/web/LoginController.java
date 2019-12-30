package cn.forest.login.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.login.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @RequestMapping("/do")
  public Map<String, Object> getUser(String loginName, String password, HttpServletRequest request) {
    return loginService.getUser(loginName, password, request);
  }

  @RequestMapping("/login_out")
  public Map<String, Object> loginOut(HttpServletRequest request) {
    return loginService.loginOut(request);
  }
  
  @RequestMapping("/edit_pass")
  public Map<String, Object> editPass(HttpServletRequest request,String oldPass,String newPass) {
    return loginService.editPass(oldPass,newPass,request);
  }
  
}
