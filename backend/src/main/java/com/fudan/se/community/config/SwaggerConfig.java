package com.fudan.se.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
// Failed to start bean 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
// 可以用另一种方法解决 yml文件配置
public class SwaggerConfig extends WebMvcConfigurationSupport {
    // 指定匹配的静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** swagger配置 */
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    // Docket Bean
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("adweb") // api文档分组
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fudan.se.community.controller"))
                // RequestHandlerSelectors配置扫描接口的方式
                //.paths(PathSelectors.ant("/login/**"))
                // paths() 扫描过滤方式
                .build();
    }

    public ApiInfo apiInfo() {
        Contact contact = new Contact("","","");
        return new ApiInfoBuilder()
                .title("Community API文档")
                .description("web3d virtual community")
                .version("1.0")
                .contact(contact)
                .license("The Apache License")
                .licenseUrl("http://www.baidu.com")
                .build();
    }
}
