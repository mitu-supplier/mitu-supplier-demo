package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Catalogs;
import cn.forest.mall.entity.ProductDeliveryStatus;
import cn.forest.mall.entity.ProductsUpdate;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.CatalogsMapper;
import cn.forest.mall.mapper.ProductDeliveryStatusMapper;
import cn.forest.mall.mapper.ProductsUpdateMapper;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product_update")
public class ProductsUpdateAction {

    @Autowired
    private ProductsUpdateMapper productsUpdateMapper;

    @Autowired
    private CatalogsMapper catalogsMapper;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private ProductDeliveryStatusMapper productDeliveryStatusMapper;

    /**
     * 保存
     *
     * @param productsUpdate
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody ProductsUpdate productsUpdate) {
        productsUpdate.setId(null);
        productsUpdate.setCreatedAt(null);
        productsUpdate.setUpdatedAt(null);
        int insert = productsUpdateMapper.insert(productsUpdate);
        return ResultSave.resultSave(insert, productsUpdate);
    }

    /**
     * 修改
     *
     * @param productsUpdate
     * @return
     */
    @RequestMapping("/updateById")
    public int updateById(@RequestBody ProductsUpdate productsUpdate) {
        return productsUpdateMapper.updateById(productsUpdate);
    }

    /**
     * 分页查询修改历史记录
     *
     * @param map
     * @return
     */
    @RequestMapping("/selectUpdateRecodeList")
    public Object selectUpdateRecodeList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<ProductsUpdate> list = productsUpdateMapper.selectListByMap(map);
            PageInfo<ProductsUpdate> productPage = new PageInfo<ProductsUpdate>(list);
            return new ResultPage<ProductsUpdate>(productPage);
        }
        return null;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        ProductsUpdate productsUpdate = productsUpdateMapper.selectById(id);
        if (productsUpdate != null) {
            Long catalogId = productsUpdate.getCatalogId();
            if (catalogId != null) {
                Catalogs catalogs = catalogsMapper.selectById(catalogId);
                productsUpdate.setCatalogName(catalogs == null ? null : catalogs.getName());
            }

            Long supplierId = productsUpdate.getSupplierId();
            if (supplierId != null) {
                Suppliers suppliers = suppliersMapper.selectById(supplierId);
                productsUpdate.setSupplierName(suppliers == null ? null : suppliers.getName());
            }

            QueryWrapper<ProductDeliveryStatus> qw = new QueryWrapper<>();
            qw.eq("product_id", id);
            qw.eq("type", 2);
            List<ProductDeliveryStatus> productDeliveryStatuses = productDeliveryStatusMapper.selectList(qw);
            if (!CollectionUtils.isEmpty(productDeliveryStatuses)) {
                List<Long> collect = productDeliveryStatuses.stream().map(ProductDeliveryStatus::getDeliveryStatus).collect(Collectors.toList());
                productsUpdate.setDeliveryStatus(StringUtils.join(collect.toArray(), ","));
            }
        }
        return productsUpdate;
    }

    /**
     * 批量审核
     *
     * @param ids
     * @param auditStatus
     * @return
     */
    @RequestMapping("/batchAudit")
    public int batchAudit(@RequestParam("ids") String ids, @RequestParam("auditStatus") Integer auditStatus) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                idList.add(Long.parseLong(str));
            }
            return productsUpdateMapper.batchAudit(idList, auditStatus);
        }
        return 0;
    }

    /**
     * 查询审核列表
     *
     * @param status
     * @param supplierId
     * @return
     */
    @RequestMapping("selectList")
    public Object selectList(@RequestParam(value = "status", required = false) Integer status,
                             @RequestParam(value = "supplierId", required = false) Long supplierId) {
        QueryWrapper<ProductsUpdate> qw = new QueryWrapper<>();
        if (status != null) {
            qw.eq("audit_status", status);
        }
        if (supplierId != null) {
            qw.eq("supplier_id", supplierId);
        }
        return productsUpdateMapper.selectList(qw);
    }

}
