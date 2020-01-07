package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.mall.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public Map<String, Object> getById(@RequestParam("id") Long id) {
        return suppliersService.getById(id);
    }

    /**
     * 商户注册前两步 保存用户信息
     *
     * @param request
     * @return
     */
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
    @RequestMapping("/register")
    public Map<String, Object> register(HttpServletRequest request) {
        return suppliersService.update(RequestMap.requestToMap(request));
    }

    /**
     * 根据code获取字典表数据
     *
     * @return
     */
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
    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        return suppliersService.getUserInfo(request);
    }

    /**
     * 获取商户入驻类型
     *
     * @return
     */
    @RequestMapping("/getEnterType")
    public Map<String, Object> getEnterType() {
        return suppliersService.getEnterType();
    }

    /**
     * 获取商户余额
     *
     * @param request
     * @return
     */
    @RequestMapping("/getBalance")
    public Map<String, Object> getBalance(HttpServletRequest request) {
        return suppliersService.getBalance(RequestMap.requestToMap(request));
    }
}
