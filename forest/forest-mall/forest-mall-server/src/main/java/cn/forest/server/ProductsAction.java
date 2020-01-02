package cn.forest.server;


import cn.forest.mall.entity.Products;
import cn.forest.mall.mapper.ProductsMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsAction {

    @Autowired
    private ProductsMapper productsMapper;

    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
        Page<Products> pages = new Page<Products>(page, pageSize);
        QueryWrapper<Products> queryWrapper = new QueryWrapper<Products>();
        return productsMapper.selectPage(pages, queryWrapper);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id){
        return productsMapper.selectById(id);
    }

    @RequestMapping("/save")
    public Object save(@RequestBody Products products){
        int insert = productsMapper.insert(products);
        if(insert > 0){
            return products.getId();
        }
        return null;
    }

    @RequestMapping("/update")
    public int update(@RequestBody Products products){
        return productsMapper.updateById(products);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id){
        return productsMapper.deleteById(id);
    }

    @RequestMapping("/batchAudit")
    public int updateStatus(@RequestParam("ids")String ids, @RequestParam("auditStatus")Integer auditStatus){
        if(StringUtils.isNotEmpty(ids)){
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split){
                idList.add(Long.parseLong(str));
            }
            return productsMapper.batchAudit(idList, auditStatus);
        }
        return 0;
    }

}
