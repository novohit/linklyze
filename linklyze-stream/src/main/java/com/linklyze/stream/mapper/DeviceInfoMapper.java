package com.linklyze.stream.mapper;

import com.linklyze.stream.domain.DeviceInfo;
import com.linklyze.stream.domain.WideInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceInfoMapper {

    DeviceInfoMapper INSTANCE = Mappers.getMapper(DeviceInfoMapper.class);

    WideInfo deviceToWide(DeviceInfo deviceInfo);
}
