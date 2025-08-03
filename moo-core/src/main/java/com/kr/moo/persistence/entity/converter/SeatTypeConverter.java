package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.SeatType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SeatTypeConverter implements AttributeConverter<SeatType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SeatType seatType) {
        return seatType.getValue();
    }

    @Override
    public SeatType convertToEntityAttribute(Integer integer) {
        return SeatType.fromCode(integer);
    }
}
