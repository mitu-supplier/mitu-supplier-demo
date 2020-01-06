package cn.forest.login.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("permissionService")
public class PermissionService {

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> getUserPermissionList(HttpServletRequest request){
        String authorization = request.getHeader(Constant.HEADER_TOKEN_STRING);
        if(authorization != null){
            HashMap userInfoMap = (HashMap)redisDao.getValue(authorization);
            if(userInfoMap != null){
                return ResultMessage.success(userInfoMap.get("permissions"));
            }
        }
        return ResultMessage.error("未获取到用户数据");
    }
}
