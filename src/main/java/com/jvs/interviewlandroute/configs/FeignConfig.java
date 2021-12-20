package com.jvs.interviewlandroute.configs;

import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Configuration
public class FeignConfig {
    @Bean
    public Decoder feignDecoder() {
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(new FeignClientMessageConverter());
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    private static class FeignClientMessageConverter extends MappingJackson2HttpMessageConverter {
        public FeignClientMessageConverter(){
            setSupportedMediaTypes(List.of(MediaType.TEXT_PLAIN));
        }
    }
}
