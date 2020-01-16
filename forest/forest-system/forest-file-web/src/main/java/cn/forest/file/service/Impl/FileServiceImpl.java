package cn.forest.file.service.Impl;

import cn.forest.common.warpper.FileInfo;
import cn.forest.file.service.FileService;
import cn.forest.file.util.FreemarkerUtils;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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


    @Override
    public List<FileInfo> browser(String path, FileInfo.FileType fileType, FileInfo.OrderType orderType) {
        if (path != null) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (!path.endsWith("/")) {
                path += "/";
            }
        } else {
            path = "/";
        }

        String browsePath = null;
        try {
            browsePath = FreemarkerUtils.process(path, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        List<FileInfo> fileInfos = new ArrayList<>();
        if (browsePath.indexOf("..") >= 0) {
            return fileInfos;
        }
        fileInfos = browser(browsePath);
        if (orderType == FileInfo.OrderType.size) {
            Collections.sort(fileInfos, new SizeComparator());
        } else if (orderType == FileInfo.OrderType.type) {
            Collections.sort(fileInfos, new TypeComparator());
        } else {
            Collections.sort(fileInfos, new NameComparator());
        }
        return fileInfos;
    }

    public List<FileInfo> browser(String path) {
        List<FileInfo> fileInfos = new ArrayList<>();
        File directory = new File(uploadBasePath + path);
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(file.getName());
                fileInfo.setUrl(fileSitePath + path + file.getName());
                fileInfo.setIsDirectory(file.isDirectory());
                fileInfo.setSize(file.length());
                fileInfo.setLastModified(new Date(file.lastModified()));
                fileInfos.add(fileInfo);
            }
        }
        return fileInfos;
    }

    private class NameComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
        }
    }

    private class SizeComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
        }
    }

    private class TypeComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(FilenameUtils.getExtension(fileInfos1.getName()), FilenameUtils.getExtension(fileInfos2.getName())).toComparison();
        }
    }
}
