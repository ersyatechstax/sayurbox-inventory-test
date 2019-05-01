package com.main.mapper.converter;

import com.main.mapper.ItemMapper;
import com.main.persistence.domain.Item;
import com.main.vo.ItemVO;
import org.springframework.stereotype.Component;

@Component
public class ItemVOConverter extends AbstractBaseVOConverter<Item,ItemVO> {

    @Override
    public Item transferVOToModel(ItemVO vo, Item model){
        return ItemMapper.INSTANCE.setVOtoModel(vo);
    }

    @Override
    public ItemVO transferModelToVO(Item model, ItemVO vo) {
        return ItemMapper.INSTANCE.setModelToVO(model);
    }


}
