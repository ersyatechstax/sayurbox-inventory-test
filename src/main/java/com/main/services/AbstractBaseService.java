package com.main.services;

import com.main.util.Constants;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by ersya 29/04/2019
 */
public class AbstractBaseService {
    public static Map<String, Object> contructPageToMap(List vos, int page, long totalElements, int totalPages) {
        Map<String, Object> map = new HashMap<>();

        map.put(Constants.PageParameter.LIST_DATA, vos);
        map.put(Constants.PageParameter.PAGE,page);
        map.put(Constants.PageParameter.TOTAL_ELEMENTS, totalElements);
        map.put(Constants.PageParameter.TOTAL_PAGES, totalPages);

        return map;
    }
}
