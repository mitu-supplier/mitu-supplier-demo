package cn.forest.system.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysDictionaryDataRemote;
import com.sun.corba.se.impl.protocol.giopmsgheaders.RequestMessage;
import org.hibernate.validator.internal.metadata.aggregated.ReturnValueMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Service("sysDictionaryDataService")
public class SysDictionaryDataService {

    @Autowired
    private SysDictionaryDataRemote sysDictionaryDataRemote;

    public Map<String, Object> list(Long page, Long pageSize, Long type){
        Object list = sysDictionaryDataRemote.list(page, pageSize, type);
        if(list != null){
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> getById(Long id){
        Object obj = sysDictionaryDataRemote.getById(id);
        if(obj != null){
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, ?> map){
        int save = sysDictionaryDataRemote.save(map);
        return ResultMessage.result(save, "添加成功", "添加失败");
    }

    public Map<String, Object> update(Map<String, ?> map){
        int update = sysDictionaryDataRemote.update(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> delete(Long id){
        int delete = sysDictionaryDataRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }
}
