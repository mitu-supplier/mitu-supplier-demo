package cn.forest.mall.remote;

import cn.forest.mall.fallback.ReceivingAccountBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = ReceivingAccountBack.class)
public interface ReceivingAccountRemote {

    @RequestMapping("/receivingAccount/list")
    Object list(@RequestParam(value = "type", required = false) Integer type);

    @RequestMapping("/saveAll")
    int saveAll(List<Map<String, Object>> mapList);

    @RequestMapping("/deleteAll")
    int deleteAll();
}
