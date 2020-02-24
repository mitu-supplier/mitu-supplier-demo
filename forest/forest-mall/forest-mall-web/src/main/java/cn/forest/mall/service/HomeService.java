package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
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

@Service("homeService")
public class HomeService {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private SuppliersRemote suppliersRemote;

    @Autowired
    private SuppliersUpdateRemote suppliersUpdateRemote;

    @Autowired
    private SysLoginLogsRemote sysLoginLogsRemote;

    @Autowired
    private ProductsUpdateRemote productsUpdateRemote;

    /**
     * 获取当前登录人信息
     *
     * @param request
     * @return
     */
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            resultMap.put("loginName", userInfoMap.get("loginName"));
            resultMap.put("name", userInfoMap.get("name"));
            Object obj = sysLoginLogsRemote.selectUserLastLoginInfo(Long.parseLong(userInfoMap.get("id").toString()));
            if (obj != null) {
                Map objMap = (Map) obj;
                resultMap.put("loginTime", userInfoMap.get("createTime"));
                resultMap.put("city", objMap.get("city"));
            }
        }
        return ResultMessage.success(resultMap);
    }

    /**
     * 统计待审核信息
     *
     * @param request
     * @return
     */
    public Map<String, Object> getAuditInfo(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        Long supplierId = null;
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            Object type = userInfoMap.get("type");
            Object typeId = userInfoMap.get("typeId");
            if (type != null && Integer.parseInt(type.toString()) == 1) {
                // 供应商
                supplierId = Long.parseLong(StringUtil.toString(typeId));
            }
        }
        List<Map<String, Object>> todoList = new ArrayList<>();
        Map<String, Object> todoMap = null;
        // 商户待审核数量
        Integer supplierAuditNum = 0;
        Object supplierAudit = suppliersRemote.selectList(0, supplierId);
        if (supplierAudit != null) {
            List suppliserAuditList = (List) supplierAudit;
            for (Object o : suppliserAuditList) {
                Map sa = (Map) o;
                if (todoList.size() < 7) {
                    todoMap = new HashMap<>();
                    todoMap.put("id", sa.get("id"));
                    todoMap.put("title", "商户 " + StringUtil.toString(sa.get("name")) + " 入驻待审核");
                    todoMap.put("type", 1);
                    todoList.add(todoMap);
                }
            }
            supplierAuditNum = suppliserAuditList.size();
        }
        resultMap.put("supplierAuditNum", supplierAuditNum);
        // 商户修改待审核数量
        todoMap = null;
        Integer supplierUpdateAuditNum = 0;
        Object suppliersUpdate = suppliersUpdateRemote.selectList(0, supplierId);
        if (suppliersUpdate != null) {
            List suppliersUpdateList = (List) suppliersUpdate;
            for (Object o : suppliersUpdateList) {
                Map sa = (Map) o;
                if (todoList.size() < 7) {
                    todoMap = new HashMap<>();
                    todoMap.put("id", sa.get("id"));
                    todoMap.put("title", "商户 " + StringUtil.toString(sa.get("name")) + " 信息修改待审核");
                    todoMap.put("type", 2);
                    todoList.add(todoMap);
                }
            }
            supplierUpdateAuditNum = suppliersUpdateList.size();
        }
        resultMap.put("supplierUpdateAuditNum", supplierUpdateAuditNum);
        // 商品待审核数量
        todoMap = null;
        Integer productAuditNum = 0;
        Object products = productsRemote.selectList(0, supplierId);
        if (products != null) {
            List productList = (List) products;
            for (Object o : productList) {
                Map sa = (Map) o;
                if (todoList.size() < 7) {
                    todoMap = new HashMap<>();
                    todoMap.put("id", sa.get("id"));
                    todoMap.put("title", "商品 " + StringUtil.toString(sa.get("name")) + " 待审核");
                    todoMap.put("type", 3);
                    todoList.add(todoMap);
                }
            }
            productAuditNum = productList.size();
        }
        resultMap.put("productAuditNum", productAuditNum);
        // 商品修改待审核数量
        int productUpdateAuditNum = 0;
        todoMap = null;
        Object productUpdate = productsUpdateRemote.selectList(0, supplierId);
        if(productUpdate != null){
            List productUpdateList = (List) productUpdate;
            for (Object o : productUpdateList) {
                Map sa = (Map) o;
                if (todoList.size() < 7) {
                    todoMap = new HashMap<>();
                    todoMap.put("id", sa.get("id"));
                    todoMap.put("title", "商品 " + StringUtil.toString(sa.get("name")) + " 信息修改待审核");
                    todoMap.put("type", 4);
                    todoList.add(todoMap);
                }
            }
            productUpdateAuditNum = productUpdateList.size();
        }
        resultMap.put("productUpdateAuditNum", productUpdateAuditNum);
        // 商品数量
        Integer productNum = 0;
        Object o = productsRemote.selectList(null, supplierId);
        if (o != null) {
            List productList = (List) o;
            productNum = productList.size();
        }
        resultMap.put("productNum", productNum);
        resultMap.put("todoList", todoList);
        return ResultMessage.success(resultMap);
    }

    /**
     * 最近7天访问数量
     *
     * @return
     */
    public Map<String, Object> count7Days() {
        Object o = sysLoginLogsRemote.count7Days();
        if (o != null) {
            return ResultMessage.success(o);
        }
        return null;
    }
}
