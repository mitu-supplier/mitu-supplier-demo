package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 公司列表
 */
@RestController
@RequestMapping("/company")
@SysLogs(desc = "公司信息管理")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @SysLogs(desc = "公司列表查询")
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("code") String code, @RequestParam("name") String name) {
        return companyService.list(page, pageSize, code, name);
    }

    @SysLogs(desc = "新增公司信息")
    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request) {
        return companyService.save(RequestMap.requestToMap(request));
    }

    @SysLogs(desc = "修改公司信息")
    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request) {
        return companyService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping("/删除公司信息")
    public Map<String, Object> delete(@RequestParam("id") Long id) {
        return companyService.delete(id);
    }

    @SysLogs(desc = "查询公司信息")
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return companyService.getById(id);
    }

    @SysLogs(desc = "批量删除公司信息")
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return companyService.batchDelete(ids);
    }

    @SysLogs(desc = "获取公司编号")
    @RequestMapping("/getCompanyCode")
    public Map<String, Object> getCompanyCode() {
        return ResultMessage.success(companyService.getCompanyCode());
    }
}
