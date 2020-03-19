package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.AuditRecodeRemote;
import cn.forest.mall.remote.SuppliersRemote;
import cn.forest.mall.remote.SuppliersUpdateRemote;
import cn.forest.mall.remote.SysUserRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("suppliersUpdateService")
public class SuppliersUpdateService {

    @Autowired
    private SuppliersUpdateRemote suppliersUpdateRemote;

    @Autowired
    private SuppliersRemote suppliersRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private SysUserRemote sysUserRemote;

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            if (type != null && Integer.parseInt(type.toString()) == 1) {
                // 供应商
                map.put("supplierId", typeId);
            }
        }
        Object obj = suppliersUpdateRemote.selectUpdateRecodeList(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 修改商户注册信息
     *
     * @param request
     * @return
     */
    public Map<String, Object> updateRegisterInfo(HttpServletRequest request) {
        int num = 0;
        Map<String, Object> map = RequestMap.requestToMap(request);
        if(!StringUtil.isBlank(map.get("name"))){
            int i = suppliersRemote.vaNameOrShotName(map.get("name").toString(), "name", Long.parseLong(map.get("id").toString()));
            if(i > 0){
                return ResultMessage.error("商户名称已经被使用");
            }
        }
        if(!StringUtil.isBlank(map.get("shortName"))){
            int i = suppliersRemote.vaNameOrShotName(map.get("shortName").toString(), "short_name", Long.parseLong(map.get("id").toString()));
            if(i > 0){
                return ResultMessage.error("商户简称已经被使用");
            }
        }
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            if (type != null && Integer.parseInt(type.toString()) == 1) {
                // 保存供应商修改信息
                Map<String, Object> supplierInfoMap = new HashMap<>();
                supplierInfoMap.put("id", map.get("id"));
                supplierInfoMap.put("updateAuditStatus", 0);
                suppliersRemote.update(supplierInfoMap);
                // 供应商
                map.put("supplierId", map.get("id"));
                map.remove("id");
                map.put("updateAuditStatus", 0);
                Object save = suppliersUpdateRemote.save(map);
                if (save != null) {
                    Map result = (Map) save;
                    num = Integer.parseInt(result.get(Constant.RESULT_NUM).toString());
                }
            }else{
                // 管理员修改
                map.put("status", 1);
                num = suppliersRemote.update(map);
                if(num > 0){
                    // 修改姓名和邮箱
                    Object obj = sysUserRemote.selectByTypeId(Long.parseLong(map.get("id").toString()));
                    if(obj != null){
                        List userList = (List) obj;
                        if(userList.size() > 0){
                            Object user = userList.get(0);
                            Map userMap = (Map) user;
                            Map<String, Object> updateMap = new HashMap<>();
                            updateMap.put("name", map.get("userName"));
                            updateMap.put("id", userMap.get("id"));
                            updateMap.put("email", map.get("email"));
                            sysUserRemote.update(updateMap);
                        }
                    }
                }
            }
        }
        return ResultMessage.result(num, "修改成功", "修改失败");
    }

    /**
     * 审核
     *
     * @param request
     * @return
     */
    public Map<String, Object> audit(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        Map<String, Object> supplierUpdateInfo = new HashMap<>();
        Long id = StringUtil.isBlank(map.get("businessId")) ? null : Long.parseLong(map.get("businessId").toString());
        Integer status = StringUtil.isBlank(map.get("auditResult")) ? null : Integer.parseInt(map.get("auditResult").toString());
        supplierUpdateInfo.put("id", id);
        supplierUpdateInfo.put("status", status);
        int update = suppliersUpdateRemote.update(supplierUpdateInfo);
        // 审核通过修改之前的供应商信息
        if (update > 0) {
            Object supplierUpdate = suppliersUpdateRemote.getById(id);
            Long supplierId = null;
            if (supplierUpdate != null) {
                Map infoMap = (Map) supplierUpdate;
                supplierId = Long.parseLong(infoMap.get("supplierId").toString());
                if(status != null && status == 1){
                    infoMap.put("created_at", null);
                    infoMap.put("updated_at", null);
                    infoMap.put("id", supplierId);
                    infoMap.put("updateAuditStatus", 1);
                    infoMap.put("status", 1);
                    infoMap.remove("alertBalance");
                    int update1 = suppliersRemote.update(infoMap);
                    if(update1 == 0){
                        return ResultMessage.error("审核失败");
                    }else{
                        // 修改姓名和邮箱
                        Object obj = sysUserRemote.selectByTypeId(supplierId);
                        if(obj != null){
                            List userList = (List) obj;
                            if(userList.size() > 0){
                                Object user = userList.get(0);
                                Map userMap = (Map) user;
                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("name", infoMap.get("userName"));
                                updateMap.put("id", userMap.get("id"));
                                updateMap.put("email", infoMap.get("email"));
                                sysUserRemote.update(updateMap);
                            }
                        }
                    }
                }else{
                    Map<String, Object> supplierInfoMap = new HashMap<>();
                    supplierInfoMap.put("id", supplierId);
                    supplierInfoMap.put("updateAuditStatus", status);
                    suppliersRemote.update(supplierInfoMap);
                }
            }
            // 保存审核记录
            String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
            HashMap userInfoMap = (HashMap) redisDao.getValue(header);
            if (userInfoMap != null) {
                map.put("auditUserId", userInfoMap.get("id"));
                map.put("auditUserName", userInfoMap.get("name"));
            }
            map.put("auditType", 3);
            map.put("businessId", supplierId);
            auditRecodeRemote.save(map);
            return ResultMessage.success("审核成功");
        }
        return ResultMessage.error("审核失败");
    }

    /**
     * 查询审核记录
     *
     * @param id
     * @return
     */
    public Map<String, Object> getAuditList(Long id) {
        Object obj = auditRecodeRemote.selectByBusinessId(id, "3");
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> view(Long id) {
        Object obj = suppliersUpdateRemote.getById(id);
        if (obj != null) {
            Map supplerInfo = (Map) obj;
            Object user = sysUserRemote.selectByTypeId(Long.parseLong(supplerInfo.get("supplierId").toString()));
            List userList = (List) user;
            if (userList != null && userList.size() > 0) {
                Map userInfo = (Map) userList.get(0);
                userInfo.put("password", null);
                supplerInfo.put("user", userInfo);
            }
            return ResultMessage.success(supplerInfo);
        }
        return null;
    }

    /**
     * 批量审核
     *
     * @param request
     * @return
     */
    public Map<String, Object> batchAudit(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String ids = null;
        if (paramMap.get("ids") != null) {
            ids = paramMap.get("ids").toString();
        }
        Integer status = Integer.parseInt(paramMap.get("status").toString());
        String auditReason = StringUtil.toString(paramMap.get("auditReason"));
        if (ids != null) {
            int auditStatus = suppliersUpdateRemote.batchAudit(ids, status);
            if(auditStatus > 0){
                String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
                HashMap userInfoMap = (HashMap) redisDao.getValue(header);
                List<Map<String, Object>> supplierList = new ArrayList<>();
                List<Map<String, Object>> auditRecodeList = new ArrayList<>();
                Map<String, Object> infoMap = null;
                Map<String, Object> auditMap = null;
                for (String str : ids.split(",")) {
                    Long id = Long.parseLong(str);
                    // 供应商信息
                    Object supplierUpdate = suppliersUpdateRemote.getById(id);
                    Long supplierId = null;
                    if (supplierUpdate != null) {
                        infoMap = (Map) supplierUpdate;
                        supplierId = Long.parseLong(infoMap.get("supplierId").toString());
                        if(status != null && status == 1){
                            infoMap.put("created_at", null);
                            infoMap.put("updated_at", null);
                            infoMap.put("id", supplierId);
                            infoMap.put("updateAuditStatus", 1);
                            infoMap.put("status", 1);
                            infoMap.remove("alertBalance");
                            supplierList.add(infoMap);
                            // 修改姓名和邮箱
                            Object obj = sysUserRemote.selectByTypeId(supplierId);
                            if(obj != null){
                                List userList = (List) obj;
                                if(userList.size() > 0){
                                    Object user = userList.get(0);
                                    Map userMap = (Map) user;
                                    Map<String, Object> updateMap = new HashMap<>();
                                    updateMap.put("name", infoMap.get("userName"));
                                    updateMap.put("id", userMap.get("id"));
                                    updateMap.put("email", infoMap.get("email"));
                                    sysUserRemote.update(updateMap);
                                }
                            }
                        }else {
                            Map<String, Object> supplierInfoMap = new HashMap<>();
                            supplierInfoMap.put("id", supplierId);
                            supplierInfoMap.put("updateAuditStatus", status);
                            supplierList.add(supplierInfoMap);
                        }
                    }
                    // 审核记录
                    auditMap = new HashMap<>();
                    if (userInfoMap != null) {
                        auditMap.put("auditUserId", userInfoMap.get("id"));
                        auditMap.put("auditUserName", userInfoMap.get("name"));
                    }
                    auditMap.put("auditResult", paramMap.get("status"));
                    auditMap.put("businessId", supplierId);
                    auditMap.put("auditType", 3);
                    auditMap.put("auditReason", auditReason);
                    auditRecodeList.add(auditMap);
                }
                // 修改供应商信息
                suppliersRemote.batchUpdate(supplierList);
                // 保存审核记录
                auditRecodeRemote.batchSave(auditRecodeList);
                return ResultMessage.success("操作成功");
            }
        }
        return ResultMessage.error("操作失败");
    }
}
