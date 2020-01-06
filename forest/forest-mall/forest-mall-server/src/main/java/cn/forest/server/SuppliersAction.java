package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.function.Supplier;

@RestController
@RequestMapping("/suppliers")
public class SuppliersAction {

    @Autowired
    private SuppliersMapper suppliersMapper;

    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        QueryWrapper<Suppliers> queryWrapper = new QueryWrapper<Suppliers>();
        if (map.get("status") != null) {
            queryWrapper.eq("status", map.get("status"));
        }
        if (StringUtil.toString(map.get("code")) != null) {
            queryWrapper.eq("code", map.get("code"));
        }
        if (StringUtil.toString(map.get("name")) != null) {
            queryWrapper.like("name", map.get("name"));
        }
        if (StringUtil.toString(map.get("shortName")) != null) {
            queryWrapper.like("short_name", map.get("shortName"));
        }
        if (StringUtil.toString(map.get("contactName")) != null) {
            queryWrapper.like("contact_name", map.get("contactName"));
        }
        if (StringUtil.toString(map.get("contactMobile")) != null) {
            queryWrapper.like("contact_mobile", map.get("contactMobile"));
        }
        queryWrapper.orderByDesc("created_at");
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            Long page = Long.parseLong(map.get("page").toString());
            Long pageSize = Long.parseLong(map.get("pageSize").toString());
            Page<Suppliers> pages = new Page<Suppliers>(page, pageSize);
            IPage<Suppliers> suppliersIPage = suppliersMapper.selectPage(pages, queryWrapper);
            return new ResultPage<Suppliers>(suppliersIPage);
        }
        return null;
    }

    @RequestMapping("/save")
    public Object save(@RequestBody Suppliers suppliers) {
        int insert = suppliersMapper.insert(suppliers);
        return ResultSave.resultSave(insert, suppliers);
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
        if (suppliers != null) {
            suppliers.setStatus(status);
            return suppliersMapper.updateById(suppliers);
        }
        return 0;
    }
}
