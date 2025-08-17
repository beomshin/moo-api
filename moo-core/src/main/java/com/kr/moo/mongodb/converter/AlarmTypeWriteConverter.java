package com.kr.moo.mongodb.converter;

import com.kr.moo.mongodb.entity.enums.AlarmType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class AlarmTypeWriteConverter implements Converter<AlarmType, Integer> {

    @Override
    public Integer convert(AlarmType source) {
        return source.getValue();
    }
}
