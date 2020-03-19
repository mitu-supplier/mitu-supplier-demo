package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.CompanyService;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
@SysLogs(desc = "供应商信息管理")
public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private CompanyService companyService;

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @SysLogs(desc = "供应商查看详情")
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return suppliersService.view(id);
    }

    /**
     * 商户注册前两步 保存用户信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商户注册保存信息")
    @RequestMapping("/saveStepOneTwo")
    public Map<String, Object> saveStepOneTwo(HttpServletRequest request) {
        return suppliersService.saveStepOneTwo(RequestMap.requestToMap(request));
    }

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "商户注册保存信息")
    @RequestMapping("/register")
    public Map<String, Object> register(HttpServletRequest request) {
        return suppliersService.saveStepOther(RequestMap.requestToMap(request));
    }

    /**
     * 根据code获取字典表数据
     *
     * @return
     */
    @SysLogs(desc = "根据code获取字典表数据")
    @RequestMapping("/getDictionaryData")
    public Map<String, Object> getDictionaryData() {
        return suppliersService.getDictionaryData();
    }

    /**
     * 获取当前注册人信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "获取当前注册人信息")
    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        return suppliersService.getUserInfo(request);
    }

    /**
     * 获取商户入驻类型
     *
     * @return
     */
    @SysLogs(desc = "获取商户入驻类型")
    @RequestMapping("/getEnterType")
    public Map<String, Object> getEnterType() {
        return suppliersService.getEnterType();
    }

    /**
     * 获取签约公司
     *
     * @return
     */
    @SysLogs(desc = "获取签约公司")
    @RequestMapping("/getCompany")
    public Map<String, Object> getCompany() {
        return companyService.getAll();
    }

    /**
     * 查看商户信息列表
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "查看商户信息列表")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return suppliersService.list(request);
    }

    /**
     * 判断是否为供应商
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "判断是否为供应商")
    @RequestMapping("/isSupplier")
    public Map<String, Object> isSupplier(HttpServletRequest request) {
        if (suppliersService.isSupplier(request)) {
            return ResultMessage.success(1);
        }
        return ResultMessage.success(0);
    }

    /**
     * 获取供应商code
     *
     * @return
     */
    @SysLogs(desc = "获取供应商code")
    @RequestMapping("/getSupplierCode")
    public Map<String, Object> getSupplierCode() {
        return ResultMessage.success(suppliersService.getSupplierCode());
    }

    /**
     * 新增供应商信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "新增供应商信息")
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return suppliersService.save(RequestMap.requestToMap(request));
    }

    /**
     * 供应商信息修改
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "供应商信息修改")
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return suppliersService.saveStepOther(RequestMap.requestToMap(request));
    }
}
