package cn.forest.file.controller;

import cn.forest.common.util.ResultMessage;
import cn.forest.file.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file-upload-path}")
    private String fileUploadPath;

    @Value("${image-upload-path}")
    private String imageUploadPath;

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload")
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();

        String filename = FilenameUtils.getName(multipartFile.getOriginalFilename());
        String uploadPath = null;
        //根据图片内容判断是否为图片文件
        InputStream inputStream = multipartFile.getInputStream();
        BufferedImage bi = ImageIO.read(inputStream);
        if (bi == null) {
            //不是图片
            uploadPath = fileUploadPath;
        } else {
            uploadPath = imageUploadPath;
        }
        String ctxFileUrl = fileService.upload(multipartFile, uploadPath, false,multipartFile.getInputStream());
        if (StringUtils.isNotEmpty(ctxFileUrl)) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("fileName", filename);
            data.put("path", ctxFileUrl);
            data.put("fileSize", multipartFile.getSize());
            return ResultMessage.success(data);
        } else {
            return ResultMessage.error("上传失败！");
        }
    }

    /**
     * 文件下载
     *
     * @param response
     * @param path     路径
     * @param fileName 文件名
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, String path, String fileName) {
        fileService.downloadFile(response, path, fileName);
    }
    
    public static void main(String[] args) throws FileNotFoundException {
      File file=new File("D:\\banner2.png");
      OSS ossClient = new OSSClientBuilder().build("http://oss-cn-beijing.aliyuncs.com", "LTAI4Ffnknp9rjCrLiaukMp1", "pd3GaR0uJGEDVqXR6K9cYT4DPcoaSX");
     //ObjectMetadata meta = new ObjectMetadata();       // 创建上传Object的Metadata
      //meta.setContentType(contentType("png"));   // 设置上传内容类型
      //meta.setCacheControl("no-cache");
      PutObjectResult putObject = ossClient.putObject("jft-merchant","images/888.png",new FileInputStream(file));
    /*
     * OSS ossClient = new
     * OSSClientBuilder().build("http://oss-cn-beijing.aliyuncs.com",
     * "LTAI4Fr6Q7QG68qFQAwwxT97", "7UibbkIul6ZbYj9dADlsIhiAyWIMT2");
     * PutObjectResult putObject =
     * ossClient.putObject("forest-lwl","images/222.png",file);
     * System.out.println(putObject.getETag());
     */
      ossClient.shutdown();
      
      
      
      
      
    }
    /**
     * 
     * @MethodName: contentType
     * @Description: 获取文件类型
     * @param FileType
     * @return String
     */
    private static String contentType(String fileType){
      fileType = fileType.toLowerCase();
      String contentType = "";
      switch (fileType) {
      case "bmp": contentType = "image/bmp";
            break;
      case "gif": contentType = "image/gif";
            break;
      case "png": contentType = "image/jpeg"; 
      break;
      case "jpeg": contentType = "image/jpeg"; 
           break;
      case "jpg": contentType = "image/jpeg";
            break;
      case "html":contentType = "text/html";
            break;
      case "txt": contentType = "text/plain";
            break;
      case "vsd": contentType = "application/vnd.visio";
            break;
      case "ppt": 
      case "pptx":contentType = "application/vnd.ms-powerpoint";
            break;
      case "doc": 
      case "docx":contentType = "application/msword";
            break;
      case "xml":contentType = "text/xml";
            break;
      case "mp4":contentType = "video/mp4";
            break;
      default: contentType = "application/octet-stream";
            break;
      }
      return contentType;
       }  
}
