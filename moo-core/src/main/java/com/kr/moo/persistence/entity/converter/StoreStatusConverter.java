package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.StoreStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StoreStatusConverter implements AttributeConverter<StoreStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StoreStatus storeStatus) {
        return storeStatus.getValue();
    }

    @Override
    public StoreStatus convertToEntityAttribute(Integer integer) {
        return StoreStatus.fromCode(integer);
    }
}
