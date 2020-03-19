package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.mall.service.CamiloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/camilo")
@SysLogs(desc = "卡密管理")
public class CamiloController {

    @Autowired
    private CamiloService camiloService;

    @SysLogs(desc = "卡密列表查询")
    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return camiloService.list(request);
    }

    @SysLogs(desc = "查询卡密记录")
    @RequestMapping("/recordList")
    public Map<String, Object> recordList(HttpServletRequest request) {
        return camiloService.recordList(request);
    }

    /**
     * excel导入卡密
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "excel导入卡密")
    @RequestMapping(value = "/importExcel")
    public Map<String, Object> importExcel(HttpServletRequest request) throws IOException {
        return camiloService.importExcel(request);
    }

    /**
     * 下载卡密模版
     *
     * @param response
     * @throws IOException
     */
    @SysLogs(desc = "下载卡密模版")
    @RequestMapping(value = "/downloadTemplate")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        camiloService.doemloadTemplate(request, response);
    }

    /**
     * excel导出卡密
     *
     * @param request
     * @return
     */
    @SysLogs(desc = "excel导出卡密")
    @RequestMapping(value = "/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, String productIds) throws IOException {
        camiloService.exportExcel(request, response, productIds);
    }
}