package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.common.util.StringUtil;
import cn.forest.mall.entity.SupplierBalanceRecord;
import cn.forest.mall.entity.Suppliers;
import cn.forest.mall.mapper.SupplierBalanceRecordMapper;
import cn.forest.mall.mapper.SuppliersMapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supplierBalanceRecord")
public class SupplierBalanceRecordAction {

    @Autowired
    private SupplierBalanceRecordMapper supplierBalanceRecordMapper;
    
    @Autowired
    private SuppliersMapper suppliersMapper;

    /**
     * 商户余额列表
     *
     * @param map
     * @return
     */
    @RequestMapping("/balanceList")
    public Object balanceList(@RequestBody Map<String, Object> map) {
      if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
        Page<Suppliers> ipage = new Page<Suppliers>(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
        QueryWrapper<Suppliers> queryWrapper = new QueryWrapper<Suppliers>();
        if(!StringUtils.isEmpty(map.get("supplierName"))) {
          queryWrapper.eq("name", map.get("supplierName"));
        }
        if(!StringUtils.isEmpty(map.get("supplierId"))) {
          queryWrapper.eq("id", map.get("supplierId"));
        }
        queryWrapper.eq("status", 1);
        queryWrapper.orderByDesc("id");
        IPage<Suppliers> selectPage = suppliersMapper.selectPage(ipage, queryWrapper);
        List<Suppliers> records = selectPage.getRecords();
        for (Suppliers suppliers : records) {
          Map<String, Object> mapRecord=new HashMap<String, Object>();
          mapRecord.put("supplierId", suppliers.getId());
          SupplierBalanceRecord balanceList = supplierBalanceRecordMapper.balanceList(mapRecord);
          if(balanceList!=null) {
            suppliers.setBalance(balanceList.getMoney());
          }
          
          
        }
        return new ResultPage<Suppliers>(selectPage);
        
      }
    /*
     * if (StringUtil.toString(map.get("page")) != null &&
     * StringUtil.toString(map.get("pageSize")) != null) {
     * PageHelper.startPage(Integer.parseInt(map.get("page").toString()),
     * Integer.parseInt(map.get("pageSize").toString()));
     * List<SupplierBalanceRecord> supplierBalanceRecords =
     * supplierBalanceRecordMapper.balanceList(map); PageInfo<SupplierBalanceRecord>
     * supplierBalanceRecordsPage = new
     * PageInfo<SupplierBalanceRecord>(supplierBalanceRecords); return new
     * ResultPage<SupplierBalanceRecord>(supplierBalanceRecordsPage); }
     */
        return null;
    }

    /**
     * 商户直充记录查询
     *
     * @param map
     * @return
     */
    @RequestMapping("/recordList")
    public Object recordList(@RequestBody Map<String, Object> map) {
        if (StringUtil.toString(map.get("page")) != null && StringUtil.toString(map.get("pageSize")) != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("pageSize").toString()));
            
            List<SupplierBalanceRecord> recordlist = supplierBalanceRecordMapper.recordlist(map);
            PageInfo<SupplierBalanceRecord> recordPageInfo = new PageInfo<SupplierBalanceRecord>(recordlist);
            return new ResultPage<SupplierBalanceRecord>(recordPageInfo);
        }
        return null;
    }

    @RequestMapping("/save")
    public int save(@RequestBody SupplierBalanceRecord supplierBalanceRecord){
        return supplierBalanceRecordMapper.insert(supplierBalanceRecord);
    }

}
