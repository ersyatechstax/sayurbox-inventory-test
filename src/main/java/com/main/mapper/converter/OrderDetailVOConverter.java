package com.main.mapper.converter;

import com.main.persistence.domain.OrderDetail;
import com.main.vo.CartVO;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailVOConverter extends AbstractBaseVOConverter<OrderDetail, CartVO> {

    @Override
    public CartVO transferModelToVO(OrderDetail model, CartVO vo) {
        if(vo == null) vo = new CartVO();
        super.transferModelToVO(model, vo);
        vo.setOrderId(model.getOrder().getSecureId());
        vo.setItemId(model.getItem().getSecureId());
        vo.setItemName(model.getItem().getName());
        vo.setNote(model.getNote());
        vo.setQuantity(model.getQuantity());
        vo.setPricePerQty(model.getItem().getPrice());
        vo.setTotalPrice(model.getItem().getPrice() * model.getQuantity());
        return vo;
    }
}
