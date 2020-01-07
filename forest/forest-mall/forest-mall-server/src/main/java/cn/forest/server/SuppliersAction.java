package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suppliers")
public class SuppliersAction {

    @Autowired
    private SuppliersMapper suppliersMapper;

    @RequestMapping("/list")
    public Object list(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            List<Suppliers> products = suppliersMapper.selectListByMap(map);
            PageInfo<Suppliers> suppliersPage = new PageInfo<Suppliers>(products);
            return new ResultPage<Suppliers>(suppliersPage);
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
