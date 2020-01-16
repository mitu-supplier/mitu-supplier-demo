package cn.forest.mall.remote;

import cn.forest.mall.fallback.AuditRecodeBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = AuditRecodeBack.class)
public interface AuditRecodeRemote {

    @RequestMapping("/auditRecode/list")
    Object list(Map<String, ?> map);

    @RequestMapping("/auditRecode/save")
    int save(Map<String, ?> map);

    @RequestMapping("/auditRecode/getById")
    Object getById(@RequestParam("id") Long id);

    @RequestMapping("/auditRecode/batchSave")
    int batchSave(List<Map<String, Object>> list);

    @RequestMapping("/auditRecode/selectByBusinessId")
    Object selectByBusinessId(@RequestParam("businessId") Long businessId, @RequestParam("auditType") Integer auditType);
}
