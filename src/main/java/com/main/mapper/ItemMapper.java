package com.main.mapper;

import com.main.persistence.domain.Item;
import com.main.vo.ItemVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "id", source = "secureId")
    ItemVO setModelToVO(Item item);

    Item setVOtoModel(ItemVO itemVO);
}
