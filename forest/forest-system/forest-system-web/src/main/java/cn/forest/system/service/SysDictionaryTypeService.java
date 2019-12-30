package cn.forest.system.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysDictionaryDataRemote;
import cn.forest.system.remote.SysDictionaryTypeRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysDictionaryTypeService")
public class SysDictionaryTypeService {

    @Autowired
    private SysDictionaryTypeRemote sysDictionaryTypeRemote;

    public Map<String, Object> list() {
        Object list = sysDictionaryTypeRemote.list();
        if (list != null) {
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> getById(Long id) {
        Object obj = sysDictionaryTypeRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, ?> map) {
        int save = sysDictionaryTypeRemote.save(map);
        return ResultMessage.result(save, "添加成功", "添加失败");
    }

    public Map<String, Object> update(Map<String, ?> map) {
        int update = sysDictionaryTypeRemote.update(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> delete(Long id) {
        int delete = sysDictionaryTypeRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }
}
