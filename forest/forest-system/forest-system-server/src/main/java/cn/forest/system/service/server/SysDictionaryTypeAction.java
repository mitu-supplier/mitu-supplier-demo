package cn.forest.system.service.server;

import cn.forest.service.SysDictionaryTypeService;
import cn.forest.system.entity.SysDictionaryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysDictionaryType")
public class SysDictionaryTypeAction {

    @Autowired
    private SysDictionaryTypeService sysDictionaryTypeService;

    @RequestMapping("/list")
    public Object list() {
        QueryWrapper<SysDictionaryType> queryWrapper = new QueryWrapper<SysDictionaryType>();
        queryWrapper.orderByAsc("id");
        return sysDictionaryTypeService.list(queryWrapper);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return sysDictionaryTypeService.getById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody SysDictionaryType sysDictionaryType) {
        if(!sysDictionaryTypeService.vaRepeat(sysDictionaryType)){
            return 0;
        }
        boolean save = sysDictionaryTypeService.save(sysDictionaryType);
        if (save) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/update")
    public int update(@RequestBody SysDictionaryType sysDictionaryType) {
        if(!sysDictionaryTypeService.vaRepeat(sysDictionaryType)){
            return 0;
        }
        boolean update = sysDictionaryTypeService.updateById(sysDictionaryType);
        if (update) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        boolean delete = sysDictionaryTypeService.removeById(id);
        if (delete) {
            return 1;
        }
        return 0;
    }

}
