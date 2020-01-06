package cn.forest.server;


import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Catalogs;
import cn.forest.mall.entity.Products;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.CatalogsMapper;
import cn.forest.mall.mapper.ProductsMapper;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsAction {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private CatalogsMapper catalogsMapper;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        QueryWrapper<Products> queryWrapper = new QueryWrapper<Products>();
        if (StringUtil.toString(map.get("name")) != null) {
            queryWrapper.like("name", map.get("name"));
        }
        String catalogsName = StringUtil.toString(map.get("catalogName"));
        if (catalogsName != null) {
            QueryWrapper<Catalogs> qw = new QueryWrapper<Catalogs>();
            qw.like("name", catalogsName);
            List<Catalogs> catalogs = catalogsMapper.selectList(qw);
            List<Long> ids = new ArrayList<>();
            if (!CollectionUtils.isEmpty(catalogs)) {
                ids = catalogs.stream().map(Catalogs::getId).collect(Collectors.toList());
            } else {
                ids.add(0L);
            }
            queryWrapper.in("catalog_id", ids);
        }
        if (StringUtil.toString(map.get("auditStatus")) != null) {
            queryWrapper.eq("audit_status", Integer.parseInt(StringUtil.toString(map.get("auditStatus"))));
        }
        String supplierName = StringUtil.toString(map.get("supplierName"));
        if (supplierName != null) {
            QueryWrapper<Suppliers> qw = new QueryWrapper<Suppliers>();
            qw.like("name", supplierName);
            List<Suppliers> suppliersList = suppliersMapper.selectList(qw);
            List<Long> ids = new ArrayList<>();
            if (!CollectionUtils.isEmpty(suppliersList)) {
                ids = suppliersList.stream().map(Suppliers::getId).collect(Collectors.toList());
            } else {
                ids.add(0L);
            }
            queryWrapper.in("supplier_id", ids);
        }
        queryWrapper.orderByAsc(new String[]{"audit_status", "sort"});
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            Long page = Long.parseLong(map.get("page").toString());
            Long pageSize = Long.parseLong(map.get("pageSize").toString());
            Page<Products> pages = new Page<Products>(page, pageSize);
            IPage<Products> productsIPage = productsMapper.selectPage(pages, queryWrapper);
            return new ResultPage<Products>(productsIPage);
        }
        return null;
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return productsMapper.selectById(id);
    }

    @RequestMapping("/save")
    public Object save(@RequestBody Products products) {
        int insert = productsMapper.insert(products);
        if (insert > 0) {
            return products.getId();
        }
        return null;
    }

    @RequestMapping("/update")
    public int update(@RequestBody Products products) {
        return productsMapper.updateById(products);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return productsMapper.deleteById(id);
    }

    @RequestMapping("/batchAudit")
    public int updateStatus(@RequestParam("ids") String ids, @RequestParam("auditStatus") Integer auditStatus) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                idList.add(Long.parseLong(str));
            }
            return productsMapper.batchAudit(idList, auditStatus);
        }
        return 0;
    }

    @RequestMapping("/selectByCode")
    public Object selectByCode(@RequestParam("code") String code) {
        QueryWrapper<Products> queryWrapper = new QueryWrapper<Products>();
        queryWrapper.eq("code", code);
        queryWrapper.eq("is_delete", 0);
        return productsMapper.selectList(queryWrapper);
    }

    @RequestMapping("/batchDelete")
    public int batchDelete(@RequestParam("ids") String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                productsMapper.deleteById(Long.parseLong(str));
            }
            return 1;
        }
        return 0;
    }
}
