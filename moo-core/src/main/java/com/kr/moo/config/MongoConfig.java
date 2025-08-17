package com.kr.moo.config;

import com.kr.moo.mongodb.converter.AlarmTypeReadConverter;
import com.kr.moo.mongodb.converter.AlarmTypeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    /**
     * 몽고디비 config
     * @return
     */

    @Bean
    public MongoCustomConversions customConversions() {

        /**
         * converter 등록
         */

        return new MongoCustomConversions(
                Arrays.asList(
                        new AlarmTypeWriteConverter(),
                        new AlarmTypeReadConverter()
                )
        );
    }

}
