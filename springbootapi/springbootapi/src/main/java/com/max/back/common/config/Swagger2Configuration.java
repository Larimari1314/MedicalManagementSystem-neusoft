package com.max.back.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    public static final String TAG_API = "API";
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())//api的配置路径
                .paths(PathSelectors.any())//扫描路径选择
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("API", "API描述"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("title") //文档标题
                .description("description")//接口概述
                .version("1.0") //版本号
                .termsOfServiceUrl(String.format("url"))//服务的域名
                .build();
    }
}
