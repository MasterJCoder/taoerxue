package com.taoerxue.common.os.qiniuyun;


import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.taoerxue.common.os.StoragePlatform;
import org.springframework.beans.factory.annotation.Value;

import java.io.InputStream;


public class Qiniu implements StoragePlatform {

    @Value("${PRODUCT}")
    Boolean product;
    @Value("${PHOTO_URL_TEST}")
    String photoURLTest;
    @Value("${PHOTO_URL_PRODUCT}")
    String photoURLProduct;

    private final String testBucket = "taoerxue-test";
    private final String productBucket = "taoerxue-product";

    private final String ACCESS_KEY = "ZI9uL9zjVVom0qVgYQRAt-B984Zw7yFnBA72CVTc";
    private final String SECRET_KEY = "a7zJHEq7BuAGqSbj0jN3IjL4wOdAyr_X6Yianf7O";
    private final Auth auth = com.qiniu.util.Auth.create(ACCESS_KEY, SECRET_KEY);
    //指定上传地区为华东地区
    private final Zone zone = Zone.zone0();

    private final Configuration configuration = new Configuration(zone);
    private final UploadManager uploadManager = new UploadManager(configuration);
    private final BucketManager bucketManager = new BucketManager(auth, configuration);

    public Boolean uploadImages(InputStream inputStream, String fileName) {

        try {
            uploadManager.put(inputStream, fileName, auth.uploadToken(getBucket(), fileName), null, null);
        } catch (QiniuException e) {
            return false;
        }
        return true;
    }

    @Override
    public String getImageURL(String fileName) {
        return getURL() + fileName;
    }

    @Override
    public Boolean exists(String fileName) {
        try {
            FileInfo fileInfo = bucketManager.stat(getBucket(), fileName);
            System.out.println(fileInfo);
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //获取 bucket
    private String getBucket() {
        return product ? productBucket : testBucket;
    }

    //获取图片的 url 路径
    private String getURL() {
        return product ? photoURLProduct : photoURLTest;
    }


}
