package com.deofis.tiendaapirest.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "amazon.s3")
@Configuration
@Data
public class AmazonS3Client {

    private AmazonS3 s3Client;
    private String endpoint;
    private String bucketName;
    private String accesKey;
    private String secretKey;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accesKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3() {
        return this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.EU_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
    }

}
