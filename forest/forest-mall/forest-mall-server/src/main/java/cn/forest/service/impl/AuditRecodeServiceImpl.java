package cn.forest.service.impl;

import cn.forest.mall.entity.AuditRecode;
import cn.forest.mall.mapper.AuditRecodeMapper;
import cn.forest.service.AuditRecodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("auditRecodeService")
public class AuditRecodeServiceImpl extends ServiceImpl<AuditRecodeMapper, AuditRecode> implements AuditRecodeService {
}
