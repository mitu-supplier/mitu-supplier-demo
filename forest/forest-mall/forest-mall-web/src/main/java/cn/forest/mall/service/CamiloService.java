package cn.forest.mall.service;

import cn.forest.commom.redis.RedisDao;
import cn.forest.common.Constant;
import cn.forest.common.util.*;
import cn.forest.mall.remote.CamiloRemote;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.ProductsRemote;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Value("${file-site-path}")
    private String fileSitePath;

    @Value("${upload-base-path}")
    private String uploadBasePath;

    @Value("${file-upload-path}")
    private String fileUploadPath;

    public Map<String, Object> list(HttpServletRequest request) {
        Map<String, Object> map = RequestMap.requestToMap(request);
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = (HashMap) redisDao.getValue(header);
        if (userInfoMap != null) {
            if (!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1) {
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
            if (!StringUtil.isBlank(userInfoMap.get("type")) && Integer.parseInt(userInfoMap.get("type").toString()) == 1) {
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
     * @return
     */
    public Map<String, Object> importExcel(HttpServletRequest request) throws IOException {
        String header = request.getHeader(Constant.HEADER_TOKEN_STRING);
        HashMap userInfoMap = null;
        if (header != null) {
            userInfoMap = (HashMap) redisDao.getValue(header);
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();
        String filename = FilenameUtils.getName(multipartFile.getOriginalFilename());
        String ctxFileUrl = upload(multipartFile, fileUploadPath, false);
        if(StringUtil.isNotBlank(ctxFileUrl)){
            Map<String, String> fieldsMap = new HashMap<>();
            fieldsMap.put("品目编号", "catalogCode");
            fieldsMap.put("商品编号", "productCode");
            fieldsMap.put("卡号", "cardNumber");
            fieldsMap.put("密码", "cardPassword");
            fieldsMap.put("失效时间", "failureTime");
            fieldsMap.put("供货价", "supplyPrice");
            JSONArray excelData = ExcelUtils.getExcelData(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), fieldsMap);
            if (excelData != null) {
                // 转换excel导入信息
                List<Map<String, Object>> mapList = new ArrayList<>();
                Map<String, Object> camiloMap = null;
                for (int i = 0; i < excelData.size(); i++) {
                    JSONObject jsonObject = excelData.getJSONObject(i);
                    camiloMap = new HashMap<>();
                    camiloMap.put("productCode", jsonObject.get("productCode"));
                    camiloMap.put("catalogCode", jsonObject.get("catalogCode"));
                    camiloMap.put("cardNumber", jsonObject.get("cardNumber"));
                    camiloMap.put("cardPassword", jsonObject.get("cardPassword"));
                    camiloMap.put("failureTime", jsonObject.get("failureTime"));
                    camiloMap.put("supplyPrice", jsonObject.get("supplyPrice"));
                    if (userInfoMap != null) {
                        camiloMap.put("userId", userInfoMap.get("id"));
                        camiloMap.put("userName", userInfoMap.get("name"));
                    }
                    mapList.add(camiloMap);
                }
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("list", mapList);
                paramMap.put("fileName", filename);
                paramMap.put("filePath", ctxFileUrl);
                Object obj = camiloRemote.batchImport(paramMap);
                Map resultMap = (Map) obj;
                if (resultMap != null) {
                    if ("200".equals(StringUtil.toString(resultMap.get("statusCode")))) {
                        return ResultMessage.success("导入成功");
                    } else {
                        return resultMap;
                    }
                }
            }
        }
        return ResultMessage.error("导入失败");
    }

    /**
     * 下载卡密模版
     *
     * @param response
     * @throws IOException
     */
    public void doemloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("templateExample/camiloTemplate.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        FileUtilEx.downloadFile(request, inputStream, response, "卡密导入模板.xlsx");
    }


    public String upload(MultipartFile multipartFile, String uploadPath, boolean relativePath) {
        if (multipartFile == null) {
            return null;
        }
        try {
            String path = FreemarkerUtils.process(uploadPath, null) + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String destPath = uploadBasePath + path;
            File destFile = new File(destPath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            FileUtils.writeByteArrayToFile(destFile, multipartFile.getBytes());
            if (relativePath) {
                return path;
            }
            String urlPath = fileSitePath + path;
            return urlPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
