package com.taoerxue.common.os;

import java.io.InputStream;

public interface StoragePlatform {
    Boolean uploadImages(InputStream inputStream, String fileName);

    String getImageURL(String fileName);

    Boolean exists(String fileName);
}
