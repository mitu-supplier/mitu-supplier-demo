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
    private RedisDao redisDao;

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private SysSequenceRemote sysSequenceRemote;

    @Autowired
    private ProductDeliveryStatusRemote productDeliveryStatusRemote;

    @Autowired
    private ProductDeliveryStatusDataRemote productDeliveryStatusDataRemote;

    @Autowired
    private ProductPicRemote productPicRemote;

    public Map<String, Object> list(HttpServletRequest request, String listType) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            if (type != null && Integer.parseInt(type.toString()) == 1) {
                // 供应商
                paramMap.put("supplierId", typeId);
            }
        }
        if (listType != null) {
            paramMap.put("listType", listType);
        }
        Object obj = productsRemote.list(paramMap);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> getById(Long id) {
        Object obj = productsRemote.getById(id);
        if (obj != null) {
            Map resultMap = (Map) obj;
            Object o = productPicRemote.selectByProduct(id, 1);
            resultMap.put("productPicList", o);
            return ResultMessage.success(resultMap);
        }
        return null;
    }

    public Map<String, Object> save(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            if (!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1) {
                paramMap.put("supplierId", userInfoMap.get("typeId"));
            }
        }
        if (StringUtil.isNotBlank(paramMap.get("auditStatus")) && Integer.parseInt(paramMap.get("auditStatus").toString()) == 0) {
            // 提交审核需要校验
            if (StringUtil.isBlank(paramMap.get("supplierId"))) {
                return ResultMessage.error("请选择商户信息");
            }
            if(StringUtil.isBlank(paramMap.get("deliveryStatus"))){
                return ResultMessage.error("请选择发货状态");
            }
            if(StringUtil.isBlank(paramMap.get("code"))){
                String code = getProductCode();
                while (productsRemote.vaProductCode(null, code, Long.parseLong(StringUtil.toString(paramMap.get("supplierId")))) > 0){
                    code = getProductCode();
                }
                paramMap.put("code", code);
            }else{
                int i = productsRemote.vaProductCode(null, StringUtil.toString(paramMap.get("code")), Long.parseLong(StringUtil.toString(paramMap.get("supplierId"))));
                if(i > 0){
                    return ResultMessage.error("商品编号已存在");
                }
            }
        }
        String imgPaths = StringUtil.toString(paramMap.get("img"));
        paramMap.remove("img");
        Object save = productsRemote.save(paramMap);
        if (save != null) {
            Long productId = Long.parseLong(save.toString());
            //保存商品图片
            productPicRemote.deleteByProduct(productId, 1);
            if(imgPaths != null){
                List<Map<String, Object>> listMap = new ArrayList<>();
                Map<String, Object> picMap = null;
                for(String str : imgPaths.split(",")){
                    picMap = new HashMap<>();
                    picMap.put("productId", productId);
                    picMap.put("path", str);
                    picMap.put("type", 1);
                    listMap.add(picMap);
                }
                productPicRemote.save(listMap);
            }
            // 保存商品发货状态
            String deliveryStatus = StringUtil.toString(paramMap.get("deliveryStatus"));
            productDeliveryStatusRemote.deleteByProductId(productId, 1);
            productDeliveryStatusRemote.saveByProductId(productId, deliveryStatus, 1);
            return ResultMessage.success("操作成功");
        }
        return ResultMessage.error("操作失败");
    }

    public Map<String, Object> update(Map<String, Object> map) {
        if (StringUtil.isNotBlank(map.get("auditStatus")) && Integer.parseInt(map.get("auditStatus").toString()) == 0) {
            if (StringUtil.isBlank(map.get("supplierId"))) {
                return ResultMessage.error("请选择商户信息");
            }
            if(StringUtil.isBlank(map.get("deliveryStatus"))){
                return ResultMessage.error("请选择发货状态");
            }
            if(StringUtil.isBlank(map.get("code"))){
                String code = getProductCode();
                while (productsRemote.vaProductCode(Long.parseLong(StringUtil.toString(map.get("id"))), code, Long.parseLong(StringUtil.toString(map.get("supplierId")))) > 0){
                    code = getProductCode();
                }
                map.put("code", code);
            }else{
                int i = productsRemote.vaProductCode(Long.parseLong(StringUtil.toString(map.get("id"))), StringUtil.toString(map.get("code")), Long.parseLong(StringUtil.toString(map.get("supplierId"))));
                if(i > 0){
                    return ResultMessage.error("商品编号已存在");
                }
            }
        }
        String imgPaths = StringUtil.toString(map.get("img"));
        map.remove("img");
        int update = productsRemote.update(map);
        if(update > 0){
            Long productId = Long.parseLong(StringUtil.toString(map.get("id")));
            //保存商品图片
            productPicRemote.deleteByProduct(productId, 1);
            if(imgPaths != null){
                List<Map<String, Object>> listMap = new ArrayList<>();
                Map<String, Object> picMap = null;
                for(String str : imgPaths.split(",")){
                    picMap = new HashMap<>();
                    picMap.put("productId", productId);
                    picMap.put("path", str);
                    picMap.put("type", 1);
                    listMap.add(picMap);
                }
                productPicRemote.save(listMap);
            }
            // 保存商品发货状态
            String deliveryStatus = StringUtil.toString(map.get("deliveryStatus"));
            productDeliveryStatusRemote.deleteByProductId(productId, 1);
            productDeliveryStatusRemote.saveByProductId(productId, deliveryStatus, 2);
        }
        return ResultMessage.result(update, "操作成功", "操作失败");
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
            return ResultMessage.result(save, "操作提交成功", "操作提交失败");
        }
        return ResultMessage.error("操作提交失败");
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
                    auditMap.put("auditReason", paramMap.get("auditReason"));
                    list.add(auditMap);
                }
                auditRecodeRemote.batchSave(list);

            }
            return ResultMessage.success("操作提交成功");
        }
        return ResultMessage.error("操作提交失败");
    }

    public Map<String, Object> batchDelete(String ids) {
        int i = productsRemote.batchDelete(ids);
        return ResultMessage.result(i, "删除成功", "删除失败");
    }

    /**
     * 查看审核记录
     *
     * @param map
     * @return
     */
    public Map<String, Object> getAuditList(Map<String, Object> map) {
        map.put("auditType", 2);
        Object obj = auditRecodeRemote.list(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * 修改上下架状态   1上架    2下架
     *
     * @param id
     * @param status
     * @return
     */
    public Map<String, Object> updateStatus(Long id, Integer status) {
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("id", id);
        productMap.put("status", status);
        int update = productsRemote.update(productMap);
        return ResultMessage.result(update, "操作成功", "操作失败");
    }

    /**
     * 获取商品编码
     *
     * @return
     */
    public String getProductCode() {
        String name = "0";
        int count = sysSequenceRemote.countByName(name);
        if (count < 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("max", 0L);
            map.put("length", 7);
            map.put("next", 1);
            sysSequenceRemote.save(map);
        }
        return name + sysSequenceRemote.getSeqValue(name);
    }

    /**
     * 获取商品发货状态
     *
     * @return
     */
    public Map<String, Object> getDeliveryStatus() {
        Object list = productDeliveryStatusDataRemote.list();
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    /**
     * 保存商品发货状态
     *
     * @param request
     * @return
     */
    public Map<String, Object> saveDeliveryStatus(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            paramMap.put("userId", userInfoMap.get("id"));
        }
        paramMap.put("isDelete", 0);
        int save = productDeliveryStatusDataRemote.save(paramMap);
        return ResultMessage.result(save, "保存成功", "保存失败");
    }
}
