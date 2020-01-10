package cn.forest.service.impl;

import cn.forest.mall.entity.CamiloRecord;
import cn.forest.mall.mapper.CamiloRecordMapper;
import cn.forest.service.CamiloRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("camiloRecordService")
public class CamiloRecordServiceImpl extends ServiceImpl<CamiloRecordMapper, CamiloRecord> implements CamiloRecordService {

    @Autowired
    private CamiloRecordMapper camiloRecodeMapper;

    @Override
    public List<CamiloRecord> recordlist(Map<String, Object> map) {
        return camiloRecodeMapper.recordlist(map);
    }
}
