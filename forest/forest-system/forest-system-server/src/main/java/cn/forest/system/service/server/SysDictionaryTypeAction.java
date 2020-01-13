package cn.forest.system.service.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysDictionaryType;
import cn.forest.system.mapper.SysDictionaryTypeMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sysDictionaryType")
public class SysDictionaryTypeAction {

    @Autowired
    private SysDictionaryTypeMapper sysDictionaryTypeMapper;

    @RequestMapping("/list")
    public Object list(Long page, Long pageSize,String name) {
        Page<SysDictionaryType> ipage = new Page<SysDictionaryType>(page, pageSize);
        QueryWrapper<SysDictionaryType> queryWrapper = new QueryWrapper<SysDictionaryType>();
        if(!StringUtils.isEmpty(name)) {
          queryWrapper.like("name", name).or().like("code", name);
        }
        queryWrapper.orderByAsc("id");
        IPage<SysDictionaryType> selectPage = sysDictionaryTypeMapper.selectPage(ipage, queryWrapper);
        return new ResultPage<SysDictionaryType>(selectPage);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return sysDictionaryTypeMapper.selectById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody SysDictionaryType sysDictionaryType) {
        return sysDictionaryTypeMapper.insert(sysDictionaryType);
    }

    @RequestMapping("/update")
    public int update(@RequestBody SysDictionaryType sysDictionaryType) {
      return sysDictionaryTypeMapper.updateById(sysDictionaryType);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
      return sysDictionaryTypeMapper.deleteById(id);
        
    }
    
    @RequestMapping("/getAll")
    public Object getAll() {
      QueryWrapper<SysDictionaryType> queryWrapper = new QueryWrapper<SysDictionaryType>();
      queryWrapper.orderByAsc("id");
      return sysDictionaryTypeMapper.selectList(queryWrapper);
     }

}
