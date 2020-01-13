package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.ExcelUtils;
import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.remote.CamiloRemote;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.ProductsRemote;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("camiloService")
public class CamiloService {

    @Autowired
    private ProductsRemote productsRemote;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private CamiloRemote camiloRemote;

    @Autowired
    private RedisDao redisDao;

    public Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            if(!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1){
                map.put("supplierId", userInfoMap.get("typeId"));
            }
        }
        Object obj = camiloRemote.selectProductCamiloList(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> recordList(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            if(!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1){
                map.put("supplierId", userInfoMap.get("typeId"));
            }
        }
        Object obj = camiloRemote.recordList(map);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    /**
     * excel 导入卡密
     *
     * @param request
     * @param response
     * @return
     */
    public Map<String, Object> importExcel(HttpServletRequest request, HttpServletResponse response) {
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = null;
        if(header != null){
            userInfoMap = (HashMap) redisDao.getValue(header);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();

        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("品目编号", "catalogCode");
        fieldsMap.put("商品编号", "productCode");
        fieldsMap.put("卡号", "cardNumber");
        fieldsMap.put("密码", "cardPassword");
        fieldsMap.put("失效时间", "failureTime");
        fieldsMap.put("供货价", "supplyPrice");
        JSONArray excelData = ExcelUtils.getExcelData(file, fieldsMap);
        if(excelData != null){
            List<Map<String, Object>> mapList = new ArrayList<>();
            Map<String, Object> camiloMap = null;
            for (int i = 0; i < excelData.size(); i++){
                JSONObject jsonObject = excelData.getJSONObject(i);
                camiloMap = new HashMap<>();
                camiloMap.put("productCode", jsonObject.get("productCode"));
                camiloMap.put("catalogCode", jsonObject.get("catalogCode"));
                camiloMap.put("cardNumber", jsonObject.get("cardNumber"));
                camiloMap.put("cardPassword", jsonObject.get("cardPassword"));
                camiloMap.put("failureTime", jsonObject.get("failureTime"));
                camiloMap.put("supplyPrice", jsonObject.get("supplyPrice"));
                if(userInfoMap != null){
                    camiloMap.put("userId", userInfoMap.get("id"));
                    camiloMap.put("userName", userInfoMap.get("name"));
                }
                mapList.add(camiloMap);
            }
            Object obj = camiloRemote.batchImport(mapList);
            Map resultMap = (Map) obj;
            if(resultMap != null){
                if("200".equals(StringUtil.toString(resultMap.get("statusCode")))){
                    return ResultMessage.success("导入成功");
                }else{
                    return resultMap;
                }
            }
        }
        //如果不需要File文件可删除
        if (file.exists()) {
            file.delete();
        }
        return ResultMessage.error("导入失败");
    }
}
