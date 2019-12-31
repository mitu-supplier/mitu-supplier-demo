package cn.forest.system.service.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysDictionaryData;
import cn.forest.system.mapper.SysDictionaryDataMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sysDictionaryData")
public class SysDictionaryDataAction {

    
    
    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

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
        queryWrapper.orderByAsc("id");
        IPage<SysDictionaryData> selectPage = sysDictionaryDataMapper.selectPage(pages, queryWrapper);
        return new ResultPage<SysDictionaryData>(selectPage);
         
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return sysDictionaryDataMapper.selectById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody SysDictionaryData sysDictionaryData) {
        return sysDictionaryDataMapper.insert(sysDictionaryData);
    }

    @RequestMapping("/update")
    public int update(@RequestBody SysDictionaryData sysDictionaryData) {
      return sysDictionaryDataMapper.updateById(sysDictionaryData);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return sysDictionaryDataMapper.deleteById(id);
    }

}
