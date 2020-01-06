package cn.forest.login.remote;

import cn.forest.login.fallback.SuppliersBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "forest-mall-server", fallback = SuppliersBack.class)
public interface SuppliersRemote {

    @RequestMapping("/suppliers/getById")
    Object getById(@RequestParam("id") Long id);
}
