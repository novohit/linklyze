package com.linklyze.stream.mapper;

import com.linklyze.stream.domain.DwsWideInfo;
import com.linklyze.stream.domain.WideInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DwsWideMapper {
    DwsWideMapper INSTANCE = Mappers.getMapper(DwsWideMapper.class);

    DwsWideInfo wideToDws(WideInfo wide);
}
