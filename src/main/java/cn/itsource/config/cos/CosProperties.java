package cn.itsource.config.cos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cos")
public class CosProperties {

    private String api_key;
    private String service_instance_id;
    private String endpoint_url;
    private String location;
    private String bucket_name;

}
