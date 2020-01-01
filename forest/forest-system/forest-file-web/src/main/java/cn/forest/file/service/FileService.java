package cn.forest.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService {

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadPath    上传路径
     * @param relativePath  是否返回相对路径
     * @return
     */
    String upload(MultipartFile multipartFile, String uploadPath, boolean relativePath);

    /**
     * 文件下载
     *
     * @param response
     * @param path  路径
     * @param fileName  文件名
     */
    void downloadFile(HttpServletResponse response, String path, String fileName);
}
