package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.remote.CatalogsRemote;
import cn.forest.mall.remote.SysDictionaryDataRemote;
import cn.forest.mall.service.ProductsService;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
@SysLogs(desc = "商品信息管理")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private CatalogsRemote catalogsRemote;

    @Autowired
    private SysDictionaryDataRemote sysDictionaryDataRemote;

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 列表查询
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商品列表")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return productsService.list(request, null);
    }

    /**
     * 保存
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "保存商品信息")
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return productsService.save(request);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "获取商品详情")
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return productsService.getById(id);
    }

    /**
     * 修改
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商品修改")
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return productsService.update(RequestMap.requestToMap(request));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "删除商品")
    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return productsService.delete(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @SysLogs(desc = "商品批量删除")
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return productsService.batchDelete(ids);
    }

    @SysLogs(desc = "获取品目信息")
    @RequestMapping("/getCatalogs")
    public Map<String, Object> getCatalogs() {
        Object list = catalogsRemote.getAllUseable();
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }

    @SysLogs(desc = "查询发货类型")
    @RequestMapping("/getDelivery_type")
    public Map<String, Object> getDelivery_type() {
        Object list = sysDictionaryDataRemote.selectByDateTypeCode("DELIVERY_TYPE");
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }

    @SysLogs(desc = "获取供应商列表")
    @RequestMapping("/getSuppliers")
    public Map<String, Object> getSuppliers() {
        return suppliersService.getAll();
    }

    @SysLogs(desc = "判断当前用户是否为供应商")
    @RequestMapping("/isSupplier")
    public Map<String, Object> isSupplier(HttpServletRequest request) {
        if (suppliersService.isSupplier(request)) {
            return ResultMessage.success(1);
        }
        return ResultMessage.success(0);
    }

    @SysLogs(desc = "修改商品上下架状态")
    @RequestMapping("/updateStatus")
    public Map<String, Object> updateStatus(Long id, Integer status) {
        return productsService.updateStatus(id, status);
    }

    /**
     * 获取商品编码
     *
     * @return
     */
    @SysLogs(desc = "获取商品编码")
    @RequestMapping("/getProductCode")
    public Map<String, Object> getProductCode() {
        return ResultMessage.success(productsService.getProductCode());
    }

    /**
     * 获取商品发货状态
     *
     * @return
     */
    @SysLogs(desc = "获取商品发货状态")
    @RequestMapping("/getDeliveryStatus")
    public Map<String, Object> getDeliveryStatus() {
        return productsService.getDeliveryStatus();
    }


    /**
     * 保存商品发货状态
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "保存商品发货状态")
    @RequestMapping("/saveDeliveryStatus")
    public Map<String, Object> saveDeliveryStatus(HttpServletRequest request) {
        return productsService.saveDeliveryStatus(request);
    }

    /**
     * 获取发货类型
     *
     * @return
     */
    @SysLogs(desc = "获取发货类型")
    @RequestMapping("/getDeliveryKind")
    public Map<String, Object> getDeliveryKind() {
        return productsService.getDeliveryKind();
    }
}
