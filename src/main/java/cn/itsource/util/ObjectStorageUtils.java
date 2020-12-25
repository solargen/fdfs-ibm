package cn.itsource.util;

import cn.itsource.config.cos.CosProperties;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class ObjectStorageUtils {

    @Autowired
    private CosProperties cosProperties;

    /**
     * 创建AmazonS3客户端
     * @return
     */
    public AmazonS3 createClient()
    {
        AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(cosProperties.getApi_key(), cosProperties.getService_instance_id());

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(15000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(cosProperties.getEndpoint_url(), cosProperties.getLocation())).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }

    /**
     * 文件上传
     * @param itemName
     * @param inputStream
     */
    public void uploadFile(String itemName, InputStream inputStream) {
        AmazonS3 client = createClient();
        log.debug("开始上传{}文件", itemName);
        client.putObject(cosProperties.getBucket_name(), itemName, inputStream, null);
        System.out.printf("文件{}上传完毕", itemName);
    }

}
