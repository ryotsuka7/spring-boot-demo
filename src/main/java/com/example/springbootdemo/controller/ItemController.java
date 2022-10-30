package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.ItemResponse;
import com.example.springbootdemo.dto.Sample;
import com.example.springbootdemo.entity.Item;
import com.example.springbootdemo.mapper.ItemMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemMapper itemMapper;

    @GetMapping("/{id}")
    public ItemResponse findById(@PathVariable int id){
        // DBからidをキーにデータを取得
        Item item = itemMapper.findById(id);

        // Responseにデータをコピーしてreturn
        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item, itemResponse);
        return  itemResponse;
    }
}
