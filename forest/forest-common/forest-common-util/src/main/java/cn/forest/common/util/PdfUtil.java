package cn.forest.common.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfUtil {
  

  /**
   * 创建字体大小
   * @param fontSize
   * @return 字体
   * @throws DocumentException
   * @throws IOException
   */
  public static Font createFont(int fontSize) throws DocumentException, IOException{
    BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
    Font font = new Font(bfChinese,fontSize,Font.NORMAL);
    return font;
  }
  /**
   * 创建字体大小-加粗
   * @param fontSize
   * @return 字体
   * @throws DocumentException
   * @throws IOException
   */
  public static Font createBoldFont(int fontSize) throws DocumentException, IOException{
    BaseFont bfChinese = BaseFont.createFont( "STSongStd-Light" ,"UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
    Font font = new Font(bfChinese,fontSize,Font.BOLD);
    return font;
  }
  /**
   * 设置横向
   * @return
   */
  public static Rectangle rotate(){
    Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        return pageSize;
  }
  
  /**
   * 
   * @param oldFile pdf 文件地址
   * @param newFile 新的pdf文件地址
   * @param syFile  水印图片地址
   * @param tpFile  二维码图片地址
   * @throws IOException
   * @throws DocumentException
   * @throws FileNotFoundException
   * @throws BadElementException
   * @throws MalformedURLException
   * @throws WriterException
   */
  public static void createPdfStamper(String oldFile,String newFile,String syFile) throws IOException,
      DocumentException, FileNotFoundException, BadElementException,
      MalformedURLException, WriterException {
    PdfReader reader = new PdfReader(oldFile);
    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(newFile));
    int numberOfPages = reader.getNumberOfPages();
    for (int i = 1; i <= numberOfPages; i++) {
      PdfContentByte under = stamp.getUnderContent(i);
        Image img = Image.getInstance(syFile);
        img.setAlignment(Image.RIGHT | Image.TEXTWRAP); // 图片居右
        img.setBorder(Image.BOX);
        img.setBorderWidth(10);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1000, 72);// 大小
        img.setRotationDegrees(0);// 旋转
        img.setAbsolutePosition(500, 770); // 图片的位置
        under.addImage(img);
    }
    stamp.close();
    reader.close();
  }
  
  public static void createPdfsyStamper(String oldFile,String newFile,String syFile) throws IOException,
      DocumentException, FileNotFoundException, BadElementException,MalformedURLException, WriterException {
    PdfReader reader = new PdfReader(oldFile);
    PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(newFile));
    Image img = Image.getInstance(syFile);
    img.setAbsolutePosition(0, 0);
    int numberOfPages = reader.getNumberOfPages();
    for (int i = 1; i <= numberOfPages; i++) {
      PdfContentByte under = stamp.getUnderContent(i);
      under.addImage(img);
    }
    stamp.close();
    reader.close();
  }
  
  public static File createFile(String path){
    File file = new File(path);
    if (!file.exists()){
            file.mkdirs();
        }
    return file;
  }
  
  /**
   * 
   * @param pdfPTable  table
   * @param cell  cell
   * @param colspanOrRowspan col or row  横向合并还是纵向合并
   * @param cellNum 合并的数量
   * @param content  内容
   * @param font  字体
   * @param center  居中 
   * @param baseColor  颜色
   * @param borderTop  上边框
   * @param borderBottom  下边框
   * @param borderLeft  左边框
   * @param borderRight  右边框
   */
  public static PdfPCell createTableCellNum(PdfPTable pdfPTable,PdfPCell cell,String colspanOrRowspan,int cellNum,String content,Font font,String center,BaseColor baseColor,boolean borderTop,boolean borderBottom,boolean borderLeft,boolean borderRight,float leading1,float leading2 ){
    cell = new PdfPCell(new Phrase(content,font));
    cell.setMinimumHeight(25);
    cell.setBackgroundColor(baseColor);
    if(colspanOrRowspan!=null){
      if("col".equals(colspanOrRowspan)){
        cell.setColspan(cellNum);
      }else if("row".equals(colspanOrRowspan)){
        cell.setRowspan(cellNum);
      }
    }
    cell.setUseAscender(true);
    if("right".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //右
    }
    if("center".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    }
    if("left".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_LEFT); //左
    }
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    if(borderBottom){
      cell.setBorderWidthBottom(0);
    }
    if(borderTop){
      cell.setBorderWidthTop(0);
    }
    if(borderLeft){
      cell.setBorderWidthLeft(0);
    }
    if(borderRight){
      cell.setBorderWidthRight(0);
    }
    cell.setLeading(leading1, leading2);
    return cell;
    
  }
  
  /**
   * 
   * @param pdfPTable  table
   * @param cell  cell
   * @param colspanOrRowspan col or row  横向合并还是纵向合并
   * @param cellNum 合并的数量
   * @param content  内容
   * @param font  字体
   * @param center  居中 
   * @param baseColor  颜色
   * @param borderTop  上边框
   * @param borderBottom  下边框
   * @param borderLeft  左边框
   * @param borderRight  右边框
   */
  public static void createTableCellNum(PdfPTable pdfPTable,PdfPCell cell,String colspanOrRowspan,int cellNum,String content,Font font,String center,BaseColor baseColor,boolean borderTop,boolean borderBottom,boolean borderLeft,boolean borderRight){
    cell = new PdfPCell(new Phrase(content,font));
    cell.setMinimumHeight(25);
    cell.setBackgroundColor(baseColor);
    if(colspanOrRowspan!=null){
      if("col".equals(colspanOrRowspan)){
        cell.setColspan(cellNum);
      }else if("row".equals(colspanOrRowspan)){
        cell.setRowspan(cellNum);
      }
    }
    cell.setUseAscender(true);
    if("right".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT); //右
    }
    if("center".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    }
    if("left".equals(center)){
      cell.setHorizontalAlignment(Element.ALIGN_LEFT); //左
    }
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    if(borderBottom){
      cell.setBorderWidthBottom(0);
    }
    if(borderTop){
      cell.setBorderWidthTop(0);
    }
    if(borderLeft){
      cell.setBorderWidthLeft(0);
    }
    if(borderRight){
      cell.setBorderWidthRight(0);
    }
    pdfPTable.addCell(cell);
  }
  
  /**
   * 
   * @param pdfPTable  table
   * @param cell  cell
   * @param colspanOrRowspan col or row  横向合并还是纵向合并
   * @param cellNum 合并的数量
   * @param content  内容
   * @param font  字体
   * @param center  居中 
   * @param baseColor  颜色
   * @param borderTop  上边框
   * @param borderBottom  下边框
   * @param borderLeft  左边框
   * @param borderRight  右边框
   */
  public static void createTableCellNum(PdfPTable pdfPTable,PdfPCell cell,String colspanOrRowspan,int cellNum,String content,Font font,boolean center,BaseColor baseColor,boolean borderTop,boolean borderBottom,boolean borderLeft,boolean borderRight){
    cell = new PdfPCell(new Phrase(content,font));
    cell.setMinimumHeight(25);
    cell.setBackgroundColor(baseColor);
    if(colspanOrRowspan!=null){
      if("col".equals(colspanOrRowspan)){
        cell.setColspan(cellNum);
      }else if("row".equals(colspanOrRowspan)){
        cell.setRowspan(cellNum);
      }
    }
    cell.setUseAscender(true);
    if(center){
      cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    }
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    if(borderBottom){
      cell.setBorderWidthBottom(0);
    }
    if(borderTop){
      cell.setBorderWidthTop(0);
    }
    if(borderLeft){
      cell.setBorderWidthLeft(0);
    }
    if(borderRight){
      cell.setBorderWidthRight(0);
    }
    pdfPTable.addCell(cell);
  }
  
  /**
   * 
   * @param pdfPTable  table
   * @param cell  cell
   * @param height 单元格高度
   * @param colspanOrRowspan col or row  横向合并还是纵向合并
   * @param cellNum 合并的数量
   * @param content  内容
   * @param font  字体
   * @param center  居中 
   * @param baseColor  颜色
   * @param borderTop  上边框
   * @param borderBottom  下边框
   * @param borderLeft  左边框
   * @param borderRight  右边框
   */
  public static void createTableCellNum(PdfPTable pdfPTable,PdfPCell cell,int height,String colspanOrRowspan,int cellNum,String content,Font font,boolean center,BaseColor baseColor,boolean borderTop,boolean borderBottom,boolean borderLeft,boolean borderRight){
    cell = new PdfPCell(new Phrase(content,font));
    cell.setMinimumHeight(height);
    cell.setBackgroundColor(baseColor);
    if(colspanOrRowspan!=null){
      if("col".equals(colspanOrRowspan)){
        cell.setColspan(cellNum);
      }else if("row".equals(colspanOrRowspan)){
        cell.setRowspan(cellNum);
      }
    }
    cell.setUseAscender(true);
    if(center){
      cell.setHorizontalAlignment(Element.ALIGN_CENTER); //水平居中
    }
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE); //垂直居中
    if(borderBottom){
      cell.setBorderWidthBottom(0);
    }
    if(borderTop){
      cell.setBorderWidthTop(0);
    }
    if(borderLeft){
      cell.setBorderWidthLeft(0);
    }
    if(borderRight){
      cell.setBorderWidthRight(0);
    }
    pdfPTable.addCell(cell);
  }
  
  
  public static void createImg(String content,String path) throws WriterException, IOException{
    int width = 300;
        int height = 300;
        String format = "png";
        //定义二维码的参数
        HashMap mapFile = new HashMap();
        //设置编码
        mapFile.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置纠错等级
        mapFile.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        mapFile.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,mapFile);
        Path file = new File(path).toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, file);
  }
  
  public static void delFile(String ... path) {
    for(int i = 0 ; i < path.length ; i++) {
      File file=new File(path[i]);
          if(file.exists()&&file.isFile())
              file.delete();
    }
  }
  
  public static void main(String[] args) throws DocumentException, IOException {
    
    PdfUtil.createFile("D:\\");
    String oldFile="D:\\"+File.separator+"old_123_PDF.pdf";
    Document document = new Document();
    
    Font font12 = PdfUtil.createFont(12);//字体大小
    Font font8 = PdfUtil.createFont(8);//字体大小
    Font font9 = PdfUtil.createFont(9);//字体大小
    
    PdfWriter instance = PdfWriter.getInstance(document, new FileOutputStream(oldFile));
    document.open();
    
    PdfContentByte canvasTitle = instance.getDirectContent();
    Phrase phraseTitle = new Phrase("货物与服务招标采购申请表",font12);
    ColumnText.showTextAligned(canvasTitle, Element.ALIGN_CENTER, phraseTitle, 300, 800, 0);
    
    document.add(new Paragraph("\n\n\n"));//添加一个空白行 换行
    PdfPTable table = new PdfPTable(6);
    table.setWidthPercentage(100);
    PdfPCell cell = null;
    
    PdfUtil.createTableCellNum(table,cell,null,0,"采购名称",font9,true,BaseColor.WHITE,false,false,false,false);
    PdfUtil.createTableCellNum(table,cell,"col",2,"北京航空航天大学",font9,false,BaseColor.WHITE,false,false,false,false);
    PdfUtil.createTableCellNum(table,cell,null,0,"采购类型",font9,true,BaseColor.WHITE,false,false,false,false);
    PdfUtil.createTableCellNum(table,cell,"col",2,"五彩沟",font9,false,BaseColor.WHITE,false,false,false,false);
    
    table.setHorizontalAlignment(Element.ALIGN_CENTER); 
    document.add(table);  
    document.add(new Paragraph("\n\n"));  
    document.close();
    
    
    
    
  }
  

}
