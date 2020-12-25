package cn.itsource.util;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

public class ObjectStorageUtils {

    private static AmazonS3 _cosClient;

    public static void main(String[] args) {

        SDKGlobalConfiguration.IAM_ENDPOINT = "https://iam.cloud.ibm.com/identity/token";

        String bucketName = "cloud-object-storage-ds-cos-standard-solargen";
        String api_key = "AH_FlohlOGL3sIqrTCFL3QJtERH4pZMfkWQEumANWrBs"; // eg "W00YiRnLW4a3fTjMB-oiB-2ySfTrFBIQQWanc--P3byk"
        String service_instance_id = "crn:v1:bluemix:public:cloud-object-storage:global:a/3a0681143ecb45dc9bf321cfec0a5be6:126288b6-6c86-409b-9caa-648dcd4965e2::"; // eg "crn:v1:bluemix:public:cloud-object-storage:global:a/3bf0d9003abfb5d29761c3e97696b71c:d6f04d83-6c4f-4a62-a165-696756d63903::"
        String endpoint_url = "https://control.cloud-object-storage.cloud.ibm.com/v2/endpoints"; // this could be any service endpoint

        String location = "jp-tok";

        _cosClient = createClient(api_key, service_instance_id, endpoint_url, location);

        listObjects(bucketName,_cosClient);


    }
    public static void createTextFile(String bucketName, String itemName, String fileText) {
        System.out.printf("Creating new item: %s\n", itemName);

        InputStream newStream = new ByteArrayInputStream(fileText.getBytes(StandardCharsets.UTF_8));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileText.length());

        PutObjectRequest req = new PutObjectRequest(bucketName, itemName, newStream, metadata);
        _cosClient.putObject(req);

        System.out.printf("Item: %s created!\n", itemName);
    }


    public static void createBucket(String bucketName) {
        System.out.printf("Creating new bucket: %s\n", bucketName);
        _cosClient.createBucket(bucketName);
        System.out.printf("Bucket: %s created!\n", bucketName);
    }

    public static AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url, String location)
    {
        AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint_url, location)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }

    public static void listObjects(String bucketName, AmazonS3 cosClient)
    {
        System.out.println("Listing objects in bucket " + bucketName);
        ObjectListing objectListing = cosClient.listObjects(new ListObjectsRequest().withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
    }

    public static void createBucket(String bucketName, AmazonS3 cosClient, String storageClass)
    {
        cosClient.createBucket(bucketName, storageClass);
    }

    public static void listBuckets(AmazonS3 cosClient)
    {
        System.out.println("Listing buckets");
        final List<Bucket> bucketList = _cosClient.listBuckets();
        for (final Bucket bucket : bucketList) {
            System.out.println(bucket.getName());
        }
        System.out.println();
    }

}
