package com.github.sjacek.swagger_demo.client.config;

import com.github.sjacek.swagger_demo.client.api.HelloControllerApi;
import com.github.sjacek.swagger_demo.client.invoker.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;

import static java.nio.charset.Charset.forName;
import static java.util.Collections.singletonList;

@Configuration
public class ApiClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${helloservice.url:http://localhost:8080}")
    private String helloServiceUrl;

    @Bean
    public RestTemplate restTemplateSGR(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(forName("UTF-16")));

        //set interceptors/requestFactory
        ClientHttpRequestInterceptor ri = (request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            logger.info(request.getURI().toURL().toString());

            return response;
        };
        restTemplate.setInterceptors(singletonList(ri));
//        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        return restTemplate;
    }

    @Bean
    public HelloControllerApi helloControllerApi() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(helloServiceUrl);
        return new HelloControllerApi(apiClient);
    }
}
