package com.wyu.plato.account.component;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.wyu.plato.account.config.OSSProperties;
import com.wyu.plato.common.util.CommonUtil;
import com.wyu.plato.common.util.uuid.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author novo
 * @since 2023-02-27 22:21
 */
@Component
@Slf4j
public class UploadComponent {

    @Autowired
    private OSSProperties ossProperties;

    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            log.info("文件为空");
            return "";
        }
        String endpoint = this.ossProperties.getEndPoint();
        String accessKeyId = this.ossProperties.getAccessKeyId();
        String accessKeySecret = this.ossProperties.getAccessKeySecret();
        String bucketName = this.ossProperties.getBucketName();

        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null : "filename is null";

        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = CommonUtil.generateUUID();

        // java8日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folder = formatter.format(now);

        String finalName = "account" + "/" + folder + "/" + fileName + suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String imageUrl = "";
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, finalName, file.getInputStream());

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            if (result.getResponse().getStatusCode() == HttpStatus.OK.value()) {
                //imageUrl = result.getResponse().getUri();
                imageUrl = String.format("http://%s.%s/%s", bucketName, endpoint, finalName);
                log.info("文件上传成功 url:[{}]", imageUrl);
            } else {
                log.info("文件上传失败:[{}]", result.getResponse());
            }
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message:" + oe.getErrorMessage());
            log.error("Error Code:" + oe.getErrorCode());
            log.error("Request ID:" + oe.getRequestId());
            log.error("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            log.error("Error Message:" + e.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return imageUrl;
    }
}
