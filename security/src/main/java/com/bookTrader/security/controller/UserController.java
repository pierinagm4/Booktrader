package com.bookTrader.security.controller;

import com.bookTrader.commons.util.SortFormatted;
import com.bookTrader.security.model.entities.User;
import com.bookTrader.security.service.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("user/FindAll")
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        try{
            Pageable paging = PageRequest.of(page, size, Sort.by(new SortFormatted().buildSort(sort)));
            Page<User> responseList = userService.findAll(paging);
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>("Error al listar usuarios", HttpStatus.BAD_REQUEST);
        }
    }

}
