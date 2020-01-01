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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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
        String ctxFileUrl = fileService.upload(multipartFile, uploadPath, false);
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
}
