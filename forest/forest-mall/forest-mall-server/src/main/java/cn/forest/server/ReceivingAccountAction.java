package cn.forest.server;

import cn.forest.mall.entity.ReceivingAccount;
import cn.forest.mall.mapper.ReceivingAccountMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receivingAccount")
public class ReceivingAccountAction {

    @Autowired
    private ReceivingAccountMapper receivingAccountMapper;

    @RequestMapping("/list")
    public Object list(@RequestParam(value = "type", required = false) Integer type) {
        QueryWrapper<ReceivingAccount> qw = new QueryWrapper<>();
        if (type != null) {
            qw.eq("type", type);
        }
        return receivingAccountMapper.selectList(qw);
    }

    @RequestMapping("/saveAll")
    public int saveAll(@RequestBody() List<ReceivingAccount> receivingAccounts) {
        if (!CollectionUtils.isEmpty(receivingAccounts)) {
            for (ReceivingAccount receivingAccount : receivingAccounts) {
                receivingAccountMapper.insert(receivingAccount);
            }
        }
        return 1;
    }

    @RequestMapping("/deleteAll")
    public int deleteAll(){
        return receivingAccountMapper.delete(new QueryWrapper<ReceivingAccount>());
    }
}
