package com.main.services;

import com.main.mapper.converter.ItemVOConverter;
import com.main.mapper.converter.ItemViewVOConverter;
import com.main.persistence.domain.Item;
import com.main.persistence.domain.ItemView;
import com.main.persistence.repository.ItemRepository;
import com.main.persistence.repository.ItemViewRepository;
import com.main.util.PageBuilder;
import com.main.vo.BaseVO;
import com.main.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * created by ersya 29/04/2019
 */
@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemVOConverter itemVOConverter;

    @Autowired
    ItemViewRepository itemViewRepository;

    @Autowired
    ItemViewVOConverter itemViewVOConverter;

    @Transactional
    public BaseVO createItem(ItemVO itemVO){
        Item item = itemRepository.save(itemVOConverter.transferVOToModel(itemVO, null));
        return new BaseVO(item.getSecureId(),item.getVersion());
    }

    public Map<String, Object> getListAvailableItems(Integer page, Integer limit, String sortBy, String sortDirection){
        Pageable pageable = PageBuilder.buildPage(page,limit,sortBy,sortDirection);
        Page<ItemView> items = itemViewRepository.findByAvailableStockGreaterThan(0, pageable);
        List<ItemVO> itemVOS = itemViewVOConverter.transferListOfModelToListOfVO(items.getContent());
        return AbstractBaseService.contructPageToMap(itemVOS,items.getNumber(),items.getTotalElements(),items.getTotalPages());
    }
}
