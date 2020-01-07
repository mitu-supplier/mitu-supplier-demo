package cn.forest.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.forest.common.service.utils.ResultPage;
import cn.forest.lyj.entity.Position;
import cn.forest.lyj.mapper.PositionMapper;

@RestController
@RequestMapping("/position")
public class PositionAction {

  @Autowired
  private PositionMapper positionMapper;
  
  /**
   * 条件分页查询
   *
   * @param page
   * @param pageSize
   * @return
   */
  @RequestMapping("/list")
  public Object list(@RequestParam("page") Long page, @RequestParam("pageSize") Long pageSize) {
      Page<Position> pages = new Page<Position>(page, pageSize);
      QueryWrapper<Position> queryWrapper = new QueryWrapper<Position>();
      queryWrapper.orderByAsc("id");
      IPage<Position> selectPage = positionMapper.selectPage(pages, queryWrapper);
      return new ResultPage<Position>(selectPage);
       
  }

  @RequestMapping("/getById")
  public Object getById(@RequestParam("id") Long id) {
      return positionMapper.selectById(id);
  }

  @RequestMapping("/save")
  public int save(@RequestBody Position position) {
      return positionMapper.insert(position);
  }

  @RequestMapping("/update")
  public int update(@RequestBody Position position) {
    return positionMapper.updateById(position);
  }

  @RequestMapping("/delete")
  public int delete(@RequestParam("id") Long id) {
      return positionMapper.deleteById(id);
  }
  
}
