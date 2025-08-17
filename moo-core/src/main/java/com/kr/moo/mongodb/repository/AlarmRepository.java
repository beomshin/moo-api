package com.kr.moo.mongodb.repository;

import com.kr.moo.mongodb.entity.AlarmEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlarmRepository extends MongoRepository<AlarmEntity, Long> {


}
