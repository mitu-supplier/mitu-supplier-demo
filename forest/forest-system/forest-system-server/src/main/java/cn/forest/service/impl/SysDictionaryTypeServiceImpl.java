package cn.forest.service.impl;

import cn.forest.service.SysDictionaryTypeService;
import cn.forest.system.entity.SysDictionaryType;
import cn.forest.system.mapper.SysDictionaryTypeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysDictionaryTypeService")
public class SysDictionaryTypeServiceImpl extends ServiceImpl<SysDictionaryTypeMapper, SysDictionaryType> implements SysDictionaryTypeService {

    @Autowired
    private SysDictionaryTypeMapper sysDictionaryTypeMapper;

    @Override
    public boolean vaRepeat(SysDictionaryType sysDictionaryType) {
        if(sysDictionaryType.getCode() == null || sysDictionaryType.getName() == null){
            return false;
        }
        QueryWrapper<SysDictionaryType> queryWrapper = new QueryWrapper<SysDictionaryType>();
        queryWrapper.eq("name", sysDictionaryType.getName()).or().eq("code", sysDictionaryType.getCode());
        if(sysDictionaryType.getId() != null){
            queryWrapper.ne("id", sysDictionaryType.getId());
        }
        List<SysDictionaryType> list = sysDictionaryTypeMapper.selectList(queryWrapper);
        return list == null || list.size() == 0;
    }
}
