package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.AuditRecodeRemote;
import cn.forest.mall.remote.SuppliersRemote;
import cn.forest.mall.remote.SysUserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("/suppliersService")
public class SuppliersService {

    @Autowired
    private SuppliersRemote suppliersRemote;

    @Autowired
    private SysUserRemote sysUserRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private RedisDao redisDao;

    /**
     * 商户列表信息
     *
     * @param map
     * @return
     */
    public Map<String, Object> list(Map<String, Object> map) {
        map.put("isDelete", 0);
        Object list = suppliersRemote.list(map);
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> getById(Long id) {
        Object obj = suppliersRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 注册
     *
     * @param map
     * @return
     */
    public Map<String, Object> register(Map<String, Object> map) {
        // 用户信息
        Map userInfoMap = new HashMap();
        userInfoMap.put("loginName", map.get("loginName"));
        userInfoMap.put("password", map.get("password"));
        userInfoMap.put("isDelete", 0);
        userInfoMap.put("isStatus", 0);
        userInfoMap.put("name", map.get("name"));
        sysUserRemote.add(userInfoMap);
        // 商户信息
        map.put("isDelete", 0);
        map.put("status", 0);
        int save = suppliersRemote.save(map);
        return ResultMessage.result(save, "提交成功", "提交失败");
    }

    /**
     * 审核
     *
     * @param request
     * @return
     */
    public Map<String, Object> audit(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        Long supplierId = Long.parseLong(map.get("businessId").toString());
        Integer status = Integer.parseInt(map.get("auditResult").toString());
        suppliersRemote.updateStatus(supplierId, status);
        // 保存审核记录
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap)redisDao.getValue(header);
        if(userInfoMap != null){
            map.put("auditUserId", userInfoMap.get("id"));
            map.put("auditUserName", userInfoMap.get("name"));
        }
        map.put("auditType", 1);
        int save = auditRecodeRemote.save(map);
        return ResultMessage.result(save, "审核成功", "审核失败");
    }

    /**
     * 查看审核记录
     *
     * @param page
     * @param pageSize
     * @param businessId
     * @return
     */
    public Map<String, Object> getAuditList(Long page, Long pageSize, Long businessId) {
        Object obj = auditRecodeRemote.list(page, pageSize, businessId, 1);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }
}
