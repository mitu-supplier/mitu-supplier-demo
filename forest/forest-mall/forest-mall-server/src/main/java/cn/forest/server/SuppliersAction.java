package cn.forest.server;

import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/suppliers")
public class SuppliersAction {

    @Autowired
    private SuppliersMapper suppliersMapper;

    @RequestMapping("/list")
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
        queryWrapper.orderByDesc("created_at");
        return suppliersMapper.selectPage(pages, queryWrapper);
    }

    @RequestMapping("/save")
    public Object save(@RequestBody Suppliers suppliers) {
        int insert = suppliersMapper.insert(suppliers);
        if(insert > 0){
            return suppliers.getId();
        }
        return null;
    }

    @RequestMapping("/update")
    public int update(@RequestBody Suppliers suppliers) {
        return suppliersMapper.updateById(suppliers);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return suppliersMapper.deleteById(id);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return suppliersMapper.selectById(id);
    }

    @RequestMapping("/updateStatus")
    public int updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        Suppliers suppliers = suppliersMapper.selectById(id);
        if(suppliers != null){
            suppliers.setStatus(status);
            return suppliersMapper.updateById(suppliers);
        }
        return 0;
    }
}
