package com.botlabs.minio.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties specific to minio client.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio", ignoreUnknownFields = false)
public class MinioProperties {

    /**
     *  minio access key ID
     */
    private String accessKey;

    /**
     *  minio secret access key
     */
    private String secretKey;

    /**
     * endpoint
     */
    private String endpoint;
    /**
     *  minio bucket name
     */
    private String bucket;
}
