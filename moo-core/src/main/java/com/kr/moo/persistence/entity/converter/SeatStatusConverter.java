package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.SeatStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SeatStatusConverter implements AttributeConverter<SeatStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeatStatus seatStatus) {
        return seatStatus.getValue();
    }

    @Override
    public SeatStatus convertToEntityAttribute(Integer integer) {
        return SeatStatus.fromCode(integer);
    }
}
