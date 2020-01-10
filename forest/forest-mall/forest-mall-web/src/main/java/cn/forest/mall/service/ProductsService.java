package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productsSercie")
public class ProductsService {

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private SysDictionaryDataRemote sysDictionaryDataRemote;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private SuppliersRemote suppliersRemote;

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> list(Map<String, Object> map) {
        Object obj = productsRemote.list(map);
        if (obj != null) {
            return ResultMessage.success(obj);
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

    public Map<String, Object> save(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        Object code = paramMap.get("code");
        if (code != null) {
            Object o = productsRemote.selectByCode(code.toString());
            if (o != null) {
                List pList = (List) o;
                if (pList.size() > 0) {
                    return ResultMessage.error("保存失败，商品编码已存在");
                }
            }
        }
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            paramMap.put("supplierId", userInfoMap.get("id"));
        }
        paramMap.put("auditStatus", 0);
        Object save = productsRemote.save(paramMap);
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
            // 保存审核记录
            if (ids != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> auditMap = null;
                String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
                HashMap userInfoMap = (HashMap) redisDao.getValue(header);
                for (String str : ids.split(",")) {
                    auditMap = new HashMap<>();
                    if (userInfoMap != null) {
                        auditMap.put("auditUserId", userInfoMap.get("id"));
                        auditMap.put("auditUserName", userInfoMap.get("name"));
                    }
                    auditMap.put("auditResult", paramMap.get("auditStatus"));
                    auditMap.put("businessId", Long.parseLong(str));
                    auditMap.put("auditType", 2);
                    list.add(auditMap);
                }
                auditRecodeRemote.batchSave(list);

            }
            return ResultMessage.success("操作成功");
        }
        return ResultMessage.error("操作失败");
    }

    public Map<String, Object> batchDelete(String ids){
        int i = productsRemote.batchDelete(ids);
        return ResultMessage.result(i, "删除成功", "删除失败");
    }
}
