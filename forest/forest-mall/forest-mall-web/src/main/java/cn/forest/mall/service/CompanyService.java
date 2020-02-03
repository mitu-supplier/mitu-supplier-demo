package cn.forest.mall.service;

import cn.forest.common.util.ResultMessage;
import cn.forest.mall.remote.CompanyRemote;
import cn.forest.mall.remote.SysSequenceRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service("companyService")
public class CompanyService {

    @Autowired
    private CompanyRemote companyRemote;

    @Autowired
    private SysSequenceRemote sysSequenceRemote;

    public Map<String, Object> list(Long page, Long pageSize, String code, String name) {
        Object list = companyRemote.list(page, pageSize, code, name);
        if(list != null){
            return ResultMessage.success(list);
        }
        return null;
    }

    public Map<String, Object> save(Map<String, Object> map){
        int result = companyRemote.vaName(null, map.get("name").toString());
        if(result > 0){
            return ResultMessage.error("公司名称已存在");
        }
        int save = companyRemote.save(map);
        return ResultMessage.result(save, "保存成功", "保存失败");
    }

    public Map<String, Object> update(Map<String, Object> map){
        int result = companyRemote.vaName(Long.parseLong(map.get("id").toString()), map.get("name").toString());
        if(result > 0){
            return ResultMessage.error("公司名称已存在");
        }
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

    /**
     * 获取公司id
     *
     * @return
     */
    public String getCompanyCode() {
        String name = "10";
        int count = sysSequenceRemote.countByName(name);
        if (count < 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name);
            map.put("max", 0L);
            map.put("length", 7);
            map.put("next", 1);
            sysSequenceRemote.save(map);
        }
        return name + sysSequenceRemote.getSeqValue(name);
    }
}
