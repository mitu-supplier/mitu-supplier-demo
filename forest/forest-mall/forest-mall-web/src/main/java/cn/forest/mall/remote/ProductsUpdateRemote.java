package cn.forest.mall.remote;

import cn.forest.mall.fallback.ProductsUpdateBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = ProductsUpdateBack.class)
public interface ProductsUpdateRemote {

    /**
     * 保存
     *
     * @param map
     * @return
     */
    @RequestMapping("/product_update/save")
    Object save(Map<String, ?> map);

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/product_update/updateById")
    int updateById(Map<String, ?> map);

    /**
     * 分页查询修改历史记录
     *
     * @param map
     * @return
     */
    @RequestMapping("/product_update/selectUpdateRecodeList")
    Object selectUpdateRecodeList(@RequestBody Map<String, Object> map);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/product_update/getById")
    Object getById(@RequestParam("id") Long id);

    /**
     * 批量审核
     *
     * @param ids
     * @param auditStatus
     * @return
     */
    @RequestMapping("/product_update/batchAudit")
    int batchAudit(@RequestParam("ids") String ids, @RequestParam("auditStatus") Integer auditStatus);

    /**
     * 待审核列表查询
     *
     * @param status
     * @param supplierId
     * @return
     */
    @RequestMapping("/product_update/selectList")
    Object selectList(@RequestParam(value = "status", required = false) Integer status,
                      @RequestParam(value = "supplierId", required = false) Long supplierId);
}
