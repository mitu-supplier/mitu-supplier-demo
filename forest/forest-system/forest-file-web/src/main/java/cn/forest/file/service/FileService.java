package cn.forest.file.service;

import cn.forest.common.warpper.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.util.List;

public interface FileService {

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param uploadPath    上传路径
     * @param relativePath  是否返回相对路径
     * @return
     */
    String upload(MultipartFile multipartFile, String uploadPath, boolean relativePath,InputStream inputStream);

    /**
     * 文件下载
     *
     * @param response
     * @param path  路径
     * @param fileName  文件名
     */
    void downloadFile(HttpServletResponse response, String path, String fileName);

    /**
     * 文件浏览
     *
     * @param path
     *            浏览路径
     * @param fileType
     *            文件类型
     * @param orderType
     *            排序类型
     * @return 文件信息
     */
    List<FileInfo> browser(String path, FileInfo.FileType fileType, FileInfo.OrderType orderType);
}
