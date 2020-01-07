package cn.forest.login.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import cn.forest.login.remote.SysPermissionsRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("permissionService")
public class PermissionService {

    @Autowired
    private RedisDao redisDao;
    
    @Autowired
    private SysPermissionsRemote sysPermissionsRemote;

    public Map<String, Object> getUserPermissionList(HttpServletRequest request){
        String authorization = request.getHeader(Constant.HEADER_TOKEN_STRING);
        if(authorization != null){

        }
        HashMap userInfoMap = (HashMap)redisDao.getValue(authorization);
        if(userInfoMap != null){
            return ResultMessage.success(userInfoMap.get("permissions"));
        }
        return ResultMessage.error("未获取到用户数据");
    }
    
    public Map<String, Object> getRoleButton(HttpServletRequest request,String urlCode){
      String authorization = request.getHeader(Constant.HEADER_TOKEN_STRING);
      Map<String, Object> resultMap=new HashMap<String, Object>();
      HashMap userInfoMap = (HashMap)redisDao.getValue(authorization);
      Long userId=Long.parseLong(userInfoMap.get("id").toString());
      Map per = (Map) sysPermissionsRemote.getpermissionByCode(urlCode.replaceAll("/", ""));
      if(per!=null) {
        List roleButton = (List) sysPermissionsRemote.getRoleButton(userId, Long.parseLong(per.get("id").toString()));
        if(!CollectionUtils.isEmpty(roleButton)) {
          for (Object object : roleButton) {
            Map button=(Map) object;
            resultMap.put(button.get("url").toString(), button.get("name"));
          }
          return ResultMessage.success(resultMap);
        }
      }
      return null;
    }
}
