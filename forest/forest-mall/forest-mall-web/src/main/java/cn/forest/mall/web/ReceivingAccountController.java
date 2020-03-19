package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
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
@SysLogs(desc = "报警提示账号管理")
public class ReceivingAccountController {

    @Autowired
    private ReceivingAccountService receivingAccountService;

    @SysLogs(desc = "报警提示账号查询")
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "type", required = false) Integer type) {
        return receivingAccountService.list(type);
    }

    @SysLogs(desc = "保存报警提示账号")
    @RequestMapping("/save")
    public Map<String, Object> save(@RequestBody List<Object> list) {
        return receivingAccountService.save(list);
    }

}
