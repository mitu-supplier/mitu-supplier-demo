package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.common.util.StringUtil;
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

    public Map<String, Object> save(Map<String, Object> map) {
        Map<String, Object> vaMap = vaNameOrCode(map);
        if(ResultMessage.SUCCESS_CODE.equals(StringUtil.toString(vaMap.get(ResultMessage.STATUSCODE)))){
            int save = catalogsRemote.save(map);
            return ResultMessage.result(save, "添加成功", "添加失败");
        }
        return vaMap;
    }

    public Map<String, Object> update(Map<String, Object> map) {
        Map<String, Object> vaMap = vaNameOrCode(map);
        if(ResultMessage.SUCCESS_CODE.equals(StringUtil.toString(vaMap.get(ResultMessage.STATUSCODE)))) {
            int update = catalogsRemote.upadte(map);
            return ResultMessage.result(update, "修改成功", "修改失败");
        }
        return vaMap;
    }

    public Map<String, Object> delete(Long id) {
      int delete = catalogsRemote.delete(id);
      return ResultMessage.result(delete, "删除成功", "删除失败");
    }
    
    public Map<String, Object> disable(Long id) {
        int disable = catalogsRemote.disable(id);
        return ResultMessage.result(disable, "操作成功", "操作失败");
    }

    public Map<String, Object> getAll() {
        Object list = catalogsRemote.getAll();
        if (list != null) {
            return ResultMessage.success(list);
        }
        return ResultMessage.error("");
    }

    public Map<String, Object> vaNameOrCode(Map<String, Object> map){
        Long id = StringUtil.isBlank(map.get("id")) ? null : Long.parseLong(map.get("id").toString());
        String name = StringUtil.isBlank(map.get("name")) ? null : map.get("name").toString();
        String code = StringUtil.isBlank(map.get("code")) ? null : map.get("code").toString();
        Long parentId = StringUtil.isBlank(map.get("parentId")) ? null : Long.parseLong(map.get("parentId").toString());
        if(name == null){
            return ResultMessage.error("名称不能为空");
        }
        if(code == null){
            return ResultMessage.error("编码不能为空");
        }
        if(!catalogsRemote.vaNameOrCode(id, name, null, parentId)){
            return ResultMessage.error("名称不能重复");
        }
        if(!catalogsRemote.vaNameOrCode(id, null, code, parentId)){
            return ResultMessage.error("编码不能重复");
        }
        return ResultMessage.success("");
    }
}
