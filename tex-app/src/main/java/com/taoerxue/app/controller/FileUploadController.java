package com.taoerxue.app.controller;

import com.taoerxue.common.bean.Result;
import com.taoerxue.common.exception.FileUploadException;
import com.taoerxue.common.os.StoragePlatform;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 * Created by lizhihui on 2017-03-16 10:01.
 */
@RestController
@RequestMapping("/file")
public class FileUploadController extends BaseController {

    private static final String PNG_CONTENT_TYPE = "image/png";
    private static final String JPG_CONTENT_TYPE = "image/jpeg";

    @Resource
    private StoragePlatform storagePlatform;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        // TODO 要不要记录谁上传了图片 log记录

        //判断图片是否为空
        if (file == null)
            throw new FileUploadException("请上传图片");

        //判断文件格式是否是图片类型,前期只能上传 png,jpg,jpeg
        if (!isImage(file))
            return Result.build(500, "请上传png,jpeg 格式的文件");

        //判断文件大小
        if (file.getSize() > 30000 * 1024)
            return Result.build(500, "图片大小不能超过30MB");

        //获取原文件的后缀名
        String suffixFileName = getSuffixFileName(file.getOriginalFilename());

        // TODO 文件命名有待确定,暂时使用 UUID 作为文件名的前缀
        //生成新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + suffixFileName;

        //七牛云上传
        Boolean result = storagePlatform.uploadImages(file.getInputStream(), newFileName);
        if (result) {
            Map<String, String> map = new HashMap<>();
            map.put("url", newFileName);
            return Result.ok(map);
        }
        return Result.build(500, "上传失败");
    }

    /**
     * 判断是否是图片文件
     *
     * @param file spring mvc MultipartFile 类型
     * @return 返回 bool 类型
     */
    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType.equals(PNG_CONTENT_TYPE) || contentType.equals(JPG_CONTENT_TYPE);
    }

    private String getSuffixFileName(String fileName) {
        return fileName.substring(fileName.indexOf(".") + 1, fileName.length());
    }

}
