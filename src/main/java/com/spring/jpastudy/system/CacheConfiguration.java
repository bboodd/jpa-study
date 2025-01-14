package com.spring.jpastudy.system;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableCaching
public class CacheConfiguration {

	@Bean
	public JCacheManagerCustomizer petclinicCacheConfigurationCustomizer() {
		return cacheManager -> cacheManager.createCache("vets", cacheConfiguration());
	}

	private javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
		return new MutableConfiguration<>().setStatisticsEnabled(true);
	}
}
