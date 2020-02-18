package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.ReceivingAccountService;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/receivingAccount")
public class ReceivingAccountController {

    @Autowired
    private ReceivingAccountService receivingAccountService;

    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "type", required = false) Integer type) {
        return receivingAccountService.list(type);
    }

    @RequestMapping("/save")
    public Map<String, Object> save(@RequestBody List<Object> list) {
        return receivingAccountService.save(list);
    }

}
