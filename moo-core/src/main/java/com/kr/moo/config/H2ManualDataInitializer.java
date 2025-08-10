package com.kr.moo.config;

import com.kr.moo.constants.RedisKey;
import com.kr.moo.persistence.entity.StoreEntity;
import com.kr.moo.persistence.repository.StoreRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile("local")
public class H2ManualDataInitializer {

    private final DataSource dataSource;
    private final RedisTemplate<String, String> redisTemplate;
    private final StoreRepository storeRepository;

    @PostConstruct
    public void runScripts() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        populator.addScript(new ClassPathResource("data.sql"));
        populator.execute(dataSource);

        List<StoreEntity> stores = storeRepository.findAll();

        for (StoreEntity store : stores) {
            log.info("◆ 개발 환경 매장 레디스 bitMap 설정 : storeId {}, seatCount {}", store.getStoreId(), store.getSeatCount());
            redisTemplate.opsForValue().setBit(RedisKey.getSeatKey(store.getStoreId()), store.getSeatCount(), false);
        }
    }
}
