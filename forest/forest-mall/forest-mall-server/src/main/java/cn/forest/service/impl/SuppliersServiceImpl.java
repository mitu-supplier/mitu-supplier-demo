package cn.forest.service.impl;

import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import cn.forest.service.SuppliersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("suppliersService")
public class SuppliersServiceImpl extends ServiceImpl<SuppliersMapper, Suppliers> implements SuppliersService {

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Override
    public Object list(Map<String, Object> map) {
        Long page = Long.parseLong(map.get("page").toString());
        Long pageSize = Long.parseLong(map.get("pageSize").toString());
        Page<Suppliers> pages = new Page<Suppliers>(page, pageSize);
        QueryWrapper<Suppliers> queryWrapper = new QueryWrapper<Suppliers>();
        if(map.get("status") != null){
            queryWrapper.eq("status", map.get("status"));
        }
        if (map.get("name") != null) {
            queryWrapper.like("name", map.get("name"));
        }
        if (map.get("isDelete") != null) {
            queryWrapper.eq("is_delete", 0);
        }
        queryWrapper.orderByDesc("created_at");
        return suppliersMapper.selectPage(pages, queryWrapper);
    }

    @Override
    public int delete(Long id) {
        Suppliers suppliers = suppliersMapper.selectById(id);
        if(suppliers != null){
            suppliers.setIsDelete(1);
            return suppliersMapper.updateById(suppliers);
        }
        return 0;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        Suppliers suppliers = suppliersMapper.selectById(id);
        if(suppliers != null){
            suppliers.setStatus(status);
            return suppliersMapper.updateById(suppliers);
        }
        return 0;
    }
}
