package com.example.multimedia.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author CookiesEason
 * 2018/07/25 14:28
 */
@Data
@Component
@ConfigurationProperties(prefix = "tengxun")
public class TengXunProperties {

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private String bucket;

    private String path;
}
