package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.CompanyRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service("companyService")
public class CompanyService {

    @Autowired
    private CompanyRemote companyRemote;

    public Map<String, Object> list(Long page, Long pageSize, String code, String name) {
        Object list = companyRemote.list(page, pageSize, code, name);
        if(list != null){
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, Object> map){
        int save = companyRemote.save(map);
        return ResultMessage.result(save, "保存成功", "保存失败");
    }

    public Map<String, Object> update(Map<String, Object> map){
        int update = companyRemote.update(map);
        return ResultMessage.result(update, "修改成功", "修改失败");
    }

    public Map<String, Object> delete(Long id){
        int delete = companyRemote.delete(id);
        return ResultMessage.result(delete, "删除成功", "删除失败");
    }

    public Map<String, Object> getById(Long id){
        Object obj = companyRemote.getById(id);
        if(obj != null){
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> getAll(){
        Object obj = companyRemote.getAll();
        if(obj != null){
            return ResultMessage.success(obj);
        }
        return null;
    }

    public Map<String, Object> batchDelete(String ids){
        int i = companyRemote.batchDelete(ids);
        return ResultMessage.result(i, "删除成功", "删除失败");
    }
}
