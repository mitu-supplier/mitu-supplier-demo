package cn.forest.ueditor;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.forest.common.util.JsonUtil;
import cn.forest.common.util.ResultMessage;
import cn.forest.common.warpper.FileInfo;
import cn.forest.file.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * ueditor服务器端接口
 */
@RestController
@RequestMapping("/ueditor")
public class UeditorController {

    private static final String UPLOAD_PATH_PREFIX = "/ueditor";
    // 状态
    private static final String STATE = "state";
    // URL
    private static final String URL = "url";
    // TITLE
    private static final String TITLE = "title";
    // 文件原名
    private static final String ORIGINAL = "original";

    @Value("${file-upload-path}")
    private String fileUploadPath;

    @Value("${image-upload-path}")
    private String imageUploadPath;

    @Autowired
    private FileService fileService;

    /**
     * 上传
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload")
    public Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFileMap().entrySet().iterator().next().getValue();

        String filename = FilenameUtils.getName(multipartFile.getOriginalFilename());
        String uploadPath = null;
        //根据图片内容判断是否为图片文件
        InputStream inputStream = multipartFile.getInputStream();
        BufferedImage bi = ImageIO.read(inputStream);
        if (bi == null) {
            //不是图片
            uploadPath = UPLOAD_PATH_PREFIX + fileUploadPath;
        } else {
            uploadPath = UPLOAD_PATH_PREFIX + imageUploadPath;
        }
        String ctxFileUrl = fileService.upload(multipartFile, uploadPath, false);
        Map<String, String> map = new HashMap<>();
        map.put(STATE, "SUCCESS");
        map.put(URL, ctxFileUrl);
        map.put(TITLE, filename);
        map.put(ORIGINAL, filename);
        return map;
    }

    @RequestMapping(value = "/listimage")
    public String listimage(int start, int size, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<FileInfo> fileInfos = fileService.browser(UPLOAD_PATH_PREFIX + imageUploadPath, FileInfo.FileType.image, null);
        List<Map<String, String>> urls = new ArrayList<>();
        fileInfos.forEach(e -> {
            Map<String, String> urlsMap = new HashMap<>();
            urlsMap.put(URL, e.getUrl());
            urls.add(urlsMap);
        });
        Map<String, Object> map = new HashMap<>();
        map.put(STATE, "SUCCESS");
        map.put("list", urls);
        map.put("start", start);
        map.put("total", fileInfos.size());

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");

        String callbackName = request.getParameter("callback");
        if (callbackName != null) {
            String res = callbackName + "(" + toUnicode(JsonUtil.toJson(map)) + ");";
            return res;
        }
        return JsonUtil.toJson(map);
    }

    public String toUnicode(String input) {
        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();

        for (char ch : chars) {

            if (ch < 256) {
                builder.append(ch);
            } else {
                builder.append("\\u" + Integer.toHexString(ch & 0xffff));
            }

        }

        return builder.toString();

    }

    /**
     * callback参数验证
     */
    public boolean validCallbackName(String name) {
        if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
            return true;
        }
        return false;
    }

}
