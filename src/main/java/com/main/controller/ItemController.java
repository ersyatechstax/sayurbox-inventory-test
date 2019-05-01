package com.main.controller;

import com.main.services.ItemService;
import com.main.vo.ItemVO;
import com.main.vo.ResponsePageVO;
import com.main.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> createItem(@Valid @RequestBody ItemVO itemVO, Errors errors){
        AbstractRequestHandler handler = new AbstractRequestHandler() {
            @Override
            public Object processRequest() {
                return itemService.createItem(itemVO);
            }
        };
        return handler.getResultWithValidation(errors);
    }

    @GetMapping("/get-list-available-items")
    public ResponseEntity<ResponsePageVO> getListAvailableItems(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "limit",defaultValue = "25") Integer limit,
            @RequestParam(value = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(value = "sort-direction", defaultValue = "asc") String sortDirection
    ){
        AbstractPageRequestHandler handler = new AbstractPageRequestHandler() {
            @Override
            public Map<String, Object> processRequest() {
                return itemService.getListAvailableItems(page,limit,sortBy,sortDirection);
            }
        };
        return handler.getResult();
    }
}
