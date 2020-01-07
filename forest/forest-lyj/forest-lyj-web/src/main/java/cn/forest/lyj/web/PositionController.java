package cn.forest.lyj.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.forest.common.util.RequestMap;
import cn.forest.common.web.util.SysLogs;
import cn.forest.lyj.service.PositionService;

@RestController
@RequestMapping("position")
@SysLogs(desc  = "岗位管理")
public class PositionController {

  @Autowired
  private PositionService positionService;
  
  
  @RequestMapping(value = "/list")
  @SysLogs(desc = "岗位列表")
  public Map<String, Object> list(Long page, Long pageSize){
      return positionService.list(page, pageSize);
  }

  @RequestMapping(value = "/getById")
  @SysLogs(desc = "获取一条岗位数据")
  public Map<String, Object> getById(@RequestParam("id") Long id){
      return  positionService.getById(id);
  }

  @RequestMapping(value = "/save")
  @SysLogs(desc = "保存岗位")
  public Map<String, Object> save(HttpServletRequest request){
      return positionService.save(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/update")
  @SysLogs(desc = "修改岗位")
  public Map<String, Object> update(HttpServletRequest request){
      return positionService.update(RequestMap.requestToMap(request));
  }

  @RequestMapping(value = "/delete")
  @SysLogs(desc = "删除岗位")
  public Map<String, Object> delete(@RequestParam("id") Long id){
      return positionService.delete(id);
  }
  
}
