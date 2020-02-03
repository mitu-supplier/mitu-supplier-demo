package cn.forest.server;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.mall.entity.Company;
import cn.forest.mall.mapper.CompanyMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyAction {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 条件分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize, @RequestParam("code") String code, @RequestParam("name") String name) {
        Page<Company> pages = new Page<Company>(page, pageSize);
        QueryWrapper<Company> queryWrapper = new QueryWrapper<Company>();
        if (name != null && !name.equals("")) {
            queryWrapper.like("name", name);
        }
        if (code != null && !code.equals("")) {
            queryWrapper.like("code", code);
        }
        queryWrapper.orderByDesc("created_at");
        IPage<Company> selectPage = companyMapper.selectPage(pages, queryWrapper);
        return new ResultPage<Company>(selectPage);

    }

    @RequestMapping("/getById")
    public Object getById(@RequestParam("id") Long id) {
        return companyMapper.selectById(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody Company company) {
        return companyMapper.insert(company);
    }

    @RequestMapping("/update")
    public int update(@RequestBody Company company) {
        return companyMapper.updateById(company);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Long id) {
        return companyMapper.deleteById(id);
    }

    @RequestMapping("/getAll")
    public Object getAll() {
        return companyMapper.selectList(new QueryWrapper<Company>());
    }

    @RequestMapping("/batchDelete")
    public int batchDelete(@RequestParam("ids") String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            List<Long> idList = new ArrayList<>();
            for (String str : split) {
                companyMapper.deleteById(Long.parseLong(str));
            }
            return 1;
        }
        return 0;
    }

    @RequestMapping("vaName")
    public int vaName(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "name") String name){
        QueryWrapper<Company> qw = new QueryWrapper<>();
        qw.eq("name", name);
        if(id != null){
            qw.ne("id", id);
        }
        List<Company> companies = companyMapper.selectList(qw);
        if(CollectionUtils.isEmpty(companies)){
            return 0;
        }
        return companies.size();
    }
}
