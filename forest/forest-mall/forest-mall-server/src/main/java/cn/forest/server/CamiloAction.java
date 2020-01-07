package cn.forest.server;

import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.DateUtil;
import cn.forest.mall.entity.Camilo;
import cn.forest.service.CamiloService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/camilo")
public class CamiloAction {

    @Autowired
    private CamiloService camiloService;

    @RequestMapping("/save")
    public Object save(@RequestBody Camilo camilo) {
        boolean save = camiloService.save(camilo);
        return ResultSave.resultSave(save ? 1 : 0, camilo);
    }

    @RequestMapping("/update")
    public int update(@RequestBody Camilo camilo) {
        boolean update = camiloService.updateById(camilo);
        return update ? 1 : 0;
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        boolean delete = camiloService.removeById(id);
        return delete ? 1 : 0;
    }

    @RequestMapping("/batchSave")
    public int batchSave(@RequestBody List<Camilo> camilos) {
        boolean b = camiloService.saveBatch(camilos);
        return b ? 1 : 0;
    }

    @RequestMapping("/selectByProductId")
    public Object selectByProductId(@RequestParam("productId") Long productId) {
        QueryWrapper<Camilo> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        qw.gt("failure_time",DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        qw.orderByAsc(new String[]{"failure_time","id"});
        return camiloService.list(qw);
    }

    @RequestMapping("/countByProductId")
    public int countByProductId(@RequestParam("productId") Long productId) {
        QueryWrapper<Camilo> qw = new QueryWrapper<>();
        qw.eq("product_id", productId);
        List<Camilo> list = camiloService.list(qw);
        qw.gt("failure_time",DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD));
        if (!CollectionUtils.isEmpty(list)) {
            return list.size();
        }
        return 0;
    }
}
