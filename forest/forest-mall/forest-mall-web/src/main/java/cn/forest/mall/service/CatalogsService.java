package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.CatalogsRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("catalogsService")
public class CatalogsService {

    @Autowired
    private CatalogsRemote catalogsRemote;

    public Map<String, Object> getChildrenByLevel(Long id) {
        Object list = catalogsRemote.getChildrenByLevel(id);
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }

    public Map<String, Object> getById(Long id) {
        Object obj = catalogsRemote.getById(id);
        if (obj != null) {
            return ResultMessage.success(obj);
        }
        return ResultMessage.error("");
    }

    public Map<String, Object> save(Map<String, ?> map) {
        int save = catalogsRemote.save(map);
        return ResultMessage.result(save, "添加成功", "添加失败");
    }

    public Map<String, Object> update(Map<String, ?> map) {
        int update = catalogsRemote.upadte(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> disable(Long id) {
        int disable = catalogsRemote.disable(id);
        return ResultMessage.result(disable, "操作成功", "操作失败");
    }
}
