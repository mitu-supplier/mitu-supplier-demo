package cn.forest.lyj.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.util.ResultMessage;
import cn.forest.lyj.remote.PositionRemote;

@Service("positionService")
public class PositionService {

  @Autowired
  private PositionRemote positionRemote;

  public Map<String, Object> list(Long page, Long pageSize) {
    Object list = positionRemote.list(page, pageSize);
    if (list != null) {
      return ResultMessage.success(list);
    }
    return null;
  }

  public Map<String, Object> getById(Long id) {
    Object obj = positionRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }

  public Map<String, Object> save(Map<String, ?> map) {
    int save = positionRemote.save(map);
    return ResultMessage.result(save, "添加成功", "添加失败");
  }

  public Map<String, Object> update(Map<String, ?> map) {
    int update = positionRemote.update(map);
    return ResultMessage.result(update, "修改成功", "修改失败");
  }

  public Map<String, Object> delete(Long id) {
    int delete = positionRemote.delete(id);
    return ResultMessage.result(delete, "删除成功", "删除失败");
  }

}
