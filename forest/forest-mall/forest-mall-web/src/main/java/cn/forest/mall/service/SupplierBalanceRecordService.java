package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.SupplierBalanceRecordRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("supplierBalanceRecordService")
public class SupplierBalanceRecordService {

    @Autowired
    private SupplierBalanceRecordRemote supplierBalanceRecordRemote;

    @Autowired
    private RedisDao redisDao;

    /**
     * 商户余额列表
     *
     * @param request
     * @return
     */
    public Object balanceList(HttpServletRequest request) {
        Map<String, Object> paramMap = getParamMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        
        Object type = userInfoMap.get("type");
        Object typeId = userInfoMap.get("typeId");
        if (type != null && Integer.parseInt(type.toString()) == 1) {
          paramMap.put("supplierId", typeId);
        }
        Object o = supplierBalanceRecordRemote.balanceList(paramMap);
        if (o != null) {
            return ResultMessage.success(o);
        }
        return null;
    }

    /**
     * 商户直充记录列表
     *
     * @param request
     * @return
     */
    public Object recordList(HttpServletRequest request) {
        Map<String, Object> paramMap = getParamMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        Object type = userInfoMap.get("type");
        Object typeId = userInfoMap.get("typeId");
        if (type != null && Integer.parseInt(type.toString()) == 1) {
          paramMap.put("supplierId", typeId);
        }
        Object o = supplierBalanceRecordRemote.recordList(paramMap);
        if (o != null) {
            return ResultMessage.success(o);
        }
        return null;
    }

    private Map<String, Object> getParamMap(HttpServletRequest request) {
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
        return paramMap;
    }

    public Map<String, Object> save(HttpServletRequest request){
        Map<String, Object> paramMap = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            paramMap.put("userId", userInfoMap.get("id"));
            paramMap.put("userName", userInfoMap.get("name"));
        }
        paramMap.put("operationType", 1);
        int save = supplierBalanceRecordRemote.save(paramMap);
        return ResultMessage.result(save, "操作成功","操作失败");
    }
}
