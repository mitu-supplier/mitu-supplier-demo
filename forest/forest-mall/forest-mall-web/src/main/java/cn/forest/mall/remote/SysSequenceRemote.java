package cn.forest.mall.remote;

import cn.forest.mall.fallback.SysSequenceBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-system-server", fallback = SysSequenceBack.class)
public interface SysSequenceRemote {

    @RequestMapping("/sys_sequence/save")
    Object save(Map<String, ?> map);

    @RequestMapping("/sys_sequence/getSeqValue")
    String getSeqValue(@RequestParam("name") String name);

    @RequestMapping("/sys_sequence/countByName")
    int countByName(@RequestParam("name") String name);
}
