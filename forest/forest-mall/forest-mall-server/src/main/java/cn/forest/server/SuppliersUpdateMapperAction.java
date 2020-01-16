package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.SuppliersUpdate;
import cn.forest.mall.mapper.SuppliersUpdateMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supplier_update")
public class SuppliersUpdateMapperAction {

    @Autowired
    private SuppliersUpdateMapper suppliersUpdateMapper;

    /**
     * 查询供应商最近一次变更信息记录
     *
     * @param supplierId 供应商id
     * @param status     状态
     * @return
     */
    @RequestMapping("/getBySupplierId")
    public Object getBySupplierId(@RequestParam("supplierId") Long supplierId, @RequestParam(value = "status", required = false) Integer status) {
        QueryWrapper<SuppliersUpdate> suppliersUpdateMapperQueryWrapper = new QueryWrapper<>();
        suppliersUpdateMapperQueryWrapper.eq("supplier_id", supplierId);
        if (status != null) {
            suppliersUpdateMapperQueryWrapper.eq("status", status);
        }
        suppliersUpdateMapperQueryWrapper.orderByDesc("created_at");
        List<SuppliersUpdate> suppliersUpdates = suppliersUpdateMapper.selectList(suppliersUpdateMapperQueryWrapper);
        if (!CollectionUtils.isEmpty(suppliersUpdates)) {
            return suppliersUpdates.get(0);
        }
        return null;
    }

    /**
     * 保存
     *
     * @param suppliersUpdate
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody SuppliersUpdate suppliersUpdate) {
        suppliersUpdate.setCreatedAt(null);
        suppliersUpdate.setId(null);
        int insert = suppliersUpdateMapper.insert(suppliersUpdate);
        return ResultSave.resultSave(insert, suppliersUpdate);
    }

    /**
     * 根据id修改
     *
     * @param suppliersUpdate
     * @return
     */
    @RequestMapping("/update")
    public int update(@RequestBody SuppliersUpdate suppliersUpdate) {
        return suppliersUpdateMapper.updateById(suppliersUpdate);
    }

    /**
     * 条件分页查询修改记录
     *
     * @param map
     * @return
     */
    @RequestMapping("/selectUpdateRecodeList")
    public Object selectUpdateRecodeList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<SuppliersUpdate> list = suppliersUpdateMapper.selectUpdateRecodeList(map);
            PageInfo<SuppliersUpdate> suppliersPage = new PageInfo<SuppliersUpdate>(list);
            return new ResultPage<SuppliersUpdate>(suppliersPage);
        }
        return null;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return suppliersUpdateMapper.selectById(id);
    }

    /**
     * 批量审核
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping("/batchAudit")
    public int updateStatus(@RequestParam("ids") String ids, @RequestParam("status") Integer status) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                idList.add(Long.parseLong(str));
            }
            return suppliersUpdateMapper.batchAudit(idList, status);
        }
        return 0;
    }
}
