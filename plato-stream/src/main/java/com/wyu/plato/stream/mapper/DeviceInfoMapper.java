package com.wyu.plato.stream.mapper;

import com.wyu.plato.stream.domain.DeviceInfo;
import com.wyu.plato.stream.domain.WideInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceInfoMapper {

    DeviceInfoMapper INSTANCE = Mappers.getMapper(DeviceInfoMapper.class);

    WideInfo deviceToWide(DeviceInfo deviceInfo);
}
