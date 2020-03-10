package cn.forest.product.remote;

import cn.forest.common.BusinessErrorCode;
import cn.forest.common.RemoteResponse;
import cn.forest.common.util.DateUtil;
import cn.forest.mall.entity.Camilo;
import cn.forest.mall.entity.ProductPic;
import cn.forest.mall.entity.Products;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.CamiloMapper;
import cn.forest.mall.mapper.ProductPicMapper;
import cn.forest.mall.mapper.ProductsMapper;
import cn.forest.mall.mapper.SuppliersMapper;
import cn.forest.product.dto.DeliveryTypeDTO;
import cn.forest.product.dto.MemberShipInfoParam;
import cn.forest.product.dto.ProductDTO;
import cn.forest.system.entity.SysDictionaryData;
import cn.forest.system.entity.SysDictionaryType;
import cn.forest.system.mapper.SysDictionaryDataMapper;
import cn.forest.system.mapper.SysDictionaryTypeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ProductController {

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Autowired
    private SysDictionaryTypeMapper sysDictionaryTypeMapper;

    @Autowired
    private CamiloMapper camiloMapper;

    @Autowired
    private ProductPicMapper productPicMapper;

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
                // 查询库存
                SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectById(products.getDeliveryType());
                Integer stock = null;
                if (sysDictionaryData != null) {
                    if ("D_KM".equals(sysDictionaryData.getCode())) {
                        // 卡密
                        QueryWrapper<Camilo> qw = new QueryWrapper<>();
                        qw.eq("product_id", products.getId());
                        qw.gt("failure_time", DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
                        List<Camilo> list = camiloMapper.selectList(qw);
                        productDTO.setStockCount(list == null ? 0 : list.size());
                        stock = list == null ? 0 : list.size();
                        productDTO.setCardCouponType("CAMILO");
                    } else if ("D_ZC".equals(sysDictionaryData.getCode())) {
                        // 直充
                        productDTO.setCardCouponType("DIRECT_CHARGE");
                    } else if ("D_SW".equals(sysDictionaryData.getCode())) {
                        // 实物
                        productDTO.setStockCount(products.getInventoryNum() == null ? 0 : products.getInventoryNum());
                        stock = products.getInventoryNum() == null ? 0 : products.getInventoryNum();
                        productDTO.setCardCouponType("DIRECT_CHARGE");
                    }
                }
                productDTO.setStockThreshold(products.getInventorySellingThreshold() == null ? 0 : products.getInventorySellingThreshold());
                productDTO.setShipCategory(products.getDeliveryKind() == null ? null : Integer.parseInt(products.getDeliveryKind() + ""));
                // 判断商品状态
                String maintainConfigStart = products.getMaintainConfigStart();
                String maintainConfigEnd = products.getMaintainConfigEnd();
                if (maintainConfigStart != null && maintainConfigEnd != null) {
                    Date now = new Date();
                    if (now.after(DateUtil.parseStrToDate(maintainConfigStart, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS))
                            && now.before(DateUtil.parseStrToDate(maintainConfigEnd, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS))) {
                        productDTO.setStatus("服务不可用");
                        productDTO.setPrompt(products.getMaintainMessage());
                    } else {
                        if (stock != null) {
                            if (stock <= productDTO.getStockThreshold()) {
                                productDTO.setStatus("库存不足");
                                productDTO.setPrompt(products.getInventoryAlertMessage());
                            } else {
                                productDTO.setStatus("正常");
                                productDTO.setPrompt("");
                            }
                        } else {
                            productDTO.setStatus("正常");
                            productDTO.setPrompt("");
                        }
                    }
                } else {
                    productDTO.setStatus("正常");
                    productDTO.setPrompt("");
                }
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
    @RequestMapping("/queryShipCategory")
    public RemoteResponse<DeliveryTypeDTO> queryShipCategory(@RequestParam("shipCategoryId") Integer shipCategoryId) {
        RemoteResponse<DeliveryTypeDTO> result = new RemoteResponse<>();
        if (shipCategoryId != null) {
            SysDictionaryType sysDictionaryType = sysDictionaryTypeMapper.selectById(Long.parseLong(shipCategoryId + ""));
            if (sysDictionaryType != null) {
                DeliveryTypeDTO deliveryTypeDTO = new DeliveryTypeDTO();
                deliveryTypeDTO.setId(shipCategoryId);
                deliveryTypeDTO.setName(sysDictionaryType.getName());
                List<MemberShipInfoParam> configList = new ArrayList<>();
                MemberShipInfoParam memberShipInfoParam = null;
                List<SysDictionaryData> sysDictionaryData = sysDictionaryDataMapper.selectByDateTypeCode(sysDictionaryType.getCode());
                if (!CollectionUtils.isEmpty(sysDictionaryData)) {
                    for (SysDictionaryData sysDictionaryDatum : sysDictionaryData) {
                        memberShipInfoParam = new MemberShipInfoParam();
                        memberShipInfoParam.setName(sysDictionaryDatum.getName());
                        configList.add(memberShipInfoParam);
                    }
                }
                deliveryTypeDTO.setConfig(configList);
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
     * 搜索商品
     *
     * @param name
     * @param category
     * @param status
     * @return
     */
    @RequestMapping("/searchMerchantProduct")
    public RemoteResponse<List<ProductDTO>> searchMerchantProduct(String name, String category, String status) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("catalogName", category);
        paramMap.put("auditStatus", 1);
        List<Products> products = productsMapper.selectListByMap(paramMap);
        List<ProductDTO> resultList = new ArrayList<>();
        ProductDTO productDTO = null;
        if (!CollectionUtils.isEmpty(products)) {
            for (Products product : products) {
                productDTO = new ProductDTO();
                productDTO.setId(Integer.parseInt(product.getId() + ""));
                productDTO.setMerchantId(Integer.parseInt(product.getSupplierId() + ""));
                Suppliers suppliers = suppliersMapper.selectById(product.getSupplierId());
                if (suppliers != null) {
                    productDTO.setMerchantAlias(suppliers.getShortName());
                }
                productDTO.setName(product.getName());
                productDTO.setMerchantProductNo(product.getCode());
                productDTO.setProductValue(product.getPrice());
                productDTO.setSalePrice(product.getSupplyPrice());
                List<String> imgPaths = new ArrayList<>();
                QueryWrapper<ProductPic> qw = new QueryWrapper<>();
                qw.eq("product_id", product.getId());
                qw.eq("type", 1);
                List<ProductPic> productPics = productPicMapper.selectList(qw);
                if (!CollectionUtils.isEmpty(productPics)) {
                    for (ProductPic productPic : productPics) {
                        imgPaths.add(productPic.getPath());
                    }
                }
                productDTO.setImages(imgPaths);
                if ("D_KM".equals(product.getDeliveryCode())) {
                    // 卡密
                    productDTO.setCardCouponType("CAMILO");
                } else if ("D_ZC".equals(product.getDeliveryCode())) {
                    // 直充
                    productDTO.setCardCouponType("DIRECT_CHARGE");
                }
                productDTO.setStatus((product.getStatus() != null && product.getStatus() == 1) ? "上架" : "下架");
                productDTO.setDetail(product.getDetails());
                resultList.add(productDTO);
            }
        }

        RemoteResponse<List<ProductDTO>> result = new RemoteResponse<>();
        result.setData(resultList);
        result.setMessage("查询成功");
        result.setCode(BusinessErrorCode.SUCCESS);
        return result;
    }
}
