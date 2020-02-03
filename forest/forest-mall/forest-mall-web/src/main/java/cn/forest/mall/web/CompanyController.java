package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.util.ResultMessage;
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
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("code") String code, @RequestParam("name") String name){
        return companyService.list(page, pageSize, code, name);
    }

    @RequestMapping("/save")
    public Map<String, Object> save(HttpServletRequest request){
        return companyService.save(RequestMap.requestToMap(request));
    }

    @RequestMapping("/update")
    public Map<String, Object> update(HttpServletRequest request){
        return companyService.update(RequestMap.requestToMap(request));
    }

    @RequestMapping("/delete")
    public Map<String, Object> delete(@RequestParam("id") Long id){
        return companyService.delete(id);
    }

    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id){
        return companyService.getById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestParam("ids") String ids) {
        return companyService.batchDelete(ids);
    }

    /**
     * 获取公司id
     * @return
     */
    @RequestMapping("/getCompanyCode")
    public Map<String, Object> getCompanyCode(){
        return ResultMessage.success(companyService.getCompanyCode());
    }
}
