package com.devmare.hack4bengal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hack4BengalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hack4BengalApplication.class, args);
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("https://hack4bengal-427818.df.r.appspot.com")
//                        .allowedMethods("*")
//                        .allowedHeaders("*")
//                        .allowCredentials(true);
//            }
//        };
//    }
}
