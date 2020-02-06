package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.Products;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SuppliersMapper;
import cn.forest.service.SuppliersService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/suppliers")
public class SuppliersAction {

    @Autowired
    private SuppliersMapper suppliersMapper;

    @Autowired
    private SuppliersService suppliersService;

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

    /**
     * 获取所有可用的供应商信息
     *
     * @return
     */
    @RequestMapping("/getAll")
    public Object getAll() {
        QueryWrapper<Suppliers> suppliersQueryWrapper = new QueryWrapper<>();
        suppliersQueryWrapper.eq("status", 1);
        suppliersQueryWrapper.select(new String[]{"id", "name"});
        return suppliersMapper.selectList(suppliersQueryWrapper);
    }

    @RequestMapping("/batchUpdate")
    public int batchUpdate(@RequestBody List<Suppliers> list) {
        boolean b = suppliersService.updateBatchById(list);
        return b ? 1 : 0;
    }

    /**
     * 数量统计
     *
     * @param status
     * @param id
     * @return
     */
    @RequestMapping("/selectList")
    public Object selectList(@RequestParam(value = "status", required = false) Integer status,
                               @RequestParam(value = "id", required = false) Long id) {
        QueryWrapper<Suppliers> qw = new QueryWrapper<>();
        if (id != null) {
            qw.eq("id", id);
        }
        if (status != null) {
            qw.eq("status", status);
        }
        return suppliersService.list(qw);
    }
}
