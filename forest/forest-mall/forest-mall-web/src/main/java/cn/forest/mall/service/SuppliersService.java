package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private SysDictionaryTypeRemote sysDictionaryTypeRemote;

    @Autowired
    private LoginRemote loginRemote;

    @Autowired
    private SysSequenceRemote sysSequenceRemote;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private SysRoleRemote sysRoleRemote;

    /**
     * 商户列表信息
     *
     * @param map
     * @return
     */
    public Map<String, Object> list(Map<String, Object> map) {
        Object obj = suppliersRemote.list(map);
        if (obj != null) {
            return ResultMessage.success(obj);
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
     *
     * @param map
     * @return
     */
    public Map<String, Object> saveStepOneTwo(Map<String, Object> map) {
        Object userId = map.get("id");
        if (userId == null) {
            if (map.get("loginName") == null) {
                return ResultMessage.error("请输入用户名");
            }
            if (map.get("password") == null) {
                return ResultMessage.error("请输入密码");
            }
            if (map.get("confirmPassword") == null) {
                return ResultMessage.error("请输入确认密码");
            }
            if (!map.get("confirmPassword").equals(map.get("password").toString())) {
                return ResultMessage.error("两次密码不一致，请重新输入");
            }
            map.remove("confirmPassword");
            Object user = loginRemote.getUser(map.get("loginName").toString().trim());
            if (user != null) {
                return ResultMessage.error("该账号已注册，请登录后继续");
            }
            map.put("type", 1);
            Object add = sysUserRemote.add(map);
            if (add != null) {
                Map userResult = (Map) add;
                int num = Integer.parseInt(userResult.get(Constant.RESULT_NUM).toString());
                userId = JsonUtil.readTree(userResult.get(Constant.RESULT).toString()).path("id").asLong();
                if (num > 0) {
                    // 生成供应商信息
                    Map<String, Object> resultSupplierInfo = new HashMap<>();
                    String supplierCode = getSupplierCode();
                    resultSupplierInfo.put("code", supplierCode);
                    resultSupplierInfo.put("status", -1);
                    Object supplierSave = suppliersRemote.save(resultSupplierInfo);
                    if (supplierSave != null) {
                        Map supplierResult = (Map) supplierSave;
                        if (Integer.parseInt(supplierResult.get(Constant.RESULT_NUM).toString()) > 0) {
                            Long supplierId = JsonUtil.readTree(supplierResult.get(Constant.RESULT).toString()).path("id").asLong();
                            // 回写用户关联供应商类型ID
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("id", userId);
                            userInfo.put("typeId", supplierId);
                            int update = sysUserRemote.update(userInfo);
                            if (update > 0) {
                                Map<String, Object> resultMap = new HashMap<>();
                                resultMap.put("userId", userId);
                                resultMap.put("supplierId", supplierId);
                                resultMap.put("supplierCode", supplierCode);
                                return ResultMessage.success(resultMap);
                            }
                        }
                    }
                }
            }
        } else {
            int update = sysUserRemote.update(map);
            if (update > 0) {
                // 保存对接招商人员
                Object investmentPerson = map.get("investmentPerson");
                if (investmentPerson != null) {
                    Object userInfo = sysUserRemote.getById(Long.parseLong(userId.toString()));
                    if (userInfo != null) {
                        Map<String, Object> supplierInfo = new HashMap<>();
                        supplierInfo.put("id", ((Map) userInfo).get("typeId"));
                        supplierInfo.put("investmentPerson", investmentPerson);
                        suppliersRemote.update(supplierInfo);
                    }
                }
                return ResultMessage.success("保存成功");
            }
        }
        return ResultMessage.error("保存失败");
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
        if(status != null && status == 1){
            sysRoleRemote.saveSupplierRole(supplierId+"");
        }
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
        int auditStatus = suppliersRemote.batchAudit(ids, status);
        if(status != null && status == 1){
            sysRoleRemote.saveSupplierRole(ids);
        }
        if(auditStatus > 0){
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
                    auditMap.put("auditResult", paramMap.get("status"));
                    auditMap.put("businessId", Long.parseLong(str));
                    auditMap.put("auditType", 1);
                    list.add(auditMap);
                }
                auditRecodeRemote.batchSave(list);
            }
            return ResultMessage.success("操作成功");
        }
        return ResultMessage.error("操作失败");
    }


    /**
     * 查看审核记录
     *
     * @param map
     * @return
     */
    public Map<String, Object> getAuditList(Map<String, Object> map) {
        map.put("auditType", 1);
        Object obj = auditRecodeRemote.list(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 根据code获取字典表数据
     *
     * @return
     */
    public Map<String, Object> getDictionaryData() {
        Map<String, Object> dataMap = new HashMap<>();
        List allTypeList = (List) sysDictionaryTypeRemote.getAll();
        if (allTypeList != null) {
            for (Object obj : allTypeList) {
                Map type = (Map) obj;
                long id = Long.parseLong(type.get("id").toString());
                Object dataList = sysDictionaryDataRemote.selectByDateTypeId(id);
                if (dataList != null) {
                    dataMap.put(type.get("code").toString(), dataList);
                }
            }
            return ResultMessage.success(dataMap);
        }
        return ResultMessage.error("未查询带数据");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public Map<String, Object> delete(Long id) {
        int delete = suppliersRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            userInfoMap.put("password", null);
            if (type != null && Integer.parseInt(type.toString()) == 1 && typeId != null) {
                // 供应商
                Object supplier = suppliersRemote.getById(Long.parseLong(typeId.toString()));
                userInfoMap.put("supplier", supplier);
            }
            return ResultMessage.success(userInfoMap);
        }
        return ResultMessage.error("未查询到用户信息");
    }

    /**
     * 获取供应商code
     *
     * @return
     */
    private String getSupplierCode() {
        String name = "1";
        int count = sysSequenceRemote.countByName(name);
        if (count < 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("max", 0L);
            map.put("length", 6);
            map.put("next", 1);
            sysSequenceRemote.save(map);
        }
        return name + sysSequenceRemote.getSeqValue(name);
    }

    /**
     * 获取供应商详情
     *
     * @param id
     * @return
     */
    public Map<String, Object> view(Long id) {
        Object supplier = suppliersRemote.getById(id);
        if (supplier != null) {
            Map supplerInfo = (Map) supplier;
            Object obj = sysUserRemote.selectByTypeId(id);
            List userList = (List) obj;
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
     * 获取入驻类型
     *
     * @return
     */
    public Map<String, Object> getEnterType() {
        Object childrenByLevel = catalogsRemote.getChildrenByLevel(null);
        if(childrenByLevel != null){
            return ResultMessage.success(childrenByLevel);
        }
        return null;
    }
}
