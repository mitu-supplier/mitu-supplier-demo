package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.service.utils.ResultSave;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.DeliveryCode;
import cn.forest.mall.mapper.DeliveryCodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/deliveryCode")
public class DeliveryCodeAction {

    @Autowired
    private DeliveryCodeMapper deliveryCodeMapper;

    /**
     * 分页查询
     *
     * @param page       页码
     * @param pageSize   每页大小
     * @param supplierId 供应商id
     * @param code       发货代号
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page,
                       @RequestParam("pageSize") Long pageSize,
                       @RequestParam(value = "supplierId", required = false) Long supplierId,
                       @RequestParam(value = "code", required = false) String code) {

        QueryWrapper<DeliveryCode> deliveryCodeQueryWrapper = new QueryWrapper<>();
        if (supplierId != null) {
            deliveryCodeQueryWrapper.eq("supplier_id", supplierId);
        }
        if (!StringUtil.isBlank(code)) {
            deliveryCodeQueryWrapper.like("code", code);
        }
        Page<DeliveryCode> ipage = new Page<DeliveryCode>(page, pageSize);
        IPage<DeliveryCode> selectPage = deliveryCodeMapper.selectPage(ipage, deliveryCodeQueryWrapper);
        return new ResultPage<DeliveryCode>(selectPage);
    }

    /**
     * 根据供应商查询发货代号
     *
     * @param supplierId 供应商id
     * @return
     */
    @RequestMapping("/select")
    public Object select(@RequestParam(value = "supplierId") Long supplierId) {
        QueryWrapper<DeliveryCode> deliveryCodeQueryWrapper = new QueryWrapper<>();
        deliveryCodeQueryWrapper.eq("supplier_id", supplierId);
        deliveryCodeQueryWrapper.orderByAsc("id");
        return deliveryCodeMapper.selectList(deliveryCodeQueryWrapper);
    }

    /**
     * 新增
     *
     * @param deliveryCode 发货代号信息
     * @return
     */
    @RequestMapping("/save")
    public Object save(@RequestBody DeliveryCode deliveryCode) {
        String code = deliveryCode.getCode();
        Long supplierId = deliveryCode.getSupplierId();
        if (supplierId != null && StringUtil.isNotBlank(code)) {
            QueryWrapper<DeliveryCode> deliveryCodeQueryWrapper = new QueryWrapper<>();
            deliveryCodeQueryWrapper.eq("supplier_id", supplierId);
            deliveryCodeQueryWrapper.like("code", code);
            List<DeliveryCode> deliveryCodes = deliveryCodeMapper.selectList(deliveryCodeQueryWrapper);
            if (CollectionUtils.isEmpty(deliveryCodes)) {
                int insert = deliveryCodeMapper.insert(deliveryCode);
                return ResultSave.resultSave(insert, deliveryCode);
            } else {
                return ResultSave.resultSave(1, deliveryCodes.get(0));
            }
        }
        return ResultSave.resultSave(0, deliveryCode);
    }

    /**
     * 通过id修改
     *
     * @param deliveryCode
     * @return
     */
    @RequestMapping("/update")
    public int update(@RequestBody DeliveryCode deliveryCode) {
        return deliveryCodeMapper.updateById(deliveryCode);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return deliveryCodeMapper.deleteById(id);
    }
}
