package com.viavarejo.teste;

import java.util.ArrayList;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfiguration {

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.viavarejo.teste.rest")) //pacote das controllers
          // .paths(PathSelectors.ant("/api/**"))                          
          .build()
          .globalOperationParameters(globalOperationParameters()) //opcional
          .apiInfo(apiInfo())          
          .enableUrlTemplating(false);                                          
    }

    private ArrayList<Parameter> globalOperationParameters() {
        return Lists.newArrayList(new ParameterBuilder()
              .name("Authorization")
              .description("Authorization Token")
              .modelRef(new ModelRef("string"))
              .parameterType("header")
              .required(false)
              .build());
    }


    private ApiInfo apiInfo() {     
        return new ApiInfoBuilder()
            .title("UtilService")
            .description("Serviços genericos para o iFootball")
            .version("1.0")
            .build();
    }
}
