package com.ciel.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/ser/**")
                        .and()
                        .before(LocalDateTime.of(2020, 5, 13, 19, 30, 5).atZone(ZoneId.of("Asia/Shanghai")))
                        // .and()
                        // .header("X-Request-Id","\\d+")
                        .filters(f ->  f.stripPrefix(1)
                        )
                        .uri("lb://server")
                        .order(0)
                        .id("server"))

                .build();
    }
}
