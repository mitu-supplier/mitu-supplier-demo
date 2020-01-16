package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.AuditRecode;
import cn.forest.mall.mapper.AuditRecodeMapper;
import cn.forest.service.AuditRecodeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auditRecode")
public class AuditRecodeAction {

    @Autowired
    private AuditRecodeService auditRecodeService;

    /**
     * 分页查询审核记录
     *
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        QueryWrapper<AuditRecode> queryWrapper = new QueryWrapper<AuditRecode>();
        queryWrapper.eq("business_id", map.get("businessId"));
        queryWrapper.eq("audit_type", map.get("auditType"));
        queryWrapper.orderByDesc("create_time");
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            Long page = Long.parseLong(map.get("page").toString());
            Long pageSize = Long.parseLong(map.get("pageSize").toString());
            Page<AuditRecode> pages = new Page<AuditRecode>(page, pageSize);
            IPage<AuditRecode> auditRecodeIPage = auditRecodeService.page(pages, queryWrapper);
            return new ResultPage<AuditRecode>(auditRecodeIPage);
        } else {
            return auditRecodeService.list(queryWrapper);
        }
    }

    @RequestMapping("/save")
    public int save(@RequestBody AuditRecode auditRecode) {
        boolean save = auditRecodeService.save(auditRecode);
        return save ? 1 : 0;
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return auditRecodeService.getById(id);
    }

    @RequestMapping("/batchSave")
    public int batchSave(@RequestBody List<AuditRecode> list) {
        boolean b = auditRecodeService.saveBatch(list);
        return b ? 1 : 0;
    }

    @RequestMapping("/selectByBusinessId")
    public Object selectByBusinessId(@RequestParam("businessId") Long businessId, @RequestParam("auditType") Integer auditType) {
        QueryWrapper<AuditRecode> queryWrapper = new QueryWrapper<AuditRecode>();
        queryWrapper.eq("business_id", businessId);
        queryWrapper.eq("audit_type", auditType);
        queryWrapper.orderByDesc("create_time");
        return auditRecodeService.list(queryWrapper);
    }
}
