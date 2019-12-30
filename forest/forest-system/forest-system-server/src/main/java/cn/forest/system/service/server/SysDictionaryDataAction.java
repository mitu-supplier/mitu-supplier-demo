package cn.forest.system.service.server;

import cn.forest.service.SysDictionaryDataService;
import cn.forest.system.entity.SysDictionaryData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysDictionaryData")
public class SysDictionaryDataAction {

    @Autowired
    private SysDictionaryDataService sysDictionaryDataService;

    /**
     * 条件分页查询
     *
     * @param page
     * @param pageSize
     * @param type     类型
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("type") Long type) {
        Page<SysDictionaryData> pages = new Page<SysDictionaryData>(page, pageSize);
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<SysDictionaryData>();
        queryWrapper.eq("type", type);
        queryWrapper.eq("is_delete", 0);
        queryWrapper.orderByAsc("id");
        return sysDictionaryDataService.page(pages, queryWrapper);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return sysDictionaryDataService.getById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody SysDictionaryData sysDictionaryData) {
        if (!sysDictionaryDataService.vaRepeat(sysDictionaryData)) {
            return 0;
        }
        sysDictionaryData.setIsDelete(0);
        boolean save = sysDictionaryDataService.save(sysDictionaryData);
        if (save) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/update")
    public int update(@RequestBody SysDictionaryData sysDictionaryData) {
        if (!sysDictionaryDataService.vaRepeat(sysDictionaryData)) {
            return 0;
        }
        boolean update = sysDictionaryDataService.updateById(sysDictionaryData);
        if (update) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return sysDictionaryDataService.delete(id);
    }

}
