package cn.forest.common.util;

public class StringUtil {

  public static boolean isBlank(String str) {
    int strLen;
    if (str == null || (strLen = str.length()) == 0 || "null".equals(str) || "[]".equals(str)) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static String toString(Object obj) {
    if (obj != null && !isBlank(obj.toString())) {
      return obj.toString();
    }
    return null;
  }

  public static boolean isBlank(Object obj) {
    return isBlank(toString(obj));
  }

  public static boolean isNotBlank(Object obj) {
    return !isBlank(obj);
  }

  public static boolean isNotBlank(String str) {
    return !isBlank(str);
  }
}
