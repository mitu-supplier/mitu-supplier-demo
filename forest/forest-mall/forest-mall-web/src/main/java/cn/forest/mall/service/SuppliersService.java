package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.AuditRecodeRemote;
import cn.forest.mall.remote.SuppliersRemote;
import cn.forest.mall.remote.SysDictionaryDataRemote;
import cn.forest.mall.remote.SysUserRemote;
import com.fasterxml.jackson.databind.JsonNode;
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
    private SysDictionaryDataRemote sysDictionaryDataRemote;

    @Autowired
    private RedisDao redisDao;

    /**
     * 商户列表信息
     *
     * @param map
     * @return
     */
    public Map<String, Object> list(Map<String, Object> map) {
        Object list = suppliersRemote.list(map);
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Map<String, Object> getById(Long id) {
        Object obj = suppliersRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 商户注册  保存用户信息
     * @param map
     * @return
     */
    public Map<String, Object> saveStepOneTwo(Map<String, Object> map){
        Object userId = map.get("id");
        if(userId == null){
            int add = sysUserRemote.add(map);
            return ResultMessage.result(add, "保存成功", "保存失败");
        }else{
            Map<String, Object> supplierInfo = new HashMap<>();
            String code = "1000001";
            supplierInfo.put("code", code);
            Object supplierId = suppliersRemote.save(supplierInfo);
            if(supplierId != null){
                supplierInfo.put("id", Long.parseLong(supplierId.toString()));
                map.put("type", 1);
                map.put("typeId", Long.parseLong(supplierId.toString()));
                int update = sysUserRemote.update(map);
                if(update > 0){
                    return ResultMessage.success(supplierInfo);
                }
            }
            return ResultMessage.error("保存失败");
        }
    }

    /**
     * 注册
     *
     * @param map
     * @return
     */
    public Map<String, Object> update(Map<String, Object> map) {
        int update = suppliersRemote.update(map);
        return ResultMessage.result(update, "保存成功", "保存失败");
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
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
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

    /**
     * 根据code获取字典表数据
     *
     * @param code
     * @return
     */
    public Map<String, Object> getDictionaryData(String code) {
        Object obj = sysDictionaryDataRemote.selectByDateTypeCode(code);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public Map<String, Object> delete(Long id){
        int delete = suppliersRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }
}
