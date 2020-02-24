package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productsUpdateService")
public class ProductsUpdateService {

    @Autowired
    private ProductsUpdateRemote productsUpdateRemote;

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private AuditRecodeRemote auditRecodeRemote;

    @Autowired
    private ProductPicRemote productPicRemote;

    @Autowired
    private ProductDeliveryStatusRemote productDeliveryStatusRemote;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> list(HttpServletRequest request) {
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
        Object o = productsUpdateRemote.selectUpdateRecodeList(paramMap);
        if (o != null) {
            return ResultMessage.success(o);
        }
        return null;
    }

    public Map<String, Object> update(HttpServletRequest request) {
        int num = 0;
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            if (type != null && Integer.parseInt(type.toString()) == 1) {
                // 保存商品修改信息
                Long productId = Long.parseLong(map.get("id").toString());
                map.put("productId", productId);
                map.remove("id");
                String imgPaths = StringUtil.toString(map.get("img"));
                map.remove("img");
                Object save = productsUpdateRemote.save(map);
                if (save != null) {
                    Map result = (Map) save;
                    num = Integer.parseInt(result.get(Constant.RESULT_NUM).toString());
                    JsonNode saveJsonNode = JsonUtil.readTree(result.get(Constant.RESULT).toString());
                    Long updateId = saveJsonNode.path("id").asLong();
                    // 处理商品图片
                    productPicRemote.deleteByProduct(updateId, 2);
                    if(imgPaths != null){
                        List<Map<String, Object>> listMap = new ArrayList<>();
                        Map<String, Object> picMap = null;
                        for(String str : imgPaths.split(",")){
                            picMap = new HashMap<>();
                            picMap.put("productId", updateId);
                            picMap.put("path", str);
                            picMap.put("type", 2);
                            listMap.add(picMap);
                        }
                        productPicRemote.save(listMap);
                    }
                    // 保存商品发货状态
                    String deliveryStatus = StringUtil.toString(map.get("deliveryStatus"));
                    productDeliveryStatusRemote.deleteByProductId(updateId, 2);
                    productDeliveryStatusRemote.saveByProductId(updateId, deliveryStatus, 2);
                }
            } else {
                // 管理员修改
                map.put("status", 1);
                return productsService.update(map);
            }
        }
        return ResultMessage.result(num, "修改成功", "修改失败");
    }

    public Map<String, Object> audit(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        Map<String, Object> productUpdateInfo = new HashMap<>();
        Long id = StringUtil.isBlank(map.get("businessId")) ? null : Long.parseLong(map.get("businessId").toString());
        Integer status = StringUtil.isBlank(map.get("auditResult")) ? null : Integer.parseInt(map.get("auditResult").toString());
        productUpdateInfo.put("id", id);
        productUpdateInfo.put("auditStatus", status);
        int update = productsUpdateRemote.updateById(productUpdateInfo);
        // 审核通过修改之前的信息
        if (update > 0) {
            if (status != null && status == 1) {
                Object supplierUpdate = productsUpdateRemote.getById(id);
                if (supplierUpdate != null) {
                    Map infoMap = (Map) supplierUpdate;
                    infoMap.put("created_at", null);
                    infoMap.put("updated_at", null);
                    infoMap.put("id", infoMap.get("productId"));
                    int update1 = productsRemote.update(infoMap);
                    if (update1 == 0) {
                        return ResultMessage.error("审核失败");
                    }else{
                        updateProductPicAndDelivery(Long.parseLong(infoMap.get("productId").toString()), id);
                    }
                }
            }
            // 保存审核记录
            String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
            HashMap userInfoMap = (HashMap) redisDao.getValue(header);
            if (userInfoMap != null) {
                map.put("auditUserId", userInfoMap.get("id"));
                map.put("auditUserName", userInfoMap.get("name"));
            }
            map.put("auditType", 4);
            auditRecodeRemote.save(map);
            return ResultMessage.success("审核成功");
        }
        return ResultMessage.error("审核失败");
    }

    public Map<String, Object> getAuditList(Long id) {
        Object obj = auditRecodeRemote.selectByBusinessId(id, 4);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> view(Long id) {
        Object obj = productsUpdateRemote.getById(id);
        if (obj != null) {
            Map resultMap = (Map) obj;
            Object o = productPicRemote.selectByProduct(id, 2);
            resultMap.put("productPicList", o);
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> batchAudit(HttpServletRequest request) {
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String ids = null;
        if (paramMap.get("ids") != null) {
            ids = paramMap.get("ids").toString();
        }
        Integer status = Integer.parseInt(paramMap.get("status").toString());
        String auditReason = StringUtil.toString(paramMap.get("auditReason"));
        if (ids != null) {
            int auditStatus = productsUpdateRemote.batchAudit(ids, status);
            if (auditStatus > 0) {
                String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
                HashMap userInfoMap = (HashMap) redisDao.getValue(header);
                List<Map<String, Object>> productList = new ArrayList<>();
                List<Map<String, Object>> auditRecodeList = new ArrayList<>();
                Map<String, Object> infoMap = null;
                Map<String, Object> auditMap = null;
                for (String str : ids.split(",")) {
                    Long id = Long.parseLong(str);
                    // 商品信息
                    if (status != null && status == 1) {
                        Object supplierUpdate = productsUpdateRemote.getById(id);
                        if (supplierUpdate != null) {
                            infoMap = (Map) supplierUpdate;
                            infoMap.put("created_at", null);
                            infoMap.put("updated_at", null);
                            infoMap.put("id", infoMap.get("productId"));
                            productList.add(infoMap);
                            updateProductPicAndDelivery(Long.parseLong(infoMap.get("productId").toString()), id);
                        }
                    }
                    // 审核记录
                    auditMap = new HashMap<>();
                    if (userInfoMap != null) {
                        auditMap.put("auditUserId", userInfoMap.get("id"));
                        auditMap.put("auditUserName", userInfoMap.get("name"));
                    }
                    auditMap.put("auditResult", paramMap.get("status"));
                    auditMap.put("businessId", Long.parseLong(str));
                    auditMap.put("auditType", 4);
                    auditMap.put("auditReason", auditReason);
                    auditRecodeList.add(auditMap);
                }
                if (status != null && status == 1) {
                    // 修改商品信息
                    productsRemote.batchUpdate(productList);

                }
                // 保存审核记录
                auditRecodeRemote.batchSave(auditRecodeList);
                return ResultMessage.success("操作成功");
            }
        }
        return ResultMessage.error("操作失败");
    }


    /**
     * 处理商品图片  以及发货状态
     * @param productId
     * @param updateId
     */
    public void updateProductPicAndDelivery(Long productId, Long updateId){
        // 商品图片
        Object o = productPicRemote.selectByProduct(updateId, 2);
        if(o != null){
            productPicRemote.deleteByProduct(productId, 1);
            List<Map<String, Object>> picMapList = new ArrayList<>();
            Map<String, Object> paramMap = null;
            List pics = (List) o;
            for (Object pic : pics) {
                Map picMap = (Map) pic;
                paramMap = new HashMap<>();
                paramMap.put("productId", productId);
                paramMap.put("type", 1);
                paramMap.put("path", picMap.get("path"));
                picMapList.add(paramMap);
            }
            productPicRemote.save(picMapList);
        }
        // 商品发货状态
        Object o1 = productDeliveryStatusRemote.selectByProductId(updateId, 2);
        if(o1 != null){
            productDeliveryStatusRemote.deleteByProductId(productId, 1);
            List<Long> list = new ArrayList<>();
            List productDeliveryList = (List) o1;
            for (Object o2 : productDeliveryList) {
                Map deliveryMap = (Map) o2;
                list.add(Long.parseLong(deliveryMap.get("deliveryStatus").toString()));
            }
            productDeliveryStatusRemote.saveByProductId(productId, StringUtils.join(list, ","), 1);
        }
    }
}
