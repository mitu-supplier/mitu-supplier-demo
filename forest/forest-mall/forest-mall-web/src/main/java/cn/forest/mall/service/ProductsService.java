package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.AuditRecodeRemote;
import cn.forest.mall.remote.ProductsRemote;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("productsSercie")
public class ProductsService {

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> list(Long page, Long pageSize) {
        Object list = productsRemote.list(page, pageSize);
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> getById(Long id) {
        Object obj = productsRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, Object> map) {
        Object save = productsRemote.save(map);
        if (save != null) {
            return ResultMessage.success("保存成功");
        }
        return ResultMessage.error("保存失败");
    }

    public Map<String, Object> update(Map<String, Object> map) {
        int update = productsRemote.update(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> delete(Long id) {
        int delete = productsRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }

    public Map<String, Object> audit(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        // 修改商品审核状态
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("id", paramMap.get("id"));
        productMap.put("auditStatus", paramMap.get("auditStatus"));
        productMap.put("deliveryTypeCode", paramMap.get("deliveryTypeCode"));
        int update = productsRemote.update(productMap);
        if (update > 0) {
            // 保存审核记录
            Map<String, Object> auditMap = new HashMap<>();
            String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
            HashMap userInfoMap = (HashMap) redisDao.getValue(header);
            if (userInfoMap != null) {
                auditMap.put("auditUserId", userInfoMap.get("id"));
                auditMap.put("auditUserName", userInfoMap.get("name"));
            }
            auditMap.put("auditResult", paramMap.get("auditStatus"));
            auditMap.put("auditReason", paramMap.get("auditReason"));
            auditMap.put("businessId", paramMap.get("id"));
            auditMap.put("auditType", 2);
            int save = auditRecodeRemote.save(auditMap);
            return ResultMessage.result(save, "审核成功", "审核失败");
        }
        return ResultMessage.error("审核失败");
    }

    public Map<String, Object> batchAudit(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String ids = null;
        if (paramMap.get("ids") != null) {
            ids = paramMap.get("ids").toString();
        }
        int auditStatus = productsRemote.batchAudit(ids, Integer.parseInt(paramMap.get("auditStatus").toString()));
        if (auditStatus > 0) {
            // 保存修改记录
            if (ids != null) {
                Map<String, Object> auditMap = new HashMap<>();
                String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
                HashMap userInfoMap = (HashMap) redisDao.getValue(header);
                if (userInfoMap != null) {
                    auditMap.put("auditUserId", userInfoMap.get("id"));
                    auditMap.put("auditUserName", userInfoMap.get("name"));
                }
                for (String str : ids.split(",")) {
                    auditMap.put("auditResult", paramMap.get("auditStatus"));
                    auditMap.put("businessId", Long.parseLong(str));
                    auditMap.put("auditType", 2);
                    auditRecodeRemote.save(auditMap);
                }

            }
            return ResultMessage.error("审核成功");
        }
        return ResultMessage.error("审核失败");
    }
}
