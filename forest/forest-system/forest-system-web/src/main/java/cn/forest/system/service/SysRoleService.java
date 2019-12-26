package cn.forest.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.forest.common.entity.JsonNode;
import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.ResultMessage;
import cn.forest.system.remote.SysPermissionsRemote;
import cn.forest.system.remote.SysRoleRemote;

@Service("sysRoleService")
public class SysRoleService {
  @Autowired
  private SysRoleRemote sysRoleRemote;

  @Autowired
  private SysPermissionsRemote sysPermissionsRemote;

  public Map<String, Object> getList(Long page, Long pageSize) {
    Object sysRole = sysRoleRemote.getSysRoleList(page, pageSize);
    if (sysRole != null) {
      return ResultMessage.success(sysRole);
    }
    return null;
  }

  public Map<String, Object> getPermissions() {
    List sysPermissionsList = (List) sysPermissionsRemote.getSysPermissionsList();
    List<JsonNode> jsonNodes = new ArrayList<JsonNode>();
    for (Object obj : sysPermissionsList) {
      Map map = (Map) obj;
      if ("1".equals(map.get("treeDepth").toString())) {
        JsonNode jsonNode = new JsonNode(Long.parseLong(map.get("id").toString()),
            Long.parseLong(map.get("parentId").toString()), map.get("name").toString(),Integer.parseInt(map.get("priority").toString()));
        jsonNode.setChildren(jsonNode(sysPermissionsList, jsonNode));
        jsonNodes.add(jsonNode);
      }
    }
    jsonNodes.sort((e,j)-> e.getPriority()-j.getPriority());
    return ResultMessage.success(jsonNodes) ;
  }

  public List<JsonNode> jsonNode(List sysPermissionsList, JsonNode oldobj) {
    List<JsonNode> JsonNodeList = new ArrayList<JsonNode>();
    for (Object obj : sysPermissionsList) {
      Map map = (Map) obj;
      if (map.get("parentId").toString().equals(oldobj.getId()+"")) {
        JsonNode jsonNode = new JsonNode(Long.parseLong(map.get("id").toString()),
            Long.parseLong(map.get("parentId").toString()), map.get("name").toString(),Integer.parseInt(map.get("priority").toString()));
        JsonNodeList.add(jsonNode);
      }
    }
    for (JsonNode jsonNode : JsonNodeList) {
      List<JsonNode> jsonNodeChildren = jsonNode(sysPermissionsList, jsonNode);
      jsonNodeChildren.sort((e,j)-> e.getPriority()-j.getPriority());
      jsonNode.setChildren(jsonNodeChildren);
    }
    return JsonNodeList;
  }
  
  public Map<String, Object> add(Map<String, ?> map) {
    int add = sysRoleRemote.add(map);
    if (add > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }
  
  public Map<String, Object> getById(Long id) {
    Object obj = sysRoleRemote.getById(id);
    if (obj != null) {
      return ResultMessage.success(obj);
    }
    return null;
  }
  
  public Map<String, Object> delete(Long id) {
    int delete = sysRoleRemote.delete(id);
    if (delete > 0) {
      return ResultMessage.result("删除成功");
    }
    return ResultMessage.error("删除失败");
  }
  
  public Map<String, Object> update(Map<String, ?> map) {
    int add = sysRoleRemote.update(map);
    if (add > 0) {
      return ResultMessage.result("修改成功");
    }
    return ResultMessage.error("修改失败");
  }

  public Map<String, Object> getSysPermissionsList(Long id) {
    Object sysPermissionsList = sysPermissionsRemote.getSysPermissionsList();
    if (sysPermissionsList != null) {
      return ResultMessage.success(sysPermissionsList);
    }
    return null;
  }
  
  public Map<String, Object> saveRolePermiss(Map<String, ?> map) {
    int sysRole = sysRoleRemote.saveRolePermiss(map);
    if (sysRole > 0) {
      return ResultMessage.result("添加成功");
    }
    return ResultMessage.error("添加失败");
  }


  public Map<String, Object> getRoleByUserId(Long userId){
    Object role = sysRoleRemote.getRoleByUserId(userId);
    if (role != null) {
      return ResultMessage.success(role);
    }
    return null;
  }
}
