package cn.itsource.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.itsource.mapper")
public class MybatisPlusConfiguration {
}
