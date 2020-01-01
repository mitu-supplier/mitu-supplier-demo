package cn.forest.file.service.Impl;

import cn.forest.file.service.FileService;
import cn.forest.file.util.FreemarkerUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {

    @Value("${file-site-path}")
    private String fileSitePath;

    @Value("${upload-base-path}")
    private String uploadBasePath;

    @Override
    public String upload(MultipartFile multipartFile, String uploadPath, boolean relativePath) {
        if (multipartFile == null) {
            return null;
        }
        try {
            String path = FreemarkerUtils.process(uploadPath, null) + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String destPath = uploadBasePath + path;
            File destFile = new File(destPath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            FileUtils.writeByteArrayToFile(destFile, multipartFile.getBytes());
            if (relativePath) {
                return path;
            }
            String urlPath = fileSitePath + path;
            return urlPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void downloadFile(HttpServletResponse response, String path, String fileName) {
        if (StringUtils.isNotEmpty(path)) {
            String realPath = path.replace(fileSitePath, uploadBasePath);
            // 以流的形式下载文件。
            File realFile = new File(realPath);
            // 清空response
            response.reset();
            InputStream in = null;
            OutputStream toClient = null;
            try {
                if (StringUtils.isEmpty(fileName)) {
                    fileName = realPath.substring(realPath.lastIndexOf("/") + 1);
                }
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
                // 以流的形式下载文件
                in = new BufferedInputStream(new FileInputStream(realFile));
                byte[] buffer = new byte[in.available()];
                in.read(buffer);
                toClient = new BufferedOutputStream(response.getOutputStream());
                toClient.write(buffer);
                toClient.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    toClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
