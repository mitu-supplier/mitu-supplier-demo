package cn.forest.server;

import cn.forest.mall.entity.Suppliers;
import cn.forest.service.SuppliersService;
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
    private SuppliersService suppliersService;

    @RequestMapping("/list")
    public Object list(Map<String, Object> map) {
        return suppliersService.list(map);
    }

    @RequestMapping("/save")
    public int save(@RequestBody Suppliers suppliers) {
        suppliers.setIsDelete(0);
        boolean save = suppliersService.save(suppliers);
        if (save) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/update")
    public int update(@RequestBody Suppliers suppliers) {
        boolean update = suppliersService.updateById(suppliers);
        if (update) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return suppliersService.delete(id);
    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return suppliersService.getById(id);
    }

    @RequestMapping("/updateStatus")
    public int updateStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        return suppliersService.updateStatus(id, status);
    }
}
