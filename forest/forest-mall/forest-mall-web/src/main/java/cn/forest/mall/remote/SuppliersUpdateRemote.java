package cn.forest.mall.remote;

import cn.forest.mall.fallback.SuppliersUpdateBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = SuppliersUpdateBack.class)
public interface SuppliersUpdateRemote {

    /**
     * 查询供应商最近一次变更信息记录
     *
     * @param supplierId 供应商id
     * @param status     状态
     * @return
     */
    @RequestMapping("/supplier_update/getBySupplierId")
    Object getBySupplierId(@RequestParam("supplierId") Long supplierId, @RequestParam(value = "status", required = false) Integer status);

    /**
     * 保存
     *
     * @param map
     * @return
     */
    @RequestMapping("/supplier_update/save")
    Object save(@RequestBody Map<String, ?> map);

    /**
     * 根据id修改
     *
     * @param map
     * @return
     */
    @RequestMapping("/supplier_update/update")
    int update(@RequestBody Map<String, ?> map);

    /**
     * 分页查询修改审核记录
     *
     * @param map
     * @return
     */
    @RequestMapping("/supplier_update/selectUpdateRecodeList")
    Object selectUpdateRecodeList(@RequestBody Map<String, Object> map);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/supplier_update/getById")
    Object getById(@RequestParam("id") Long id);

    /**
     * 批量审核
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/supplier_update/batchAudit")
    int batchAudit(@RequestParam("ids") String ids, @RequestParam("status") Integer status);
}
