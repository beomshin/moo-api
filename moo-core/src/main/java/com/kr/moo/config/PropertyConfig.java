package com.kr.moo.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

@Configuration
@PropertySource(
        value = {
                "classpath:application-core.yaml",
                "classpath:application-core-${spring.profiles.active}.yaml"
        },
        factory = PropertyConfig.YamlPropertySourceFactory.class
)
public class PropertyConfig {

        /**
         * Yaml 통합 factory 생성
         */
        public static class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

                @Override
                public org.springframework.core.env.PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
                        var factory = new YamlPropertiesFactoryBean();
                        factory.setResources(resource.getResource());
                        var props = factory.getObject();
                        return new PropertiesPropertySource(resource.getResource().getFilename(), props);
                }
        }
}