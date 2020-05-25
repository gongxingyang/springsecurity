package com.taikang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author itw_gongxy
 * @date 2020/4/17 13:56
 */
@RestController
public class IdentifierController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${service-identifier-time}")
    private Integer serviceIdentifierTime;
    private static final String SOURCES="1234567890QWERTYUIOPASDFGHJKLZXCVBNM";

    @GetMapping("/identifier/code")
    public String getIdentifierCode(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        IntStream.range(0,8).forEach(value->{
            sb.append(SOURCES.charAt(random.nextInt(SOURCES.length())));
        });
        String identifierCode = sb.toString();
        redisTemplate.opsForValue().set("SERVICE_CODE:"+identifierCode,identifierCode,serviceIdentifierTime, TimeUnit.SECONDS);
        return identifierCode;
    }
}
