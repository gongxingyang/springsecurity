package com.taikang.config;

import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.time.Duration;

@Configuration
public class RedisLettuceConfiguration {

	@Value(value = "${spring.redis.host}")
	private String host;
	@Value(value = "${spring.redis.port}")
	private Integer port;
	@Value(value = "${spring.redis.password}")
	private String passWord;

//连接factory 
	public @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
		return new LettuceConnectionFactory(redisStandaloneConfiguration(), lettuceClientConfiguration());
	}

	public @Bean(value = "redisTemplate")
    StringRedisTemplate redisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		LettuceConnectionFactory factory = lettuceConnectionFactory();
		factory.afterPropertiesSet();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setEnableTransactionSupport(false);
		StringRedisSerializer serializer = new StringRedisSerializer(Charset.forName("UTF-8"));
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(serializer);
		redisTemplate.setHashValueSerializer(serializer);
		redisTemplate.setStringSerializer(serializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;

	}

	public @Bean
    LettuceClientConfiguration lettuceClientConfiguration() {
		return LettucePoolingClientConfiguration.builder().clientResources(lettuceClientResources()).poolConfig(genericObjectPoolConfig())
				.commandTimeout(Duration.ofSeconds(5)).shutdownTimeout(Duration.ZERO).build();

	}
	
	@Bean(destroyMethod = "shutdown")
	public DefaultClientResources lettuceClientResources() {
		return DefaultClientResources.create();
	}

	public @Bean
    GenericObjectPoolConfig genericObjectPoolConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(8);
		config.setMaxIdle(8);
		config.setMinIdle(0);
		config.setMaxWaitMillis(6000);
		return config;

	}

	public @Bean
    RedisStandaloneConfiguration redisStandaloneConfiguration() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
		redisStandaloneConfiguration.setPassword(RedisPassword.of(passWord));
		return redisStandaloneConfiguration;

	}

}
