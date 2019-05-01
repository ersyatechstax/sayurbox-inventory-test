package com.main.util;

import com.main.exception.GeneralException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * created by ersya 30/03/2019
 */
public class PageBuilder {
    public static Pageable buildPage(Integer page, Integer limit, String sortBy, String sortDirection){
        Pageable pageable;
        if(sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("ascending")){
            pageable = PageRequest.of(page,limit, Sort.by(sortBy).ascending());
        }
        else if(sortDirection.equalsIgnoreCase("desc") || sortDirection.equalsIgnoreCase("descending")){
            pageable = PageRequest.of(page,limit, Sort.by(sortBy).descending());
        }
        else{
            throw new GeneralException("Wrong sorting direction");
        }
        return pageable;
    }
}
