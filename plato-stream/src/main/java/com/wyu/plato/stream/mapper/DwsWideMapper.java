package com.wyu.plato.stream.mapper;

import com.wyu.plato.stream.domain.DwsWideInfo;
import com.wyu.plato.stream.domain.WideInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DwsWideMapper {
    DwsWideMapper INSTANCE = Mappers.getMapper(DwsWideMapper.class);

    DwsWideInfo wideToDws(WideInfo wide);
}
