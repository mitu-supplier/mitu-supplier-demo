package cn.forest.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.ptg.MemAreaPtg;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExcelUtils {
  private static final String XLSX = ".xlsx";
  private static final String XLS = ".xls";
  private static final short IMG_HEIGTH = 30; // 导出图片高度
  private static final short IMG_WIDTH = 30; // 导出图片宽度
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 时间格式类型
  // 导出指定位置（如：F:\\data\\excel\\，不写即本工程目录下）
  private static final String PATH = "";

  /**
   * 读取文件数据
   *
   * @param file .xlsx文件或者.xls文件
   * @param fieldsMap  key excel对应的列名, value 每列对应的字段名称
   * @return 文件数据
   */
  public static JSONArray getExcelData(String fileName,InputStream inputStream, Map<String, String> fieldsMap,int lastCellNum) {
      JSONArray array = null;
      Workbook book = null;
      try {
          if (fileName.endsWith(XLSX)) {
              book = new XSSFWorkbook(inputStream);
          } else if (fileName.endsWith(XLS)) {
              POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
              book = new HSSFWorkbook(poifsFileSystem);
          } else {
              return array;
          }
          array = read(book, fieldsMap,lastCellNum);
      } catch (Exception e) {
          e.printStackTrace();
          if(book != null){
              try {
                  book.close();
              }catch (IOException ie){
                  ie.printStackTrace();
              }
          }
      }
      if(book != null){
          try {
              book.close();
          }catch (IOException e){
              e.printStackTrace();
          }
      }
      return array;
  }

  /**
   * 将文件的数据解析为JSON
   */
  private static JSONArray read(Workbook book, Map<String, String> fieldsMap,int lastCellNum) {
      Sheet sheet = book.getSheetAt(0);
      int rowStart = sheet.getFirstRowNum(); // 首行下标
      int rowEnd = sheet.getLastRowNum(); // 尾行下标
      // 获取第一行JSON对象键
      Row firstRow = sheet.getRow(rowStart);
      int cellStart = firstRow.getFirstCellNum();
      int cellEnd = lastCellNum;//firstRow.getLastCellNum();
      Map<Integer, String> keyMap = new HashMap<Integer, String>();
      for (int j = cellStart; j < cellEnd; j++) {
          // 表头遇到空格停止解析
          String val = getValue(firstRow.getCell(j));
          if (val == null || val.trim().length() == 0) {
              cellEnd = j;
              break;
          }
          if(fieldsMap.get(val)!=null) {
            keyMap.put(j, fieldsMap.get(val));
          }
      }
      if (keyMap.isEmpty()) {
          return (JSONArray) Collections.emptyList();
      }
      // 获取每行JSON对象的值
      JSONArray array = new JSONArray();
      // 如果首行与尾行相同，表明只有一行，返回表头数据
      if (rowStart == rowEnd) {
          JSONObject object = new JSONObject();
          for (int i : keyMap.keySet()) {
              object.put(keyMap.get(i), "");
          }
          array.add(object);
          return array;
      }
      for (int i = rowStart + 1; i <= rowEnd; i++) {
          Row eachRow = sheet.getRow(i);
          JSONObject obj = new JSONObject();
          StringBuffer sb = new StringBuffer();
          for (int k = cellStart; k < cellEnd; k++) {
              if (eachRow != null) {
                  String val = getValue(eachRow.getCell(k));
                  sb.append(val); // 所有数据添加到里面，用于判断该行是否为空
                  obj.put(keyMap.get(k), val);
              }
          }
          if (sb.toString().length() > 0) {
              array.add(obj);
          }
      }
      return array;
  }

  /**
   * 获取表格单元格数据
   */
  private static String getValue(Cell cell) {
      // 空白或空
      if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
          return "";
      }
      // 0. 数字 类型
      if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
          if (HSSFDateUtil.isCellDateFormatted(cell)) {
              Date date = cell.getDateCellValue();
              DateFormat df = new SimpleDateFormat(DATE_FORMAT);
              return df.format(date);
          }
          cell.setCellType(CellType.STRING);
          String val = cell.getStringCellValue() + "";
          val = val.toUpperCase();
          if (val.contains("E")) {
              val = val.split("E")[0].replace(".", "");
          }
          return val;
      }
      // 1. String类型
      if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
          String val = cell.getStringCellValue();
          if (val == null || val.trim().length() == 0) {
              return "";
          }
          return val.trim();
      }
      // 2. 公式 CELL_TYPE_FORMULA
      if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
          return cell.getCellFormula();
      }
      // 4. 布尔值 CELL_TYPE_BOOLEAN
      if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
          return cell.getBooleanCellValue() + "";
      }
      // 5. 错误 CELL_TYPE_ERROR
      return "";
  }

  /**
   * excel导出
   *
   * @param title   表名称
   * @param rowList 导出每行数据
   */
  public static void export(String title, List<List<Object>> rowList,String[] titles,HttpServletResponse response) {
      if (rowList == null) {
          rowList = Collections.emptyList();
      }
      SXSSFWorkbook book = new SXSSFWorkbook();
      Sheet sheet = book.createSheet(title);
      Drawing patriarch = sheet.createDrawingPatriarch();
      CellStyle style = book.createCellStyle();
      // 数据居左
      style.setAlignment(HorizontalAlignment.LEFT);
      
      CellStyle cellStyle = book.createCellStyle();
      cellStyle.setAlignment(HorizontalAlignment.CENTER);  // 设置单元格水平方向对其方式
      cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 设置单元格垂直方向对其方式
      
      Row sr1 = sheet.createRow(0);
      for (int j = 0; j < titles.length; j++) {
        sheet.setColumnWidth(j, 3766); 
        setExcelValue(sr1.createCell(j), titles[j], cellStyle);
      }
 
      
      // 写数据
      for (int i = 0; i < rowList.size(); i++) {
          List<Object> row = rowList.get(i);
          Row sr = sheet.createRow(i+1);
          for (int j = 0; j < row.size(); j++) {
              if (row.get(j) != null && row.get(j) instanceof URL) {
                  URL url = (URL) row.get(j);
                  sr.setHeight((short) (IMG_WIDTH * IMG_HEIGTH));
                  setExcelImg(book, patriarch, i, j, url);
              } else {
                  setExcelValue(sr.createCell(j), row.get(j), style);
              }
          }
      }
      try {
          OutputStream output=response.getOutputStream();
          response.reset();
          response.setHeader("Content-disposition", "attachment; filename="+new String(title.getBytes("UTF-8"), "iso8859-1")+".xlsx");
          response.setContentType("application/msexcel");
          book.write(output);
          output.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  /**
   * 导出写图片
   */
  private static void setExcelImg(SXSSFWorkbook wb, Drawing patriarch, int rowIndex, int cloumIndex, URL url) {
      // （jdk1.7版本try中定义流可自动关闭）
      try (InputStream is = url.openStream();
           ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
          byte[] buff = new byte[1024];
          int rc = 0;
          while ((rc = is.read(buff, 0, 1024)) > 0) {
              outputStream.write(buff, 0, rc);
          }
          // 设置图片位置
          XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, cloumIndex, rowIndex, cloumIndex + 1, rowIndex + 1);
          anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
          patriarch.createPicture(anchor, wb.addPicture(outputStream.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
          outputStream.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  /**
   * 导出写数据
   */
  private static void setExcelValue(Cell cell, Object value, CellStyle style) {
      // 写数据
      if (value == null) {
          cell.setCellValue("");
      } else {
          if (value instanceof Integer || value instanceof Long) {
              cell.setCellType(CellType.NUMERIC);
              cell.setCellValue(Long.valueOf(value.toString()));
          } else if (value instanceof BigDecimal) {
              cell.setCellType(CellType.NUMERIC);
              cell.setCellValue(((BigDecimal) value).setScale(3, RoundingMode.HALF_UP).doubleValue());
          } else {
              cell.setCellValue(value.toString());
          }
          cell.setCellStyle(style);
      }
  }
}
