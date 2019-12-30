package cn.forest.service.impl;

import cn.forest.service.SysDictionaryDataService;
import cn.forest.system.entity.SysDictionaryData;
import cn.forest.system.mapper.SysDictionaryDataMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysDictionaryDataService")
public class SysDictionaryDataServiceImpl extends ServiceImpl<SysDictionaryDataMapper, SysDictionaryData> implements SysDictionaryDataService {

    @Autowired
    private SysDictionaryDataMapper sysDictionaryDataMapper;

    @Override
    public boolean vaRepeat(SysDictionaryData sysDictionaryData) {
        if (sysDictionaryData.getCode() == null || sysDictionaryData.getName() == null) {
            return false;
        }
        QueryWrapper<SysDictionaryData> queryWrapper = new QueryWrapper<SysDictionaryData>();
        queryWrapper.eq("is_delete", 0);
        queryWrapper.eq("name", sysDictionaryData.getName()).or().eq("code", sysDictionaryData.getCode());
        if (sysDictionaryData.getId() != null) {
            queryWrapper.ne("id", sysDictionaryData.getId());
        }
        List<SysDictionaryData> list = sysDictionaryDataMapper.selectList(queryWrapper);
        return list == null || list.size() == 0;
    }

    @Override
    public int delete(Long id) {
        SysDictionaryData sysDictionaryData = sysDictionaryDataMapper.selectById(id);
        if(sysDictionaryData != null){
            sysDictionaryData.setIsDelete(1);
            return sysDictionaryDataMapper.updateById(sysDictionaryData);
        }
        return 0;
    }
}
