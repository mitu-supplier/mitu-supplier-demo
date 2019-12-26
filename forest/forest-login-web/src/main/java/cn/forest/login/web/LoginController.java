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

  @RequestMapping("/")
  public Map<String, Object> getUser(String loginName, String password, HttpServletRequest request) {
    return loginService.getUser(loginName, password, request);
  }

}
