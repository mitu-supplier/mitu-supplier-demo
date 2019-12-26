package cn.forest.common.entity;

import java.util.List;

public class JsonNode {

  public JsonNode() {

  }

  public JsonNode(Long id, Long parentId, String name,int priority) {
    this.id = id;
    this.parentId = parentId;
    this.name = name;
    this.priority=priority;
  }

  private Long id;

  private Long parentId;

  private String name;
  
  private int priority;

  private List<JsonNode> children;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<JsonNode> getChildren() {
    return children;
  }

  public void setChildren(List<JsonNode> children) {
    this.children = children;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

}
