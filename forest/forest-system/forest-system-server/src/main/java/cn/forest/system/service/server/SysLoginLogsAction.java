package cn.forest.system.service.server;

import cn.forest.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.system.entity.SysLoginLogs;
import cn.forest.system.mapper.SysLoginLogsMapper;

import java.util.List;

@RestController
@RequestMapping("sys_login_logs")
public class SysLoginLogsAction {

    @Autowired
    private SysLoginLogsMapper sysLoginLogsMapper;

    @RequestMapping("/add")
    public int add(@RequestBody SysLoginLogs loginLogs) {
        return sysLoginLogsMapper.insert(loginLogs);
    }

    @RequestMapping("/list")
    public Object getList(@RequestParam(value = "page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam(value = "userName", required = false) String userName) {
        Page<SysLoginLogs> pages = new Page<SysLoginLogs>(page, pageSize);
        QueryWrapper<SysLoginLogs> queryWrapper = new QueryWrapper<SysLoginLogs>();
        if (!StringUtil.isBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        queryWrapper.orderByDesc("create_time");
        IPage<SysLoginLogs> selectPage = sysLoginLogsMapper.selectPage(pages, queryWrapper);
        return new ResultPage<SysLoginLogs>(selectPage);
    }

    @RequestMapping("/count7Days")
    public Object count7Days() {
        List<SysLoginLogs> sysLoginLogs = sysLoginLogsMapper.count7Days();
        if (!CollectionUtils.isEmpty(sysLoginLogs)) {
            for (SysLoginLogs sysLoginLog : sysLoginLogs) {
                sysLoginLog.setCreateTime(sysLoginLog.getCreateTime().replaceAll("-", "/"));
            }
        }
        return sysLoginLogs;
    }

    /**
     * 查询用户上次登录信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectUserLastLoginInfo")
    public Object selectUserLastLoginInfo(@RequestParam("userId") Long userId) {
        QueryWrapper<SysLoginLogs> queryWrapper = new QueryWrapper<SysLoginLogs>();
        queryWrapper.like("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        List<SysLoginLogs> sysLoginLogs = sysLoginLogsMapper.selectList(queryWrapper);
        if(sysLoginLogs != null && sysLoginLogs.size() >= 2){
            return sysLoginLogs.get(1);
        }
        return null;
    }
}
