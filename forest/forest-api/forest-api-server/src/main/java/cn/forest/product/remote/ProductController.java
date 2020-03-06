package cn.forest.product.remote;

import cn.forest.common.BusinessErrorCode;
import cn.forest.common.RemoteResponse;
import cn.forest.mall.entity.Products;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.ProductsMapper;
import cn.forest.mall.mapper.SuppliersMapper;
import cn.forest.product.dto.CamiloDTO;
import cn.forest.product.dto.DeliveryTypeDTO;
import cn.forest.product.dto.ProductDTO;
import cn.forest.system.entity.SysDictionaryData;
import cn.forest.system.mapper.SysDictionaryDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    /**
     * 查询商户商品
     *
     * @param merchantProductId 商户产品标识
     * @return
     */
    @RequestMapping("/queryMerchantProduct")
    public RemoteResponse<ProductDTO> queryMerchantProduct(@RequestParam(value = "merchantProductId") Integer merchantProductId) {
        RemoteResponse<ProductDTO> result = new RemoteResponse<>();
        if (merchantProductId != null) {
            Products products = productsMapper.selectById(Long.parseLong(merchantProductId + ""));
            if (products != null) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(merchantProductId);
                productDTO.setMerchantId(Integer.parseInt(products.getSupplierId() + ""));
                Suppliers suppliers = suppliersMapper.selectById(products.getSupplierId());
                if (suppliers != null) {
                    productDTO.setMerchantAlias(suppliers.getShortName());
                }
                productDTO.setName(products.getName());
                productDTO.setMerchantProductNo(products.getCode());
                productDTO.setProductValue(products.getPrice());
                productDTO.setSalePrice(products.getSupplyPrice());
                productDTO.setStockCount(null);
                productDTO.setStockThreshold(products.getInventorySellingThreshold());
                productDTO.setShipCategory(Integer.parseInt(products.getDeliveryType() + ""));
                SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectById(products.getDeliveryType());
                if (sysDictionaryData != null) {
                    if ("D_KM".equals(sysDictionaryData.getCode())) {
                        productDTO.setCardCouponType("CAMILO");
                    } else if ("D_ZC".equals(sysDictionaryData.getCode())) {
                        productDTO.setCardCouponType("DIRECT_CHARGE");
                    }
                }
                productDTO.setStatus(null);
                productDTO.setPrompt(null);
                result.setCode(BusinessErrorCode.SUCCESS);
                result.setMessage("查询成功");
                result.setData(productDTO);
                return result;
            }
        }
        result.setCode(BusinessErrorCode.PRODUCT_NOT_FOUND);
        result.setMessage("商户商品不存在");
        return result;
    }

    /**
     * 查询发货类型
     *
     * @param shipCategoryId 发货类型标识
     * @return
     */
    public RemoteResponse<DeliveryTypeDTO> queryShipCategory(@RequestParam("shipCategoryId") Integer shipCategoryId) {
        RemoteResponse<DeliveryTypeDTO> result = new RemoteResponse<>();
        if (shipCategoryId != null) {
            SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectById(Long.parseLong(shipCategoryId + ""));
            if (sysDictionaryData != null) {
                DeliveryTypeDTO deliveryTypeDTO = new DeliveryTypeDTO();
                deliveryTypeDTO.setId(shipCategoryId);
                deliveryTypeDTO.setName(sysDictionaryData.getName());
                result.setCode(BusinessErrorCode.SUCCESS);
                result.setMessage("查询成功");
                result.setData(deliveryTypeDTO);
                return result;
            }
        }
        result.setCode(BusinessErrorCode.ERROR);
        result.setMessage("未找到对应的发货类型");
        return result;
    }

    /**
     * 获取卡密接口
     * 通过商户产品标识获取卡密，支持幂等。通过businessType和orderNo决定请求的唯一性
     *
     * @param businessType      业务类型
     * @param orderNo           业务订单号
     * @param merchantId        商户标识
     * @param merchantProductNo 商户产品编号
     * @return
     */
    public RemoteResponse<CamiloDTO> outStore(String businessType, String orderNo, Integer merchantId, String merchantProductNo) {
        RemoteResponse<CamiloDTO> result = new RemoteResponse<>();

        return result;
    }

    /**
     *
     * @return
     */
    public RemoteResponse searchMerchantProduct(String name, String category, String status){
        return null;
    }
}
