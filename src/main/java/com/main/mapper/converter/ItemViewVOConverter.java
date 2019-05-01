package com.main.mapper.converter;

import com.main.persistence.domain.ItemView;
import com.main.vo.ItemVO;
import org.springframework.stereotype.Component;

@Component
public class ItemViewVOConverter extends AbstractNonBaseVOConverter<ItemView, ItemVO> {

    @Override
    public ItemVO transferModelToVO(ItemView model, ItemVO vo){
        if(vo == null)vo = new ItemVO();
        vo.setId(model.getSecureId());
        vo.setVersion(model.getVersion());
        vo.setName(model.getName());
        vo.setDescription(model.getDescription());
        vo.setStock(model.getActualStock());
        vo.setReservedStock(model.getReservedStock());
        vo.setAvailableStock(model.getAvailableStock());
        return vo;
    }
}
