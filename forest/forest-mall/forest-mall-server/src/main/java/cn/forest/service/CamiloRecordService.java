package cn.forest.service;

import cn.forest.mall.entity.CamiloRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface CamiloRecordService extends IService<CamiloRecord> {

    List<CamiloRecord> recordlist(Map<String, Object> map);
}
