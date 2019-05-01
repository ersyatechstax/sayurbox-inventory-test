package com.main.mapper;

import com.main.persistence.domain.Test;
import com.main.vo.TestVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * created by ersya 30/03/2019
 */
@Mapper(componentModel = "spring")
public interface TestMapper {
    TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);


    @Mapping(target = "id", source = "secureId")
    TestVO modelToVO(Test test);

    Test VOToModel(TestVO testVO);
}
