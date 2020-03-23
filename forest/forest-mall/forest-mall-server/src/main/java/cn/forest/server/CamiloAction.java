package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.DateUtil;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Camilo;
import cn.forest.mall.entity.CamiloRecord;
import cn.forest.mall.entity.Products;
import cn.forest.mall.mapper.ProductsMapper;
import cn.forest.service.CamiloRecordService;
import cn.forest.service.CamiloService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camilo")
public class CamiloAction {

    @Autowired
    private CamiloService camiloService;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private CamiloRecordService camiloRecodeService;

    @RequestMapping("/save")
    public Object save(@RequestBody Camilo camilo) {
        boolean save = camiloService.save(camilo);
        return ResultSave.resultSave(save ? 1 : 0, camilo);
    }

    @RequestMapping("/update")
    public int update(@RequestBody Camilo camilo) {
        boolean update = camiloService.updateById(camilo);
        return update ? 1 : 0;
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        boolean delete = camiloService.removeById(id);
        return delete ? 1 : 0;
    }

    @RequestMapping("/batchSave")
    public int batchSave(@RequestBody List<Camilo> camilos) {
        boolean b = camiloService.saveBatch(camilos);
        return b ? 1 : 0;
    }

    @RequestMapping("/selectByProductId")
    public Object selectByProductId(@RequestParam("productId") Long productId) {
        QueryWrapper<Camilo> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.gt("failure_time", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        qw.orderByAsc(new String[]{"failure_time", "id"});
        return camiloService.list(qw);
    }

    @RequestMapping("/countByProductId")
    public int countByProductId(@RequestParam("productId") Long productId) {
        QueryWrapper<Camilo> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.gt("failure_time", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        List<Camilo> list = camiloService.list(qw);
        if (!CollectionUtils.isEmpty(list)) {
            return list.size();
        }
        return 0;
    }

    @RequestMapping("/selectProductCamiloList")
    public Object selectProductCamiloList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            map.put("deliveryCode", "D_KM");
            map.put("auditStatus", 1);
            List<Products> products = productsMapper.selectListByMap(map);
            if (!CollectionUtils.isEmpty(products)) {
                for (Products product : products) {
                    // 卡密数量 失效时间
                    QueryWrapper<Camilo> qw = new QueryWrapper<>();
                    qw.eq("product_id", product.getId());
                    qw.eq("use_flag", 0);
                    qw.gt("failure_time", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
                    qw.orderByAsc(new String[]{"failure_time", "id"});
                    List<Camilo> list = camiloService.list(qw);
                    if (!CollectionUtils.isEmpty(list)) {
                        product.setStock(list.size());
                        product.setFailureTime(list.get(0).getFailureTime());
                    } else {
                        product.setStock(0);
                        product.setFailureTime("--");
                    }
                }
            }
            PageInfo<Products> productsPage = new PageInfo<Products>(products);
            return new ResultPage<Products>(productsPage);
        }
        return null;
    }

    @RequestMapping("/recordList")
    public Object recordList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<CamiloRecord> recordlist = camiloRecodeService.recordlist(map);
            PageInfo<CamiloRecord> productsPage = new PageInfo<CamiloRecord>(recordlist);
            return new ResultPage<CamiloRecord>(productsPage);
        }
        return null;
    }

    @RequestMapping("/batchImport")
    public Object importCamilo(@RequestBody Map<String, Object> map) {
        List list = (List) map.get("list");
        String fileName = StringUtil.isBlank(map.get("fileName")) ? null : map.get("fileName").toString();
        String filePath = StringUtil.isBlank(map.get("filePath")) ? null : map.get("filePath").toString();
        if (!CollectionUtils.isEmpty(list)) {
            Camilo camilo = null;
            List<Camilo> camiloList = new ArrayList<>();
            QueryWrapper<Camilo> qw = null;
            for (Object obj : list) {
                camilo = JsonUtil.toObject(JsonUtil.toJson(obj), Camilo.class);
                // 检验空值
                /*
                 * if (StringUtil.isBlank(camilo.getCatalogCode())) { return
                 * ResultMessage.error("品目编号不能为空"); }
                 */
                if (StringUtil.isBlank(camilo.getProductCode())) {
                    return ResultMessage.error("商品编号不能为空");
                }
                if (StringUtil.isBlank(camilo.getCardNumber())) {
                    return ResultMessage.error("卡号不能为空");
                }
                if (StringUtil.isBlank(camilo.getCardPassword())) {
                    return ResultMessage.error("密码不能为空");
                }
                if (StringUtil.isBlank(camilo.getFailureTime())) {
                    return ResultMessage.error("失效时间不能为空");
                } else {
                    Date date = DateUtil.parseStrToDate(camilo.getFailureTime(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
                    Date nowDate = DateUtil.parseStrToDate(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD), DateUtil.DATE_FORMAT_YYYY_MM_DD);
                    if (date != null && nowDate != null) {
                        if(date.compareTo(nowDate) <= 0){
                            return ResultMessage.error("卡密已过期，请检查后导入");
                        }
                    }
                }
                // 校验商品是否存在
                Products products = productsMapper.selectByCode(camilo.getProductCode(), null);
                if (products == null) {
                    return ResultMessage.error("未找到对应商品，商品编号" + camilo.getProductCode());
                }
                // 判断商品状态
                if (products.getAuditStatus() == null || products.getAuditStatus() != 1) {
                    return ResultMessage.error("商品编号" + camilo.getProductCode() + "状态未审核通过");
                }
                // 校验卡密是否重复
                qw = new QueryWrapper<>();
                qw.eq("product_id", products.getId());
                qw.eq("card_number", camilo.getCardNumber());
                List<Camilo> selectList = camiloService.list(qw);
                if (!CollectionUtils.isEmpty(selectList)) {
                    return ResultMessage.error("同一商品编号下的卡密不能重复，商品编号：" + camilo.getProductCode());
                }
                camilo.setProductId(products.getId());
                camiloList.add(camilo);
            }
            boolean b = camiloService.saveBatch(camiloList);
            if (b) {
                Map<Long, List<Camilo>> mapList = camiloList.stream().collect(Collectors.groupingBy(Camilo::getProductId));
                if (!CollectionUtils.isEmpty(mapList)) {
                    List<CamiloRecord> recordList = new ArrayList<>();
                    CamiloRecord camiloRecord = null;
                    Iterator it = mapList.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        camiloRecord = new CamiloRecord();
                        camiloRecord.setProductId(Long.parseLong(entry.getKey().toString()));
                        List<Camilo> cList = (List<Camilo>) entry.getValue();
                        camiloRecord.setNum(cList.size());
                        if (cList.size() > 0) {
                            camiloRecord.setPrice(cList.get(0).getSupplyPrice());
                            camiloRecord.setUserId(cList.get(0).getUserId());
                            camiloRecord.setUserName(cList.get(0).getUserName());
                        }
                        camiloRecord.setFileName(fileName);
                        camiloRecord.setFilePath(filePath);
                        recordList.add(camiloRecord);
                    }
                    camiloRecodeService.saveBatch(recordList);
                }
            }
        }
        return ResultMessage.success("");
    }
}
