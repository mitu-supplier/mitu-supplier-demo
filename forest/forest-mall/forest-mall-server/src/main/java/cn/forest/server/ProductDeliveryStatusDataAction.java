package cn.forest.server;

import cn.forest.mall.entity.ProductDeliveryStatus;
import cn.forest.mall.entity.ProductDeliveryStatusData;
import cn.forest.mall.mapper.ProductDeliveryStatusDataMapper;
import cn.forest.mall.mapper.ProductDeliveryStatusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productDeliveryStatusData")
public class ProductDeliveryStatusDataAction {

    @Autowired
    private ProductDeliveryStatusDataMapper productDeliveryStatusDataMapper;

    @Autowired
    private ProductDeliveryStatusMapper productDeliveryStatusMapper;

    /**
     * 获取所有的发货状态
     *
     * @return
     */
    @RequestMapping("/list")
    public Object list() {
        QueryWrapper<ProductDeliveryStatusData> qw = new QueryWrapper<>();
        qw.orderByAsc("id");
        return productDeliveryStatusDataMapper.selectList(qw);
    }

    @RequestMapping("/save")
    public int save(@RequestBody ProductDeliveryStatusData productDeliveryStatusData) {
        return productDeliveryStatusDataMapper.insert(productDeliveryStatusData);
    }

    @RequestMapping("/update")
    public int update(@RequestBody ProductDeliveryStatusData productDeliveryStatusData) {
        return productDeliveryStatusDataMapper.updateById(productDeliveryStatusData);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return productDeliveryStatusDataMapper.deleteById(id);
    }

    /**
     * 查询商品的发货状态
     *
     * @param productId
     * @return
     */
    @RequestMapping("/selectByProductId")
    public Object selectByProductId(@RequestParam("product_id") Long productId) {
        QueryWrapper<ProductDeliveryStatus> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        List<ProductDeliveryStatus> list = productDeliveryStatusMapper.selectList(qw);
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> collect = list.stream().map(ProductDeliveryStatus::getDeliveryStatus).collect(Collectors.toList());
            QueryWrapper<ProductDeliveryStatusData> dataqw = new QueryWrapper<>();
            dataqw.in("id", collect);
            return productDeliveryStatusDataMapper.selectList(dataqw);
        }
        return null;
    }
}
