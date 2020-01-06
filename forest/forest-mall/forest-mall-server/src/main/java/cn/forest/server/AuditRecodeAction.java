package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.AuditRecode;
import cn.forest.mall.mapper.AuditRecodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auditRecode")
public class AuditRecodeAction {

    @Autowired
    private AuditRecodeMapper auditRecodeMapper;

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
        queryWrapper.orderByDesc("audit_time");
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            Long page = Long.parseLong(map.get("page").toString());
            Long pageSize = Long.parseLong(map.get("pageSize").toString());
            Page<AuditRecode> pages = new Page<AuditRecode>(page, pageSize);
            IPage<AuditRecode> auditRecodeIPage = auditRecodeMapper.selectPage(pages, queryWrapper);
            return new ResultPage<AuditRecode>(auditRecodeIPage);
        }
        return null;
    }

    @RequestMapping("/save")
    public int save(@RequestBody AuditRecode auditRecode) {
        return auditRecodeMapper.insert(auditRecode);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return auditRecodeMapper.selectById(id);
    }
}
