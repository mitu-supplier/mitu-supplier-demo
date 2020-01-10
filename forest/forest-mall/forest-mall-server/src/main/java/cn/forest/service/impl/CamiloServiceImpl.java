package cn.forest.service.impl;

import cn.forest.mall.entity.Camilo;
import cn.forest.mall.mapper.CamiloMapper;
import cn.forest.service.CamiloService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("camiloService")
public class CamiloServiceImpl extends ServiceImpl<CamiloMapper, Camilo> implements CamiloService {

}
