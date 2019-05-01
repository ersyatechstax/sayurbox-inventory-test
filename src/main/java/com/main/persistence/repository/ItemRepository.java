package com.main.persistence.repository;

import com.main.persistence.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends BaseRepository<Item> {

    Optional<Item> findByName(String name);

    List<Item> findBySecureIdIn(List<String> secureIds);

    Page<Item> findByStockGreaterThan(Integer stock, Pageable pageable);
}
