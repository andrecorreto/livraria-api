package br.com.alura.livraria.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfigurations {

    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                        
          .build()
          .apiInfo(apiInfo());                                           
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfo( 
    	      "API Livraria", 
    	      "Projeto final Bootcamp Java - Alura", 
    	      "Termos de Uso", 
    	      "Termos de Serviço", 
    	      new Contact("André Correto", "www.example.com", "andrecorreto@msn.com"), 
    	      "License of API", "API license URL", Collections.emptyList());
    }
    
}
