package cn.forest.service.impl;

import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import cn.forest.service.SuppliersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("suppliersService")
public class SuppliersServiceImpl extends ServiceImpl<SuppliersMapper, Suppliers> implements SuppliersService {
}
