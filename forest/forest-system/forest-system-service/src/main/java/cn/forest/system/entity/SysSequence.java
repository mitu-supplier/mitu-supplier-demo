package cn.forest.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 序列
 */
@TableName(value = "sys_sequence")
public class SysSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    private Long id;

    /**
     * sequence名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 最大id
     */
    @TableField(value = "max")
    private Long max;

    /**
     * 生成序列后的长度,以0补全
     */
    @TableField(value = "length")
    private Integer length;

    /**
     * 增长的长度
     */
    @TableField(value = "next")
    private Integer next;

    /**
     * 规则以###max_id###做为替换
     */
    @TableField(value = "rules")
    private String rules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }
}
