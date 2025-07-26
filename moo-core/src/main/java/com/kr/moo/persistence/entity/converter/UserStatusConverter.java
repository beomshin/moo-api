package com.kr.moo.persistence.entity.converter;

import com.kr.moo.persistence.entity.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserStatus userStatus) {
        return userStatus.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer integer) {
        return UserStatus.fromCode(integer);
    }
}
