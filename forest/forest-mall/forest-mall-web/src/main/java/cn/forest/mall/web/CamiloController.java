package cn.forest.mall.web;

import cn.forest.common.util.RequestMap;
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
public class CamiloController {

    @Autowired
    private CamiloService camiloService;

    @RequestMapping("/list")
    public Map<String, Object> list(HttpServletRequest request) {
        return camiloService.list(request);
    }

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
    @RequestMapping(value = "/downloadTemplate")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        camiloService.doemloadTemplate(request, response);
    }
}