package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.OperatingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class OperatingStatusConverter implements AttributeConverter<OperatingStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OperatingStatus operatingStatus) {
        return operatingStatus.getValue();
    }

    @Override
    public OperatingStatus convertToEntityAttribute(Integer integer) {
        return OperatingStatus.fromCode(integer);
    }
}
