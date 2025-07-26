package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.UserLoginType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserLoginTypeConverter implements AttributeConverter<UserLoginType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserLoginType userLoginType) {
        return userLoginType.getValue();
    }

    @Override
    public UserLoginType convertToEntityAttribute(Integer integer) {
        return UserLoginType.fromCode(integer);
    }
}
