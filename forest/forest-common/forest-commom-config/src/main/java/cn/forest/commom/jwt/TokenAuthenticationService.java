package cn.forest.commom.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import cn.forest.common.Constant;
import org.json.JSONException;
import org.springframework.util.StringUtils;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.util.SpringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenAuthenticationService {
  public static final long EXPIRATIONTIME = 1*24*60*60*1000; // 5天
  static final String SECRET = "278U@#$%^ki0op23lwe"; // JWT密码

  // JWT生成方法
  public static String addAuthentication(String username) throws JSONException {
    // 生成JWT
    String jwt = Jwts.builder()
        // 保存权限（角色）
        .setSubject(username)
        // 有效期设置
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        // 签名设置
        .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    return jwt;

  }

  public static boolean getAuthenticationUser(HttpServletRequest request) {
    String token = request.getHeader(Constant.HEADER_TOKEN_STRING);

    if (token != null) {
      Claims claims = claims = Jwts.parser()
          // 验签
          .setSigningKey(SECRET)
          // 去掉 Bearer
          .parseClaimsJws(token).getBody();
      // 拿用户名
      String user = claims.getSubject();
      if(!StringUtils.isEmpty(user)) {
        RedisDao bean = SpringUtil.getBean(RedisDao.class);
        if(bean.isNotKey(token)) {
          return true;
        }
      }
    }
    return false;
  }
}
