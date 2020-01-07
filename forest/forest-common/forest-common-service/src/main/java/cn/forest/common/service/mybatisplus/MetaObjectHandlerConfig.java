package cn.forest.common.service.mybatisplus;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import cn.forest.common.util.DateUtil;

@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    Object createTime = getFieldValByName("createTime", metaObject);
    Object createdAt = getFieldValByName("createdAt", metaObject);
    if (createTime == null) {
      setFieldValByName("createTime",
          DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS), metaObject);// mybatis-plus版本2.0.9+
    }
    if(createdAt==null) {
      setFieldValByName("createdAt",
          DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS), metaObject);// mybatis-plus版本2.0.9+
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    Object updateTime = getFieldValByName("updateTime", metaObject);
    Object updatedAt = getFieldValByName("updatedAt", metaObject);
    if (updateTime == null) {
      setFieldValByName("updateTime",
          DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS), metaObject);// mybatis-plus版本2.0.9+
    }
    if (updatedAt == null) {
      setFieldValByName("updatedAt",
          DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS), metaObject);// mybatis-plus版本2.0.9+
    }
  }

}
