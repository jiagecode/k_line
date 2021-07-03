package com.line.backstage.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;


/**
 * @author BaoBao
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2 {

    /**
     * 多模块配置 是否渲染控制台
     */
    private boolean enable = true;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("woSen猎头")
                .description("寻找发光的你")
                .termsOfServiceUrl("http://192.168.1.9/")
                .version("1.0")
                .contact("我是你嘉哥哦~")
                .build();
    }

    @Bean(value = "controllerApi")
    public Docket controllerApi() {
        ParameterBuilder pb = new ParameterBuilder();
        List<Parameter> lp = Lists.newArrayList();
        pb.name("Authorization")
                .description("User Authorization")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .build();
        // 根据每个方法名也知道当前方法在设置什么参数
        lp.add(pb.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("controllerApi")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.line.backstage.controller"))
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
}
