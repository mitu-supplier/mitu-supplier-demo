package cn.forest.server;


import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Products;
import cn.forest.mall.mapper.CatalogsMapper;
import cn.forest.mall.mapper.ProductsMapper;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<Products> products = productsMapper.selectListByMap(map);
            PageInfo<Products> productsPage = new PageInfo<Products>(products);
            return new ResultPage<Products>(productsPage);
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
