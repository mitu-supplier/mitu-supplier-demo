package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.ReceivingAccountRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("receivingAccountService")
public class ReceivingAccountService {

    @Autowired
    private ReceivingAccountRemote receivingAccountRemote;

    public Map<String, Object> save(Map<String, Object> paramMap){
        List<Map<String, Object>> list = new ArrayList<>();
        List paramList = (List) paramMap.get("list");
        for (Object o : paramList) {
            list.add((Map) o);
        }
        receivingAccountRemote.deleteAll();
        int i = receivingAccountRemote.saveAll(list);
        return ResultMessage.result(i, "保存成功", "保存失败");
    }

    public Map<String, Object> list(Integer type){
        Object list = receivingAccountRemote.list(type);
        if(list != null){
            return ResultMessage.success(list);
        }
        return null;
    }
}
