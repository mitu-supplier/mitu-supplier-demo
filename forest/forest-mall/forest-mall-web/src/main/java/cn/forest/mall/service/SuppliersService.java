package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
            if(map.get("password") == null){
                return ResultMessage.error("请输入密码");
            }
            if(map.get("confirmPassword") == null){
                return ResultMessage.error("请输入确认密码");
            }
            if(!map.get("confirmPassword").equals(map.get("password").toString())){
                return ResultMessage.error("两次密码不一致，请重新输入");
            }
            map.remove("confirmPassword");
            Object user = loginRemote.getUser(map.get("loginName").toString().trim());
            if (user != null) {
                return ResultMessage.error("该账号已注册，请登录后继续");
            }
            Object add = sysUserRemote.add(map);
            if (add != null) {
                Map result = (Map) add;
                int num = Integer.parseInt(result.get(Constant.RESULT_NUM).toString());
                if (num > 0) {
                    return ResultMessage.success(result.get(Constant.RESULT));
                }
            }
        } else {
            Map<String, Object> resultSupplierInfo = new HashMap<>();
            resultSupplierInfo.put("code", getSupplierCode());
            resultSupplierInfo.put("status", -1);
            Object supplierSave = suppliersRemote.save(resultSupplierInfo);
            if (supplierSave != null) {
                Map supMap = (Map) supplierSave;
                if (Integer.parseInt(supMap.get(Constant.RESULT_NUM).toString()) > 0) {
                    JsonNode supplierNode = JsonUtil.readTree(supMap.get(Constant.RESULT).toString());
                    resultSupplierInfo.put("id", supplierNode.path("id").asLong());
                    map.put("type", 1);
                    map.put("typeId", supplierNode.path("id").asLong());
                    int update = sysUserRemote.update(map);
                    if (update > 0) {
                        return ResultMessage.success(resultSupplierInfo);
                    }
                }
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
        for (Object obj : allTypeList) {
            Map type = (Map)obj;
            long id = Long.parseLong(type.get("id").toString());
            Object dataList = sysDictionaryDataRemote.selectByDateTypeId(id);
            if (dataList != null) {
                dataMap.put(type.get("code").toString(), dataList);
            }
        }
        return ResultMessage.success(dataMap);
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
            Object typeId = userInfoMap.get("type_id");
            if (type != null && Integer.parseInt(type.toString()) == 1 && typeId != null) {
                // 供应商
                Object supplier = suppliersRemote.getById(Long.parseLong(typeId.toString()));
                userInfoMap.put("supplier", supplier);
            }
            return ResultMessage.success(userInfoMap);
        }
        return ResultMessage.error("未查询到用户信息");
    }


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
        return name+ sysSequenceRemote.getSeqValue(name);
    }
}
