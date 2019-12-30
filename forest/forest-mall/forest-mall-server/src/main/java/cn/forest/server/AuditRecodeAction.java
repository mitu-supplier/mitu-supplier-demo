package cn.forest.server;

import cn.forest.mall.entity.AuditRecode;
import cn.forest.service.AuditRecodeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditRecode")
public class AuditRecodeAction {

    @Autowired
    private AuditRecodeService auditRecodeService;

    /**
     * 分页查询审核记录
     *
     * @param page
     * @param pageSize
     * @param businessId
     * @param auditType
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("businessId") Long businessId, @RequestParam("auditType") Integer auditType) {
        Page<AuditRecode> pages = new Page<AuditRecode>(page, pageSize);
        QueryWrapper<AuditRecode> queryWrapper = new QueryWrapper<AuditRecode>();
        queryWrapper.eq("business_id", businessId);
        queryWrapper.eq("audit_type", auditType);
        queryWrapper.orderByDesc("audit_time");
        return auditRecodeService.page(pages, queryWrapper);
    }

    @RequestMapping("/save")
    public int save(@RequestBody AuditRecode auditRecode) {
        boolean save = auditRecodeService.save(auditRecode);
        if (save) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return auditRecodeService.getById(id);
    }
}
