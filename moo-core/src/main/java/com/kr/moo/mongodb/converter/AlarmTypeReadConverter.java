package com.kr.moo.mongodb.converter;

import com.kr.moo.mongodb.entity.enums.AlarmType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class AlarmTypeReadConverter implements Converter<Integer, AlarmType> {

    @Override
    public AlarmType convert(Integer source) {
        return AlarmType.fromCode(source);
    }
}
