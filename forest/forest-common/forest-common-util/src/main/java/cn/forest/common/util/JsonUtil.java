package cn.forest.common.util;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 工具类 - Json
 *
 */
public class JsonUtil {
  /** ObjectMapper */
  private static ObjectMapper mapper = new ObjectMapper();

  /**
   * 不可实例化
   */
  private JsonUtil() {
  }

  /**
   * 将对象转换为JSON字符串
   * 
   * @param value 对象
   * @return JSOn字符串
   */
  public static String toJson(Object value) {
    try {
      return mapper.writeValueAsString(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * json字符串转 JsonNode
   * 
   * @param json
   * @return
   */
  public static JsonNode readTree(String json) {
    try {
      return mapper.readTree(json);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将JSON字符串转换为对象
   * 
   * @param json      JSON字符串
   * @param valueType 对象类型
   * @return 对象
   */
  @SuppressWarnings("deprecation")
  public static <T> T toObject(String json, Class<T> valueType) {
    try {
      return mapper.readValue(json, valueType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将JSON字符串转换为对象
   * 
   * @param json          JSON字符串
   * @param typeReference 对象类型
   * @return 对象
   */
  @SuppressWarnings("deprecation")
  public static <T> T toObject(String json, TypeReference<?> typeReference) {
    try {
      return mapper.readValue(json, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将JSON字符串转换为对象
   * 
   * @param json     JSON字符串
   * @param javaType 对象类型
   * @return 对象
   */
  @SuppressWarnings("deprecation")
  public static <T> T toObject(String json, JavaType javaType) {
    try {
      return mapper.readValue(json, javaType);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 将对象转换为JSON流
   * 
   * @param writer writer
   * @param value  对象
   */
  public static void writeValue(Writer writer, Object value) {
    try {
      mapper.writeValue(writer, value);
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
