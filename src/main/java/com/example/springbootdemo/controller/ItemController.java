package com.example.springbootdemo.controller;

import com.example.springbootdemo.dto.ItemRequest;
import com.example.springbootdemo.dto.ItemResponse;
import com.example.springbootdemo.entity.Item;
import com.example.springbootdemo.mapper.ItemMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemMapper itemMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponse findById(@PathVariable int id) {
        // DBからidをキーにデータを取得
        Item item = itemMapper.findById(id);

        // Responseにデータをコピーしてreturn
        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item, itemResponse);
        return itemResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponse> getItems() {
        List<ItemResponse> itemResponseList = new ArrayList<>();

        List<Item> itemList = itemMapper.findAll();

        itemList.forEach(item -> {
            ItemResponse itemResponse = new ItemResponse();
            BeanUtils.copyProperties(item, itemResponse);
            itemResponseList.add(itemResponse);
        });

        return itemResponseList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public ItemResponse doPost(@RequestBody ItemRequest itemRequest) {
        Item item = new Item();
        BeanUtils.copyProperties(itemRequest, item);

        int ret = itemMapper.insert(item);

        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item, itemResponse);

        return itemResponse;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ItemResponse doPut(@PathVariable int id, @RequestBody ItemRequest itemRequest) {
        Item item = new Item();
        BeanUtils.copyProperties(itemRequest, item);
        item.setId(id);
        itemMapper.update(item);

        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item, itemResponse);

        return itemResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void doDelete(@PathVariable int id) {
        boolean ret = itemMapper.delete(id);
    }
}
