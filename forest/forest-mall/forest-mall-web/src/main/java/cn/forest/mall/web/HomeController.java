package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 后台首页数据
 */
@RestController
@RequestMapping("/home")
@SysLogs(desc = "后台首页管理")
public class HomeController {

    @Autowired
    private HomeService homeService;

    /**
     * 获取当前登录人信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "获取当前登录人信息")
    @RequestMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        return homeService.getUserInfo(request);
    }

    /**
     * 获取待审核信息
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "获取待审核信息")
    @RequestMapping("/getAuditInfo")
    public Map<String, Object> getAuditInfo(HttpServletRequest request) {
        return homeService.getAuditInfo(request);
    }

    /**
     * 最近7天的访问量
     *
     * @return
     */
    @SysLogs(desc = "最近7天的访问量")
    @RequestMapping("/count7Days")
    public Map<String, Object> count7Days() {
        return homeService.count7Days();
    }
}
