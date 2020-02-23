package cn.forest.server;

import cn.forest.mall.entity.ProductPic;
import cn.forest.mall.mapper.ProductPicMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productPic")
public class ProductPicAction {

    @Autowired
    private ProductPicMapper productPicMapper;

    /**
     * 查询商品图片
     *
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/selectByProduct")
    public Object selectByProduct(@RequestParam(value = "productId") Long productId, @RequestParam(value = "type") Integer type) {
        QueryWrapper<ProductPic> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.eq("type", type);
        return productPicMapper.selectList(qw);
    }

    /**
     * 删除商品图片
     *
     * @param productId
     * @param type
     * @return
     */
    @RequestMapping("/deleteByProduct")
    public int deleteByProduct(@RequestParam(value = "productId") Long productId, @RequestParam(value = "type") Integer type) {
        QueryWrapper<ProductPic> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.eq("type", type);
        return productPicMapper.delete(qw);
    }

    /**
     * 保存
     * @param list
     */
    @RequestMapping("/save")
    public void save(@RequestBody List<ProductPic> list) {
        if(!CollectionUtils.isEmpty(list)){
            for (ProductPic productPic : list) {
                productPicMapper.insert(productPic);
            }
        }
    }
}
